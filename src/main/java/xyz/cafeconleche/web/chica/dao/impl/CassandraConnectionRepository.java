package xyz.cafeconleche.web.chica.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.connect.NoSuchConnectionException;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Select.Where;

import xyz.cafeconleche.web.chica.entity.UserConnection;

public class CassandraConnectionRepository implements ConnectionRepository {

	private final String userId;
	
	private final CassandraAdminOperations cassandraTemplate;
	
	private final ConnectionFactoryLocator connectionFactoryLocator;

	private final TextEncryptor textEncryptor;

	private final String tablePrefix;
	
	public CassandraConnectionRepository(String userId, CassandraAdminOperations cassandraTemplate,
			ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor, String tablePrefix) {
		
		this.userId = userId;
		this.cassandraTemplate = cassandraTemplate;
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.textEncryptor = textEncryptor;
		this.tablePrefix = tablePrefix;
	}
	
	@Override
	public MultiValueMap<String, Connection<?>> findAllConnections() {
		
		Statement selectStatement = selectFromUserConnection()
				.where(QueryBuilder.eq("username", userId))
				.orderBy(QueryBuilder.asc("providerId"), QueryBuilder.asc("rank"));
		
		List<UserConnection> userConnections = cassandraTemplate.select(selectStatement, UserConnection.class);
		System.out.println("--- userConnections: " + userConnections);
		
		List<Connection<?>> resultList = map(userConnections);
		System.out.println("---resultList: " + resultList);
		
		MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();
		Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
		for (String registeredProviderId : registeredProviderIds) {
			connections.put(registeredProviderId, Collections.<Connection<?>>emptyList());
		}
		for (Connection<?> connection : resultList) {
			String providerId = connection.getKey().getProviderId();
			if (connections.get(providerId).size() == 0) {
				connections.put(providerId, new LinkedList<Connection<?>>());
			}
			connections.add(providerId, connection);
		}
		return connections;
	}

	@Override
	public List<Connection<?>> findConnections(String providerId) {
		
		Statement selectStatement = selectFromUserConnection()
				.where(QueryBuilder.eq("username", userId))
				.and(QueryBuilder.eq("providerId", providerId))
				.orderBy(QueryBuilder.asc("providerId"), QueryBuilder.asc("rank"));
		
		List<UserConnection> userConnections = cassandraTemplate.select(selectStatement, UserConnection.class);
		System.out.println("--- userConnections: " + userConnections);
		
		return map(userConnections);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A> List<Connection<A>> findConnections(Class<A> apiType) {
		List<?> connections = findConnections(getProviderId(apiType));
		return (List<Connection<A>>) connections;
	}

	@Override
	public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUserIds) {
		
		if (providerUserIds == null || providerUserIds.isEmpty()) {
			throw new IllegalArgumentException("Unable to execute find: no providerUsers provided");
		}
		
		List<UserConnection> userConnections = new ArrayList<>();
		Select select = selectFromUserConnection();
		Where where = select.where(QueryBuilder.eq("username", userId)); 
		
		for (Iterator<Entry<String, List<String>>> it = providerUserIds.entrySet().iterator(); it.hasNext();) {
			Entry<String, List<String>> entry = it.next();
			String providerId = entry.getKey();
			
			where.and(QueryBuilder.eq("providerId", providerId)).and(QueryBuilder.in("providerUserId", entry.getValue()))
				.orderBy(QueryBuilder.asc("providerId"), QueryBuilder.asc("rank"));
			
			List<UserConnection> tempUserConnections = cassandraTemplate.select(where, UserConnection.class);
			System.out.println("tempUserConnections: " + tempUserConnections);
			userConnections.addAll(tempUserConnections);
		}
		
		System.out.println("userConnections: " + userConnections);
		List<Connection<?>> resultList = map(userConnections);
		
		System.out.println("resultList: " + resultList);
		
		MultiValueMap<String, Connection<?>> connectionsForUsers = new LinkedMultiValueMap<String, Connection<?>>();
		for (Connection<?> connection : resultList) {
			String providerId = connection.getKey().getProviderId();
			List<String> userIds = providerUserIds.get(providerId);
			List<Connection<?>> connections = connectionsForUsers.get(providerId);
			if (connections == null) {
				connections = new ArrayList<Connection<?>>(userIds.size());
				for (int i = 0; i < userIds.size(); i++) {
					connections.add(null);
				}
				connectionsForUsers.put(providerId, connections);
			}
			String providerUserId = connection.getKey().getProviderUserId();
			int connectionIndex = userIds.indexOf(providerUserId);
			connections.set(connectionIndex, connection);
		}
		
		System.out.println("connectionsForUsers: " + connectionsForUsers);
		return connectionsForUsers;
	}

	@Override
	public Connection<?> getConnection(ConnectionKey connectionKey) {
		
		System.out.println("getConnection");
		try {
			
			Statement selectStatement = selectFromUserConnection()
					.where(QueryBuilder.eq("username", userId))
					.and(QueryBuilder.eq("providerId", connectionKey.getProviderId()))
					.and(QueryBuilder.eq("providerUserId", connectionKey.getProviderUserId()));
			
			UserConnection userConnection = cassandraTemplate.selectOne(selectStatement, UserConnection.class);
			System.out.println("userConnection: " + userConnection);
			Connection<?> connection = map(userConnection);
			System.out.println("connection: " + connection);
			
			return connection;
		} catch (EmptyResultDataAccessException e) {
			System.err.println(e.getMessage());
			throw new NoSuchConnectionException(connectionKey);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
		System.out.println("getConnection apytype: " + apiType + ", providerUserId: " + providerUserId);
		String providerId = getProviderId(apiType);
		return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
		String providerId = getProviderId(apiType);
		Connection<A> connection = (Connection<A>) findPrimaryConnection(providerId);
		if (connection == null) {
			throw new NotConnectedException(providerId);
		}
		return connection;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
		String providerId = getProviderId(apiType);
		return (Connection<A>) findPrimaryConnection(providerId);
	}

	@Override
	public void addConnection(Connection<?> connection) {
		
		try {
			String tableName = tablePrefix + "user_connection";
			ConnectionData data = connection.createData();
			
		//	select max(rank) as rank from user_connection where username = 'username1' and providerId = 'facebook';
			Statement selectStatement = QueryBuilder.select("select max(rank) as rank")
				.from(tableName)
				.where(QueryBuilder.eq("username", userId))
				.and(QueryBuilder.eq("providerId", data.getProviderId()));
		
		
			UserConnection userConnection = map(data);
			userConnection.setUsername(userId);
			
			int rank = cassandraTemplate.selectOne(selectStatement, Integer.class);
			userConnection.setRank(++rank);
			
			cassandraTemplate.insert(userConnection);

		} catch (DuplicateKeyException e) {
			System.err.println("Duplicate key: " + e.getMessage());
			throw new DuplicateConnectionException(connection.getKey());
		}
	}

	@Override
	public void updateConnection(Connection<?> connection) {
		
		ConnectionData data = connection.createData();
		Statement selectStatement = selectFromUserConnection()
				.where(QueryBuilder.eq("username", userId))
				.and(QueryBuilder.eq("providerId", data.getProviderId()))
				.and(QueryBuilder.eq("providerUserId", data.getProviderUserId()));
		
		UserConnection userConnection = cassandraTemplate.selectOne(selectStatement, UserConnection.class);
		System.out.println("userConnection: " + userConnection);
		
		userConnection.setDisplayName(data.getDisplayName());
		userConnection.setProfileUrl(data.getProfileUrl());
		userConnection.setImageUrl(data.getImageUrl());
		userConnection.setAccessToken(encrypt(data.getAccessToken()));
		userConnection.setSecret(encrypt(data.getSecret()));
		userConnection.setRefreshToken(encrypt(data.getRefreshToken()));
		userConnection.setExpireTime(data.getExpireTime());
				
		cassandraTemplate.update(userConnection);
	}
	
	@Override
	public void removeConnections(String providerId) {

		String tableName = tablePrefix + "user_connection";
		
		Statement deleteStatement = QueryBuilder.delete().from(tableName)
				.where(QueryBuilder.eq("username", userId))
				.and(QueryBuilder.eq("providerId", providerId));
		
		boolean ok = cassandraTemplate.getCqlOperations().execute(deleteStatement);
		System.out.println("removeConnections: " + ok);
	}

	@Override
	public void removeConnection(ConnectionKey connectionKey) {

		String tableName = tablePrefix + "user_connection";
		
		Statement deleteStatement = QueryBuilder.delete().from(tableName)
				.where(QueryBuilder.eq("username", userId))
				.and(QueryBuilder.eq("providerId", connectionKey.getProviderId()))
				.and(QueryBuilder.eq("providerUserId", connectionKey.getProviderUserId()));
		
		boolean ok = cassandraTemplate.getCqlOperations().execute(deleteStatement);
		System.out.println("removeConnection: " + ok);
	}
	
	private Select selectFromUserConnection() {
		
		String tableName = tablePrefix + "user_connection";
		
		return QueryBuilder
				.select("username", "providerId", "providerUserId", "accessToken", "displayName", "expireTime", "imageUrl", "profileUrl", "rank", "refreshToken", "secret")
				.from(tableName);
	}
	
	private List<Connection<?>> map(List<UserConnection> userConnections) {
		System.out.println("--- map userConnections: " + userConnections);
		
		List<Connection<?>> resultList = new ArrayList<>();
		
		for (UserConnection userConnection : userConnections) {
			Connection<?> connection = map(userConnection); 
			resultList.add(connection);
		}
		
		return resultList;
	}
	
	private Connection<?> map(UserConnection userConnection) {
		System.out.println("--- map userConnection: " + userConnection);
		
		ConnectionData connectionData = new ConnectionData(userConnection.getProviderId(), userConnection.getProviderUserId(), userConnection.getDisplayName(), 
				userConnection.getProfileUrl(), userConnection.getImageUrl(),
				decrypt(userConnection.getAccessToken()), 
				decrypt(userConnection.getSecret()), 
				decrypt(userConnection.getRefreshToken()), 
				expireTime(userConnection.getExpireTime()));
		
		ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());
		Connection<?> connection = connectionFactory.createConnection(connectionData);
		
		return connection;
	}
	
	private UserConnection map(ConnectionData data) { 
		UserConnection userConnection = new UserConnection();
		userConnection.setUsername(userId);
		userConnection.setProviderId(data.getProviderId());
		userConnection.setProviderUserId(data.getProviderUserId());
		userConnection.setAccessToken(encrypt(data.getAccessToken()));
		userConnection.setDisplayName(data.getDisplayName());
		userConnection.setExpireTime(data.getExpireTime());
		userConnection.setImageUrl(data.getImageUrl());
		userConnection.setProfileUrl(data.getProfileUrl());
		userConnection.setRefreshToken(encrypt(data.getRefreshToken()));
		userConnection.setSecret(encrypt(data.getSecret()));
		return userConnection;
	}
	
	private <A> String getProviderId(Class<A> apiType) {
		return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
	}
	
	private Connection<?> findPrimaryConnection(String providerId) {

		System.out.println("findPrimaryConnection providerId: " + providerId);
		
		Statement selectStatement = selectFromUserConnection()
				.where(QueryBuilder.eq("username", userId))
				.and(QueryBuilder.eq("providerId", providerId))
				.orderBy(QueryBuilder.asc("rank"));
		
		List<UserConnection> userConnections = cassandraTemplate.select(selectStatement, UserConnection.class);
		System.out.println("userConnections: " + userConnections);
		List<Connection<?>> connections = map(userConnections);
		System.out.println("connections: " + connections);
		
		if (connections.size() > 0) {
			return connections.get(0);
		} else {
			return null;
		}		
	}
	
	private String decrypt(String encryptedText) {
		return encryptedText != null ? textEncryptor.decrypt(encryptedText) : encryptedText;
	}
	
	private Long expireTime(long expireTime) {
		return expireTime == 0 ? null : expireTime;
	}
	
	private String encrypt(String text) {
		return text != null ? textEncryptor.encrypt(text) : text;
	}
	

}

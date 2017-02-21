package xyz.cafeconleche.web.chica.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

//@Repository("cassandraUsersConnectionRepository")
//@Repository("usersConnectionRepository")
public class CassandraUsersConnectionRepository implements UsersConnectionRepository {

	private CassandraAdminOperations cassandraTemplate;
	
	private final ConnectionFactoryLocator connectionFactoryLocator;
	
	private final TextEncryptor textEncryptor;
	
	private ConnectionSignUp connectionSignUp;
	
	private String tablePrefix = "";
	
	public CassandraUsersConnectionRepository(CassandraAdminOperations cassandraTemplate, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
		this.cassandraTemplate = cassandraTemplate; // TODO check if requires new
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.textEncryptor = textEncryptor;
	}
	
	/**
	 * The command to execute to create a new local user profile in the event no user id could be mapped to a connection.
	 * Allows for implicitly creating a user profile from connection data during a provider sign-in attempt.
	 * Defaults to null, indicating explicit sign-up will be required to complete the provider sign-in attempt.
	 * @param connectionSignUp a {@link ConnectionSignUp} object
	 * @see #findUserIdsWithConnection(Connection)
	 */
	public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
		this.connectionSignUp = connectionSignUp;
	}
	
	/**
	 * Sets a table name prefix. This will be prefixed to all the table names before queries are executed. Defaults to "".
	 * This is can be used to qualify the table name with a schema or to distinguish Spring Social tables from other application tables. 
	 * @param tablePrefix the tablePrefix to set
	 */
	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}
	
	@Override
	public List<String> findUserIdsWithConnection(Connection<?> connection) {
		ConnectionKey key = connection.getKey();
		String tableName = tablePrefix + "user_connection";
				
		Statement selectStatement = QueryBuilder.select("username").from(tableName)
				.where(QueryBuilder.eq("providerId", key.getProviderId()))
				.and(QueryBuilder.eq("providerUserId", key.getProviderUserId()));
		
		List<String> localUserIds = cassandraTemplate.select(selectStatement, String.class);
		System.out.println("*** localUserIds: " + localUserIds);
		
		if (localUserIds.size() == 0 && connectionSignUp != null) {
			String newUserId = connectionSignUp.execute(connection);
			if (newUserId != null) {
				createConnectionRepository(newUserId).addConnection(connection);
				return Arrays.asList(newUserId);
			}
		}
		return localUserIds;
	}
	
	@Override
	public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
		
		String tableName = tablePrefix + "user_connection";
		
		Statement selectStatement = QueryBuilder.select("username").from(tableName)
				.where(QueryBuilder.eq("providerId", providerId))
				.and(QueryBuilder.in("providerUserId", providerUserIds));
		
		List<String> localUserIds = cassandraTemplate.select(selectStatement, String.class);
		System.out.println("*** localUserIdsTo: " + localUserIds);
		Set<String> userIdsConnectedTo = new HashSet<String>(localUserIds);
		System.out.println("*** userIdsConnectedTo: " + userIdsConnectedTo);

		return userIdsConnectedTo;
	}
	
	@Override
	public ConnectionRepository createConnectionRepository(String userId) {
		if (userId == null) {
			throw new IllegalArgumentException("userId cannot be null");
		}
		return new CassandraConnectionRepository(userId, cassandraTemplate, connectionFactoryLocator, textEncryptor, tablePrefix);
	}
	
	
	
	//============
	
	private xyz.cafeconleche.web.chica.entity.User getUser(String username) {

		Statement selectStatement = QueryBuilder.select().from("user_roles")
				.where(QueryBuilder.eq("username", username))
				.limit(1);
		
//		String statement = "select * from user_roles where username='topi'";
		xyz.cafeconleche.web.chica.entity.User user = cassandraTemplate.selectOne(selectStatement, xyz.cafeconleche.web.chica.entity.User.class);
		
		System.out.println("user: " + user);
		
		return user;
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(String username) {
		
		Statement selectStatement = QueryBuilder.select("role").from("user_roles")
				.where(QueryBuilder.eq("username", username));
		
		List<String> roles = cassandraTemplate.select(selectStatement, String.class);
		System.out.println("*** roles: " + roles);
		
		if (roles != null && !roles.isEmpty()) {
			
			Set<GrantedAuthority> grantedAuthorities = new HashSet<>(); 
			roles.forEach(r -> grantedAuthorities.add(new SimpleGrantedAuthority(r)));
			return new ArrayList<GrantedAuthority>(grantedAuthorities);
		}
		
		return null;
	}

	

	

	

}

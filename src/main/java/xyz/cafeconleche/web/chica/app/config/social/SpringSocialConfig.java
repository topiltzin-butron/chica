package xyz.cafeconleche.web.chica.app.config.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.GenericConnectionStatusView;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.stereotype.Component;

import xyz.cafeconleche.web.chica.dao.impl.CassandraConnectionRepository;
import xyz.cafeconleche.web.chica.dao.impl.CassandraUsersConnectionRepository;
import xyz.cafeconleche.web.chica.social.AnonymousUserIdSource;

//@Configuration
//@EnableSocial
//extends SocialConfigurerAdapter { 
	
//@Configuration
//@EnableSocial
//@PropertySource(value = "classpath:application.properties")
public class SpringSocialConfig implements SocialConfigurer {

	@Autowired
	private CassandraAdminOperations cassandraTemplate;
	
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
			Environment environment) {

		System.out.println("========================");
		System.out.println("appid: " + environment.getProperty("spring.social.facebook.appId"));
		System.out.println("appsecret: " + environment.getProperty("spring.social.facebook.appSecret"));
		System.out.println("========================");
		
		connectionFactoryConfigurer.addConnectionFactory(new FacebookConnectionFactory(
				environment.getProperty("spring.social.facebook.appId"),
				environment.getProperty("spring.social.facebook.appSecret")
        ));
		
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		return new CassandraUsersConnectionRepository(cassandraTemplate, connectionFactoryLocator, Encryptors.noOpText());
	}

	@Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }
	
	@Bean
	public TextEncryptor textEncryptor() {
		TextEncryptor textEncryptor = Encryptors.noOpText();
		return textEncryptor;
	}
	
//	=======
	
	
	
	
/*
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
			Environment environment) {

		System.out.println("--- addConnectionFactories ---");
		System.out.println("ConnectionFactoryConfigurer: " + connectionFactoryConfigurer);
		System.out.println("appId: " + environment.getProperty("spring.social.facebook.appId"));
		System.out.println("appSecret: " + environment.getProperty("spring.social.facebook.appSecret"));
		
//		connectionFactoryConfigurer.addConnectionFactory(
//				new FacebookConnectionFactory(
//						environment.getProperty("spring.social.facebook.appId"), 
//						environment.getProperty("spring.social.facebook.appSecret")));
		
	}
	
//	------------
	
	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Facebook facebook(ConnectionRepository repository) {
		Connection<Facebook> connection = repository
				.findPrimaryConnection(Facebook.class);
		return connection != null ? connection.getApi() : null;
	}

//	@Bean(name = { "connect/facebookConnect", "connect/facebookConnected" })
//	public GenericConnectionStatusView facebookConnectView() {
//		return new GenericConnectionStatusView("facebook", "Facebook");
//	}
	
//	-----------
	
//	@Override
//	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
//		System.out.println("--- getUsersConnectionRepository ---");
//		InMemoryUsersConnectionRepository repository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
//		return repository;
//	}
	
	@Override
	public UserIdSource getUserIdSource() {
		System.out.println("--- getUserIdSource ---");
		return new AnonymousUserIdSource();
	}

	@Bean
	public ConnectController connectController(ConnectionFactoryLocator factoryLocator, ConnectionRepository repository) {
		System.out.println("--- connectController ---");
		System.out.println("ConnectionFactoryLocator: " + factoryLocator);
		System.out.println("ConnectionRepository" + repository);
		ConnectController controller = new ConnectController(factoryLocator, repository);
		return controller;
	}
	
*/	

//	@Bean
//	@Scope (value = "request", proxyMode = ScopedProxyMode.INTERFACES)
//	@RequestScope(proxyMode = ScopedProxyMode.INTERFACES)
//	public Facebook facebook(ConnectionRepository repository) {
//		System.out.println("--- facebook --- repository: " + repository);
//		Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
//		return connection != null ? connection.getApi() : null;
//	}
	
	
	
//	@Inject
//	private Environment environment;
//
//	@Bean
//	public ConnectionFactoryLocator connectionFactoryLocator() {
//
//		String appId = environment.getProperty("spring.social.facebook.appId");
//		String appSecret = environment.getProperty("spring.social.facebook.appSecret");
//
//		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
//		registry.addConnectionFactory(new FacebookConnectionFactory(appId, appSecret));
//
//		// TODO add more social
//
//		return registry;
//	}

	/*
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
			Environment environment) {

		System.out.println("addConnectionFactories");
		
		String appId = environment.getProperty("spring.social.facebook.appId");
		String appSecret = environment.getProperty("spring.social.facebook.appSecret");
//		connectionFactoryConfigurer.addConnectionFactory(new FacebookConnectionFactory(appId, appSecret));
		
		System.out.println("facebook appId: " + appId + " appSecret: " + appSecret);
		connectionFactoryConfigurer.addConnectionFactory(new FacebookConnectionFactory(environment.getProperty("spring.social.facebook.appId"), environment.getProperty("spring.social.facebook.appSecret")));
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new UserIdSource() {

			@Override
			public String getUserId() {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication == null) {
					throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
				}
				return authentication.getName();
			}
		};
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {

		// TODO change to make it persistent
		UsersConnectionRepository usersConnectionRepository =
			    new InMemoryUsersConnectionRepository(connectionFactoryLocator);
		
		return usersConnectionRepository;
	}
	*/
	
	
	
//	@Bean
//	public ConnectionRepository repository() {
//		ConnectionFactoryLocator connectionFactoryLocator = connectionFactoryLocator();
//		UsersConnectionRepository usersConnectionRepository =
//			    new InMemoryUsersConnectionRepository(connectionFactoryLocator);
//		
//		return usersConnectionRepository.;
//	}
	
//	@Bean
//	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)   
//	public Facebook facebook() {
//		
//		// create a connection repository for the single-user 'jbauer'
//		ConnectionRepository repository = getUsersConnectionRepository.createConnectionRepository("jbauer");
//		
//	    return connectionRepository().getPrimaryConnection(Facebook.class).getApi();
//	}
	
//	@Bean
//	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
//	public Facebook facebook(ConnectionRepository repository) {
//		
//		System.out.println("=== facebook bean ===");
//		System.out.println("repository: " + repository);
//		MultiValueMap<String, Connection<?>> allConnections = repository.findAllConnections();
//		System.out.println("allConnections: " + allConnections.keySet());
//		Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
//		return connection != null ? connection.getApi() : null;
//	}
	
	//by: java.lang.IllegalArgumentException: One configuration class must implement getUserIdSource from SocialConfigurer.
	
//	@Override
//	public UserIdSource getUserIdSource() {
//		return new AuthenticationNameUserIdSource();
//	}
	
	

}

package xyz.cafeconleche.web.chica.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import xyz.cafeconleche.web.chica.dao.SecurityDao;

@Repository("securityDao")
public class SecurityDaoCassandraImpl implements SecurityDao {

	@Autowired
	private CassandraAdminOperations cassandraTemplate;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("--- loadUserByUsername ---");
		
		// todo validate
		xyz.cafeconleche.web.chica.entity.User userResult = getUser(username);
		
		if (userResult == null) {
			throw new UsernameNotFoundException("user: " + username + " not found");
		}

		List<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(username);
		System.out.println("grantedAuthorities (" + username + "): " + grantedAuthorities);
		System.out.println("cassandraTemplate: " + cassandraTemplate);
		
//		Set<GrantedAuthority> setAuths =  new HashSet<>();
//		setAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//		setAuths.add(new SimpleGrantedAuthority("ROLE_DBA"));
//		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(setAuths);

		UserDetails user = populateUser(userResult, grantedAuthorities);
        return user;
	}
	
	@Override
	public SocialUserDetails loadSocialUserByUsername(String username) {
		
		System.out.println("--- loadSocialUserByUsername ---");
		// todo validate
		xyz.cafeconleche.web.chica.entity.User userResult = getUser(username);
				
		if (userResult == null) {
			throw new UsernameNotFoundException("user: " + username + " not found");
		}

		List<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(username);
		System.out.println("grantedAuthorities (" + username + "): " + grantedAuthorities);
		System.out.println("cassandraTemplate: " + cassandraTemplate);
		
		SocialUserDetails socialUser = populateSocialUser(userResult, grantedAuthorities);
        return socialUser;
	}
	
	private UserDetails populateUser(xyz.cafeconleche.web.chica.entity.User userResult,
			List<GrantedAuthority> authorities) {
		
		boolean enabled = true;
		boolean accountNotExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		UserDetails user = new User(userResult.getUsername(), userResult.getPassword(), enabled, accountNotExpired,
				credentialsNonExpired, accountNonLocked, authorities);
		return user;
	}
	
	private SocialUserDetails populateSocialUser(xyz.cafeconleche.web.chica.entity.User userResult,
			List<GrantedAuthority> grantedAuthorities) {
		
		boolean enabled = true;
		boolean accountNotExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		SocialUser socialUser = new SocialUser(userResult.getUsername(), userResult.getPassword(), enabled, accountNotExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);
		
		return socialUser;
	}

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

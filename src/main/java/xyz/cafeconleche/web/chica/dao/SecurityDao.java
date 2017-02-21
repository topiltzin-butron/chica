package xyz.cafeconleche.web.chica.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.security.SocialUserDetails;

public interface SecurityDao {

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	SocialUserDetails loadSocialUserByUsername(String userId);

}

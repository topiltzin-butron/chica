package xyz.cafeconleche.web.chica.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.stereotype.Service;

import xyz.cafeconleche.web.chica.dao.SecurityDao;
import xyz.cafeconleche.web.chica.service.SocialSecurityService;

@Service("socialSecurityService")
public class SocialSecurityServicePojoImpl implements SocialSecurityService {

	@Autowired
	private SecurityDao securityDao;

	@Override
	public SocialUserDetails loadUserByUserId(String username) throws UsernameNotFoundException {
		return securityDao.loadSocialUserByUsername(username);
	}

}

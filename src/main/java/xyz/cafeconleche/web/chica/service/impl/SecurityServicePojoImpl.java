package xyz.cafeconleche.web.chica.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import xyz.cafeconleche.web.chica.dao.SecurityDao;
import xyz.cafeconleche.web.chica.service.SecurityService;

@Service("securityService")
public class SecurityServicePojoImpl implements SecurityService {

	@Autowired
	private SecurityDao securityDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return securityDao.loadUserByUsername(username);
	}

}

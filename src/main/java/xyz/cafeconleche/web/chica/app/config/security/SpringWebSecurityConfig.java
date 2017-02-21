package xyz.cafeconleche.web.chica.app.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import xyz.cafeconleche.web.chica.service.SecurityService;

//@Configuration
//@EnableWebSecurity
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	// @Qualifier("userDetailsService")
	private SecurityService securityService;
	
	@Autowired
	private void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		System.out.println("--- configureGlobal ---");
//		authenticationManagerBuilder.inMemoryAuthentication().withUser("vaca").password("qwerty").roles("USER");
//		authenticationManagerBuilder.inMemoryAuthentication().withUser("maria").password("qwerty").roles("ADMIN");
//		authenticationManagerBuilder.inMemoryAuthentication().withUser("jorge").password("qwerty").roles("DBA");

//		authenticationManagerBuilder.jdbcAuthentication().dataSource(dataSource)
//			.passwordEncoder(passwordEncoder())
//			.usersByUsernameQuery("SELECT C.USER_NAME as principal, C.PASSWORD as credentials, true FROM USER_ACCOUNT U LEFT JOIN CREDENTIALS C ON U.USER_NAME = C.USER_NAME WHERE U.USER_NAME=?")
//			.authoritiesByUsernameQuery("SELECT U.USER_NAME as principal, UR.ROLE_ID as role FROM USER_ACCOUNT U LEFT JOIN USER_ROLES UR ON U.USER_UUID = UR.USER_UUID WHERE U.USER_NAME=?");

		authenticationManagerBuilder.userDetailsService(securityService).passwordEncoder(passwordEncoder());
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("--- configure ---");
		http.authorizeRequests()
			.antMatchers("/vaca/admin/**").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/vaca/dba/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')")
			.and().formLogin().loginPage("/login").failureUrl("/login?error")
				.usernameParameter("principal").passwordParameter("credentials")
			.and().logout().logoutSuccessUrl("/login?logout")
			.and().exceptionHandling().accessDeniedPage("/403")
			.and().csrf();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	*/

}

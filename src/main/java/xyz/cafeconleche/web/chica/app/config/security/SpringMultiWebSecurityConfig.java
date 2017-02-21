package xyz.cafeconleche.web.chica.app.config.security;

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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import xyz.cafeconleche.web.chica.service.SecurityService;

@Configuration
@EnableWebSecurity
public class SpringMultiWebSecurityConfig {
	
	@Autowired
	// @Qualifier("userDetailsService")
	private SecurityService securityService;
	
//	Autowired
//	private SocialSecurityService socialSecurityService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(securityService).passwordEncoder(passwordEncoder());
	}

	@Configuration
    @Order(1)
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		
		@Autowired // restAuthenticationEntryPoint
		private AuthenticationEntryPoint authenticationEntryPoint;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			System.out.println("--- configure api ---");
			
			http.authorizeRequests()
				.antMatchers("/rest/greetings").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')")
				.antMatchers("/rest/greetings/details/**").access("hasRole('ROLE_ADMIN')")
				.and().httpBasic();
			
//			http //.antMatcher("/rest**") 
//				.csrf().disable()
//				.exceptionHandling()
//				.authenticationEntryPoint(authenticationEntryPoint)
//				.and()
//				.authorizeRequests()
//				.antMatchers("/rest/greetings").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')")
//				.antMatchers("/rest/greetings/**").access("hasRole('ROLE_ADMIN')")
//				.and()
//				.formLogin()
//				.successHandler(successHandler())
//				.failureHandler(failureHandler())
//				.and()
//				.logout();
			
//			http
//			.antMatcher("/rest**")                               
//			.authorizeRequests()
//            .anyRequest().access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')")
//            .and()
//        .httpBasic();

			
		}
		
		@Bean
		public AuthenticationSuccessHandler successHandler() {
			return new SavedRequestAwareAuthenticationSuccessHandler();
		}
		
		@Bean
		public AuthenticationFailureHandler failureHandler() {
			return new SimpleUrlAuthenticationFailureHandler();
		}
		
	}
	
	@Configuration
    @Order(2)
	public static class FormLoginSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {

			System.out.println("--- configure form ---");
			
			http.authorizeRequests()
			.antMatchers("/vaca/admin").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/vaca/dba").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')")
			.and().httpBasic();
			
//			http.antMatcher("/vaca/**") 
//				.csrf()
//				.and().exceptionHandling().accessDeniedPage("/403")
//				.and().authorizeRequests()
//				.antMatchers("/vaca/admin").access("hasRole('ROLE_ADMIN')")
//				.antMatchers("/vaca/dba").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')")
//				.and().formLogin().loginPage("/login").failureUrl("/login?error")
//					.usernameParameter("principal").passwordParameter("credentials")
//				.and().logout().logoutSuccessUrl("/login?logout");
			
//			http.authorizeRequests()
//				.antMatchers("/vaca/admin").access("hasRole('ROLE_ADMIN')")
//				.antMatchers("/vaca/dba").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')")
//				.and().formLogin().loginPage("/login").failureUrl("/login?error")
//					.usernameParameter("principal").passwordParameter("credentials")
//				.and().logout().logoutSuccessUrl("/login?logout")
//				.and().exceptionHandling().accessDeniedPage("/403")
//				.and().csrf();
//				.and().apply(new SpringSocialConfigurer());
			
		}
		
	}
	
//	@Bean
//	public AuthenticationManager authenticationManager() throws Exception {
//		
//		AuthenticationManagerBuilder authenticationManagerBuilder = context
//				.getBean(AuthenticationManagerBuilder.class);
//	
//		return authenticationManagerBuilder.build();
//	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}

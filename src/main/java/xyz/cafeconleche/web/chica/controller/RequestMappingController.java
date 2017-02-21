package xyz.cafeconleche.web.chica.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity.RequestMatcherConfigurer;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
@RequestMapping("/app")
public class RequestMappingController {

	@Autowired
	private RequestMappingHandlerMapping handlerMapping;
	
//	@Autowired
//	@Qualifier("xyz.cafeconleche.web.chica.app.config.security.SpringWebSecurityConfig")
//	SpringWebSecurityConfig webSecurityConfigurerAdapter;
	
	@Autowired
	private Filter springSecurityFilterChain; 
	
	//HttpSecurity http
//	springMultiWebSecurityConfig.ApiWebSecurityConfigurationAdapter,
//	springMultiWebSecurityConfig.FormLoginSecurityConfigurationAdapter,
	
	@RequestMapping(value="/requestMapping")
	public ModelAndView requestMapping() {
		
		System.out.println("requestMapping list");
		
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();

		System.out.println("springSecurityFilterChain: " + springSecurityFilterChain);
		System.out.println(springSecurityFilterChain.getClass());
		
		FilterChainProxy filterChainProxy = (FilterChainProxy) springSecurityFilterChain;
		
		System.out.println("filterConfig: " + filterChainProxy.getFilterConfig());
		
		
		for (SecurityFilterChain sfc : filterChainProxy.getFilterChains()) {
			
			//AnyRequestMatcher
			
			System.out.println("\t sfc.class: " + sfc.getClass());
			System.out.println("\t sfc: " + sfc);
			
			DefaultSecurityFilterChain dsfc = (DefaultSecurityFilterChain) sfc;
			
			RequestMatcher requestMatcher = dsfc.getRequestMatcher();
			System.out.println("\t requestMatcher: " + requestMatcher);
			System.out.println("\t requestMatcher.class: " + requestMatcher.getClass());
			
			
			List<Filter> filters = sfc.getFilters();
			System.out.println("\t +++++++++++++++++++++++++++++++++++");
			for (Filter filter : filters) {
//				System.out.println("filter: " + filter);
				
				if(filter instanceof FilterSecurityInterceptor) {
					FilterSecurityInterceptor fsi = (FilterSecurityInterceptor) filter;
					System.out.println("\t\t ========");
					
					FilterInvocationSecurityMetadataSource sms = fsi.getSecurityMetadataSource();
					
					Class<?> secureObjects = fsi.getSecureObjectClass();
					System.out.println("\t\t secureObjects: " + secureObjects);
					
					SecurityMetadataSource smds = fsi.obtainSecurityMetadataSource();
					System.out.println("\t\t smds: " + smds);
					
					
					Collection<ConfigAttribute> configAttributes = sms.getAllConfigAttributes();
					
					System.out.println("\t\t MetadataSource: " + sms);
					System.out.println("\t\t configAttributes: " + configAttributes);
					for (ConfigAttribute configAttribute : configAttributes) {
						System.out.println("\t\t\t configAttribute.getAttribute(): " + configAttribute.getAttribute());
						System.out.println("\t\t\t configAttribute.getAttribute().class: " + configAttribute.getAttribute().getClass());
					}
				}
				
				if(filter instanceof WebAsyncManagerIntegrationFilter) {
					WebAsyncManagerIntegrationFilter wamif = (WebAsyncManagerIntegrationFilter) filter;
					System.out.println("\t\t WebAsyncManagerIntegrationFilter: " + wamif.getFilterConfig());
				}
				
				if(filter instanceof SecurityContextPersistenceFilter) {
					SecurityContextPersistenceFilter scpf = (SecurityContextPersistenceFilter) filter;
					System.out.println("\t\t SecurityContextPersistenceFilter: " + scpf.getFilterConfig());
				}
				
				if(filter instanceof UsernamePasswordAuthenticationFilter) {
					UsernamePasswordAuthenticationFilter upaf = (UsernamePasswordAuthenticationFilter) filter;
					System.out.println("\t\t UsernamePasswordAuthenticationFilter: " + upaf.getFilterConfig());
				}
				
				if(filter instanceof BasicAuthenticationFilter) {
					BasicAuthenticationFilter baf = (BasicAuthenticationFilter) filter;
					System.out.println("\t\t BasicAuthenticationFilter: " + baf.getFilterConfig());
				}
				
				if(filter instanceof BasicAuthenticationFilter) {
					BasicAuthenticationFilter baf = (BasicAuthenticationFilter) filter;
					System.out.println("\t\t BasicAuthenticationFilter: " + baf.getFilterConfig());
				}
				
				if(filter instanceof RequestCacheAwareFilter) {
					RequestCacheAwareFilter rcaf = (RequestCacheAwareFilter) filter;
					System.out.println("\t\t RequestCacheAwareFilter: " + rcaf.getFilterConfig());
				}
				
				if(filter instanceof SecurityContextHolderAwareRequestFilter) {
					SecurityContextHolderAwareRequestFilter scharf = (SecurityContextHolderAwareRequestFilter) filter;
					System.out.println("\t\t SecurityContextHolderAwareRequestFilter: " + scharf.getFilterConfig());
				}
				if(filter instanceof SecurityContextHolderAwareRequestFilter) {
					SecurityContextHolderAwareRequestFilter scharf = (SecurityContextHolderAwareRequestFilter) filter;
					System.out.println("\t\t SecurityContextHolderAwareRequestFilter: " + scharf.getFilterConfig());
				}
				
			}
			System.out.println("\t +++++++++++++++++++++++++++++++++++");
		}
		
		
		
		ModelAndView modelAndView = new ModelAndView("requestMapping.show");
		modelAndView.addObject("handlerMethods", handlerMethods);
		return modelAndView;
	}
	
}

package xyz.cafeconleche.web.chica.app.servlet3;


import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import xyz.cafeconleche.web.chica.app.config.SpringRootConfig;
import xyz.cafeconleche.web.chica.app.config.SpringWebConfig;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SpringRootConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { SpringWebConfig.class };
//		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
	}

//	@Override
//	protected void registerContextLoaderListener(ServletContext servletContext) {
////		servletContext.addListener(new RequestContextListener());
//	}
	
//	@Override
//	protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter) {
//		FilterRegistration.Dynamic securityFilter = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
//		securityFilter.addMappingForUrlPatterns(null, false, "/*");
//		return securityFilter;
//	}
	
//	@Override
//	protected Filter[] getServletFilters() {
//		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
//		encodingFilter.setEncoding("UTF-8");
//		encodingFilter.setForceEncoding(true);
//		
//		DelegatingFilterProxy reconnectDelegate = new DelegatingFilterProxy("apiExceptionHandler");
//		
//		return new Filter[] { reconnectDelegate, encodingFilter, new HiddenHttpMethodFilter() };
//	}
	
}

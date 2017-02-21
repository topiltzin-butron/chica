package xyz.cafeconleche.web.chica.app.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.view.LiteDeviceDelegatingViewResolver;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.cafeconleche.web.chica.view.PdfViewResolver;

@EnableWebMvc
@Configuration
@ComponentScan({ "xyz.cafeconleche.web.chica.controller" })
//@Import({ SpringWebSecurityConfig.class, SpringCassandraConfig.class })
@Import({ SpringCassandraConfig.class })
public class SpringWebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
//	// TODO add resources
//		@Override
//	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//	        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
//	    }
	
	
	@Bean
	public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
		return new DeviceResolverHandlerInterceptor();
	}
	
	@Bean
	public SitePreferenceHandlerInterceptor sitePreferenceHandlerInterceptor() {
		return new SitePreferenceHandlerInterceptor();
	}
	
	@Bean
	public SitePreferenceHandlerMethodArgumentResolver sitePreferenceHandlerMethodArgumentResolver(){
		return new SitePreferenceHandlerMethodArgumentResolver();
	}
	
	@Bean
	public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver() { 
		return new DeviceHandlerMethodArgumentResolver();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(deviceResolverHandlerInterceptor());
		registry.addInterceptor(sitePreferenceHandlerInterceptor());
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(deviceHandlerMethodArgumentResolver());
		argumentResolvers.add(sitePreferenceHandlerMethodArgumentResolver());
	}

//	@Bean
//	public InternalResourceViewResolver viewResolver() {
//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		viewResolver.setViewClass(JstlView.class);
//		viewResolver.setPrefix("/WEB-INF/views/jsp/");
//		viewResolver.setSuffix(".jsp");
//		return viewResolver;
//	}
	
	// multiple view resolver
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//		configurer.ignoreAcceptHeader(true).defaultContentType(MediaType.TEXT_HTML);
		configurer.ignoreAcceptHeader(true).defaultContentType(MediaType.APPLICATION_JSON);
		
	}
	
	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);
		
		// define possible resolvers
		List<ViewResolver> resolvers = new ArrayList<>();
		resolvers.add(new TilesViewResolver());
		resolvers.add(pdfViewResolver());
		
		resolver.setViewResolvers(resolvers);
		return resolver;
	}
	
	@Bean
	public ViewResolver pdfViewResolver() {
		ViewResolver resolver = new PdfViewResolver();
		return resolver; 
	}
	
	@Bean
	public LiteDeviceDelegatingViewResolver liteDeviceDelegatingViewResolver() {
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		viewResolver.setOrder(1); // TODO check this order
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		
		LiteDeviceDelegatingViewResolver liteViewResolver = new LiteDeviceDelegatingViewResolver(viewResolver);
		liteViewResolver.setNormalPrefix("jsp/");
		liteViewResolver.setMobilePrefix("mobile/");
		liteViewResolver.setTabletPrefix("tablet/");
		
		return liteViewResolver;
	}
	
	// tiles
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(new String[] {"/WEB-INF/views/**/tiles.xml"});
		tilesConfigurer.setCheckRefresh(true);
		return tilesConfigurer;
	}
	
	// TODO maybe remove
//	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		TilesViewResolver viewResolver = new TilesViewResolver();
		registry.viewResolver(viewResolver);
	}
	
	// exception
	@Bean
	public SimpleMappingExceptionResolver createMappingExceptionResolver() {

		Properties exceptionMappings = new Properties();
		exceptionMappings.setProperty("Exception", "exception");
		
		SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
		resolver.setExceptionMappings(exceptionMappings);
		resolver.setDefaultErrorView("exception");
		resolver.setExceptionAttribute("exceptionAttribute");
		
		return resolver;
	}
	
	
	

	// Data source
//	@Resource(name="jdbc/taleme", type=javax.sql.DataSource.class, lookup="jdbc/taleme")
	@Bean(name = "dataSource")
	public DataSource dataSource() {
		
		System.out.println("--- dataSource ---");
		
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		dataSourceLookup.setResourceRef(true);
		DataSource dataSource = dataSourceLookup.getDataSource("jdbc/taleme");
		
		
		System.out.println("ds: " + dataSource);
		
		return dataSource;
		
		
//		DriverManagerDataSource dataSource =  new DriverManagerDataSource();
//		
//		dataSource.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
//		dataSource.setUrl("jdbc:derby://localhost:1527//Users/topiltzin/Tools/db/databases/taleme");
//		dataSource.setUsername("taleme");
//		dataSource.setPassword("T4l3me");
//		return dataSource;
	}
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJackson2HttpMessageConverter());
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false));
		return converter;
	}
	
	
	
//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		converters.add(new MappingJackson2HttpMessageConverter());
//		super.configureMessageConverters(converters);
//	}
	
}

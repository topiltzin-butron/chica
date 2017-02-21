package xyz.cafeconleche.web.chica.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"xyz.cafeconleche.web.chica.*", "xyz.cafeconleche.web.chica.service.*", "xyz.cafeconleche.web.chica.dao.*", "xyz.cafeconleche.web.chica.base.exception.handler"})
public class SpringRootConfig {

}

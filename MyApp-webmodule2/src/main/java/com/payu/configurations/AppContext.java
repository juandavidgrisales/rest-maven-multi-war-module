package com.payu.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

@Configuration
@ComponentScan("com.payu")
@ContextConfiguration({"classpath:META-INF/persistence.xml"})
public class AppContext {
	
}

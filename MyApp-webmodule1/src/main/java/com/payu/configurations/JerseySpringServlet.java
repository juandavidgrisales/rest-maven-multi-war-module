package com.payu.configurations;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;

@WebServlet(
        name = "jersey-servlet",
        loadOnStartup = 1,
        urlPatterns = "/rest/*",
        initParams = {
                @WebInitParam(name = "com.sun.jersey.api.json.POJOMappingFeature", value = "true"),
                @WebInitParam(name = "com.sun.jersey.config.property.packages", value = "com.payu.webservices")
        }
)
public class JerseySpringServlet extends SpringServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

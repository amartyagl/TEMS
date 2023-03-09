package com.globallogic.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayConfig {

	@Autowired
	AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes().route("authentication-service", r -> r.path("/authenticate/**").filters(f -> f.filter(filter)).uri("lb://authentication-service"))
                .route("xlsdatabase", r -> r.path("/participant/**","/employee/**","/meeting/**","/sme/**","/meetingemployee/**").filters(f -> f.filter(filter)).uri("lb://xlsdatabase")).build();
    }
	

}

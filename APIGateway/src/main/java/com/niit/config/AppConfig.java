/*
 * Author : Mushib Khan
 * Date : 30-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    /**
     * This method is for define a set of routes that map incoming request to specific target URLs.
     *
     * @param routeLocatorBuilder - By passing RouteLocatorBuilder you can define routes that match specific patterns in the incoming request,and route request to backend services.
     * @return
     */
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p.path("/userAuth/**")
                        .uri("http://localhost:8083"))
                .route(p -> p.path("/customer/**")
                        .uri("http://localhost:8081"))
                .route(p -> p.path("/restaurant/**")
                        .uri("http://localhost:8084"))
                .build();
    }
}

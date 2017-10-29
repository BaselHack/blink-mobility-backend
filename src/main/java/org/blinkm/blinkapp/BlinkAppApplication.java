package org.blinkm.blinkapp;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import org.springframework.web.util.pattern.PathPatternParser;

@SpringBootApplication
public class BlinkAppApplication {


	private final RideRepository rideRepository;

	public BlinkAppApplication(RideRepository rideRepository) {
		this.rideRepository = rideRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BlinkAppApplication.class, args);
	}
	

	///
	/// Router
	///
	@Bean
	protected RouterFunction<ServerResponse> routes() {

		RideHandler handler = new RideHandler(this.rideRepository);

		return route(
				GET("/rides").and(accept(APPLICATION_JSON)), handler::listRides).andRoute(
				GET("/rides/{rideId}").and(accept(APPLICATION_JSON)), handler::listRide).andRoute(
				POST("/rides").and(accept(APPLICATION_JSON)), handler::createRide).andRoute(
				PUT("/rides/{rideId}").and(accept(APPLICATION_JSON)), handler::modifyRide);
	}


	@Bean
	public CorsWebFilter corsWebFilter(CorsProperties corsProperties) {
		UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource(new PathPatternParser());
		corsConfigurationSource.registerCorsConfiguration(corsProperties.getMapping(), corsProperties.translate());
		return new CorsWebFilter(corsConfigurationSource);
	}


	@Bean
	public HttpHandler httpHandler(RouterFunction routers, CorsWebFilter corsWebFilter) {
		return WebHttpHandlerBuilder
				.webHandler(RouterFunctions.toWebHandler(routers))
				.filter(corsWebFilter)
				.build();
	}
	
}

package com.globallogic.apigateway.config;

import java.util.List; 
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;

import org.springframework.stereotype.Component;

@Component
public class RouterValidator {

	public static final List<String> openApiEndPoints = List.of("/authenticate/signIn", "/authenticate/signUp", "/authenticate/welcome");
	public Predicate<ServerHttpRequest> isSecured = request -> openApiEndPoints.stream()
			.noneMatch(uri -> request.getURI().getPath().contains(uri));


}

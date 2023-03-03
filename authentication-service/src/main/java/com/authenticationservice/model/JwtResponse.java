package com.authenticationservice.model;

import java.io.Serializable;

public class JwtResponse implements Serializable{

	private static final long serialVersionUID = -8427921511654130082L;

	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}

}

package com.globallogic.authenticationservice.controller;

import com.globallogic.authenticationservice.model.JwtRequest;
import com.globallogic.authenticationservice.service.JwtUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping(value = "authenticate")
public class AuthenticationController {
	@Autowired
	private JwtUserDetailsService userDetailsService;
	@PostMapping(value = "/signIn")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		log.info(jwtRequest.toString());
		return ResponseEntity.ok(userDetailsService.authenticateUser(jwtRequest.getEmail(), jwtRequest.getPassword()));
	}
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String getUser() {
		return "Welcome to Auth controller";
	}
}








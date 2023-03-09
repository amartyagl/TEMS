package com.authenticationservice.controller;

import com.authenticationservice.model.JwtRequest;
import com.authenticationservice.dto.UserDto;
import com.authenticationservice.service.JwtUserDetailsService;
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
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String getUser() {
		return "Welcome to Auth controller";
	}
}








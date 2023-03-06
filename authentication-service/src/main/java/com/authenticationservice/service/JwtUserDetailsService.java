package com.authenticationservice.service;

import java.util.Date;

import com.authenticationservice.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.authenticationservice.model.User;
import com.authenticationservice.dto.UserDto;
import com.authenticationservice.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUserDetailsService {


	private static final int JWT_TOKEN_VALIDITY = 1000*60*60*2;

	private String secret = "dadsnrgsthghdjgmdhgxfxmgfdyjmhgtxgdcjbngkjghchhfjdlshvfishdvuhzsuhvujksdbvkub";

	@Autowired
	private UserRepository userDao;
	@KafkaListener(topics = "gmailTopic",groupId = "group2")
	public User  consume(EmployeeDto employeeDto)
	{
		User newUser = new User();
		newUser.setEmail(employeeDto.getEmail());
		newUser.setPassword(employeeDto.getPassword());
		newUser.setRoles(employeeDto.getRoles());
		return userDao.save(newUser);

	}


//	public User save(UserDto user) {
//		User newUser = new User();
//		newUser.setEmail(user.getEmail());
//		newUser.setPassword(user.getPassword());
//		return userDao.save(newUser);
//
//	}

	public String authenticateUser(String email, String password) {
		User user1 = userDao.findById(email).get();
		String jwttoken = "";
		if (email.equals(user1.getEmail()) && password.equals(user1.getPassword())) {
			Claims claims = Jwts.claims();
			claims.put("emailId", user1.getEmail());
			jwttoken = Jwts.builder().setClaims(claims).setSubject(user1.getEmail()).setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000*60*60*2))
					.signWith(SignatureAlgorithm.HS256, secret.getBytes()).compact();
		}
		return jwttoken;
	}

}

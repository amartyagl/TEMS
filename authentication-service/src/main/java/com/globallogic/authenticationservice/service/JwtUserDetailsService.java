package com.globallogic.authenticationservice.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.authenticationservice.dto.EmployeeDto;
import com.globallogic.authenticationservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.globallogic.authenticationservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Slf4j
public class JwtUserDetailsService {


	private static final int JWT_TOKEN_VALIDITY = 1000*60*60*2;

	private String secret = "dadsnrgsthghdjgmdhgxfxmgfdyjmhgtxgdcjbngkjghchhfjdlshvfishdvuhzsuhvujksdbvkub";

	@Autowired
	private UserRepository userDao;
	@KafkaListener(topics = "gmailTopic",groupId = "group2")
	public User consume(String key)  {
		log.info("In kafka listener");
		ObjectMapper mapper=new ObjectMapper();
		EmployeeDto employeeDto=null;
		try {
			employeeDto=mapper.readValue(key, EmployeeDto.class);
		}
		catch (IOException exception)
		{
			System.out.println(exception.getMessage());
		}

		log.info("Adding user from consuming through kafka");
		User newUser = new User();
		newUser.setEmail(employeeDto.getEmail());
		newUser.setPassword(employeeDto.getPassword());
		newUser.setRoles( employeeDto.getRoles());
		return userDao.save(newUser);
	}


	public String authenticateUser(String email, String password) {
		User user1 = userDao.findById(email).get();
		String jwttoken = "";

		if (email.equals(user1.getEmail()) && password.equals(user1.getPassword())) {
			Claims claims = Jwts.claims();
			claims.put("emailId", user1.getEmail());
			jwttoken = Jwts.builder()
					.setClaims(claims)
					.setSubject(user1.getEmail())
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + 1800000))
					.signWith(SignatureAlgorithm.HS256, secret.getBytes())
					.compact();
		}
		return jwttoken;
	}

}

package com.authenticationservice.dto;

import lombok.AllArgsConstructor; 
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private String email;
	private String password;
}

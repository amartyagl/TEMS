package com.authenticationservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EmployeeDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long eid;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private List<String> roles;
	private String projectCode;
	private String location;

}

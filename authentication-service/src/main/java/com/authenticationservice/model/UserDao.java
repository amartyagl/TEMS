package com.authenticationservice.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDao {

	@Id
	@Column(name = "emailId", unique = true, nullable = false, length = 100)
	private String email;
	@Column(name = "password", nullable = false)
	@JsonIgnore
	private String password;

}

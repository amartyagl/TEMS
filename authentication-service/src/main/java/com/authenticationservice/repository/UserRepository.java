package com.authenticationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authenticationservice.model.UserDao;

@Repository
public interface UserRepository extends JpaRepository<UserDao, String> {

	

}

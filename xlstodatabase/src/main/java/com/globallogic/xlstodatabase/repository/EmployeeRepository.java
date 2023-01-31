package com.globallogic.xlstodatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globallogic.xlstodatabase.modal.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query(value="select * from employee where email=?1",nativeQuery=true)
	Employee findByEmail(String email);

}

package com.globallogic.xlstodatabase.repository;

import com.globallogic.xlstodatabase.modal.RegisteredEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisteredEmployeeRepo extends JpaRepository<RegisteredEmployee,Integer> {
    @Query(value = "select * from registered_employee where eid=?1",nativeQuery = true)
    List<RegisteredEmployee> findByEid(long eid);
}

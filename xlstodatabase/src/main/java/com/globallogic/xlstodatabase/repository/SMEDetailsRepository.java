package com.globallogic.xlstodatabase.repository;

import com.globallogic.xlstodatabase.dto.SMEDetailsDto;
import com.globallogic.xlstodatabase.modal.SMEDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SMEDetailsRepository extends JpaRepository<SMEDetails,Long> {
    @Query(value = "select * from smedetails where eid=?1",nativeQuery = true)
    List<SMEDetails> getAllTopics(long eid);

    @Query(value = "delete from smedetails where eid=?1 and topic=?2",nativeQuery = true)
    @Modifying
    void deleteTopic(long eid,String topic);

    @Query(value = "select * from smedetails where eid=?1 and topic=?2",nativeQuery = true)
    SMEDetails findyByEidAndTopic(long eid, String topic);
}

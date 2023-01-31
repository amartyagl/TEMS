package com.globallogic.xlstodatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globallogic.xlstodatabase.modal.MeetingDetails;

import java.util.List;

@Repository
public interface MeetingDetailsRepository extends JpaRepository<MeetingDetails, String> {

    @Query(value="UPDATE meetingdetails set meetingAnchor=?1 where meetingId=?2",nativeQuery=true)
    MeetingDetails updateSME(String meetingAnchor, String meetingId);

    @Query(value="select * meetingdetails where meetingAnchor=?1",nativeQuery=true)
    List<MeetingDetails> getBySME(String meetingAnchor);

    



}

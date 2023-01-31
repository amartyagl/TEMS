package com.globallogic.xlstodatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globallogic.xlstodatabase.modal.ParticipantOfMeeting;
import com.globallogic.xlstodatabase.modal.ParticipantOfmeetingsId;

import java.util.List;

@Repository
public interface ParticipantsofMeetingRepository extends JpaRepository<ParticipantOfMeeting, ParticipantOfmeetingsId>{

    @Query(value="select * from participantsofmeeting where mid=?1",nativeQuery=true)
    List<ParticipantOfMeeting> findByMid(String mid);

}

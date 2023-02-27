package com.globallogic.xlstodatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globallogic.xlstodatabase.modal.ParticipantOfMeeting;
import com.globallogic.xlstodatabase.modal.ParticipantOfmeetingsId;

import java.util.Date;
import java.util.List;

@Repository
public interface ParticipantsofMeetingRepository extends JpaRepository<ParticipantOfMeeting, ParticipantOfmeetingsId> {

    @Query(value = "select * from participantsofmeeting where mid=?1", nativeQuery = true)
    List<ParticipantOfMeeting> findByMid(String mid);

    @Query(value = "select * from participantsofmeeting where mid=?1 and eid=?2", nativeQuery = true)
    ParticipantOfMeeting findByMidAndEid(String mid, Long eid);

    @Query(value = "select count(*) from participantsofmeeting p inner join meetingdetails m on p.mid=m.meeting_id where (m.meeting_date between ?1 and ?2) and (p.eid=?3)", nativeQuery = true)
    int countSeesionAttened(Date fromDate, Date toDate, long eid);
    @Query(value = "select avg(p.assesment_score) from participantsofmeeting p inner join meetingdetails m on p.mid=m.meeting_id where (m.meeting_date between ?1 and ?2) and (p.eid=?3)", nativeQuery = true)
    int getAverageScore(Date fromDate, Date toDate, long eid);
}
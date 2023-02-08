package com.globallogic.xlstodatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globallogic.xlstodatabase.modal.MeetingDetails;

import java.util.Date;
import java.util.List;

@Repository
public interface MeetingDetailsRepository extends JpaRepository<MeetingDetails, String> {

	@Query(value = "UPDATE meetingdetails set meetingAnchor=?1 and topic=?2 where meetingId=?3", nativeQuery = true)
	MeetingDetails updateSME(Long meetingAnchor, String topic, String meetingId);

	@Query(value = "select * from meetingdetails where meeting_anchor=?1", nativeQuery = true)
	List<MeetingDetails> getBySME(Long smeId);

	
	@Query(value="select * from meetingdetails where meeting_id=?1", nativeQuery = true)
	MeetingDetails findByMeetingId(String meetingId);

	@Query(value = "select * from meetingdetails where topic=?1",nativeQuery = true)
	List<MeetingDetails> findByTopic(String topic);

	@Query(value = "select sum(total_hours) from meetingdetails where (meeting_date between ?1 and ?2) and (meeting_anchor=?3)",nativeQuery=true)
	int getTotalHours(Date startDate,Date endDate,long eid);

	@Query(value = "select meeting_id from meetingdetails where meeting_date between ?1 and ?2",nativeQuery=true)
	List<String> getMeetingDetailsBetweenDate(Date startDate,Date endDate);

	@Query(value = "select * from meetingdetails where meeting_date between ?1 and ?2",nativeQuery=true)
	List<MeetingDetails> getMeetingDetailsOfNext2Week(Date startDate,Date endDate);

}

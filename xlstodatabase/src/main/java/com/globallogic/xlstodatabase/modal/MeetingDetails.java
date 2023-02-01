package com.globallogic.xlstodatabase.modal;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity

@Table(name = "meetingdetails")
public class MeetingDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String meetingId;
	private Date meetingDate;
	private String topic;
	@ManyToOne
	@JoinColumn(name = "meetingAnchor")
	private Employee meetingAnchor;
	private String totalHours;
	@OneToMany(mappedBy = "mid")
	private List<ParticipantOfMeeting> participantOfMeetings;

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public Date getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Employee getMeetingAnchor() {
		return meetingAnchor;
	}

	public void setMeetingAnchor(Employee meetingAnchor) {
		this.meetingAnchor = meetingAnchor;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public List<ParticipantOfMeeting> getParticipantOfMeetings() {
		return participantOfMeetings;
	}

	public void setParticipantOfMeetings(List<ParticipantOfMeeting> participantOfMeetings) {
		this.participantOfMeetings = participantOfMeetings;
	}

}

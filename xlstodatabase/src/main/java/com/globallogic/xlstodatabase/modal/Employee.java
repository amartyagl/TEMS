package com.globallogic.xlstodatabase.modal;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id

	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eid;
	private String firstName;
	private String lastName;
	private String email;
	private String projectCode;
	private String location;

	@OneToMany(mappedBy = "meetingAnchor")
	private List<MeetingDetails> meetingDetails;

	@OneToMany(mappedBy = "eid")
	private List<ParticipantOfMeeting> participantsOfMeeting;

	public Long getEid() {
		return eid;
	}

	public void setEid(Long eid) {
		this.eid = eid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<MeetingDetails> getMeetingDetails() {
		return meetingDetails;
	}

	public void setMeetingDetails(List<MeetingDetails> meetingDetails) {
		this.meetingDetails = meetingDetails;
	}

	public List<ParticipantOfMeeting> getParticipantsOfMeeting() {
		return participantsOfMeeting;
	}

	public void setParticipantsOfMeeting(List<ParticipantOfMeeting> participantsOfMeeting) {
		this.participantsOfMeeting = participantsOfMeeting;
	}

}


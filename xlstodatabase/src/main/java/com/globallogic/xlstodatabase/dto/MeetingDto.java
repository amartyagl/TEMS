package com.globallogic.xlstodatabase.dto;

import java.io.Serializable;

public class MeetingDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String meetingId;
	private String firstName;
	private String lastName;
	private String email;
	private String duration;
	private String timeJoined;
	private String timeExited;
	private String meetingDate;

	public String getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(String meetingDate) {
		this.meetingDate = meetingDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getTimeJoined() {
		return timeJoined;
	}

	public void setTimeJoined(String timeJoined) {
		this.timeJoined = timeJoined;
	}

	public String getTimeExited() {
		return timeExited;
	}

	public void setTimeExited(String timeExited) {
		this.timeExited = timeExited;
	}

}

package com.globallogic.xlstodatabase.dto;

import java.io.Serializable;

public class MeetingDetailsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String meetingId;
	private String topic;
	private String meetingDate;

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(String meetingDate) {
		this.meetingDate = meetingDate;
	}

	@Override
	public String toString() {
		return "MeetingDetailsDto [meetingId=" + meetingId + ", topic=" + topic + ", meetingDate=" + meetingDate + "]";
	}

}

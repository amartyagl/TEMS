package com.globallogic.xlstodatabase.dto;

import java.io.Serializable;

public class SmeTopicDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long smeId;
	private String topic;
	private String meetingId;

	public Long getSmeId() {
		return smeId;
	}

	public void setSmeId(Long smeId) {
		this.smeId = smeId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	@Override
	public String toString() {
		return "SmeTopicDto [smeId=" + smeId + ", topic=" + topic + ", meetingId=" + meetingId + "]";
	}

}

package com.globallogic.xlstodatabase.dto;

import lombok.Data;

import java.io.Serializable;

@Data
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
	private String assessmentScore;

}

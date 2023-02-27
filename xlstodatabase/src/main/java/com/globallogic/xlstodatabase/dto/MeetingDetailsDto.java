package com.globallogic.xlstodatabase.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MeetingDetailsDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private long eid;
	private String meetingId;
	private String topic;
	private String meetingDate;
	private String hours;

}

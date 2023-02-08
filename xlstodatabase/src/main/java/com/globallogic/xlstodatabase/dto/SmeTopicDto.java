package com.globallogic.xlstodatabase.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SmeTopicDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long smeId;
	private String topic;
	private String meetingId;

}

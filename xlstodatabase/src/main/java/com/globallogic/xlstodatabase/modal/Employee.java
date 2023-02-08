package com.globallogic.xlstodatabase.modal;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "employee")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
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
	@OneToMany(mappedBy = "eid")
	private List<SMEDetails> smeDetails;
}


package com.globallogic.xlstodatabase.modal;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
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
	private Double totalHours;
	@OneToMany(mappedBy = "mid")
	private List<ParticipantOfMeeting> participantOfMeetings;

}

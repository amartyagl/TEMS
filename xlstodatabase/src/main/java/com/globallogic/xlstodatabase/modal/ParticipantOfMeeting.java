package com.globallogic.xlstodatabase.modal;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity

@Table(name = "participantsofmeeting")

@IdClass(ParticipantOfmeetingsId.class)
public class ParticipantOfMeeting implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id

	@ManyToOne

	@JoinColumn(name = "eid")
	private Employee eid;

	@Id

	@ManyToOne

	@JoinColumn(name = "mid")
	private MeetingDetails mid;

	private String timeExisted;

	private String duration;

	private String assesmentScore;
	
	private String timeJoined;

	public Employee getEid() {
		return eid;
	}

	public void setEid(Employee eid) {
		this.eid = eid;
	}

	public MeetingDetails getMid() {
		return mid;
	}

	public void setMid(MeetingDetails mid) {
		this.mid = mid;
	}

	public String getTimeExisted() {
		return timeExisted;
	}

	public void setTimeExisted(String timeExisted) {
		this.timeExisted = timeExisted;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getAssesmentScore() {
		return assesmentScore;
	}

	public void setAssesmentScore(String assesmentScore) {
		this.assesmentScore = assesmentScore;
	}

	@Override
	public int hashCode() {
		return Objects.hash(assesmentScore, duration, eid, mid, timeExisted, timeJoined);
	}
	
	

	public String getTimeJoined() {
		return timeJoined;
	}

	public void setTimeJoined(String timeJoined) {
		this.timeJoined = timeJoined;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParticipantOfMeeting other = (ParticipantOfMeeting) obj;
		return Objects.equals(assesmentScore, other.assesmentScore) && Objects.equals(duration, other.duration)
				&& Objects.equals(eid, other.eid) && Objects.equals(mid, other.mid)
				&& Objects.equals(timeExisted, other.timeExisted) && Objects.equals(timeJoined, other.timeJoined);
	}

	@Override
	public String toString() {
		return "ParticipantOfMeeting [eid=" + eid + ", mid=" + mid + ", timeExisted=" + timeExisted + ", duration="
				+ duration + ", assesmentScore=" + assesmentScore + ", timeJoined=" + timeJoined + "]";
	}

	
}


package com.globallogic.xlstodatabase.modal;

import java.io.Serializable;
import java.util.Objects;

public class ParticipantOfmeetingsId implements Serializable {

	private static final long serialVersionUID = 1L;
	private String mid;
	private Long eid;

	public ParticipantOfmeetingsId() {
		super();
	}

	public ParticipantOfmeetingsId(String mid, Long eid) {
		super();
		this.mid = mid;
		this.eid = eid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(eid, mid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParticipantOfmeetingsId other = (ParticipantOfmeetingsId) obj;
		return Objects.equals(eid, other.eid) && Objects.equals(mid, other.mid);
	}

	@Override
	public String toString() {
		return "ParticipantOfmeetingsId [mid=" + mid + ", eid=" + eid + "]";
	}

}


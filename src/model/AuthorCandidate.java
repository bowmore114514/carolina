package model;

import java.util.Calendar;

public class AuthorCandidate {
	private String eventId;//イベントID
	private String autherCandidateId;
	private Calendar autherCandidate;//候補日
	private String autherCandidateRemark;//候補日に付随する備考

	//--------------------Constructor-----------------------
	public AuthorCandidate(String eventId, String autherCandidateId, Calendar autherCandidate,
			String autherCandidateRemark) {
		super();
		this.eventId = eventId;
		this.autherCandidateId = autherCandidateId;
		this.autherCandidate = autherCandidate;
		this.autherCandidateRemark = autherCandidateRemark;
	}
	//------------------Getter amd Setters--------------------
	public String getEventId() {
		return eventId;
	}

	public String getAutherCandidateId() {
		return autherCandidateId;
	}

	public void setAutherCandidateId(String autherCandidateId) {
		this.autherCandidateId = autherCandidateId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Calendar getAutherCandidate() {
		return autherCandidate;
	}

	public void setAutherCandidate(Calendar autherCandidate) {
		this.autherCandidate = autherCandidate;
	}

	public String getAutherCandidateRemark() {
		return autherCandidateRemark;
	}

	public void setAutherCandidateRemark(String autherCandidateRemark) {
		this.autherCandidateRemark = autherCandidateRemark;
	}







}
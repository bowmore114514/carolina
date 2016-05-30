package model;

public class UserCandidate {
	private String eventId;
	private String itemId;
	private String userCandidateId;
	private String candidateRemark;
	private int preferredFlagSet;//希望日時ごとのフラグ 1:参加,0:不参加(初期値), 2:△
	public UserCandidate(String eventId, String itemId, String userCandidateId,
			String candidateRemark, Integer preferredFlagSet) {
		super();
		this.eventId = eventId;
		this.itemId = itemId;
		this.userCandidateId = userCandidateId;
		this.candidateRemark = candidateRemark;
		this.preferredFlagSet = preferredFlagSet;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getUserCandidateId() {
		return userCandidateId;
	}
	public void setUserCandidateId(String userCandidateId) {
		this.userCandidateId = userCandidateId;
	}
	public String getCandidateRemark() {
		return candidateRemark;
	}
	public void setCandidateRemark(String candidateRemark) {
		this.candidateRemark = candidateRemark;
	}
	public int getPreferredFlagSet() {
		return preferredFlagSet;
	}
	public void setPreferredFlagSet(int preferredFlagSet) {
		this.preferredFlagSet = preferredFlagSet;
	}





}

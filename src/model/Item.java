package model;

import java.util.Calendar;

public class Item {
	private String eventId;//イベントID
	private String itemId;//投稿ID
	private String userName; //投稿者の名前
	private String userPass;//編集用のpass
	private String userRemark;//投稿者の備考
	private Calendar userRegistDay;//投稿日時


	public Item(String eventId, String itemId, String userName, String userPass, String userRemark,
			Calendar userRegistDay) {
		super();
		this.eventId = eventId;
		this.itemId = itemId;
		this.userName = userName;
		this.userPass = userPass;
		this.userRemark = userRemark;
		this.userRegistDay = userRegistDay;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	public String getUserRemark() {
		return userRemark;
	}
	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}
	public Calendar getUserRegistDay() {
		return userRegistDay;
	}
	public void setUserRegistDay(Calendar userRegistDay) {
		this.userRegistDay = userRegistDay;
	}

	public void setUserRegistDay(int year,int month,int date,int hour, int minute ,int second ) {
		Event.setYear(this.userRegistDay ,year);
		Event.setMonth(this.userRegistDay ,month);
		Event.setDate(this.userRegistDay ,date);
		Event.setHour(this.userRegistDay ,hour);
		Event.setMinute(this.userRegistDay ,minute);
		Event.setSecond(this.userRegistDay ,second);
	}

	public int getUserRegistYear(){
		return Event.getYear(userRegistDay);
	}
	public int getRegistMonth(){
		return Event.getMonth(userRegistDay);
	}
	public int getRegistDate(){
		return Event.getDate(userRegistDay);
	}
	public int getRegistHour(){
		return Event.getHour(userRegistDay);
	}
	public int getRegistMinute(){
		return Event.getMinute(userRegistDay);
	}
	public int getRegistSecond(){
		return Event.getSecond(userRegistDay);
	}

	public void setRegistYear(int year){
		Event.setYear(this.userRegistDay ,year);
	}
	public void setRegistMonth(int month){
		Event.setMonth(this.userRegistDay ,month);
	}
	public void setRegistDate(int date){
		Event.setDate(this.userRegistDay ,date);
	}
	public void setRegistHour(int hour){
		Event.setHour(this.userRegistDay ,hour);
	}
	public void setRegistMinute(int minute){
		Event.setMinute(this.userRegistDay ,minute);
	}
	public void setRegistSeconde(int second){
		Event.setSecond(this.userRegistDay ,second);
	}



}

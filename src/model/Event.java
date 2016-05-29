package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Event {
//--------------------Instance fields--------------------

	/**
	 * イベントID
	 */
	private String  eventId;

	/**
	 * イベント名
	 */
	private String eventName;//イベントの名前
	/**
	 * イベント製作者の備考欄
	 */
	private String autherRemark;//イベント製作者の備考欄
	/**
	 * イベント製作者名
	 */
	private String autherName;//イベント製作者の名前
	/**
	 * イベント製作者の編集用pass
	 */
	private String autherPass;//イベント製作者の編集用pass
	/**
	 * イベントページのURL。?pageid=
	 */
	private String eventUrl;//イベントページのURL
	/**
	 * 締め切り日時
	 */
	private Calendar deadlineDay;//締め切り日時
	/**
	 * 確定日時
	 */
	private Calendar determinedDay ;//確定日時
	/**
	 * 投稿日時
	 */
	private Calendar registDay;//投稿日時
	/**
	 * イベント最終更新日時（書き込みや編集、確定した際）
	 */
	private Calendar updatedate;//イベント最終更新日時（書き込みや編集、確定した際）
	/**
	 * イベント確定のフラグ 1:確定,0:未確定
	 */
	private int determinedFlag;//イベント確定のフラグ 1:確定,0:未確定
	/**
	 * イベントの公開フラグ.1:公開,0:非公開
	 */
	private int eventOpenFlag;//イベントの公開フラグ.1:公開,0:非公開


//--------------------Static fields--------------------

	/**
	 *管理者用パスワード。初期値：password
	 */
	public static String masterKey = "password";//管理者用のパスワード

//-------------------Constructor--------------------
	public Event(String eventId, String eventName, String autherRemark, String autherName, String autherPass,
			String eventUrl, Calendar deadlineDay, Calendar determinedDay, Calendar registDay, Calendar updatedate,
			int determinedFlag, int eventOpenFlag) {
		super();
		this.eventId = eventId;
		this.eventName = eventName;
		this.autherRemark = autherRemark;
		this.autherName = autherName;
		this.autherPass = autherPass;
		this.eventUrl = eventUrl;
		this.deadlineDay = deadlineDay;
		this.determinedDay = determinedDay;
		this.registDay = registDay;
		this.updatedate = updatedate;
		this.determinedFlag = determinedFlag;
		this.eventOpenFlag = eventOpenFlag;
	}

//--------------------Getter and Setters--------------------
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getAutherRemark() {
		return autherRemark;
	}

	public void setAutherRemark(String autherRemark) {
		this.autherRemark = autherRemark;
	}

	public String getAutherName() {
		return autherName;
	}

	public void setAutherName(String autherName) {
		this.autherName = autherName;
	}

	public String getAutherPass() {
		return autherPass;
	}

	public void setAutherPass(String autherPass) {
		this.autherPass = autherPass;
	}

	public String getEventUrl() {
		return eventUrl;
	}

	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}

	public Calendar getDeadlineDay() {
		return deadlineDay;
	}

	public void setDeadlineDay(Calendar deadlineDay) {
		this.deadlineDay = deadlineDay;
	}

	public Calendar getDeterminedDay() {
		return determinedDay;
	}

	public void setDeterminedDay(Calendar determinedDay) {
		this.determinedDay = determinedDay;
	}

	public Calendar getRegistDay() {
		return registDay;
	}

	public void setRegistDay(Calendar registDay) {
		this.registDay = registDay;
	}

	public Calendar getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Calendar updatedate) {
		this.updatedate = updatedate;
	}

	public int getDeterminedFlag() {
		return determinedFlag;
	}

	public void setDeterminedFlag(int determinedFlag) {
		this.determinedFlag = determinedFlag;
	}

	public int getEventOpenFlag() {
		return eventOpenFlag;
	}

	public void setEventOpenFlag(int eventOpenFlag) {
		this.eventOpenFlag = eventOpenFlag;
	}

	public static String getMasterKey() {
		return masterKey;
	}

	public static void setMasterKey(String masterKey) {
		Event.masterKey = masterKey;
	}

	//--------------------Static methods-------------------

	/**
	 * @param str
	 * @return 空文字かnullならばfalse。それ以外はtrue
	 *
	 */
	public static boolean isNotStrEnpty (String str){
		if(str!= null && str.length() != 0) return true;
		else return false;
	}



	/**
	 * @param cal Calendar
	 * @return calの年
	 */
	public  static int getYear(Calendar cal){
		return cal.get(Calendar.YEAR);
	}
	/**
	 * @param cal Calendar
	 * @return calの月
	 */
	public  static int getMonth(Calendar cal){
		return cal.get(Calendar.MONTH);
	}
	/**
	 * @param cal Calendar
	 * @return calの日
	 */
	public  static int getDate(Calendar cal){
		return cal.get(Calendar.DATE);
	}
	/**
	 * @param cal Calendar
	 * @return calの時（24時間表示）
	 */
	public  static int getHour(Calendar cal){
		return cal.get(Calendar.HOUR_OF_DAY);
	}
	/**
	 * @param cal Calendar
	 * @return calの分
	 */
	public  static int getMinute(Calendar cal){
		return cal.get(Calendar.MINUTE);
	}
	/**
	 * @param cal Calendar
	 * @return calの秒
	 */
	public  static int getSecond(Calendar cal){
		return cal.get(Calendar.SECOND);
	}

	public  static void setYear(Calendar cal,int year){
		if(year>0) cal.set(Calendar.YEAR, year);
		else cal.set(Calendar.YEAR, Calendar.YEAR);
	}

	public  static void setMonth(Calendar cal,int month){
		if(month>=0) cal.set(Calendar.MONTH, month);
		else cal.set(Calendar.MONTH, Calendar.MONTH );
	}

	public  static void setDate(Calendar cal,int date){
		if(date>0) cal.set(Calendar.DATE, date);
		else cal.set(Calendar.DATE, Calendar.DATE);
	}

	public  static void setHour(Calendar cal,int hour){
		if(hour>0) cal.set(Calendar.HOUR_OF_DAY, hour);
		else cal.set(Calendar.HOUR_OF_DAY, Calendar.HOUR_OF_DAY);
	}

	public  static void setMinute(Calendar cal,int minute){
		if(minute>0) cal.set(Calendar.MINUTE, minute);
		else cal.set(Calendar.MINUTE, Calendar.MINUTE);
	}

	public  static void setSecond(Calendar cal,int second){
		if(second>0) cal.set(Calendar.SECOND, second);
		else cal.set(Calendar.SECOND, Calendar.SECOND);
	}

    /**
     * @param value 入力文字列
     * @param length 文字幅
     * @return 文字列valueの右からlengrh個の要素を取り出す。（順序不動）
     */
    public static String rightstring(String value, int length){
        try{
            if(value.length() >= length){
                return value.substring(value.length() - length);
            }else{
                return  value.substring(1);
            }
        }catch(Exception e){
            return value;
        }
    }

    /**
     * @param url '......?pageid=********'の形の文字列
     * @return イベントID
     */
    public static String getIdFromURL(String url){
    	return String.valueOf(Integer.parseInt((rightstring(url,4))));
    }
	/**
	 * @param strId イベントID
	 * @return 数字文字列++++****。++++：乱数部、****：ID部分
	 */
	public static String createEventPageId(String strId){//EventIdからURL用のEventIdに変換。
		Random rand=new Random();//乱数を用いてURL推定しにくくする
		return String.valueOf(( rand.nextInt(9999) + 1 )*10000 + Integer.parseInt(strId));
	}

	/**
	 * @param eventにURLをセット(eventのeventIdはセットされているものとする)
	 */
	public static void createEventPageUrl(Event event){///EventClassからEventURLを生成。ID生成後に実行すべし

		event.setEventUrl("?pageid=" + createEventPageId(event.getEventId()));
	}
	/**
	 * @param id
	 * @return URL
	 */
	public static String getUrlbyId(int id){///EventClassからEventURLを生成。ID生成後に実行すべし

		return "?pageid=" + createEventPageId(String.valueOf(id));
	}

	/**
	 * @param strCal yyyyMMddHHmmss
	 * @return strCalのClendar化
	 */
	public static Calendar getCalendarByStr(String strCal){//フォーマットされた日時のStrをCalendarに変換
		Calendar cal = Calendar.getInstance();
		int yyyy = Integer.parseInt(strCal.substring(0,4));
		int mM = Integer.parseInt(strCal.substring(4,6));
		int dd = Integer.parseInt(strCal.substring(6,8));
		int hh = Integer.parseInt(strCal.substring(8,10));
		int mm = Integer.parseInt(strCal.substring(10,12));
		int ss = Integer.parseInt(strCal.substring(12,14));
		Event.setYear(cal, yyyy);
		Event.setMonth(cal, mM);
		Event.setDate(cal, dd);
		Event.setHour(cal, hh);
		Event.setMinute(cal, mm);
		Event.setSecond(cal, ss);
		return cal;
	}

	/**
	 * @param cal Calendar class
	 * @return Calendar classを文字列"yyyyMMddHHmmss"に変換したもの
	 */
	public static String getStrByCalendar(Calendar cal){//CalendarクラスをフォーマットしてStrに変換
		SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(cal.getTime());
	}


}

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import model.AuthorCandidate;
import model.Event;
import model.Item;
import model.UserCandidate;

public class EventDAO {
	private final String HOST ="localhost";//ホスト名
	private final String PORT ="5432";//ポート番号
	private final String DBNAME ="Carolina";//データベース名
	private String ROLENAME = "postgres";//ロール名
	private final String PASSWORD = "0000";//パスワード
	private final String URL = "jdbc:postgresql://" +HOST+":"+ PORT + "/" + DBNAME;//サーバーURL

	//--------------------INSERT文----------------------


	public  static String insEvent="INSERT INTO event(eventname, authorremark,authorname,authorpass,eventurl,deadlineday"
				+ ",determineday,registday,updatedate,determinedflag,eventopenflag) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)" ;

	public static String insItem ="INSERT INTO item"
			+ "(eventid,username,userremark,userpass,userregistday)"
			+ " VALUES(?,?,?,?,?)";

	public static String insAutCand ="INSERT INTO authorcandidate"
			+ "(eventid,authorcandidate,authorremark)"
			+ " VALUES(?,?,?)";

	public static String insUseCand = "INSERT INTO usercandidate"
			+ "(eventid,itemid,preferredflag,userremark)"
			+ " VALUES(?,?,?,?)";

	//--------------------UPDATE文---------------------
	public  static String upEvent="update event set eventname = ?, authorremark = ?, authorname = ?, authorpass = ?, eventurl = ?, deadlineday = ?"
			+ ", determinedday = ?, registday = ?, updatedate = ?, determinedflag = ?, eventopenflag = ? where eventid = ?";

	public static String upItem ="UPDATE  item SET"
			+ "username=? ,userremark=? ,userpass=? ,userregistday=? where itemid=? and eventid=?";

	public static String upAutCand ="update authorcandidate set"
			+ "authorcandidate=? ,authorremark=? where eventid=? and  candidateid=?";

	public static String upUseCand = "update  usercandidate set"
			+ "preferredflag=? ,authorremark=? where eventid=? and itemid=? and usercandidateid=?";


	//---------------------DELETE文----------------------
	public static String delEvent = "DELETE FROM event WHERE eventid =?";

	public static String delItem = "DELETE FROM item WHERE eventid=? and itemid=?";

	public static String delAutCand = "DELETE FROM authorcandidate WHERE eventid=? and candidateid=?";

	public static String delUseCand = "DELETE FROM usercandidate WHERE eventid=? and itemid=? and usercandidateid=?";

	//--------------------SELECT文-------------------------
	public static String selEvent = "SELECT eventname, authorremark ,authorname ,authorpass,eventurl,deadlineday"
			+ ",determinedday,registday,updatedate,determinedflag,eventopenflag ,eventid FROM event";

	public static String selItem ="SELECT username ,userremark ,userpass ,userregistday ,itemid ,eventid FROM item";

	public static String selAutCand = "SELECT authorcandidate ,authorremark, eventid ,candidateid FROM authorcandidate";

	public static String selUseCand = " SELECT preferredflag ,authorremark , eventid ,itemid ,usercandidateid";

	//--------------------Instance methods------------------

	public  ArrayList <AuthorCandidate> getAuthorCandidate(){//indexにeventIDを入力すると、そのeventIDのCandidateを返す
		ArrayList<AuthorCandidate> eventCandidate = new ArrayList<AuthorCandidate>();//取り出したデータの格納先
		//----------接続パラメータ設定----------
		Connection conn = null;
		PreparedStatement  pStmt = null; // SQL文オブジェクト
	    ResultSet  resultSet = null; // 問合せ結果オブジェクト

		try {
			//--------------JDBCドライバ読み込み----------
			Class.forName("org.postgresql.Driver");

			//----------------DBに接続---------------------
			conn=DriverManager.getConnection(URL,ROLENAME,PASSWORD);



			//SELECT文準備
			pStmt = conn.prepareStatement(selAutCand);
			//----------SQL文実行----------
			 resultSet = pStmt.executeQuery();
			while(resultSet.next()){
				String eventId = String.valueOf( resultSet.getInt("EVENTID"));
				Calendar candidate = Event.getCalendarByStr( resultSet.getString("AUTHORCANDIDATE"));
				String remark = resultSet.getString("AUTHORREMARK");
				String candId = String.valueOf( resultSet.getInt("CANDIDATEID"));
				AuthorCandidate autCand = new AuthorCandidate(eventId,candId,candidate,remark);
				eventCandidate.add(autCand);
			}




		//---------------------ここからエラー処理-----------------------
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e){
			e.printStackTrace();
			return null;
		} finally {
			//-----DB切断-----
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					return null;
				}
			}
		}
		//--------------------ここまでエラー処理---------------------------
		return eventCandidate;//------returnしたいものを書け------

	}

	public  ArrayList <Event> getEventList(){
		ArrayList<Event> EventList = new ArrayList<Event>();//取り出したデータの格納先
		//----------接続パラメータ設定----------
		Connection conn = null;
		PreparedStatement  pStmt = null; // SQL文オブジェクト
	    ResultSet  resultSet = null; // 問合せ結果オブジェクト
		try {
			//--------------JDBCドライバ読み込み----------
			Class.forName("org.postgresql.Driver");

			//----------------DBに接続---------------------
			conn=DriverManager.getConnection(URL,ROLENAME,PASSWORD);




			//SELECT文準備
			pStmt = conn.prepareStatement(selEvent);

			//----------SQL文実行----------
			 resultSet = pStmt.executeQuery();


			while(resultSet.next()){
/* SELECT EVENTNAME, AUTHORREMARK ,AUTHORNAME ,AUTHORPASS,EVENTURL,DEADLINEDAY"
			+ ",DETERMINEDDAY,REGISTDAY,UPDATEDATE,DETERMINEDFLAG,EVENTOPENFLAG ,EVENTID FROM event";*/
				String eventId = String.valueOf(resultSet.getInt("EVENTID"));
				String eventName = resultSet.getString("EVENTNAME");//イベントの名前
				Calendar  registDay = Event.getCalendarByStr(resultSet.getString("REGISTDAY"));//投稿日時
				String autherName  = resultSet.getString("AUTHORNAME");//イベント製作者の名前
				String autherPass  = resultSet.getString("AUTHORPASS");//イベント製作者の編集用pass
				Calendar deadlineDay = Event.getCalendarByStr(resultSet.getString("DEADLINEDAY"));//締め切り日時
				Calendar determinedDay = Event.getCalendarByStr(resultSet.getString("DETERMINEDAY"));//確定日時
				int determinedFlag = resultSet.getInt("DETERMINEDFLAG");//イベント確定のフラグ 1:確定,0:未確定
				int eventOpenFlag = resultSet.getInt("EVENTOPENFLAG");//イベントの公開フラグ.1:公開,0:非公開
				String eventUrl =  resultSet.getString("EVENTURL");//イベントページのURL
				Calendar last = Event.getCalendarByStr(resultSet.getString("UPDATEDATE"));//最終更新日
				String remark = resultSet.getString("AUTHORREMARK");//備考


				Event event = new Event(eventId,eventName,remark,autherName,
						autherPass,eventUrl,deadlineDay, determinedDay, registDay,last,determinedFlag,
						eventOpenFlag);
				EventList.add(event);

			}

		//---------------------ここからエラー処理-----------------------
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e){
			e.printStackTrace();
			return null;
		} finally {
			//-----DB切断-----
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					return null;
				}
			}
		}
		//--------------------ここまでエラー処理---------------------------
		return EventList;//------returnしたいものを書け------

	}



	public  ArrayList <UserCandidate> getUserCandidateList(){//eventIDを入力すると、そのeventIDのAutherRemarkを返す
		ArrayList<UserCandidate> useCandiList = new ArrayList<UserCandidate>();//取り出したデータの格納先
		//----------接続パラメータ設定----------
		Connection conn = null;
		PreparedStatement  pStmt = null; // SQL文オブジェクト
	    ResultSet  resultSet = null; // 問合せ結果オブジェクト

		try {
			//--------------JDBCドライバ読み込み----------
			Class.forName("org.postgresql.Driver");

			//----------------DBに接続---------------------
			conn=DriverManager.getConnection(URL,ROLENAME,PASSWORD);




			//SELECT文準備
			pStmt = conn.prepareStatement(selUseCand);
			//----------SQL文実行----------
			 resultSet = pStmt.executeQuery();
			while(resultSet.next()){
				String eventId =String.valueOf( resultSet.getInt("EVENTID"));
				String itemId = String.valueOf(resultSet.getInt("ITEMID"));
				String uRemark = resultSet.getString("USERREMARK");
				String uCandId = String.valueOf( resultSet.getInt("USERCANDIDATEID"));
				int flag = resultSet.getInt("PREFERREDFLAG");

				UserCandidate u = new UserCandidate(eventId,itemId,uCandId,uRemark,flag);
				useCandiList.add(u);
			}




		//---------------------ここからエラー処理-----------------------
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e){
			e.printStackTrace();
			return null;
		} finally {
			//-----DB切断-----
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					return null;
				}
			}
		}
		//--------------------ここまでエラー処理---------------------------
		return useCandiList;//------returnしたいものを書け------

	}


	public  ArrayList <Item> get(){//eventIDを入力すると、そのeventIDのAutherRemarkを返す
		ArrayList<Item> itemList = new ArrayList<Item>();//取り出したデータの格納先
		//----------接続パラメータ設定----------
		Connection conn = null;
		PreparedStatement  pStmt = null; // SQL文オブジェクト
	    ResultSet  resultSet = null; // 問合せ結果オブジェクト

		try {
			//--------------JDBCドライバ読み込み----------
			Class.forName("org.postgresql.Driver");

			//----------------DBに接続---------------------
			conn=DriverManager.getConnection(URL,ROLENAME,PASSWORD);




			//SELECT文準備
			pStmt = conn.prepareStatement(selItem);
			//----------SQL文実行----------
			 resultSet = pStmt.executeQuery();
			while(resultSet.next()){
				String eventId =String.valueOf( resultSet.getInt("EVENTID"));
				String itemId = String.valueOf(resultSet.getInt("ITEMID"));
				String name = resultSet.getString("USERNAME");
				String uRemark = resultSet.getString("USERREMARK");
				Calendar uRegistDay = Event.getCalendarByStr ( resultSet.getString("USERREGISTDAY"));
				String uPass = resultSet.getString("USERPASS");

				Item u = new Item(eventId,itemId,name,uPass,uRemark,uRegistDay);
				itemList.add(u);
			}




		//---------------------ここからエラー処理-----------------------
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e){
			e.printStackTrace();
			return null;
		} finally {
			//-----DB切断-----
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					return null;
				}
			}
		}
		//--------------------ここまでエラー処理---------------------------
		return itemList;//------returnしたいものを書け------

	}

//----------------------------INSERT methods -------------------------------------
	public  int insertEvent(Event event){
		ResultSet  resultSet = null;
		int id= 0;
		int m= 0;
		Connection conn = null;
		PreparedStatement  pStmt = null; // SQL文オブジェクト
		int result=1;
		try {
			//--------------JDBCドライバ読み込み----------
			Class.forName("org.postgresql.Driver");

			//----------------DBに接続---------------------
			conn=DriverManager.getConnection(URL,ROLENAME,PASSWORD);



			//INSERT文準備
			pStmt = conn.prepareStatement(insEvent);

			//----------SQL文実行----------


				pStmt.setString(1, event.getEventName());
				pStmt.setString(2, event.getauthorRemark());
				pStmt.setString(3, event.getauthorName());
				pStmt.setString(4, event.getauthorPass());
				pStmt.setString(5, event.getEventUrl());
				pStmt.setString(6, Event.getStrByCalendar(event.getDeadlineDay()));
				pStmt.setString(7, Event.getStrByCalendar(event.getDeterminedDay()));
				pStmt.setString(8, Event.getStrByCalendar(event.getRegistDay()));
				pStmt.setString(9,Event.getStrByCalendar(event.getUpdatedate()));
				pStmt.setInt(10, event.getDeterminedFlag());
				pStmt.setInt(11, event.getEventOpenFlag());
				result *= pStmt.executeUpdate();

				//--------------ID返却用
				String s = "SELECT EVENTID  FROM event ";


				//SELECT文準備
				pStmt = conn.prepareStatement(s);
				//----------SQL文実行----------
				resultSet = pStmt.executeQuery();
				while(resultSet.next()){
					id = resultSet.getInt("EVENTID");
					if(m>=id){
						id=m;
					}else{
						m=id;
					}
					}

				//---------------------ここからエラー処理-----------------------
				if(result != 1) return 0;
		} catch (SQLException e){
			e.printStackTrace();
			return 0;
		} catch (ClassNotFoundException e){
			e.printStackTrace();
			return 0;
		} finally {
			//-----DB切断-----
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					return 0;
				}
			}
		}
		//--------------------ここまでエラー処理---------------------------
		return id;

	}



	public  int insertItemList(Item item){   //indexにeventIDを入力すると、そのeventIDのAutherRemarkを返す
		ResultSet  resultSet = null;
		int id= 0;
		int n= 0;
		Connection conn = null;
		PreparedStatement  pStmt = null; // SQL文オブジェクト
		int result=1;
		try {
			//--------------JDBCドライバ読み込み----------
			Class.forName("org.postgresql.Driver");

			//----------------DBに接続---------------------
			conn=DriverManager.getConnection(URL,ROLENAME,PASSWORD);



			//INSERT文準備
			pStmt = conn.prepareStatement(insItem);
			//----------SQL文実行----------
			pStmt.setInt(1, Integer.parseInt(item.getEventId()));
			pStmt.setString(2,item.getUserName());
			pStmt.setString(3, item.getUserRemark());
			pStmt.setString(4, item.getUserPass());
			pStmt.setString(5, Event.getStrByCalendar(item.getUserRegistDay()));
			result *= pStmt.executeUpdate();
			//--------------ID返却用
			String s = "SELECT ITEMID  FROM item";


			/*				//SELECT文準備
				pStmt = conn.prepareStatement(s);
				//----------SQL文実行----------
				resultSet = pStmt.executeQuery();
				id = resultSet.getInt("ITEMID");*/
			pStmt = conn.prepareStatement(s);
			//----------SQL文実行----------
			resultSet = pStmt.executeQuery();
			while(resultSet.next()){
				id = resultSet.getInt("ITEMID");
				if(n>=id){
					id=n;
					}else{
						n=id;
					}
					}





			//---------------------ここからエラー処理-----------------------
			if(result != 1) return 0;
		} catch (SQLException e){
			e.printStackTrace();
			return 0;
		} catch (ClassNotFoundException e){
			e.printStackTrace();
			return 0;
		} finally {
			//-----DB切断-----
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					return 0;
				}
			}
		}
		//--------------------ここまでエラー処理---------------------------
		return id;

	}

	public  boolean insertAuthorCandidateList(AuthorCandidate autCand){//indexにeventIDを入力すると、そのeventIDのAutherRemarkを返す

		Connection conn = null;
		PreparedStatement  pStmt = null; // SQL文オブジェクト
		int result=1;
		try {
			//--------------JDBCドライバ読み込み----------
			Class.forName("org.postgresql.Driver");

			//----------------DBに接続---------------------
			conn=DriverManager.getConnection(URL,ROLENAME,PASSWORD);



			//INSERT文準備
			pStmt = conn.prepareStatement(insAutCand);
			//----------SQL文実行----------

				pStmt.setInt(1, Integer.parseInt(autCand.getEventId()));
				pStmt.setString(2,Event.getStrByCalendar(autCand.getAutherCandidate()));
				pStmt.setString(3, autCand.getAutherCandidateRemark());
				result *= pStmt.executeUpdate();

			//---------------------ここからエラー処理-----------------------
			if(result != 1) return false;
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e){
			e.printStackTrace();
			return false;
		} finally {
			//-----DB切断-----
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					return false;
				}
			}
		}
		//--------------------ここまでエラー処理---------------------------
		return true;

	}


	public  boolean insertUserCandidateList(UserCandidate userCand){//indexにeventIDを入力すると、そのeventIDのAutherRemarkを返す

		Connection conn = null;
		PreparedStatement  pStmt = null; // SQL文オブジェクト
		int result=1;
		try {
			//--------------JDBCドライバ読み込み----------
			Class.forName("org.postgresql.Driver");

			//----------------DBに接続---------------------
			conn=DriverManager.getConnection(URL,ROLENAME,PASSWORD);
			/*	public static String insUseCand = "INSERT INTO usercandidate "
			+ "(EVENTID,ITEMID,PREFERREDFLAG,USERREMARKREMARK)"
			+ " VALUES(?,?,?,?)";
*/


			//INSERT文準備
			pStmt = conn.prepareStatement(insUseCand);
			//----------SQL文実行----------

				pStmt.setInt(1, Integer.parseInt(userCand.getEventId()));
				pStmt.setInt(2,Integer.parseInt(userCand.getItemId()));
				pStmt.setInt(3, userCand.getPreferredFlagSet());
				pStmt.setString(4, userCand.getCandidateRemark());
				result *= pStmt.executeUpdate();

			//---------------------ここからエラー処理-----------------------
			if(result != 1) return false;
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e){
			e.printStackTrace();
			return false;
		} finally {
			//-----DB切断-----
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					return false;
				}
			}
		}
		//--------------------ここまでエラー処理---------------------------
		return true;

	}


	//-----------------------------UPDATE文--------------------------------------

	public  boolean updateEvent(Event event){

		Connection conn = null;
		PreparedStatement  pStmt = null; // SQL文オブジェクト
		int result=1;
		try {
			//--------------JDBCドライバ読み込み----------
			Class.forName("org.postgresql.Driver");

			//----------------DBに接続---------------------
			conn=DriverManager.getConnection(URL,ROLENAME,PASSWORD);



			//INSERT文準備
			pStmt = conn.prepareStatement(upEvent);

			//----------SQL文実行----------


				pStmt.setString(1, event.getEventName());
				pStmt.setString(2, event.getauthorRemark());
				pStmt.setString(3, event.getauthorName());
				pStmt.setString(4, event.getauthorPass());
				pStmt.setString(5, event.getEventUrl());
				pStmt.setString(6, Event.getStrByCalendar(event.getDeadlineDay()));
				pStmt.setString(7, Event.getStrByCalendar(event.getDeterminedDay()));
				pStmt.setString(8, Event.getStrByCalendar(event.getRegistDay()));
				pStmt.setString(9,Event.getStrByCalendar(event.getUpdatedate()));
				pStmt.setInt(10, event.getDeterminedFlag());
				pStmt.setInt(11, event.getEventOpenFlag());
				pStmt.setInt(12, Integer.parseInt(event.getEventId()));
				result *= pStmt.executeUpdate();

				//--------------ID返却用


				//---------------------ここからエラー処理-----------------------
				if(result != 1) return false;
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e){
			e.printStackTrace();
			return false;
		} finally {
			//-----DB切断-----
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					return false;
				}
			}
		}
		//--------------------ここまでエラー処理---------------------------
		return true;

	}



	public  boolean updateItem(Item item){   //indexにeventIDを入力すると、そのeventIDのAutherRemarkを返す

		Connection conn = null;
		PreparedStatement  pStmt = null; // SQL文オブジェクト
		int result=1;
		try {
			//--------------JDBCドライバ読み込み----------
			Class.forName("org.postgresql.Driver");

			//----------------DBに接続---------------------
			conn=DriverManager.getConnection(URL,ROLENAME,PASSWORD);



			//INSERT文準備
			pStmt = conn.prepareStatement(upItem);
			//----------SQL文実行----------

			pStmt.setString(1,item.getUserName());
			pStmt.setString(2, item.getUserRemark());
			pStmt.setString(3, item.getUserPass());
			pStmt.setString(4, Event.getStrByCalendar(item.getUserRegistDay()));
			pStmt.setInt(6, Integer.parseInt(item.getEventId()));
			pStmt.setInt(6,Integer.parseInt(item.getItemId()));
			result *= pStmt.executeUpdate();
			//--------------ID返却用






			//---------------------ここからエラー処理-----------------------
			if(result != 1) return false;
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e){
			e.printStackTrace();
			return false;
		} finally {
			//-----DB切断-----
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					return false;
				}
			}
		}
		//--------------------ここまでエラー処理---------------------------
		return true;

	}

	public  boolean updateAuthorCandidateList(AuthorCandidate autCand){//indexにeventIDを入力すると、そのeventIDのAutherRemarkを返す

		Connection conn = null;
		PreparedStatement  pStmt = null; // SQL文オブジェクト
		int result=1;
		try {
			//--------------JDBCドライバ読み込み----------
			Class.forName("org.postgresql.Driver");

			//----------------DBに接続---------------------
			conn=DriverManager.getConnection(URL,ROLENAME,PASSWORD);



			//INSERT文準備
			pStmt = conn.prepareStatement(upAutCand);
			//----------SQL文実行----------

				pStmt.setInt(3, Integer.parseInt(autCand.getEventId()));
				pStmt.setString(1,Event.getStrByCalendar(autCand.getAutherCandidate()));
				pStmt.setString(2, autCand.getAutherCandidateRemark());
				pStmt.setInt(4, Integer.parseInt( autCand.getAutherCandidateId()));
				result *= pStmt.executeUpdate();

			//---------------------ここからエラー処理-----------------------
			if(result != 1) return false;
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e){
			e.printStackTrace();
			return false;
		} finally {
			//-----DB切断-----
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					return false;
				}
			}
		}
		//--------------------ここまでエラー処理---------------------------
		return true;

	}


	public  boolean updateUserCandidateList(UserCandidate userCand){//indexにeventIDを入力すると、そのeventIDのAutherRemarkを返す

		Connection conn = null;
		PreparedStatement  pStmt = null; // SQL文オブジェクト
		int result=1;
		try {
			//--------------JDBCドライバ読み込み----------
			Class.forName("org.postgresql.Driver");

			//----------------DBに接続---------------------
			conn=DriverManager.getConnection(URL,ROLENAME,PASSWORD);
			/*	public static String insUseCand = "INSERT INTO usercandidate "
			+ "(EVENTID,ITEMID,PREFERREDFLAG,USERREMARKREMARK)"
			+ " VALUES(?,?,?,?)";
*/


			//INSERT文準備
			pStmt = conn.prepareStatement(upUseCand);
			//----------SQL文実行----------

				pStmt.setInt(3, Integer.parseInt(userCand.getEventId()));
				pStmt.setInt(4,Integer.parseInt(userCand.getItemId()));
				pStmt.setInt(1, userCand.getPreferredFlagSet());
				pStmt.setString(2, userCand.getCandidateRemark());
				pStmt.setInt(5, Integer.parseInt(userCand.getUserCandidateId()));
				result *= pStmt.executeUpdate();

			//---------------------ここからエラー処理-----------------------
			if(result != 1) return false;
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e){
			e.printStackTrace();
			return false;
		} finally {
			//-----DB切断-----
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					return false;
				}
			}
		}
		//--------------------ここまでエラー処理---------------------------
		return true;

	}
	//---------------------------DELETE文------------------------------------------

	public  boolean deleteEvent(String eventId){

		Connection conn = null;
		PreparedStatement  pStmt = null; // SQL文オブジェクト
		int result=1;
		try {
			//--------------JDBCドライバ読み込み----------
			Class.forName("org.postgresql.Driver");

			//----------------DBに接続---------------------
			conn=DriverManager.getConnection(URL,ROLENAME,PASSWORD);



			//INSERT文準備
			pStmt = conn.prepareStatement(delEvent);

			//----------SQL文実行----------


				pStmt.setString(1,eventId);
				result *= pStmt.executeUpdate();

				//--------------ID返却用


				//---------------------ここからエラー処理-----------------------
				if(result != 1) return false;
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e){
			e.printStackTrace();
			return false;
		} finally {
			//-----DB切断-----
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					return false;
				}
			}
		}
		//--------------------ここまでエラー処理---------------------------
		return true;

	}



	public  boolean deleteItem(String eventId,String itemId){   //indexにeventIDを入力すると、そのeventIDのAutherRemarkを返す

		Connection conn = null;
		PreparedStatement  pStmt = null; // SQL文オブジェクト
		int result=1;
		try {
			//--------------JDBCドライバ読み込み----------
			Class.forName("org.postgresql.Driver");

			//----------------DBに接続---------------------
			conn=DriverManager.getConnection(URL,ROLENAME,PASSWORD);



			//INSERT文準備
			pStmt = conn.prepareStatement(delItem);
			//----------SQL文実行----------


			pStmt.setInt(1, Integer.parseInt(eventId));
			pStmt.setInt(2,Integer.parseInt(itemId));
			result *= pStmt.executeUpdate();
			//--------------ID返却用






			//---------------------ここからエラー処理-----------------------
			if(result != 1) return false;
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e){
			e.printStackTrace();
			return false;
		} finally {
			//-----DB切断-----
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					return false;
				}
			}
		}
		//--------------------ここまでエラー処理---------------------------
		return true;

	}

	public  boolean deleteAuthorCandidateList(String eventId,String candidateId){//indexにeventIDを入力すると、そのeventIDのAutherRemarkを返す

		Connection conn = null;
		PreparedStatement  pStmt = null; // SQL文オブジェクト
		int result=1;
		try {
			//--------------JDBCドライバ読み込み----------
			Class.forName("org.postgresql.Driver");

			//----------------DBに接続---------------------
			conn=DriverManager.getConnection(URL,ROLENAME,PASSWORD);



			//INSERT文準備
			pStmt = conn.prepareStatement(delAutCand);
			//----------SQL文実行----------

				pStmt.setInt(1, Integer.parseInt(eventId));
				//pStmt.setInt(2, Integer.parseInt( candidateId));
				result *= pStmt.executeUpdate();

			//---------------------ここからエラー処理-----------------------
			if(result != 1) return false;
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e){
			e.printStackTrace();
			return false;
		} finally {
			//-----DB切断-----
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					return false;
				}
			}
		}
		//--------------------ここまでエラー処理---------------------------
		return true;

	}


	public  boolean deleteUserCandidateList(String eventId,String itemId,String userCandId){//indexにeventIDを入力すると、そのeventIDのAutherRemarkを返す

		Connection conn = null;
		PreparedStatement  pStmt = null; // SQL文オブジェクト
		int result=1;
		try {
			//--------------JDBCドライバ読み込み----------
			Class.forName("org.postgresql.Driver");

			//----------------DBに接続---------------------
			conn=DriverManager.getConnection(URL,ROLENAME,PASSWORD);
			/*	public static String insUseCand = "INSERT INTO usercandidate "
			+ "(EVENTID,ITEMID,PREFERREDFLAG,USERREMARKREMARK)"
			+ " VALUES(?,?,?,?)";
*/


			//INSERT文準備
			pStmt = conn.prepareStatement(delUseCand);
			//----------SQL文実行----------

				pStmt.setInt(1, Integer.parseInt(eventId));
				pStmt.setInt(2,Integer.parseInt(itemId));
				pStmt.setInt(3, Integer.parseInt(userCandId));
				result *= pStmt.executeUpdate();

			//---------------------ここからエラー処理-----------------------
			if(result != 1) return false;
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e){
			e.printStackTrace();
			return false;
		} finally {
			//-----DB切断-----
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
					return false;
				}
			}
		}
		//--------------------ここまでエラー処理---------------------------
		return true;

	}
}
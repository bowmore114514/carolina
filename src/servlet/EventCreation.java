package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EventDAO;
import model.AuthorCandidate;
import model.Event;

/**
 * Servlet implementation class EventCreation
 */
@WebServlet("/EventCreation")
public class EventCreation extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventCreation() {
        super();
        // TODO Auto-generated constructor stub
    }




	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);


		//-------------------リクエストパラメータの取得-------------------//
		request.setCharacterEncoding("UTF-8");
		String autherName = request.getParameter("authername"); //イベント作成者
		String eventName = request.getParameter("eventname"); 		//イベント名
		String autherRemark = request.getParameter("autherRemark");	//備考
		String autherPass = request.getParameter("autherpass");		//イベント作成者パスワード
		int eventOpenFlag = Integer.parseInt(request.getParameter("eventopenflag")); //イベント公開・非公開


		//候補日-----------------------------
		Calendar candiDateCal = Calendar.getInstance(); 			//Calendar作成
		ArrayList<Calendar> candidate = new ArrayList<Calendar>();	//ArrayList<Calendar>作成

		ArrayList<String> candiRemark = new ArrayList<String>();
		String candiRemarkStr = "NO DATA";

		int year , month , date , hour , minute = -1;	//初期化

		//パラメータの取得
		for (int i = 0; i < 5; i++){
			year = Integer.parseInt(request.getParameter("year" + i));
			month = Integer.parseInt(request.getParameter("month" + i));
			date = Integer.parseInt(request.getParameter("date" + i));
			hour = Integer.parseInt(request.getParameter("hour" + i));
			minute = Integer.parseInt(request.getParameter("minute") + i);
			candiRemarkStr = request.getParameter("candiremark" + i);


			//いずれか一つでも初期化子のままだったら
			if(year == -1 || month == -1 || date == -1 || hour == -1 || minute == -1){
				//スルー

			}else{  //全て入力されていたらCalendarにセットしArrayListに積む
			candiDateCal.set(year, month, date, hour, minute);

			candidate.add(candiDateCal);
			candiRemark.add(candiRemarkStr);

			}
		}
		//候補日取得終了---------------------





		//締切日-----------------------------
		Calendar deadline = Calendar.getInstance(); //Calendarの作成

		int deadlineYear , deadlineMonth , deadlineDate , deadlinehour , deadlineMinute= -1; //初期化

		deadlineYear = Integer.parseInt(request.getParameter("deadlineyear"));
		deadlineMonth = Integer.parseInt(request.getParameter("deadlinemonth"));
		deadlineDate = Integer.parseInt(request.getParameter("deadlinedate"));
		deadlinehour = Integer.parseInt(request.getParameter("deadlinehour"));
		deadlineMinute = Integer.parseInt(request.getParameter("deadlineminute"));

		deadline.set(deadlineYear, deadlineMonth, deadlineDate, deadlinehour, deadlineMinute); //Calendarにセット
		//締切日取得終了---------------------



		//-------------------リクエストパラメータ終了-------------------//

		Calendar registDay = Calendar.getInstance(); //投稿日時

		//初期化
		String eventId = "NO DATA";
		Calendar determinedDay = Calendar.getInstance(); //確定日時
		int determinedFlag = 0; //イベント確定のフラグ
		String eventUrl = "NO DATA";
		Calendar updatedate = Calendar.getInstance();//イベント最終更新日時（書き込みや編集、確定した際）

		//イベントインスタンス生成
		Event event = new Event(eventId, eventName, autherRemark, autherName, autherPass,
				eventUrl, deadline, determinedDay, registDay, updatedate,
				determinedFlag, eventOpenFlag);



		//----------------DAO----------------
		EventDAO dao = new EventDAO();

		//Event Table
		int intEventId = dao.insertEvent(event);//引数にいろいろいれる

		//StringのeventIdを編集する
		eventId = String.valueOf(intEventId);


		//Candidate

		String autherCandidateId = "-1";

		for (int i = 0; i < candidate.size(); i++){

			//分解
			Calendar autherCandidateCal = candidate.get(i);
			String autherCandidateRemark = candiRemark.get(i);

			AuthorCandidate authorCandidate = new AuthorCandidate(eventId, autherCandidateId, autherCandidateCal,
					autherCandidateRemark);

			//candidate Table insert
			dao.insertAuthorCandidateList(authorCandidate);

		}

		//----------------DAO終了----------------


		//eventId
		event.setEventId(eventId);


		//URL
		Event.createEventPageUrl(event);


		request.setAttribute("event",event);


		//フォワード(イベント作成決定後のページ）
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/FinishCreating");
		dispatcher.forward(request, response);
		return;
	}

}

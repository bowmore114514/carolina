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
import model.Event;
import model.Item;
import model.UserCandidate;

/**
 * Servlet implementation class Update
 */
@WebServlet("/Update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Update() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);

		// リクエストパラメータの取得 （判別用）
		request.setCharacterEncoding("UTF-8");
		int action = Integer.parseInt("action");
		String eventId = request.getParameter("eventId");

		// DAO
		EventDAO dao = new EventDAO();

		// 1:イベント情報の更新
		// 11:一般投稿者情報の更新

		if (action == 1) {

			// -------------------リクエストパラメータの取得-------------------//
			// request.setCharacterEncoding("UTF-8");
			//String eventId = request.getParameter("eventId"); //イベントId
			String autherName = request.getParameter("authername"); // イベント作成者
			String eventName = request.getParameter("eventname"); // イベント名
			String autherRemark = request.getParameter("autherRemark"); // 備考
			//String autherPass = request.getParameter("autherpass"); // イベント作成者パスワード
			//String eventUrl = request.getParameter("eventUrl"); //イベントURL
			int eventOpenFlag = Integer.parseInt(request.getParameter("eventopenflag")); // イベント公開・非公開


			// 締切日-----------------------------
			Calendar deadline = Calendar.getInstance(); // Calendarの作成

			int deadlineYear, deadlineMonth, deadlineDate = -1; // 初期化

			deadlineYear = Integer.parseInt(request.getParameter("deadlineyear"));
			deadlineMonth = Integer.parseInt(request.getParameter("deadlinemonth"));
			deadlineDate = Integer.parseInt(request.getParameter("deadlinedate"));

			deadline.set(deadlineYear, deadlineMonth, deadlineDate); // Calendarにセット
			// 締切日取得終了---------------------

			// -------------------リクエストパラメータ終了-------------------//




			// ------------------------------DAO-----------------------------//

			//EventTableのデータを取得
			ArrayList<Event> eventList = dao.getEventList();

			//初期化
			String autherPass = "NO DATA";
			String eventUrl = "NO DATA";
			Calendar determinedDay = Calendar.getInstance();
			Calendar registDay = Calendar.getInstance();
			int determinedFlag = -1;


			for (Event data : eventList){
				if (eventId == data.getEventId()){

					autherPass = data.getauthorPass();
					eventUrl = data.getEventUrl();
					determinedDay = data.getDeterminedDay();
					registDay = data.getRegistDay();
					determinedFlag = data.getDeterminedFlag();

					break;
				}
			}


			//更新日時
			Calendar updatedate = Calendar.getInstance();


			//Eventインスタンス生成
			Event event = new Event(eventId, eventName, autherRemark, autherName, autherPass,
					eventUrl, deadline, determinedDay, registDay, updatedate,
					determinedFlag, eventOpenFlag);


			//EventTable update
			dao.updateEvent(event);

			// ----------------------------DAO終了---------------------------//



			// 一般投稿者
		}else if (action == 11) {

			// -------------------リクエストパラメータの取得-------------------//
			String itemId = request.getParameter("itemid");
			String userName = request.getParameter("username");
			String userRemark = request.getParameter("userremark");
			String userPass = request.getParameter("userPass");

			// 参加・不参加・未定　リクエストパラメータ取得とDAO

			String preferredFlagStr = "NO DATA";
			int preferredFlagSet = -1;

			String candidateRemark = "NO DATA";
			String userCandidateId = "NO DATA";

			for (int i = 0; i < 5; i++) {
				preferredFlagStr = request.getParameter("preferredFlag" + i);
				candidateRemark = request.getParameter("candidateremark" + i);


				if (preferredFlagStr == null || preferredFlagStr == "") {

				} else {
					preferredFlagSet = Integer.parseInt(preferredFlagStr);


					UserCandidate userCandidate = new UserCandidate(eventId, itemId, userCandidateId,
							candidateRemark, preferredFlagSet);

					//DAO
					dao.updateUserCandidateList(userCandidate);
				}

			}


			//投稿日時（更新日時）
			Calendar userRegistDay = Calendar.getInstance();

			//itemインスタンス生成
			Item item = new Item(eventId, itemId, userName, userPass, userRemark,userRegistDay);



			//DAO
			//Item Table update
			dao.updateItem(item);


			// ----------------------------DAO終了---------------------------//

		}

		// フォワード 更新完了ページ
		RequestDispatcher dispatcher = request.getRequestDispatcher("");
		dispatcher.forward(request, response);
		return;

	}

}

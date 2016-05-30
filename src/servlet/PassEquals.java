package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EventDAO;
import model.AuthorCandidate;
import model.Event;
import model.Item;

/**
 * Servlet implementation class PassEquals
 */
@WebServlet("/PassEquals")
public class PassEquals extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PassEquals() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);


		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		int action = Integer.parseInt( request.getParameter("action")); //この値によってイベント作成者か参加者かマスターかを判定する
		String Pass = request.getParameter("pass"); //入力されたパスワード
		String eventId = request.getParameter("eventId");
		String eventUrl = request.getParameter("pageid");//とれるかわからん

		EventDAO dao = new EventDAO();




		//action 1:イベント作成者2:一般投稿者  0:master
		if (action == 1){

			//EventTableからeventIdに対応するレコードを取得
			ArrayList<Event> eventList = dao.getEventList(); //全EventTable
			ArrayList<Event> event = new ArrayList<Event>(); //対応するeventIdのレコードを格納

			for (Event data : eventList){
				if (eventId == data.getEventId()){
					event.add(data);
				}
			}

			String PassCheck = event.get(0).getauthorPass();//設定されたパスワードを取得


			if (PassCheck == Pass || Pass == "master"){ //パスワードがあっていた場合


				//候補日情報
				ArrayList<AuthorCandidate> autherCandidateList = dao.getAuthorCandidate();
				ArrayList<AuthorCandidate> autherCandidate = new ArrayList<AuthorCandidate>();




				//イベント情報
				request.setAttribute("event", event);

				//フォワード イベント情報編集ページ
				RequestDispatcher dispatcher = request.getRequestDispatcher("");
				dispatcher.forward(request, response);
				return;

			}else{ //パスワードが間違っていた場合


				//フォワード イベントページに
				RequestDispatcher dispatcher = request.getRequestDispatcher("");
				dispatcher.forward(request, response);
				return;
			}





		}else if (action == 2 || Pass == "master"){ //一般投稿者

			//リクエストパラメータからItemIdを取得
			String ItemId = request.getParameter("itemid");

			Item item = (Item) dao.selectItem(eventId,ItemId);

			String PassCheck = item.getUserPass();

			request.setAttribute("item", item);


			if(PassCheck == Pass){

				//フォワード 編集ページに
				RequestDispatcher dispatcher = request.getRequestDispatcher("");
				dispatcher.forward(request, response);
				return;

			}else{

				//フォワード イベントページに
				RequestDispatcher dispatcher = request.getRequestDispatcher("");
				dispatcher.forward(request, response);
				return;
			}


		}else if (action == 0){

			String masterKey = Event.masterKey;

			if(Pass == masterKey){

				request.setAttribute("master", masterKey);

				//フォワード 一覧ページに
				RequestDispatcher dispatcher = request.getRequestDispatcher("/EventListView");
				dispatcher.forward(request, response);
				return;

			}else{

				//フォワード indexに
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
				return;
			}

		}
	}

}

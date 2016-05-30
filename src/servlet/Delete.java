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
import model.Item;
import model.UserCandidate;

/**
 * Servlet implementation class Delete
 */
@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);

		//リクエストパラメータの取得
		int action = Integer.parseInt(request.getParameter("action"));
		String eventId = request.getParameter("eventid");

		//dao
		EventDAO dao = new EventDAO();

		//ItemTable
		ArrayList<Item> itemList = dao.get();

		//userCandidateの中身をすべて取ってくる
		ArrayList<UserCandidate> userCandidateList = dao.getUserCandidateList();



		//2:イベント情報
		//22:一般投稿者情報

		if (action == 2){
			//イベント情報、一般投稿者情報すべて削除

			//イベント情報-------------------

			//AuthorCandidateTable削除
			ArrayList<AuthorCandidate> autherCandidateList = dao.getAuthorCandidate();

			for (AuthorCandidate data : autherCandidateList){
				if(eventId == data.getEventId()){
					dao.deleteAuthorCandidateList(eventId, data.getAutherCandidateId());
				}
			}

			//EventTable削除
			dao.deleteEvent(eventId);

			//イベント情報終了---------------



			//一般投稿者情報-----------------

			//UserCandidateTable削除
			for (UserCandidate userCandidate : userCandidateList){
				if(eventId == userCandidate.getEventId()){
					dao.deleteUserCandidateList(eventId, userCandidate.getItemId(), userCandidate.getUserCandidateId());
				}
			}

			//ItemTable削除
			for (Item item : itemList){
				if(eventId == item.getEventId()){
					dao.deleteItem(eventId, item.getItemId());
				}
			}

			//一般投稿者情報終了-------------





		}else if (action == 22){
			//特定の一般投稿者情報のみを削除

			//リクエストパラメータ
			String itemId = request.getParameter("itemid");

			//UserCandidateTable
			for (UserCandidate userCandidate : userCandidateList){
				if (eventId == userCandidate.getEventId() && itemId == userCandidate.getItemId()){
					dao.deleteUserCandidateList(eventId, itemId, userCandidate.getUserCandidateId());
				}
			}

			//ItemTable
			dao.deleteItem(eventId, itemId);


		}


		//フォワード 削除完了ページ
		RequestDispatcher dispatcher = request.getRequestDispatcher("");
		dispatcher.forward(request, response);
		return;

	}

}

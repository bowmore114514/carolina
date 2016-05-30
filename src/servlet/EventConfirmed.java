package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EventDAO;
import model.AuthorCandidate;

/**
 * Servlet implementation class EventConfirmed
 */
@WebServlet("/EventConfirmed")
public class EventConfirmed extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventConfirmed() {
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

		String eventId = request.getParameter("eventId");
		int Flag = Integer.parseInt(request.getParameter("determinedflag"));


		//更新日時
		Calendar updatedate = Calendar.getInstance();


		//イベント確定のフラグ
		int determinedFlag = 1;



		//DAO
		EventDAO dao = new EventDAO();

		ArrayList<AuthorCandidate> authorCandidate = (ArrayList<AuthorCandidate>)dao.selectAuthorCandidate(eventId);

		//確定日時
		//Calendar determinedDay = authorCandidate.get(Flag -1).getAutherCandidate();

		//dao.insertEvent(eventId,determinedDay,determinedFlag);//更新日時とかイベント確定のフラグを入れる



	}

}

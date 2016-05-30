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
import model.Event;



/**
 * Servlet implementation class EventListView
 */
@WebServlet("/EventListView")
public class EventListView extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventListView() {
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
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=" + "UTF-8");
		response.getWriter().append("Served at: ").append(request.getContextPath());


		String masterKey = (String) request.getAttribute("masterKey");


		EventDAO dao = new EventDAO();

		ArrayList<Event> eventList = dao.getEventList();

		request.setAttribute("masterKey", masterKey);
		request.setAttribute("eventList", eventList);


		// フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/EventList.jsp");
		dispatcher.forward(request, response);
		return;

	}
}

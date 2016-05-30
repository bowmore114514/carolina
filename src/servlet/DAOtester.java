package servlet;

import java.io.IOException;
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
import model.Item;
import model.UserCandidate;

/**
 * Servlet implementation class DAOtester
 */
@WebServlet("/DAOtester")
public class DAOtester extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DAOtester() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		Calendar c = Calendar.getInstance();
		String s ="10";
		int n=10;
		EventDAO dao = new EventDAO();
		Event event= new Event(s,s,s,s,s,s,c,c,c,c,n,n);
		Item item = new Item(s,s,s,s,s,c);
		UserCandidate uc = new UserCandidate(s,s,s,s,n);
		AuthorCandidate ac = new AuthorCandidate(s,s,c,s);
		for(int i=0;i<5000;i++){
/*		dao.updateAuthorCandidateList(ac);
		dao.updateEvent(event);
		dao.updateItem(item);
		dao.updateUserCandidateList(uc);*/
			dao.insertAuthorCandidateList(ac);
		}
		//request.setAttribute(, );

		RequestDispatcher dispatcher
		= request.getRequestDispatcher("/WEB-INF/test.jsp");
		dispatcher.forward(request,response);
	}

}

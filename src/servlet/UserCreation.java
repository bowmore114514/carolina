package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserCreation
 */
@WebServlet("/UserCreation")
public class UserCreation extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserCreation() {
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

		//ユーザー投稿情報の登録
		// -------------------リクエストパラメータの取得-------------------//
		request.setCharacterEncoding("UTF-8");
		String itemId = request.getParameter("itemid");
		String userName = request.getParameter("username");
		String userRemark = request.getParameter("userremark");

		// 参加・不参加・未定
		ArrayList<Integer> preferredFlag = new ArrayList<Integer>();
		ArrayList<String> candidateUserRemark = new ArrayList<String>();

		String preferredFlagStr = "NO DATA";
		int preferredFlagInt = -1;

		String candidateUserRemarkStr = "NO DATA";

		for (int i = 0; i < 5; i++) {
			preferredFlagStr = request.getParameter("preferredFlag" + i);

			if (preferredFlagStr == null || preferredFlagStr == "") {

			} else {
				preferredFlagInt = Integer.parseInt(preferredFlagStr);

				preferredFlag.add(preferredFlagInt);
				candidateUserRemark.add(candidateUserRemarkStr);
			}
		}

		// -------------------リクエストパラメータ終了-------------------//

		// ------------------------------DAO-----------------------------//

		// ----------------------------DAO終了---------------------------//

		}

		// フォワード 更新完了ページ
		RequestDispatcher dispatcher = request.getRequestDispatcher("");
		dispatcher.forward(request, response);
		return;
	}

}

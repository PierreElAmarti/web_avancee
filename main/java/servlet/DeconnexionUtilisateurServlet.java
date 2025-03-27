package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DeconnexionUtilisateurServlet
 */
@WebServlet("/DeconnexionUtilisateurServlet")
public class DeconnexionUtilisateurServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public static final String VUE_ACCEUIL = "/AcceuilServlet";
	public static final String ATT_ID = "id";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeconnexionUtilisateurServlet()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		HttpSession vSession = request.getSession();
		if (vSession.getAttribute(ATT_ID) != null)
		{
			vSession.setAttribute(ATT_ID, null);
		}
		this.getServletContext().getRequestDispatcher(VUE_ACCEUIL).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

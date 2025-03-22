package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class InscriptionUtilisateurServlet
 */
@WebServlet("/InscriptionUtilisateurServlet")
public class InscriptionUtilisateurServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public static final String ATT_UTILISATEUR = "Utilisateur";
	public static final String ATT_FORM = "form";
	public static final String ATT_ID = "id";

	public static final String VUE_ACCEUIL = "/WEB-INF/Acceuil.jsp";
	public static final String VUE_FORM = "/WEB-INF/utilisateur/InscriptionUtilisateur.jsp";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InscriptionUtilisateurServlet()
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
		HttpSession vSession = request.getSession();
		if(vSession.getAttribute(ATT_ID) != null) {
			this.getServletContext().getRequestDispatcher(VUE_ACCEUIL).forward(request, response);
			return;
		}
		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession vSession = request.getSession();
		// TODO Code pour créer un utilisateur en bdd
		if(vSession.getAttribute(ATT_ID) != null) {
			this.getServletContext().getRequestDispatcher(VUE_ACCEUIL).forward(request, response);
			return;
		}
		this.getServletContext().getRequestDispatcher(VUE_ACCEUIL).forward(request, response);

	}

}

package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOFactory;
import dao.UtilisateurDao;
import model.Utilisateur;
import service.ConnexionUtilisateurService;

/**
 * Servlet implementation class ConnectionUtilisateurServlet
 */
@WebServlet("/ConnexionUtilisateurServlet")
public class ConnexionUtilisateurServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_UTILISATEUR = "Utilisateur";
	public static final String ATT_FORM = "form";
	public static final String ATT_ID = "id";

	public static final String VUE_ACCEUIL = "/WEB-INF/Acceuil.jsp";
	public static final String VUE_FORM = "/WEB-INF/utilisateur/ConnexionUtilisateur.jsp";

	private UtilisateurDao utilisateurDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConnexionUtilisateurServlet()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException
	{
		this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession vSession = request.getSession();
		if (vSession.getAttribute(ATT_ID) != null)
		{
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
		// TODO Code pour vérifier si l'utilisateur en bdd puis le connecter ou non
		if (vSession.getAttribute(ATT_ID) != null)
		{
			this.getServletContext().getRequestDispatcher(VUE_ACCEUIL).forward(request, response);
			return;
		}

		ConnexionUtilisateurService form = new ConnexionUtilisateurService(utilisateurDao);

		Utilisateur utilisateur = form.getUtilisateur(request);

		if (form.getErreurs().isEmpty())
		{
			vSession.setAttribute(ATT_ID, utilisateur.getId());
			this.getServletContext().getRequestDispatcher(VUE_ACCEUIL).forward(request, response);
		}
		else
		{
			request.setAttribute(ATT_FORM, form);
			this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
		}

		this.getServletContext().getRequestDispatcher(VUE_ACCEUIL).forward(request, response);
	}

}

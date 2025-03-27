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
import service.CompteService;

/**
 * Servlet implementation class CompteServlet
 */
@WebServlet("/CompteServlet")
public class CompteServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_UTILISATEUR = "utilisateur";
	public static final String ATT_FORM = "form";
	public static final String ATT_ID = "id";

	public static final String VUE_CONNEXION = "/ConnexionUtilisateurServlet";
	public static final String VUE_FORM = "/WEB-INF/utilisateur/Compte.jsp";

	private UtilisateurDao utilisateurDao;

	public void init() throws ServletException
	{
		this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CompteServlet()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession vSession = request.getSession();
		if (vSession.getAttribute(ATT_ID) == null)
		{
			this.getServletContext().getRequestDispatcher(VUE_CONNEXION).forward(request, response);
			return;
		}
		Utilisateur utilisateur = utilisateurDao.trouver((Long) (vSession.getAttribute(ATT_ID)));
		request.setAttribute(ATT_UTILISATEUR, utilisateur);
		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession vSession = request.getSession();
		if (vSession.getAttribute(ATT_ID) == null)
		{
			this.getServletContext().getRequestDispatcher(VUE_CONNEXION).forward(request, response);
			return;
		}
		Utilisateur utilisateur = utilisateurDao.trouver((Long) (vSession.getAttribute(ATT_ID)));

		CompteService form = new CompteService(utilisateurDao);

		utilisateur = form.updateUtilisateur(request, utilisateur);

		if (form.getErreurs().isEmpty())
		{
			utilisateurDao.modifier(utilisateur);
			request.setAttribute(ATT_UTILISATEUR, utilisateur);
			this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
		}
		else
		{
			request.setAttribute(ATT_FORM, form);
			request.setAttribute(ATT_UTILISATEUR, utilisateur);
			this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
		}
	}

}

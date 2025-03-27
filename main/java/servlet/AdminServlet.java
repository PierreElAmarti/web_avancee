package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOFactory;
import dao.PartieDao;
import dao.UtilisateurDao;
import model.PartieMaitre;
import model.Utilisateur;
import service.ConnexionUtilisateurService;
import service.CreationPartieService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String ATT_UTILISATEUR = "utilisateur";
	public static final String ATT_ID = "id";
    public static final String ATT_PARTIE = "partie";
	public static final String ATT_FORM = "form";

	public static final String VUE_FORM = "/WEB-INF/Admin.jsp";
	public static final String VUE_ACCEUIL = "/AcceuilServlet";
	public static final String VUE_PROFIL = "/ProfilServlet";

	public static final String CONF_DAO_FACTORY = "daofactory";
	private UtilisateurDao utilisateurDao;
	private PartieDao partieDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init() throws ServletException
	{
		this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
		this.partieDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPartieDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 HttpSession session = request.getSession();

		if (session.getAttribute(ATT_ID) != null)
		{
			Utilisateur user = utilisateurDao.trouver( (Long) session.getAttribute(ATT_ID) );
			if(user.getPermission() < 10) {
				this.getServletContext().getRequestDispatcher(VUE_PROFIL).forward(request, response);
				return ;
			}
       }
       
       /* Et enfin (ré)enregistrement de la map en session */
		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession vSession = request.getSession();
		// TODO Code pour vérifier si l'utilisateur en bdd puis le connecter ou non
		if (vSession.getAttribute(ATT_ID) == null)
		{
			this.getServletContext().getRequestDispatcher(VUE_ACCEUIL).forward(request, response);
			return;
		}

		CreationPartieService form = new CreationPartieService(partieDao, utilisateurDao);

		PartieMaitre partie = form.creationPartie(request);

		if (form.getErreurs().isEmpty())
		{
			partieDao.creer(partie);
			this.getServletContext().getRequestDispatcher(VUE_ACCEUIL).forward(request, response);
		}
		else
		{
			request.setAttribute(ATT_PARTIE, partie);
			request.setAttribute(ATT_FORM, form);
			this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
		}
	}

}

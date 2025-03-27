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

/**
 * Servlet implementation class ProfilServlet
 */
@WebServlet("/ProfilServlet")
public class ProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String ATT_UTILISATEUR = "utilisateur";
	public static final String ATT_ID = "id";
    public static final String ATT_PARTIES = "parties";

	public static final String VUE_PROFIL = "/WEB-INF/Profil.jsp";

	public static final String CONF_DAO_FACTORY = "daofactory";
	private UtilisateurDao utilisateurDao;
	private PartieDao partieDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfilServlet() {
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
	        List<PartieMaitre> parties = partieDao.historique(user);
			user.setClassement(utilisateurDao.classement(user));
			request.setAttribute( ATT_UTILISATEUR, user );
	        request.setAttribute( ATT_PARTIES, parties );
        }
        
        /* Et enfin (ré)enregistrement de la map en session */
		this.getServletContext().getRequestDispatcher(VUE_PROFIL).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOFactory;
import dao.UtilisateurDao;
import model.Utilisateur;

/**
 * Servlet implementation class AcceuilServlet
 */
@WebServlet("/AcceuilServlet")
public class AcceuilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String ATT_UTILISATEUR = "utilisateur";
	public static final String ATT_ID = "id";
    public static final String ATT_UTILISATEURS = "utilisateurs";

	public static final String VUE_ACCEUIL = "/WEB-INF/Acceuil.jsp";

	public static final String CONF_DAO_FACTORY = "daofactory";
	private UtilisateurDao utilisateurDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcceuilServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init() throws ServletException
	{
		this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 HttpSession session = request.getSession();
         List<Utilisateur> utilisateurs = utilisateurDao.lister();

 		if (session.getAttribute(ATT_ID) != null)
 		{
 			request.setAttribute( ATT_UTILISATEUR, utilisateurDao.trouver( (Long) session.getAttribute(ATT_ID) ) );
         }
         
         /* Et enfin (ré)enregistrement de la map en session */
         request.setAttribute( ATT_UTILISATEURS, utilisateurs );
		this.getServletContext().getRequestDispatcher(VUE_ACCEUIL).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

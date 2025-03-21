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
import dao.UtilisateurDao;
import model.Utilisateur;

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

	public static final String VUE_SUCCES = "/WEB-INF/Acceuil.jsp";
	public static final String VUE_FORM = "/WEB-INF/utilisateur/ConnexionUtilisateur.jsp";

    private UtilisateurDao     utilisateurDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConnexionUtilisateurServlet()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void init() throws ServletException{
		this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		Utilisateur test = new Utilisateur();
		test.setId(3L);
		test.setAdresseMail("test.test@test.test");
		test.setMotsDePasse("testMdp");
		test.setNomUtilisateur("updateUsername");
		test.setElo(15);
		test.setPermission(1);
		test.setQuestionSecrete(2);	
		test.setReponseSecrete("testResponseSecrete");
//		this.utilisateurDao.supprimer(test);
//		this.utilisateurDao.creer(test);
//		List<Utilisateur> temp = this.utilisateurDao.lister();
//		for(Utilisateur tempUtilisateur: temp) {
//			System.out.println(tempUtilisateur.getNomUtilisateur());
//		}
//		Utilisateur temp = this.utilisateurDao.trouver(1);
//		System.out.println(((temp != null)?temp.getNomUtilisateur(): "rien"));
//		this.utilisateurDao.modifier(test);
//		System.out.println(((this.utilisateurDao.nomUtilisateurDejaPris("updateUsername"))? "Pris" : "Pas Pris"));
//		Utilisateur test1 = this.utilisateurDao.getByUsernameOrMail("test.test@.test");
//		System.out.println(((test1 != null)? "existe" : "existe pas"));
		HttpSession vSession = request.getSession();
		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession vSession = request.getSession();

		this.getServletContext().getRequestDispatcher(VUE_SUCCES).forward(request, response);
	}

}

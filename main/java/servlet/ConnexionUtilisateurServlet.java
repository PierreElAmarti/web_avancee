package servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
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
import model.Partie;
import model.PartieMaitre;
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
	public static final String ATT_ID = "id";

	public static final String VUE_ACCEUIL = "/WEB-INF/Acceuil.jsp";
	public static final String VUE_FORM = "/WEB-INF/utilisateur/ConnexionUtilisateur.jsp";

    private UtilisateurDao     utilisateurDao;
    private PartieDao 		   partieDao;

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
		this.partieDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPartieDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
//		PartieMaitre tempPartieMaitre = new PartieMaitre();
//		tempPartieMaitre.setId(14L);
//		tempPartieMaitre.setTemps("12,12");
//		tempPartieMaitre.setDate(new Date(0));
//		List<Partie> tempParties = new ArrayList<Partie>();
//		for(int i = 0; i < 5; i++) {
//			Utilisateur utilisateur = new Utilisateur();
//			utilisateur.setId(1L);
//			utilisateur.setAdresseMail("test.test@test.test");
//			utilisateur.setElo(15);
//			utilisateur.setMotsDePasse("testMdp");
//			utilisateur.setNomUtilisateur("testUsername");
//			utilisateur.setPermission(1);
//			utilisateur.setQuestionSecrete(2);
//			utilisateur.setReponseSecrete("testResponseSecrete");
//			Partie tempPartie = new Partie();
//			tempPartie.setGagnant((i%5 == 0));
//			tempPartie.setScore(i*5L);
//			tempPartie.setUtilisateur(utilisateur);
//			tempParties.add(tempPartie);
//		}
//		tempPartieMaitre.setPartieFils(tempParties);
		
		List<PartieMaitre> temp = this.partieDao.lister();
		
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
		if(vSession.getAttribute(ATT_ID) != null) {
			this.getServletContext().getRequestDispatcher(VUE_ACCEUIL).forward(request, response);
			return;
		}
		this.getServletContext().getRequestDispatcher(VUE_ACCEUIL).forward(request, response);
	}

}

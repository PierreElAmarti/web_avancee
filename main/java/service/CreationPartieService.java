package service;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import dao.PartieDao;
import dao.UtilisateurDao;
import model.Partie;
import model.PartieMaitre;

public class CreationPartieService {

	private static final String CHAMP_DATE = "date";
	private static final String CHAMP_TEMPS = "temps";
	private static final String CHAMP_GAGNANT = "gagnant";
	private static final String CHAMP_USERNAME1 = "nomUtilisateur1";
	private static final String CHAMP_USERNAME2 = "nomUtilisateur2";
	private static final String CHAMP_SCORE1 = "score1";
	private static final String CHAMP_SCORE2 = "score2";

	private Map<String, String> erreurs = new HashMap<String, String>();

	private UtilisateurDao utilisateurDao;
	private PartieDao partieDao;

	public CreationPartieService(PartieDao partieDao, UtilisateurDao utilisateurDao)
	{
		super();
		this.utilisateurDao = utilisateurDao;
		this.partieDao = partieDao;
	}

	public PartieMaitre creationPartie(HttpServletRequest request)
	{
		String date = getValeurChamp(request, CHAMP_DATE);
		String temps = getValeurChamp(request, CHAMP_TEMPS);
		String gagnant = getValeurChamp(request, CHAMP_GAGNANT);
		String username1 = getValeurChamp(request, CHAMP_USERNAME1);
		String username2 = getValeurChamp(request, CHAMP_USERNAME2);
		String score1 = getValeurChamp(request, CHAMP_SCORE1);
		String score2 = getValeurChamp(request, CHAMP_SCORE2);
		
		PartieMaitre vRet = new PartieMaitre();

		try
		{
			validationDate(date);
		}
		catch (Exception e)
		{
			setErreur(CHAMP_DATE, e.getMessage());
		}

		try
		{
			validationTemps(temps);
		}
		catch (Exception e)
		{
			setErreur(CHAMP_TEMPS, e.getMessage());
		}

		try
		{
			validationUsername(username1);
		}
		catch (Exception e)
		{
			setErreur(CHAMP_USERNAME1, e.getMessage());
		}

		try
		{
			validationUsername(username2);
		}
		catch (Exception e)
		{
			setErreur(CHAMP_USERNAME2, e.getMessage());
		}

		try
		{
			validationScore(score1);
		}
		catch (Exception e)
		{
			setErreur(CHAMP_SCORE1, e.getMessage());
		}

		try
		{
			validationScore(score2);
		}
		catch (Exception e)
		{
			setErreur(CHAMP_SCORE2, e.getMessage());
		}

		if(date != null) {
			SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
			try {
				vRet.setDate((Date) format.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		vRet.setTemps(temps);
		List<Partie> listePartie = new ArrayList<Partie>();
		Partie partie1 = new Partie();
		if(score1 != null) {
			partie1.setScore(Long.parseLong(score1));
		}
		if(username1 != null) {
			partie1.setUtilisateur(utilisateurDao.getByUsernameOrMail(username1));
		}
		Partie partie2 = new Partie();
		if(score2 != null) {
			partie2.setScore(Long.parseLong(score2));
		}
		if(username2 != null) {
			partie2.setUtilisateur(utilisateurDao.getByUsernameOrMail(username2));
		}
		if(gagnant == "gagnant1") {
			partie1.setGagnant(true);
			partie2.setGagnant(false);
		}else {
			partie1.setGagnant(false);
			partie2.setGagnant(true);
		}
		listePartie.add(partie1);
		listePartie.add(partie2);
		vRet.setPartieFils(listePartie);
		
		return vRet;
	}

	private void validationUsername(String username) throws Exception
	{
		if (username != null)
		{
			if (username.length() < 2)
			{
				throw new Exception("Le nom d'utilisateur doit contenir au moins 2 caractères.");
			}
			else if (!utilisateurDao.nomUtilisateurDejaPris(username))
			{
				throw new Exception("ce nom d'utilisateur n'existe pas.");
			}
		}
		else
		{
			throw new Exception("Merci d'entrer un nom d'utilisateur.");
		}
	}

	private void validationScore(String score) throws Exception
	{
		if (score == null || Integer.parseInt(score) < 0)
		{
			throw new Exception("Le score saisie est invalide");
		}
	}

	private void validationDate(String date) throws Exception
	{
		if (date == null)
		{
			throw new Exception("Veuillez remplir le champ");
		}
	}

	private void validationTemps(String temps) throws Exception
	{
		if (temps == null )
		{
			throw new Exception("Veuillez remplir le champ");
		}
	}

	private void setErreur(String champ, String message)
	{
		erreurs.put(champ, message);
	}

	private static String getValeurChamp(HttpServletRequest request, String nomChamp)
	{
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0)
		{
			return null;
		}
		else
		{
			return valeur;
		}
	}

	public Map<String, String> getErreurs()
	{
		return erreurs;
	}
}

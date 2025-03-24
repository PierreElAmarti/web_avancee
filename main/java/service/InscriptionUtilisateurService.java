package service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.Utilisateur;

public class InscriptionUtilisateurService
{

	private static final String CHAMP_USERNAME = "nomUtilisateur";
	private static final String CHAMP_EMAIL = "email";
	private static final String CHAMP_MOT_DE_PASSE = "motDePasse";
	private static final String CHAMP_CONFIRMER_MOT_DE_PASSE = "confirmerMotDePasse";
	private static final String CHAMP_QUESTION_SECRETE = "questionSecrete";
	private static final String CHAMP_REPONSE_QUESTION_SECRETE = "reponseQuestionSecrete";

	private Map<String, String> erreurs = new HashMap<String, String>();

	Utilisateur creationUtilisateur(HttpServletRequest request)
	{
		Utilisateur vRet = null;

		return vRet;
	}

	public Map<String, String> getErreurs()
	{
		return erreurs;
	}
}

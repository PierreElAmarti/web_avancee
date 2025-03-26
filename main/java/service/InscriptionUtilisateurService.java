package service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCrypt;

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

	public Utilisateur creationUtilisateur(HttpServletRequest request)
	{
		String username = getValeurChamp(request, CHAMP_USERNAME);
		String email = getValeurChamp(request, CHAMP_EMAIL);
		String motDePasse = getValeurChamp(request, CHAMP_MOT_DE_PASSE);
		String confirmerMotDePasse = getValeurChamp(request, CHAMP_CONFIRMER_MOT_DE_PASSE);
		String questionSecrete = getValeurChamp(request, CHAMP_QUESTION_SECRETE);
		String reponseQuestionSecrete = getValeurChamp(request, CHAMP_REPONSE_QUESTION_SECRETE);

		Utilisateur vRet = new Utilisateur();

		try
		{
			validationUsername(username);
		}
		catch (Exception e)
		{
			setErreur(CHAMP_USERNAME, e.getMessage());
		}

		try
		{
			validationEmail(email);
		}
		catch (Exception e)
		{
			setErreur(CHAMP_EMAIL, e.getMessage());
		}

		try
		{
			validationMotDePasse(motDePasse);
		}
		catch (Exception e)
		{
			setErreur(CHAMP_MOT_DE_PASSE, e.getMessage());
		}

		try
		{
			validationConfirmerMotsDePasse(motDePasse, confirmerMotDePasse);
		}
		catch (Exception e)
		{
			setErreur(CHAMP_MOT_DE_PASSE, e.getMessage());
		}

		try
		{
			validationQuestionSecrete(questionSecrete);
		}
		catch (Exception e)
		{
			setErreur(CHAMP_QUESTION_SECRETE, e.getMessage());
		}

		try
		{
			validationReponseQuestionSecrete(reponseQuestionSecrete);
		}
		catch (Exception e)
		{
			setErreur(CHAMP_QUESTION_SECRETE, e.getMessage());
		}

		vRet.setNomUtilisateur(username);
		vRet.setAdresseMail(email);
		vRet.setMotsDePasse(BCrypt.hashpw(motDePasse, BCrypt.gensalt()));
		try
		{
			vRet.setQuestionSecrete(Integer.parseInt(questionSecrete));
		}
		catch (Exception e)
		{
			setErreur(CHAMP_QUESTION_SECRETE, "La question secrete est invalide");
		}
		vRet.setReponseSecrete(BCrypt.hashpw(reponseQuestionSecrete, BCrypt.gensalt()));
		vRet.setElo(1000);
		vRet.setPermission(1);

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
		}
		else
		{
			throw new Exception("Merci d'entrer un nom d'utilisateur.");
		}
	}

	private void validationEmail(String email) throws Exception
	{
		if (email != null)
		{
			if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)"))
			{
				throw new Exception("Merci de saisir une adresse mail valide.");
			}
		}
		else
		{
			throw new Exception("Merci d'entrer une adresse mail.");
		}

	}

	private void validationMotDePasse(String motDePasse) throws Exception
	{
		if (motDePasse != null)
		{
			if (motDePasse.length() < 8)
			{
				throw new Exception("Le mot de passe doit contenir au moins 8 caractères.");
			}
		}
		else
		{
			throw new Exception("Merci d'entrer un mot de passe.");
		}
	}

	private void validationConfirmerMotsDePasse(String motDePasse, String confirmer) throws Exception
	{
		if (confirmer != null)
		{
			if (!confirmer.equals(motDePasse))
			{
				throw new Exception("les deux mots de passe ne sont pas identique");
			}
		}
		else
		{
			throw new Exception("Merci de confirmer votre mot de passe.");
		}
	}

	private int validationQuestionSecrete(String questionSecrete) throws Exception
	{
		if (questionSecrete == null || Integer.parseInt(questionSecrete) <= 5)
		{
			throw new Exception("La question secrete est invalide");
		}
		return Integer.parseInt(questionSecrete);
	}

	private void validationReponseQuestionSecrete(String reponseQuestionSecrete) throws Exception
	{
		if (reponseQuestionSecrete == null)
		{
			throw new Exception("Merci d'entrer une réponse à la question secrete.");
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

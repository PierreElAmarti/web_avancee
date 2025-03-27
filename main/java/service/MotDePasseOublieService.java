package service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCrypt;

import dao.UtilisateurDao;
import model.Utilisateur;

public class MotDePasseOublieService extends FormService
{

	private static final String CHAMP_USERNAME = "nomUtilisateur";
	private static final String CHAMP_MOT_DE_PASSE = "motDePasse";
	private static final String CHAMP_CONFIRMER_MOT_DE_PASSE = "confirmerMotDePasse";
	private static final String CHAMP_QUESTION_SECRETE = "questionSecrete";
	private static final String CHAMP_REPONSE_QUESTION_SECRETE = "reponseQuestionSecrete";

	private UtilisateurDao utilisateurDao;

	private String nomUtilisateur;

	public MotDePasseOublieService(UtilisateurDao utilisateurDao)
	{
		super();
		this.utilisateurDao = utilisateurDao;
	}

	public Utilisateur changementUtilisateur(HttpServletRequest request, Utilisateur utilisateur)
	{
		nomUtilisateur = getValeurChamp(request, CHAMP_USERNAME);
		String motDePasse = getValeurChamp(request, CHAMP_MOT_DE_PASSE);
		String confirmerMotDePasse = getValeurChamp(request, CHAMP_CONFIRMER_MOT_DE_PASSE);
		String questionSecrete = getValeurChamp(request, CHAMP_QUESTION_SECRETE);
		String reponseQuestionSecrete = getValeurChamp(request, CHAMP_REPONSE_QUESTION_SECRETE);

		if (utilisateur == null)
		{
			try
			{
				validationEmailUsername(nomUtilisateur);
			}
			catch (Exception e)
			{
				setErreur(CHAMP_USERNAME, e.getMessage());
			}
			utilisateur = utilisateurDao.getByUsernameOrMail(nomUtilisateur);
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
			setErreur(CHAMP_CONFIRMER_MOT_DE_PASSE, e.getMessage());
		}

		try
		{
			validationQuestionSecrete(questionSecrete, utilisateur);
		}
		catch (Exception e)
		{
			setErreur(CHAMP_QUESTION_SECRETE, e.getMessage());
		}
		try
		{
			validationReponseQuestionSecrete(reponseQuestionSecrete, utilisateur);
		}
		catch (Exception e)
		{
			setErreur(CHAMP_QUESTION_SECRETE, e.getMessage());
		}

		if (motDePasse != null && utilisateur != null)
		{
			utilisateur.setMotsDePasse(BCrypt.hashpw(motDePasse, BCrypt.gensalt()));
		}

		return utilisateur;
	}

	private void validationEmailUsername(String emailUsername) throws Exception
	{
		if (emailUsername == null)
		{
			throw new Exception("Merci d'entrer une adresse mail ou un nom d'utilisateur.");
		}
		else if (!utilisateurDao.emailDejaPris(emailUsername) && !utilisateurDao.nomUtilisateurDejaPris(emailUsername))
		{
			throw new Exception("nom d'utilisateur ou adresse mail inconnu.");
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

	private void validationQuestionSecrete(String questionSecrete, Utilisateur utilisateur) throws Exception
	{
		if (questionSecrete == null || Integer.parseInt(questionSecrete) > 5 || Integer.parseInt(questionSecrete) < 0
				|| (utilisateur != null && Integer.parseInt(questionSecrete) != utilisateur.getQuestionSecrete()))
		{
			throw new Exception("La question secrete est invalide");
		}
	}

	private void validationReponseQuestionSecrete(String reponseQuestionSecrete, Utilisateur utilisateur)
			throws Exception
	{
		if (reponseQuestionSecrete == null)
		{
			throw new Exception("Merci d'entrer une réponse à la question secrete.");
		}
		else if (utilisateur != null && !BCrypt.checkpw(reponseQuestionSecrete, utilisateur.getReponseSecrete()))
		{
			throw new Exception("La question secrete est invalide");
		}
	}

	public String getNomUtilisateur()
	{
		return nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur)
	{
		this.nomUtilisateur = nomUtilisateur;
	}
}

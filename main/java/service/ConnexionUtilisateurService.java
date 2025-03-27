package service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCrypt;

import dao.UtilisateurDao;
import model.Utilisateur;

public class ConnexionUtilisateurService extends FormService
{
	private static final String CHAMP_USERNAME_EMAIL = "nomUtilisateur";
	private static final String CHAMP_MOT_DE_PASSE = "motDePasse";
	private static final String CONNEXION = "connexion";

	private UtilisateurDao utilisateurDao;

	private String nomUtilisateur;

	public ConnexionUtilisateurService(UtilisateurDao utilisateurDao)
	{
		super();
		this.utilisateurDao = utilisateurDao;
	}

	public Utilisateur getUtilisateur(HttpServletRequest request)
	{
		nomUtilisateur = getValeurChamp(request, CHAMP_USERNAME_EMAIL);
		String password = getValeurChamp(request, CHAMP_MOT_DE_PASSE);

		try
		{
			validationEmailUsername(nomUtilisateur);
		}
		catch (Exception e)
		{
			setErreur(CONNEXION, e.getMessage());
		}

		Utilisateur vRet = utilisateurDao.getByUsernameOrMail(nomUtilisateur);

		try
		{
			validationCompte(vRet, password);
		}
		catch (Exception e)
		{
			setErreur(CONNEXION, e.getMessage());
		}

		return vRet;
	}

	private void validationEmailUsername(String emailUsername) throws Exception
	{
		if (emailUsername == null)
		{
			throw new Exception("Merci d'entrer une adresse mail ou un nom d'utilisateur.");
		}
	}

	private void validationCompte(Utilisateur utilisateur, String password) throws Exception
	{
		if (utilisateur == null)
		{
			throw new Exception("Nom d'utilisateur ou mot de passe incorect");
		}
		else if (!BCrypt.checkpw(password, utilisateur.getMotsDePasse()))
		{
			throw new Exception("Nom d'utilisateur ou mot de passe incorect");
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

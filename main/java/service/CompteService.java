package service;

import javax.servlet.http.HttpServletRequest;

import dao.UtilisateurDao;
import model.Utilisateur;

public class CompteService extends FormService
{

	private static final String CHAMP_USERNAME = "nomUtilisateur";

	private UtilisateurDao utilisateurDao;

	public CompteService(UtilisateurDao utilisateurDao)
	{
		super();
		this.utilisateurDao = utilisateurDao;
	}

	public Utilisateur updateUtilisateur(HttpServletRequest request, Utilisateur utilisateur)
	{
		String username = getValeurChamp(request, CHAMP_USERNAME);

		try
		{
			validationUsername(username);
		}
		catch (Exception e)
		{
			setErreur(CHAMP_USERNAME, e.getMessage());
		}

		utilisateur.setNomUtilisateur(username);

		return utilisateur;
	}

	private void validationUsername(String username) throws Exception
	{
		if (username != null)
		{
			if (username.length() < 2)
			{
				throw new Exception("Le nom d'utilisateur doit contenir au moins 2 caractères.");
			}
			else if (utilisateurDao.nomUtilisateurDejaPris(username))
			{
				throw new Exception("ce nom d'utilisateur est déjà pris.");
			}
		}
		else
		{
			throw new Exception("Merci d'entrer un nom d'utilisateur.");
		}
	}
}

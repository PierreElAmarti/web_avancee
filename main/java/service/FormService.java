package service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class FormService
{
	private Map<String, String> erreurs = new HashMap<String, String>();

	public Map<String, String> getErreurs()
	{
		return erreurs;
	}

	protected void setErreur(String champ, String message)
	{
		erreurs.put(champ, message);
	}

	protected static String getValeurChamp(HttpServletRequest request, String nomChamp)
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
}

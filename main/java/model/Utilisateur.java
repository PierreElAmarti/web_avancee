package model;

public class Utilisateur
{
	private Long id;
	private String nomUtilisateur;
	private String adresseMail;
	private String motDePasse;
	private Integer elo;
	private Integer questionSecrete;
	private String reponseSecrete;
	private Integer permission;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getAdresseMail()
	{
		return adresseMail;
	}

	public void setAdresseMail(String adresseMail)
	{
		this.adresseMail = adresseMail;
	}

	public String getMotsDePasse()
	{
		return motsDePasse;
	}

	public void setMotsDePasse(String motsDePasse)
	{
		this.motsDePasse = motsDePasse;
	}

	public Integer getElo()
	{
		return elo;
	}

	public void setElo(Integer elo)
	{
		this.elo = elo;
	}

	public Integer getQuestionSecrete()
	{
		return questionSecrete;
	}

	public void setQuestionSecrete(Integer questionSecrete)
	{
		this.questionSecrete = questionSecrete;
	}

	public String getReponseSecrete()
	{
		return reponseSecrete;
	}

	public void setReponseSecrete(String reponseSecrete)
	{
		this.reponseSecrete = reponseSecrete;
	}

	public Integer getPermission()
	{
		return permission;
	}

	public void setPermission(Integer permission)
	{
		this.permission = permission;
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

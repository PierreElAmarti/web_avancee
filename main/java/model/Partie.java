package model;

public class Partie {
	private Long id;
	private Long idMaitre;
	private Utilisateur utilisateur;
	private Long score;
	private boolean gagnant;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdMaitre() {
		return idMaitre;
	}
	public void setIdMaitre(Long idMaitre) {
		this.idMaitre = idMaitre;
	}
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur aUtilisateur) {
		this.utilisateur = aUtilisateur;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	public boolean isGagnant() {
		return gagnant;
	}
	public void setGagnant(boolean gagnant) {
		this.gagnant = gagnant;
	}
}

package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Partie {
	private Long id;
	private List<Utilisateur> utilisateurs;
	private List<Long> scores;
	private Date date;
	private String temps;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}
	public void setUtilisateurs(List<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}
	public List<Long> getScores() {
		return scores;
	}
	public void setScores(List<Long> scores) {
		this.scores = scores;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTemps() {
		return temps;
	}
	public void setTemps(String temps) {
		this.temps = temps;
	}
	
}

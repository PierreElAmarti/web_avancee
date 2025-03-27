package model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class PartieMaitre {
	private Long id;
	private List<Partie> partieFils;
	private Date date;
	private String temps;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Partie> getPartieFils() {
		return partieFils;
	}
	public void setPartieFils(List<Partie> aPartieFils) {
		this.partieFils = aPartieFils;
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

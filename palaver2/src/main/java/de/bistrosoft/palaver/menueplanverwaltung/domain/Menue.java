package de.bistrosoft.palaver.menueplanverwaltung.domain;

import java.util.List;

import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;

public class Menue {
	private Long id;
	private String name;
	private Mitarbeiter koch;
	private List<Rezept> rezepte;
	
	public Menue(Long id, String name, Mitarbeiter koch){
		this.id=id;
		this.name=name;
		this.koch=koch;
		rezepte=null;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Mitarbeiter getKoch() {
		return koch;
	}
	public void setKoch(Mitarbeiter koch) {
		this.koch = koch;
	}
	public List<Rezept> getRezepte() {
		return rezepte;
	}
	public void setRezepte(List<Rezept> rezepte) {
		this.rezepte = rezepte;
	}	
}

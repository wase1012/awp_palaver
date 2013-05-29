package de.bistrosoft.palaver.menueplanverwaltung.domain;



import java.util.List;


import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;

public class Menue {
	private Long id;
	private String name;
	private List<Rezept> rezepte;
	private Mitarbeiter koch;
	private String kochname;
	private Boolean hauptgericht;
	private Geschmack geschmack;
	private Menueart menueart;
	private boolean aufwand;
	private boolean favorit;
	
	public Menue(Long id, String name, String koch){
		this.id=id;
		this.name=name;
		this.kochname=koch;
	}
	public Menue(Long id, String name, String koch, Geschmack geschmack, Menueart menueart, Boolean aufwand, Boolean favorit){
		this.id=id;
		this.name=name;
		this.kochname=koch;
		this.geschmack = geschmack;
		this.menueart = menueart;
		this.aufwand = aufwand;
		this.favorit = favorit;
	}
	
	
	
	public Menue(Long id, String name, Mitarbeiter koch){
		this.id=id;
		this.name=name;
		this.koch=koch;
	}
	
	public Menue(Long id, String name, Mitarbeiter koch, Geschmack geschmack, Menueart menueart, Boolean aufwand, Boolean favorit){
		this.id=id;
		this.name=name;
		this.koch=koch;
		this.geschmack = geschmack;
		this.menueart = menueart;
		this.aufwand = aufwand;
		this.favorit = favorit;
				
	}
	
	public Menue(String name) {
		this.name=name;
	}
	
	public Menue(String name, Mitarbeiter koch) {
		this.name=name;
		this.koch=koch;
	}

	public Menue() {
	}

	public List<Rezept> getRezepte() {
		return rezepte;
	}

	public void setRezepte(List<Rezept> rezepte) {
		this.rezepte = rezepte;
	}

	public String getKochname() {
		return kochname;
	}

	public void setKochname(String kochname) {
		this.kochname = kochname;
	}
	
	public Boolean getHauptgericht() {
		return hauptgericht;
	}

	public void setHauptgericht(Boolean hauptgericht) {
		this.hauptgericht = hauptgericht;
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
	
	
	public Geschmack getGeschmack() {
		return this.geschmack;
	}

	public void setGeschmack(Geschmack geschmack) {
		this.geschmack = geschmack;
	}

	
	public Menueart getMenueart() {
		return this.menueart;
	}

	public void setMenueart(Menueart menueart) {
		this.menueart = menueart;
	}
	
	public boolean getAufwand() {
		return aufwand;
	}

	public void setAufwand(boolean aufwand) {
		this.aufwand = aufwand;
	}

	public boolean getFavorit() {
		return this.favorit;
	}

	public void setFavorit(boolean favorit) {
		this.favorit = favorit;
	}
	
	
	@Override
	public String toString() {
		return ""+id +"";
	}
	
}
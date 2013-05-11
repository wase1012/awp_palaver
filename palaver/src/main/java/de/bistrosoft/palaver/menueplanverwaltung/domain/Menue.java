package de.bistrosoft.palaver.menueplanverwaltung.domain;

import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;

public class Menue {
	private Long id;
	private String name;
	private Mitarbeiter koch;
	private String kochname;
	
	public Menue(Long id, String name, String koch){
		this.id=id;
		this.name=name;
		this.kochname=koch;
	}
	
	public Menue(Long id, String name, Mitarbeiter koch){
		this.id=id;
		this.name=name;
		this.koch=koch;
	}
	
	public Menue(String name) {
		this.name=name;
	}

	public Menue() {
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
	
}

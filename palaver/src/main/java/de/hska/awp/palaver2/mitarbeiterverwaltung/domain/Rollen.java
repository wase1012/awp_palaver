package de.hska.awp.palaver2.mitarbeiterverwaltung.domain;

import java.util.ArrayList;
import java.util.List;

public class Rollen implements java.io.Serializable {

	private static final long serialVersionUID = -3366000000412110979L;
	private Long id;
	private String name;
	private List<Mitarbeiter> mitarbeiters = new ArrayList<Mitarbeiter>();

	public Rollen() {
	}

	public Rollen(String name) {
		super();
		this.name = name;
	}
	
	public Rollen(Long id,String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Rollen(Long id, String name, List<Mitarbeiter> mitarbeiters) {
		super();
		this.id = id;
		this.name = name;
		this.mitarbeiters = mitarbeiters;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Mitarbeiter> getMitarbeiters() {
		return mitarbeiters;
	}

	public void setMitarbeiters(List<Mitarbeiter> mitarbeiters) {
		this.mitarbeiters = mitarbeiters;
	}
	
	public Rollen addMitarbeiter(Mitarbeiter mitarbeiter) {
		if (mitarbeiters == null) {
			mitarbeiters = new ArrayList<Mitarbeiter>();
		}
		mitarbeiters.add(mitarbeiter);
		return this;
	}


	@Override
	public String toString() {
		return "Rollen [id=" + id + ", name=" + name + "]";
	}

}

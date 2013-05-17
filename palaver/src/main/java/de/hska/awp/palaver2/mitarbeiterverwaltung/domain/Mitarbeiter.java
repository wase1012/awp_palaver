package de.hska.awp.palaver2.mitarbeiterverwaltung.domain;

import java.util.ArrayList;
import java.util.List;

public class Mitarbeiter implements java.io.Serializable {

	private static final long serialVersionUID = -590239735735958622L;
	private Long id;
	private String name;
	private String vorname;
	private String email;
	private String passwort;
	private String eintrittsdatum;
	private String austrittsdatum;
	private List<Rollen> rollen = new ArrayList<Rollen>();

	public Mitarbeiter() {
		super();
	}

	public Mitarbeiter(Long id, String name, String vorname, String email,
			String passwort, String eintrittsdatum, String austrittsdatum, List<Rollen> rollen) {
		this.id = id;
		this.name = name;
		this.vorname = vorname;
		this.email = email;
		this.passwort = passwort;
		this.eintrittsdatum = eintrittsdatum;
		this.austrittsdatum = austrittsdatum;
		this.rollen = rollen;
	}

	public Mitarbeiter(Long id, String name, String vorname, String email,
			String passwort, String eintrittsdatum, String austrittsdatum) {
		this.id = id;
		this.name = name;
		this.vorname = vorname;
		this.email = email;
		this.passwort = passwort;
		this.eintrittsdatum = eintrittsdatum;
		this.austrittsdatum = austrittsdatum;
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

	public String getVorname() {
		return this.vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswort() {
		return this.passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public String getEintrittsdatum() {
		return this.eintrittsdatum;
	}

	public void setEintrittsdatum(String eintrittsdatum) {
		this.eintrittsdatum = eintrittsdatum;
	}

	public String getAustrittsdatum() {
		return this.austrittsdatum;
	}

	public void setAustrittsdatum(String austrittsdatum) {
		this.austrittsdatum = austrittsdatum;
	}

	public List<Rollen> getRollen() {
		return rollen;
	}

	public void setRollen(List<Rollen> rollen) {
		this.rollen = rollen;
	}
	
	public Mitarbeiter addRollen(Rollen rolle) {
		if (rollen == null) {
			rollen = new ArrayList<>();
		}
		rollen.add(rolle);
		return this;
	}

	@Override
	public String toString() {
		return "Mitarbeiter [id=" + id + ", name=" + name + ", vorname="
				+ vorname + ", email=" + email + ", passwort=" + passwort
				+ ", eintrittsdatum=" + eintrittsdatum + ", austrittsdatum="
				+ austrittsdatum + "]";
	}

}

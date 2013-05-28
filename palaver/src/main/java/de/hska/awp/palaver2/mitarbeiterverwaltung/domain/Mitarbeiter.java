package de.hska.awp.palaver2.mitarbeiterverwaltung.domain;

import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;

/**
 * Die Klasse spiegelt den Mitarbeiter wieder.
 * @author Christian Barth
 *
 */
public class Mitarbeiter implements java.io.Serializable {

	private static final long serialVersionUID = -590239735735958622L;
	private Long id;
	private String name;
	private String vorname;
	private String benutzername;
	private String email;
	private String passwort;
	private String eintrittsdatum;
	private String austrittsdatum;
	private List<Rollen> rollen = new ArrayList<Rollen>();

	public Mitarbeiter() {
		super();
	}
	
	public Mitarbeiter(String name, String vorname, String email,
			String passwort, String eintrittsdatum, String austrittsdatum, List<Rollen> rollen, String benutzername) {
		this.name = name;
		this.vorname = vorname;
		this.email = email;
		this.passwort = passwort;
		this.eintrittsdatum = eintrittsdatum;
		this.austrittsdatum = austrittsdatum;
		this.rollen = rollen;
		this.benutzername = benutzername;
	}

	public Mitarbeiter(Long id, String name, String vorname, String email,
			String passwort, String eintrittsdatum, String austrittsdatum, List<Rollen> rollen, String benutzername) {
		this.id = id;
		this.name = name;
		this.vorname = vorname;
		this.email = email;
		this.passwort = passwort;
		this.eintrittsdatum = eintrittsdatum;
		this.austrittsdatum = austrittsdatum;
		this.rollen = rollen;
		this.benutzername = benutzername;
	}

	public Mitarbeiter(Long id, String name, String vorname, String email,
			String passwort, String eintrittsdatum, String austrittsdatum, String benutzername) {
		this.id = id;
		this.name = name;
		this.vorname = vorname;
		this.email = email;
		this.passwort = passwort;
		this.eintrittsdatum = eintrittsdatum;
		this.austrittsdatum = austrittsdatum;
		this.benutzername = benutzername;
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
			rollen = new ArrayList<Rollen>();
		}
		rollen.add(rolle);
		return this;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	@Override
	public String toString() {
		return "Mitarbeiter [id=" + id + ", name=" + name + ", vorname="
				+ vorname + ", email=" + email + ", passwort=" + passwort
				+ ", eintrittsdatum=" + eintrittsdatum + ", austrittsdatum="
				+ austrittsdatum + "]";
	}
	

}

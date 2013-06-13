package de.hska.awp.palaver2.mitarbeiterverwaltung.domain;

import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;

/**
 * Die Klasse spiegelt die Rollen wieder.
 * 
 * @author Christian Barth
 * 
 */
public class Rollen implements java.io.Serializable {

	private static final long serialVersionUID = -3366000000412110979L;
	private Long id;
	private String name;
	private List<Mitarbeiter> mitarbeiters = new ArrayList<Mitarbeiter>();
	private List<Nachricht> nachrichten = new ArrayList<Nachricht>();
	
	public static final String	ADMINISTRATOR = "Chef";
	public static final String	BESTELLER = "Besteller";
	public static final String	EINKAUF = "Einkauf";
	public static final String	KOCH = "Koch";
	public static final String	SPEISEPLAN = "Speiseplan freigeben";

	public Rollen() {
	}

	public Rollen(String name) {
		super();
		this.name = name;
	}

	public Rollen(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Rollen(Long id, String name, List<Mitarbeiter> mitarbeiters, List<Nachricht> nachrichten) {
		super();
		this.id = id;
		this.name = name;
		this.mitarbeiters = mitarbeiters;
		this.nachrichten = nachrichten;
	}

	public Rollen(Long id, String name, List<Nachricht> nachrichten) {
		this.id = id;
		this.name = name;
		this.nachrichten = nachrichten;
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

	public List<Nachricht> getNachrichten() {
		return nachrichten;
	}

	public void setNachrichten(List<Nachricht> nachrichten) {
		this.nachrichten = nachrichten;
	}

	public Rollen addNachricht(Nachricht nachricht) {
		if (nachricht == null) {
			nachrichten = new ArrayList<Nachricht>();
		}
		nachrichten.add(nachricht);
		return this;
	}

	@Override
	public String toString() {
		return name;
	}

}

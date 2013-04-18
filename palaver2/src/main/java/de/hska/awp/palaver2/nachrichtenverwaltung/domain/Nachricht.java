package de.hska.awp.palaver2.nachrichtenverwaltung.domain;

import de.hska.awp.palaver2.bean.Bean;

public class Nachricht implements Bean {

	private Long			id;
	private String			nachricht;
//	private Mitarbeiter		sender;
//	private Mitarbeiter		empfaenger;
	
	public Nachricht(Long id, String nachricht) {
		super();
		this.id = id;
		this.nachricht = nachricht;
	}

	/**
	 * @return the nachricht
	 */
	public String getNachricht() {
		return nachricht;
	}

	/**
	 * @param nachricht the nachricht to set
	 */
	public void setNachricht(String nachricht) {
		this.nachricht = nachricht;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	
}

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
	
	
	public Long getId() {
		return id;
	}
	
	
	public String getNachricht() {
		return nachricht;
	}
	
	
	public void setNachricht(String nachricht) {
		this.nachricht = nachricht;
		
	}
	
	
}

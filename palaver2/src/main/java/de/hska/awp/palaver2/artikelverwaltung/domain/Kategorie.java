package de.hska.awp.palaver2.artikelverwaltung.domain;
/**
 * @author Mihail Boehm
 * @datum 18.04.2013
 * 
 * @class Kategorie
 * @version 1.0
 */
public class Kategorie {
	
	private Long  id;	
	private String name;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
}

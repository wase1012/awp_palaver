/**
 * Created by bach1014
 * 17.04.2013 - 13:57:32
 */
package de.hska.awp.palaver2.lieferantenverwaltung.domain;

import de.hska.awp.palaver2.bean.Bean;

/**
 * @author bach1014
 *
 */
public class Ansprechpartner implements Bean{

	private Long id;
	private String name;
	private String telefon;
	private String fax;
	
	
	public Ansprechpartner() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 * @param telefon
	 * @param fax
	 */
	public Ansprechpartner(Long id, String name, String telefon, String fax) {
		super();
		this.id = id;
		this.name = name;
		this.telefon = telefon;
		this.fax = fax;
	}
	
	/**
	 * @return the id
	 */
	@Override
	public Long getId()
	{
		return id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the telefon
	 */
	public String getTelefon() {
		return telefon;
	}

	/**
	 * @param telefon the telefon to set
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ansprechpartner other = (Ansprechpartner) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Ansprechpartner [id=" + id + ", name=" + name + ", telefon="
				+ telefon + ", fax=" + fax + "]";
	}

	
	
}
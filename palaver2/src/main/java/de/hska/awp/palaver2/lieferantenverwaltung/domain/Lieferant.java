/**
 * Created by bach1014
 * 17.04.2013 - 13:47:37
 */
package de.hska.awp.palaver2.lieferantenverwaltung.domain;

import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.bean.Bean;

/**
 * @author bach1014
 *
 */
public class Lieferant implements Bean {

	private Long id;
	private String name;
	private String strasse;
	private String plz;
	private String ort;
	private String email;
	private String telefon;
	private String fax;
	private List<Ansprechpartner> ansprechpartner;
	
	

	
	/**
	 * @param id
	 * @param name
	 * @param strasse
	 * @param plz
	 * @param ort
	 * @param email
	 * @param telefon
	 * @param fax
	 * @param ansprechpartner
	 */
	public Lieferant(Long id, String name, String strasse, String plz,
			String ort, String email, String telefon, String fax,
			List<Ansprechpartner> ansprechpartner) {
		super();
		this.id = id;
		this.name = name;
		this.strasse = strasse;
		this.plz = plz;
		this.ort = ort;
		this.email = email;
		this.telefon = telefon;
		this.fax = fax;
		this.ansprechpartner = ansprechpartner;
	}

	
	/**
	 * @param ap adds a new contact
	 */
	public Lieferant addAnsprechpartner(Ansprechpartner ap) {
		if (ansprechpartner == null)
			ansprechpartner = new ArrayList<Ansprechpartner>();
		ansprechpartner.add(ap);
		
		return this;
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the strasse
	 */
	public String getStrasse() {
		return strasse;
	}

	/**
	 * @param strasse the strasse to set
	 */
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	/**
	 * @return the plz
	 */
	public String getPlz() {
		return plz;
	}

	/**
	 * @param plz the plz to set
	 */
	public void setPlz(String plz) {
		this.plz = plz;
	}

	/**
	 * @return the ort
	 */
	public String getOrt() {
		return ort;
	}

	/**
	 * @param ort the ort to set
	 */
	public void setOrt(String ort) {
		this.ort = ort;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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

	/**
	 * @return the ansprechpartner
	 */
	public List<Ansprechpartner> getAnsprechpartner() {
		return ansprechpartner;
	}

	/**
	 * @param ansprechpartner the ansprechpartner to set
	 */
	public void setAnsprechpartner(List<Ansprechpartner> ansprechpartner) {
		this.ansprechpartner = ansprechpartner;
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
		Lieferant other = (Lieferant) obj;
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
		return "Lieferant [id=" + id + ", name=" + name + ", strasse="
				+ strasse + ", plz=" + plz + ", ort=" + ort + ", email="
				+ email + ", telefon=" + telefon + ", fax=" + fax
				+ ", ansprechpartner=" + ansprechpartner + "]";
	}



	
	
}

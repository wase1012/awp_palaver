/*
 * Created by Elena Weiss
 * 
 */
package de.hska.awp.palaver2.bestellverwaltung.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import de.hska.awp.palaver2.bean.Bean;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * The Class Bestellung.
 */
public class Bestellung implements Bean {

	/** The id. */
	private Long id;
	
	/** The datum. */
	private Date datum;
	
	/** The lieferant. */
	private List<Lieferant> lieferant;
	
	/**
	 * 
	 *
	 * @param id 
	 * @param datum 
	 * @param lieferant 
	 */
	public Bestellung (Long id, Date datum, List<Lieferant> lieferant) {
	
		super();
		this.setId(id);
		this.setDatum(datum);
		this.setLieferant(lieferant);
		
	}
	
	/* (non-Javadoc)
	 * @see de.hska.awp.palaver2.bean.Bean#getId()
	 */
	public Long getId() {
		return id;
	}



	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	

	/**
	 * Gets the datum.
	 *
	 * @return the datum
	 */
	public Date getDatum() {
		return datum;
	}

	/**
	 * Sets the datum.
	 *
	 * @param datum the new datum
	 */
	public void setDatum(Date datum) {
		this.datum = datum;
	}

	/**
	 * Gets the lieferant.
	 *
	 * @return the lieferant
	 */
	public List<Lieferant> getLieferant() {
		return lieferant;
	}

	/**
	 * Sets the lieferant.
	 *
	 * @param lieferant the new lieferant
	 */
	public void setLieferant(List<Lieferant> lieferant) {
		this.lieferant = lieferant;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datum == null) ? 0 : datum.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lieferant == null) ? 0 : lieferant.hashCode());
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
		Bestellung other = (Bestellung) obj;
		if (datum == null) {
			if (other.datum != null)
				return false;
		} else if (!datum.equals(other.datum))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lieferant == null) {
			if (other.lieferant != null)
				return false;
		} else if (!lieferant.equals(other.lieferant))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bestellung [id=" + id + ", datum=" + datum + ", lieferant="
				+ lieferant + "]";
	}

	
	
}

package de.hska.awp.palaver2.bestellverwaltung.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * @author Elena W
 * Die Klasse Bestellung spiegelt den Bestellung aus der Datenbank wieder
 */

public class Bestellung implements java.io.Serializable {
	
	private static final long serialVersionUID = -4115989551813492575L;


	
	private Long id;
	private Lieferant lieferant;
	private Date datum;	
	
	public Bestellung() {
	}

	public Bestellung(Long id, Lieferant lieferant, Date datum) {
		this.id = id;
		this.lieferant = lieferant;
		this.datum = datum;
	}

	public Long getId() {
		return this.id;
	}

	public Lieferant getLieferant() {
		return this.lieferant;
	}

	public void setLieferant(Lieferant lieferant) {
		this.lieferant = lieferant;
	}

	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

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
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bestellung [id=" + id + ", lieferant=" + lieferant + ", datum="
				+ datum + "]";
	}

}

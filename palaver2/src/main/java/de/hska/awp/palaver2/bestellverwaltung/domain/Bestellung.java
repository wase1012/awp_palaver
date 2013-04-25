package de.hska.awp.palaver2.bestellverwaltung.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * @author Elena W
 * Die Klasse Bestellung spiegelt den Bestellung aus der Datenbank wieder
 * 
 */
@Entity
@Table(name = "bestellung", catalog = "palaver")
@NamedQueries({
	@NamedQuery(name = Bestellung.FIND_BESTELLUNG_BY_ID, query = "Select k FROM Bestellung k WHERE k.id = :"
			+ Bestellung.PARAM_ID),
	@NamedQuery(name = Bestellung.FIND_ALL_BESTELLUNG, query = "Select k FROM Bestellung k")
})

public class Bestellung implements java.io.Serializable {
	
	private static final long serialVersionUID = -4115989551813492575L;

	private static final String PREFIX = "Bestellung.";
	public static final String FIND_BESTELLUNG_BY_ID = PREFIX
			+ "findBestellungById";
	public static final String FIND_ALL_BESTELLUNG = PREFIX + "findAllBestellung";
	public static final String PARAM_ID = "id";
	
	private Long id;
	private Lieferant lieferant;
	private Date datum;
	
	private Set<Bestellposition> bestellpositions = new HashSet<Bestellposition>(
			0);

	public Bestellung() {
	}

	public Bestellung(Lieferant lieferant, Date datum) {
		this.lieferant = lieferant;
		this.datum = datum;
	}

	
	public Bestellung(Lieferant lieferant, Date datum,
			Set<Bestellposition> bestellpositions) {
		this.lieferant = lieferant;
		this.datum = datum;
		this.bestellpositions = bestellpositions;
	}

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lieferant_fk", nullable = false)
	public Lieferant getLieferant() {
		return this.lieferant;
	}

	public void setLieferant(Lieferant lieferant) {
		this.lieferant = lieferant;
	}

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datum", nullable = false, length = 19)
	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bestellung")
	public Set<Bestellposition> getBestellpositions() {
		return this.bestellpositions;
	}

	public void setBestellpositions(Set<Bestellposition> bestellpositions) {
		this.bestellpositions = bestellpositions;
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

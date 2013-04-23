package de.hska.awp.palaver2.bestellverwaltung.domain;

// Generated 21.04.2013 16:08:42 by Hibernate Tools 3.4.0.CR1

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * Bestellung generated by hbm2java
 */
@Entity
@Table(name = "bestellung", catalog = "palaver")
public class Bestellung implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -4115989551813492575L;
	private Integer id;
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
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

}

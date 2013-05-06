package de.hska.awp.palaver2.bestellverwaltung.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private List<Bestellposition> bestellpositionen;
	
	public Bestellung() {
		super();
	}

	public Bestellung(Long id, Lieferant lieferant, Date datum) {
		super();
		this.id = id;
		this.lieferant = lieferant;
		this.datum = datum;
	}

	/**
	 * @param id
	 * @param lieferant
	 * @param datum
	 * @param bestellpositionen
	 */
	public Bestellung(Long id, Lieferant lieferant, Date datum, List<Bestellposition> bestellpositionen) {
		super();
		this.id = id;
		this.lieferant = lieferant;
		this.datum = datum;
		this.bestellpositionen = bestellpositionen;
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

	public List<Bestellposition> getBestellpositionen() {
		return bestellpositionen;
	}

	public void setBestellpositionen(List<Bestellposition> bestellpositionen) {
		this.bestellpositionen = bestellpositionen;
	}
	
	public Bestellung addBestellposition(Bestellposition bestellposition) {
		if (bestellpositionen == null) {
			bestellpositionen = new ArrayList<>();
		}
		bestellpositionen.add(bestellposition);
		return this;
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

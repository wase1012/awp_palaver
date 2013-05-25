package de.hska.awp.palaver2.bestellverwaltung.domain;

import java.util.ArrayList;
import java.sql.Date;
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
	private Date lieferdatum;
	private Date lieferdatum2;
	private List<Bestellposition> bestellpositionen;
	private boolean bestellt;
	
	public Bestellung() {
		super();
	}

	public Bestellung(Long id, Lieferant lieferant, Date datum, Date lieferdatum, Date lieferdatum2, boolean bestellt) {
		super();
		this.id = id;
		this.lieferant = lieferant;
		this.datum = datum;
		this.lieferdatum = lieferdatum;
		this.bestellt = bestellt;
		this.lieferdatum2 = lieferdatum2;
	}

	/**
	 * @param id
	 * @param lieferant
	 * @param datum
	 * @param bestellpositionen
	 */
	public Bestellung(Long id, Lieferant lieferant, Date datum, Date lieferdatum, Date lieferdatum2, List<Bestellposition> bestellpositionen, boolean bestellt) {
		super();
		this.id = id;
		this.lieferant = lieferant;
		this.datum = datum;
		this.lieferdatum = lieferdatum;
		this.bestellpositionen = bestellpositionen;
		this.bestellt = bestellt;
		this.lieferdatum2 = lieferdatum2;
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

	public Date getLieferdatum() {
		return lieferdatum;
	}

	public void setLieferdatum(Date lieferdatum) {
		this.lieferdatum = lieferdatum;
	}

	public List<Bestellposition> getBestellpositionen() {
		return bestellpositionen;
	}

	public void setBestellpositionen(List<Bestellposition> bestellpositionen) {
		this.bestellpositionen = bestellpositionen;
	}
	
	public Bestellung addBestellposition(Bestellposition bestellposition) {
		if (bestellpositionen == null) {
			bestellpositionen = new ArrayList<Bestellposition>();
		}
		bestellpositionen.add(bestellposition);
		return this;
	}

	public boolean isBestellt() {
		return bestellt;
	}

	public void setBestellt(boolean bestellt) {
		this.bestellt = bestellt;
	}

	public Date getLieferdatum2() {
		return lieferdatum2;
	}

	public void setLieferdatum2(Date lieferdatum2) {
		this.lieferdatum2 = lieferdatum2;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bestellung [id=" + id + ", lieferant=" + lieferant + ", datum="
				+ datum + ", lieferdatum=" + lieferdatum + ", lieferdatum2="
				+ lieferdatum2 + ", bestellt=" + bestellt + "]";
	}

	

}

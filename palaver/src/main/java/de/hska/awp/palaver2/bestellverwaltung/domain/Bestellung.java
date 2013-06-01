package de.hska.awp.palaver2.bestellverwaltung.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * Die Klasse Bestellung spiegelt den Bestellung aus der Datenbank wieder.
 * 
 * @author Elena W
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

	/**
	 * @author Christian Barth
	 * @param id
	 * @param lieferant
	 * @param datum
	 * @param lieferdatum
	 * @param lieferdatum2
	 * @param bestellt
	 */
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
	 * @author Christian Barth
	 * @param id
	 * @param lieferant
	 * @param datum
	 * @param lieferdatum
	 * @param lieferdatum2
	 * @param bestellpositionen
	 * @param bestellt
	 */
	public Bestellung(Long id, Lieferant lieferant, Date datum, Date lieferdatum, Date lieferdatum2, List<Bestellposition> bestellpositionen,
			boolean bestellt) {
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
	
	public String getDatumS() {
		DateFormat df=new SimpleDateFormat("dd.MM.yyyy");
		Date date = datum;
		String s=df.format(date); 
		return s;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Date getLieferdatum() {
		return lieferdatum;
	}
	
	public String getLieferdatumS() {
		DateFormat df=new SimpleDateFormat("dd.MM.yyyy");
		Date date = lieferdatum;
		String s=df.format(date); 
		return s;
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
	
	public String getLieferdatum2S() {
		DateFormat df=new SimpleDateFormat("dd.MM.yyyy");
		Date date = lieferdatum2;
		String s=df.format(date); 
		return s;
	}

	public void setLieferdatum2(Date lieferdatum2) {
		this.lieferdatum2 = lieferdatum2;
	}

	@Override
	public String toString() {
		return "Bestellung [id=" + id + ", lieferant=" + lieferant + ", datum=" + datum + ", lieferdatum=" + lieferdatum + ", lieferdatum2="
				+ lieferdatum2 + ", bestellt=" + bestellt + "]";
	}

}

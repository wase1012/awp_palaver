package de.hska.awp.palaver2.bestellverwaltung.domain;

import java.util.Date;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;



/**
 * @author Elena W
 * Die Klasse Bestellposition spiegelt den Bestellposition aus der Datenbank wieder
 */
	
public class Bestellposition implements java.io.Serializable {

	private static final long serialVersionUID = 1291141881234373163L;

	private Long id;
	private Artikel artikel;
	private Bestellung bestellung;	
	private Float durchschnitt;
	private Float kantine;
	private Float gesamt;
	private Integer freitag;
	private Integer montag;
	private Date lieferdatum;

	public Bestellposition() {
		super();
	}

	/**
	 * @param id
	 * @param artikel
	 * @param bestellung
	 * @param menge
	 */
	public Bestellposition(Long id, Artikel artikel, Bestellung bestellung, 
			Float durchschnitt, Float kantine, Float gesamt, Integer freitag, Integer montag, Date lieferdatum) {
		super();
		this.id = id;
		this.artikel = artikel;
		this.bestellung = bestellung;
		this.durchschnitt = durchschnitt;
		this.kantine = kantine;
		this.gesamt = gesamt;
		this.freitag = freitag;
		this.montag = montag;
		this.lieferdatum = lieferdatum;
	}




	public Long getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	public Artikel getArtikel() {
		return this.artikel;
	}

	
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}

	public Bestellung getBestellung() {
		return this.bestellung;
	}

	
	public void setBestellung(Bestellung bestellung) {
		this.bestellung = bestellung;
	}

	public Float getDurchschnitt() {
		return durchschnitt;
	}

	public void setDurchschnitt(Float durchschnitt) {
		this.durchschnitt = durchschnitt;
	}

	public Float getKantine() {
		return kantine;
	}

	public void setKantine(Float kantine) {
		this.kantine = kantine;
	}

	public Float getGesamt() {
		return gesamt;
	}

	public void setGesamt(Float gesamt) {
		this.gesamt = gesamt;
	}

	/**
	 * @return the freitag
	 */
	public Integer getFreitag() {
		return freitag;
	}

	/**
	 * @param freitag the freitag to set
	 */
	public void setFreitag(Integer freitag) {
		this.freitag = freitag;
	}

	/**
	 * @return the montag
	 */
	public Integer getMontag() {
		return montag;
	}

	/**
	 * @param montag the montag to set
	 */
	public void setMontag(Integer montag) {
		this.montag = montag;
	}

	public Date getLieferdatum() {
		return lieferdatum;
	}

	public void setLieferdatum(Date lieferdatum) {
		this.lieferdatum = lieferdatum;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bestellposition [id=" + id + ", artikel=" + artikel
				+ ", bestellung=" + bestellung
				+ ", durchschnitt=" + durchschnitt + ", kantine=" + kantine
				+ ", gesamt=" + gesamt + "]";
	}


	

}

package de.hska.awp.palaver2.bestellverwaltung.domain;

import java.sql.Date;

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
	private Integer durchschnitt;
	private Integer kantine;
	private Integer gesamt;
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
			Integer durchschnitt, Integer kantine, Integer gesamt, Integer freitag, Integer montag, Date lieferdatum) {
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

	public Integer getDurchschnitt() {
		return durchschnitt;
	}

	public void setDurchschnitt(Integer durchschnitt) {
		this.durchschnitt = durchschnitt;
	}

	public Integer getKantine() {
		return kantine;
	}

	public void setKantine(Integer kantine) {
		this.kantine = kantine;
	}

	public Integer getGesamt() {
		return gesamt;
	}

	public void setGesamt(Integer gesamt) {
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
		if(lieferdatum==null){
			lieferdatum =  new Date(00000000);
		}
		return lieferdatum;
	}

	public void setLieferdatum(Date lieferdatum) {
		this.lieferdatum = lieferdatum;
	}



	

}

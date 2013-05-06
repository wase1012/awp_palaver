package de.hska.awp.palaver2.bestellverwaltung.domain;

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
			Float durchschnitt, Float kantine, Float gesamt) {
		super();
		this.id = id;
		this.artikel = artikel;
		this.bestellung = bestellung;
		this.durchschnitt = durchschnitt;
		this.kantine = kantine;
		this.gesamt = gesamt;
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

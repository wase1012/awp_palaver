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
	private int menge;
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
	public Bestellposition(Long id, int menge, Artikel artikel, Bestellung bestellung, 
			Float durchschnitt, Float kantine, Float gesamt) {
		super();
		this.id = id;
		this.menge = menge;
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

	public int getMenge() {
		return this.menge;
	}

	
	public void setMenge(int menge) {
		this.menge = menge;
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

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artikel == null) ? 0 : artikel.hashCode());
		result = prime * result
				+ ((bestellung == null) ? 0 : bestellung.hashCode());
		result = prime * result
				+ ((durchschnitt == null) ? 0 : durchschnitt.hashCode());
		result = prime * result + ((gesamt == null) ? 0 : gesamt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kantine == null) ? 0 : kantine.hashCode());
		result = prime * result + menge;
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
		Bestellposition other = (Bestellposition) obj;
		if (artikel == null) {
			if (other.artikel != null)
				return false;
		} else if (!artikel.equals(other.artikel))
			return false;
		if (bestellung == null) {
			if (other.bestellung != null)
				return false;
		} else if (!bestellung.equals(other.bestellung))
			return false;
		if (durchschnitt == null) {
			if (other.durchschnitt != null)
				return false;
		} else if (!durchschnitt.equals(other.durchschnitt))
			return false;
		if (gesamt == null) {
			if (other.gesamt != null)
				return false;
		} else if (!gesamt.equals(other.gesamt))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kantine == null) {
			if (other.kantine != null)
				return false;
		} else if (!kantine.equals(other.kantine))
			return false;
		if (menge != other.menge)
			return false;
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bestellposition [id=" + id + ", artikel=" + artikel
				+ ", bestellung=" + bestellung + ", menge=" + menge
				+ ", durchschnitt=" + durchschnitt + ", kantine=" + kantine
				+ ", gesamt=" + gesamt + "]";
	}

	

}

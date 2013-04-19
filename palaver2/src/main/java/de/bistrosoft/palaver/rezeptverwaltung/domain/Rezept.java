/**
 * Michael Marschall
 * 18.04.2013 20:05:25
 */

package de.bistrosoft.palaver.rezeptverwaltung.domain;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.bean.Bean;

public class Rezept implements Bean {

	private Long id;
	private String bezeichnung;
	private ArtType art;
	private GeschmackType geschmack;
	private Artikel artikel;
	private String kommentar;
	private int portion;
	private Mengeneinheit mengeneinheit;
	private Float menge;
	private ZubereitungType zubereitung;
	private Boolean charakteristika;

	public Rezept(Long id, String bezeichnung, ArtType art,
			GeschmackType geschmack, Artikel artikel, String kommentar,
			int portion,Float menge, Mengeneinheit mengeneinheit,
			ZubereitungType zubereitung, Boolean charakteristika) {
		super();
		this.id = id;
		this.bezeichnung = bezeichnung;
		this.art = art;
		this.geschmack = geschmack;
		this.artikel = artikel;
		this.kommentar = kommentar;
		this.portion = portion;
		this.mengeneinheit = mengeneinheit;
		this.menge = menge;
		this.zubereitung = zubereitung;
		this.charakteristika = charakteristika;
	}

	public Float getMenge() {
		return menge;
	}

	public void setMenge(Float menge) {
		this.menge = menge;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public ArtType getArt() {
		return art;
	}

	public void setArt(ArtType art) {
		this.art = art;
	}

	public GeschmackType getGeschmack() {
		return geschmack;
	}

	public void setGeschmack(GeschmackType geschmack) {
		this.geschmack = geschmack;
	}

	public Artikel getArtikel() {
		return artikel;
	}

	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}

	public String getKommentar() {
		return kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	public int getPortion() {
		return portion;
	}

	public void setPortion(int portion) {
		this.portion = portion;
	}

	public Mengeneinheit getMengeneinheit() {
		return mengeneinheit;
	}

	public void setMengeneinheit(Mengeneinheit mengeneinheit) {
		this.mengeneinheit = mengeneinheit;
	}

	public ZubereitungType getZubereitung() {
		return zubereitung;
	}

	public void setZubereitung(ZubereitungType zubereitung) {
		this.zubereitung = zubereitung;
	}

	public Boolean isCharakteristika() {
		return charakteristika;
	}

	public void setCharakteristika(Boolean charakteristika) {
		this.charakteristika = charakteristika;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((art == null) ? 0 : art.hashCode());
		result = prime * result + ((artikel == null) ? 0 : artikel.hashCode());
		result = prime * result
				+ ((bezeichnung == null) ? 0 : bezeichnung.hashCode());
		result = prime * result
				+ ((charakteristika == null) ? 0 : charakteristika.hashCode());
		result = prime * result
				+ ((geschmack == null) ? 0 : geschmack.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((kommentar == null) ? 0 : kommentar.hashCode());
		result = prime * result + ((menge == null) ? 0 : menge.hashCode());
		result = prime * result
				+ ((mengeneinheit == null) ? 0 : mengeneinheit.hashCode());
		result = prime * result + portion;
		result = prime * result
				+ ((zubereitung == null) ? 0 : zubereitung.hashCode());
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
		Rezept other = (Rezept) obj;
		if (art != other.art)
			return false;
		if (artikel == null) {
			if (other.artikel != null)
				return false;
		} else if (!artikel.equals(other.artikel))
			return false;
		if (bezeichnung == null) {
			if (other.bezeichnung != null)
				return false;
		} else if (!bezeichnung.equals(other.bezeichnung))
			return false;
		if (charakteristika == null) {
			if (other.charakteristika != null)
				return false;
		} else if (!charakteristika.equals(other.charakteristika))
			return false;
		if (geschmack != other.geschmack)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kommentar == null) {
			if (other.kommentar != null)
				return false;
		} else if (!kommentar.equals(other.kommentar))
			return false;
		if (menge == null) {
			if (other.menge != null)
				return false;
		} else if (!menge.equals(other.menge))
			return false;
		if (mengeneinheit == null) {
			if (other.mengeneinheit != null)
				return false;
		} else if (!mengeneinheit.equals(other.mengeneinheit))
			return false;
		if (portion != other.portion)
			return false;
		if (zubereitung != other.zubereitung)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Rezept [id=" + id + ", bezeichnung=" + bezeichnung + ", art="
				+ art + ", geschmack=" + geschmack + ", artikel=" + artikel
				+ ", kommentar=" + kommentar + ", portion=" + portion
				+ ", mengeneinheit=" + mengeneinheit + ", menge=" + menge
				+ ", zubereitung=" + zubereitung + ", charakteristika="
				+ charakteristika + "]";
	}

}

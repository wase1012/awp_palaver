package de.bistrosoft.palaver.rezeptverwaltung.domain;

// Generated 25.04.2013 13:27:05 by Hibernate Tools 4.0.0

import java.math.BigDecimal;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;

public class RezeptHasArtikel {

	private Artikel artikel;
	private double menge;
	private Mengeneinheit mengeneinheit;
	private Rezept rezept;

	public RezeptHasArtikel() {
		super();
	}

	public RezeptHasArtikel(Artikel artikel, Mengeneinheit mengeneinheit,
			double menge) {
		this.artikel = artikel;
		this.mengeneinheit = mengeneinheit;
		this.menge = menge;
	}

	public RezeptHasArtikel(Rezept rezept, Artikel artikel,
			Mengeneinheit mengeneinheit, double menge) {
		this.rezept = rezept;
		this.artikel = artikel;
		this.menge = menge;
		this.mengeneinheit = mengeneinheit;

	}

	@Override
	public String toString() {
		return "artikel " + artikel.getId() + menge + mengeneinheit + "   "
				+ artikel.getName();
	}

	public double getMenge() {
		return this.menge;

	}

	public void setMenge(double menge) {
		this.menge = menge;
		System.out.println(this.menge);
	}

	public String getArtikelName() {
		return this.artikel.getName();
	}

	public Long getArtikelId() {
		return this.artikel.getId();
	}

	public void setArtike(Artikel artikel) {
		this.artikel = artikel;
	}

	// public void setRezeptart(Rezeptart rezeptart) {
	// this.rezeptart = rezeptart;
	// }

	// public Artikel getArtikel() {
	// return this.artikel;
	// }
	//
	// public void setArtikel(Artikel artikel) {
	// this.artikel = artikel;
	// }
	//
	// public void setMengeneinheit(Mengeneinheit mengeneinheit) {
	// this.mengeneinheit = mengeneinheit;
	// }
	//
	//
	// public BigDecimal getMenge() {
	// return this.menge;
	// }
	//
	// public void setMenge(BigDecimal menge) {
	// this.menge = menge;
	// }
}

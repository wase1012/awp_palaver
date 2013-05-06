package de.bistrosoft.palaver.rezeptverwaltung.domain;

// Generated 25.04.2013 13:27:05 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.bistrosoft.palaver.artikelverwaltung.domain.Artikel;
import de.bistrosoft.palaver.artikelverwaltung.domain.Mengeneinheit;


public class RezeptHasArtikel  {

	private Artikel artikel;
	private BigDecimal menge;
	private Mengeneinheit mengeneinheit;
	

	public RezeptHasArtikel() {
		super();
	}

	public RezeptHasArtikel(Artikel artikel,Mengeneinheit mengeneinheit, BigDecimal menge) {
		this.artikel = artikel;
		this.mengeneinheit = mengeneinheit;
		this.menge = menge;
	}
	public BigDecimal getMenge() {
		return this.menge;
		
	}
	public void setMenge(BigDecimal menge) {
		this.menge = menge;
	}

	public String getArtikelName() {
	return this.artikel.getName();
}
	


//	public Artikel getArtikel() {
//		return this.artikel;
//	}
//
//	public void setArtikel(Artikel artikel) {
//		this.artikel = artikel;
//	}
//
//	public void setMengeneinheit(Mengeneinheit mengeneinheit) {
//		this.mengeneinheit = mengeneinheit;
//	}
//
//
//	public BigDecimal getMenge() {
//		return this.menge;
//	}
//
//	public void setMenge(BigDecimal menge) {
//		this.menge = menge;
//	}
}

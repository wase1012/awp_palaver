/**
 * Created by Sebastian Walz
 */
package de.hska.awp.palaver2.artikelverwaltung.domain;

import java.sql.SQLException;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.MengeneinheitDAO;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

public class Artikel implements java.io.Serializable {
	private static final long serialVersionUID = 6557876739298794189L;

	private Long id;

	private Mengeneinheit mengeneinheitBestellung;
	private Mengeneinheit mengeneinheitKoch;
	private Kategorie kategorie;
	private Lieferant lieferant;

	private String artikelnr;
	private String name;

	private Double bestellgroesse;
	private Float preis;
	private Integer durchschnitt;
	private String notiz;

	private boolean nonfood;
	private boolean standard;
	private boolean grundbedarf;
	private boolean fuerRezept;
	

	private boolean autoBestellen;

	public Artikel() {
		super();
	}

	public Artikel(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/**
	 * ALL
	 * @throws SQLException 
	 * @throws DAOException 
	 * @throws ConnectException 
	 */
	public Artikel(Long id, Mengeneinheit meBestellung, Long meKoch,
			Kategorie kategorie, Lieferant lieferant, String artikelnr,
			String name, Double bestellgroesse, Float preis,
			Integer durchschnitt, String notiz, boolean nonfood,
			boolean standard, boolean grundbedarf, boolean fuerRezept,
			boolean autoBestellen) throws ConnectException, DAOException, SQLException {
		super();
		this.id = id;
		this.mengeneinheitBestellung = meBestellung;
		if(meKoch != null)
			this.mengeneinheitKoch = MengeneinheitDAO.getInstance().getMengeneinheitById(meKoch);
		else
			this.mengeneinheitKoch = null;
		this.kategorie = kategorie;
		this.lieferant = lieferant;
		this.artikelnr = artikelnr;
		this.name = name;
		this.bestellgroesse = bestellgroesse;
		this.durchschnitt = durchschnitt;
		this.preis = preis;
		this.notiz = notiz;
		this.nonfood = nonfood;
		this.standard = standard;
		this.grundbedarf = grundbedarf;
		this.fuerRezept = fuerRezept;
		this.autoBestellen = autoBestellen;

	}

	public Artikel(Long id, Mengeneinheit mengeneinheit, Kategorie kategorie,
			Lieferant lieferant, String artikelnr, String name,
			Double bestellgroesse, Float preis, boolean standard,
			boolean grundbedarf, Integer durchschnitt, boolean lebensmittel,
			String notiz) {
		super();
		this.id = id;
		this.mengeneinheitBestellung = mengeneinheit;
		this.kategorie = kategorie;
		this.lieferant = lieferant;
		this.artikelnr = artikelnr;
		this.name = name;
		this.bestellgroesse = bestellgroesse;
		this.preis = preis;
		this.standard = standard;
		this.grundbedarf = grundbedarf;
		this.durchschnitt = durchschnitt;
		this.nonfood = lebensmittel;
		this.notiz = notiz;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Mengeneinheit getMengeneinheitBestellung() {
		return this.mengeneinheitBestellung;
	}

	public void setMengeneinheitBestellung(Mengeneinheit mengeneinheit) {
		this.mengeneinheitBestellung = mengeneinheit;
	}

	public String getArtikelnr() {
		return this.artikelnr;
	}

	public void setArtikelnr(String artikelnr) {
		this.artikelnr = artikelnr;
	}

	public Mengeneinheit getMengeneinheitKoch() {
		return this.mengeneinheitKoch;
	}

	public void setMengeneinheitKoch(Mengeneinheit mengeneinheit) {
		this.mengeneinheitKoch = mengeneinheit;
	}

	public Kategorie getKategorie() {
		return this.kategorie;
	}

	public void setKategorie(Kategorie kategorie) {
		this.kategorie = kategorie;
	}

	public Lieferant getLieferant() {
		return this.lieferant;
	}

	public void setLieferant(Lieferant lieferant) {
		this.lieferant = lieferant;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getBestellgroesse() {
		return this.bestellgroesse;
	}

	public void setBestellgroesse(Double bestellgroesse) {
		this.bestellgroesse = bestellgroesse;
	}

	public Float getPreis() {
		return this.preis;
	}

	public void setPreis(Float preis) {
		this.preis = preis;
	}

	public boolean isStandard() {
		return this.standard;
	}

	public void setStandard(boolean standard) {
		this.standard = standard;
	}

	public boolean isGrundbedarf() {
		return this.grundbedarf;
	}

	public void setGrundbedarf(boolean grundbedarf) {
		this.grundbedarf = grundbedarf;
	}

	public Integer getDurchschnitt() {
		return this.durchschnitt;
	}

	public void setDurchschnitt(Integer durchschnitt) {
		this.durchschnitt = durchschnitt;
	}

	public boolean isNonfood() {
		return this.nonfood;
	}

	public void setNonfood(boolean nonfood) {
		this.nonfood = nonfood;
	}

	public String getNotiz() {
		return this.notiz;
	}

	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}
	
	public boolean isFuerRezept() {
		return fuerRezept;
	}

	public void setFuerRezept(boolean fuerRezept) {
		this.fuerRezept = fuerRezept;
	}

	public boolean isAutoBestellen() {
		return autoBestellen;
	}

	public void setAutoBestellen(boolean autoBestellen) {
		this.autoBestellen = autoBestellen;
	}



	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Artikel other = (Artikel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Artikel [name=" + name + "]";
	}
}
/**
 * Created by Sebastian Walz
 */
package main.java.de.hska.awp.palaver2.artikelverwaltung.domain;

import main.java.de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;


public class Artikel implements java.io.Serializable 
{
	private static final long 		serialVersionUID = 6557876739298794189L;

	private Long id;
	
	private Mengeneinheit 	mengeneinheit;
	private Kategorie 		kategorie;
	private Lieferant 		lieferant;
	private String 			artikelnr;
	private String 			name;
	private Double 			bestellgroesse;
	private Float 			preis;
	private boolean 		bio;
	private boolean 		standard;
	private boolean 		grundbedarf;
	private Integer 		durchschnitt;
	private boolean 		lebensmittel;

	public Artikel() 
	{
		super();
	}
	
	public Artikel(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/**
	 * @param mengeneinheit
	 * @param kategorie
	 * @param lieferant
	 * @param artikelnr
	 * @param name
	 * @param bestellgroesse
	 * @param preis
	 * @param bio
	 * @param standard
	 * @param grundbedarf
	 * @param durchschnitt
	 * @param lebensmittel
	 */
	public Artikel(Mengeneinheit mengeneinheit, Kategorie kategorie,
			Lieferant lieferant, String artikelnr, String name,
			Double bestellgroesse, Float preis, boolean bio, boolean standard,
			boolean grundbedarf, Integer durchschnitt, boolean lebensmittel)
	{
		super();
		this.mengeneinheit = mengeneinheit;
		this.kategorie = kategorie;
		this.lieferant = lieferant;
		this.artikelnr = artikelnr;
		this.name = name;
		this.bestellgroesse = bestellgroesse;
		this.preis = preis;
		this.bio = bio;
		this.standard = standard;
		this.grundbedarf = grundbedarf;
		this.durchschnitt = durchschnitt;
		this.lebensmittel = lebensmittel;
	}

	/**
	 * @param id
	 * @param mengeneinheit
	 * @param kategorie
	 * @param lieferant
	 * @param artikelnr
	 * @param name
	 * @param bestellgroesse
	 * @param preis
	 * @param bio
	 * @param standard
	 * @param grundbedarf
	 * @param durchschnitt
	 * @param lebensmittel
	 */
	public Artikel(Long id, Mengeneinheit mengeneinheit, Kategorie kategorie,
			Lieferant lieferant, String artikelnr, String name,
			Double bestellgroesse, Float preis, boolean bio, boolean standard,
			boolean grundbedarf, Integer durchschnitt, boolean lebensmittel)
	{
		super();
		this.id = id;
		this.mengeneinheit = mengeneinheit;
		this.kategorie = kategorie;
		this.lieferant = lieferant;
		this.artikelnr = artikelnr;
		this.name = name;
		this.bestellgroesse = bestellgroesse;
		this.preis = preis;
		this.bio = bio;
		this.standard = standard;
		this.grundbedarf = grundbedarf;
		this.durchschnitt = durchschnitt;
		this.lebensmittel = lebensmittel;
	}

	public Long getId() 
	{
		return this.id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public Mengeneinheit getMengeneinheit() 
	{
		return this.mengeneinheit;
	}

	public void setMengeneinheit(Mengeneinheit mengeneinheit) 
	{
		this.mengeneinheit = mengeneinheit;
	}

	public Kategorie getKategorie() 
	{
		return this.kategorie;
	}

	public void setKategorie(Kategorie kategorie) 
	{
		this.kategorie = kategorie;
	}

	public Lieferant getLieferant() 
	{
		return this.lieferant;
	}

	public void setLieferant(Lieferant lieferant) 
	{
		this.lieferant = lieferant;
	}

	public String getArtikelnr() 
	{
		return this.artikelnr;
	}

	public void setArtikelnr(String artikelnr) 
	{
		this.artikelnr = artikelnr;
	}

	public String getName() 
	{
		return this.name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public Double getBestellgroesse() 
	{
		return this.bestellgroesse;
	}

	public void setBestellgroesse(Double bestellgroesse) 
	{
		this.bestellgroesse = bestellgroesse;
	}

	public Float getPreis() 
	{
		return this.preis;
	}

	public void setPreis(Float preis) 
	{
		this.preis = preis;
	}

	public boolean isBio() 
	{
		return this.bio;
	}

	public void setBio(boolean bio) 
	{
		this.bio = bio;
	}

	public boolean isStandard() 
	{
		return this.standard;
	}

	public void setStandard(boolean standard) 
	{
		this.standard = standard;
	}

	public boolean isGrundbedarf() 
	{
		return this.grundbedarf;
	}

	public void setGrundbedarf(boolean grundbedarf) 
	{
		this.grundbedarf = grundbedarf;
	}

	public Integer getDurchschnitt() 
	{
		return this.durchschnitt;
	}

	public void setDurchschnitt(Integer durchschnitt) 
	{
		this.durchschnitt = durchschnitt;
	}

	public boolean isLebensmittel() 
	{
		return this.lebensmittel;
	}

	public void setLebensmittel(boolean lebensmittel) 
	{
		this.lebensmittel = lebensmittel;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Artikel [name=" + name + "]";
	}
}
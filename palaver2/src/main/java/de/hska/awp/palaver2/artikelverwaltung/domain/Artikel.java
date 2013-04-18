/**
 * Created by Sebastian Walz
 * 16.04.2013 15:32:46
 */
package de.hska.awp.palaver2.artikelverwaltung.domain;

import de.hska.awp.palaver2.bean.Bean;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

public class Artikel implements Bean
{
	private Long			id;
	private String			name;
	private float			preis;
	private Lieferant		lieferant;
	private Mengeneinheit	mengeneinheit;		
	private Boolean			bio;		
	private Kategorie		kategorie;
	private String			artikelnummer;
	private Boolean			standard;
	private Boolean			grundbedarf;
	private Integer			durchschnitt;
	private float			bestellgroesse;
	
	public Artikel(Long id, String name, float preis, Boolean bio,
			String artikelnummer, Boolean standard, Boolean grundbedarf,
			Integer durchschnitt, float bestellgroesse)
	{
		super();
		this.id = id;
		this.name = name;
		this.preis = preis;
		this.bio = bio;
		this.artikelnummer = artikelnummer;
		this.standard = standard;
		this.grundbedarf = grundbedarf;
		this.durchschnitt = durchschnitt;
		this.bestellgroesse = bestellgroesse;
	}


	/**
	 * @param id
	 * @param name
	 * @param preis
	 * @param lieferant
	 * @param mengeneinheit
	 * @param bio
	 * @param kategorie
	 * @param artikelnummer
	 * @param standard
	 * @param grundbedarf
	 * @param durchschnitt
	 * @param bestellgroesse
	 */
	public Artikel(Long id, String name, float preis, Lieferant lieferant,
			Mengeneinheit mengeneinheit, Boolean bio, Kategorie kategorie,
			String artikelnummer, Boolean standard, Boolean grundbedarf,
			Integer durchschnitt, float bestellgroesse)
	{
		super();
		this.id = id;
		this.name = name;
		this.preis = preis;
		this.lieferant = lieferant;
		this.mengeneinheit = mengeneinheit;
		this.bio = bio;
		this.kategorie = kategorie;
		this.artikelnummer = artikelnummer;
		this.standard = standard;
		this.grundbedarf = grundbedarf;
		this.durchschnitt = durchschnitt;
		this.bestellgroesse = bestellgroesse;
	}


	@Override
	public Long getId()
	{
		return id;
	}


	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}


	/**
	 * @return the preis
	 */
	public float getPreis()
	{
		return preis;
	}


	/**
	 * @param preis the preis to set
	 */
	public void setPreis(float preis)
	{
		this.preis = preis;
	}


	/**
	 * @return the bio
	 */
	public Boolean getBio()
	{
		return bio;
	}


	/**
	 * @param bio the bio to set
	 */
	public void setBio(Boolean bio)
	{
		this.bio = bio;
	}


	/**
	 * @return the artikelnummer
	 */
	public String getArtikelnummer()
	{
		return artikelnummer;
	}


	/**
	 * @param artikelnummer the artikelnummer to set
	 */
	public void setArtikelnummer(String artikelnummer)
	{
		this.artikelnummer = artikelnummer;
	}


	/**
	 * @return the standard
	 */
	public Boolean getStandard()
	{
		return standard;
	}


	/**
	 * @param standard the standard to set
	 */
	public void setStandard(Boolean standard)
	{
		this.standard = standard;
	}


	/**
	 * @return the grundbedarf
	 */
	public Boolean getGrundbedarf()
	{
		return grundbedarf;
	}


	/**
	 * @param grundbedarf the grundbedarf to set
	 */
	public void setGrundbedarf(Boolean grundbedarf)
	{
		this.grundbedarf = grundbedarf;
	}


	/**
	 * @return the durchschnitt
	 */
	public Integer getDurchschnitt()
	{
		return durchschnitt;
	}


	/**
	 * @param durchschnitt the durchschnitt to set
	 */
	public void setDurchschnitt(Integer durchschnitt)
	{
		this.durchschnitt = durchschnitt;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}


	/**
	 * @return the bestellgroesse
	 */
	public float getBestellgroesse()
	{
		return bestellgroesse;
	}


	/**
	 * @param bestellgroesse the bestellgroesse to set
	 */
	public void setBestellgroesse(float bestellgroesse)
	{
		this.bestellgroesse = bestellgroesse;
	}


	public Lieferant getLieferant()
	{
		return lieferant;
	}


	public void setLieferant(Lieferant lieferant)
	{
		this.lieferant = lieferant;
	}


	/**
	 * @return the mengeneinheit
	 */
	public Mengeneinheit getMengeneinheit()
	{
		return mengeneinheit;
	}


	/**
	 * @param mengeneinheit the mengeneinheit to set
	 */
	public void setMengeneinheit(Mengeneinheit mengeneinheit)
	{
		this.mengeneinheit = mengeneinheit;
	}


	/**
	 * @return the kategorie
	 */
	public Kategorie getKategorie()
	{
		return kategorie;
	}


	/**
	 * @param kategorie the kategorie to set
	 */
	public void setKategorie(Kategorie kategorie)
	{
		this.kategorie = kategorie;
	}

}

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


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Artikel [id=" + id + ", name=" + name + ", preis=" + preis
				+ ", lieferant=" + lieferant + ", mengeneinheit="
				+ mengeneinheit + ", bio=" + bio + ", kategorie=" + kategorie
				+ ", artikelnummer=" + artikelnummer + ", standard=" + standard
				+ ", grundbedarf=" + grundbedarf + ", durchschnitt="
				+ durchschnitt + ", bestellgroesse=" + bestellgroesse + "]";
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((artikelnummer == null) ? 0 : artikelnummer.hashCode());
		result = prime * result + Float.floatToIntBits(bestellgroesse);
		result = prime * result + ((bio == null) ? 0 : bio.hashCode());
		result = prime * result
				+ ((durchschnitt == null) ? 0 : durchschnitt.hashCode());
		result = prime * result
				+ ((grundbedarf == null) ? 0 : grundbedarf.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((kategorie == null) ? 0 : kategorie.hashCode());
		result = prime * result
				+ ((lieferant == null) ? 0 : lieferant.hashCode());
		result = prime * result
				+ ((mengeneinheit == null) ? 0 : mengeneinheit.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(preis);
		result = prime * result
				+ ((standard == null) ? 0 : standard.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artikel other = (Artikel) obj;
		if (artikelnummer == null)
		{
			if (other.artikelnummer != null)
				return false;
		} else if (!artikelnummer.equals(other.artikelnummer))
			return false;
		if (Float.floatToIntBits(bestellgroesse) != Float
				.floatToIntBits(other.bestellgroesse))
			return false;
		if (bio == null)
		{
			if (other.bio != null)
				return false;
		} else if (!bio.equals(other.bio))
			return false;
		if (durchschnitt == null)
		{
			if (other.durchschnitt != null)
				return false;
		} else if (!durchschnitt.equals(other.durchschnitt))
			return false;
		if (grundbedarf == null)
		{
			if (other.grundbedarf != null)
				return false;
		} else if (!grundbedarf.equals(other.grundbedarf))
			return false;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kategorie == null)
		{
			if (other.kategorie != null)
				return false;
		} else if (!kategorie.equals(other.kategorie))
			return false;
		if (lieferant == null)
		{
			if (other.lieferant != null)
				return false;
		} else if (!lieferant.equals(other.lieferant))
			return false;
		if (mengeneinheit == null)
		{
			if (other.mengeneinheit != null)
				return false;
		} else if (!mengeneinheit.equals(other.mengeneinheit))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(preis) != Float.floatToIntBits(other.preis))
			return false;
		if (standard == null)
		{
			if (other.standard != null)
				return false;
		} else if (!standard.equals(other.standard))
			return false;
		return true;
	}

}

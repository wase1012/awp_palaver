/**
 * Created by Sebastian Walz
 */
package de.hska.awp.palaver2.artikelverwaltung.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

@Entity
@Table(name = "artikel", catalog = "palaver")
@NamedQueries({
	@NamedQuery(name = Artikel.FIND_ALL_ARTIKLES, query = "SELECT a FROM Artikel a"),
	@NamedQuery(name = Artikel.FIND_ARTIKLE_BY_ID, query = "SELECT a FROM Artikel a WHERE a.id = :"
	+ Artikel.PARAM_ID)
})
public class Artikel implements java.io.Serializable 
{
	private static final long 		serialVersionUID = 6557876739298794189L;
	
	private static final String 	PREFIX = "Artikel.";
	public static final String		FIND_ALL_ARTIKLES = PREFIX + "findAllArtikel";
	public static final String		FIND_ARTIKLE_BY_ID = PREFIX + "findArtikelById";
	public static final String PARAM_ID = "id";
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mengeneinheit_fk", nullable = false)
	private Mengeneinheit mengeneinheit;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kategorie_fk", nullable = false)
	private Kategorie kategorie;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lieferant_fk", nullable = false)
	private Lieferant lieferant;
	
	@Column(name = "artikelnr", length = 45)
	private String artikelnr;
	
	@Column(name = "name", nullable = false, length = 45)
	private String name;
	
	@Column(name = "bestellgroesse", precision = 22, scale = 0)
	private Double bestellgroesse;
	
	@Column(name = "preis", precision = 12, scale = 0)
	private Float preis;
	
	@Column(name = "bio", nullable = false)
	private boolean bio;
	
	@Column(name = "standard", nullable = false)
	private boolean standard;
	
	@Column(name = "grundbedarf", nullable = false)
	private boolean grundbedarf;
	
	@Column(name = "durchschnitt")
	private Integer durchschnitt;
	
	@Column(name = "lebensmittel", nullable = false)
	private boolean lebensmittel;

	public Artikel() 
	{
		super();
	}

	public Artikel(Mengeneinheit mengeneinheit, Kategorie kategorie,
			Lieferant lieferant, String name, boolean bio, boolean standard,
			boolean grundbedarf, boolean lebensmittel) 
	{
		this.mengeneinheit = mengeneinheit;
		this.kategorie = kategorie;
		this.lieferant = lieferant;
		this.name = name;
		this.bio = bio;
		this.standard = standard;
		this.grundbedarf = grundbedarf;
		this.lebensmittel = lebensmittel;
	}

	public Artikel(Mengeneinheit mengeneinheit, Kategorie kategorie,
			Lieferant lieferant, String artikelnr, String name,
			Double bestellgroesse, Float preis, boolean bio, boolean standard,
			boolean grundbedarf, Integer durchschnitt, boolean lebensmittel) 
	{
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
}
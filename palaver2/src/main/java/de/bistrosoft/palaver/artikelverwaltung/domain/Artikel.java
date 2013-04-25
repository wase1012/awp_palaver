/**
 * Created by Sebastian Walz
 */
package de.bistrosoft.palaver.artikelverwaltung.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.bistrosoft.palaver.bestellverwaltung.domain.Bestellposition;
import de.bistrosoft.palaver.lieferantenverwaltung.domain.Lieferant;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;

@Entity
@Table(name = "artikel", catalog = "palaver")
public class Artikel implements java.io.Serializable {

	
	private static final long serialVersionUID = 7630923491684241376L;
	private Long id;
	private Mengeneinheit mengeneinheit;
	private Kategorie kategorie;
	private Lieferant lieferant;
	private String artikelnr;
	private String name;
	private Double bestellgroesse;
	private Float preis;
	private boolean bio;
	private boolean standard;
	private boolean grundbedarf;
	private Integer durchschnitt;
	private boolean lebensmittel;
	private Set<Bestellposition> bestellpositions = new HashSet<Bestellposition>(
			0);
	private Set<RezeptHasArtikel> rezeptHasArtikels = new HashSet<RezeptHasArtikel>(
			0);

	public Artikel() {
	}

	public Artikel(Mengeneinheit mengeneinheit, Kategorie kategorie,
			Lieferant lieferant, String name, boolean bio, boolean standard,
			boolean grundbedarf, boolean lebensmittel) {
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
			boolean grundbedarf, Integer durchschnitt, boolean lebensmittel,
			Set<Bestellposition> bestellpositions,
			Set<RezeptHasArtikel> rezeptHasArtikels) {
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
		this.bestellpositions = bestellpositions;
		this.rezeptHasArtikels = rezeptHasArtikels;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mengeneinheit_fk", nullable = false)
	public Mengeneinheit getMengeneinheit() {
		return this.mengeneinheit;
	}

	public void setMengeneinheit(Mengeneinheit mengeneinheit) {
		this.mengeneinheit = mengeneinheit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kategorie_fk", nullable = false)
	public Kategorie getKategorie() {
		return this.kategorie;
	}

	public void setKategorie(Kategorie kategorie) {
		this.kategorie = kategorie;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lieferant_fk", nullable = false)
	public Lieferant getLieferant() {
		return this.lieferant;
	}

	public void setLieferant(Lieferant lieferant) {
		this.lieferant = lieferant;
	}

	@Column(name = "artikelnr", length = 45)
	public String getArtikelnr() {
		return this.artikelnr;
	}

	public void setArtikelnr(String artikelnr) {
		this.artikelnr = artikelnr;
	}

	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "bestellgroesse", precision = 22, scale = 0)
	public Double getBestellgroesse() {
		return this.bestellgroesse;
	}

	public void setBestellgroesse(Double bestellgroesse) {
		this.bestellgroesse = bestellgroesse;
	}

	@Column(name = "preis", precision = 12, scale = 0)
	public Float getPreis() {
		return this.preis;
	}

	public void setPreis(Float preis) {
		this.preis = preis;
	}

	@Column(name = "bio", nullable = false)
	public boolean isBio() {
		return this.bio;
	}

	public void setBio(boolean bio) {
		this.bio = bio;
	}

	@Column(name = "standard", nullable = false)
	public boolean isStandard() {
		return this.standard;
	}

	public void setStandard(boolean standard) {
		this.standard = standard;
	}

	@Column(name = "grundbedarf", nullable = false)
	public boolean isGrundbedarf() {
		return this.grundbedarf;
	}

	public void setGrundbedarf(boolean grundbedarf) {
		this.grundbedarf = grundbedarf;
	}

	@Column(name = "durchschnitt")
	public Integer getDurchschnitt() {
		return this.durchschnitt;
	}

	public void setDurchschnitt(Integer durchschnitt) {
		this.durchschnitt = durchschnitt;
	}

	@Column(name = "lebensmittel", nullable = false)
	public boolean isLebensmittel() {
		return this.lebensmittel;
	}

	public void setLebensmittel(boolean lebensmittel) {
		this.lebensmittel = lebensmittel;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "artikel")
	public Set<Bestellposition> getBestellpositions() {
		return this.bestellpositions;
	}

	public void setBestellpositions(Set<Bestellposition> bestellpositions) {
		this.bestellpositions = bestellpositions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "artikel")
	public Set<RezeptHasArtikel> getRezeptHasArtikels() {
		return this.rezeptHasArtikels;
	}

	public void setRezeptHasArtikels(Set<RezeptHasArtikel> rezeptHasArtikels) {
		this.rezeptHasArtikels = rezeptHasArtikels;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((artikelnr == null) ? 0 : artikelnr.hashCode());
		result = prime * result
				+ ((bestellgroesse == null) ? 0 : bestellgroesse.hashCode());
		result = prime
				* result
				+ ((bestellpositions == null) ? 0 : bestellpositions.hashCode());
		result = prime * result + (bio ? 1231 : 1237);
		result = prime * result
				+ ((durchschnitt == null) ? 0 : durchschnitt.hashCode());
		result = prime * result + (grundbedarf ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((kategorie == null) ? 0 : kategorie.hashCode());
		result = prime * result + (lebensmittel ? 1231 : 1237);
		result = prime * result
				+ ((lieferant == null) ? 0 : lieferant.hashCode());
		result = prime * result
				+ ((mengeneinheit == null) ? 0 : mengeneinheit.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((preis == null) ? 0 : preis.hashCode());
		result = prime
				* result
				+ ((rezeptHasArtikels == null) ? 0 : rezeptHasArtikels
						.hashCode());
		result = prime * result + (standard ? 1231 : 1237);
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
		if (artikelnr == null) {
			if (other.artikelnr != null)
				return false;
		} else if (!artikelnr.equals(other.artikelnr))
			return false;
		if (bestellgroesse == null) {
			if (other.bestellgroesse != null)
				return false;
		} else if (!bestellgroesse.equals(other.bestellgroesse))
			return false;
		if (bestellpositions == null) {
			if (other.bestellpositions != null)
				return false;
		} else if (!bestellpositions.equals(other.bestellpositions))
			return false;
		if (bio != other.bio)
			return false;
		if (durchschnitt == null) {
			if (other.durchschnitt != null)
				return false;
		} else if (!durchschnitt.equals(other.durchschnitt))
			return false;
		if (grundbedarf != other.grundbedarf)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kategorie == null) {
			if (other.kategorie != null)
				return false;
		} else if (!kategorie.equals(other.kategorie))
			return false;
		if (lebensmittel != other.lebensmittel)
			return false;
		if (lieferant == null) {
			if (other.lieferant != null)
				return false;
		} else if (!lieferant.equals(other.lieferant))
			return false;
		if (mengeneinheit == null) {
			if (other.mengeneinheit != null)
				return false;
		} else if (!mengeneinheit.equals(other.mengeneinheit))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (preis == null) {
			if (other.preis != null)
				return false;
		} else if (!preis.equals(other.preis))
			return false;
		if (rezeptHasArtikels == null) {
			if (other.rezeptHasArtikels != null)
				return false;
		} else if (!rezeptHasArtikels.equals(other.rezeptHasArtikels))
			return false;
		if (standard != other.standard)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Artikel [id=" + id + ", mengeneinheit=" + mengeneinheit
				+ ", kategorie=" + kategorie + ", lieferant=" + lieferant
				+ ", artikelnr=" + artikelnr + ", name=" + name
				+ ", bestellgroesse=" + bestellgroesse + ", preis=" + preis
				+ ", bio=" + bio + ", standard=" + standard + ", grundbedarf="
				+ grundbedarf + ", durchschnitt=" + durchschnitt
				+ ", lebensmittel=" + lebensmittel + ", bestellpositions="
				+ bestellpositions + ", rezeptHasArtikels=" + rezeptHasArtikels
				+ "]";
	}

}
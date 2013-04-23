package de.hska.awp.palaver2.lieferantenverwaltung.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;

/** 
 * @author bach1014
 * Die Klasse Lieferant spiegelt den Lieferant aus der Datenbank wieder
 */
@Entity
@Table(name = "lieferant", catalog = "palaver")
@NamedQueries({
		@NamedQuery(name = Lieferant.FIND_LIEFERANT_BY_NAME, query = "SELECT k FROM Lieferant k WHERE k.name = :"
				+ Ansprechpartner.PARAM_NAME),
		@NamedQuery(name = Lieferant.FIND_LIEFERANT_BY_ID, query = "Select k FROM Lieferant k WHERE k.id = :"
				+ Ansprechpartner.PARAM_ID),
		@NamedQuery(name = Lieferant.FIND_ALL_LIEFERANT, query = "Select k FROM Lieferant k") })
public class Lieferant implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 509321793481530142L;

	private static final String PREFIX = "Lieferant.";

	public static final String FIND_LIEFERANT_BY_NAME = PREFIX
			+ "findLieferantByName";
	public static final String FIND_LIEFERANT_BY_ID = PREFIX
			+ "findLieferantById";
	public static final String FIND_ALL_LIEFERANT = PREFIX + "findAllLieferant";
	public static final String PARAM_NAME = "name";
	public static final String PARAM_ID = "id";

	private Long id;
	private String name;
	private String strasse;
	private String plz;
	private String ort;
	private String email;
	private String telefon;
	private String fax;
	private Set<Ansprechpartner> ansprechpartners = new HashSet<Ansprechpartner>(
			0);
	private Set<Bestellung> bestellungs = new HashSet<Bestellung>(0);
	private Set<Artikel> artikels = new HashSet<Artikel>(0);

	public Lieferant() {
	}

	public Lieferant(String name) {
		this.name = name;
	}

	/**
	 * Konstruktor mit nachfolgenden Parameter
	 * @param name
	 * @param strasse
	 * @param plz
	 * @param ort
	 * @param email
	 * @param telefon
	 * @param fax
	 * @param ansprechpartners
	 * @param bestellungs
	 * @param artikels
	 */
	public Lieferant(String name, String strasse, String plz, String ort,
			String email, String telefon, String fax,
			Set<Ansprechpartner> ansprechpartners, Set<Bestellung> bestellungs,
			Set<Artikel> artikels) {
		this.name = name;
		this.strasse = strasse;
		this.plz = plz;
		this.ort = ort;
		this.email = email;
		this.telefon = telefon;
		this.fax = fax;
		this.ansprechpartners = ansprechpartners;
		this.bestellungs = bestellungs;
		this.artikels = artikels;
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

	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "strasse", length = 45)
	public String getStrasse() {
		return this.strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	@Column(name = "plz", length = 45)
	public String getPlz() {
		return this.plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	@Column(name = "ort", length = 45)
	public String getOrt() {
		return this.ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	@Column(name = "email", length = 45)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "telefon", length = 45)
	public String getTelefon() {
		return this.telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	@Column(name = "fax", length = 45)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lieferant")
	public Set<Ansprechpartner> getAnsprechpartners() {
		return this.ansprechpartners;
	}

	public void setAnsprechpartners(Set<Ansprechpartner> ansprechpartners) {
		this.ansprechpartners = ansprechpartners;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lieferant")
	public Set<Bestellung> getBestellungs() {
		return this.bestellungs;
	}

	public void setBestellungs(Set<Bestellung> bestellungs) {
		this.bestellungs = bestellungs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lieferant")
	public Set<Artikel> getArtikels() {
		return this.artikels;
	}

	public void setArtikels(Set<Artikel> artikels) {
		this.artikels = artikels;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Lieferant other = (Lieferant) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lieferant [id=" + id + ", name=" + name + ", strasse="
				+ strasse + ", plz=" + plz + ", ort=" + ort + ", email="
				+ email + ", telefon=" + telefon + ", fax=" + fax + "]";
	}

}

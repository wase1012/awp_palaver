package de.hska.awp.palaver2.lieferantenverwaltung.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;

/**
 * 
 * @author bach1014
 * 
 */
@Entity
@Table(name = "lieferant", catalog = "palaver")
public class Lieferant implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 509321793481530142L;

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
	 * Constructor with
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

	/**
	 * 
	 * @return
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	@Column(name = "strasse", length = 45)
	public String getStrasse() {
		return this.strasse;
	}

	/**
	 * 
	 * @param strasse
	 */
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	/**
	 * 
	 * @return
	 */
	@Column(name = "plz", length = 45)
	public String getPlz() {
		return this.plz;
	}

	/**
	 * 
	 * @param plz
	 */
	public void setPlz(String plz) {
		this.plz = plz;
	}

	/**
	 * 
	 * @return
	 */
	@Column(name = "ort", length = 45)
	public String getOrt() {
		return this.ort;
	}

	/**
	 * 
	 * @param ort
	 */
	public void setOrt(String ort) {
		this.ort = ort;
	}

	/**
	 * 
	 * @return
	 */
	@Column(name = "email", length = 45)
	public String getEmail() {
		return this.email;
	}

	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * @return
	 */
	@Column(name = "telefon", length = 45)
	public String getTelefon() {
		return this.telefon;
	}

	/**
	 * 
	 * @param telefon
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	/**
	 * 
	 * @return
	 */
	@Column(name = "fax", length = 45)
	public String getFax() {
		return this.fax;
	}

	/**
	 * 
	 * @param fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * 
	 * @return
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lieferant")
	public Set<Ansprechpartner> getAnsprechpartners() {
		return this.ansprechpartners;
	}

	/**
	 * 
	 * @param ansprechpartners
	 */
	public void setAnsprechpartners(Set<Ansprechpartner> ansprechpartners) {
		this.ansprechpartners = ansprechpartners;
	}

	/**
	 * 
	 * @return
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lieferant")
	public Set<Bestellung> getBestellungs() {
		return this.bestellungs;
	}

	/**
	 * 
	 * @param bestellungs
	 */
	public void setBestellungs(Set<Bestellung> bestellungs) {
		this.bestellungs = bestellungs;
	}

	/**
	 * 
	 * @return
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lieferant")
	public Set<Artikel> getArtikels() {
		return this.artikels;
	}

	/**
	 * 
	 * @param artikels
	 */
	public void setArtikels(Set<Artikel> artikels) {
		this.artikels = artikels;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Lieferant [id=" + id + ", name=" + name + ", strasse="
				+ strasse + ", plz=" + plz + ", ort=" + ort + ", email="
				+ email + ", telefon=" + telefon + ", fax=" + fax + "]";
	}

}

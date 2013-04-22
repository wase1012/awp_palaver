package de.hska.awp.palaver2.lieferantenverwaltung.domain;

// Generated 21.04.2013 16:08:42 by Hibernate Tools 3.4.0.CR1

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
 * Lieferant generated by hbm2java
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

}

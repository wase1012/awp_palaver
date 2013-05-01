package de.bistrosoft.palaver.lieferantenverwaltung.domain;

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

import de.bistrosoft.palaver.artikelverwaltung.domain.Artikel;
import de.bistrosoft.palaver.bestellverwaltung.domain.Bestellung;

/**
 * 
 * @author bach1014
 * 
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
	private String kundennummer;
	private String bezeichnung;
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
		super();
	}

	public Lieferant(Long id, String name, String kundennummer,
			String bezeichnung, String strasse, String plz, String ort,
			String email, String telefon, String fax) {
		this.id = id;
		this.name = name;
		this.kundennummer = kundennummer;
		this.bezeichnung = bezeichnung;
		this.strasse = strasse;
		this.plz = plz;
		this.ort = ort;
		this.email = email;
		this.telefon = telefon;
		this.fax = fax;
	}

	public Lieferant(String name, String kundennummer, String bezeichnung,
			String strasse, String plz, String ort, String email,
			String telefon, String fax, Set<Ansprechpartner> ansprechpartners,
			Set<Bestellung> bestellungs, Set<Artikel> artikels) {
		this.name = name;
		this.kundennummer = kundennummer;
		this.bezeichnung = bezeichnung;
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

	@Column(name = "kundennummer", length = 45)
	public String getKundennummer() {
		return this.kundennummer;
	}

	public void setKundennummer(String kundennummer) {
		this.kundennummer = kundennummer;
	}

	@Column(name = "bezeichnung", length = 45)
	public String getBezeichnung() {
		return this.bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
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
		result = prime
				* result
				+ ((ansprechpartners == null) ? 0 : ansprechpartners.hashCode());
		result = prime * result
				+ ((artikels == null) ? 0 : artikels.hashCode());
		result = prime * result
				+ ((bestellungs == null) ? 0 : bestellungs.hashCode());
		result = prime * result
				+ ((bezeichnung == null) ? 0 : bezeichnung.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fax == null) ? 0 : fax.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((kundennummer == null) ? 0 : kundennummer.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ort == null) ? 0 : ort.hashCode());
		result = prime * result + ((plz == null) ? 0 : plz.hashCode());
		result = prime * result + ((strasse == null) ? 0 : strasse.hashCode());
		result = prime * result + ((telefon == null) ? 0 : telefon.hashCode());
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
		if (ansprechpartners == null) {
			if (other.ansprechpartners != null)
				return false;
		} else if (!ansprechpartners.equals(other.ansprechpartners))
			return false;
		if (artikels == null) {
			if (other.artikels != null)
				return false;
		} else if (!artikels.equals(other.artikels))
			return false;
		if (bestellungs == null) {
			if (other.bestellungs != null)
				return false;
		} else if (!bestellungs.equals(other.bestellungs))
			return false;
		if (bezeichnung == null) {
			if (other.bezeichnung != null)
				return false;
		} else if (!bezeichnung.equals(other.bezeichnung))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fax == null) {
			if (other.fax != null)
				return false;
		} else if (!fax.equals(other.fax))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kundennummer == null) {
			if (other.kundennummer != null)
				return false;
		} else if (!kundennummer.equals(other.kundennummer))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ort == null) {
			if (other.ort != null)
				return false;
		} else if (!ort.equals(other.ort))
			return false;
		if (plz == null) {
			if (other.plz != null)
				return false;
		} else if (!plz.equals(other.plz))
			return false;
		if (strasse == null) {
			if (other.strasse != null)
				return false;
		} else if (!strasse.equals(other.strasse))
			return false;
		if (telefon == null) {
			if (other.telefon != null)
				return false;
		} else if (!telefon.equals(other.telefon))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lieferant [id=" + id + ", name=" + name + ", kundennummer="
				+ kundennummer + ", bezeichnung=" + bezeichnung + ", strasse="
				+ strasse + ", plz=" + plz + ", ort=" + ort + ", email="
				+ email + ", telefon=" + telefon + ", fax=" + fax
				+ ", ansprechpartners=" + ansprechpartners + ", bestellungs="
				+ bestellungs + ", artikels=" + artikels + "]";
	}

}

package de.bistrosoft.palaver.lieferantenverwaltung.domain;

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

/**
 * 
 * @author bach1014
 *
 */
@Entity
@Table(name = "ansprechpartner", catalog = "palaver")
@NamedQueries({
		@NamedQuery(name = Ansprechpartner.FIND_ANSPRECHPARTNER_BY_NAME, query = "SELECT k FROM Ansprechpartner k WHERE k.name = :"
				+ Ansprechpartner.PARAM_NAME),
		@NamedQuery(name = Ansprechpartner.FIND_ANSPRECHPARTNER_BY_ID, query = "Select k FROM Ansprechpartner k WHERE k.id = :"
				+ Ansprechpartner.PARAM_ID),
		@NamedQuery(name = Ansprechpartner.FIND_ALL_ANSPRECHPARTNER, query = "Select k FROM Ansprechpartner k")
})
public class Ansprechpartner implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8664124200867096510L;

	private static final String PREFIX = "Ansprechpartner.";

	public static final String FIND_ANSPRECHPARTNER_BY_NAME = PREFIX
			+ "findAnsprechpartnerByName";
	public static final String FIND_ANSPRECHPARTNER_BY_ID = PREFIX
			+ "findAnsprechpartnerById";
	public static final String FIND_ALL_ANSPRECHPARTNER = PREFIX + "findAllAnsprechpartner";
	public static final String PARAM_NAME = "name";
	public static final String PARAM_ID = "id";

	private Long id;
	private Lieferant lieferant;
	private String name;
	private String telefon;
	private String handy;
	private String fax;

	public Ansprechpartner() {
	}

	public Ansprechpartner(Lieferant lieferant, String name) {
		this.lieferant = lieferant;
		this.name = name;
	}

	public Ansprechpartner(Lieferant lieferant, String name, String telefon,
			String handy, String fax) {
		this.lieferant = lieferant;
		this.name = name;
		this.telefon = telefon;
		this.handy = handy;
		this.fax = fax;
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
	@JoinColumn(name = "lieferant_fk", nullable = false)
	public Lieferant getLieferant() {
		return this.lieferant;
	}

	public void setLieferant(Lieferant lieferant) {
		this.lieferant = lieferant;
	}

	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "telefon", length = 45)
	public String getTelefon() {
		return this.telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	@Column(name = "handy", length = 45)
	public String getHandy() {
		return this.handy;
	}

	public void setHandy(String handy) {
		this.handy = handy;
	}

	@Column(name = "fax", length = 45)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Override
	public String toString() {
		return "Ansprechpartner [id=" + id + ", lieferant=" + lieferant
				+ ", name=" + name + ", telefon=" + telefon + ", handy="
				+ handy + ", fax=" + fax + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fax == null) ? 0 : fax.hashCode());
		result = prime * result + ((handy == null) ? 0 : handy.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lieferant == null) ? 0 : lieferant.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Ansprechpartner other = (Ansprechpartner) obj;
		if (fax == null) {
			if (other.fax != null)
				return false;
		} else if (!fax.equals(other.fax))
			return false;
		if (handy == null) {
			if (other.handy != null)
				return false;
		} else if (!handy.equals(other.handy))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lieferant == null) {
			if (other.lieferant != null)
				return false;
		} else if (!lieferant.equals(other.lieferant))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (telefon == null) {
			if (other.telefon != null)
				return false;
		} else if (!telefon.equals(other.telefon))
			return false;
		return true;
	}
}

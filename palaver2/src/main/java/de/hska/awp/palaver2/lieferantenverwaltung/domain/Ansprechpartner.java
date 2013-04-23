package de.hska.awp.palaver2.lieferantenverwaltung.domain;

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

	private String fax;

	/**
	 * 
	 */
	public Ansprechpartner() {
	}

	/**
	 * Constructor with
	 * @param lieferant
	 * @param name
	 * @param telefon
	 * @param fax
	 */
	public Ansprechpartner(Lieferant lieferant, String name, String telefon,
			String fax) {
		this.lieferant = lieferant;
		this.name = name;
		this.telefon = telefon;
		this.fax = fax;
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lieferant_fk", nullable = false)
	public Lieferant getLieferant() {
		return this.lieferant;
	}

	/**
	 * 
	 * @param lieferant
	 */
	public void setLieferant(Lieferant lieferant) {
		this.lieferant = lieferant;
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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ansprechpartner other = (Ansprechpartner) obj;
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

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Ansprechpartner [id=" + id + ", lieferant=" + lieferant
				+ ", name=" + name + ", telefon=" + telefon + ", fax=" + fax
				+ "]";
	}

}

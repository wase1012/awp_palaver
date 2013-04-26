package de.hska.awp.palaver2.lieferantenverwaltung.domain;


/**
 * 
 * @author bach1014
 *
 */

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
	
	public String getHandy() {
		return this.handy;
	}

	public void setHandy(String handy) {
		this.handy = handy;
	}

	/**
	 * 
	 * @return
	 */
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Ansprechpartner [id=" + id + ", name=" + name + ", telefon="
				+ telefon + ", handy=" + handy + ", fax=" + fax + "]";
	}

	

}

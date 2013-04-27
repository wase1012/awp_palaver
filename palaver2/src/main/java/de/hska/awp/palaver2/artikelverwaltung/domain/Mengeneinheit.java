package de.hska.awp.palaver2.artikelverwaltung.domain;
/**
 * @author bach1014
 * @edit Mihail Boehm 27.04.2013
 */

public class Mengeneinheit implements java.io.Serializable {

	private static final long serialVersionUID = 5210068506937506344L;

	private Long id;
	private String name;
	private String kurz;

	public Mengeneinheit() {
		super();
	}

	public Mengeneinheit(Long id, String name, String kurz) {
		this.id = id;
		this.name = name;
		this.kurz = kurz;
	}

	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKurz() {
		return this.kurz;
	}

	public void setKurz(String kurz) {
		this.kurz = kurz;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	// Geändert von S.Walz wegen GUI
	@Override
	public String toString() {
		return name;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kurz == null) ? 0 : kurz.hashCode());
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
		Mengeneinheit other = (Mengeneinheit) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kurz == null) {
			if (other.kurz != null)
				return false;
		} else if (!kurz.equals(other.kurz))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}

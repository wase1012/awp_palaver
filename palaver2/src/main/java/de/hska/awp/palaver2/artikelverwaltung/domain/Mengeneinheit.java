/**
 * Created by bach1014
 * 17.04.2013 - 14:44:55
 */
package de.hska.awp.palaver2.artikelverwaltung.domain;

import de.hska.awp.palaver2.bean.Bean;

/**
 * @author bach1014
 *
 */
public class Mengeneinheit implements Bean{

	private Long id;
	private String name;
	private String kurz;
	
	
	
	/**
	 * @param id
	 * @param name
	 * @param kurz
	 */
	public Mengeneinheit(Long id, String name, String kurz) {
		super();
		this.id = id;
		this.name = name;
		this.kurz = kurz;
	}

	/**
	 * @return the id
	 */
	@Override
	public Long getId()
	{
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the kurz
	 */
	public String getKurz() {
		return kurz;
	}
	/**
	 * @param kurz the kurz to set
	 */
	public void setKurz(String kurz) {
		this.kurz = kurz;
	}

	/* (non-Javadoc)
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

	/* (non-Javadoc)
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Mengeneinheit [id=" + id + ", name=" + name + ", kurz=" + kurz
				+ "]";
	}
	
	
	
}

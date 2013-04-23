/**
 *  Created by Sebastian Walz
 */
package de.hska.awp.palaver2.artikelverwaltung.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "kategorie", catalog = "palaver")
public class Kategorie implements java.io.Serializable {
	private static final long serialVersionUID = -4647006694762094910L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "name", nullable = false, length = 45)
	private String name;

	/**
	 * Standardkonstruktor
	 */
	public Kategorie() {
		super();
	}

	/**
	 * Konstruktor
	 * 
	 * @param name
	 *            Kategoriename
	 */
	public Kategorie(String name) {
		this.name = name;
	}

	/**
	 * getId + setId
	 * 
	 * @return KategorieId
	 */
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * getName + setName
	 * 
	 * @return KategorieName
	 */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Kategorie [id=" + id + ", name=" + name + "]";
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
		Kategorie other = (Kategorie) obj;
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
	
	

	
}

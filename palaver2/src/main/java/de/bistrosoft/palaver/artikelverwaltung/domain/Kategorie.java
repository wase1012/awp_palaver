/**
 *  Created by Sebastian Walz
 */
package de.bistrosoft.palaver.artikelverwaltung.domain;

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

@Entity
@Table(name = "kategorie", catalog = "palaver")
public class Kategorie implements java.io.Serializable {

	private static final long 			serialVersionUID = -4647006694762094910L;
	
	private Long id;
	private String name;
	private Set<Artikel> artikels = new HashSet<Artikel>(0);

	public Kategorie() {
	}

	public Kategorie(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Kategorie(String name, Set<Artikel> artikels) {
		this.name = name;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "kategorie")
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
		result = prime * result
				+ ((artikels == null) ? 0 : artikels.hashCode());
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
		Kategorie other = (Kategorie) obj;
		if (artikels == null) {
			if (other.artikels != null)
				return false;
		} else if (!artikels.equals(other.artikels))
			return false;
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
		return "Kategorie [id=" + id + ", name=" + name + ", artikels="
				+ artikels + "]";
	}

}

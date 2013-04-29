package de.bistrosoft.palaver.rezeptverwaltung.domain;

// Generated 24.04.2013 16:47:07 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Geschmack generated by hbm2java
 */
@Entity
@Table(name = "geschmack", catalog = "palaver", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Geschmack implements java.io.Serializable {

	private static final long serialVersionUID = 2070753066033963562L;
	private Long id;
	private String name;
	private Set<Rezept> rezepts = new HashSet<Rezept>(0);

	public Geschmack() {
		super();
	}

	public Geschmack(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", unique = true, nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "geschmack")
	public Set<Rezept> getRezepts() {
		return this.rezepts;
	}

	public void setRezepts(Set<Rezept> rezepts) {
		this.rezepts = rezepts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Geschmack other = (Geschmack) obj;
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
		return name;
	}

}

package de.hska.awp.palaver2.artikelverwaltung.domain;

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
import javax.persistence.UniqueConstraint;

import de.hska.awp.palaver2.rezeptverwaltung.domain.RezeptHasArtikel;

/**
 * 
 * @author bach1014
 *
 */
@Entity
@Table(name = "mengeneinheit", catalog = "palaver", uniqueConstraints = {
		@UniqueConstraint(columnNames = "name"),
		@UniqueConstraint(columnNames = "kurz") })
public class Mengeneinheit implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5210068506937506344L;
	
	private Long id;
	private String name;
	private String kurz;
	private Set<RezeptHasArtikel> rezeptHasArtikels = new HashSet<RezeptHasArtikel>(
			0);
	private Set<Artikel> artikels = new HashSet<Artikel>(0);

	public Mengeneinheit() {
	}

	public Mengeneinheit(String name, String kurz,
			Set<RezeptHasArtikel> rezeptHasArtikels, Set<Artikel> artikels) {
		this.name = name;
		this.kurz = kurz;
		this.rezeptHasArtikels = rezeptHasArtikels;
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

	@Column(name = "name", unique = true, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "kurz", unique = true, length = 5)
	public String getKurz() {
		return this.kurz;
	}

	public void setKurz(String kurz) {
		this.kurz = kurz;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mengeneinheit")
	public Set<RezeptHasArtikel> getRezeptHasArtikels() {
		return this.rezeptHasArtikels;
	}

	public void setRezeptHasArtikels(Set<RezeptHasArtikel> rezeptHasArtikels) {
		this.rezeptHasArtikels = rezeptHasArtikels;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mengeneinheit")
	public Set<Artikel> getArtikels() {
		return this.artikels;
	}

	public void setArtikels(Set<Artikel> artikels) {
		this.artikels = artikels;
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

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Mengeneinheit [id=" + id + ", name=" + name + ", kurz=" + kurz
				+ "]";
	}

}

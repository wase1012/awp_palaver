package de.bistrosoft.palaver.artikelverwaltung.domain;

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
import javax.persistence.UniqueConstraint;

import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;

/**
 * 
 * @author bach1014
 * 
 */
@Entity
@Table(name = "mengeneinheit", catalog = "palaver", uniqueConstraints = {
		@UniqueConstraint(columnNames = "name"),
		@UniqueConstraint(columnNames = "kurz") })
@NamedQueries({
		@NamedQuery(name = Mengeneinheit.FIND_MENGENEINHEIT_BY_KURZ, query = "SELECT k FROM Mengeneinheit k WHERE k.kurz = :"
				+ Mengeneinheit.PARAM_KURZ),
		@NamedQuery(name = Mengeneinheit.FIND_MENGENEINHEIT_BY_NAME, query = "SELECT k FROM Mengeneinheit k WHERE k.name = :"
				+ Mengeneinheit.PARAM_NAME),
		@NamedQuery(name = Mengeneinheit.FIND_MENGENEINHEIT_BY_ID, query = "Select k FROM Mengeneinheit k WHERE k.id = :"
				+ Mengeneinheit.PARAM_ID),
		@NamedQuery(name = Mengeneinheit.FIND_ALL_MENGENEINHEIT, query = "Select k FROM Mengeneinheit k") })
public class Mengeneinheit implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5210068506937506344L;

	private static final String PREFIX = "Mengeneinheit.";

	public static final String FIND_MENGENEINHEIT_BY_NAME = PREFIX
			+ "findMengeneinheitByName";
	public static final String FIND_MENGENEINHEIT_BY_ID = PREFIX
			+ "findMengeneinheitById";
	public static final String FIND_MENGENEINHEIT_BY_KURZ = PREFIX
			+ "findMengeneinheitByKurz";
	public static final String FIND_ALL_MENGENEINHEIT = PREFIX
			+ "findAllMengeneinheit";
	public static final String PARAM_NAME = "name";
	public static final String PARAM_ID = "id";
	public static final String PARAM_KURZ = "kurz";

	private Long id;
	private String name;
	private String kurz;
	private Set<RezeptHasArtikel> rezeptHasArtikels = new HashSet<RezeptHasArtikel>(
			0);
	private Set<Artikel> artikels = new HashSet<Artikel>(0);

	public Mengeneinheit() {
		super();
	}

	public Mengeneinheit(Long id, String name, String kurz) {
		this.id = id;
		this.name = name;
		this.kurz = kurz;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((artikels == null) ? 0 : artikels.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kurz == null) ? 0 : kurz.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((rezeptHasArtikels == null) ? 0 : rezeptHasArtikels
						.hashCode());
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
		Mengeneinheit other = (Mengeneinheit) obj;
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
		if (rezeptHasArtikels == null) {
			if (other.rezeptHasArtikels != null)
				return false;
		} else if (!rezeptHasArtikels.equals(other.rezeptHasArtikels))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return kurz;
	}

}

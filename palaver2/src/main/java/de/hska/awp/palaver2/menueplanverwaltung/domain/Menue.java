package de.hska.awp.palaver2.menueplanverwaltung.domain;

// Generated 21.04.2013 16:08:42 by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.rezeptverwaltung.domain.Rezept;

/**
 * Menue generated by hbm2java
 */
@Entity
@Table(name = "menue", catalog = "palaver", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Menue implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -489234749011441338L;
	private Integer id;
	private Mitarbeiter mitarbeiter;
	private String name;
	private Set<Rezept> rezepts = new HashSet<Rezept>(0);
	private Set<Menueplan> menueplans = new HashSet<Menueplan>(0);

	public Menue() {
	}

	public Menue(Mitarbeiter mitarbeiter, String name) {
		this.mitarbeiter = mitarbeiter;
		this.name = name;
	}

	public Menue(Mitarbeiter mitarbeiter, String name, Set<Rezept> rezepts,
			Set<Menueplan> menueplans) {
		this.mitarbeiter = mitarbeiter;
		this.name = name;
		this.rezepts = rezepts;
		this.menueplans = menueplans;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "koch", nullable = false)
	public Mitarbeiter getMitarbeiter() {
		return this.mitarbeiter;
	}

	public void setMitarbeiter(Mitarbeiter mitarbeiter) {
		this.mitarbeiter = mitarbeiter;
	}

	@Column(name = "name", unique = true, nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "menue_has_rezept", catalog = "palaver", joinColumns = { @JoinColumn(name = "menue_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "rezept_id", nullable = false, updatable = false) })
	public Set<Rezept> getRezepts() {
		return this.rezepts;
	}

	public void setRezepts(Set<Rezept> rezepts) {
		this.rezepts = rezepts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "menue")
	public Set<Menueplan> getMenueplans() {
		return this.menueplans;
	}

	public void setMenueplans(Set<Menueplan> menueplans) {
		this.menueplans = menueplans;
	}

}

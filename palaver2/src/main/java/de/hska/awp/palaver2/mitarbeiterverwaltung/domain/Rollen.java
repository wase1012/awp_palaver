package de.hska.awp.palaver2.mitarbeiterverwaltung.domain;

// Generated 21.04.2013 16:08:42 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Rollen generated by hbm2java
 */
@Entity
@Table(name = "rollen", catalog = "palaver", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Rollen implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3366000000412110979L;
	private Integer id;
	private String name;
	private Set<Mitarbeiter> mitarbeiters = new HashSet<Mitarbeiter>(0);

	public Rollen() {
	}

	public Rollen(String name) {
		this.name = name;
	}

	public Rollen(String name, Set<Mitarbeiter> mitarbeiters) {
		this.name = name;
		this.mitarbeiters = mitarbeiters;
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

	@Column(name = "name", unique = true, nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "mitarbeiter_has_rollen", catalog = "palaver", joinColumns = { @JoinColumn(name = "rollen_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "mitarbeiter_mitarbeiternummer", nullable = false, updatable = false) })
	public Set<Mitarbeiter> getMitarbeiters() {
		return this.mitarbeiters;
	}

	public void setMitarbeiters(Set<Mitarbeiter> mitarbeiters) {
		this.mitarbeiters = mitarbeiters;
	}

}

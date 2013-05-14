package de.bistrosoft.palaver.rezeptverwaltung.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;

public class Rezept implements java.io.Serializable {

	private static final long serialVersionUID = 7984117576450240771L;

	private Long id;
	private Geschmack geschmack;
	private Rezeptart rezeptart;
	private Mitarbeiter mitarbeiter;
	private String name;
	private String kommentar;
	private int portion;
	private boolean aufwand;
	private boolean favorit;
	private Date erstellt;
	private List<RezeptHasArtikel> artikel;

	public List<RezeptHasArtikel> getArtikel() {
		return artikel;
	}

	public void setArtikel(List<RezeptHasArtikel> artikel) {
		this.artikel = artikel;
	}

	public Rezept() {
		super();
	}

	public Rezept(Rezeptart rezeptart, Geschmack geschmack,
			Mitarbeiter mitarbeiter, String name, String kommentar, int portion) {
		super();
		this.rezeptart = rezeptart;
		this.geschmack = geschmack;
		this.mitarbeiter = mitarbeiter;
		this.name = name;
		this.kommentar = kommentar;
		this.portion = portion;
	}

	public Rezept(Long id, Rezeptart rezeptart, Geschmack geschmack,
			Mitarbeiter mitarbeiter, String name, String kommentar, int portion) {
		super();
		this.id = id;
		this.rezeptart = rezeptart;
		this.geschmack = geschmack;
		this.mitarbeiter = mitarbeiter;
		this.name = name;
		this.kommentar = kommentar;
		this.portion = portion;
	}

	public Rezept(Long id, Geschmack geschmack, Rezeptart rezeptart,
			Mitarbeiter mitarbeiter, String name, String kommentar,
			int portion, boolean aufwand, Date erstellt, boolean favorit) {
		super();
		this.id = id;
		this.geschmack = geschmack;
		this.rezeptart = rezeptart;
		this.mitarbeiter = mitarbeiter;
		this.name = name;
		this.kommentar = kommentar;
		this.portion = portion;
		this.aufwand = aufwand;
		this.erstellt = erstellt;
		this.favorit = favorit;
	}

	public Rezept(Long id) {
		super();
		this.id = id;

	}

	// public Rezept(Rezeptart rezeptart2, Geschmack geschmack2, Mitarbeiter
	// mitarbeiter2, String string) {
	// }
	//
	// public Rezept(Rezeptart rezeptart, Geschmack geschmack, String name,
	// Mitarbeiter mitarbeiter) {
	// this.rezeptart = rezeptart;
	// this.geschmack = geschmack;
	// this.name = name;
	// this.mitarbeiter = mitarbeiter;
	// }
	//
	// public Rezept(Geschmack geschmack, Rezeptart rezeptart,
	// Mitarbeiter mitarbeiter, String name, String kommentar,
	// int portion, Set<Menue> menues,
	// Set<RezeptHasFussnote> rezeptHasFussnotes,
	// Set<RezeptHasArtikel> rezeptHasArtikels,
	// RezeptHasZubereitung rezeptHasZubereitung) {
	// this.geschmack = geschmack;
	// this.rezeptart = rezeptart;
	// this.mitarbeiter = mitarbeiter;
	// this.name = name;
	// this.kommentar = kommentar;
	// this.portion = portion;
	// // this.menues = menues;
	// this.rezeptHasFussnotes = rezeptHasFussnotes;
	// this.rezeptHasArtikels = rezeptHasArtikels;
	// this.rezeptHasZubereitung = rezeptHasZubereitung;
	// }

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "geschmack_fk")
	public Geschmack getGeschmack() {
		return this.geschmack;
	}

	public void setGeschmack(Geschmack geschmack) {
		this.geschmack = geschmack;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rezeptart_fk", nullable = false)
	public Rezeptart getRezeptart() {
		return this.rezeptart;
	}

	public void setRezeptart(Rezeptart rezeptart) {
		this.rezeptart = rezeptart;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mitarbeiter_fk")
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

	@Column(name = "kommentar", length = 1000)
	public String getKommentar() {
		return this.kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	@Column(name = "portion", nullable = false)
	public int getPortion() {
		return this.portion;
	}

	public void setPortion(int portion) {
		this.portion = portion;
	}

	// @PrePersist
	// private void prePersist() {
	// erstellt = new Date();
	// }
	//
	// public Date getErzeugt() {
	// return erstellt == null ? null : (Date) erstellt.clone();
	// }
	//
	// public void setErzeugt() {
	// this.erstellt = erstellt == null ? null : (Date) erstellt.clone();
	// }

	// @ManyToMany(fetch = FetchType.LAZY)
	// @JoinTable(name = "menue_has_rezept", catalog = "palaver", joinColumns =
	// { @JoinColumn(name = "rezept_id", nullable = false, updatable = false) },
	// inverseJoinColumns = { @JoinColumn(name = "menue_id", nullable = false,
	// updatable = false) })
	// public Set<Menue> getMenues() {
	// return this.menues;
	// }
	//
	// public void setMenues(Set<Menue> menues) {
	// this.menues = menues;
	// }

	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "rezept")
	// public Set<RezeptHasFussnote> getRezeptHasFussnotes() {
	// return this.rezeptHasFussnotes;
	// }
	//
	// public void setRezeptHasFussnotes(Set<RezeptHasFussnote>
	// rezeptHasFussnotes) {
	// this.rezeptHasFussnotes = rezeptHasFussnotes;
	// }
	//
	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "rezept")
	// public Set<RezeptHasArtikel> getRezeptHasArtikels() {
	// return this.rezeptHasArtikels;
	// }
	//
	// public void setRezeptHasArtikels(Set<RezeptHasArtikel> rezeptHasArtikels)
	// {
	// this.rezeptHasArtikels = rezeptHasArtikels;
	// }
	//
	// @OneToOne(fetch = FetchType.LAZY, mappedBy = "rezept")
	// public RezeptHasZubereitung getRezeptHasZubereitung() {
	// return this.rezeptHasZubereitung;
	// }
	//
	// public void setRezeptHasZubereitung(
	// RezeptHasZubereitung rezeptHasZubereitung) {
	// this.rezeptHasZubereitung = rezeptHasZubereitung;
	// }

	public Date getErstellt() {
		return erstellt;
	}

	public void setErstellt(Date erstellt) {
		this.erstellt = erstellt;
	}

	public boolean getAufwand() {
		return aufwand;
	}

	public void setAufwand(boolean aufwand) {
		this.aufwand = aufwand;
	}

	public boolean getFavorit() {
		return this.favorit;
	}

	public void setFavorit(boolean favorit) {
		this.favorit = favorit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		// result = prime * result + ((artikel == null) ? 0 :
		// artikel.hashCode());
		// result = prime * result + (aufwand ? 1231 : 1237);
		// result = prime * result
		// + ((erstellt == null) ? 0 : erstellt.hashCode());
		// result = prime * result + (favorit ? 1231 : 1237);
		result = prime * result
				+ ((geschmack == null) ? 0 : geschmack.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((kommentar == null) ? 0 : kommentar.hashCode());
		result = prime * result
				+ ((mitarbeiter == null) ? 0 : mitarbeiter.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + portion;
		result = prime * result
				+ ((rezeptart == null) ? 0 : rezeptart.hashCode());
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
		Rezept other = (Rezept) obj;
		if (artikel == null) {
			if (other.artikel != null)
				return false;
		} else if (!artikel.equals(other.artikel))
			return false;
		if (aufwand != other.aufwand)
			return false;
		if (erstellt == null) {
			if (other.erstellt != null)
				return false;
		} else if (!erstellt.equals(other.erstellt))
			return false;
		if (favorit != other.favorit)
			return false;
		if (geschmack == null) {
			if (other.geschmack != null)
				return false;
		} else if (!geschmack.equals(other.geschmack))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kommentar == null) {
			if (other.kommentar != null)
				return false;
		} else if (!kommentar.equals(other.kommentar))
			return false;
		if (mitarbeiter == null) {
			if (other.mitarbeiter != null)
				return false;
		} else if (!mitarbeiter.equals(other.mitarbeiter))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (portion != other.portion)
			return false;
		if (rezeptart == null) {
			if (other.rezeptart != null)
				return false;
		} else if (!rezeptart.equals(other.rezeptart))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "" + id + "";
	}

}

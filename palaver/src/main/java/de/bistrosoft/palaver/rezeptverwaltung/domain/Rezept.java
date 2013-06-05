/**
 * Created by Michael Marschall
 */
package de.bistrosoft.palaver.rezeptverwaltung.domain;

import java.sql.Date;
import java.util.List;

import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;

public class Rezept implements java.io.Serializable {

	private static final long serialVersionUID = 7984117576450240771L;

	//Variablen
	private Long id;
	private Rezeptart rezeptart;
	private Mitarbeiter mitarbeiter;
	private String name;
	private String kommentar;
	private Date erstellt;
	private List<RezeptHasArtikel> artikel;
	private Boolean menue;
	private List<Zubereitung> zubereitung;

	//Konstruktoren
	public Rezept() {
		super();
	}

	public Rezept(Rezeptart rezeptart, Mitarbeiter mitarbeiter, String name,
			String kommentar) {
		super();
		this.rezeptart = rezeptart;
		this.mitarbeiter = mitarbeiter;
		this.name = name;
		this.kommentar = kommentar;
	}

	public Rezept(Long id, Rezeptart rezeptart, Mitarbeiter mitarbeiter,
			String name, String kommentar) {
		super();
		this.id = id;
		this.rezeptart = rezeptart;
		this.mitarbeiter = mitarbeiter;
		this.name = name;
		this.kommentar = kommentar;
	}

	public Rezept(Long id, Rezeptart rezeptart, Mitarbeiter mitarbeiter,
			String name, String kommentar, Date erstellt) {
		super();
		this.id = id;
		this.rezeptart = rezeptart;
		this.mitarbeiter = mitarbeiter;
		this.name = name;
		this.kommentar = kommentar;
		this.erstellt = erstellt;
	}

	public Rezept(Long id) {
		super();
		this.id = id;
	}
	
	//Getter- und Setter
	public Boolean getMenue() {
		return menue;
	}

	public void setMenue(Boolean menue) {
		this.menue = menue;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Zubereitung> getZubereitung() {
		return zubereitung;
	}

	public void setZubereitung(List<Zubereitung> zubereitung) {
		this.zubereitung = zubereitung;
	}

	public Rezeptart getRezeptart() {
		return this.rezeptart;
	}

	public void setRezeptart(Rezeptart rezeptart) {
		this.rezeptart = rezeptart;
	}

	public Mitarbeiter getMitarbeiter() {
		return this.mitarbeiter;
	}

	public void setMitarbeiter(Mitarbeiter mitarbeiter) {
		this.mitarbeiter = mitarbeiter;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKommentar() {
		return this.kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	public Date getErstellt() {
		return erstellt;
	}

	public void setErstellt(Date erstellt) {
		this.erstellt = erstellt;
	}

	public List<RezeptHasArtikel> getArtikel() {
		return artikel;
	}

	public void setArtikel(List<RezeptHasArtikel> artikel) {
		this.artikel = artikel;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((kommentar == null) ? 0 : kommentar.hashCode());
		result = prime * result
				+ ((mitarbeiter == null) ? 0 : mitarbeiter.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((rezeptart == null) ? 0 : rezeptart.hashCode());
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
		Rezept other = (Rezept) obj;
		if (artikel == null) {
			if (other.artikel != null)
				return false;
		} else if (!artikel.equals(other.artikel))
			return false;
		if (erstellt == null) {
			if (other.erstellt != null)
				return false;
		} else if (!erstellt.equals(other.erstellt))
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
		if (rezeptart == null) {
			if (other.rezeptart != null)
				return false;
		} else if (!rezeptart.equals(other.rezeptart))
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "" + id + "";
	}
}

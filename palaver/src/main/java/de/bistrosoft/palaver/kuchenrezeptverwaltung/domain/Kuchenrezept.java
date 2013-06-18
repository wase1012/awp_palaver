package de.bistrosoft.palaver.kuchenrezeptverwaltung.domain;

import java.sql.Date;
import java.util.List;

public class Kuchenrezept implements java.io.Serializable {

	private static final long serialVersionUID = 7984117576450240771L;

	private Long id;
	private String name;
	private String kommentar;
	private Date erstellt;
	private List<KuchenrezeptHasArtikel> artikel;
	List<FussnoteKuchen> fussnotekuchen;
	private String fussnoten;
	private Boolean aktiv;
	
	public List<KuchenrezeptHasArtikel> getArtikel() {
		return artikel;
	}

	public void setArtikel(List<KuchenrezeptHasArtikel> artikel) {
		this.artikel = artikel;
	}

	public Kuchenrezept() {
		super();
	}
	
	public Kuchenrezept(String name, String fn) {
		super();
		this.name = name;
		this.fussnoten = fn;
	}

	public Kuchenrezept(Long id, String name,
			String kommentar) {
		super();
		this.id = id;
		this.name = name;
		this.kommentar = kommentar;
	}
	
	public Kuchenrezept(Long id, String name,
			String kommentar, Boolean aktiv) {
		super();
		this.id = id;
		this.name = name;
		this.kommentar = kommentar;
		this.aktiv = aktiv;
	}

	public Kuchenrezept(Long id, String name,
			String kommentar, Date erstellt, Boolean aktiv) {
		super();
		this.id = id;
		this.name = name;
		this.kommentar = kommentar;
		this.erstellt = erstellt;
		this.aktiv = aktiv;
	}
	public Kuchenrezept(Long id, String name,
			String kommentar, Date erstellt) {
		super();
		this.id = id;
		this.name = name;
		this.kommentar = kommentar;
		this.erstellt = erstellt;
	}
	
	public Kuchenrezept(Long id) {
		super();
		this.id = id;

	}
	public List<FussnoteKuchen> getFussnoteKuchen() {
		return fussnotekuchen;
	}

	public void setFussnoteKuchen(List<FussnoteKuchen> fussnotekuchen) {
		this.fussnotekuchen = fussnotekuchen;
	}
	
	public String getFussnoten() {
		return fussnoten;
	}
	
	public void setFussnoten(String fussnoten) {
		this.fussnoten = fussnoten;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getAktiv() {
		return aktiv;
	}

	public void setAktiv(Boolean aktiv) {
		this.aktiv = aktiv;
	}
//	
	@Override
	public String toString() {
		return "" + id + " " + fussnoten;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artikel == null) ? 0 : artikel.hashCode());
		result = prime * result
				+ ((erstellt == null) ? 0 : erstellt.hashCode());
		result = prime * result
				+ ((fussnotekuchen == null) ? 0 : fussnotekuchen.hashCode());
		result = prime * result
				+ ((fussnoten == null) ? 0 : fussnoten.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((aktiv == null) ? 0 : aktiv.hashCode());
		result = prime * result
				+ ((kommentar == null) ? 0 : kommentar.hashCode());
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
		Kuchenrezept other = (Kuchenrezept) obj;
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
		if (fussnotekuchen == null) {
			if (other.fussnotekuchen != null)
				return false;
		} else if (!fussnotekuchen.equals(other.fussnotekuchen))
			return false;
		if (fussnoten == null) {
			if (other.fussnoten != null)
				return false;
		} else if (!fussnoten.equals(other.fussnoten))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (aktiv == null) {
			if (other.aktiv != null)
				return false;
		} else if (!aktiv.equals(other.aktiv))
			return false;
		if (kommentar == null) {
			if (other.kommentar != null)
				return false;
		} else if (!kommentar.equals(other.kommentar))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	

}

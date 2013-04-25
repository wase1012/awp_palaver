package de.hska.awp.palaver2.bestellverwaltung.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;



/**
 * @author Elena W
 * Die Klasse Bestellposition spiegelt den Bestellposition aus der Datenbank wieder
 */
@Entity
@Table(name = "bestellposition", catalog = "palaver")

@NamedQueries({
	@NamedQuery(name = Bestellposition.FIND_BESTELLPOSITION_BY_ID, query = "Select k FROM Bestellposition k WHERE k.id = :"
			+ Bestellposition.PARAM_ID),
	@NamedQuery(name = Bestellposition.FIND_ALL_BESTELLPOSITION, query = "Select k FROM Bestellposition k")
			})
	
	
	public class Bestellposition implements java.io.Serializable {

	private static final long serialVersionUID = -3160765850839739452L;	
	private static final String PREFIX = "Bestellposition.";
	public static final String FIND_BESTELLPOSITION_BY_ID = PREFIX
			+ "findBestellpositionById";
	public static final String FIND_ALL_BESTELLPOSITION = PREFIX + "findAllBestellposition";
	public static final String PARAM_ID = "id";

	private Long id;
	private Artikel artikel;
	private Bestellung bestellung;	
	private int menge;

	public Bestellposition() {
	}


	public Bestellposition(Artikel artikel, Bestellung bestellung, int menge) {
		this.artikel = artikel;
		this.bestellung = bestellung;
		this.menge = menge;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "artikel_fk", nullable = false)
	public Artikel getArtikel() {
		return this.artikel;
	}

	
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bestellung_fk", nullable = false)
	public Bestellung getBestellung() {
		return this.bestellung;
	}

	
	public void setBestellung(Bestellung bestellung) {
		this.bestellung = bestellung;
	}

	@Column(name = "menge", nullable = false)
	public int getMenge() {
		return this.menge;
	}

	
	public void setMenge(int menge) {
		this.menge = menge;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artikel == null) ? 0 : artikel.hashCode());
		result = prime * result
				+ ((bestellung == null) ? 0 : bestellung.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + menge;
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
		Bestellposition other = (Bestellposition) obj;
		if (artikel == null) {
			if (other.artikel != null)
				return false;
		} else if (!artikel.equals(other.artikel))
			return false;
		if (bestellung == null) {
			if (other.bestellung != null)
				return false;
		} else if (!bestellung.equals(other.bestellung))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (menge != other.menge)
			return false;
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bestellposition [id=" + id + ", artikel=" + artikel
				+ ", bestellung=" + bestellung + ", menge=" + menge + "]";
	}

}

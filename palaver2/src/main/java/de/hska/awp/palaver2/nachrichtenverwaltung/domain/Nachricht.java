package de.hska.awp.palaver2.nachrichtenverwaltung.domain;

// Generated 21.04.2013 16:08:42 by PhilippT Tools 3.4.0.CR1

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

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;

/**
 * Nachricht generated by PhilippT
 */
@Entity
@Table(name = "nachrichten", catalog = "palaver")
@NamedQueries({
	@NamedQuery(name = Nachricht.FIND_Nachricht_BY_ID, query = "Select n FROM Nachricht n WHERE n.id = :"
			+ Ansprechpartner.PARAM_ID),
	@NamedQuery(name = Nachricht.FIND_ALL_Nachricht, query = "Select n FROM Nachricht n") } )

public class Nachricht implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1291141882464373163L;
	
	private static final String PREFIX = "Nachricht.";
	public static final String FIND_Nachricht_BY_ID = PREFIX
			+ "findNachrichtById";
	public static final String FIND_ALL_Nachricht = PREFIX + "findAllNachricht";
	public static final String PARAM_ID = "id";
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_fk", nullable = false)
	private Mitarbeiter mitarbeiterBySenderFk;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empfaenger_fk", nullable = false)
	private Mitarbeiter mitarbeiterByEmpfaengerFk;
	
	@Column(name = "nachricht", length = 300)
	private String nachricht;

	public Nachricht() {
	}

	public Nachricht(Mitarbeiter mitarbeiterBySenderFk,
			Mitarbeiter mitarbeiterByEmpfaengerFk) {
		this.mitarbeiterBySenderFk = mitarbeiterBySenderFk;
		this.mitarbeiterByEmpfaengerFk = mitarbeiterByEmpfaengerFk;
	}

	public Nachricht(Mitarbeiter mitarbeiterBySenderFk,
			Mitarbeiter mitarbeiterByEmpfaengerFk, String nachricht) {
		this.mitarbeiterBySenderFk = mitarbeiterBySenderFk;
		this.mitarbeiterByEmpfaengerFk = mitarbeiterByEmpfaengerFk;
		this.nachricht = nachricht;
	}


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Mitarbeiter getMitarbeiterBySenderFk() {
		return this.mitarbeiterBySenderFk;
	}

	public void setMitarbeiterBySenderFk(Mitarbeiter mitarbeiterBySenderFk) {
		this.mitarbeiterBySenderFk = mitarbeiterBySenderFk;
	}

	public Mitarbeiter getMitarbeiterByEmpfaengerFk() {
		return this.mitarbeiterByEmpfaengerFk;
	}

	public void setMitarbeiterByEmpfaengerFk(
			Mitarbeiter mitarbeiterByEmpfaengerFk) {
		this.mitarbeiterByEmpfaengerFk = mitarbeiterByEmpfaengerFk;
	}

	public String getNachricht() {
		return this.nachricht;
	}

	public void setNachricht(String nachricht) {
		this.nachricht = nachricht;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Nachricht [id=" + id + ", nachricht=" + nachricht + "]";
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
				+ ((nachricht == null) ? 0 : nachricht.hashCode());
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
		Nachricht other = (Nachricht) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nachricht == null) {
			if (other.nachricht != null)
				return false;
		} else if (!nachricht.equals(other.nachricht))
			return false;
		return true;
	}

}

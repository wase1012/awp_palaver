package de.hska.awp.palaver2.nachrichtenverwaltung.domain;

import de.hska.awp.palaver2.bean.Bean;

public class Nachricht implements Bean {

	private Long			id;
	private String			nachricht;
//	private Mitarbeiter		sender;
//	private Mitarbeiter		empfaenger;
	
	public Nachricht(Long id, String nachricht) {
		super();
		this.id = id;
		this.nachricht = nachricht;
	}

	/**
	 * @return the nachricht
	 */
	public String getNachricht() {
		return nachricht;
	}

	/**
	 * @param nachricht the nachricht to set
	 */
	public void setNachricht(String nachricht) {
		this.nachricht = nachricht;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
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

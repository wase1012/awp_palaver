package de.hska.awp.palaver2.emailversand;

public class MailModel {
	
	private long id;
	private String passwort;
	private String schlussel;
	private String descript;
	private long length;
	
	public MailModel(Long id, String passwort, String key, long length, String zweck){
		super();
		this.id = id;
		this.passwort = passwort;
		this.schlussel = key;
		this.descript = zweck;
		this.length = length;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (length ^ (length >>> 32));
		result = prime * result
				+ ((passwort == null) ? 0 : passwort.hashCode());
		result = prime * result
				+ ((schlussel == null) ? 0 : schlussel.hashCode());
		result = prime * result + ((descript == null) ? 0 : descript.hashCode());
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
		MailModel other = (MailModel) obj;
		if (id != other.id)
			return false;
		if (length != other.length)
			return false;
		if (passwort == null) {
			if (other.passwort != null)
				return false;
		} else if (!passwort.equals(other.passwort))
			return false;
		if (schlussel == null) {
			if (other.schlussel != null)
				return false;
		} else if (!schlussel.equals(other.schlussel))
			return false;
		if (descript == null) {
			if (other.descript != null)
				return false;
		} else if (!descript.equals(other.descript))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MailModel [id=" + id + ", passwort=" + passwort
				+ ", schlussel=" + schlussel + ", zweck=" + descript + ", length="
				+ length + "]";
	}

	/**
	 * @return the length
	 */
	public long getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(long length) {
		this.length = length;
	}

	public MailModel() {
		super();
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the passwort
	 */
	public String getPasswort() {
		return passwort;
	}

	/**
	 * @param passwort the passwort to set
	 */
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	/**
	 * @return the schlussel
	 */
	public String getSchlussel() {
		return schlussel;
	}

	/**
	 * @param schlussel the schlussel to set
	 */
	public void setSchlussel(String schlussel) {
		this.schlussel = schlussel;
	}	
	/**
	 * @return the zweck
	 */
	public String getDescript() {
		return descript;
	}

	/**
	 * @param zweck the zweck to set
	 */
	public void setZweck(String zweck) {
		this.descript = zweck;
	}
}


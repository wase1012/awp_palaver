package de.hska.awp.palaver2.menueplanverwaltung.domain;

// Generated 21.04.2013 16:08:42 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MenueplanId generated by hbm2java
 */
@Embeddable
public class MenueplanId implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1052982117801211000L;
	private Date datum;
	private int position;

	public MenueplanId() {
	}

	public MenueplanId(Date datum, int position) {
		this.datum = datum;
		this.position = position;
	}

	@Column(name = "datum", nullable = false, length = 10)
	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	@Column(name = "position", nullable = false)
	public int getPosition() {
		return this.position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MenueplanId))
			return false;
		MenueplanId castOther = (MenueplanId) other;

		return ((this.getDatum() == castOther.getDatum()) || (this.getDatum() != null
				&& castOther.getDatum() != null && this.getDatum().equals(
				castOther.getDatum())))
				&& (this.getPosition() == castOther.getPosition());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDatum() == null ? 0 : this.getDatum().hashCode());
		result = 37 * result + this.getPosition();
		return result;
	}

}

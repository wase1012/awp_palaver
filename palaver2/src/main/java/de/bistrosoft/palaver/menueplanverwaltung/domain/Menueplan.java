package de.bistrosoft.palaver.menueplanverwaltung.domain;

// Generated 21.04.2013 16:08:42 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Menueplan generated by hbm2java
 */
@Entity
@Table(name = "menueplan", catalog = "palaver")
public class Menueplan implements java.io.Serializable {


	private static final long serialVersionUID = 8694962424700161570L;
	private MenueplanId id;
	private Menue menue;

	public Menueplan() {
	}

	public Menueplan(MenueplanId id, Menue menue) {
		this.id = id;
		this.menue = menue;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "datum", column = @Column(name = "datum", nullable = false, length = 10)),
			@AttributeOverride(name = "position", column = @Column(name = "position", nullable = false)) })
	public MenueplanId getId() {
		return this.id;
	}

	public void setId(MenueplanId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menue", nullable = false)
	public Menue getMenue() {
		return this.menue;
	}

	public void setMenue(Menue menue) {
		this.menue = menue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((menue == null) ? 0 : menue.hashCode());
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
		Menueplan other = (Menueplan) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (menue == null) {
			if (other.menue != null)
				return false;
		} else if (!menue.equals(other.menue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Menueplan [id=" + id + ", menue=" + menue + "]";
	}

}

package de.bistrosoft.palaver.menueplanverwaltung.domain;



import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;


@Entity
@Table(name = "menue_has_fussnote", catalog = "palaver")
public class MenueHasFussnote implements java.io.Serializable {

	private MenueHasFussnoteId id;
	private Fussnote fussnote;
	private Menue menue;

	public MenueHasFussnote() {
	}

	public MenueHasFussnote(MenueHasFussnoteId id, Fussnote fussnote,
			Menue menue) {
		this.id = id;
		this.fussnote = fussnote;
		this.menue = menue;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "menueFk", column = @Column(name = "menue_fk", nullable = false)),
			@AttributeOverride(name = "fussnoteFk", column = @Column(name = "fussnote_fk", nullable = false)) })
	public MenueHasFussnoteId getId() {
		return this.id;
	}

	public void setId(MenueHasFussnoteId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fussnote_fk", nullable = false, insertable = false, updatable = false)
	public Fussnote getFussnote() {
		return this.fussnote;
	}

	public void setFussnote(Fussnote fussnote) {
		this.fussnote = fussnote;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menue_fk", nullable = false, insertable = false, updatable = false)
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
		result = prime * result
				+ ((fussnote == null) ? 0 : fussnote.hashCode());
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
		MenueHasFussnote other = (MenueHasFussnote) obj;
		if (fussnote == null) {
			if (other.fussnote != null)
				return false;
		} else if (!fussnote.equals(other.fussnote))
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
		return "menueHasFussnote [fussnote=" + fussnote + ", menue=" + menue
				+ "]";
	}

}

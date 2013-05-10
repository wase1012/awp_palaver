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
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;


@Entity
@Table(name = "menue_has_fussnote", catalog = "palaver")
public class MenueHasRezept implements java.io.Serializable {

	private MenueHasRezeptId id;
	private Menue menue;
	private Rezept rezept;

	public MenueHasRezept() {
	}

	public MenueHasRezept(MenueHasRezeptId id, 
			Menue menue, Rezept rezept) {
		this.id = id;
		this.menue = menue;
		this.rezept = rezept;
	}
	
	public MenueHasRezept(Menue menue, Rezept rezept) {
		
		this.menue = menue;
		this.rezept = rezept;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "menueFk", column = @Column(name = "menue_fk", nullable = false)),
			@AttributeOverride(name = "rezeptFk", column = @Column(name = "rezept_fk", nullable = false)) })
	public MenueHasRezeptId getId() {
		return this.id;
	}

	public void setId(MenueHasRezeptId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rezept_fk", nullable = false, insertable = false, updatable = false)
	public Rezept getRezept() {
		return rezept;  ///stand bei beidem noch this.
	}

	public void setRezept(Rezept rezept) {
		this.rezept = rezept;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menue_fk", nullable = false, insertable = false, updatable = false)
	public Menue getMenue() {
		return menue;
	}

	public void setMenue(Menue menue) {
		this.menue = menue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((rezept == null) ? 0 : rezept.hashCode());
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
		MenueHasRezept other = (MenueHasRezept) obj;
		if (rezept == null) {
			if (other.rezept != null)
				return false;
		} else if (!rezept.equals(other.rezept))
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
		return "menueHasFussnote [fussnote=" + rezept + ", menue=" + menue
				+ "]";
	}

}

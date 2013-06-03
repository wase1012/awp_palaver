package de.bistrosoft.palaver.rezeptverwaltung.domain;

import java.util.HashSet;
import java.util.Set;

/*
 * @author Jan Lauinger
 */
public class Zubereitung implements java.io.Serializable {

	private static final long serialVersionUID = -962227872830047781L;
	
	//Variablen
	private Long id;
	private String name;
	private Set<RezeptHasZubereitung> rezeptHasZubereitungs = new HashSet<RezeptHasZubereitung>(
			0);

	//Konstruktoren
	public Zubereitung() {
	}

	public Zubereitung(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Zubereitung(Long id, String name,
			Set<RezeptHasZubereitung> rezeptHasZubereitungs) {
		this.id = id;
		this.name = name;
		this.rezeptHasZubereitungs = rezeptHasZubereitungs;
	}
	
	//Getter- und Setter
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

	public Set<RezeptHasZubereitung> getRezeptHasZubereitungs() {
		return this.rezeptHasZubereitungs;
	}

	public void setRezeptHasZubereitungs(
			Set<RezeptHasZubereitung> rezeptHasZubereitungs) {
		this.rezeptHasZubereitungs = rezeptHasZubereitungs;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((rezeptHasZubereitungs == null) ? 0 : rezeptHasZubereitungs
						.hashCode());
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
		Zubereitung other = (Zubereitung) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (rezeptHasZubereitungs == null) {
			if (other.rezeptHasZubereitungs != null)
				return false;
		} else if (!rezeptHasZubereitungs.equals(other.rezeptHasZubereitungs))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
}

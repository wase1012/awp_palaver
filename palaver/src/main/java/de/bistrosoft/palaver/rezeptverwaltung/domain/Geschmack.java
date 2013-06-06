package de.bistrosoft.palaver.rezeptverwaltung.domain;

/*
 * @author Jasmin Baumgartner
 */
public class Geschmack implements java.io.Serializable {

	private static final long serialVersionUID = 2070753066033963562L;

	// Variablen
	private Long id;
	private String name;
	private Boolean inaktiv;

	// Konstruktoren
	public Geschmack() {
		super();
	}

	public Geschmack(String name) {
		this.name = name;
	}

	public Geschmack(Long id) {
		this.id = id;
	}

	public Geschmack(Long id, String name, Boolean inaktiv) {
		this.id = id;
		this.name = name;
		this.inaktiv = inaktiv;
	}

	// Getter- und Setter
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getInaktiv() {
		return inaktiv;
	}

	public void setInaktiv(Boolean inaktiv) {
		this.inaktiv = inaktiv;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inaktiv == null) ? 0 : inaktiv.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		Geschmack other = (Geschmack) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inaktiv == null) {
			if (other.inaktiv != null)
				return false;
		} else if (!inaktiv.equals(other.inaktiv))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
}

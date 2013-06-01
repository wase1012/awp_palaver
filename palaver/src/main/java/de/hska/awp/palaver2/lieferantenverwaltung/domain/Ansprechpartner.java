/**
 * Created by Christian Barth
 * 26.04.2013 - 09:32:35
 */
package de.hska.awp.palaver2.lieferantenverwaltung.domain;

/**
 * Klasse Ansprechpartner
 * 
 * @author Christian Barth
 * 
 */
public class Ansprechpartner implements java.io.Serializable {

	private static final long serialVersionUID = 4524105542736585477L;

	private Long id;
	private String name;
	private String telefon;
	private String handy;
	private String fax;
	private Lieferant lieferant;

	public Ansprechpartner() {
	}

	public Ansprechpartner(Lieferant lieferant, String name) {
		this.lieferant = lieferant;
		this.name = name;
	}

	public Ansprechpartner(Long id, String name, String telefon, String handy, String fax, Lieferant lieferant) {
		this.id = id;
		this.name = name;
		this.telefon = telefon;
		this.handy = handy;
		this.fax = fax;
		this.lieferant = lieferant;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lieferant getLieferant() {
		return this.lieferant;
	}

	public void setLieferant(Lieferant lieferant) {
		this.lieferant = lieferant;
	}

	public String getName() {
		if (name == null) {
			name = "";
		}
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelefon() {
		if (telefon == null) {
			telefon = "";
		}
		return this.telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getHandy() {
		if (handy == null) {
			handy = "";
		}
		return this.handy;
	}

	public void setHandy(String handy) {
		this.handy = handy;
	}

	public String getFax() {
		if (fax == null) {
			fax = "";
		}
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Override
	public String toString() {
		return "Ansprechpartner [id=" + id + ", name=" + name + ", telefon=" + telefon + ", handy=" + handy + ", fax=" + fax + "]";
	}

}

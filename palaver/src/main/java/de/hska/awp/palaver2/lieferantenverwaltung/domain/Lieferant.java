/**
 * Created by Christian Barth
 * 26.04.2013 - 09:32:35
 */
package de.hska.awp.palaver2.lieferantenverwaltung.domain;

/**
 * Klasse Lieferant
 * 
 * @author Christian Barth
 */
public class Lieferant implements java.io.Serializable {

	private static final long serialVersionUID = 509321793481530142L;

	private Long id;
	private String name;
	private String kundennummer;
	private String bezeichnung;
	private String strasse;
	private String plz;
	private String ort;
	private String email;
	private String telefon;
	private String fax;
	private String notiz;
	private boolean mehrereliefertermine;

	public Lieferant() {
	}

	public Lieferant(String name) {
		this.name = name;
	}

	public Lieferant(Long id, String name, String kundennummer, String bezeichnung, String strasse, String plz, String ort, String email,
			String telefon, String fax, String notiz, Boolean mehrereliefertermine) {
		this.id = id;
		this.name = name;
		this.kundennummer = kundennummer;
		this.bezeichnung = bezeichnung;
		this.strasse = strasse;
		this.plz = plz;
		this.ort = ort;
		this.email = email;
		this.telefon = telefon;
		this.fax = fax;
		this.notiz = notiz;
		this.mehrereliefertermine = mehrereliefertermine;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getKundennummer() {
		if (kundennummer == null) {
			kundennummer = "";
		}
		return this.kundennummer;
	}

	public void setKundennummer(String kundennummer) {
		this.kundennummer = kundennummer;
	}

	public String getBezeichnung() {
		if (bezeichnung == null) {
			bezeichnung = "";
		}
		return this.bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public String getStrasse() {
		if (strasse == null) {
			strasse = "";
		}
		return this.strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getPlz() {
		if (plz == null) {
			plz = "";
		}
		return this.plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getOrt() {
		if (ort == null) {
			ort = "";
		}
		return this.ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getEmail() {
		if (email == null) {
			email = "";
		}
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getFax() {
		if (fax == null) {
			fax = "";
		}
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getNotiz() {
		return notiz;
	}

	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}

	public Boolean getMehrereliefertermine() {
		return mehrereliefertermine;
	}

	public void setMehrereliefertermine(Boolean mehrereLiefertermine) {
		this.mehrereliefertermine = mehrereLiefertermine;
	}

	// Geändert von S.Walz wegen GUI
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Lieferant other = (Lieferant) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

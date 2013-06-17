package de.bistrosoft.palaver.kuchenrezeptverwaltung.domain;




public class KuchenrezeptHasFussnote implements java.io.Serializable {

	private KuchenrezeptHasFussnoteId kuchenHasFussnoteId;
	private FussnoteKuchen fussnotekuchen;
	private Kuchenrezept kuchenrezept;
	private Long fussnotekuchenid;

	public KuchenrezeptHasFussnote() {
	}

	public KuchenrezeptHasFussnote(KuchenrezeptHasFussnoteId kuchenHasFussnoteId, FussnoteKuchen fussnotekuchen,
			Kuchenrezept kuchenrezept) {
		this.kuchenHasFussnoteId = kuchenHasFussnoteId;
		this.fussnotekuchen = fussnotekuchen;
		this.kuchenrezept = kuchenrezept;
	}
	
	public KuchenrezeptHasFussnote( FussnoteKuchen fussnotekuchen,
			Kuchenrezept kuchenrezept) {
		
		this.fussnotekuchen = fussnotekuchen;
		this.kuchenrezept = kuchenrezept;
	}
	public KuchenrezeptHasFussnote( Long fussnotekuchenid,
			Kuchenrezept kuchenrezept) {
		
		this.fussnotekuchenid = fussnotekuchenid;
		this.kuchenrezept = kuchenrezept;
	}
	

	public KuchenrezeptHasFussnoteId getId() {
		return this.kuchenHasFussnoteId;
	}
	public void setId(Long id) {
		this.kuchenHasFussnoteId = kuchenHasFussnoteId;
	}
	
	public FussnoteKuchen getFussnoteKuchen() {
		return this.fussnotekuchen;
	}

	public void setFussnoteKuchen(FussnoteKuchen fussnotekuchen) {
		this.fussnotekuchen = fussnotekuchen;
	}

	
	public Kuchenrezept getKuchen() {
		return this.kuchenrezept;
	}

	public void setKuchen(Kuchenrezept kuchenrezept) {
		this.kuchenrezept = kuchenrezept;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fussnotekuchen == null) ? 0 : fussnotekuchen.hashCode());
		result = prime * result + ((kuchenrezept == null) ? 0 : kuchenrezept.hashCode());
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
		KuchenrezeptHasFussnote other = (KuchenrezeptHasFussnote) obj;
		if (fussnotekuchen == null) {
			if (other.fussnotekuchen != null)
				return false;
		} else if (!fussnotekuchen.equals(other.fussnotekuchen))
			return false;
		if (kuchenrezept == null) {
			if (other.kuchenrezept != null)
				return false;
		} else if (!kuchenrezept.equals(other.kuchenrezept))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "kuchenHasFussnote [fussnote=" + fussnotekuchen + ", kuchenrezept=" + kuchenrezept
				+ "]";
	}

	public Long getFussnoteid() {
		return fussnotekuchenid;
	}

	public void setFussnoteid(Long fussnotekuchenid) {
		this.fussnotekuchenid = fussnotekuchenid;
	}

}

/**
 * Michael Marschall
 * 18.04.2013 20:32:25
 */
package de.bistrosoft.palaver.rezeptverwaltung.domain;

public enum ZubereitungType {
	
	HERD(0), 
	KONVEKTOMAT(1);
	
		
	private int value;
		
	private ZubereitungType(int value) {
		this.value = value;
	}
		
	public static ZubereitungType valueOf(int value) {
		switch (value) {
			case 0:
				return HERD;
			case 1:
				return KONVEKTOMAT;
			default:
				return null;
		}
	}
		
	public int value() {
		return value;
	}

}

/**
 * 
Jan Lauinger
*
18.04.2013
*
 */
package de.bistrosoft.palaver.rezeptverwaltung.domain;

public enum GeschmackType {
	
	EXOTISCH(0), 
	DEFTIG(1),
	KLASSISCH(2),
	MEDITERRAN(3);
		
	private int value;
		
	private GeschmackType(int value) {
		this.value = value;
	}
		
	public static GeschmackType valueOf(int value) {
		switch (value) {
			case 0:
				return EXOTISCH;
			case 1:
				return DEFTIG;
			case 2:
				return KLASSISCH;
			case 3:
				return MEDITERRAN;
			default:
				return null;
		}
	}
		
	public int value() {
		return value;
	}
}




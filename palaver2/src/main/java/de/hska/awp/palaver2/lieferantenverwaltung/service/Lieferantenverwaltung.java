/**
 * Created by bach1014
 * 17.04.2013 - 14:54:02
 */
package de.hska.awp.palaver2.lieferantenverwaltung.service;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * @author bach1014
 *
 */
public class Lieferantenverwaltung {
	
	public Lieferant createLieferant(Lieferant lieferant) {
		
		if (lieferant == null) {
			return lieferant;
		}
	
		//TODO Überprüfung, ob es den Lieferanten schon gibt, notfalls exceptions werfen	
		//lieferant = (Lieferant) em.persist(lieferant);
		
		return lieferant;
	}
	

}

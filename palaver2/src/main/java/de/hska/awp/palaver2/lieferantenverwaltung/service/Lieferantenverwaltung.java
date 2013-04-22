/**
 * Created by bach1014
 * 17.04.2013 - 14:54:02
 */
package de.hska.awp.palaver2.lieferantenverwaltung.service;

import java.io.Serializable;

import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;

/**
 * @author bach1014
 *
 */
public class Lieferantenverwaltung implements Serializable {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -5676269878706221454L;

	private LieferantenverwaltungDao dao;
	
	public Lieferant createLieferant(Lieferant lieferant) {
		
		if (lieferant == null) {
			return lieferant;
		}
		
		lieferant = (Lieferant) dao.create(lieferant);
		
		return lieferant;
	}
	

}

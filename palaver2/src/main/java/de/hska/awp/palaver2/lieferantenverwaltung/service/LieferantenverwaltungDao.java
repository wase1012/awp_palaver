/**
 * Created by bach1014
 * 22.04.2013 - 17:43:09
 */
package de.hska.awp.palaver2.lieferantenverwaltung.service;

import java.util.List;

import static de.hska.awp.palaver2.util.Dao.QueryParameter.with;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.Dao;

/**
 * @author bach1014
 * 
 */
public class LieferantenverwaltungDao extends Dao {

	private static final long serialVersionUID = -6166455307123578665L;

	public List<Ansprechpartner> findAllAnsprechpartner() {

		List<Ansprechpartner> aplist = null;

		aplist = find(Ansprechpartner.class,
				Ansprechpartner.FIND_ALL_ANSPRECHPARTNER);

		return aplist;
	}

	public List<Ansprechpartner> findAnsprechpartnerByName(String name) {

		List<Ansprechpartner> aplist = null;

		aplist = find(Ansprechpartner.class,
				Ansprechpartner.FIND_ANSPRECHPARTNER_BY_NAME,
				with(Ansprechpartner.PARAM_NAME, name).build());

		return aplist;
	}

	public Ansprechpartner findAnsprechpartnerById(Long id) {

		Ansprechpartner ap = null;

		ap = find(Ansprechpartner.class, id);

		return ap;
	}

	public List<Lieferant> findAllLieferant() {

		List<Lieferant> lieferantlist = null;

		lieferantlist = find(Lieferant.class, Lieferant.FIND_ALL_LIEFERANT);

		return lieferantlist;
	}

	public List<Lieferant> findLieferantByName(String name) {

		List<Lieferant> lieferantlist = null;

		lieferantlist = find(Lieferant.class, Lieferant.FIND_LIEFERANT_BY_NAME,
				with(Lieferant.PARAM_NAME, name).build());

		return lieferantlist;
	}

	public Lieferant findLieferantById(Long id) {

		Lieferant lieferant = null;

		lieferant = find(Lieferant.class, id);

		return lieferant;
	}
}

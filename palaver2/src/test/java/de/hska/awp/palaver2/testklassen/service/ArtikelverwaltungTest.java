/**
 * Created by Sebastian Walz
 * 24.04.2013 16:59:46
 */
package de.hska.awp.palaver2.testklassen.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;


public class ArtikelverwaltungTest {
	
Artikelverwaltung av = Artikelverwaltung.getInstance();		 
private static final Long ARTIKEL_ID_VORHANDEN = Long.valueOf(1);
final Mengeneinheit menge = new Mengeneinheit(); 
final Kategorie kategorie = new Kategorie();
final Lieferant lieferant= new Lieferant();
final Boolean bio = true;
final Boolean standard = true;
final Boolean grundbedarf = true;
final Boolean lebensmittel = true;


	@Test
	public void getArtikelById(){
		
	
		Artikel art = null;
		Boolean exception = false;
		final Long id = ARTIKEL_ID_VORHANDEN;
		
		
		try{
		 art = av.getArtikelById(id);
		}
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		assertThat(art.getId(),is(id));
	}
	
	@Test
    public void getArtikelByName() {
    	
    	final String name = "Walnusskerne 500G";
    	Boolean exception = false;
    	List<Artikel> artikellist = null;
	
    		try{
    			
    			artikellist = av.getArtikelByName(name);
    		
    		}
    		catch (ConnectException | DAOException | SQLException e)
    		{
    			exception = true;
    		}
    		for (Artikel a : artikellist) {
    			assertThat(a.getName(), is(name));
    		}
    	}
	@Test
	public void createArtikel(){
		
		Artikel art = new Artikel();
		Boolean exception = false;
		
		final String name = "SuperEis";
		
		
		art.setName(name);
		art.setMengeneinheit(menge);
	    art.setKategorie(kategorie);
		art.setLieferant(lieferant);
		art.setBio(bio);
		art.setStandard(standard);
		art.setGrundbedarf(grundbedarf);
		art.setLebensmittel(lebensmittel);
		
		try{
	    av.createArtikel(art);
		}
		catch (ConnectException | DAOException e)
		{
			exception = true;
		}
		
		assertThat(art.getName(),is(name));
	}
	
	@Test
	public void updateArtikel (){
		
		Artikel art = new Artikel();
		
		Boolean exception = false;
		final Long id = Long.valueOf(1);
		final String neuerName = "TestFleisch";
		
		try{
			
		av.getArtikelById(id);
		
		}
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		art.setName(neuerName);
		art.setMengeneinheit(menge);
	    art.setKategorie(kategorie);
		art.setLieferant(lieferant);
		art.setBio(bio);
		art.setStandard(standard);
		art.setGrundbedarf(grundbedarf);
		art.setLebensmittel(lebensmittel);
		
		try{
		av.updateArtikel(art);
		}
		catch (ConnectException | DAOException e)
		{
			exception = true;
		}
		assertThat(art.getName(), is(neuerName));
	}
}


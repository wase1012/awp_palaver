/**
 * Created by Elena W
 */
package de.hska.awp.palaver2.testklassen.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.artikelverwaltung.service.Kategorienverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;

/**
 * Testklasse für die Kategorienverwaltung
 * @author Elena W
 */


public class KategorienverwaltungTest {
	Kategorienverwaltung kv = Kategorienverwaltung.getInstance();
	private static final Long KATEGORIE_ID_VORHANDEN = Long.valueOf(1);
	
	@Test
	public void getKategorieById(){
		
	
		Kategorie kat = null;
		Boolean exception = false;
		final Long id = KATEGORIE_ID_VORHANDEN;
		
		
		try{
		 kat = kv.getKategorieById(id);
		}
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		assertThat(kat.getId(),is(id));
	}
	/**
	 * Testmethode createNewKategorie
	 * Erzeugt eine Kategorie in der Datenbank
	 * @throws SQLException 
	 * @throws DAOException 
	 * @throws ConnectException 
	 */
	@Test
	public void createNewKategorie(){
		
		Kategorie kat = new Kategorie();
		Boolean exception = false;
		final String name = "SuperKategorie";
		
		
		kat.setName(name);
				
		try{
	    kv.createNewKategorie(kat);
		}
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		
		assertThat(kat.getName(),is(name));
	}
	
	/**
	 * Testmethode updateKategorie
	 * Aktualisierung des Namens einer Kategorie in der Datenbank
     * @throws SQLException 
     * @throws DAOException 
     * @throws ConnectException 
	 */
	@Test
	public void updateKategorie (){
	
		Kategorie kat = new Kategorie();
	
		Boolean exception = false;
		final Long id = KATEGORIE_ID_VORHANDEN;
		final String neuerName = "TestKategorie";
	
		try{		
			kv.getKategorieById(id);	
		}
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		kat.setName(neuerName);		
		try{
			kv.updateKategorie(kat);
		}
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		assertThat(kat.getName(), is(neuerName));
	}
}

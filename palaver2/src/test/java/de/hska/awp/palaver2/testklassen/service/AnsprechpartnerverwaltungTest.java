/**
 * Created by Elena W
 */
package de.hska.awp.palaver2.testklassen.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Ansprechpartnerverwaltung;

	/**
	 * Testklasse für die Ansprechpartnerverwaltung
	 * @author Elena W
	 */
	public class AnsprechpartnerverwaltungTest {

		Ansprechpartnerverwaltung apv = Ansprechpartnerverwaltung.getInstance();		 
		private static final Long ANSPRECHPARTNER_ID_VORHANDEN = Long.valueOf(1);
		final Lieferant lieferant = new Lieferant();

		/**
		 * Die Testmethode sucht nach einem Ansprechpartner anhand einer Id
		 * 
		 * @throws SQLException
		 * @throws DAOException
		 * @throws ConnectException
		 */
		@Test
		public void getAnsprechpartnerById(){
	

			Ansprechpartner ap = null;
			Boolean exception = false;
			final Long id = ANSPRECHPARTNER_ID_VORHANDEN;
	
	
			try{
				ap = apv.getAnsprechpartnerById(id);
			}
			catch (ConnectException | DAOException | SQLException e)
			{
				exception = true;
			}
			assertThat(ap.getId(),is(id));
		}
		
		/**
		 * Die Testmethode liefert alle Artikel zurück.
		 * 
		 * @throws SQLException
		 * @throws DAOException
		 * @throws ConnectException
		 * @return List<Artikel>
		 */
		@Test 
		public void getAllAnsprechpartner() {
		    	
			   	Boolean exception = false;
			   	List<Ansprechpartner> aplist = null;
			
		    		try{
		    			
		    			aplist = apv.getAllAnsprechpartner();
		    		
		    		}
		    		catch (ConnectException | DAOException | SQLException e)
		    		{
		    			exception = true;
		    		}
		    		assertThat(aplist.isEmpty(), is(false));
		    	}
		    	
		/**
		 * Testmethode  getAnsprechpartnerByName liefert alle Ansprechpartner
		 * anhand des Parameter name zurück.
		 * @throws SQLException
		 * @throws DAOException
		 * @throws ConnectException
		 */
		@Test
	    public void getAnsprechpartnerByName() {
	    	
	    	final String name = "Armin Maier";
	    	Boolean exception = false;
	    	List<Ansprechpartner> aplist = null;
		
	    		try{
	    			
	    			aplist = apv.getAnsprechpartnerByName(name);
	    		
	    		}
	    		catch (ConnectException | DAOException | SQLException e)
	    		{
	    			exception = true;
	    		}
	    		for (Ansprechpartner ap : aplist) {
	    			assertThat(ap.getName(), is(name));
	    		}
	    	}
		
		/**
		 * Testmethode createAnsprechpartner
		 * Erzeugt einen Ansprechpartner in der Datenbank
		 * @throws SQLException 
		 * @throws DAOException 
		 * @throws ConnectException 
		 */
		@Test
		public void createAnsprechpartner(){
			
			Ansprechpartner ap = new Ansprechpartner();
			Boolean exception = false;
			
			final String name = "Test test";
			
			
			ap.setName(name);
			ap.setLieferant(lieferant);
			
			try{
		    apv.createAnsprechpartner(ap);
			}
			catch (ConnectException | DAOException | SQLException e)
			{
				exception = true;
			}
			
			assertThat(ap.getName(),is(name));
		}
		
		 /**
		 * Testmethode updateAnsprechpartner
		 * Aktualisiert die Name eines Ansprechpartner in der Datenbank
	     * @throws SQLException 
	     * @throws DAOException 
	     * @throws ConnectException 
		 */
		@Test
		public void updateAnsprechpartner (){
			
			Ansprechpartner ap = new Ansprechpartner();
			
			Boolean exception = false;
			final Long id = Long.valueOf(1);
			final String neuerName = "Test";
			
			try{
				
			apv.getAnsprechpartnerById(id);
			
			}
			catch (ConnectException | DAOException | SQLException e)
			{
				exception = true;
			}
			ap.setName(neuerName);
			ap.setLieferant(lieferant);
					
			try{
			apv.updateAnsprechpartner(ap);
			}
			catch (ConnectException | DAOException | SQLException e)
			{
				exception = true;
			}
			assertThat(ap.getName(), is(neuerName));
		}
		
		/**
		 * Testmethode  getAnsprechpartnerByLieferant 
		 * liefert alle Ansprechpartner anhang des Parameters Lieferant zurück.
		 * @throws SQLException
		 * @throws DAOException
		 * @throws ConnectException
		 */
		@Test
	    public void getAnsprechpartnerByLieferant() {
	    	
		   	Boolean exception = false;
		   	List<Ansprechpartner> aplist = null;
		
//	    		try{
	    			
	    			aplist = apv.getAnsprechpartnerByLieferant(lieferant);
	    		
//	    		}
//	    		catch (ConnectException | DAOException | SQLException e)
//	    		{
//	    			exception = true;
//	    		}
	    		for (Ansprechpartner ap : aplist) {
	    			assertThat(aplist.isEmpty(), is(false));
	    		}
	    	}
	}

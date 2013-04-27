package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;
import org.junit.Test;
import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.util.AbstractTest;
import de.hska.awp.palaver2.data.KategorieDAO;


/**
 * @author mihail boehm Testklasse fuer die Kategorie
 */
public class KategorieTest extends AbstractTest {

	final String NAME = "Milch";
	final Long ID = Long.valueOf("3");
	private KategorieDAO dao = new KategorieDAO();

	/**
	 * Die Testmethode liefert alle Kategorie aus der Datenbank zurück.
	 * @throws SQLException 
	 * @throws DAOException 
	 * @throws ConnectException
	 */
	@Test
	public void getKategorien()
	{
		Boolean exception = false;
		List<Kategorie> kategorielist = null;
		try
		{
			kategorielist = dao.getAllKategories();
		} 
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		assertThat(kategorielist.isEmpty(), is(false));
		assertThat(exception, is(false));
	}
	
	/**
	 * Die Testmethode sucht nach einer Kategorie anhand einer Id
 	 * @throws SQLException 
 	 * @throws DAOException 
 	 * @throws ConnectException 
	 */
    @Test
    public void getKategorieById() throws ConnectException, DAOException, SQLException {
    	Kategorie kategorie = new Kategorie();
    	kategorie = dao.getKategorieById(ID);
		assertThat(kategorie.getId(), is(ID));
    }

		/**
	 * Testmethode updateKategorie Update des Namens einer Kategorie in der
	 * Datenbank
		 * @throws SQLException 
		 * @throws DAOException 
		 * @throws ConnectException 
	 */
	@Test
	public void updateKategorie() throws ConnectException, DAOException, SQLException {
		Kategorie kategorie = new Kategorie();
		kategorie = dao.getKategorieById(ID);
    	kategorie.setName(NAME + "_update");
    	dao.updateKategorie(kategorie);
	}
	
	 /**
	 * Testmethode createKategorie
	 * Erzeugen einer Kategorie in der Datenbank
	 * @throws SQLException 
	 * @throws DAOException 
	 * @throws ConnectException 
	 */
    @Test
    public void createKategorie() throws ConnectException, DAOException, SQLException {
    	Kategorie kategorie = new Kategorie();
    	kategorie.setName(NAME + "_NEW");   	
    	dao.createNewKategorie(kategorie);
    }
}

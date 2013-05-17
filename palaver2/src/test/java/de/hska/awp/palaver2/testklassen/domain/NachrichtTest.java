package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.MitarbeiterDAO;
import de.hska.awp.palaver2.data.NachrichtDAO;
import de.hska.awp.palaver2.data.RollenDAO;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;
import de.hska.awp.palaver2.util.AbstractTest;

/**
 * @author PhilippT
 *
 */
@Ignore
public class NachrichtTest extends AbstractTest {
	

	private static final String test_nachricht = "Ich bin die Testnachricht";	
	private NachrichtDAO ndao = new NachrichtDAO();
	private MitarbeiterDAO mdao = new MitarbeiterDAO();
	private RollenDAO rdao = new RollenDAO();

	/**
	 * Test ob alle Nachrichten aus der Datenbank ausgelesen werden können.
	 */
    @Test
    public void findAllNachricht() {
    	

		List<Nachricht> nachricht = null;
		
		try {
			nachricht = ndao.getAllNachricht();
		} catch (ConnectException | DAOException | SQLException e) {
			throw new NullPointerException("fehler");
		} 

		assertThat(nachricht.isEmpty(), is(false));
    }
    
    /**
     * Test, eine Nachricht anhand einer gegebenen ID suchen
     */
	
    @Test
    public void findNachrichtById() {
    	
    	Long id = Long.valueOf(1);

		Nachricht nachricht = null;;
		try {
			nachricht = ndao.getNachrichtById(id);
		} catch (ConnectException | DAOException | SQLException e) {
			throw new NullPointerException("error");
		} 

		assertThat(nachricht.getId(), is(id));
    }
    
    /**
     * Testmethode um Nachrichten zu einer Rolle zu suchen, auszulesen
     * @throws ConnectException
     * @throws DAOException
     * @throws SQLException
     */
    
    @Test
    public void findNachrichtByRolle() throws ConnectException, DAOException, SQLException {
    	
    	Long id = Long.valueOf(1);
    	Rollen rolle = rdao.getRollenById(id);
    	List<Nachricht> nachrichten = null;
		try {
			nachrichten = ndao.getNachrichtByRolle(rolle);
		} catch (ConnectException | DAOException | SQLException e) {
			throw new NullPointerException("fehler");
		} 

		assertThat(nachrichten.isEmpty(), is(false));
    	
    }
    
    /**
     * Test, eine neue Nachricht anzulegen, in die Datenbank zu schreiben
     */
    
	@Test
	public void createNachrichten() {
		
		final Nachricht n = new Nachricht();
		n.setNachricht(test_nachricht);
		
    	Long sid = Long.valueOf(1);
    	Mitarbeiter sender = null;
		try {
			sender = mdao.getMitarbeiterById(sid);
			if(sender==null){
			System.out.println("Mitarbeiter nicht gefunden bei sid");
			}
		} catch (ConnectException | DAOException | SQLException e) {
			throw new NullPointerException();
		}
    	n.setMitarbeiterBySenderFk(sender);
    	
    	Long eid = Long.valueOf(1);   	
    	Rollen empfaenger = null;
		try {
			empfaenger = rdao.getRollenById(eid);
			if(empfaenger==null){
				System.out.println("Rolle nicht gefunden bei eid");
				}
		} catch (ConnectException | DAOException | SQLException e) {
			throw new NullPointerException();
		}
		n.setEmpfaengerRolle(empfaenger);    	
    	try {
			ndao.createNachricht(n);
		} catch (ConnectException | DAOException | SQLException e) {
			throw new NullPointerException();
		}
	}
	
	/**
	 * Test, eine Nachricht zu löschen. Gegeben ist die ID
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	
    @Test
    public void deleteNachricht() throws ConnectException, DAOException, SQLException {
    	
    	Long id = Long.valueOf(2);
    	Nachricht nachricht = new Nachricht();
    	nachricht = ndao.getNachrichtById(id);
    	
    	ndao.deleteNachricht(nachricht.getId());
    	
    }
			
}
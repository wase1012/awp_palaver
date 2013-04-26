package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.NachrichtDAO;
import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;

/**
 * @author PhilippT
 *
 */

public class NachrichtTest  {
	

	private static final String test_nachricht = "Ich bin die Testnachricht";	
	private NachrichtDAO ndao = new NachrichtDAO();
//	private MitarbeiterDAO mdao;
//	private RollenDAO rdao;

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
	
    @Test
    public void findNachrichtById() {
    	
    	Long id = Long.valueOf(2);

		Nachricht nachricht;
		try {
			nachricht = ndao.getNachrichtById(id);
		} catch (ConnectException | DAOException | SQLException e) {
			throw new NullPointerException("error");
		} 

		assertThat(nachricht.getId(), is(id));
    }
    
//	@Test
//	public void createNachrichten() {
//		
//		final Nachricht n = new Nachricht();
//		n.setNachricht(test_nachricht);
//		
//    	Long sid = Long.valueOf(1);
//    	Mitarbeiter sender = mdao.getMitarbeiterById(sid);
//    	n.setMitarbeiterBySenderFk(sender.id);
//    	
//    	Long eid = Long.valueOf(1);   	
//    	Rollen empfaenger = rdao.getRollenById(eid);
//		n.setEmpfaengerRolle(empfaenger.id);    	
//    	
//	}
//	
    @Test
    public void deleteNachricht() throws ConnectException, DAOException, SQLException {
    	
    	Long id = Long.valueOf(3);
    	Nachricht nachricht = new Nachricht();
    	nachricht = ndao.getNachrichtById(id);
    	
    	ndao.deleteNachricht(nachricht);
    	
    }
			
}
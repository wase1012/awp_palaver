package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.MitarbeiterDAO;
import de.hska.awp.palaver2.data.RollenDAO;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.util.AbstractTest;


public class MitarbeiterTest extends AbstractTest{

	private MitarbeiterDAO mdao = new MitarbeiterDAO();
	private RollenDAO rdao = new RollenDAO();
	
    @Test
    public void getMitarbeiterById() throws Exception {
    	
    	Long id = Long.valueOf(2);

		Mitarbeiter mitarbeiter = null;
		try {
			mitarbeiter = mdao.getMitarbeiterById(id);
		} catch (ConnectException | DAOException | SQLException e) {
			throw new NullPointerException("error");
		} 

		assertThat(mitarbeiter.getId(), is(id));
		List <Rollen> list = new ArrayList<Rollen>();
		try {
			list = mitarbeiter.getRollen();
		} catch (Exception e) {
			throw new Exception("Mitarbeiter hat keine Rolle");
		}
		assertThat(list.isEmpty(), is(false));
		
    }
    
    @Test
	public void getAllMitarbeiter()
	{
		Boolean exception = false;
		List<Mitarbeiter> list = null;
		try
		{
			list = mdao.getAllMitarbeiter();
		} 
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		assertThat(list.isEmpty(), is(false));
		assertThat(exception, is(false));
	}
    
    @Test
    public void createMitarbeiter() throws Exception {
    	
    	Mitarbeiter m = new Mitarbeiter();
    	m.setName("TestMitarbeiter");
    	m.setVorname("0175/55667788");
    	m.setEmail("test@web.de");
    	m.setPasswort("1234");
    	m.setEintrittsdatum("heute");
    	m.setAustrittsdatum("morgen");
    	
    	Rollen rolle = null;
    		rolle = rdao.getRollenById(Long.valueOf("1"));
    	m.addRollen(rolle);
    		rolle = rdao.getRollenById(Long.valueOf("2"));
    	m.addRollen(rolle);
    	try
		{
    	mdao.createMitarbeiter(m);
		} 
		catch (ConnectException | DAOException | SQLException e)
		{
			throw new Exception("createMitarbeiter hat nicht funktioniert");
		}
    	
    }
    
    @Test
    public void updateMitarbeiter() throws Exception {
    	
    	Mitarbeiter m = mdao.getMitarbeiterById(Long.valueOf("1"));
    	m.setName("updateTestMitarbeiter");
    	   	
    	Rollen rolle = null;
    		rolle = rdao.getRollenById(Long.valueOf("1"));
    	m.addRollen(rolle);
    		rolle = rdao.getRollenById(Long.valueOf("2"));
    	m.addRollen(rolle);
    	try
		{
    	mdao.updateMitarbeiter(m);
		} 
		catch (ConnectException | DAOException | SQLException e)
		{
			throw new Exception("updateMitarbeiter hat nicht funktioniert");
		}
    	
    }
    
    @Test
    public void getMitarbeiterByRollenId() throws Exception {
    	
    	Long id = Long.valueOf(1);

		List<Mitarbeiter> list = new ArrayList<Mitarbeiter>();
		
			list = mdao.getMitarbeiterByRollenId(id);
		
		System.out.print(list);
		assertThat(list.isEmpty(), is(false));
		
    }
}

package de.hska.awp.palaver2.testklassen.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.RollenDAO;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.util.AbstractTest;


public class RollenTest extends AbstractTest{

	private RollenDAO rdao = new RollenDAO();
	
    @Test
    public void getRollenById() throws Exception {
    	
    	Long id = Long.valueOf("1");

		Rollen rolle = null;
		
			rolle = rdao.getRollenById(id);
		

		assertThat(rolle.getId(), is(id));
		
//		List <Mitarbeiter> list = new ArrayList<Mitarbeiter>();
//			
//		list = rolle.getMitarbeiters();
//		if(list==null){
//			System.out.print("liste von Mitarbeitern ist leer");
//		}
//		assertThat(list.isEmpty(), is(false));
		
    }
    
    @Test
	public void getAllRollen()
	{
		Boolean exception = false;
		List<Rollen> list = null;
		try
		{
			list = rdao.getAllRollen();
		} 
		catch (ConnectException | DAOException | SQLException e)
		{
			exception = true;
		}
		assertThat(list.isEmpty(), is(false));
		assertThat(exception, is(false));
		System.out.println(list);
	}
    
    @Test
    public void createRollen() throws Exception {
    	
    	Rollen r = new Rollen();
    	r.setName("TestsRolle");
    	
    	try
		{
    	rdao.createRollen(r);
		} 
		catch (ConnectException | DAOException | SQLException e)
		{
			throw new Exception("Eine Rolle konnte nicht angelegt werden");
		}
	}

	@Test
	public void getRollenByMitarbeiterFK() throws ConnectException, DAOException, SQLException {
		List<Rollen> list = null;
		Long id = Long.valueOf(1);
		
			list = rdao.getRollenByMitarbeiterFK(id);
			
		assertThat(list.isEmpty(), is(false));
		System.out.println(list);
	}
}

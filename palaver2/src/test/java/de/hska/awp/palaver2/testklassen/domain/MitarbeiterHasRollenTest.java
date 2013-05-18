package de.hska.awp.palaver2.testklassen.domain;

import java.sql.SQLException;

import org.junit.Test;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.MitarbeiterDAO;
import de.hska.awp.palaver2.data.MitarbeiterHasRollenDAO;
import de.hska.awp.palaver2.data.RollenDAO;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.util.AbstractTest;


public class MitarbeiterHasRollenTest extends AbstractTest{

	private MitarbeiterHasRollenDAO mhrdao = new MitarbeiterHasRollenDAO();
	private MitarbeiterDAO mdao = new MitarbeiterDAO();
	private RollenDAO rdao = new RollenDAO();
	
    @Test
    public void deleteMitarbeiterHasRollen() throws ConnectException, DAOException, SQLException {
    	
    	Long mid = Long.valueOf(1);
    	Long rid = Long.valueOf(1);
    	
    	Mitarbeiter m = mdao.getMitarbeiterById(mid);
    	Rollen r = rdao.getRollenById(rid);
    	
    	mhrdao.deleteMitarbeiterHasRollen(m, r);
    	
    }
}

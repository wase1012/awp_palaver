package de.hska.awp.palaver2.testklassen.domain;

import java.sql.SQLException;

import org.junit.Test;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.data.MitarbeiterHasRollenDAO;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.MitarbeiterHasRollen;
import de.hska.awp.palaver2.util.AbstractTest;


public class MitarbeiterHasRollenTest extends AbstractTest{

	private MitarbeiterHasRollenDAO mhrdao = new MitarbeiterHasRollenDAO();
	
    @Test
    public void createanddeleteMitarbeiterHasRollen() {
    	
    	Long mid = Long.valueOf(1);
    	Long rid = Long.valueOf(1);
    	
    	MitarbeiterHasRollen mitarbeiterhasrollen = new MitarbeiterHasRollen(mid, rid);
		try {
			mhrdao.createMitarbeiterHasRollen(mitarbeiterhasrollen);
		} catch (ConnectException | DAOException | SQLException e) {
			throw new NullPointerException("create MitarbeiterHasRollen konnte nicht angelegt werden");
		} 
		try {
			mhrdao.deleteMitarbeiterHasRollen(mid, rid);
		}catch (ConnectException | DAOException | SQLException e) {
			throw new NullPointerException("delete MitarbeiterHasRollen konnte nicht gelöscht werden");
		} 
    }
}

package de.bistrosoft.palaver.regelverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.RegelDAO;
import de.bistrosoft.palaver.regelverwaltung.domain.Regel;

public class Regelverwaltung extends RegelDAO{

	private static Regelverwaltung instance = null;

	public Regelverwaltung() {
		super();
	}

	public static Regelverwaltung getInstance() {
		if (instance == null) {
			instance = new Regelverwaltung();
		}
		return instance;
	}
	
	public List<Regel> getAllRegeln() throws ConnectException,
	DAOException, SQLException {

		List<Regel> result = null;
		result = super.getAllRegeln();

		return result;
	}
	
	public void createRegel(Regel regel)
			throws ConnectException, DAOException, SQLException {

		super.createRegel(regel);
	}
}

package main.java.de.bistrosoft.palaver.regelverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import main.java.de.hska.awp.palaver2.data.ConnectException;
import main.java.de.hska.awp.palaver2.data.DAOException;
import main.java.de.bistrosoft.palaver.data.RegelDAO;
import main.java.de.bistrosoft.palaver.regelverwaltung.domain.Regel;

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

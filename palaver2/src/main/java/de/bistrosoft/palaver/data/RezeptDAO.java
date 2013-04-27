package de.bistrosoft.palaver.data;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasFussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasZubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;

public class RezeptDAO extends AbstractDAO {

	private final static String GET_ALL_REZEPTS = "SELECT * FROM rezept";
	private final static String GET_REZEPT_BY_ID = "SELECT * FROM rezept WHERE id = {0}";
	private final static String GET_REZEPT_BY_NAME = "SELECT * FROM rezept WHERE name = {0}";
	private final static String PUT_REZEPT = "INSERT INTO rezept(`name`," +
																"`rezeptart_fk`," +
																"`kommentar`,`" +
																"`portion`," +
																"`geschmack_fk`," +
																"`mitarebietr_fk`)VALUES({0})";
	private final static String UPDATE_REZEPT = "UPDATE rezept SET `name` = {0}, `rezeptart_fk` = {1},`kommentar` = {2},`portion` = {3},`geschmack_fk` = {4},`mitarebietr_fk` = {5} WHERE id = {6} ";

	public RezeptDAO() {
		super();
	}
	
//	public List<Rezept> getAllRezepts() throws ConnectException, DAOException, SQLException {
//		List<Rezept> list = new ArrayList<Rezept>();
//		ResultSet set = get (GET_ALL_REZEPTS);
//		
//		while(set.next()){
//			list.add(new Rezept(set.getLong("id"),
//					GeschmackDAO.getInstance().getGeschmackById(set.getLong("geschmack_fk")),
//					ZubereitungDAO.getInstance().getZubereitungById(set.getLong("zubereitung_fk")),
//					new Mitarbeiter(),
//					set.getString("name"),
//					set.getString("kommentar"),
//					set.getInt("portion"),
//					new Menue(),
//					new RezeptHasFussnote(),
//					new RezeptHasArtikel(),
//					new RezeptHasZubereitung()
//					));
//		}
//		return list;
//	}
	
}

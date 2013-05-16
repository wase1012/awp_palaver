package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.regelverwaltung.domain.Regel;
import de.hska.awp.palaver2.data.AbstractDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;

public class RegelDAO extends AbstractDAO {
	
	private static RegelDAO instance = null;
	
	private final String CREATE_REGEL ="INSERT INTO " + TABLE + "(zeile, spalte, regeltyp, operator, kriterien, fehlermeldung, aktiv) " +
			"VALUES({0},{1},{2},{3},{4},{5},{6})";
	private final static String TABLE = "regel";
	private static final String GET_ALL_REGELN = "SELECT * FROM " + TABLE;
	
	public static RegelDAO getInstance() {
		if (instance == null) {
			instance = new RegelDAO();
		}
		return instance;
	}
	
	public List<Regel> getAllRegeln() throws ConnectException,
	DAOException, SQLException {
		List<Regel> list = new ArrayList<Regel>();

		ResultSet set = get(GET_ALL_REGELN);

		while (set.next()) {
			list.add(new Regel(set.getString("regeltyp"), set.getString("zeile"), set.getString("spalte"), 
					set.getString("operator"), set.getString("kriterien"), set.getString("fehlermeldung"), set.getBoolean("aktiv")
			));
		}

		return list;
	}


	public void createRegel(Regel regel) throws ConnectException, DAOException, SQLException{
		putManaged(MessageFormat.format(CREATE_REGEL, regel.getZeile(),regel.getSpalte(),regel.getRegeltyp(),
				regel.getOperator(),regel.getKriterien(),regel.getFehlermeldung(),regel.getAktiv()));
	
	}
}

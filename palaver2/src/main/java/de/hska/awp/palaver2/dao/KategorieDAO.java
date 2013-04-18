package de.hska.awp.palaver2.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;

/**
 * @author Mihail Boehm
 * @datum 18.04.2013
 * @version 1.0
 */
public class KategorieDAO extends AbstractDAO {

	private static KategorieDAO instance = null;
	private final static String GET_ALL_KATEGORIES = "SELECT * FROM kategorie";
	
	private KategorieDAO(){
		super();
	}
	/**
	 * @return instance
	 */
	public static KategorieDAO getInstance()
	{
		if(instance == null)
		{
			instance = new KategorieDAO();
		}
		return instance;
	}
	/**
	 * @return Kategorieliste
	 * @throws ConnectException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public List<Kategorie> getAllKategorie() throws ConnectException, DAOException, SQLException
	{
		List<Kategorie> list = new ArrayList<Kategorie>();		
		ResultSet set = get(GET_ALL_KATEGORIES);		
		while(set.next())
		{
			list.add(new Kategorie(set.getLong("id"),
								set.getString("name")
								));
		}		
		return list;
	}	
}

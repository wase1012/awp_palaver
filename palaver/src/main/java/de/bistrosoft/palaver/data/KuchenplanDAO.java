/**
 * 
 */
package de.bistrosoft.palaver.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.FussnoteKuchen;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenplan;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenplanHasKuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Fussnotekuchenverwaltung;
import de.bistrosoft.palaver.menueplanverwaltung.ArtikelBedarf;
import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.artikelverwaltung.service.Mengeneinheitverwaltung;
import de.hska.awp.palaver2.data.AbstractDAO;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;

/**
 * @author Christine
 * 
 */
public class KuchenplanDAO extends AbstractDAO {
	private static KuchenplanDAO instance;
	private final String TABLE = "kuchenplan";
	private final String ID = "id";

	private final String GET_KUCHENPLAN_BY_WEEK = "SELECT * FROM " + TABLE
			+ " WHERE week = {0} AND year = {1,number,#}";
	private final String GET_KUCHENREZEPTE_BY_KUCHENPLAN = "SELECT ku.*, khk.tag, khk.anzahl "
			+ "FROM kuchenrezept ku, kuchenplan_has_kuchenrezepte khk "
			+ "WHERE ku.id = khk.kuchenrezept_fk AND khk.kuchenplan_fk = {0}";
	private final String CREATE_KUCHENREZEPT_FOR_KUCHENPLAN = "INSERT INTO kuchenplan_has_kuchenrezepte (kuchenplan_fk, kuchenrezept_fk, tag, anzahl) "
			+ "VALUES ({0},{1},{2},{3})";
	private final String CREATE_KUCHENPLAN = "INSERT INTO kuchenplan (week,year)  VALUES ({0},{1,number,#})";
	private final String DELETE_KUCHENPLANITEMS_BY_KUCHENPLAN = "DELETE FROM kuchenplan_has_kuchenrezepte WHERE kuchenplan_fk = {0}";
	private final String GET_ARTIKEL_BY_WEEK = "select artikel_fk, sum(menge) menge, einheit from kuchenrezept_has_artikel WHERE kuchenrezept_fk IN "+ 
							"(select kuchenrezept_fk from kuchenplan_has_kuchenrezepte where kuchenplan_fk = "+
								"(select id from kuchenplan where week={0} AND year= {1,number,#})) "+
							"GROUP BY artikel_fk, einheit";

	public KuchenplanDAO() {
		super();
	}

	public static KuchenplanDAO getInstance() {
		if (instance == null) {
			instance = new KuchenplanDAO();
		}
		return instance;
	}
	
	public List<ArtikelBedarf> getKuchenartikelByWeek(Week week){
		List<ArtikelBedarf> ab = new ArrayList<ArtikelBedarf>();
		
		try {
			ResultSet set = getManaged(MessageFormat.format(GET_ARTIKEL_BY_WEEK, week.getWeek(), week.getYear()));
			
			while(set.next()){
				Artikel art = Artikelverwaltung.getInstance().getArtikelById(set.getLong("artikel_fk"));
				Double menge = set.getDouble("menge");
				Mengeneinheit einheit = Mengeneinheitverwaltung.getInstance().getMengeneinheitById(set.getLong("einheit"));
			
				ab.add(new ArtikelBedarf(art,menge,einheit,1));
			}
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ab;
	}

	
	
	
	public Kuchenplan getKuchenplanByWeekWithItems(Week week)
			throws ConnectException, DAOException, SQLException {
		Kuchenplan kuchenplan = null;
		ResultSet setKpl = getManaged(MessageFormat.format(
				GET_KUCHENPLAN_BY_WEEK, week.getWeek(), week.getYear()));
		while (setKpl.next()) {
			kuchenplan = new Kuchenplan(setKpl.getLong(ID), week);
		}
		if (kuchenplan != null) {
			List<KuchenplanHasKuchenrezept> kuchen = new ArrayList<KuchenplanHasKuchenrezept>();
			ResultSet setKuchen = getManaged(MessageFormat.format(
					GET_KUCHENREZEPTE_BY_KUCHENPLAN, kuchenplan.getId()));
			while (setKuchen.next()) {
				Long id = setKuchen.getLong("id");
				String name = setKuchen.getString("name");
				String kommentar = null;
				Kuchenrezept kuchenrezept = new Kuchenrezept(id, name, kommentar);
				int anzahl = setKuchen.getInt("anzahl");
				int tag = setKuchen.getInt("tag");
				List<FussnoteKuchen> fussnoten = Fussnotekuchenverwaltung.getInstance()
						.getFussnoteKuchenByKuchen(id);
				String fn="";
				for(FussnoteKuchen f: fussnoten){
					fn = fn+" ("+f.getAbkuerzung().toString()+")";
				}
				kuchenrezept.setFussnoteKuchen(fussnoten);
				KuchenplanHasKuchenrezept kuchenComp = new KuchenplanHasKuchenrezept(kuchenrezept, tag, anzahl, fn);
				kuchen.add(kuchenComp);
			}
			kuchenplan.setKuchenrezepte(kuchen);
		}
		return kuchenplan;
	}

	public void createKuchenrezepteForKuchenplan(Kuchenplan kpl, Kuchenrezept kuchenrezept,
			String name, int tag, int anzahl) throws ConnectException,
			DAOException {
		putManaged(MessageFormat.format(CREATE_KUCHENREZEPT_FOR_KUCHENPLAN,
				kpl.getId(), kuchenrezept.getId(), tag, anzahl));
	}

	public void createKuchenplan(Kuchenplan kuchenplan) throws ConnectException,
			DAOException {
		Week week = kuchenplan.getWeek();
		putManaged(MessageFormat.format(CREATE_KUCHENPLAN, week.getWeek(),
				week.getYear()));
	}

	public void deleteItemsByKuchenplan(Kuchenplan kuchenplan)
			throws ConnectException, DAOException {
		putManaged(MessageFormat.format(DELETE_KUCHENPLANITEMS_BY_KUCHENPLAN,
				kuchenplan.getId()));
	}
}

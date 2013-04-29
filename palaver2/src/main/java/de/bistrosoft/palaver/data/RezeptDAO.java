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
	private final static String PUT_REZEPT = "INSERT INTO rezept(`name`," + "`rezeptart_fk`," + "`kommentar`,`" + "`portion`," + "`geschmack_fk`," + "`mitarbeiter_fk`)VALUES({0})";
	private final static String UPDATE_REZEPT = "UPDATE rezept SET `name` = {0}, `rezeptart_fk` = {1},`kommentar` = {2},`portion` = {3},`geschmack_fk` = {4},`mitarebietr_fk` = {5} WHERE id = {6} ";
	private final static String GET_GESCHMACK_NAME = "SELECT geschmack.name FROM geschmack JOIN rezept ON geschmack.id = rezept.geschmack_fk WHERE rezept.id = {0};";
	private final static String GET_FUSSNOTEN_REZEPT = " SELECT fussnote.name FROM fussnote JOIN rezept_has_fussnote ON rezept_has_fussnote.fussnote_fk = fussnote.id WHERE rezept_has_fussnote.rezept_fk = {0};";
	private final static String GET_ZUTATEN_REZEPT = "SELECT artikel.name FROM artikel JOIN rezept_has_artikel ON artikel.id = rezept_has_artikel.artikel_fk JOIN rezept ON rezept.id = rezept_has_artikel.rezept_fk WHERE rezept.id = {0};";
	private final static String GET_ZUBEREITUNG_NAME = "SELECT zubereitung.name FROM zubereitung JOIN rezept_has_zubereitung ON rezept_has_zubereitung.rezept_fk = zubereitung.id WHERE rezept_has_zubereitung.rezept_fk = {0};";
	private final static String GET_REZEPTART_NAME = "SELECT rezeptart.name FROM rezeptart JOIN rezept ON rezept.rezeptart_fk = rezeptart.id WHERE rezept.id = {0};";

	public RezeptDAO() {
		super();
	}

	public List<Rezept> getAllRezepts() throws ConnectException, DAOException,
			SQLException {
		List<Rezept> list = new ArrayList<Rezept>();
		ResultSet set = get(GET_ALL_REZEPTS);
		;
		while (set.next()) {
			list.add(new Rezept(RezeptartDAO.getInstance().getRezeptartById(
					set.getLong("id")), GeschmackDAO.getInstance()
					.getGeschmackById(set.getLong("id")),
			// ZubereitungDAO.getInstance().getZubereitungById(set.getLong("id")),
			// MitarbeiterDAO.getInstance().getMitarbeiterByName(set.getLong("id"),
			// null,
					set.getString("name"), null, null
			// set.getString("menues")
			));
		}
		return list;
	}

	public List<Rezept> getRezeptById(int rezeptID) throws ConnectException,
			DAOException, SQLException {
		List<Rezept> rezept = new ArrayList<Rezept>();
		ResultSet set = get(GET_REZEPT_BY_ID);
		while (set.next()) {
			rezept.add(new Rezept(RezeptartDAO.getInstance().getRezeptartById(
					set.getLong(rezeptID)), GeschmackDAO.getInstance()
					.getGeschmackById(set.getLong("id")),
			// ZubereitungDAO.getInstance().getZubereitungById(set.getLong("id")),
			// MitarbeiterDAO.getInstance().getMitarbeiterByName(set.getLong("id"),
			// null,
					set.getString("name"), null, null
			// set.getString("menues")
			));
		}
		return rezept;
	}

	// public void updateRezept(Rezept rezept) throws ConnectException,
	// DAOException {
	// put(MessageFormat.format(UPDATE_REZEPT, "'" + artikel.getArtikelnr()
	// + "'", "'" + artikel.getName() + "'", artikel
	// .getBestellgroesse(), artikel.getMengeneinheit().getId(),
	// artikel.getPreis(), artikel.getLieferant().getId(), Util
	// .convertBoolean(artikel.isBio()), artikel
	// .getKategorie().getId(), Util.convertBoolean(artikel
	// .isStandard()), Util.convertBoolean(artikel
	// .isGrundbedarf()), artikel.getDurchschnitt(), Util
	// .convertBoolean(artikel.isLebensmittel())));
	// }
//	INSERT INTO rezept(`name`," + "`rezeptart_fk`," + "`kommentar`,`" + "`portion`," + "`geschmack_fk`," + "`mitarbeiter_fk`)VALUES({0})";

	public void createRezept(Rezept rezept) throws ConnectException,
			DAOException {
		put(MessageFormat.format(
				PUT_REZEPT,
				"'" + rezept.getName() + "','" + rezept.getRezeptart() + "',"
						+ rezept.getKommentar() + ","
						+ rezept.getPortion() + ","
						+ rezept.getGeschmack().getId() + ","
						+ rezept.getMitarbeiter().getId() + ","
						));
	}

}

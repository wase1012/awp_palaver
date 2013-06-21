package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.emailversand.MailModel;
import de.hska.awp.palaver2.util.Util;

public class MailDAO extends AbstractDAO{

	private static MailDAO instance = null;
	private final static String PUT_EMAIL = "INSERT INTO email(`passwort`,`schlussel`,`pw_length`,`descript`)VALUES({0})";
	private final static String GET_EMAIL_BY_ENUM = "SELECT * FROM email where descript = ";
	public MailDAO() {
		super();
	}

	/**
	 * @return instance
	 */
	public static MailDAO getInstance() {
		if (instance == null) {
			instance = new MailDAO();
		}
		return instance;
	}
	
	public MailModel getMailByEnum(String filter) throws ConnectException, DAOException, SQLException{
		MailModel result = null;
		ResultSet set = getManaged(GET_EMAIL_BY_ENUM + "'" + filter + "'");
		openConnection();
		while (set.next()) {
			result = new MailModel(set.getLong("id"), set.getString("passwort"), set.getString("schlussel"), set.getLong("pw_length"),
					set.getString("descript"));
		}
		
		closeConnection();
		return result;		
	}
	
	public void setMail(MailModel mail) throws ConnectException, DAOException {
		putManaged(MessageFormat.format(
				PUT_EMAIL,
				"'" + mail.getPasswort() + "','" + mail.getSchlussel() + "','" + mail.getLength() + "','" + mail.getDescript() + "'"));
	}
}

package de.hska.awp.palaver2.emailversand;

import javax.mail.*;

/**
 * Klass wird für das Emailversand benötigt
 * 
 * @author Mihail Boehm
 */
public class Mail {
	private static Mail instance = null;

	public Mail() {
		super();
	}

	public static Mail getInstance() {
		if (instance == null) {
			instance = new Mail();
		}
		return instance;
	}

	/**
	 * @param to
	 *            Empfanger
	 * @param subject
	 *            Thema
	 * @param message
	 *            Text
	 */
	public boolean EmailVersand(String to, String subject, String message,
			String anhang) {
		boolean ergebnis = false;
		if (anhang == null) {
			try {
				MailActions.sendOhneAnhang(MailAccounts.NACHRICHT, to, subject, message);
				ergebnis = true;
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		} else
		{
			try {
				MailActions.sendMitAnhang(MailAccounts.NACHRICHT, to, subject, message, anhang);
				ergebnis = true;
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		return ergebnis;
	}
}
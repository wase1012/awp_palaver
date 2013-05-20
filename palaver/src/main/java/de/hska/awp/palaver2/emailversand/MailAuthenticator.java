package de.hska.awp.palaver2.emailversand;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
/**
 * @author Mihail Boehm
 */
public class MailAuthenticator extends Authenticator{
	private String 		user;
    private String 		password;
     
    /**
     * Benutzername und Passwort prüfen
     * @param user
     * @param password
     */
    public MailAuthenticator(String user, String password)
    {
        this.user = user;
        this.password = password;
    }
     
    /**
     * getPasswordAuthentication() wird automatisch aufgerufen
     * sobald der Benutzernamen + Passwort verlangt wird
     */
    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(user, password);
    }
}

package de.hska.awp.palaver2.email;
/**
 * @author Mihon
 */
public enum MailAccounts
{
    // Hier bitte die ensprechenden Attribute jeweils anpassen
    GOOGLEMAIL("smtp.googlemail.com", 25, "mihail.boehm", "GjgjdVbif", "absender");
    /*
    GMX("mail.gmx.net", 25, "login", "passwort", "absender"),
    ARCOR("mail.arcor.de", 25, "login", "passwort", "absender"),
    WEB("smtp.web.de", 25, "login", "passwort", "absender"),
    YAHOO("smtp.mail.yahoo.de", 25, "login", "passwort", "absender"),
     Hier können weitere E-Mail Accounts als ENUM angelegt werden*/
    
     
    private String smtpHost;
    private int port;
    private String username;
    private String password;
    private String email;
     
    /**
     * Setzt die notwendigen Attribute des MailAccounts
     * @param smtpHost - SMTP Host
     * @param port - Port
     * @param username - Benutzername
     * @param password - Passwort
     * @param email - Absender E-Mail
     */
    private MailAccounts(String smtpHost, int port, String username, String password, String email)
    {
        this.smtpHost = smtpHost;
        this.port = port;
        this.username = username;
        this.password = password;
        this.email = email;
    }
     
    public int getPort()
    {
        return port;
    }
     
    public String getSmtpHost()
    {
        return smtpHost;
    }
     
    public MailAuthenticator getPasswordAuthentication()
    {
        return new MailAuthenticator(username, password);
    }
     
    public String getEmail()
    {
        return email;
    }
}

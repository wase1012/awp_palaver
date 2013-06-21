package de.hska.awp.palaver2.emailversand;

/**
 * @author Mihon
 */
public enum MailAccounts {
	 NACHRICHT("smtp.1und1.de", 25, "bestellung@cafepalaver.de", "bestellung@cafepalaver.de", "nachricht");
     
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
	    private MailAccounts(String smtpHost, int port, String username, String email, String filter)
	    {
	        this.smtpHost = smtpHost;
	        this.port = port;
	        this.username = username;
	        try {
				this.password = Mail.getInstance().Password(filter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
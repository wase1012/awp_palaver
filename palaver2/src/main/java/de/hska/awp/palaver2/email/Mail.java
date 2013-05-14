package de.hska.awp.palaver2.email;

import javax.mail.*;
/**
 * Klass wird für das Emailversand benötigt
 * @author Mihail Boehm
 */
public class Mail
{
	private static Mail instance = null;
	
    public Mail()
    {
    	super();    
    }
    
    public static Mail getInstance() {
		if (instance == null) {
			instance = new Mail();
		}
		return instance;
	}
    
    /**
     * @param to Empfanger
     * @param subject Thema
     * @param message Text
     */
    public void EmailVersand(String to, String subject, String message)
    {
    	try {
				MailActions.send(MailAccounts.GOOGLEMAIL, to, subject, message);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
    }
    
    /*
    public void Test()
    {
    	 String recipient = "***";
         String subject = "Hallo zusammen ...";
         String text = "... ich bin eine E-Mail2 : - )";

 			try {
 				MailActions.send(MailAccounts.GOOGLEMAIL, recipient, subject, text);
 				System.out.print("kein Error");
 			} catch (MessagingException e) {
 				// TODO Auto-generated catch block
 				System.out.print("Error " +  e.toString());
 				e.printStackTrace();
 			}
    }
    */
}
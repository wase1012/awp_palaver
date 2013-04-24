/**
 * Created by Sebastian Walz
 * 24.04.2013 17:31:30
 */
package de.hska.awp.palaver2.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@SuppressWarnings("deprecation")
public class HibernateUtil
{
	private static SessionFactory 		sessionFactory;
	
	/**
	 * Erstellt eine neue SessionFactory
	 */
	static
	{
		try
		{
			sessionFactory = new Configuration().configure().buildSessionFactory();
		}
		catch (Throwable e)
		{
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}
	
	/**
	 * Schlieﬂt den Cache und die Verbindung
	 */
	public static void shutDown()
	{
		getSessionFactory().close();
	}
}

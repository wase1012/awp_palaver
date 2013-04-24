/**
 * Created by Sebastian Walz
 * 24.04.2013 17:29:09
 */
package de.hska.awp.palaver2.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HibernateListener implements ServletContextListener
{
	@Override
	public void contextDestroyed(ServletContextEvent arg0)
	{
		HibernateUtil.shutDown();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0)
	{
		HibernateUtil.getSessionFactory();
	}
}

/**
 * Created by Sebastian Walz
 */
package main.java.de.hska.awp.palaver2.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.de.hska.awp.palaver.Application;
import main.java.de.hska.awp.palaver2.gui.layout.DefaultView;
import main.java.de.hska.awp.palaver2.gui.layout.MainLayout;

/**
 * @author Sebastian
 *
 */
public class ViewHandler
{
	private static ViewHandler					instance = null;
	private static final Logger					log = LoggerFactory.getLogger(ViewHandler.class.getName());
	
	private ViewHandler()
	{
		super();
	}
	
	public static ViewHandler getInstance()
	{
		if (instance == null)
		{
			instance = new ViewHandler();
		}
		return instance;
	}
	
	public void switchView(Class<? extends View> view)
	{
		switchView(view, null); 
	}
	
	public void switchView(Class<? extends View> view, ViewData data)
	{
		try
		{
			if (view != null)
			{
				View current = view.newInstance();
				Application.getInstance().getLayout().removeComponent(Application.getInstance().getLayout().getComponent(Application.getInstance().getLayout().getComponentCount() -1));
				Application.getInstance().getLayout().addComponent(view.newInstance());
				Application.getInstance().getLayout().setExpandRatio(Application.getInstance().getLayout().getComponent(Application.getInstance().getLayout().getComponentCount() -1), 1);
				current.getViewParam(data);
				log.info("Switch View to: " + current.getClass());
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			Application.getInstance().getLayout().addComponent(new DefaultView());
			Application.getInstance().getLayout().setExpandRatio(Application.getInstance().getLayout().getComponent(Application.getInstance().getLayout().getComponentCount() -1), 1);
		} 
	}
	
	public void returnToDefault()
	{
		switchView(DefaultView.class);
	}
}

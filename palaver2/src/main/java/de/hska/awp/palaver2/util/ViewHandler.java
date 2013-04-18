/**
 * 
 */
package de.hska.awp.palaver2.util;

import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.gui.layout.DefaultView;
import de.hska.awp.palaver2.gui.layout.MainLayout;

/**
 * @author Sebastian
 *
 */
public class ViewHandler
{
	private static ViewHandler					instance = null;
	
	private Class<? extends VerticalLayout> 	currentView = DefaultView.class;
	
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
	
	public void switchView(Class<? extends VerticalLayout> view)
	{
		try
		{
			if (view != null)
			{
				MainLayout.getInstance().removeComponent(MainLayout.getInstance().getComponent(MainLayout.getInstance().getComponentCount() -1));
				MainLayout.getInstance().addComponent(view.newInstance());
				MainLayout.getInstance().setExpandRatio(MainLayout.getInstance().getComponent(MainLayout.getInstance().getComponentCount() -1), 1);
			}
		} 
		catch (InstantiationException e)
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
}

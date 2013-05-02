/**
 * Created by Sebastian Walz
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
		try
		{
			if (view != null)
			{
				MainLayout.getInstance().removeComponent(MainLayout.getInstance().getComponent(MainLayout.getInstance().getComponentCount() -1));
				MainLayout.getInstance().addComponent(view.newInstance());
				MainLayout.getInstance().setExpandRatio(MainLayout.getInstance().getComponent(MainLayout.getInstance().getComponentCount() -1), 1);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			MainLayout.getInstance().addComponent(new DefaultView());
			MainLayout.getInstance().setExpandRatio(MainLayout.getInstance().getComponent(MainLayout.getInstance().getComponentCount() -1), 1);
		} 
	}
	
	public void switchView(Class<? extends View> view, ViewData data)
	{
		try
		{
			if (view != null)
			{
				View current = view.newInstance();
				MainLayout.getInstance().removeComponent(MainLayout.getInstance().getComponent(MainLayout.getInstance().getComponentCount() -1));
				MainLayout.getInstance().addComponent(current);
				MainLayout.getInstance().setExpandRatio(MainLayout.getInstance().getComponent(MainLayout.getInstance().getComponentCount() -1), 1);
				current.getViewParam(data);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			MainLayout.getInstance().addComponent(new DefaultView());
			MainLayout.getInstance().setExpandRatio(MainLayout.getInstance().getComponent(MainLayout.getInstance().getComponentCount() -1), 1);
		} 
	}
	
	public void returnToDefault()
	{
		switchView(DefaultView.class);
	}
}

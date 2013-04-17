/**
 * 
 */
package de.hska.awp.palaver2.gui.layout;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.MenuItem;

import de.hska.awp.palaver2.gui.view.ArtikelErstellen;

/**
 * @author Sebastian
 *
 */
@SuppressWarnings("serial")
public class MainLayout extends VerticalLayout implements Command
{
	private static MainLayout		instance = null;
	
	private HorizontalLayout		header = new HorizontalLayout();
	
	private MenuBar					menu = new MenuBar();
	
	private MainLayout()
	{
		super();
		
		this.setSizeFull();
		
		header.setWidth("100%");
		header.setHeight("100px");
		
		Image logo = new Image("", new ThemeResource("../img/cafe_palaver_Logo.png"));
		header.addComponent(logo);
		header.setComponentAlignment(logo, Alignment.MIDDLE_RIGHT);
		
		this.addComponent(header);
		
		menu.setWidth("100%");
		menu.addItem("Artikel", this);
		
		this.addComponent(menu);
		
//		VerticalLayout content = new VerticalLayout();
//		content.setSizeFull();
//		
//		this.addComponent(content);
//		
//		this.setExpandRatio(content, 1);
		
		this.addComponent(new ArtikelErstellen());
		this.setExpandRatio(this.getComponent(2), 1);
	}
	
	public static MainLayout getInstance()
	{
		if (instance == null)
		{
			instance = new MainLayout();
		}
		return instance;
	}

	@Override
	public void menuSelected(MenuItem selectedItem)
	{
		
	}
}

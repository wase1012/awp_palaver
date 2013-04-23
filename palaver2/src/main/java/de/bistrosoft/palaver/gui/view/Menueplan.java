package de.bistrosoft.palaver.gui.view;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.menueplanverwaltung.MenueplanGridLayout;


@SuppressWarnings("serial")
public class Menueplan extends VerticalLayout{

	private VerticalLayout	box = new VerticalLayout();

	public Menueplan()
	{
		super();
		this.setSizeFull();
//		this.setWidth("750px");
		this.setMargin(true);
		
		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		
		MenueplanGridLayout menueplan = new MenueplanGridLayout();
		
		box.addComponent(menueplan);
		box.setComponentAlignment(menueplan, Alignment.MIDDLE_CENTER);
	}
	
}

package de.bistrosoft.palaver.gui.view;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.menuplanverwaltung.DragdropGridLayout;

public class Menuplan extends VerticalLayout{

	private VerticalLayout	box = new VerticalLayout();

	public Menuplan()
	{
		super();
//		this.setSizeFull();
		this.setWidth("750px");
		this.setMargin(true);
		
		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		
		box.addComponent(new DragdropGridLayout());
	}
	
}

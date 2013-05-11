package de.bistrosoft.palaver.menueplanverwaltung;

import java.util.ArrayList;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class MenueAddComponent extends CustomComponent implements Component.Listener{
	
	public MenueAddComponent(){
		
		VerticalLayout vl = new VerticalLayout();
		setCompositionRoot(vl);
		
		Label lbText = new Label();
		lbText.setIcon(new ThemeResource("../icons/addIcon.png"));
		vl.addComponent(lbText);
	}

	@Override
	public void componentEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

	
	
}

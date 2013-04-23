package de.bistrosoft.palaver.menueplanverwaltung;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class MenueComponent extends CustomComponent{
	
	public MenueComponent(String text){
		VerticalLayout vl = new VerticalLayout();
		setCompositionRoot(vl);
		Label lbText = new Label(text);
		vl.addComponent(lbText);
		HorizontalLayout hlProp = new HorizontalLayout();
		CheckBox cbVeg = new CheckBox("Veg");
		hlProp.addComponent(cbVeg);
		vl.addComponent(hlProp);
		Button btDelete = new Button("Löschen");
//		btDelete.addClickListener(new ClickListener() {
//			
//			@Override
//			public void buttonClick(ClickEvent event) {
//				Button btTmp = event.getButton();
//				for (int row = 0; row < ROWS; row++) {
//			        for (int col = 0; col < COLUMNS; col++) {
//			        	if(tmp.equals(layout.getComponent(col, row))) {
//			        		layout.removeComponent(tmp);
//				
//			}
//		});
		vl.addComponent(btDelete);
		
		
	}
	
	
	
	
	

}

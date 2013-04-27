package de.bistrosoft.palaver.menueplanverwaltung;

import java.util.ArrayList;

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import fi.jasoft.dragdroplayouts.DDGridLayout;

@SuppressWarnings("serial")
public class MenueComponent extends CustomComponent{
	
	Component destComp;
	int destRow;
	int destCol;
	DDGridLayout menueGrid;
	Button btn = new Button("ADD");
	
	public MenueComponent(String text,DDGridLayout nMenueGrid,int nDestRow, int nDestCol){
		destCol = nDestCol;
		destRow = nDestRow;
		menueGrid = nMenueGrid;
		
		VerticalLayout vl = new VerticalLayout();
		setCompositionRoot(vl);
		
		Label lbText = new Label(text);
		vl.addComponent(lbText);
		
		HorizontalLayout hlProp = new HorizontalLayout();
		
		//Testdaten
		ArrayList<String> eigenschaften = new ArrayList<>();
		eigenschaften.add("W");
		eigenschaften.add("V");
		eigenschaften.add("S");
		eigenschaften.add("D");
		
		for(int i=1;i<eigenschaften.size();++i){
			CheckBox cbTmp = new CheckBox(eigenschaften.get(i));
			cbTmp.setEnabled(true);
//			cbTmp.set
			hlProp.addComponent(cbTmp);
		}
		vl.addComponent(hlProp);
		
		Button btDelete = new Button("Löschen");
		btn.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				menueGrid.removeComponent(btn);
				WinSelectMenue window = new WinSelectMenue(menueGrid, destComp, destRow, destCol);
        		UI.getCurrent().addWindow(window);
        		window.setModal(true);
        		window.setWidth("50%");
        		window.setHeight("50%");
				
			}
		});
		
		btDelete.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(final ClickEvent event) {
				
				ConfirmDialog.show(UI.getCurrent(), "Menü aus Plan löschen:", "Wollen Sie das Menü wirklich aus dem Plan löschen?",
				        "Ja", "Nein", new ConfirmDialog.Listener() {

				            public void onClose(ConfirmDialog dialog) {
				                if (dialog.isConfirmed()) {
				                	menueGrid.removeComponent(destCol, destRow);
				                	menueGrid.addComponent(btn, destCol, destRow);
				        			menueGrid.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
				                }
				            }			            
				        });	
			        }
		});
		
		vl.addComponent(btDelete);
		
		
	}
}

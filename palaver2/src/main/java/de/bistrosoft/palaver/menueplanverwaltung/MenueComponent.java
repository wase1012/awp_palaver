package de.bistrosoft.palaver.menueplanverwaltung;

import java.util.ArrayList;

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.server.ThemeResource;
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
import com.vaadin.ui.themes.BaseTheme;

import fi.jasoft.dragdroplayouts.DDGridLayout;

@SuppressWarnings("serial")
public class MenueComponent extends CustomComponent{
	
	Component destComp;
	public int destRow;
	public int destCol;
	DDGridLayout menueGrid;
	Button btn = new Button();
	Button btDelete = new Button("Löschen");
	
	// Konstruktor für Menükomponente 
	public MenueComponent(String text, DDGridLayout nMenueGrid, int nDestRow, int nDestCol){
		destCol = nDestCol;
		destRow = nDestRow;
		menueGrid = nMenueGrid;
		destComp = this;
		
		// Vertikales Layout erstellen
		VerticalLayout vl = new VerticalLayout();
		setCompositionRoot(vl);
		
		// Menübezeichnung des ausgewählten Menüs der Menükomponente hinzufügen
		@SuppressWarnings("deprecation")
		Label lbText = new Label("<div align=center>"+text+"</div>", Label.CONTENT_XHTML);
		vl.addComponent(lbText);
		
		// Horizontales Layout erstellen
		HorizontalLayout hlProp = new HorizontalLayout();
		
		//Testdaten für Eigenschaften
		ArrayList<String> eigenschaften = new ArrayList<>();
		eigenschaften.add("W");
		eigenschaften.add("V");
		eigenschaften.add("S");
		eigenschaften.add("D");
		
		// Für jede Eigenschaft eine Checkbox erstellen
		for(int i=1;i<eigenschaften.size();++i){
			CheckBox cbTmp = new CheckBox(eigenschaften.get(i));
			cbTmp.setEnabled(true);
			hlProp.addComponent(cbTmp);
		}
		// Horizontales Layout dem vertikalen Layout zufügen
		vl.addComponent(hlProp);
		
		//Clicklistener für den ADD Button
		btn.setStyleName(BaseTheme.BUTTON_LINK);
		btn.setIcon(new ThemeResource("img/addIcon.png"));
		btn.addStyleName("menueplan-add");
        btn.addClickListener(new ClickListener() {
			
        	// Click-Listener für ADD_Buttons
			@Override
			public void buttonClick(ClickEvent event) {
	       		WinSelectMenue window = new WinSelectMenue(menueGrid, btn, destRow, destCol);
        		UI.getCurrent().addWindow(window);
        		window.setModal(true);
        		window.setWidth("50%");
        		window.setHeight("50%");
			}
		});
		btn.setHeight("100px");
		btn.setWidth("149px");
		
		// ClickListener für den Löschbutton
		btDelete.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(final ClickEvent event) {
				
				// Window erstellen welches abfragt, ob man das Menü wirklich aus dem Menüplan löschen will
				ConfirmDialog.show(UI.getCurrent(), "Menü aus Plan löschen:", "Wollen Sie das Menü wirklich aus dem Plan löschen?",
				        "Ja", "Nein", new ConfirmDialog.Listener() {

				            public void onClose(ConfirmDialog dialog) {
				                if (dialog.isConfirmed()) {
				                	//finde position
				                    Component sourceComp = destComp;
				                	Integer sourceRow =-1;
				                    Integer sourceColumn=-1;
				                    
				                    final int COLUMNS = menueGrid.getColumns();
				                    final int ROWS = menueGrid.getRows();
				                    
				                    for (int row = 0; row < ROWS; row++) {
				            	        for (int col = 0; col < COLUMNS; col++) {
				            	        	if(sourceComp.equals(menueGrid.getComponent(col, row))) {
				            	        		sourceColumn=col;
				            	        		sourceRow=row;
				            	        	}
				            	        }
				                    }		                    
				                	
				                	//aktuelle Menükomponente löschen
				                	menueGrid.removeComponent(destComp);
				                	
				                	//ADD Button hinzufügen
				                	menueGrid.addComponent(btn, sourceColumn, sourceRow);
				        			menueGrid.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
				                }
				            }			            
				        });	
			        }
		});
		
		vl.addComponent(btDelete);
		vl.setComponentAlignment(lbText, Alignment.MIDDLE_CENTER);
		vl.setComponentAlignment(hlProp, Alignment.MIDDLE_CENTER);
		vl.setComponentAlignment(btDelete, Alignment.MIDDLE_CENTER);
		vl.setHeight("100px");
		
		
	}
}

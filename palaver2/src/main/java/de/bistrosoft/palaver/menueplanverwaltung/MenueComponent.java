package de.bistrosoft.palaver.menueplanverwaltung;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Reindeer;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.regelverwaltung.domain.Regel;

import fi.jasoft.dragdroplayouts.DDGridLayout;

@SuppressWarnings("serial")
public class MenueComponent extends CustomComponent{
	
	private Component comp;
	public int row;
	public int col;
	public MenueplanGridLayout menueplan;
	private DDGridLayout menueGrid;
	private Button btn = new Button();
	private Button btDelete = new Button("Löschen");
	private Button btChange = new Button("Ändern");
	private Menue menue;
	private Boolean isChanged;
	private Button btFehler;
	
	private List<String> fehlermeldungen;
	private List<Regel> FehlerRegeln;
	
	String descNotification;
	
	
	
	public List<Regel> getFehlerRegeln() {
		return FehlerRegeln;
	}

	public void setFehlerRegeln(List<Regel> fehlerRegeln) {
		FehlerRegeln = fehlerRegeln;
	}

	public List<String> getFehlermeldungen() {
		return fehlermeldungen;
	}

	public void setFehlermeldungen(List<String> fehlermeldungen) {
		this.fehlermeldungen = fehlermeldungen;
		if(fehlermeldungen!=null && fehlermeldungen.size()>0){
			btFehler.setVisible(true);
			String desc="";
			descNotification="";
			for (String s : fehlermeldungen){
				desc+=s;
				descNotification+=s;
			}
			btFehler.setDescription(desc);
			
			btFehler.addClickListener(new ClickListener() {

				// Click-Listener Fehler-Buttons
				@Override
				public void buttonClick(ClickEvent event) {
					Notification notification = new Notification(descNotification);
					notification.setDelayMsec(500);
					notification.show(Page.getCurrent());
				}
			});
		}
		else btFehler.setVisible(false);
		
	}

	public MenueplanGridLayout getMenueplan() {
		return menueplan;
	}

	public void setMenueplan(MenueplanGridLayout menueplan) {
		this.menueplan = menueplan;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public Menue getMenue() {
		return menue;
	}

	public void setMenue(Menue menue) {
		this.menue = menue;
	}

	public DDGridLayout getMenueGrid() {
		return menueGrid;
	}

	public void setMenueGrid(DDGridLayout menueGrid) {
		this.menueGrid = menueGrid;
	}

	public Boolean isChanged() {
		return isChanged;
	}

	public void isChanged(Boolean isChanged) {
		this.isChanged = isChanged;
	}
	
	public void addFehlerRegel(Regel menueplanRegel) {
		if (menueplanRegel == null){
			return;
		}
		
		if(FehlerRegeln == null){
			FehlerRegeln = new ArrayList<>();
		}
		
		if (FehlerRegeln.indexOf(menueplanRegel)>=0){
			return;
		}
		
		FehlerRegeln.add(menueplanRegel);
		
		btFehler.setVisible(true);
		
		String desc = "<html>";
		descNotification = "";
		
		for (Regel r : FehlerRegeln){
			desc += r.getFehlermeldung() + "<br>";
			descNotification += r.getFehlermeldung() + "\n\r";
		}
		
		desc += "</html>";
		
		btFehler.setDescription(desc);
		
		btFehler.addClickListener(new ClickListener() {

			// Click-Listener Fehler-Buttons
			@Override
			public void buttonClick(ClickEvent event) {
				Notification notification = new Notification(descNotification);
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
			}
		});
	}

	// Konstruktor für Menükomponente 
	public MenueComponent(Menue menue, MenueplanGridLayout nMenueplan, DDGridLayout nMenueGrid, int nRow, int nCol, Boolean isChanged){
		this.isChanged = isChanged;
		col = nCol;
		row = nRow;
		this.menueplan=nMenueplan;
		menueGrid = nMenueGrid;
		comp = this;
		this.setMenue(menue);
		
		// Vertikales Layout erstellen
		VerticalLayout vl = new VerticalLayout();
		setCompositionRoot(vl);
		
		// Menübezeichnung des ausgewählten Menüs der Menükomponente hinzufügen
		@SuppressWarnings("deprecation")
		Label lbText = new Label("<div align=center>"+ menue.getName() +"</div>", Label.CONTENT_XHTML);
		vl.addComponent(lbText);
		
//		// Horizontales Layout erstellen
//		HorizontalLayout hlProp = new HorizontalLayout();
//		
//		// Horizontales Layout dem vertikalen Layout zufügen
//		vl.addComponent(hlProp);
		
		//Clicklistener für den ADD Button
		btn.setStyleName(BaseTheme.BUTTON_LINK);
		btn.setIcon(new ThemeResource("img/Menue.png"));
		btn.addStyleName("menueplan-add");
        btn.addClickListener(new ClickListener() {
			
        	// Click-Listener für ADD_Buttons
			@Override
			public void buttonClick(ClickEvent event) {
	       		WinSelectMenue window = new WinSelectMenue(menueplan, btn, row, col);
        		UI.getCurrent().addWindow(window);
        		window.setModal(true);
        		window.setWidth("50%");
        		window.setHeight("50%");
			}
		});
		btn.setHeight("90px");
		btn.setWidth("149px");
		
		
		//ClickListener für den Ändernbutton
		btChange.addClickListener(new ClickListener(){
			
			@Override
			public void buttonClick(final ClickEvent event) {
				
	        	//finde position
				Component sourceComp = comp;
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
	            //Add
	
	            WinSelectMenue window = new WinSelectMenue(menueplan, sourceComp, row, col);
	    		UI.getCurrent().addWindow(window);
	    		window.setModal(true);
	    		window.setWidth("50%");
	    		window.setHeight("50%");
			}
		});
		        
		
		
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
				                    Component sourceComp = comp;
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
				                	menueGrid.removeComponent(comp);
				                	
				                	//ADD Button hinzufügen
				                	menueGrid.addComponent(btn, sourceColumn, sourceRow);
				        			menueGrid.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
				                }
				            }			            
				        });	
			        }
		});
		
		btFehler= new Button();
		btFehler.setStyleName(BaseTheme.BUTTON_LINK);
		btFehler.setIcon(new ThemeResource("img/icon_fehler.bmp"));
//		btFehler.addStyleName("menueplan-add");
		vl.addComponent(btFehler);
		btFehler.setDescription("Fehler");
		btFehler.setVisible(false);
//		vl.setComponentAlignment(btFehler, Alignment.MIDDLE_CENTER);
		
		HorizontalLayout hl = new HorizontalLayout();
		btChange.setStyleName(Reindeer.BUTTON_SMALL);
		btDelete.setStyleName(Reindeer.BUTTON_SMALL);
		hl.addComponent(btChange);
		hl.addComponent(btDelete);
		vl.addComponent(hl);
		vl.setComponentAlignment(lbText, Alignment.MIDDLE_CENTER);
//		vl.setComponentAlignment(hlProp, Alignment.MIDDLE_CENTER);
		vl.setComponentAlignment(hl, Alignment.BOTTOM_CENTER);
//		vl.setComponentAlignment(btDelete, Alignment.BOTTOM_RIGHT);
		vl.setHeight("90px");
		
		
	}
}

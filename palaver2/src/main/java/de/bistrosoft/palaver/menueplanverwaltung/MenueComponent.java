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
import com.vaadin.ui.themes.Reindeer;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;

import fi.jasoft.dragdroplayouts.DDGridLayout;

@SuppressWarnings("serial")
public class MenueComponent extends CustomComponent{
	
	private Component comp;
	public int row;
	public int col;
	private DDGridLayout menueGrid;
	private Button btn = new Button();
	private Button btDelete = new Button("Löschen");
	private Button btChange = new Button("Ändern");
	private Menue menue;
	private Boolean isChanged;
	
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

	// Konstruktor für Menükomponente 
	public MenueComponent(Menue menue, DDGridLayout nMenueGrid, int nRow, int nCol, Boolean isChanged){
		this.isChanged = isChanged;
		col = nCol;
		row = nRow;
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
		
		// Horizontales Layout erstellen
		HorizontalLayout hlProp = new HorizontalLayout();
		
		//Testdaten für Eigenschaften
		ArrayList<String> eigenschaften = new ArrayList<>();
		eigenschaften.add("W");
		eigenschaften.add("V");
		eigenschaften.add("S");
		eigenschaften.add("D");
		
		// Für jede Eigenschaft eine Checkbox erstellen
//		for(int i=1;i<eigenschaften.size();++i){
//			CheckBox cbTmp = new CheckBox(eigenschaften.get(i));
//			cbTmp.setEnabled(true);
//			hlProp.addComponent(cbTmp);
//		}
		// Horizontales Layout dem vertikalen Layout zufügen
		vl.addComponent(hlProp);
		
		//Clicklistener für den ADD Button
		btn.setStyleName(BaseTheme.BUTTON_LINK);
		btn.setIcon(new ThemeResource("img/Menue.png"));
		btn.addStyleName("menueplan-add");
        btn.addClickListener(new ClickListener() {
			
        	// Click-Listener für ADD_Buttons
			@Override
			public void buttonClick(ClickEvent event) {
	       		WinSelectMenue window = new WinSelectMenue(menueGrid, btn, row, col);
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
				
				// Window erstellen welches abfragt, ob man das Menü wirklich aus dem Menüplan löschen will
				ConfirmDialog.show(UI.getCurrent(), "Menü ändern:", "Wollen Sie das Menü wirklich durch ein anderes ersetzen?",
				        "Ja", "Nein", new ConfirmDialog.Listener() {

							//löschen
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
				                	//Add
				                	menueGrid.addComponent(btn, sourceColumn, sourceRow);
				        			menueGrid.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
				        			btn.click();
				                }
				            }			            
				        });	
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
		
		HorizontalLayout hl = new HorizontalLayout();
		btChange.setStyleName(Reindeer.BUTTON_SMALL);
		btDelete.setStyleName(Reindeer.BUTTON_SMALL);
		hl.addComponent(btChange);
		hl.addComponent(btDelete);
		vl.addComponent(hl);
		vl.setComponentAlignment(lbText, Alignment.MIDDLE_CENTER);
		vl.setComponentAlignment(hlProp, Alignment.MIDDLE_CENTER);
		vl.setComponentAlignment(hl, Alignment.BOTTOM_CENTER);
//		vl.setComponentAlignment(btDelete, Alignment.BOTTOM_RIGHT);
		vl.setHeight("90px");
		
		
	}
}

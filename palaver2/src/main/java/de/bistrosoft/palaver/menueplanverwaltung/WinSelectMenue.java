package de.bistrosoft.palaver.menueplanverwaltung;
import java.util.ArrayList;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueverwaltung;
import fi.jasoft.dragdroplayouts.DDGridLayout;

@SuppressWarnings("serial")
public class WinSelectMenue extends Window {
	
	Window subwindow = this;
	
	private Table menueList = new Table();
	private TextField searchField = new TextField();
	private ArrayList<TextField> textfields = new ArrayList<>();
	private Button addNewmenueButton = new Button("Neu");
	private Button ok = new Button("Auswählen");
	private FormLayout editorLayout = new FormLayout();
	private FieldGroup editorFields = new FieldGroup();
	
	private static final String MENU = "name";
	private static final String[] fieldNames = new String[] { MENU, "id", "koch"};
	
	Component destComp;
	int destRow;
	int destCol;
	DDGridLayout menueGrid;

	BeanItemContainer<Menue>  menueContainer;

	// Konstruktor 
	public WinSelectMenue(DDGridLayout nMenueGrid,Component nDestComp,int nDestRow, int nDestCol) {
		menueGrid = nMenueGrid;
		destComp=nDestComp;
		destCol = nDestCol;
		destRow = nDestRow;
		
		initLayout();
		initMenueList();
		initEditor();
		initSearch();
		initOKButton();
	}
	
	// Layout festlegen
	private void initLayout() {

		//SplitPanel erstellen
		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		setContent(splitPanel);

		// Horizontales und vertikales Layout anlegen
		HorizontalLayout bottomLeftLayout = new HorizontalLayout();
		VerticalLayout leftLayout = new VerticalLayout();
		
		// Splitpanel die Layouts zufügen
		splitPanel.addComponent(leftLayout);
		splitPanel.addComponent(editorLayout);
		
		// Der linken Seite Tabelle hinzufügen und Layout für Suchfeld und "Neu" Button
		leftLayout.addComponent(menueList);
		leftLayout.addComponent(bottomLeftLayout);
		
		// Suchfeld und "Neu" Button hinzufügen
		bottomLeftLayout.addComponent(searchField);
		bottomLeftLayout.addComponent(addNewmenueButton);

		// Linke Seite Höhe und Breite auf 100% setzen
		leftLayout.setSizeFull();

		// Kontaktliste soll gesamten Platz verwenden
		leftLayout.setExpandRatio(menueList, 1);
		menueList.setSizeFull();

		// "bottomleftlayout" soll gesamte Breite verwenden
		bottomLeftLayout.setWidth("100%");
		
		// Suchfeld soll verfügbare Breite verwenden
		searchField.setWidth("100%");
		bottomLeftLayout.setExpandRatio(searchField, 1);

		editorLayout.setMargin(true);
		editorLayout.setVisible(false);
	}

	// Rechte Seite 
	private void initEditor() {

		// Für jeden Feldnamen 
		for (String fieldName : fieldNames) {
			
			// erster Buchstabe groß
			String f = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			
			// soll ein Textfeld erstellt werden
			TextField field = new TextField(f);
			field.setEnabled(false);
			// Inhalt des Feldes in Array speichern
			textfields.add(field);
			
			// dem EditorLayout das Textfeld hinzufügen
			
				editorLayout.addComponent(field);
				field.setWidth("100%");

				editorFields.bind(field, fieldName);

			
						
			if(fieldName == "id"){
				field.setVisible(false);
			}
		}

		editorFields.setBuffered(false);
		
		// Auswahl Button hinzufügen
		editorLayout.addComponent(ok);
	}

	// Suchfeld 
	private void initSearch() {

		// Info im Suchfeld setzen
		searchField.setInputPrompt("Menü suchen");

		// TextChangeEvent wird ausgelöst, wenn bei der Eingabe eine Pause ist
		searchField.setTextChangeEventMode(TextChangeEventMode.LAZY);

		// ChangeListener hinzufügen
		searchField.addTextChangeListener(new TextChangeListener() {
				public void textChange(final TextChangeEvent event) {

					// Alle Filter entfernen
					menueContainer.removeAllContainerFilters();
					// Nur den eingegeben Filter hinzufügen
					menueContainer.addContainerFilter(new MenueFilter(event
							.getText()));
				}
		});
	}

	// Klasse für MenueFilter
	private class MenueFilter implements Filter {
		private String needle;

		// Konstruktor für Menuefilter
		public MenueFilter(String needle) {
			// string in Kleinbuchstaben
			this.needle = needle.toLowerCase();
		}

		public boolean passesFilter(Object itemId, Item item) {
			String haystack = ("" + item.getItemProperty(MENU).getValue()).toLowerCase();
			return haystack.contains(needle);
		}

		public boolean appliesToProperty(Object id) {
			return true;
		}
	}

	// Clicklistener für Auswahl Button
	private void initOKButton() {
		ok.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				// aktuelle Column und Row ermitteln
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
                
				// Menübezeichnung des ausgewählten Menüs
				String titel = textfields.get(0).getValue();
				String s = textfields.get(1).getValue();
				Long id = Long.parseLong(s.trim());
				String k = textfields.get(2).getValue();
//				Mitarbeiter m = Mitarbeiterverwaltung.getInstance().getMitarbeiterById(id)
				Menue menue = new Menue(id,titel,k);
				// Aktuelle Menükomponente aus Plan löschen
				menueGrid.removeComponent(destComp);
				
				// Neue Menükomponente aus ausgewähltem Menü erstellen und hinzufügen
				MenueComponent menueComp = new MenueComponent(menue, menueGrid, sourceRow, sourceColumn,true);
				menueGrid.addComponent(menueComp, sourceColumn, sourceRow);
				menueGrid.setComponentAlignment(menueComp, Alignment.MIDDLE_CENTER);
				
				// Window schließen
				subwindow.close();
		}
	});
	}

	private void initMenueList() {
		
		// Container für Menüliste festlegen
		menueContainer = new BeanItemContainer<Menue>(Menue.class, Menueverwaltung.getInstance().getAllMenues());
		menueList.setContainerDataSource(menueContainer);

		// Spalten festlegen
		menueList.setVisibleColumns(new Object[] {"name"});
		menueList.sort(new Object[] {"name"}, new boolean[] {true});
		
		// Spaltenbezeichnung angeben
		menueList.setSelectable(true);
		menueList.setImmediate(true);

		//Changelistener hinzufügen
		menueList.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				// Angeklicktes Objekt 
				Object menueId = menueList.getValue();

				// Falls Objekt nicht null ist
				if (menueId != null)
					
					// auf der rechten Seite anzeigen
					editorFields.setItemDataSource(menueList
							.getItem(menueId));

				editorLayout.setVisible(menueId != null);
			}
		});
	}

}

package de.bistrosoft.palaver.menueplanverwaltung;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
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
	
	Menue menue;
	private Table menueList = new Table();
	private TextField searchField = new TextField();
	private TextField id = new TextField("Id");
	private TextField angezName = new TextField("Angezeigter Name");
	private TextField name = new TextField("Name");
	private TextField koch = new TextField("Koch");
	private TextField menueart = new TextField("Menueart");
	private TextField geschmack = new TextField("Geschmack");
	private CheckBox aufwand = new CheckBox("Aufwand");
	private CheckBox favorit = new CheckBox("Favorit");
	private Button addNewmenueButton = new Button("Neu");
	private Button ok = new Button("Auswählen");
	private FormLayout editorLayout = new FormLayout();

	private static final String MENU = "name";
	
	Component destComp;
	int destRow;
	int destCol;
	MenueplanGridLayout menueplan;
	DDGridLayout menueGrid;

	BeanItemContainer<Menue>  menueContainer;
	
	// Horizontales und vertikales Layout anlegen
	HorizontalLayout bottomLeftLayout = new HorizontalLayout();
	VerticalLayout leftLayout = new VerticalLayout();
	
	// Konstruktor 
	public WinSelectMenue(MenueplanGridLayout nMenuePlan,Component nDestComp,int nDestRow, int nDestCol) {
		setCaption("Menü einfügen");
		menueplan = nMenuePlan;
		menueGrid=menueplan.layout;
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

		
		// Splitpanel die Layouts zufï¿½gen
		splitPanel.addComponent(leftLayout);
		splitPanel.addComponent(editorLayout);
		
		// Der linken Seite Tabelle hinzufï¿½gen und Layout fï¿½r Suchfeld und "Neu" Button
		leftLayout.addComponent(menueList);
		leftLayout.addComponent(bottomLeftLayout);
		
		// Suchfeld und "Neu" Button hinzufï¿½gen
		bottomLeftLayout.addComponent(searchField);
		bottomLeftLayout.addComponent(addNewmenueButton);

		// Linke Seite Hï¿½he und Breite auf 100% setzen
		leftLayout.setSizeFull();

		// Kontaktliste soll gesamten Platz verwenden
		leftLayout.setExpandRatio(menueList, 1);
		menueList.setSizeFull();

		// "bottomleftlayout" soll gesamte Breite verwenden
		bottomLeftLayout.setWidth("100%");
		
		// Suchfeld soll verfï¿½gbare Breite verwenden
		searchField.setWidth("100%");
		bottomLeftLayout.setExpandRatio(searchField, 1);

		editorLayout.setMargin(true);
		editorLayout.setVisible(false);
	}

	// Rechte Seite 
	private void initEditor() {

		HorizontalLayout v = new HorizontalLayout();
		v.addComponents(favorit, aufwand);
		editorLayout.addComponents(angezName, name, koch, menueart, geschmack, v);

		editorLayout.addComponent(ok);
	}

	// Suchfeld 
	private void initSearch() {

		// Info im Suchfeld setzen
		searchField.setInputPrompt("Menü suchen");
			
		// TextChangeEvent wird ausgelï¿½st, wenn bei der Eingabe eine Pause ist
		searchField.setTextChangeEventMode(TextChangeEventMode.LAZY);

		// ChangeListener hinzufï¿½gen
		searchField.addTextChangeListener(new TextChangeListener() {
				public void textChange(final TextChangeEvent event) {

					// Alle Filter entfernen
					menueContainer.removeAllContainerFilters();
					// Nur den eingegeben Filter hinzufÃ¼gen
					menueContainer.addContainerFilter(new MenueFilter(event
							.getText()));
				}
		});
	}

	// Klasse fï¿½r MenueFilter
	private class MenueFilter implements Filter {
		private String needle;

		// Konstruktor fï¿½r Menuefilter
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

	// Clicklistener fï¿½r Auswahl Button
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
                
                menueplan.removeMenue(destComp);
                
				// Menï¿½bezeichnung des ausgewï¿½hlten Menï¿½s
				try {
					Menue menue = Menueverwaltung.getInstance().getMenueByName(name.getValue());
					// Neue Menï¿½komponente aus ausgewï¿½hltem Menï¿½ erstellen und hinzufï¿½gen
					MenueComponent menueComp = new MenueComponent(menue,angezName.getValue(), menueplan, menueGrid, sourceRow, sourceColumn,true);
					menueplan.addMenue(menueComp, sourceColumn, sourceRow);
					menueGrid.setComponentAlignment(menueComp, Alignment.MIDDLE_CENTER);
									
				} catch (Exception e) {
					
					e.printStackTrace();
				} 

				// Window schlieï¿½en
				subwindow.close();
		}
	});
	}

	private void initMenueList() {
		
		// Container fï¿½r Menï¿½liste festlegen
		menueContainer = new BeanItemContainer<Menue>(Menue.class, Menueverwaltung.getInstance().getAllMenuesTabelle());
		
		menueList.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					menue = (Menue) event.getProperty().getValue();
					id.setValue(menue.getId().toString());
					id.setVisible(false);
					angezName.setValue(menue.getName());
					name.setValue(menue.getName());
					name.setEnabled(false);
					koch.setValue(menue.getKochname());
					koch.setEnabled(false);
					if(menue.getMenueart()!=null){						
						menueart.setValue(menue.getMenueart().getName());
					}
					menueart.setEnabled(false);
					if(menue.getGeschmack()!=null){						
						geschmack.setValue(menue.getGeschmack().getName());
					}
					geschmack.setEnabled(false);
					favorit.setValue(menue.getFavorit());
					aufwand.setValue(menue.getAufwand());
					favorit.setEnabled(false);
					aufwand.setEnabled(false);
					editorLayout.setVisible(true);
					
				}

			}
		});
		menueList.setContainerDataSource(menueContainer);

		// Spalten festlegen
		menueList.setVisibleColumns(new Object[] {"name"});
		menueList.sort(new Object[] {"name"}, new boolean[] {true});
		
		// Spaltenbezeichnung angeben
		menueList.setSelectable(true);
		menueList.setImmediate(true);
		
		// bei Ã„ndern Komponente aus Menï¿½plan selektieren
		if (menueGrid.getComponent(destCol, destRow).toString().contains("de.bistrosoft.palaver.menueplanverwaltung.MenueComponent")){
			Integer MenueSelected = new Integer (((MenueComponent) menueGrid.getComponent(destCol, destRow)).getMenue().getId().intValue());
			Integer Index = new Integer(0);
			for (int i = 0; i < menueList.getItemIds().size(); i++) {
				if(menueContainer.getIdByIndex(i).getId().intValue() == MenueSelected){
					Index = i;
				}
			}
			menueList.select(menueContainer.getIdByIndex(Index));

		}
		
	}

}

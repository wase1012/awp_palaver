package de.bistrosoft.palaver.gui.view;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenrezeptHasArtikel;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Kuchenrezeptverwaltung;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.IConstants;

/**
 * @author Christine Hartkorn
 * 
 */
@SuppressWarnings("serial")
public class KuchenrezeptAnlegen extends VerticalLayout implements View,
		ValueChangeListener {

	// Layouts
	private VerticalLayout box = new VerticalLayout();
	private HorizontalLayout infos = new HorizontalLayout();
	private VerticalLayout infosLinks = new VerticalLayout();
	private HorizontalLayout control = new HorizontalLayout();
	private HorizontalLayout hlRezeptZutaten = new HorizontalLayout();

	// Tabellen
	private Table zutatenTable;
	private FilterTable artikelTable;
	
	List<KuchenrezeptHasArtikel> tmpZutaten = new ArrayList<KuchenrezeptHasArtikel>();
	
	//BeanContainer
	private BeanItemContainer<Artikel> containerArtikel;
	private BeanItemContainer<KuchenrezeptHasArtikel> containerKuchenrezeptHasArtikel;

	// Überschriften
	@SuppressWarnings("deprecation")
	private Label ueberschrift = new Label(
			"<pre><font size='4px' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Kuchenrezept anlegen</font></pre>",
			Label.CONTENT_XHTML);
	@SuppressWarnings("deprecation")
	private Label ueberschrift2 = new Label(
			"<pre><font size='4px' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Kuchenrezept bearbeiten</font></pre>",
			Label.CONTENT_XHTML);
	@SuppressWarnings({ "unused", "deprecation" })
	private Label d1 = new Label("<div>&nbsp;&nbsp;&nbsp;</div>",
			Label.CONTENT_XHTML);

	// Textfeld
	private TextField name = new TextField("Bezeichnung");
	
	// NativeSelect
	private NativeSelect mitarbeiterNs = new NativeSelect("Bäcker");

	// Textarea
	private TextArea kommentar = new TextArea("Kommentar");
	
	// Buttons
	private Button btSpeichern = new Button("Speichern");
	private Button verwerfen = new Button("Verwerfen");
	private Button update = new Button(IConstants.BUTTON_SAVE);

	// Strings
	private String nameInput;
	private String kommentarInput;
	private String mitarbeiterInput;
	public String valueString = new String();

	Kuchenrezept kuchenrezept;
	
	// Listen
	List<KuchenrezeptHasArtikel> ausgArtikel = new ArrayList<KuchenrezeptHasArtikel>();
	List<KuchenrezeptHasArtikel> artikel = new ArrayList<KuchenrezeptHasArtikel>();

	// Konstruktor
	public KuchenrezeptAnlegen() {

		super();
		this.setSizeFull();
		this.setMargin(true);

		// Komponenten
		
		name.setWidth("100%");
		name.setImmediate(true);
		name.setMaxLength(200);
		name.setInputPrompt(nameInput);

		mitarbeiterNs.setWidth("100%");
		mitarbeiterNs.setImmediate(true);
		mitarbeiterNs.setData(mitarbeiterInput);
		mitarbeiterNs.setNullSelectionAllowed(false);

		kommentar.setImmediate(true);
		kommentar.setMaxLength(1000);

		box.setWidth("800px");
		box.setSpacing(true);
		
		infos.setWidth("100%");
		infos.setSpacing(true);
		
		this.addComponent(box);
		box.addComponent(ueberschrift);
		ueberschrift.setWidth("300px");
		box.addComponent(infos);
		infos.addComponent(infosLinks);
		infosLinks.addComponent(name);
		name.setWidth("100%");
		infosLinks.addComponent(mitarbeiterNs);
		infosLinks.setWidth("350px");
		mitarbeiterNs.setWidth("100%");
		box.addComponent(hlRezeptZutaten);
		infos.addComponent(kommentar);

		control.setSpacing(true);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_CENTER);
		btSpeichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));

		control.addComponent(verwerfen);
		control.addComponent(btSpeichern);

		// ValueChangeListener Name
		name.addValueChangeListener(this);

		// ValueChangeListener Mitarbeiter
		mitarbeiterNs.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
				mitarbeiterInput = valueString;
			}
		});
		
		kommentar.setHeight("70px");
		kommentar.setWidth("350px");
		infos.setComponentAlignment(kommentar, Alignment.MIDDLE_LEFT);
		// ValueChangeListener Kommentar
		kommentar.addValueChangeListener(this);

		// ClickListener Verwerfen
		verwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().returnToDefault();
			}
		});
		
		// ClickListener Speichern
		btSpeichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if(validiereEingabe()){					
					speichern();
				}
			}
		});
		
		hlRezeptZutaten.setWidth("800px");
		hlRezeptZutaten.setHeight("393px");		
		
		zutatenTable = new Table();
		zutatenTable.setSizeFull();
		zutatenTable.setWidth("435px");
		zutatenTable.setStyleName("palaverTable2");
		zutatenTable.setPageLength(16);
		zutatenTable.setImmediate(true);
		zutatenTable.setColumnWidth("artikelname", 200);
		zutatenTable.setColumnWidth("menge", 100);
		zutatenTable.setColumnWidth("einheit", 90);

		artikelTable = new FilterTable();
		artikelTable.setSizeFull();
		artikelTable.setWidth("270px");
		artikelTable.setStyleName("palaverTable2");

		artikelTable.setFilterBarVisible(true);
		artikelTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);

		containerKuchenrezeptHasArtikel = new BeanItemContainer<KuchenrezeptHasArtikel>(
				KuchenrezeptHasArtikel.class);
		zutatenTable.setContainerDataSource(containerKuchenrezeptHasArtikel);
		zutatenTable.setVisibleColumns(new Object[] { "artikelname","menge", "einheit" });
		zutatenTable.setEditable(true);
		
		hlRezeptZutaten.addComponent(zutatenTable);
		hlRezeptZutaten.addComponent(artikelTable);
		
		// Aufteilung im Layout
		hlRezeptZutaten.setExpandRatio(zutatenTable, 3);
		hlRezeptZutaten.setExpandRatio(artikelTable, 2);
		hlRezeptZutaten.setSpacing(true);
		
		// Überschriften
		artikelTable.setCaption("Artikel");
		zutatenTable.setCaption("Zutatenliste");

		// Drag&Drop
		artikelTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Zutat loeschen und Artikel wieder in Liste setzen.
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				KuchenrezeptHasArtikel selected = (KuchenrezeptHasArtikel) t
						.getData("itemId");
				containerKuchenrezeptHasArtikel.removeItem(selected);
				tmpZutaten.remove(selected);
				containerArtikel.addItem(selected.getArtikel());
				artikelTable.markAsDirty();
				zutatenTable.markAsDirty();
			}
		});

		zutatenTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		
		// Drag&Drop
		zutatenTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Verschiebt einen Artikel in die Zutatenliste.
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof Artikel) {
					Artikel selected = (Artikel) t.getData("itemId");
					containerArtikel.removeItem(selected);
					KuchenrezeptHasArtikel tmp=new KuchenrezeptHasArtikel(selected);
					tmpZutaten.add(tmp);
					containerKuchenrezeptHasArtikel.addItem(tmp);
				}

				artikelTable.markAsDirty();
				zutatenTable.markAsDirty();
			}
		});

		try {
			containerArtikel = new BeanItemContainer<Artikel>(Artikel.class,
					Artikelverwaltung.getInstance().getAllArtikel());
			artikelTable.setContainerDataSource(containerArtikel);
			artikelTable
					.setVisibleColumns(new Object[] { "name" });
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		load();
		
		//Baecker auf aktuellen User setzen
		mitarbeiterNs.select(Application.getInstance().getUser());
	}

	public void load() {

		mitarbeiterNs.removeAllItems();

		try {
			List<Mitarbeiter> mitarbeiter = Mitarbeiterverwaltung.getInstance()
					.getAllMitarbeiter();
			for (Mitarbeiter e : mitarbeiter) {
				mitarbeiterNs.addItem(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void getViewParam(ViewData data) {

		kuchenrezept = (Kuchenrezept) ((ViewDataObject<?>) data).getData();
		try {
			kuchenrezept = Kuchenrezeptverwaltung.getInstance().getKuchenrezeptById(kuchenrezept.getId(),true);
		} catch (ConnectException e2) {
			e2.printStackTrace();
		} catch (DAOException e2) {
			e2.printStackTrace();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		if(kuchenrezept.getArtikel() != null){
			tmpZutaten = kuchenrezept.getArtikel();
		}
		else{
			tmpZutaten = new ArrayList<KuchenrezeptHasArtikel>();
		}
		
		control.replaceComponent(btSpeichern, update);
		box.replaceComponent(ueberschrift, ueberschrift2);
		ueberschrift2.setWidth("300px");
		

		update.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		update.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if(validiereEingabe()){					
					update();
				}
			}
		});

		/**
		 * Daten in Felder schreiben
		 */
		
			name.setValue(kuchenrezept.getName());

			mitarbeiterNs.select(kuchenrezept.getMitarbeiter());

			BeanItemContainer<KuchenrezeptHasArtikel> artikelcontainer;
			List<KuchenrezeptHasArtikel> list = new ArrayList<KuchenrezeptHasArtikel>();
	
			try {
				list = Kuchenrezeptverwaltung.getInstance().getAllArtikelByKuchenrezeptId1(
						kuchenrezept);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
	
			try {
				artikelcontainer = new BeanItemContainer<KuchenrezeptHasArtikel>(
						KuchenrezeptHasArtikel.class);
			
			zutatenTable = null;
			zutatenTable = new Table();
			zutatenTable.setSizeFull();
			zutatenTable.setStyleName("palaverTable2");
			zutatenTable.setImmediate(true);
			
			zutatenTable.setContainerDataSource(artikelcontainer);
			zutatenTable.setVisibleColumns(new Object[] { "artikelname", "menge", "einheit" });
			
			tmpZutaten=list;
			
			for(KuchenrezeptHasArtikel kha : list){
				containerArtikel.removeItem(kha);
				containerKuchenrezeptHasArtikel.addItem(kha);				
			}
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		kuchenrezept.setArtikel(list);

		kommentar.setValue(kuchenrezept.getKommentar());
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		nameInput = name.getValue();
		kommentarInput = kommentar.getValue();
	}
	
	// Funktion zum Speichern
	private void speichern() {
		KuchenrezeptSpeichern();
	}
	
	//Funktion zum Speichern eines Kuchenrezeptes
	private void KuchenrezeptSpeichern(){
		Kuchenrezept kuchenrezept = new Kuchenrezept();

		kuchenrezept.setName(nameInput);

		java.util.Date date = new java.util.Date();
		Date date2 = new Date(date.getTime());

		kuchenrezept.setErstellt(date2);

		kuchenrezept.setKommentar(kommentarInput);
		
		try {
			kuchenrezept.setMitarbeiter((Mitarbeiter) mitarbeiterNs.getValue());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		String aus = "1";
		try {
			if (aus == "2") {
				aus = "1";
				return;
			} else {
				Kuchenrezeptverwaltung.getInstance().createKuchenrezept(kuchenrezept);
			}
			
			Kuchenrezept kuchenrezeptNeu = null;

			try {
				kuchenrezeptNeu = Kuchenrezeptverwaltung.getInstance().getKuchenrezeptByName1(nameInput);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	
			@SuppressWarnings("unchecked")
			BeanItemContainer<KuchenrezeptHasArtikel> bicArtikel = (BeanItemContainer<KuchenrezeptHasArtikel>) zutatenTable
					.getContainerDataSource();
			ausgArtikel = bicArtikel.getItemIds();
			kuchenrezeptNeu.setArtikel(ausgArtikel);
		
			if (ausgArtikel.isEmpty()) {
				Notification notification = new Notification(
						"Bitte Zutaten eintragen");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
				aus = "2";
				return;
			}
		
			try {
				Kuchenrezeptverwaltung.getInstance().saveArtikel(kuchenrezeptNeu);
			} catch (Exception e) {
				e.printStackTrace();
			}
		

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Notification notification = new Notification(
				"Rezept wurde gespeichert!");
		notification.setDelayMsec(500);
		notification.show(Page.getCurrent());
		ViewHandler.getInstance().switchView(
				KuchenrezeptAnzeigen.class);
	}
	

	// Methode zum Aendern eines Rezepts
	private void update() {
		
		// setzt Rezeptname
		kuchenrezept.setName(nameInput);
		
		// setzt Ã„nderungsdatum
		java.util.Date date = new java.util.Date();
		Date date3 = new Date(date.getTime());
		kuchenrezept.setErstellt(date3);
		
		// setzt Kommentar
		kuchenrezept.setKommentar(kommentarInput);
		
		// setzt Mitarbeiter
		try {
			kuchenrezept.setMitarbeiter((Mitarbeiter) mitarbeiterNs.getValue());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// setzt Artikel
		kuchenrezept.setArtikel(tmpZutaten);
		
		try {
			Kuchenrezeptverwaltung.getInstance().updateKuchenrezept(kuchenrezept);
			Kuchenrezeptverwaltung.getInstance().deleteZutatenZuKuchenrezept(kuchenrezept);
			Kuchenrezeptverwaltung.getInstance().saveArtikel(kuchenrezept);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Notification notification = new Notification(
				"Rezept wurde geändert!");
		notification.setDelayMsec(500);
		notification.show(Page.getCurrent());
		ViewHandler.getInstance().switchView(
				KuchenrezeptAnzeigen.class);
	}
	
	// Funktion zum Anzeigen der Notification
	private void showNotification(String text){
		Notification notification = new Notification(text);
		notification.setDelayMsec(500);
		notification.show(Page.getCurrent());
	}
		
	// Funktion zur Validierung
	private Boolean validiereEingabe(){
		if(name.getValue().isEmpty()){
			showNotification("Bitte Bezeichnung eingeben!");
			return false;
		}
		if(mitarbeiterNs.getValue()==null){
			showNotification("Bitte Bäcker wählen!");
			return false;
		}
		if (tmpZutaten != null || tmpZutaten.size() != 0) {
			for (KuchenrezeptHasArtikel kha : tmpZutaten) {
				if (kha.getMenge() >= 100000.0) {
					showNotification(IConstants.INFO_REZEPT_MENGE);
					return false;
				}
			}
		
			if (tmpZutaten == null || tmpZutaten.size() == 0) {
				showNotification(IConstants.INFO_REZEPT_ZUTATEN);
				return false;
			}
		}

		
		return true;
	}
	
}

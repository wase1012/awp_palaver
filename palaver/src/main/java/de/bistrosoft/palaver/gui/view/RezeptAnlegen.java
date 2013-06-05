package de.bistrosoft.palaver.gui.view;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ReadOnlyException;
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
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.data.RezeptDAO;
import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasZubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptartverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Zubereitungverwaltung;
import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * @author Jan Lauinger
 * 
 */
@SuppressWarnings("serial")
public class RezeptAnlegen extends VerticalLayout implements View,
		ValueChangeListener {

	// Layouts
	private HorizontalLayout box = new HorizontalLayout();
	private HorizontalLayout control = new HorizontalLayout();

	private VerticalLayout vlRezeptdetailsLinks = new VerticalLayout();
	private VerticalLayout vlRezeptdetailsRechts = new VerticalLayout();
	private HorizontalLayout hlRezeptZutaten = new HorizontalLayout();

	// Tabellen
	private Table zutatenTable;
	private FilterTable artikelTable;

	List<RezeptHasArtikel> tmpZutaten = new ArrayList<RezeptHasArtikel>();
	
	// BeanContainer
	private BeanItemContainer<Artikel> containerArtikel;
	private BeanItemContainer<RezeptHasArtikel> containerRezeptHasArtikel;

	// Ueberschriften
	@SuppressWarnings("deprecation")
	private Label ueberschrift = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Rezept anlegen</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label ueberschrift2 = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Rezept bearbeiten</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label dummyl = new Label("<div>&nbsp;&nbsp;&nbsp;</div>",
			Label.CONTENT_XHTML);

	// TextFelder
	private TextField name = new TextField("Bezeichnung");

	// TwinCol
	private TwinColSelect zubereitung = new TwinColSelect("Zubereitung");

	// ComboBoxen
	private ComboBox mitarbeiterCb = new ComboBox("Koch");
	private ComboBox rezeptartCb = new ComboBox("Rezeptart");

	// OptionGroup
	private OptionGroup rezeptartOg = new OptionGroup();

	// TextArea
	private TextArea kommentar = new TextArea("Kommentar");

	// Buttons
	private Button btSpeichern = new Button(IConstants.BUTTON_SAVE);
	private Button btVerwerfen = new Button(IConstants.BUTTON_DISCARD);
	private Button btUpdate = new Button(IConstants.BUTTON_SAVE);

	// Variablen
	private String nameInput;
	private String kommentarInput;
	private String rezeptartInput;
	private String mitarbeiterInput;
	public String valueString = new String();

	Rezept rezept;
	List<RezeptHasArtikel> ausgArtikel = new ArrayList<RezeptHasArtikel>();
	List<Zubereitung> listzubereitung = new ArrayList<Zubereitung>();
	List<RezeptHasArtikel> artikel = new ArrayList<RezeptHasArtikel>();

	// Konstruktor
	public RezeptAnlegen() {

		super();
		this.setSizeFull();
		this.setMargin(true);

		rezeptartOg.addItem("Hauptgericht");
		rezeptartOg.addItem("Beilage");

		rezeptartOg.select(2);
		rezeptartOg.setNullSelectionAllowed(false);
		rezeptartOg.setHtmlContentAllowed(true);
		rezeptartOg.setImmediate(true);

		name.setWidth("100%");
		name.setImmediate(true);
		name.setInputPrompt(nameInput);
		name.setSizeFull();

		zubereitung.setWidth("100%");
		zubereitung.setImmediate(true);

		mitarbeiterCb.setWidth("100%");
		mitarbeiterCb.setImmediate(true);
		mitarbeiterCb.setInputPrompt(mitarbeiterInput);
		mitarbeiterCb.setNullSelectionAllowed(false);

		rezeptartCb.setWidth("100%");
		rezeptartCb.setImmediate(true);
		rezeptartCb.setInputPrompt(rezeptartInput);
		rezeptartCb.setNullSelectionAllowed(false);

		kommentar.setWidth("100%");
		kommentar.setImmediate(true);

		box.setWidth("800px");
		box.setSpacing(true);

		this.addComponent(box);
		box.addComponent(vlRezeptdetailsLinks);
		box.addComponent(vlRezeptdetailsRechts);
		this.addComponent(hlRezeptZutaten);
		vlRezeptdetailsLinks.addComponent(ueberschrift);
		vlRezeptdetailsLinks.addComponent(name);
		vlRezeptdetailsLinks.addComponent(mitarbeiterCb);
		vlRezeptdetailsLinks.addComponent(rezeptartOg);
		vlRezeptdetailsLinks.addComponent(dummyl);

		vlRezeptdetailsRechts.addComponent(zubereitung);
		vlRezeptdetailsRechts.addComponent(kommentar);

		hlRezeptZutaten.setSizeFull();

		control.setSpacing(true);
		this.addComponent(control);
		this.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		btSpeichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		btVerwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		btSpeichern.setEnabled(true);

		control.addComponent(btVerwerfen);
		control.addComponent(btSpeichern);

		// ValueChangeListener
		name.addValueChangeListener(this);
		rezeptartOg.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
				rezeptartInput = valueString;
			}
		});
		mitarbeiterCb.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
				mitarbeiterInput = valueString;
			}
		});
		kommentar.addValueChangeListener(this);

		btVerwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().returnToDefault();
			}
		});

		btSpeichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if(validiereEingabe()){					
					speichern();
				}
			}
		});

		zutatenTable = new Table();
		zutatenTable.setSizeFull();
		zutatenTable.setStyleName("palaverTable");
		zutatenTable.setImmediate(true);

		artikelTable = new FilterTable();
		artikelTable.setSizeFull();
		artikelTable.setStyleName("palaverTable");

		artikelTable.setFilterBarVisible(true);
		artikelTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);

		containerRezeptHasArtikel = new BeanItemContainer<RezeptHasArtikel>(
				RezeptHasArtikel.class);
		zutatenTable.setContainerDataSource(containerRezeptHasArtikel);
		zutatenTable.setVisibleColumns(new Object[] { "artikelname", "menge",
				"einheit" });
		zutatenTable.setEditable(true);

		hlRezeptZutaten.addComponent(zutatenTable);
		hlRezeptZutaten.addComponent(artikelTable);
		
		box.setExpandRatio(vlRezeptdetailsLinks, 1);
		box.setExpandRatio(vlRezeptdetailsRechts, 1);

		hlRezeptZutaten.setExpandRatio(zutatenTable, 1);
		hlRezeptZutaten.setExpandRatio(artikelTable, 1);
		hlRezeptZutaten.setSpacing(true);

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
				RezeptHasArtikel selected = (RezeptHasArtikel) t
						.getData("itemId");
				containerRezeptHasArtikel.removeItem(selected);
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
					RezeptHasArtikel tmp=new RezeptHasArtikel(selected);
					tmpZutaten.add(tmp);
					containerRezeptHasArtikel.addItem(tmp);
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
		
		//Koch auf aktuellen User setzen
		mitarbeiterCb.select(Application.getInstance().getUser());
	}

	public void load() {

		mitarbeiterCb.removeAllItems();
		rezeptartCb.removeAllItems();
		zubereitung.removeAllItems();

		try {
			List<Mitarbeiter> mitarbeiter = Mitarbeiterverwaltung.getInstance().getAllMitarbeiter();
			for (Mitarbeiter e : mitarbeiter) {
				mitarbeiterCb.addItem(e);
			}
			
			List<Rezeptart> rezeptart = Rezeptartverwaltung.getInstance()
					.getAllRezeptart();
			for (Rezeptart e : rezeptart) {
				rezeptartCb.addItem(e.getId());
				rezeptartCb.setItemCaption(e.getId(), e.getName());
			}

			List<Zubereitung> zb = Zubereitungverwaltung.getInstance()
					.getAllZubereitung();
			for (Zubereitung z : zb) {
				zubereitung.addItem(z);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void getViewParam(ViewData data) {

		rezept = (Rezept) ((ViewDataObject<?>) data).getData();
		if(rezept.getArtikel()!=null){
			tmpZutaten=rezept.getArtikel();
		}
		else{
			tmpZutaten=new ArrayList<RezeptHasArtikel>();
		}
		System.out.println(rezept.getMitarbeiter());
		control.replaceComponent(btSpeichern, btUpdate);
		vlRezeptdetailsLinks.replaceComponent(ueberschrift, ueberschrift2);

		btUpdate.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		btUpdate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				update();
			}
		});

		/**
		 * Daten in Felder schreiben
		 */
		System.out.println("name zu rezept: " + name);
		try {
			name.setValue(RezeptDAO.getInstance().getRezeptById(rezept.getId()).getName().toString());
			if (rezept.getRezeptart().getName().equals("Hauptgericht")) {
				rezeptartOg.select("Hauptgericht");
			} else {
				rezeptartOg.select("Beilage");
			}
		} catch (Exception e3) {
			e3.printStackTrace();
		}


		for (int i = 0; i < rezept.getZubereitung().size(); i++) {
			zubereitung.select(rezept.getZubereitung().get(i));
			System.out.println("Micha war hier !  "+ rezept.getZubereitung().get(i).getName());
		}
		
		try {
			List<Mitarbeiter> mitarbeiter = Mitarbeiterverwaltung.getInstance().getAllMitarbeiter();
			System.out.println(mitarbeiter.get(0));
			System.out.println(rezept.getMitarbeiter());
			System.out.println(rezept.getMitarbeiter().equals(mitarbeiter.get(0)));
			mitarbeiterCb.select(rezept.getMitarbeiter());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		BeanItemContainer<RezeptHasArtikel> artikelcontainer;
		List<RezeptHasArtikel> list = new ArrayList<RezeptHasArtikel>();

		try {
			list = Rezeptverwaltung.getInstance().getAllArtikelByRezeptId1(
					rezept.getId());
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		try {
			artikelcontainer = new BeanItemContainer<RezeptHasArtikel>(
					RezeptHasArtikel.class);

			zutatenTable = null;
			zutatenTable = new Table();
			zutatenTable.setSizeFull();
			zutatenTable.setStyleName("palaverTable2");
			zutatenTable.setImmediate(true);

			zutatenTable.setContainerDataSource(artikelcontainer);
			zutatenTable.setVisibleColumns(new Object[] { "artikelname",
					"menge", "einheit" });
			tmpZutaten=list;
			for (RezeptHasArtikel rha : list) {
				containerArtikel.removeItem(rha);
				System.out.println(rha.getArtikelname());
				containerRezeptHasArtikel.addItem(rha);
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		}
		rezept.setArtikel(list);
		
		try {
			kommentar.setValue(RezeptDAO.getInstance()
					.getRezeptById(rezept.getId()).getKommentar().toString());
		} catch (ReadOnlyException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		nameInput = name.getValue();
		kommentarInput = kommentar.getValue();
	}

	// Funktion zum Speichern eines Rezeptes
	public void rezeptSpeichern() {
		Rezept rezept = new Rezept();

		rezept.setName(nameInput);

		java.util.Date date = new java.util.Date();
		Date date2 = new Date(date.getTime());

		try {
			rezept.setRezeptart(Rezeptartverwaltung.getInstance()
					.getRezeptartByNameB(rezeptartInput));
		} catch (Exception e) {
			e.printStackTrace();
		}
		rezept.setErstellt(date2);
		rezept.setKommentar(kommentarInput);
		try {
			rezept.setMitarbeiter((Mitarbeiter) mitarbeiterCb.getValue());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		String aus = "1";
		try {
			if (aus == "2") {
				aus = "1";
				return;
			} else {
				Rezeptverwaltung.getInstance().createRezept(rezept);
			}

			Rezept rezeptNeu = null;

			try {
				rezeptNeu = Rezeptverwaltung.getInstance().getRezeptByName1(nameInput);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			// Liste der Zubereitungen
			if (zubereitung.getValue().toString() != "[]") {
				List<String> ZubereitungId = Arrays.asList(zubereitung.getValue().toString().substring(1, zubereitung.getValue().toString().length() - 1).split("\\s*,\\s*"));

				List<RezeptHasZubereitung> zubereitunglist = new ArrayList<RezeptHasZubereitung>();
				for (String sId : ZubereitungId) {

					Zubereitung zubereitung1 = new Zubereitung();
					try {

						zubereitung1 = Zubereitungverwaltung.getInstance()
								.getZubereitungByName(sId);
						
						RezeptHasZubereitung a = new RezeptHasZubereitung(
								zubereitung1, rezeptNeu);
						zubereitunglist.add(a);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				for (RezeptHasZubereitung i : zubereitunglist) {
					try {
						Rezeptverwaltung.getInstance().ZubereitungAdd(i);
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			}

			System.out.println("Bei Speichern: "
					+ zutatenTable.getContainerDataSource().size());
			@SuppressWarnings("unchecked")
			BeanItemContainer<RezeptHasArtikel> bicArtikel = (BeanItemContainer<RezeptHasArtikel>) zutatenTable
					.getContainerDataSource();
			System.out.println("ausgArtikel vor bicArtikel: " + ausgArtikel);
			ausgArtikel = bicArtikel.getItemIds();
			System.out.println("ausgArtikel: " + ausgArtikel);
			System.out.println("rez: " + rezeptNeu);
			rezeptNeu.setArtikel(ausgArtikel);

			if (ausgArtikel.isEmpty()) {
				Notification notification = new Notification(
						"Bitte Zutaten eintragen");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
				aus = "2";
				return;
			}

			try {
				System.out.println("rezeptAnlegen speichern id: "
						+ rezept.getId());
				Rezeptverwaltung.getInstance().saveArtikel(rezeptNeu);
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
		ViewHandler.getInstance().switchView(RezeptAnzeigenTabelle.class);
	}

	private void speichern() {
		rezeptSpeichern();
	}

//	private void rezeptAlsHauptgerichtSpeichern() {
//		if (ausgArtikel.isEmpty()) {
//			return;
//		}
//		Menue menue = new Menue();
//
//		menue.setName(nameInput);
//		try {
//			menue.setKoch(MitarbeiterDAO.getInstance().getMitarbeiterById(
//					Long.parseLong(mitarbeiterInput.toString())));
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//
//		try {
//			Menueverwaltung.getInstance().createRezeptAlsMenue(menue);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void rezeptAlsMenuSpeichern() {
//		if (ausgArtikel.isEmpty()) {
//			return;
//		}
//		MenueHasRezept mhr = new MenueHasRezept();
//		try {
//			mhr.setMenue(MenueDAO.getInstance().getMenueIdByName(nameInput));
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		try {
//			mhr.setRezept(RezeptDAO.getInstance().getRezeptByName1(nameInput));
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		try {
//			Menueverwaltung.getInstance().RezepteAdd(mhr);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		mhr.setHauptgericht(true);
//		Notification notification = new Notification(
//				"Rezept wurde als Menü gespeichert!");
//		notification.setDelayMsec(500);
//		notification.show(Page.getCurrent());
//	}

	// Methode zum �ndern eines Rezepts
	private void update() {
		System.out.println("!--- Starte Update ---!");
//		rezept=new Rezept();
		String rezeptnameAlt = rezept.getName();
		System.out.println("Rezept, das geändernt werden soll: " + rezeptnameAlt);

		// setzt Rezeptname
		rezept.setName(nameInput);
		System.out.println("Rezeptname: " + nameInput);

		// setzt Änderungsdatum
		java.util.Date date = new java.util.Date();
		Date date2 = new Date(date.getTime());
		rezept.setErstellt(date2);

		// setzt Kommentar
		rezept.setKommentar(kommentarInput);

		// setzt Rezeptart
		try {
			rezept.setRezeptart(Rezeptartverwaltung.getInstance()
					.getRezeptartByNameB(rezeptartInput));
			System.out.println("Rezeptart: " + rezeptnameAlt);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// setzt Mitarbeiter
		try {
			rezept.setMitarbeiter((Mitarbeiter) mitarbeiterCb.getValue());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//Setze Artikel
		rezept.setArtikel(tmpZutaten);
		
		try {
			Rezeptverwaltung.getInstance().updateRezept(rezept);
			Rezeptverwaltung.getInstance().deleteZutatenZuRezept(rezept);
			Rezeptverwaltung.getInstance().saveArtikel(rezept);
			Rezeptverwaltung.getInstance().deleteZubereitungZuRezept(rezept);
			//TODO: Methode wie saveArtikel für Zubereitungen
//			Rezeptverwaltung.getInstance().saveZubereitungen(rezept);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Liste der Zubereitungen
		if (zubereitung.getValue().toString() != "[]") {
			List<String> ZubereitungId = Arrays.asList(zubereitung.getValue().toString().substring(1, zubereitung.getValue().toString().length() - 1).split("\\s*,\\s*"));
			
			List<RezeptHasZubereitung> zubereitunglist = new ArrayList<RezeptHasZubereitung>();
			for (String sId : ZubereitungId) {
				Zubereitung zubereitung1 = new Zubereitung();
				try {
					
					zubereitung1 = Zubereitungverwaltung.getInstance()
							.getZubereitungByName(sId);
					System.out.println("!§!§"+zubereitung1);
					//
					RezeptHasZubereitung a = new RezeptHasZubereitung(
							zubereitung1, rezept);
					zubereitunglist.add(a);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			for (RezeptHasZubereitung i : zubereitunglist) {
				try {
					Rezeptverwaltung.getInstance().ZubereitungAdd(i);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		}
	}
	private void showNotification(String text){
		Notification notification = new Notification(text);
		notification.setDelayMsec(500);
		notification.show(Page.getCurrent());
	}
		
	
	private Boolean validiereEingabe(){
		if(name.getValue().isEmpty()){
			showNotification("Bitte Namen eingeben!");
			return false;
		}
		if(mitarbeiterCb.getValue()==null){
			showNotification("Bitte Koch wählen!");
			return false;
		}
		if(rezeptartOg.getValue()==null){
			showNotification("Bitte Rezeptart wählen!");
			return false;
		}
		if(tmpZutaten==null || tmpZutaten.size()==0){
			showNotification("Bitte Zutaten wählen!");
			return false;
		}
		return true;
	}
}

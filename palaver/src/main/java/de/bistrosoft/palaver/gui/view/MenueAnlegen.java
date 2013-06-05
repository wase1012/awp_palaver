package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tepi.filtertable.FilterTable;


import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.data.FussnoteDAO;
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.data.MenueDAO;
import de.bistrosoft.palaver.data.MenueartDAO;
import de.hska.awp.palaver2.data.MitarbeiterDAO;
import de.bistrosoft.palaver.data.RezeptDAO;
import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasFussnote;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasRezept;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueart;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueartverwaltung;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueverwaltung;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptartverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;



@SuppressWarnings("serial")
public class MenueAnlegen extends VerticalLayout implements View,
               ValueChangeListener {

	
	List<Rezept> listrezept = new ArrayList<Rezept>();
	List<Fussnote> listfussnote = new ArrayList<Fussnote>();
	Menue menue = new Menue();
//	Menue menue2;
//	Menue menue3;
	Integer z = 0;
	private String merker = "leer";
	private String merkerh = "leer";
	private String merker1 = "leer";
	private String merker2 = "leer";
	private HorizontalLayout haupt = new HorizontalLayout();
	private HorizontalLayout unten = new HorizontalLayout();
	private VerticalLayout box = new VerticalLayout();
	private VerticalLayout box2 = new VerticalLayout();
	private HorizontalLayout horizont1 = new HorizontalLayout();
	private HorizontalLayout horizont2 = new HorizontalLayout();
	private HorizontalLayout horizont3 = new HorizontalLayout();
	private HorizontalLayout horizont4 = new HorizontalLayout();
	private VerticalLayout links = new VerticalLayout();
	private VerticalLayout mitte = new VerticalLayout();
	private VerticalLayout rechts = new VerticalLayout();
	private VerticalLayout links2 = new VerticalLayout();
	private VerticalLayout mitte2 = new VerticalLayout();
	private VerticalLayout rechts2 = new VerticalLayout();
	private VerticalLayout links3 = new VerticalLayout();
	private VerticalLayout rezeptvl = new VerticalLayout();
	private VerticalLayout mitte3 = new VerticalLayout();
	private VerticalLayout rechts3 = new VerticalLayout();
	private VerticalLayout blank2 = new VerticalLayout();
	private VerticalLayout b1 = new VerticalLayout();
	private VerticalLayout b2 = new VerticalLayout();
	private VerticalLayout b3 = new VerticalLayout();
	private HorizontalLayout control = new HorizontalLayout();
	private HorizontalLayout cbs = new HorizontalLayout();
	
	private VerticalLayout box3 = new VerticalLayout();
	private int zahl = 0;
	
	private Button update = new Button("Ã„ndern");
	
	private Label ueberschrift = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Menue anlegen</font><b></pre>",
			ContentMode.HTML);
	private Label ueberschrift2 = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Menue ï¿½ndern</font><b></pre>",
			ContentMode.HTML);

	private Label dummy = new Label(
			"<div>&nbsp;&nbsp;&nbsp;</div>",
			ContentMode.HTML);
	private Label dummy1 = new Label(
			"<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>",
			ContentMode.HTML);
	private Label dummy2 = new Label(
			"<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>",
			ContentMode.HTML);
	private Label dummy3 = new Label(
			"<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>",
			ContentMode.HTML);
	private Label dummy4 = new Label(
			"<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>",
			ContentMode.HTML);
	private Label d1 = new Label(
			"<div>&nbsp;&nbsp;&nbsp;</div>",
			ContentMode.HTML);
	private Label d2 = new Label(
			"<div>&nbsp;&nbsp;&nbsp;</div>",
			ContentMode.HTML);
	private Label d3 = new Label(
			"<div>&nbsp;&nbsp;&nbsp;</div>",
			ContentMode.HTML);
	private Label d4 = new Label(
			"<div>&nbsp;&nbsp;&nbsp;</div>",
			ContentMode.HTML);
	private Label r = new Label(
			"<div>&nbsp;&nbsp;&nbsp;</div>",
			ContentMode.HTML);
    private TextField menuename = new TextField("Menuename");
    private ComboBox ersteller = new ComboBox("Menueersteller");
	public static  TextField hauptgericht = new TextField("Hauptgericht");
	public static TextField beilage1 = new TextField("Beilage 1");
	public static TextField beilage2 = new TextField("Beilage 2");
    
	private TwinColSelect fussnoten = new TwinColSelect("Fussnoten");
	
	
	private ComboBox menueartCb = new ComboBox("Menueart");
	private ComboBox geschmackCb = new ComboBox("Geschmack");

	private CheckBox favorit = new CheckBox("Favorit");
	private CheckBox aufwand = new CheckBox("Aufwand");
	

	private Button speichern = new Button("Speichern");
	private Button verwerfen = new Button("Verwerfen");
	private Button neuesRezept = new Button("neues Rezeptanlegen");
	private Button hZufuegen = new Button();
	private Button b1Zufuegen = new Button();
	private Button b2Zufuegen = new Button();
	private Button hEntfernen = new Button();
	private Button b1Entfernen = new Button();
	private Button b2Entfernen = new Button();
	
	private String hauptgerichtInput;
	private String beilage1Input;
	private String beilage2Input;
	private String menuenameInput;
	private String erstellerInput;
	public String valueString = new String();
	
	private String althg;
	private String altb1;
	private String altb2;
	private MenueHasFussnote mhf;
	
	private List<MenueHasFussnote> fusstontenlist = new ArrayList<MenueHasFussnote>();

	private Button neuRezept = new Button("neues Rezept anlegen");

	private Button btAdd = new Button("Add");

	
	private String geschmackInput;
	private String menueartInput;
	

	public static String beilageid;
	public static String beilage2id;
	public static String hauptgerichtid;
	
	public MenueAnlegen() {
		super();
		this.setSizeFull();
		this.setMargin(true);
		hauptgericht.setWidth("100%");
		beilage1.setWidth("100%");
		beilage2.setWidth("100%");
		hauptgericht.setEnabled(false);
		beilage1.setEnabled(false);
		beilage2.setEnabled(false);
		fussnoten.setWidth("100%");
		menuename.setWidth("90%");
		menuename.setMaxLength(200);
        b1Zufuegen.setWidth("150");
        b2Zufuegen.setWidth("150");
        hZufuegen.setWidth("150");
		
       
		box.setWidth("300px");
		box.setSpacing(true);

		this.addComponent(haupt);
		this.setComponentAlignment(haupt, Alignment.MIDDLE_CENTER);
		this.addComponent(unten);
		this.setComponentAlignment(unten, Alignment.MIDDLE_CENTER);
		haupt.addComponent(box);
		box.addComponent(ueberschrift);
		box.addComponent(menuename);
		box.addComponent(ersteller);
		box.addComponent(menueartCb);
		box.addComponent(geschmackCb);
		box.addComponent(cbs);
		box.setComponentAlignment(cbs, Alignment.MIDDLE_LEFT);
		cbs.addComponent(favorit);
		cbs.addComponent(d4);
		cbs.addComponent(aufwand);
		
		haupt.addComponent(box2);
		box2.addComponent(horizont1);
		box2.addComponent(horizont2);
		box2.addComponent(horizont3);
		box2.addComponent(horizont4);
		box2.setComponentAlignment(horizont1, Alignment.MIDDLE_CENTER);
		box2.setComponentAlignment(horizont2, Alignment.MIDDLE_CENTER);
		box2.setComponentAlignment(horizont3, Alignment.MIDDLE_CENTER);
		box2.setComponentAlignment(horizont4, Alignment.MIDDLE_LEFT);
		horizont1.addComponent(links);
		horizont1.addComponent(b1);
		horizont1.addComponent(mitte);
		horizont1.addComponent(rechts);
		horizont1.setComponentAlignment(links, Alignment.MIDDLE_CENTER);
		horizont1.setComponentAlignment(mitte, Alignment.MIDDLE_CENTER);
		horizont1.setComponentAlignment(rechts, Alignment.MIDDLE_CENTER);
		horizont2.addComponent(links2);
		horizont2.addComponent(b2);
		horizont2.addComponent(mitte2);
		horizont2.addComponent(blank2);
		horizont2.addComponent(rechts2);
		horizont2.setComponentAlignment(links2, Alignment.MIDDLE_CENTER);
		horizont2.setComponentAlignment(mitte2, Alignment.MIDDLE_CENTER);
		horizont2.setComponentAlignment(rechts2, Alignment.MIDDLE_CENTER);
		horizont3.addComponent(links3);
		horizont3.addComponent(b3);
		horizont3.addComponent(mitte3);
		horizont3.addComponent(rechts3);
		horizont3.setComponentAlignment(links3, Alignment.MIDDLE_CENTER);
		horizont3.setComponentAlignment(mitte3, Alignment.MIDDLE_CENTER);
		horizont3.setComponentAlignment(rechts3, Alignment.MIDDLE_CENTER);
		links.addComponent(hauptgericht);		
     	links2.addComponent(beilage1);		
		links3.addComponent(beilage2);
		horizont4.addComponent(rezeptvl);
		rezeptvl.addComponent(r);
		rezeptvl.addComponent(neuesRezept);
		mitte.addComponent(dummy1);
		mitte2.addComponent(dummy2);
		mitte3.addComponent(dummy3);
		mitte.addComponent(hZufuegen);
		mitte2.addComponent(b1Zufuegen);
		blank2.addComponent(dummy);
		mitte3.addComponent(b2Zufuegen);
		mitte.addComponent(hEntfernen);
		mitte2.addComponent(b1Entfernen);		
		mitte3.addComponent(b2Entfernen);
		hZufuegen.setPrimaryStyleName(Reindeer.BUTTON_SMALL);
		hZufuegen.setIcon(new ThemeResource("img/add.ico"));
		b1Zufuegen.setPrimaryStyleName(Reindeer.BUTTON_SMALL);
		b1Zufuegen.setIcon(new ThemeResource("img/add.ico"));
		b2Zufuegen.setPrimaryStyleName(Reindeer.BUTTON_SMALL);
		b2Zufuegen.setIcon(new ThemeResource("img/add.ico"));
		hEntfernen.setPrimaryStyleName(Reindeer.BUTTON_SMALL);
		hEntfernen.setIcon(new ThemeResource("img/Delete.ico"));
		b1Entfernen.setPrimaryStyleName(Reindeer.BUTTON_SMALL);
		b1Entfernen.setIcon(new ThemeResource("img/Delete.ico"));
		b2Entfernen.setPrimaryStyleName(Reindeer.BUTTON_SMALL);
		b2Entfernen.setIcon(new ThemeResource("img/Delete.ico"));
		haupt.addComponent(box3);
		box3.addComponent(fussnoten);
		b1.addComponent(d1);
		b2.addComponent(d2);
		b3.addComponent(d3);
		

		

		
		
		control.setSpacing(true);
		unten.addComponent(control);
		unten.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);

		hauptgericht.setImmediate(true);
		hauptgericht.setInputPrompt(hauptgerichtInput);
	
		geschmackCb.setNullSelectionAllowed(false);
		menueartCb.setNullSelectionAllowed(false);
		menuename.setImmediate(true);
		menuename.setInputPrompt(menuenameInput);
		ersteller.setImmediate(true);
		ersteller.setInputPrompt(erstellerInput);
		ersteller.setNullSelectionAllowed(false);
		beilage1.setImmediate(true);
		beilage1.setInputPrompt(beilage1Input);	
		

		beilage2.setImmediate(true);
		beilage2.setInputPrompt(beilage2Input);
	

		fussnoten.setImmediate(true);
		fussnoten.setWidth("400");
	
		
		load();

		control.addComponent(verwerfen);
		control.addComponent(speichern);
		speichern.setIcon(new ThemeResource("img/save.ico"));
		verwerfen.setIcon(new ThemeResource("img/cross.ico"));
		
		
		
		
//Value Changelistener fuer das Hauptgericht, sobald sich das feld veraendert wird der merkerh auf x gesetzt
		// es dient dazu den Inupt des Feldes zu speichern, das X dient fuer das Update
		hauptgericht.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());
               merkerh = "x";
				hauptgerichtInput = valueString;
				
			}
		});
		
		//Value Changelistener fuer die Beilage1, sobald sich das feld veraendert wird der merker1 auf x gesetzt
				// es dient dazu den Inupt des Feldes zu speichern, das X dient fuer das Update
		beilage1.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());
				 merker1 = "x";
				beilage1Input = valueString;
				
			}
		});
		
		//Value Changelistener fuer die Beilage2, sobald sich das feld veraendert wird der merker2 auf x gesetzt
		// es dient dazu den Inupt des Feldes zu speichern, das X dient fuer das Update
		beilage2.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());
				 merker2 = "x";
				beilage2Input = valueString;
			}
		});
		
		//Value Changelistener fuer den Ersteller, also den Koch, der Inputstring wird zum Speichern benoetigt
		ersteller.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				erstellerInput = valueString;
			}
		});
		
		//Value Changelistener fuer den Menuenamen, der Inputstring wird zum Speichern benoetigt
		menuename.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				menuenameInput = valueString;
			}
		});
		
		//Value Changelistener fuer die Fussnoten, der Inputstring wird zum Speichern benoetigt
		fussnoten.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String
						.valueOf(event.getProperty().getValue());
           merker = "x";
           
			}
		});	
		
		//Value Changelistener fuer den Geschmack, der Inputstring wird zum Speichern benoetigt
		geschmackCb.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
				geschmackInput = valueString;
			}
		});
		
		//Value Changelistener fuer die Menueart, der Inputstring wird zum Speichern benoetigt
		menueartCb.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
				menueartInput = valueString;
			}
		});
		
		//Verwerfen laedt die Seite neu und verwirft somit alle vorigen Eingaben
		verwerfen.addClickListener(new ClickListener(){
			@Override
			public void buttonClick(ClickEvent event)
			{
			ViewHandler.getInstance().switchView(MenueAnlegen.class);
			}
		});
		
		//wenn auf den Button neuesRezept geklcikt wird leitet dieser Button einen zur Rezeptanlegenseite
		neuesRezept.addClickListener(new ClickListener(){
			@Override
			public void buttonClick(ClickEvent event)
			{
			ViewHandler.getInstance().switchView(RezeptAnlegen.class);
			}
		});
		
		//hZufuehgen oeffet ein neues Fenster in welchem man das hauptgericht fuer das Menue auswaehlen kann
		hZufuegen.addClickListener(new ClickListener(){
			@Override
			public void buttonClick(ClickEvent event)
			{   merkerh = "x";
				zahl = 0;
				WinSelectRezepte window = new WinSelectRezepte(zahl);
						  UI.getCurrent().addWindow(window);
						  window.setModal(true);
						  window.setWidth("80%");
						  window.setHeight("80%");
			}
		});
		
		//b1Zufuehgen oeffet ein neues Fenster in welchem man die erste Beilage fuer das Menue auswaehlen kann
		b1Zufuegen.addClickListener(new ClickListener(){
			@Override
			public void buttonClick(ClickEvent event)
			{   merker1 = "x";
				zahl = 1;
				WinSelectRezepte window = new WinSelectRezepte(zahl);
						  UI.getCurrent().addWindow(window);
						  window.setModal(true);
						  window.setWidth("50%");
						  window.setHeight("50%");
             }
		});
		
		//b2Zufuehgen oeffet ein neues Fenster in welchem man die zweite Beilage fuer das Menue auswaehlen kann
		b2Zufuegen.addClickListener(new ClickListener(){
			@Override
			public void buttonClick(ClickEvent event)
			{   merker2 = "x";
				zahl = 2;
				WinSelectRezepte window = new WinSelectRezepte(zahl);
						  UI.getCurrent().addWindow(window);
						  window.setModal(true);
						  window.setWidth("50%");
						  window.setHeight("50%");
            }
		});
		
        //der Button speichern leitet zur Funktion zum Pruefen der Eingaben weiter
		speichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {				
				pruefen(1);				
					}
					});
		
		
		hEntfernen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {	
				merkerh = "leer";
					hauptgericht.setValue("");		
					}
					});
		
		b1Entfernen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {	
				merker1 = "leer";
					beilage1.setValue("");		
					}
					});
		
		b2Entfernen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				merker2 = "leer";
					beilage2.setValue("");		
					}
					});
	      }

	
      

    //in der Prozedur load wird die ganze Seite geladen, alle felder und Werte werden geleert
	//auch werden die inhalte fuer den Geschmack, die Koeche, die Menueart udn die Fussnoten geladen
	public void load() {
		System.out.println("load");
		//Variablen leeren WICHTIG
		beilage1.setValue("");
		beilage2.setValue("");
		hauptgericht.setValue("");
		beilageid = null;
		beilage2id = null;
		hauptgerichtid = null;
		
		//Inhalte laden
		try {
			
			
			List<Mitarbeiter> mitarbeiter = Mitarbeiterverwaltung.getInstance()
					.getAllMitarbeiter();
			for (Mitarbeiter e : mitarbeiter) {
				ersteller.addItem(e);
				

			}
			
				List<Geschmack> geschmack = Geschmackverwaltung.getInstance()
						.getAllGeschmackAktiv();
				for (Geschmack e : geschmack) {
					geschmackCb.addItem(e);
					
                    
				}
				
				List<Menueart> menueart = Menueartverwaltung.getInstance()
						.getAllMenueart();
				for (Menueart e : menueart) {
					menueartCb.addItem(e);
					
				}		
			
			List<Fussnote> fussnote = Fussnotenverwaltung.getInstance().getAllFussnote();
			for (Fussnote e : fussnote) {
				fussnoten.addItem(e.getId());
				fussnoten.setItemCaption(e.getId(), e.getName());
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Diese Funktion wird angesprochen, wenn man ein Menue ansehen oder ï¿½ndern moechte, es wir ein Menue
	// uebergeben welches dann angeziegt werden soll
	@Override
	public void getViewParam(ViewData data) {
		
		//leere menues erstellen
//		menue2 = new Menue();
//		menue3 = new Menue();
		
		//Daten des uebergebenen Menues speichern
		menue = (Menue) ((ViewDataObject<?>)data).getData();
		//Vollständiges menü laden
		try {
			menue = MenueDAO.getInstance().getAllItemsForUpdate(menue.getId());
		} catch (ConnectException e) {
			 
			e.printStackTrace();
		} catch (DAOException e) {
			 
			e.printStackTrace();
		} catch (SQLException e) {
			 
			e.printStackTrace();
		}
		System.out.println("hier!!!!!!!!!!!");
		System.out.println(menue);
		//den Button speichern durch den Button aendern ersetzen
		control.replaceComponent(speichern, update);
		box.replaceComponent(ueberschrift, ueberschrift2);		
		update.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		
		beilage1.setValue("");
		beilage2.setValue("");
		hauptgericht.setValue("");
		
		//Clicklistener fuer den Update Button, die Funktion pruefen wird aufgerufen
		update.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {		
				
				pruefen(2);		
			}
		});
	
		//in diesen prozeduren werden die daten des Menues in die Felder uebertragen
		
		fussnotenEinlesen(menue);
		normalEinlesen(menue);
		beilagenEinlesen(menue);

		//die Merker werden geleert, wichtig dies darf erst nach den 
		//Prozeduren passieren sonst sind sie automatisch auf x gesetzt
		 merker = "leer";
		 merkerh = "leer";
		 merker1 = "leer";
		 merker2 = "leer";
		
		
		
	}

	//in Menuetabelle speichern werden alle Formulardaten, welche in die tabelle
	//Menue gespeichert werden sollen, der Rueckgabewert ist ein Menue
private Menue menuetabelleSpeichern() {

//	Menue menue = new Menue();
	MenueHasFussnote fussnote = new MenueHasFussnote();

	
	menue.setName(menuenameInput);
	menue.setAufwand(aufwand.getValue());
	menue.setFavorit(favorit.getValue());

	try {
		menue.setKoch((Mitarbeiter) ersteller.getValue());
	} catch (Exception e1) {
		e1.printStackTrace();
	}

	try {
		menue.setGeschmack((Geschmack) geschmackCb.getValue());
	} catch (Exception e1) {
		e1.printStackTrace();
	}
	
	try {
		menue.setMenueart((Menueart) menueartCb.getValue());
	} catch (Exception e1) {
		e1.printStackTrace();
	}
		
	return menue;
	
}

//Auslesen der Fussnoten asu dem Twincol
private void fussnotenSetzen(Menue menue1) {
	
	//die Fussnoten werden aus dem Valuestring ausgelsen udn in eine Liste geschrieben
	if(fussnoten.getValue().toString() != "[]"){
		List<String> FussnoteId = Arrays.asList(valueString.substring(1,
				valueString.length() - 1).split("\\s*,\\s*"));
		
		
		
		BeanItemContainer<MenueHasFussnote> fussnotencontainer;
		List<MenueHasFussnote> fussnotelist = new ArrayList<MenueHasFussnote>();

		//alle Eintraege der FussnoteId werden durchlaufen
		for (String sId : FussnoteId) {
			
			//id der Fussnote wird geuscht
			Long id = null;
			try {
				id = Long.parseLong(sId.trim());

			} catch (NumberFormatException nfe) {

			}

			//ein Objekt von Typ Fussnote
			Fussnote fussnote1 = null;

			//ueber getFussnoteById wird das Objekt zur Id heruasgesucht
			//und in einem MenueHasFussnoteobjekt zusammen mit dem ertsellten Menue gespeichert
			try {			
				fussnote1 = Fussnotenverwaltung.getInstance()
						.getFussnoteById(id);					
				MenueHasFussnote a = new MenueHasFussnote(fussnote1.getId(),
						menue1);
				//stehen alle Fussnoten die zu einem Menue gehoeren darin(mit Menue)
				fussnotelist.add(a);
			} catch (Exception e) {
				 
				e.printStackTrace();
			}
		}
		
		//alle Eintraege  der Liste werden durchlaufen und in die DB gespeichert
		for (MenueHasFussnote i :fussnotelist) {	
			try{
			Menueverwaltung.getInstance().FussnoteAdd(i);
			} catch (Exception e) {
				 
				e.printStackTrace();
			}
		}
	}	
}
  

  //in dieser Prozedur wird das Hauptgericht zu einem Menue gespeichert
  private void hauptgerichtSetzen(Menue menue1) {
	if (hauptgericht.getValue() != null) {
		Rezept rezept = new Rezept();
		try {
			rezept = RezeptDAO.getInstance().getRezept1(Long.parseLong(hauptgerichtid.toString()));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		MenueHasRezept mhr = new MenueHasRezept(menue1, rezept, true);
		try{
			Menueverwaltung.getInstance().RezepteAdd(mhr);
			} catch (Exception e) {
				
				e.printStackTrace();
			}		
		}
		else {
		}	
  }

  //in der Prozedur wird die erste Beilage gespeichert
private void beilage1Setzen(Menue menue1) {
	if (beilageid != null) {
		Rezept rezept1 = new Rezept();
						try {
							rezept1 = RezeptDAO.getInstance().getRezept1(Long.parseLong(beilageid.toString()));
						} catch (Exception e) {
							
							e.printStackTrace();
						}
						MenueHasRezept mhr1 = new MenueHasRezept(menue1, rezept1, false);
						try{
							Menueverwaltung.getInstance().RezepteAdd(mhr1);
							} catch (Exception e) {
							
								e.printStackTrace();
							}
						}
						 else {
						 }
}

//in der Prozedur wird die zweite Beilage gespeichert
private void beilage2Setzen(Menue menue1) {
	if (beilage2id != null) {	
		Rezept rezept2 = new Rezept();						
						try {
							rezept2 = RezeptDAO.getInstance().getRezept1(Long.parseLong(beilage2id.toString()));
						} catch (Exception e) {
							 
							e.printStackTrace();
						}
						MenueHasRezept mhr2 = new MenueHasRezept(menue1, rezept2, false);
						try{
							Menueverwaltung.getInstance().RezepteAdd(mhr2);
							} catch (Exception e) {
								 
								e.printStackTrace();
							}
						}
						else {
						}
}

// in der Prozedur werden die Fussnoten zu einem Menue eingelesen
private void fussnotenEinlesen(Menue menue2) {
	try {
		listfussnote = FussnoteDAO.getInstance().getFussnoteByMenue(menue2.getId());
	} catch (Exception e2) {
		 
		e2.printStackTrace();
	}
	//pruefen ob es uerberhaupt Fussnoten gibt
	if(listfussnote != null) {
		//wenn aj Fussnoten setzen
		for ( Fussnote fn: listfussnote) {			
			fussnoten.select(fn.getId());
		}
			}
	//ansonsten nichts machen
	else {		
	}
}

//in dieser Prozedur werden die normalen Items des Meneus eingelesen
private void normalEinlesen(Menue menue) {	
	System.out.println("einlesen");
	favorit.setValue(menue.getFavorit());
	aufwand.setValue(menue.getAufwand());	
	try {
//		System.out.println(MitarbeiterDAO.getInstance().getMitarbeiterByMenue(menue2.getId()).getId());
	} catch (Exception e1) {
		 
		e1.printStackTrace();
	}
	menuename.setValue(menue.getName());
	try {
		System.out.println(menue.getKoch());
		ersteller.setValue(menue.getKoch());
	} catch (Exception e) {
		 
		e.printStackTrace();
	}
	
	try {
		System.out.println(menue.getGeschmack());
		geschmackCb.setValue(menue.getGeschmack());
	} catch (Exception e) {
		 
		e.printStackTrace();
	}
	
	try {
		menueartCb.setValue(menue.getMenueart());
	} catch (Exception e) {
		 
		e.printStackTrace();
	}
	
	try {
		hauptgericht.setValue(MenueDAO.getInstance().getHauptgerichtMenue(menue.getId()).getName());
		althg = MenueDAO.getInstance().getHauptgerichtMenue(menue.getId()).getId().toString();
	} catch (Exception e) {
		 
		e.printStackTrace();
	}
	
	
}

//in der Funktion pruefen wird abgefragt ob die wichtigen Attribute fuer das menue gesetzt sind
//wenn ja wird zu speichern oder aendern navigiert
private void pruefen(int i) {
	if (menuename.getValue() != null && menuename.getValue() != "" ) {
		
		if (ersteller.getValue() != null && ersteller.getValue() != "" ) {
	
			if ( hauptgericht.getValue() != null && hauptgericht.getValue() != "" ) {
				
				if ( geschmackCb.getValue() != null && geschmackCb.getValue() != "" ) {
					
					if ( menueartCb.getValue() != null && menueartCb.getValue() != "" ) {
						if (i == 1) {
							speichern();
						}
						else{
							aendern();
						}
					} else {
						Notification notification = new Notification("Bitte geben Sie die Menueart ein");
						notification.setDelayMsec(500);
						notification.show(Page.getCurrent());
					}
				} else {
					Notification notification = new Notification("Bitte geben Sie den Geschmack ein");
					notification.setDelayMsec(500);
					notification.show(Page.getCurrent());
				}
			} 	 
			else {
				
				Notification notification = new Notification("Bitte geben Sie ein Hauptgericht ein");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
			}
			}
			else{
				Notification notification = new Notification("Bitte geben Sie den MenÃ¼ersteller an einen Namen");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
			}
		}
		else{ 
			Notification notification = new Notification("Bitte geben sie dem Menue einen Namen");
			notification.setDelayMsec(500);
			notification.show(Page.getCurrent());
			}
}

//Beilagen zu einem Menue einlesen
private void beilagenEinlesen(Menue menue2) {
	//Beilagen in eine Liste schreiben
try {
	listrezept = MenueDAO.getInstance().getBeilagenByMenue(menue2.getId());
} catch (Exception e) {
	 
	e.printStackTrace();
}

//pruefen ob die Liste leer ist
if(listrezept != null) {
	
z = listrezept.size();

//wenn die Liste keine Eintraege hat wird ncihts gemacht
if(z < 1){
}

else {
	Integer j = 0;
	//wenn 2 Eintraege vorhanden sind werden beide eingetragen
	if(z == 2){
		
		//die variablen welche mit alt beginnen dienen dazu fetszustellen ob sich die Beilage aendert
		for (Rezept x : listrezept) {
			
			if(j == 0) {
				beilage1.setValue(x.getName());
				altb1 = x.getId().toString();
				j = j + 1;
			}
			else{
				beilage2.setValue(x.getName());
				altb2 = x.getId().toString();
			}
		}
			
	}
	//wenn nur eine Beilage existiert
	else{
		for (Rezept t : listrezept) {
			beilage1.setValue(t.getName());
			altb1 = t.getId().toString();
		}
	}
}
}
else {

}
}

//hier wird das Menue gespeichert
private void speichern() {
	System.out.println("inspeichern");
	Menue m = menuetabelleSpeichern();
	 try {
			Menueverwaltung.getInstance().createMenue(m);
		} catch (Exception e) {
			e.printStackTrace();
		}
	  System.out.println("gespeichert");
	 Menue menue1 = null;
		try {
			menue1 = Menueverwaltung.getInstance().getMenueByName1(menuenameInput);
		} catch (Exception e1) {
			 
			e1.printStackTrace();
		}
	
		System.out.println(menue1.getName());
	 fussnotenSetzen(menue1);
	 hauptgerichtSetzen(menue1);
	 beilage1Setzen(menue1);
	 beilage2Setzen(menue1);	


	Notification notification = new Notification("Menue wurde gespeichert!");
	notification.setDelayMsec(500);
	notification.show(Page.getCurrent());
	ViewHandler.getInstance().switchView(MenueAnzeigenTabelle.class);
	
	
}

//hier werden die Aenderungen an einem Menue vermerkt
private void aendern() {
	System.out.println("in andern");
	
	MenueHasFussnote fussnote = new MenueHasFussnote();
			Menue m = menuetabelleSpeichern();
	
	try {
		Menueverwaltung.getInstance().updateMenue(m, menue.getId());
		
	} catch (Exception e) {
		e.printStackTrace();
	}
     
	//wenn der merkerh auf x gesetzt wird(Hauptgericht hat sich geaendert)
	//wird das alte Hautgericht(alth) geloescht und das neue eingefuegt
	if (merkerh == "x") {
		try {
		Menueverwaltung.getInstance().RezepteDelete(menue, althg);
	} catch (Exception e1) {
		 
		e1.printStackTrace();
	}
		hauptgerichtSetzen(menue);
	}
	

	//wenn der merker1 auf x gesetzt wird(Beilage1 hat sich geaendert)
		//wird die alte Beilage1(altb1) geloescht und die neue eingefuegt
	if (merker1 == "x") {
		if (altb1 != null) {
		try {
		Menueverwaltung.getInstance().RezepteDelete(menue, altb1);
	} catch (Exception e1) {
		 
		e1.printStackTrace();
	}
		}
		else {
			
		}
		
		 beilage1Setzen(menue);
	}
	
	//wenn der merker2 auf x gesetzt wird(Beilage2 hat sich geaendert)
			//wird die alte Beilage2(altb1) geloescht und die neue eingefuegt
	if (merker2 == "x") {
		if (altb2 != null) {
		try {
		Menueverwaltung.getInstance().RezepteDelete(menue, altb2);
	} catch (Exception e1) {
		 
		e1.printStackTrace();
	}
		}
		else {
			
		}
		 beilage2Setzen(menue);
	}

	//wenn der merker auf x gesetzt ist, das heiï¿½t die Fussnoten ahben sich geaendert
	//werdne dei alten Fussnoten geloescht und neue eingefuegt
	if(merker == "x") {
	try {
		Menueverwaltung.getInstance().FussnoteDelete(menue);
	} catch (Exception e1) {
		 
		e1.printStackTrace();
	}
    fussnotenSetzen(menue);
	}
	else {
		
	}
   
	Notification notification = new Notification("Menue wurde geaendert!");
	notification.setDelayMsec(500);
	notification.show(Page.getCurrent());
	ViewHandler.getInstance().switchView(MenueAnzeigenTabelle.class);
	
}
	@Override
	public void valueChange(ValueChangeEvent event) {
		
		
	}






	



	
}

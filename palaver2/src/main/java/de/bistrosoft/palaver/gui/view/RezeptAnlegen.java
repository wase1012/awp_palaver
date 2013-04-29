/**
 * 
 * Jan Lauinger
 * 18.04.2013 - 21:21:58
 *
 */
package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;

/**
 * @author Jan Lauinger
 * 
 */
public class RezeptAnlegen extends VerticalLayout {

	private VerticalLayout box = new VerticalLayout();

	//Labelfeld
	private Label ueberschrift = new Label("<pre><b><font size='4' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Rezept anlegen</font><b></pre>",Label.CONTENT_XHTML);
	
	//Text Felder
	private TextField bezeichnung = new TextField("Bezeichnung");
	private TextField rezeptersteller = new TextField("Rezeptersteller");
	private TextField portion = new TextField("Portion");
	private TextField art1 = new TextField("Rezeptart");
	private TextField geschmack1 = new TextField("Geschmack");
	
	//Checkboxen
	private CheckBox herd = new CheckBox("Herd");
	private CheckBox konvektomat = new CheckBox("Konvektomat");
	
	//Combofeld
	private ComboBox art = new ComboBox("Rezeptart");
	private ComboBox geschmack = new ComboBox("Geschmack");
	
	//Twincolselect
	private TwinColSelect fussnoten = new TwinColSelect();
	
	//Textarea
	private TextArea kommentar = new TextArea("Kommentar");
	
	//Buttons
	private Button speichern = new Button("Speichern");
	private Button verwerfen = new Button("Verwerfen");
	private Button zutatenhinzufuegen = new Button("Zutaten hinzufügen");

	//Tabelle
	private Table zutaten = new Table("Zutaten");

	public RezeptAnlegen() {
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		
        //Prozedur, welche gestartet wird, wenn man auf den Button verwerfen klickt
		// diese führt die Funktion felderLeeren aus
		verwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				felderLeeren();

				Notification.show("Rezept wurde verworfen",
						Type.TRAY_NOTIFICATION);
			}
		});
        
		//Prozedur, welche gestartet wird, wenn man auf den Button speichernn klickt
		// diese führt die Funktion felderLeeren aus
		//Datensätze werden in der DB gespeichert (noch nicht)
		speichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				felderLeeren();

				Notification.show("Rezept wurde erfolgreich gespeichert",
						Type.TRAY_NOTIFICATION);
			}
		});	
		
		
        //vertikales Layout auf die Seite legen
		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		
		//neue Horizontale Layouts einfuegen 
		HorizontalLayout subBox = new HorizontalLayout();				
		HorizontalLayout control = new HorizontalLayout();
				
		//Eigenschaften der Felder anpassen
		box.setWidth("300px");
		box.setSpacing(true);
		bezeichnung.setWidth("100%");
		rezeptersteller.setWidth("100%");
		kommentar.setWidth("100%");
		portion.setWidth("100%");
		portion.setValue("30");
		portion.setReadOnly(true);
		fussnoten.setMultiSelect(true);
		fussnoten.setLeftColumnCaption("Auswahl");
		fussnoten.setRightColumnCaption("ausgewaehlte Fussnoten");
		subBox.setWidth("100%");
		control.setSpacing(true);
		art1.setDescription("bitte die entsprechende Zahl eintragen <br />" +
				"            1 - Hauptgericht <br />" +
				"            2 - Pasta <br />" +
				"            3 - Suppe <br />" +
				"            4 - Salat <br />" +
				"            5 - Dessert <br />");
		geschmack1.setDescription("bitte die entsprechende Zahl eintragen <br />" +
				"            1 - exotisch <br />" +
				"            2 - klassisch <br />" +
				"            3 - deftig <br />" +
				"            4 - mediterran <br />");
		
		
		//Komponenten zum Layout 'subBox' hinzufuegen
		subBox.addComponent(herd);
		subBox.addComponent(konvektomat);		
		
		
        //Komponenten zum Layout 'box' hinzufuegen
		box.addComponent(ueberschrift);
		box.addComponent(bezeichnung);
		box.addComponent(rezeptersteller);
		box.addComponent(portion);
		//box.addComponent(geschmack);
		box.addComponent(geschmack1);
		//box.addComponent(art);
		box.addComponent(art1);
		box.addComponent(subBox);
		//box.addComponent(fussnoten);
        box.addComponent(zutaten);
		box.addComponent(zutatenhinzufuegen);
		box.addComponent(kommentar);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_LEFT);

		//Komponenten zum Layout 'control' hinzufuegen
		control.addComponent(verwerfen);
		control.addComponent(speichern);
        
		
		//Prozeduren ausführen
		geschmackAdd();
		artAdd();
		fussnotenAdd();
		zutatenAdd();
		
	}
    // mit der Prozedur felderLeeren wrden die Ihnalte aus den Feldern geloescht
	private void felderLeeren() {
		bezeichnung.setValue("");
		rezeptersteller.setValue("");
		//geschmack.setValue(null);
		//art.setValue(null);
		geschmack1.setValue("");
		art1.setValue("");
		fussnoten.removeAllItems();
		zutaten.removeAllItems();
		kommentar.setValue("");
		herd.setValue(false);
		konvektomat.setValue(false);
		fussnotenAdd();
	}
    //artAdd fuegt die Rezeptarten in die Combobox 'art'
	private void artAdd() {
		art.addItem(1);
		art.setItemCaption(1, "Hauptgericht");
		art.addItem(2);
		art.setItemCaption(2, "Suppe");
		art.addItem(3);
		art.setItemCaption(3, "Beilage");
	}
    //fussnotenAdd fuegt die Fussnoten in die Twincolselect 'fussnoten' ein 
	private void fussnotenAdd() {
		
		
		
		fussnoten.addItem(1);
		fussnoten.setItemCaption(1, "Rindfleisch ");
		fussnoten.addItem(2);
		fussnoten.setItemCaption(2, "ohne Zwiebel");
		fussnoten.addItem(3);
		fussnoten.setItemCaption(3, "Tomaten");
		fussnoten.addItem(4);
		fussnoten.setItemCaption(4, "vegetarisch");
		fussnoten.addItem(5);
		fussnoten.setItemCaption(5, "vegan");
		fussnoten.addItem(6);
		fussnoten.setItemCaption(6, "Nuesse");
	}
	 //zutatennAdd fuegt die Zutaten in die Tabelle 'zutaten' ein 
	//bruacht man spaeter nicht mehr
	private void zutatenAdd() {
		zutaten.addContainerProperty("Bezeichnug", String.class, null);
		zutaten.addContainerProperty("Menge", Integer.class, null);
		zutaten.addContainerProperty("Mengeneinheit", String.class, null);
		zutaten.addContainerProperty("Typ", String.class, null);

		zutaten.addItem(
				new Object[] { "Hackfleisch", 3500, "g", "Hauptzutat" },
				new Integer(1));
		zutaten.addItem(new Object[] { "Tomaten", 10, "st", "Hauptzutat" },
				new Integer(2));
		zutaten.addItem(new Object[] { "Käse", 1000, "g", "Hauptzutat" },
				new Integer(3));
		zutaten.addItem(new Object[] { "Salz", 3, "g", "Standard" },
				new Integer(4));
	}
	 //geschmacknAdd fuegt die Geschmacksrichtungen in die Combobox 'geschmack' ein 
	private void geschmackAdd() {
		geschmack.addItem(1);
		geschmack.setItemCaption(1, "klassisch");
		geschmack.addItem(2);
		geschmack.setItemCaption(2, "exotisch");
	}

}

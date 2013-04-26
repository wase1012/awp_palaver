/**
 * 
 * Jan Lauinger
 * 18.04.2013 - 21:21:58
 *
 */
package de.bistrosoft.palaver.gui.view;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Jan Lauinger
 *
 */
public class RezeptAnlegen extends VerticalLayout{
	
	private VerticalLayout	box = new VerticalLayout();
	
	private TextField bezeichnung = new TextField("Bezeichnung");
	private TextField rezeptersteller = new TextField("Rezeptersteller"); 
	private TextField portion = new TextField("Portion");	
	private CheckBox herd = new CheckBox("Herd");
	private CheckBox konvektomat = new CheckBox("Konvektomat");	
	private ComboBox art = new ComboBox("Rezeptart");
	private ComboBox geschmack = new ComboBox("Geschmack");
private TwinColSelect fussnoten = new TwinColSelect();
	private TextArea kommentar = new TextArea("Kommentar");
	private Button speichern = new Button("Speichern");
	private Button verwerfen = new Button("Verwerfen");
	private Button zutatenhinzufuegen = new Button("Zutaten hinzufügen");
	
	private Table zutaten = new Table("Zutaten");
	
	public RezeptAnlegen()
	{
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		bezeichnung.setWidth("100%");
		kommentar.setWidth("100%");
		portion.setWidth("100%");
		portion.setReadOnly(true);
		portion.setValue("30");
		
		
		box.setWidth("300px");
		box.setSpacing(true);
			
		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		
		box.addComponent(bezeichnung);
		box.addComponent(rezeptersteller);
		box.addComponent(portion);
		box.addComponent(geschmack);
		box.addComponent(art);
		
		HorizontalLayout subBox = new HorizontalLayout();
		subBox.setWidth("100%");
		box.addComponent(subBox);
		subBox.addComponent(herd);
		subBox.addComponent(konvektomat);

		box.addComponent(fussnoten);
		fussnoten.setLeftColumnCaption("Auswahl an Fussnoten");
		fussnoten.setRightColumnCaption("Fussnoten fuer das neue Rezept");
		
		
		fussnoten.addItem("1");
       fussnoten.setItemCaption(1, "Rindfleisch ");
       fussnoten.addItem(2);
       fussnoten.setItemCaption(2, "ohne Zwiebel");        fussnoten.addItem(3);
       fussnoten.setItemCaption(3, "Tomaten");
       fussnoten.addItem(4);
       fussnoten.setItemCaption(4, "vegetarisch");
        fussnoten.addItem(5);
        fussnoten.setItemCaption(5, "vegan");
        fussnoten.addItem(6);
       fussnoten.setItemCaption(6, "Nuesse");
		box.addComponent(kommentar);
		
		
		box.addComponent(zutaten);
			
		HorizontalLayout control = new HorizontalLayout();
		control.setSpacing(true);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		
		control.addComponent(zutatenhinzufuegen);
		
		control.addComponent(verwerfen);
		control.addComponent(speichern);
		
		//Test anfang
//		  zutaten = new Table();
	   zutaten.addContainerProperty("Bezeichnug", String.class, null);
	   zutaten.addContainerProperty("Menge", Integer.class, null);
	   zutaten.addContainerProperty("Mengeneinheit", String.class, null);
	   zutaten.addContainerProperty("Typ", String.class, null);

	   zutaten.addItem(new Object[] {
		   "Hackfleisch", 3500, "g", "Hauptzutat" }, new Integer(1));
	   zutaten.addItem(new Object[] {
			   "Tomaten", 10, "st", "Hauptzutat" }, new Integer(2));
	   zutaten.addItem(new Object[] {
			   "Käse", 1000, "g", "Hauptzutat" }, new Integer(3));
	   zutaten.addItem(new Object[] {
			   "Salz", 3, "g", "Standard" }, new Integer(4));
	   
	   
	   //Test ende
	}

}


 
package de.bistrosoft.palaver.gui.view;

import com.vaadin.client.ui.Action;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


public class RezeptAnzeigen extends VerticalLayout{
	
	private HorizontalLayout oben = new HorizontalLayout();
	
	private HorizontalLayout unten = new HorizontalLayout();
	
	private VerticalLayout	links = new VerticalLayout();
	private VerticalLayout	rechts = new VerticalLayout();
	private VerticalLayout	mitte = new VerticalLayout();
	//ueberschrift = new Label("Rezept Anzeige");
  // ueberschrift.setContentMode(ContentMode.HTML);
 
	private TextField bezeichnung = new TextField("Bezeichnung");     
	private TextField rezeptersteller = new TextField("Rezeptersteller");  
	private TextField portion = new TextField("Portion");	
	
	// variabel gestalten? da man neue einfuegen kann
	private CheckBox herd = new CheckBox("Herd");
	private CheckBox konvektomat = new CheckBox("Konvektomat");
	
	private TextField art = new TextField("Rezeptart");
	private TextField geschmack = new TextField("Geschmack");
	private Table fussnoten = new Table("Fussnoten");
	private TextArea kommentar = new TextArea("Kommentar");
	private Button speichern = new Button("Speichern");
	private Button verwerfen = new Button("Verwerfen");
	private Button zutatenhinzufuegen = new Button("Zutaten hinzufügen");
	
	private Table zutaten = new Table("Zutaten");
	
	public RezeptAnzeigen()
	{
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		bezeichnung.setWidth("100%");
	
		bezeichnung.setValue("Lasangne");
		bezeichnung.setReadOnly(true);
		rezeptersteller.setWidth("100%");
		rezeptersteller.setValue("Koch1");
		rezeptersteller.setReadOnly(true);		
		kommentar.setWidth("100%");
		kommentar.setValue("blablablabla");
		kommentar.setReadOnly(true);		
		portion.setWidth("100%");
		portion.setValue("30");
		portion.setReadOnly(true);		
		geschmack.setWidth("100%");
		geschmack.setValue("klassisch");
		geschmack.setReadOnly(true);		
		art.setWidth("100%");
		art.setValue("Hauptgericht");
		art.setReadOnly(true);		
		herd.setWidth("100%");
		herd.setValue(true);
		herd.setReadOnly(true);		
		konvektomat.setWidth("100%");
		konvektomat.setValue(true);
		konvektomat.setReadOnly(true);		
		fussnoten.setSizeFull();
		
		fussnoten.setImmediate(true);
		
		//oben.setWidth("100%");
		oben.setSpacing(true);
		links.setWidth("300px");
		mitte.setWidth("300px");
		rechts.setWidth("300px");
		unten.setWidth("300px");	
		unten.setSpacing(true);
		 unten.setHeight(100.0f, Unit.PERCENTAGE);
		this.addComponent(oben);
		this.setComponentAlignment(oben, Alignment.MIDDLE_CENTER);
		
		
		
		
		//oben.setHeight("100%");
		oben.addComponent(links);
		oben.addComponent(mitte);
		oben.addComponent(rechts);
		
		
		links.addComponent(bezeichnung);
		links.addComponent(rezeptersteller);
		links.addComponent(portion);
		links.addComponent(geschmack);
		links.addComponent(art);
		
		HorizontalLayout subBox = new HorizontalLayout();
		subBox.setWidth("100%");
		links.addComponent(subBox);
		subBox.addComponent(herd);
		subBox.addComponent(konvektomat);
		subBox.setImmediate(true);
		
		
		mitte.addComponent(fussnoten);
		 fussnoten.addContainerProperty("Bezeichnug", String.class, null);
		 fussnoten.addItem(new Object[] {
				   "Rindfleisch"}, new Integer(1));
		 fussnoten.addItem(new Object[] {
				   "Tomaten"}, new Integer(2));
		 
		
		 mitte.addComponent(unten);
		
		rechts.addComponent(zutaten);
			
		HorizontalLayout control = new HorizontalLayout();
		control.setSpacing(true);
		rechts.addComponent(control);
		rechts.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		
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
		
		unten.addComponent(kommentar);
	}

}


 
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
	
	
	
	private VerticalLayout	box = new VerticalLayout();
	 
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
		bezeichnung.setImmediate(true);
		bezeichnung.setInputPrompt("Lasangne");
		rezeptersteller.setWidth("100%");
		rezeptersteller.setImmediate(true);
		rezeptersteller.setInputPrompt("Koch1");
		kommentar.setWidth("100%");
		kommentar.setImmediate(true);
		kommentar.setInputPrompt("blablablabla");
		portion.setWidth("100%");
		portion.setImmediate(true);
		portion.setInputPrompt("30");
		geschmack.setWidth("100%");
		geschmack.setImmediate(true);
		geschmack.setInputPrompt("klassisch");
		art.setWidth("100%");
		art.setImmediate(true);
		art.setInputPrompt("Hauptgericht");
		herd.setWidth("100%");
		herd.setImmediate(true);
		herd.setValue(true);
		konvektomat.setWidth("100%");
		konvektomat.setImmediate(true);
		konvektomat.setValue(true);
		fussnoten.setSizeFull();
		
		fussnoten.setImmediate(true);
		
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
		subBox.setImmediate(true);
		
		
		box.addComponent(fussnoten);
		 fussnoten.addContainerProperty("Bezeichnug", String.class, null);
		 fussnoten.addItem(new Object[] {
				   "Rindfleisch"}, new Integer(1));
		 fussnoten.addItem(new Object[] {
				   "Tomaten"}, new Integer(2));
		 
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
	   
	   herd.setImmediate(true);
		konvektomat.setImmediate(true);
	   //Test ende
	}

}

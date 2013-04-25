
 
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
	
	private TextField portion = new TextField("Portion");	
	
	// variabel gestalten? da man neue einfuegen kann
	private CheckBox herd = new CheckBox("Herd");
	private CheckBox konvektomat = new CheckBox("Konvektomat");
	
	private ComboBox art = new ComboBox("Art");
	private ComboBox geschmack = new ComboBox("Geschmack");
	private ComboBox charakteristika = new ComboBox("Charakteristika");
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
		
		kommentar.setWidth("100%");
		portion.setWidth("100%");
		charakteristika.setWidth("100%");
		
		box.setWidth("300px");
		box.setSpacing(true);
			
		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		
		box.addComponent(bezeichnung);
		box.addComponent(portion);
		box.addComponent(geschmack);
		box.addComponent(art);
		
		HorizontalLayout subBox = new HorizontalLayout();
		subBox.setWidth("100%");
		box.addComponent(subBox);
		subBox.addComponent(herd);
		subBox.addComponent(konvektomat);

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


 
package de.bistrosoft.palaver.gui.view;

import com.vaadin.client.ui.Action;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


public class RezeptAnzeigen extends VerticalLayout{
	
	//Layouts
	private HorizontalLayout oben = new HorizontalLayout();
	private HorizontalLayout unten = new HorizontalLayout();
	private VerticalLayout	links = new VerticalLayout();
	private VerticalLayout	rechts = new VerticalLayout();
	private VerticalLayout	mitte = new VerticalLayout();
	private VerticalLayout verticalunten = new VerticalLayout();
	//Ueberschriften
	private Label uBezeichnung = new Label("<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Bezeichnung</font><b></pre>", Label.CONTENT_XHTML);
	private Label uRezeptersteller = new Label("<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Rezeptersteller</font><b></pre>", Label.CONTENT_XHTML);
	private Label uPortion = new Label("<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Portion</font><b></pre>", Label.CONTENT_XHTML);
	private Label uBelegung = new Label("<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Resourcenbelegung</font><b></pre>", Label.CONTENT_XHTML);
	private Label uArt = new Label("<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Rezeptart</font><b></pre>", Label.CONTENT_XHTML);
	private Label uGeschmack = new Label("<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Geschmack</font><b></pre>", Label.CONTENT_XHTML);
	private Label uKommentar = new Label("<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Kommentar</font><b></pre>", Label.CONTENT_XHTML);
	private Label uFussnoten = new Label("<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Fussnoten</font><b></pre>", Label.CONTENT_XHTML);
	private Label uZutaten = new Label("<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Zutaten</font><b></pre>", Label.CONTENT_XHTML);
	 //Textfelder
	private TextField bezeichnung = new TextField(); 
	private TextField rezeptersteller = new TextField();  
	private TextField portion = new TextField();	
	private TextField art = new TextField();
	private TextField geschmack = new TextField();
	//Checkboxen
	private CheckBox herd = new CheckBox("Herd");
	private CheckBox konvektomat = new CheckBox("Konvektomat");
	//Tabellen
	private Table fussnoten = new Table();
	private Table zutaten = new Table();
	//Textarea
	private TextArea kommentar = new TextArea();
	//Button
	private Button aendern = new Button("Rezept bearbeiten");
	
	public RezeptAnzeigen()
	{
		super();
		this.setSizeFull();
		this.setMargin(true);
		this.addComponent(oben);
		this.setComponentAlignment(oben, Alignment.MIDDLE_CENTER);
		
		HorizontalLayout subBox = new HorizontalLayout();
		HorizontalLayout control = new HorizontalLayout();
		
		//Eigenschaften anpassen
		bezeichnung.setWidth("100%");	
		bezeichnung.setValue("Lasangne");
		bezeichnung.setReadOnly(true);
		rezeptersteller.setWidth("100%");
		rezeptersteller.setValue("Koch1");
		rezeptersteller.setReadOnly(true);		
		kommentar.setWidth("100%");
		kommentar.setValue("es wird sowohl der Herd als auch der Konvektomat benoetigt");
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
		
		
		oben.setSpacing(true);
		links.setWidth("300px");
		links.setSpacing(true);
		mitte.setWidth("300px");
		mitte.setSpacing(true);
		rechts.setWidth("300px");
		rechts.setSpacing(true);
		unten.setWidth("100%");	
		unten.setSpacing(true);
		unten.setHeight(100.0f, Unit.PERCENTAGE);
		subBox.setWidth("100%");
		subBox.setImmediate(true);
		control.setSpacing(true);
		
		//Komponenten zufuegen
		oben.addComponent(links);
		oben.addComponent(mitte);
		oben.addComponent(rechts);
		links.addComponent(uBezeichnung);
		links.addComponent(bezeichnung);
		links.addComponent(uRezeptersteller);
		links.addComponent(rezeptersteller);
		links.addComponent(uPortion);
		links.addComponent(portion);
		links.addComponent(uGeschmack);
		links.addComponent(geschmack);
		links.addComponent(uArt);
		links.addComponent(art);
		links.addComponent(uBelegung);
		links.addComponent(subBox);
		subBox.addComponent(herd);
		subBox.addComponent(konvektomat);
		links.addComponent(control);
		links.setComponentAlignment(control, Alignment.MIDDLE_LEFT);
		control.addComponent(aendern);
	    mitte.addComponent(uFussnoten);
		mitte.addComponent(fussnoten);
		mitte.addComponent(unten);
		rechts.addComponent(uZutaten);
	    rechts.addComponent(zutaten);
		
		 fussnoten.addContainerProperty("Bezeichnug", String.class, null);
		 fussnoten.addItem(new Object[] {
				   "Rindfleisch"}, new Integer(1));
		 fussnoten.addItem(new Object[] {
				   "Tomaten"}, new Integer(2));
		 
		
		
			
		
		
		
		
		
		
		
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
	   
	   
	   unten.addComponent(verticalunten);
	   verticalunten.addComponent(uKommentar);
	   verticalunten.addComponent(kommentar);
	}

}

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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Jan Lauinger
 *
 */
public class RezeptAnlegen extends VerticalLayout{
	
	private VerticalLayout	box = new VerticalLayout();
	
	private TextField bezeichnung = new TextField("Bezeichnung");
	private TextField kommentar = new TextField("Kommentar");
	private TextField portion = new TextField("Portion");

	
//	private CheckBox exotisch = new CheckBox("exotisch");
//	private CheckBox klassisch = new CheckBox("klassisch");
//	private CheckBox deftig = new CheckBox("deftig");
//	private CheckBox mediterran = new CheckBox("mediterran");
	
//	private CheckBox beilage = new CheckBox("Beilage");
//	private CheckBox hauptgericht = new CheckBox("Hauptgericht");
//	private CheckBox kuchen = new CheckBox("Kuchen");
//	private CheckBox salat = new CheckBox("Salat");
//	private CheckBox dessert = new CheckBox("Dessert");
//	private CheckBox pasta = new CheckBox("Pasta");
//	private CheckBox suppe = new CheckBox("Suppe");
	
	private CheckBox herd = new CheckBox("Herd");
	private CheckBox konvektomat = new CheckBox("Konvektomat");
	
	private ComboBox art = new ComboBox("Art");
	private ComboBox geschmack = new ComboBox("Geschmack");
	private ComboBox charakteristika = new ComboBox("Charakteristika");
	
	private Button speichern = new Button("Speichern");
	private Button verwerfen = new Button("Verwerfen");
	
	
	public RezeptAnlegen()
	{
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		bezeichnung.setWidth("100%");
		kommentar.setWidth("100%");
		portion.setWidth("100%");
		charakteristika.setWidth("100%");
		
		box.setWidth("300px");
//		box.setHeight("100%");
		box.setSpacing(true);
		
		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		
		box.addComponent(bezeichnung);
		box.addComponent(portion);
		box.addComponent(kommentar);
		box.addComponent(geschmack);
		box.addComponent(art);
		
		
		HorizontalLayout subBox = new HorizontalLayout();
		subBox.setWidth("100%");
		box.addComponent(subBox);
		subBox.addComponent(herd);
		subBox.addComponent(konvektomat);

		
//		HorizontalLayout subBox2 = new HorizontalLayout();
//		subBox2.setWidth("100%");
//		box.addComponent(subBox2);
//		subBox2.addComponent(dessert);
//		subBox2.addComponent(pasta);
//		subBox2.addComponent(suppe);
		
		
		
		HorizontalLayout control = new HorizontalLayout();
//		control.setWidth("100%");
		control.setSpacing(true);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		
		control.addComponent(verwerfen);
		control.addComponent(speichern);
	}

}

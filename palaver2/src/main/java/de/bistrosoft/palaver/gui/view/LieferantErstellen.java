/**
 * Created by Sebastian Walz
 * 18.04.2013 15:41:31
 */
package de.bistrosoft.palaver.gui.view;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class LieferantErstellen extends VerticalLayout
{
	private HorizontalLayout	box = new HorizontalLayout();
	
	private TextField			name = new TextField("Name");
	private TextField			strasse = new TextField("Staße");
	private TextField			plz = new TextField("PLZ");
	private TextField			ort = new TextField("Ort");
	private TextField			email = new TextField("E-Mail");
	private TextField			telefon = new TextField("Telefon");
	private TextField			fax = new TextField("Telefax");
	
	private ListSelect			ansprehpartner = new ListSelect("Ansprechpartner");
	
	private Button				speichern = new Button("Speichern");
	private Button				verwerfen = new Button("Verwerfen");
	
	public LieferantErstellen()
	{
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		name.setWidth("100%");
		strasse.setWidth("100%");
		plz.setWidth("100%");
		ort.setWidth("100%");
		email.setWidth("100%");
		telefon.setWidth("100%");
		fax.setWidth("100%");
		
		ansprehpartner.setWidth("100%");
		
		box.setWidth("610px");
		box.setSpacing(true);
		
		VerticalLayout links = new VerticalLayout();
		VerticalLayout rechts = new VerticalLayout();
		links.setWidth("300px");
		rechts.setWidth("300px");
		links.setSpacing(true);
		rechts.setSpacing(true);
		box.addComponent(links);
		box.addComponent(rechts);
		
		links.addComponent(name);
		links.addComponent(strasse);
		links.addComponent(plz);
		links.addComponent(ort);
		links.addComponent(email);
		links.addComponent(telefon);
		links.addComponent(fax);
		
		rechts.addComponent(ansprehpartner);
		
		HorizontalLayout contol = new HorizontalLayout();
		contol.setSpacing(true);
		
		contol.addComponent(verwerfen);
		contol.addComponent(speichern);
		
		rechts.addComponent(contol);
		rechts.setComponentAlignment(contol, Alignment.MIDDLE_RIGHT);
		
		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
	}
}

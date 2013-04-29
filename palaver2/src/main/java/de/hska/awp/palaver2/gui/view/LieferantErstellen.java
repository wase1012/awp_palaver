/**
 * Created by Sebastian Walz
 * 18.04.2013 15:41:31
 */
package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class LieferantErstellen extends VerticalLayout
{
	private HorizontalLayout	box = new HorizontalLayout();
	
	private TextField			name = new TextField("Name");
	private TextField			bezeichnung = new TextField("Bezeichnung");
	private TextField			kundennummer = new TextField("Kundennummer");
	private TextField			strasse = new TextField("Staße");
	private TextField			plz = new TextField("PLZ");
	private TextField			ort = new TextField("Ort");
	private TextField			email = new TextField("E-Mail");
	private TextField			telefon = new TextField("Telefon");
	private TextField			fax = new TextField("Telefax");
	
	private String nameInput;
	private String strasseInput;
	private String plzInput;
	private String ortInput;
	private String emailInput;
	private String telefonInput;
	private String faxInput;
	private String bezInput;
	private String knrInput;
	private String ansprInput;
	
	
	
	private ListSelect			ansprehpartner = new ListSelect("Ansprechpartner");
	
	private Button				speichern = new Button("Speichern");
	private Button				verwerfen = new Button("Verwerfen");
	
	public LieferantErstellen()
	{
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		name.setWidth("100%");
		bezeichnung.setWidth("100%");
		kundennummer.setWidth("100%");
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
		links.addComponent(bezeichnung);
		links.addComponent(kundennummer);
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
		
		name.setImmediate(true);
		name.setInputPrompt(nameInput);
		name.setMaxLength(45);
		
		bezeichnung.setImmediate(true);
		bezeichnung.setInputPrompt(bezInput);
		bezeichnung.setMaxLength(45);
		
		kundennummer.setImmediate(true);
		kundennummer.setInputPrompt(knrInput);
		kundennummer.setMaxLength(45);
		
		strasse.setImmediate(true);
		strasse.setInputPrompt(strasseInput);
		strasse.setMaxLength(45);
		
		plz.setImmediate(true);
		plz.setInputPrompt(plzInput);
		plz.setMaxLength(45);
		
		ort.setImmediate(true);
		ort.setInputPrompt(ortInput);
		ort.setMaxLength(45);
		
		email.setImmediate(true);
		email.setInputPrompt(emailInput);
		email.setMaxLength(45);
		
		telefon.setImmediate(true);
		telefon.setInputPrompt(telefonInput);
		telefon.setMaxLength(45);
		
		fax.setImmediate(true);
		fax.setInputPrompt(faxInput);
		fax.setMaxLength(45);
		
        name.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                nameInput = valueString;
            }
        });
        
        bezeichnung.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                bezInput = valueString;
            }
        });
        
        kundennummer.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                knrInput = valueString;
            }
        });
        
        strasse.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                strasseInput = valueString;
            }
        });
        
        plz.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                plzInput = valueString;
            }
        });
        
        ort.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                ortInput = valueString;
            }
        });
        
        email.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                emailInput = valueString;
            }
        });
        
        telefon.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                telefonInput = valueString;
            }
        });
        
        fax.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                faxInput = valueString;
            }
        });
        	
	speichern.addClickListener(new ClickListener()
	{			
		@Override
		public void buttonClick(ClickEvent event)
		{
			final Window dialog = new Window("Speichern");
			dialog.setClosable(false);
			dialog.setWidth("300px");
			dialog.setHeight("150px");
			dialog.setModal(true);
			dialog.center();
			dialog.setResizable(false);
			
			Label message = new Label("Lieferant gespeichert");
			
			Button okButton = new Button("OK");
			
			VerticalLayout dialogContent = new VerticalLayout();
			dialogContent.setSizeFull();
			dialogContent.setMargin(true);
			dialog.setContent(dialogContent);
			
			dialogContent.addComponent(message);
			dialogContent.addComponent(okButton);
			dialogContent.setComponentAlignment(okButton, Alignment.BOTTOM_RIGHT);
			
			UI.getCurrent().addWindow(dialog);
			Lieferant lieferant = new Lieferant();
			lieferant.setName(nameInput);
			lieferant.setBezeichnung(bezInput);
			lieferant.setKundennummer(knrInput);
			lieferant.setStrasse(strasseInput);
			lieferant.setPlz(plzInput);
			lieferant.setOrt(ortInput);
			lieferant.setEmail(emailInput);
			lieferant.setTelefon(telefonInput);
			lieferant.setFax(faxInput);
//			lieferant.setAnsprechpartner();
			
			
			try {
				Lieferantenverwaltung.getInstance().createLieferant(lieferant);
			} catch (ConnectException | DAOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			okButton.addClickListener(new ClickListener()
			{	
				@Override
				public void buttonClick(ClickEvent event)
				{
					UI.getCurrent().removeWindow(dialog);
					ViewHandler.getInstance().returnToDefault();
				}
			});
		}
	});
	}
}

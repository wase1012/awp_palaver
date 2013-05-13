/**
 * Created by Sebastian Walz
 * 18.04.2013 15:41:31
 */
package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class LieferantErstellen extends VerticalLayout implements View
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
	private TextArea			notiz = new TextArea("Notiz");
	private CheckBox			mehrereliefertermine = new CheckBox("mehrereliefertermine");
	
	private String nameInput;
	private String strasseInput;
	private String plzInput;
	private String ortInput;
	private String emailInput;
	private String telefonInput;
	private String faxInput;
	private String bezInput;
	private String knrInput;
	private String notizInput;
	
	private Lieferant lieferant = new Lieferant();

	
	private Button			speichern = new Button(IConstants.BUTTON_SAVE);
	private Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
	
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
		notiz.setWidth("100%");
		notiz.setRows(3);
		mehrereliefertermine.setWidth("100%");
		
		box.setWidth("65%");
		box.setHeight("90%");
		box.setSpacing(true);
		
		VerticalLayout links = new VerticalLayout();
		links.setWidth("250px");
		links.setSpacing(true);
		
		VerticalLayout mitte = new VerticalLayout();
		mitte.setWidth("250px");
		mitte.setSpacing(true);
		
		VerticalLayout rechts = new VerticalLayout();
		rechts.setWidth("250px");
		rechts.setSpacing(true);
		
		box.addComponent(links);
		box.addComponent(mitte);
		
		
		links.addComponent(name);
		links.addComponent(bezeichnung);
		links.addComponent(kundennummer);
		links.addComponent(strasse);
		mitte.addComponent(plz);
		mitte.addComponent(ort);
		mitte.addComponent(email);
		mitte.addComponent(telefon);
		rechts.addComponent(fax);
		rechts.addComponent(notiz);
		rechts.addComponent(mehrereliefertermine);
		
		box.addComponent(rechts);
		
		HorizontalLayout control = new HorizontalLayout();
		control.setWidth("100%");
		control.setSpacing(true);
//		box.addComponent(control);
//		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
//		
		control.addComponent(verwerfen);
		control.addComponent(speichern);
		
		rechts.addComponent(control);
		rechts.setComponentAlignment(control, Alignment.TOP_CENTER);
		
//		rechts.addComponent(verwerfen);
//		rechts.addComponent(speichern);
		
		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		
		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		
		name.setImmediate(true);
		name.addValidator(new StringLengthValidator("Bitte gültigen Namen eingeben", 3,45, false));
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
		
//        Validator postalCodeValidator = new AbstractStringValidator(
//                "Bitte gültige PLZ eingeben.") {
//			@Override
//			protected boolean isValidValue(String value) {
//                return value.matches("[1-9][0-9]{4}");
//			}
//        };
		
		plz.setImmediate(true);
//		plz.addValidator(postalCodeValidator);
		plz.setMaxLength(45);
		
		ort.setImmediate(true);
		ort.setInputPrompt(ortInput);
		ort.setMaxLength(45);
		
		email.setImmediate(true);
		email.addValidator(new EmailValidator("Bitte gültige E-Mailadresse angeben"));
		email.setMaxLength(45);
		
		telefon.setImmediate(true);
		telefon.setInputPrompt(telefonInput);
		telefon.setMaxLength(45);
		
		fax.setImmediate(true);
		fax.setInputPrompt(faxInput);
		fax.setMaxLength(45);
		
		notiz.setImmediate(true);
		notiz.setInputPrompt(notizInput);
		notiz.setMaxLength(300);
		
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
        
        notiz.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                notizInput = valueString;
            }
        });
        
        	
	speichern.addClickListener(new ClickListener()
	{			
		@Override
		public void buttonClick(ClickEvent event)
		{
			final Window dialog = new Window();
			dialog.setClosable(false);
			dialog.setWidth("300px");
			dialog.setHeight("150px");
			dialog.setModal(true);
			dialog.center();
			dialog.setResizable(false);
			dialog.setStyleName("dialog-window");
			
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
			lieferant.setName(nameInput);
			lieferant.setBezeichnung(bezInput);
			lieferant.setKundennummer(knrInput);
			lieferant.setStrasse(strasseInput);
			lieferant.setPlz(plzInput);
			lieferant.setOrt(ortInput);
			lieferant.setEmail(emailInput);
			lieferant.setTelefon(telefonInput);
			lieferant.setFax(faxInput);
			lieferant.setNotiz(notizInput);
			lieferant.setMehrereliefertermine(mehrereliefertermine.getValue());
				
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
					ViewHandler.getInstance().switchView(LieferantSuche.class, new ViewDataObject<Lieferant>(lieferant));				}
			});
		}
	});
	}

	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}
}

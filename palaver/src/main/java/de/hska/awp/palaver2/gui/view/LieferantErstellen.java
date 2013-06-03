/**
 * Created by Sebastian Walz
 * 18.04.2013 15:41:31
 */
package de.hska.awp.palaver2.gui.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger	log	= LoggerFactory.getLogger(LieferantErstellen.class.getName());
	
	private HorizontalLayout	box = new HorizontalLayout();
	private VerticalLayout		fenster = new VerticalLayout();
	
	private Label				headline;
	
	private TextField			name = new TextField("Name");
	private TextField			bezeichnung = new TextField("Bezeichnung");
	private TextField			kundennummer = new TextField("Kundennummer");
	private TextField			strasse = new TextField("Stra�e");
	private TextField			plz = new TextField("PLZ");
	private TextField			ort = new TextField("Ort");
	private TextField			email = new TextField("E-Mail");
	private TextField			telefon = new TextField("Telefon");
	private TextField			fax = new TextField("Telefax");
	private TextArea			notiz = new TextArea("Notiz");
	private CheckBox			mehrereliefertermine = new CheckBox("mehrere Liefertermine");
	
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
		
		headline = new Label("Neuer Lieferant");
		headline.setStyleName("ViewHeadline");
		
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
		notiz.setHeight("92px");
		notiz.setRows(3);
		mehrereliefertermine.setWidth("100%");
		
		fenster.setWidth("900px");
		fenster.setSpacing(true);
		
		VerticalLayout links = new VerticalLayout();
		links.setWidth("250px");
		links.setSpacing(true);
		
		VerticalLayout mitte = new VerticalLayout();
		mitte.setWidth("250px");
		mitte.setSpacing(true);
		
		VerticalLayout rechts = new VerticalLayout();
		rechts.setWidth("250px");
		rechts.setSpacing(true);
		
		box.setWidth("900px");
		box.setSpacing(true);
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
		
		mitte.addComponent(control);
		mitte.setComponentAlignment(control, Alignment.TOP_CENTER);
		
//		rechts.addComponent(verwerfen);
//		rechts.addComponent(speichern);
		
		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		speichern.setEnabled(false);
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		
		
		fenster.addComponent(headline);
		fenster.setComponentAlignment(headline, Alignment.MIDDLE_CENTER);
		fenster.addComponent(box);
		fenster.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		
		this.addComponent(fenster);
		this.setComponentAlignment(fenster, Alignment.MIDDLE_CENTER);
		
		name.setImmediate(true);
		name.setRequired(true);
		name.addValidator(new StringLengthValidator("Bitte g�ltigen Namen eingeben", 3,45, false));
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
//                "Bitte g�ltige PLZ eingeben.") {
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
		email.addValidator(new EmailValidator("Bitte g�ltige E-Mailadresse angeben"));
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
        		if (name.isValid() == true) {
        			speichern.setEnabled(true);
        		}
        		else {
        			speichern.setEnabled(false);
        		}

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
        
    verwerfen.addClickListener(new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			ViewHandler.getInstance().returnToDefault();
			
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
			} catch (Exception e) {
				log.error(e.toString());
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

/**
 * Created by Elena W
 */
package de.hska.awp.palaver2.gui.view;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.Util;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class MitarbeiterErstellen extends VerticalLayout implements View
{
	private HorizontalLayout	box = new HorizontalLayout();
	private VerticalLayout		fenster = new VerticalLayout();
	
	private Label				headline;
	
	private TextField			name = new TextField("Name");
	private TextField			vorname = new TextField("Vorname");
	private TextField			email = new TextField("E-Mail");
	private PasswordField		passwort = new PasswordField("Passwort");
	private TextField			eintrittsdatum = new TextField("Eintrittsdatum");
	private TextField			austrittsdatum = new TextField("Austrittsdatum");
	private TextField			benutzername = new TextField("Benutzername");
	
	private String nameInput;
	private String vornameInput;
	private String emailInput;
	private String passwortInput;
	private String eintrittsdatumInput;
	private String austrittsdatumInput;
	private String benutzernameInput;

	
	private Mitarbeiter mitarbeiter = new Mitarbeiter();

	
	private Button			speichern = new Button(IConstants.BUTTON_SAVE);
	private Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
	
	public MitarbeiterErstellen()
	{
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		headline = new Label("Neuer Mitarbeiter");
		headline.setStyleName("ViewHeadline");
		
		name.setWidth("100%");
		vorname.setWidth("100%");
		email.setWidth("100%");
		passwort.setWidth("100%");
		eintrittsdatum.setWidth("100%");
		austrittsdatum.setWidth("100%");
		benutzername.setWidth("100%");
		
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
		
		
		mitte.addComponent(name);
		mitte.addComponent(vorname);		
		mitte.addComponent(email);
		mitte.addComponent(passwort);
		mitte.addComponent(eintrittsdatum);
		mitte.addComponent(austrittsdatum);	
		mitte.addComponent(benutzername);
		
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
		name.addValidator(new StringLengthValidator("Bitte g�ltigen Namen eingeben", 3,45, false));
		name.setMaxLength(45);
		
		vorname.setImmediate(true);
		vorname.addValidator(new StringLengthValidator("Bitte g�ltigen Namen eingeben", 3,45, false));
		vorname.setMaxLength(45);
		
		email.setImmediate(true);
		email.addValidator(new EmailValidator("Bitte g�ltige E-Mailadresse angeben"));
		email.setMaxLength(45);
		
		passwort.setImmediate(true);
		passwort.setInputPrompt(passwortInput);
		passwort.setMaxLength(45);
		
		eintrittsdatum.setImmediate(true);
		eintrittsdatum.setInputPrompt(eintrittsdatumInput);
		eintrittsdatum.setMaxLength(45);
		
		austrittsdatum.setImmediate(true);
		austrittsdatum.setInputPrompt(austrittsdatumInput);
		austrittsdatum.setMaxLength(300);
		
		benutzername.setImmediate(true);
		benutzername.setInputPrompt(benutzernameInput);
		benutzername.setMaxLength(45);
		
		
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
        
        vorname.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                vornameInput = valueString;
            }
        });
              
       
        email.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                emailInput = valueString;
            }
        });
        
        passwort.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                passwortInput = valueString;
            }
        });
        
       eintrittsdatum.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                eintrittsdatumInput = valueString;
            }
        });
        
        austrittsdatum.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                austrittsdatumInput = valueString;
            }
        });
        
        	benutzername.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                benutzernameInput = valueString;
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
			
			Label message = new Label("Mitarbeiter gespeichert");
			
			Button okButton = new Button("OK");
			
			VerticalLayout dialogContent = new VerticalLayout();
			dialogContent.setSizeFull();
			dialogContent.setMargin(true);
			dialog.setContent(dialogContent);
			
			dialogContent.addComponent(message);
			dialogContent.addComponent(okButton);
			dialogContent.setComponentAlignment(okButton, Alignment.BOTTOM_RIGHT);
			
			UI.getCurrent().addWindow(dialog);
			mitarbeiter.setName(nameInput);
			mitarbeiter.setVorname(vornameInput);
			mitarbeiter.setEmail(emailInput);
			try
			{
				mitarbeiter.setPasswort(Util.getMD5(passwortInput).toString());
			} catch (UnsupportedEncodingException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			mitarbeiter.setEintrittsdatum(eintrittsdatumInput);
			mitarbeiter.setAustrittsdatum(austrittsdatumInput);
			mitarbeiter.setBenutzername(benutzernameInput);
							
			try {
				Mitarbeiterverwaltung.getInstance().createMitarbeiter(mitarbeiter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			okButton.addClickListener(new ClickListener()
			{	
				@Override
				public void buttonClick(ClickEvent event)
				{
					UI.getCurrent().removeWindow(dialog);
					ViewHandler.getInstance().switchView(MitarbeiterSuche.class, new ViewDataObject<Mitarbeiter>(mitarbeiter));				}
			});
		}
	});
	}

	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}
}

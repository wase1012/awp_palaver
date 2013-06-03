package de.hska.awp.palaver2.gui.view;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.emailversand.Mail;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * 
 * @author Mihail Boehm
 *
 */
@SuppressWarnings("serial")
public class EmailOhneBestellung extends VerticalLayout implements  View{
	
	@SuppressWarnings("unused")
	private static final Logger	log	= LoggerFactory.getLogger(EmailOhneBestellung.class.getName());
	
	private VerticalLayout		fenster = new VerticalLayout();
	
	private Label				headline;
	
	private TextField			empfaenger = new TextField("Empf�nger");
	private TextField			betreff = new TextField("Betreff");
	private TextArea			nachricht = new TextArea("Nachricht");
	
	private String empfaengerInput;
	private String betreffInput;
	private String nachrichtInput;
	private String anhang = null;
	private boolean lieferanten = false;
	private Lieferant lieferant;
	
	private Button			senden = new Button(IConstants.BUTTON_SENDEN);
	private Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);

	Panel root;         // Root element for contained components.
    Panel imagePanel;   // Panel that contains the uploaded image.
    File  file;         // File to write to.
	
	public EmailOhneBestellung() {
		super();
		this.setSizeFull();
		
		this.setMargin(true);

		
		headline = new Label("Email senden");
		headline.setStyleName("ViewHeadline");
		
		empfaenger.setWidth("45%");
		betreff.setWidth("45%");
		nachricht.setWidth("100%");
		nachricht.setHeight("250px");
		
		fenster.setWidth("550px");
		fenster.setSpacing(true);
		fenster.addComponent(headline);
		fenster.setComponentAlignment(headline, Alignment.MIDDLE_CENTER);
	
		fenster.addComponent(empfaenger);
		fenster.addComponent(betreff);
		fenster.addComponent(nachricht);
		
		HorizontalLayout control = new HorizontalLayout();
		control.setWidth("50%");
		control.setSpacing(true);
		
		control.addComponent(verwerfen);
		control.addComponent(senden);
		fenster.addComponent(control);
		
		
		this.addComponent(fenster);
		this.setComponentAlignment(fenster, Alignment.MIDDLE_CENTER);
		
		
		empfaenger.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());
                empfaengerInput = valueString;
            }
        });
		
		betreff.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());
                betreffInput = valueString;
            }
        });
		
		nachricht.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());
                nachrichtInput = valueString;
            }
        });
		
		senden.addClickListener(new ClickListener()
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
				
				Label message = new Label("Email wurde gesendet");
				
				Button okButton = new Button("OK");
				VerticalLayout dialogContent = new VerticalLayout();
				dialogContent.setSizeFull();
				dialogContent.setMargin(true);
				dialog.setContent(dialogContent);
				
				dialogContent.addComponent(message);
				dialogContent.addComponent(okButton);
				dialogContent.setComponentAlignment(okButton, Alignment.BOTTOM_RIGHT);
				
				UI.getCurrent().addWindow(dialog);
				
				Mail mail = Mail.getInstance();
				mail.EmailVersand(empfaengerInput, betreffInput, nachrichtInput, anhang);
				
				okButton.addClickListener(new ClickListener()
				{	
					@Override
					public void buttonClick(ClickEvent event)
					{
						UI.getCurrent().removeWindow(dialog);
						if(lieferanten == false)
							ViewHandler.getInstance().returnToDefault();
						else
							ViewHandler.getInstance().switchView(LieferantSuche.class, new ViewDataObject<Lieferant>(lieferant));			}
				});
			}
		});
		
		verwerfen.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(lieferanten == false)
					ViewHandler.getInstance().returnToDefault();
				else
					ViewHandler.getInstance().switchView(LieferantSuche.class, new ViewDataObject<Lieferant>(lieferant));				
			}
		});
	}

	@Override
	public void getViewParam(ViewData data) {
		lieferant = (Lieferant) ((ViewDataObject<?>) data).getData();
		empfaengerInput = lieferant.getEmail();
		empfaenger.setValue(empfaengerInput);
		lieferanten = true;
		
	}
}

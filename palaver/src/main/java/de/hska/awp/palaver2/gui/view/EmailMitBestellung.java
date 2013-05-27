package de.hska.awp.palaver2.gui.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import de.hska.awp.palaver2.emailversand.Mail;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * 
 * @author Mihail Boehm
 *
 */
@SuppressWarnings("serial")
public class EmailMitBestellung extends VerticalLayout implements  
Upload.Receiver, View{
	
	private VerticalLayout		fenster = new VerticalLayout();
	
	private Label				headline;
	
	private TextField			empfaenger = new TextField("Empfänger");
	private TextField			betreff = new TextField("Betreff");
	private TextArea			nachricht = new TextArea("Nachricht");
	
	private String empfaengerInput;
	private String betreffInput;
	private String nachrichtInput;
	private String anhang = null;
	
	private Button			senden = new Button(IConstants.BUTTON_SENDEN);
	private Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);

	Panel root;         // Root element for contained components.
    Panel imagePanel;   // Panel that contains the uploaded image.
    File  file;         // File to write to.
	
	@SuppressWarnings("deprecation")
	public EmailMitBestellung() {
		super();
		this.setSizeFull();
		
		this.setMargin(true);
		
		
		root = new Panel();

        // Create the Upload component.
        final Upload upload =
                new Upload("", this);
        
        // Use a custom button caption instead of plain "Upload".
        //upload.setButtonCaption("Upload Now");
        upload.setButtonCaption("Anhang");
		
		
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
		control.setWidth("100%");
		control.setSpacing(true);
		
		control.addComponent(verwerfen);
		control.addComponent(senden);
		fenster.addComponent(control);
		
	
		control.addComponent(root);
		control.addComponent(upload);
		
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
				Mail mail = Mail.getInstance();
				mail.EmailVersand(empfaengerInput, betreffInput, nachrichtInput, anhang);
			}
		});
		
		verwerfen.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().returnToDefault();
				
			}
		});
	}

	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}

	 // Callback method to begin receiving the upload.
	@Override
    public OutputStream receiveUpload(String filename, String MIMEType) {
		FileOutputStream fos = null; // Output stream to write to
        file = new File(filename);
        this.anhang = file.getAbsolutePath();
        try {
            // Open the file for writing.
            fos = new FileOutputStream(file);
        } catch (final java.io.FileNotFoundException e) {
            // Error while opening the file. Not reported here.
            e.printStackTrace();
            return null;
        }

        return fos; // Return the output stream to write to
    }	
}

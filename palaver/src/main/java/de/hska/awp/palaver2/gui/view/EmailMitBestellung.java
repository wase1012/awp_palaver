package de.hska.awp.palaver2.gui.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
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

import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellpositionverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.emailversand.Mail;
import de.hska.awp.palaver2.excel.CreateExcelFile;
import de.hska.awp.palaver2.util.BestellungData;
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
public class EmailMitBestellung extends VerticalLayout implements View {
		//Upload.Receiver, 

	private VerticalLayout fenster = new VerticalLayout();

	private Label headline;

	private TextField empfaenger = new TextField("Empfänger");
	private TextField betreff = new TextField("Betreff");
	private TextArea nachricht = new TextArea("Nachricht");

	private String empfaengerInput;
	private String betreffInput;
	private String nachrichtInput;
	private String anhang = null;

	private Bestellung bestellung;

	private Button senden = new Button(IConstants.BUTTON_SENDEN);
	private Button verwerfen = new Button(IConstants.BUTTON_DISCARD);
	private Button download = new Button(IConstants.BUTTON_DOWNLOAD);

	@SuppressWarnings("deprecation")
	public EmailMitBestellung() {
		super();
		this.setSizeFull();

		this.setMargin(true);

		headline = new Label("Bestellung senden");
		headline.setStyleName("ViewHeadline");

		empfaenger.setWidth("45%");
		betreff.setWidth("45%");
		nachricht.setWidth("100%");
		nachricht.setHeight("250px");
		
		download.setVisible(false);

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
		control.addComponent(download);
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

		senden.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Mail mail = Mail.getInstance();
				mail.EmailVersand(empfaengerInput, betreffInput,
				nachrichtInput, anhang);
			}
		});
		
		download.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
			
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
		bestellung = (Bestellung) ((ViewDataObject<?>) data).getData();
		empfaengerInput = bestellung.getLieferant().getEmail();
		empfaenger.setValue(empfaengerInput);
		// Create excel
		CreateExcelFile cef = CreateExcelFile.getInstance();		
		this.anhang = cef.Create(bestellung);
		if(this.anhang != null)
		{
			download.setVisible(true);
			Resource res = new FileResource(new File(anhang));
			FileDownloader fd = new FileDownloader(res);
			fd.extend(download);
		}
	}

	/* Callback method to begin receiving the upload.
	@Override
	public OutputStream receiveUpload(String filename, String MIMEType) {
		// Generiren EXCEL

		// Speichern

		// Attach

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
	}*/
}

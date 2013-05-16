/**
 * Created by Sebastian
 * 17.04.2013 - 16:24:51
 */
package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.artikelverwaltung.service.Kategorienverwaltung;
import de.hska.awp.palaver2.artikelverwaltung.service.Mengeneinheitverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;
import de.hska.awp.palaver2.util.CustomDoubleValidator;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * @author Sebastian Walz
 *	Diese Klasse ist eine Eingabeform fuer das Erstellen oder Ändern eines Artikels. 
 *	Wenn die Klasse ohne Parameter aufgerufen wird, dient sie zum Erstellen, werden Parameter 
 *	uebergeben, werde die Daten automatisch in die Felder geschrieben und anstatt einen neuen 
 *	Artikel anzulegen wird er geaendert.
 */
@SuppressWarnings({ "serial", "deprecation" })
public class ArtikelErstellen extends VerticalLayout implements View
{
	private VerticalLayout		box = new VerticalLayout();
	private HorizontalLayout 	control = new HorizontalLayout();
	
	private Label				headline;
	
	private TextField			name = new TextField("Name");
	private TextField			preis = new TextField("Preis");
	private TextField			artnr = new TextField("Artikelnummer");
	private TextField			durchschnitt = new TextField("Durchschnitt");
	private TextField			bestellung = new TextField("Bestellgr�ߟe");
	
	private ComboBox			lieferant = new ComboBox("Lieferant");
	private ComboBox			mengeneinheit = new ComboBox("Mengeneinheit");
	private ComboBox			kategorie = new ComboBox("Kategorie");
	
	private CheckBox			bio = new CheckBox("Bio");
	private CheckBox			standard = new CheckBox("Standard");
	private CheckBox			grundbedarf = new CheckBox("Grundbedarf");
	private CheckBox			lebensmittel = new CheckBox("Lebensmittel");
	
	private Button				speichern = new Button(IConstants.BUTTON_SAVE);
	private Button				verwerfen = new Button(IConstants.BUTTON_DISCARD);
	private Button				addLieferant = new Button(IConstants.BUTTON_NEW);
	private Button				addMengeneinheit = new Button(IConstants.BUTTON_NEW);
	private Button				addKategorie = new Button(IConstants.BUTTON_NEW);
	private Button				update = new Button(IConstants.BUTTON_SAVE);
	
	private Artikel				artikel;
	private String				nameText;
	private String				kurzText;
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
	
	/**
	 * Der Konstruktor wird automatisch von dem ViewHandler aufgerufen. 
	 * Er erzeugt das Layout, befuellt die Comboboxen und stellt die Funktionen bereit.
	 */
	public ArtikelErstellen()
	{
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		headline = new Label("Neuer Artikel");
		headline.setStyleName("ViewHeadline");
		
		name.setWidth("100%");
		name.setImmediate(true);
		name.addValidator(new StringLengthValidator("Name zu lang oder zu kurz: {0}",2,50,false));
		
		preis.setWidth("100%");
		preis.setImmediate(true);
		preis.addValidator(new CustomDoubleValidator("Ung�ltiger Preis: {0}"));
		
		artnr.setWidth("100%");
		artnr.setImmediate(true);
//		artnr.addValidator(new StringLengthValidator("Artikelnummer zu lang oder zu kurz: {0}",2,30,false));
		
		durchschnitt.setWidth("100%");
		durchschnitt.setImmediate(true);
		durchschnitt.addValidator(new IntegerValidator("Keine Zahl! {0}"));
		
		bestellung.setWidth("100%");
		bestellung.setImmediate(true);
		bestellung.addValidator(new CustomDoubleValidator("Ung�ltige Bestellgr�ߟe: {0}"));
		
		lieferant.setWidth("100%");
		
		mengeneinheit.setWidth("100%");
		
		kategorie.setWidth("100%");
		
		durchschnitt.setEnabled(false);
		
		addLieferant.setIcon(new ThemeResource(IConstants.BUTTON_NEW_ICON));
		addMengeneinheit.setIcon(new ThemeResource(IConstants.BUTTON_NEW_ICON));
		addKategorie.setIcon(new ThemeResource(IConstants.BUTTON_NEW_ICON));
		
		box.setWidth("300px");
//		box.setHeight("100%");
		box.setSpacing(true);
		
		
		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		
		box.addComponent(headline);
		box.addComponent(name);
		box.addComponent(preis);
		
		HorizontalLayout lieferantLayout = new HorizontalLayout();
		lieferantLayout.setWidth("100%");
		lieferantLayout.addComponent(lieferant);
		lieferantLayout.addComponent(addLieferant);
		lieferantLayout.setExpandRatio(lieferant, 1);
		lieferantLayout.setComponentAlignment(addLieferant, Alignment.BOTTOM_RIGHT);
		
		box.addComponent(lieferantLayout);
		box.addComponent(artnr);
		
		HorizontalLayout mengeneinheitLayout = new HorizontalLayout();
		mengeneinheitLayout.setWidth("100%");
		mengeneinheitLayout.addComponent(mengeneinheit);
		mengeneinheitLayout.addComponent(addMengeneinheit);
		mengeneinheitLayout.setExpandRatio(mengeneinheit, 1);
		mengeneinheitLayout.setComponentAlignment(addMengeneinheit, Alignment.BOTTOM_RIGHT);
		
		box.addComponent(mengeneinheitLayout);
		
		HorizontalLayout kategorieLayout = new HorizontalLayout();
		kategorieLayout.setWidth("100%");
		kategorieLayout.addComponent(kategorie);
		kategorieLayout.addComponent(addKategorie);
		kategorieLayout.setExpandRatio(kategorie, 1);
		kategorieLayout.setComponentAlignment(addKategorie, Alignment.BOTTOM_RIGHT);
		
		box.addComponent(kategorieLayout);
		
		HorizontalLayout subBox = new HorizontalLayout();
		subBox.setWidth("100%");
		box.addComponent(subBox);
		
		subBox.addComponent(bio);
		subBox.addComponent(grundbedarf);
		
		HorizontalLayout subBox2 = new HorizontalLayout();
		subBox2.setWidth("100%");
		box.addComponent(subBox2);
		
		VerticalLayout subBox2_Vertical = new VerticalLayout();
		
		subBox2_Vertical.addComponent(standard);
		subBox2_Vertical.addComponent(lebensmittel);
		subBox2_Vertical.setSpacing(true);
		
		subBox2.addComponent(subBox2_Vertical);
		subBox2.addComponent(durchschnitt);
		
		box.addComponent(bestellung);
		
		
//		control.setWidth("100%");
		control.setSpacing(true);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		
		control.addComponent(verwerfen);
		control.addComponent(speichern);
		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		
		grundbedarf.addValueChangeListener(new ValueChangeListener() 
		{
            @Override
            public void valueChange(final ValueChangeEvent event) 
            {
            	durchschnitt.setEnabled(!durchschnitt.isEnabled());
            }
        });
		
		verwerfen.addClickListener(new ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				ViewHandler.getInstance().returnToDefault();
			}
		});
		
		speichern.addClickListener(new ClickListener()
		{			
			@Override
			public void buttonClick(ClickEvent event)
			{
				Artikel artikel = new Artikel();
				artikel.setArtikelnr(artnr.getValue());
				artikel.setBestellgroesse(Double.parseDouble(bestellung.getValue()));
				artikel.setBio(bio.getValue());
				artikel.setDurchschnitt(durchschnitt.isEnabled() ? Integer.parseInt(durchschnitt.getValue()) : 0);
				artikel.setGrundbedarf(grundbedarf.getValue());
				artikel.setKategorie((Kategorie) kategorie.getValue());
				artikel.setLebensmittel(lebensmittel.getValue());
				artikel.setLieferant((Lieferant) lieferant.getValue());
				artikel.setMengeneinheit((Mengeneinheit) mengeneinheit.getValue());
				artikel.setName(name.getValue());
				artikel.setPreis(Float.parseFloat(preis.getValue().replace(',', '.')));
				artikel.setStandard(standard.getValue());
				
				String notification = "Artikel gespeichert";
				
				try
				{
					Artikelverwaltung.getInstance().createArtikel(artikel);
				} 
				catch (ConnectException | DAOException e)
				{
					e.printStackTrace();
					notification = e.toString();
				}
				
				final Window dialog = new Window();
				dialog.setClosable(false);
				dialog.setWidth("300px");
				dialog.setHeight("150px");
				dialog.setModal(true);
				dialog.center();
				dialog.setResizable(false);
				dialog.setStyleName("dialog-window");
				
				Label message = new Label(notification);
				
				Button okButton = new Button("OK");
				
				VerticalLayout dialogContent = new VerticalLayout();
				dialogContent.setSizeFull();
				dialogContent.setMargin(true);
				dialog.setContent(dialogContent);
				
				dialogContent.addComponent(message);
				dialogContent.addComponent(okButton);
				dialogContent.setComponentAlignment(okButton, Alignment.BOTTOM_RIGHT);
				
				UI.getCurrent().addWindow(dialog);
				
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
		addLieferant.addClickListener(new ClickListener()
		{	
			@Override
			public void buttonClick(ClickEvent event)
			{
				addLieferant();
			}
		});
		addMengeneinheit.addClickListener(new ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				addMengeneinheit();
			}
		});		
		addKategorie.addClickListener(new ClickListener()
		{	
			@Override
			public void buttonClick(ClickEvent event)
			{
				addKategorie();
			}
		});
		
		load();
	}
	
	/**
	 * Befüllt die Comboboxen mit Inhalt
	 */
	private void load()
	{
		lieferant.removeAllItems();
		kategorie.removeAllItems();
		mengeneinheit.removeAllItems();
		try
		{
			List<Lieferant> lieferanten = Lieferantenverwaltung.getInstance().getAllLieferanten();
			for (Lieferant e : lieferanten)
			{
				lieferant.addItem(e);
			}
			List<Kategorie> kategorien = Kategorienverwaltung.getInstance().getAllKategories();
			for (Kategorie e : kategorien)
			{
				kategorie.addItem(e);
			}
			List<Mengeneinheit> mengen = Mengeneinheitverwaltung.getInstance().getAllMengeneinheit();
			for (Mengeneinheit e : mengen)
			{
				mengeneinheit.addItem(e);
			}
		} 
		catch (ConnectException | DAOException | SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Wenn ein Artikel uebergeben wird, werden die Felder mit den Daten gefuellt 
	 * und der Speicher-Knopf durch einen Update-Knopf ersetzt.
	 * @param ViewData - ein Artikel in einem ViewData Objekt.
	 */
	@Override
	public void getViewParam(ViewData data)
	{
		artikel = (Artikel) ((ViewDataObject<?>)data).getData();
		
		control.replaceComponent(speichern, update);
		
		update.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		update.addClickListener(new ClickListener()
		{	
			/**
			 * Wenn der Update-Knopf gedrueckt wird, wird der Artikel mit den neuen 
			 * Daten aus den Feldern Ueberschrieben; die ID bleibt. ANschliessend wird 
			 * er gespeichert und ein Dialog-Fenster oeffnet sich.
			 */
			@Override
			public void buttonClick(ClickEvent event)
			{
				artikel.setArtikelnr(artnr.getValue());
				artikel.setBestellgroesse(Double.parseDouble(bestellung.getValue()));
				artikel.setBio(bio.getValue());
				artikel.setDurchschnitt(durchschnitt.isEnabled() ? Integer.parseInt(durchschnitt.getValue()) : 0);
				artikel.setGrundbedarf(grundbedarf.getValue());
				artikel.setKategorie((Kategorie) kategorie.getValue());
				artikel.setLebensmittel(lebensmittel.getValue());
				artikel.setLieferant((Lieferant) lieferant.getValue());
				artikel.setMengeneinheit((Mengeneinheit) mengeneinheit.getValue());
				artikel.setName(name.getValue());
				artikel.setPreis(Float.parseFloat(preis.getValue().replace(',', '.')));
				artikel.setStandard(standard.getValue());
				
				String notification = "Artikel gespeichert";
				
				final Window dialog = new Window();
				dialog.setClosable(false);
				dialog.setWidth("300px");
				dialog.setHeight("150px");
				dialog.setModal(true);
				dialog.center();
				dialog.setResizable(false);
				dialog.setStyleName("dialog-window");
				
				Label message = new Label(notification);
				
				Button okButton = new Button(IConstants.BUTTON_OK);
				
				VerticalLayout dialogContent = new VerticalLayout();
				dialogContent.setSizeFull();
				dialogContent.setMargin(true);
				dialog.setContent(dialogContent);
				
				dialogContent.addComponent(message);
				dialogContent.addComponent(okButton);
				dialogContent.setComponentAlignment(okButton, Alignment.BOTTOM_RIGHT);
				
				UI.getCurrent().addWindow(dialog);
				
				okButton.addClickListener(new ClickListener()
				{	
					@Override
					public void buttonClick(ClickEvent event)
					{
						UI.getCurrent().removeWindow(dialog);
						ViewHandler.getInstance().switchView(ArtikelAnzeigen.class);
					}
				});
				
				try
				{
					Artikelverwaltung.getInstance().updateArtikel(artikel);
				} 
				catch (ConnectException | DAOException e)
				{
					e.printStackTrace();
					notification = e.toString();
				}
			}
		});
		
		/**
		 * Daten in Felder schreiben.
		 */
		headline.setValue("Artikel bearbeiten");
		
		name.setValue(artikel.getName());
		artnr.setValue(artikel.getArtikelnr());
		preis.setValue(artikel.getPreis() + "");
		lieferant.select(artikel.getLieferant());
		mengeneinheit.select(artikel.getMengeneinheit());
		kategorie.select(artikel.getKategorie());
		bestellung.setValue(artikel.getBestellgroesse() + "");
		standard.setValue(artikel.isStandard());
		grundbedarf.setValue(artikel.isGrundbedarf());
		bio.setValue(artikel.isBio());
		lebensmittel.setValue(artikel.isLebensmittel());
		durchschnitt.setValue(artikel.getDurchschnitt() + "");
	}
	
	private void addMengeneinheit()
	{
		final Window win = new Window("Neue Mengeneinheit");
		win.setModal(true);
		win.setWidth("350px");
		win.setHeight("200px");
		win.center();
		win.setResizable(false);
		
		VerticalLayout	box = new VerticalLayout();
		VerticalLayout  frame = new VerticalLayout();
		
		TextField		name = new TextField("Name");
		TextField		kurz = new TextField("Kurz");
		
		Button			speichern = new Button(IConstants.BUTTON_SAVE);
		Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
		
		name.setWidth("100%");
		kurz.setWidth("100%");
		
		frame.setSizeFull();
		
		box.setSpacing(true);		
		box.addComponent(name);
		box.addComponent(kurz);
		win.setContent(frame);
		UI.getCurrent().addWindow(win);
		frame.addComponent(box);
		frame.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
	
		HorizontalLayout control = new HorizontalLayout();
		control.setSpacing(true);
		box.setWidth("300px");
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);	
		control.addComponent(verwerfen);
		control.addComponent(speichern);
		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		
		name.setImmediate(true);
		name.setMaxLength(15);
		name.addValidator(new StringLengthValidator("Bitte gültigen Namen eingeben", 4,15, false));
		
		kurz.setImmediate(true);
		kurz.setMaxLength(4);	
		kurz.addValidator(new StringLengthValidator("Bitte gültiges Kürzel eingeben", 1,4, false));
		
		verwerfen.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().removeWindow(win);
			}
		});

		speichern.addClickListener(new ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				Mengeneinheit me = new Mengeneinheit();
				me.setName(nameText);
				me.setKurz(kurzText);
				try {
					Mengeneinheitverwaltung.getInstance().createNewMengeneinheit(me);
				} catch (ConnectException | DAOException | SQLException e) {
					throw new NullPointerException("Bitte gültige Werte eingeben");
				}
				load();
				UI.getCurrent().removeWindow(win);
			}
		});


        name.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                nameText = valueString;
            }
        });
        
        kurz.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());
                kurzText = valueString;
            }
        });
	}
	
	private void addKategorie()
	{
		final Window win = new Window("Neue Kategorie");
		win.setModal(true);
		win.setResizable(false);
		win.setWidth("350px");
		win.setHeight("200px");
		
		VerticalLayout	box = new VerticalLayout();
		VerticalLayout  frame = new VerticalLayout();
		
		TextField		name = new TextField("Name");
		
		Button			speichern = new Button(IConstants.BUTTON_SAVE);
		Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
		
		name.setWidth("100%");	
		box.setSpacing(true);
		
		frame.setSizeFull();
		win.setContent(frame);
		UI.getCurrent().addWindow(win);

		frame.addComponent(box);
		frame.setComponentAlignment(box, Alignment.MIDDLE_CENTER);		
		box.addComponent(name);
	
		HorizontalLayout control = new HorizontalLayout();
		control.setSpacing(true);
		box.setWidth("300px");
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);	
		control.addComponent(verwerfen);
		control.addComponent(speichern);
		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		
		name.setImmediate(true);
		name.setMaxLength(15);
		name.addValidator(new StringLengthValidator("Bitte gültigen Namen eingeben", 4,15, false));
		
		verwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().removeWindow(win);						
			}
		});

		speichern.addClickListener(new ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				Kategorie ka = new Kategorie();
				ka.setName(nameText);
				try {
					Kategorienverwaltung.getInstance().createNewKategorie(ka);
				} catch (ConnectException | DAOException | SQLException e) {
					throw new NullPointerException("Bitte gültige Werte eingeben");
				}
				load();
				UI.getCurrent().removeWindow(win);
			}
		});


        name.addValueChangeListener(new ValueChangeListener() {
        	
            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                nameText = valueString;
            }
        });
	}
	
	private void addLieferant()
	{
		final Window win = new Window("Neuer Lieferant");
		win.setModal(true);
		win.setResizable(false);
		win.setWidth("940px");
		win.setHeight("300px");
		win.center();
		
		HorizontalLayout	box = new HorizontalLayout();
		VerticalLayout		frame = new VerticalLayout();
		
		final TextField		name = new TextField("Name");
		TextField			bezeichnung = new TextField("Bezeichnung");
		TextField			kundennummer = new TextField("Kundennummer");
		TextField			strasse = new TextField("Staße");
		TextField			plz = new TextField("PLZ");
		TextField			ort = new TextField("Ort");
		TextField			email = new TextField("E-Mail");
		TextField			telefon = new TextField("Telefon");
		TextField			fax = new TextField("Telefax");
		TextArea			notiz = new TextArea("Notiz");
		final CheckBox		mehrereliefertermine = new CheckBox("mehrereliefertermine");
		
		final Lieferant lieferant = new Lieferant();
		final Button			speichern = new Button(IConstants.BUTTON_SAVE);
		final Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
		
		frame.addComponent(box);
		frame.setSizeFull();
		frame.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		win.setContent(frame);
		UI.getCurrent().addWindow(win);
		
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
		
		box.setWidth("900px");
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
		speichern.setEnabled(false);
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		
		name.setImmediate(true);
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
        
        	
	speichern.addClickListener(new ClickListener()
	{			
		@Override
		public void buttonClick(ClickEvent event)
		{
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
			load();
			UI.getCurrent().removeWindow(win);
		}
	});
	
	verwerfen.addClickListener(new ClickListener()
	{
		
		@Override
		public void buttonClick(ClickEvent event)
		{
			UI.getCurrent().removeWindow(win);
		}
	});
	}
}
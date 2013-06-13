/**
 * Created by Sebastian
 * 17.04.2013 - 16:24:51
 */
package de.hska.awp.palaver2.gui.view;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.artikelverwaltung.service.Kategorienverwaltung;
import de.hska.awp.palaver2.artikelverwaltung.service.Mengeneinheitverwaltung;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;
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
@SuppressWarnings({ "serial" })
public class ArtikelErstellen extends VerticalLayout implements View, ValueChangeListener
{
	private static final Logger	log	= LoggerFactory.getLogger(ArtikelErstellen.class.getName());
	
	private VerticalLayout		box = new VerticalLayout();
	private HorizontalLayout 	control = new HorizontalLayout();
	
	private Label				headline;
	
	private TextField			name = new TextField("Name");
	private TextField			preis = new TextField("Preis");
	private TextField			artnr = new TextField("Artikelnummer");
	private TextField			durchschnitt = new TextField("Durchschnitt");
	private TextField			bestellung = new TextField("Gebinde");
	private TextField			notiz = new TextField("Notiz");
	
	private NativeSelect		lieferant = new NativeSelect("Lieferant");
	private NativeSelect		mengeneinheit = new NativeSelect("Mengeneinheit");
	private NativeSelect		kategorie = new NativeSelect("Kategorie");
	
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
	private Button				meSpeichern = new Button(IConstants.BUTTON_SAVE);
	private Button				kaSpeichern = new Button(IConstants.BUTTON_SAVE);
	
	private Artikel				artikel;
	
	private TextField			mename = new TextField("Name");
	private TextField			mekurz = new TextField("Kurz");
	private TextField			kaname = new TextField("Name");
	
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
		name.setRequired(true);
		name.addValueChangeListener(this);
		
		preis.setWidth("100%");
		preis.setImmediate(true);
		
		artnr.setWidth("100%");
		artnr.setImmediate(true);
//		artnr.addValidator(new StringLengthValidator("Artikelnummer zu lang oder zu kurz: {0}",2,30,false));
		
		durchschnitt.setWidth("100%");
		durchschnitt.setImmediate(true);
		
		bestellung.setWidth("100%");
		bestellung.setImmediate(true);
		bestellung.setRequired(true);
		bestellung.addValueChangeListener(this);
		
		lieferant.setWidth("100%");
		lieferant.setRequired(true);
		lieferant.setImmediate(true);
		lieferant.addValueChangeListener(this);
		
		mengeneinheit.setWidth("100%");
		mengeneinheit.setRequired(true);
		mengeneinheit.setImmediate(true);
		mengeneinheit.addValueChangeListener(this);
		
		kategorie.setWidth("100%");
		kategorie.setRequired(true);
		kategorie.setImmediate(true);
		kategorie.addValueChangeListener(this);
		
		notiz.setWidth("100%");
		notiz.setImmediate(true);
		notiz.addValueChangeListener(this);
		
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
		box.addComponent(notiz);
		
		HorizontalLayout lieferantLayout = new HorizontalLayout();
		lieferantLayout.setWidth("100%");
		lieferantLayout.addComponent(lieferant);
		lieferantLayout.addComponent(addLieferant);
		lieferantLayout.setExpandRatio(lieferant, 1);
		lieferantLayout.setComponentAlignment(addLieferant, Alignment.BOTTOM_RIGHT);
		
		box.addComponent(lieferantLayout);
		box.addComponent(artnr);
		box.addComponent(bestellung);
		
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
		
		
//		control.setWidth("100%");
		control.setSpacing(true);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		
//		speichern.setEnabled(false);
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
					if (ArtikelErstellen.this.getParent() instanceof Window) {
					Window win = (Window) ArtikelErstellen.this.getParent();
					win.close();
				}
				else {
					ViewHandler.getInstance().returnToDefault();
				}
			}
		});
		
		speichern.addClickListener(new ClickListener()
		{			
			@Override
			public void buttonClick(ClickEvent event)
			{
				if (validiereEingabe()) {
				
				Artikel artikel = new Artikel();
				artikel.setArtikelnr(artnr.getValue());
				artikel.setBestellgroesse((bestellung.getValue() == "") ? 0.0 : Double.parseDouble(bestellung.getValue()));
				artikel.setBio(bio.getValue());
				artikel.setDurchschnitt(durchschnitt.isEnabled() ? Integer.parseInt(durchschnitt.getValue()) : 0);
				artikel.setGrundbedarf(grundbedarf.getValue());
				artikel.setKategorie((Kategorie) kategorie.getValue());
				artikel.setLebensmittel(lebensmittel.getValue());
				artikel.setLieferant((Lieferant) lieferant.getValue());
				artikel.setMengeneinheit((Mengeneinheit) mengeneinheit.getValue());
				artikel.setName(name.getValue());
				artikel.setPreis((preis.getValue() == "") ? 0F : Float.parseFloat(preis.getValue().replace(',', '.')));
				artikel.setStandard(standard.getValue());
				artikel.setNotiz(notiz.getValue());
				
				String notification = "Artikel gespeichert";
				
				try
				{
					Artikelverwaltung.getInstance().createArtikel(artikel);
				} 
				catch (Exception e)
				{
					log.error(e.toString());
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
						if (ArtikelErstellen.this.getParent() instanceof Window) {
							Window win = (Window) ArtikelErstellen.this.getParent();
							win.close();
							UI.getCurrent().removeWindow(dialog);
						}
						else {
							UI.getCurrent().removeWindow(dialog);
							ViewHandler.getInstance().switchView(ArtikelErstellen.class);
						}
					}
				});
			}
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
		catch (Exception e)
		{
			log.error(e.toString());
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
				artikel.setNotiz(notiz.getValue());
				
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
				catch (Exception e)
				{
					log.error(e.toString());
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
		notiz.setValue(artikel.getNotiz());
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
		
		Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
		
		mename.setWidth("100%");
		mekurz.setWidth("100%");
		
		frame.setSizeFull();
		
		box.setSpacing(true);		
		box.addComponent(mename);
		box.addComponent(mekurz);
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
		control.addComponent(meSpeichern);
		
		meSpeichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		
		mename.setImmediate(true);
		mename.setMaxLength(15);
		mename.setRequired(true);
		
		mekurz.setImmediate(true);
		mekurz.setMaxLength(4);	
		mekurz.setRequired(true);
		
		verwerfen.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().removeWindow(win);
			}
		});

		meSpeichern.addClickListener(new ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				if (validiereMengeneinheit()) {
					Mengeneinheit me = new Mengeneinheit();
					me.setName(mename.getValue());
					me.setKurz(mekurz.getValue());
					String notification = "Mengeneinheit gespeichert";
					try {
						Mengeneinheitverwaltung.getInstance().createMengeneinheit(me);
						UI.getCurrent().removeWindow(win);
					} catch (Exception e) {
						log.error(e.toString());
						if(e.toString().contains("INSERT INTO mengeneinheit"))
							notification = "diese Name oder dieses K�rzel sind bereits in der System vorhanden.";
						else
							notification = e.toString();
					}
					load();
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
						}
					});
				}
			}
		});

	}
	
	protected boolean validiereMengeneinheit() {
		if (mename.isValid() == false) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_MENGENEINHEITNAME);
			return false;
		}
		if (mekurz.isValid() == false) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_MENGENEINHEITKURZ);
			return false;
		}
		else {
			return true;
		}
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
		
		Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
		
		kaname.setWidth("100%");	
		box.setSpacing(true);
		
		frame.setSizeFull();
		win.setContent(frame);
		UI.getCurrent().addWindow(win);

		frame.addComponent(box);
		frame.setComponentAlignment(box, Alignment.MIDDLE_CENTER);		
		box.addComponent(kaname);
	
		HorizontalLayout control = new HorizontalLayout();
		control.setSpacing(true);
		box.setWidth("300px");
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);	
		control.addComponent(verwerfen);
		control.addComponent(kaSpeichern);
		kaSpeichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		
		kaname.setImmediate(true);
		kaname.setMaxLength(15);
		kaname.setRequired(true);
		
		verwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().removeWindow(win);						
			}
		});

		kaSpeichern.addClickListener(new ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				if (validiereKategorie()) {
					
					Kategorie ka = new Kategorie();
					ka.setName(kaname.getValue());
					String notification = "Kategorie gespeichert";
					try {
						Kategorienverwaltung.getInstance().createNewKategorie(ka);
						UI.getCurrent().removeWindow(win);
					} catch (Exception e) {
						log.error(e.toString());
						if(e.toString().contains("INSERT INTO kategorie"))
							notification = "diese Name ist bereits in der System vorhanden.";
						else
							notification = e.toString();					
					}
					load();
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
						}
					});
				}
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


	protected boolean validiereKategorie() {
		
		if (kaname.isValid() == false) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_KATEGORIENAME);
			return false;
		}
		else {
			return true;
		}
	}

	private void addLieferant() {
		final Window win = new Window("Neuer Lieferant");
		win.setModal(true);
		win.setResizable(false);
		win.setWidth("950px");
		win.setHeight("500px");
	
		LieferantErstellen le = new LieferantErstellen();
		addComponent(le);
	
		win.setContent(le);
		UI.getCurrent().addWindow(win);
		
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) {
	}
	
	private Boolean validiereEingabe() {
		if (name.getValue().toString() == "[]") {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_NAME);
			return false;
		}
		if (mengeneinheit.isValid() == false) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_MENGENEINHEIT);
			return false;
		}
		if (kategorie.getValue() == null) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_KATEGORIE);
			return false;
		}
		if (bestellung.getValue() == null) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_GEBINDE);
			return false;
		}
		else {
			return true;
		}
	}
}

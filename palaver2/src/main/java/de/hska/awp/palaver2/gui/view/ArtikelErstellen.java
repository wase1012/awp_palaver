/**
 * Created by Sebastian
 * 17.04.2013 - 16:24:51
 */
package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
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
	
	private TextField			name = new TextField("Name");
	private TextField			preis = new TextField("Preis");
	private TextField			artnr = new TextField("Artikelnummer");
	private TextField			durchschnitt = new TextField("Durchschnitt");
	private TextField			bestellung = new TextField("Bestellgröße");
	
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
	private Button				update = new Button("Update");
	
	private Artikel				artikel;
	
	/**
	 * Der Konstruktor wird automatisch von dem ViewHandler aufgerufen. 
	 * Er erzeugt das Layout, befuellt die Comboboxen und stellt die Funktionen bereit.
	 */
	public ArtikelErstellen()
	{
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		name.setWidth("100%");
		name.setImmediate(true);
		name.addValidator(new StringLengthValidator("Name zu lang oder zu kurz: {0}",2,50,false));
		
		preis.setWidth("100%");
		preis.setImmediate(true);
		preis.addValidator(new CustomDoubleValidator("Ungültiger Preis: {0}"));
		
		artnr.setWidth("100%");
		artnr.setImmediate(true);
		artnr.addValidator(new StringLengthValidator("Artikelnummer zu lang oder zu kurz: {0}",2,30,false));
		
		durchschnitt.setWidth("100%");
		durchschnitt.setImmediate(true);
		durchschnitt.addValidator(new IntegerValidator("Keine Zahl! {0}"));
		
		bestellung.setWidth("100%");
		bestellung.setImmediate(true);
		bestellung.addValidator(new CustomDoubleValidator("Ungültige Bestellgröße: {0}"));
		
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
				ViewHandler.getInstance().switchView(LieferantErstellen.class);
			}
		});
		addMengeneinheit.addClickListener(new ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				ViewHandler.getInstance().switchView(MengeneinheitErstellen.class);
			}
		});
		
		load();
	}
	
	/**
	 * Befüllt die Comboboxen mit Inhalt
	 */
	private void load()
	{
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
		update.addClickListener(new ClickListener()
		{	
			/**
			 * Wenn der Update-Knopf gedrueckt wird, wird der Artikel mit den neuen 
			 * Daten aus den Feldern überschrieben; die ID bleibt. ANschliessend wird 
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
}


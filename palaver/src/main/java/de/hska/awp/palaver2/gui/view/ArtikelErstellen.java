/**
 * Created by Sebastian
 * 17.04.2013 - 16:24:51
 */
package de.hska.awp.palaver2.gui.view;

import java.util.List;

import javax.xml.datatype.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.risto.stepper.IntStepper;

import com.google.gwt.text.shared.Parser;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
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
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * @author Sebastian Walz Diese Klasse ist eine Eingabeform fuer das Erstellen
 *         oder Ã„ndern eines Artikels. Wenn die Klasse ohne Parameter
 *         aufgerufen wird, dient sie zum Erstellen, werden Parameter
 *         uebergeben, werde die Daten automatisch in die Felder geschrieben und
 *         anstatt einen neuen Artikel anzulegen wird er geaendert.
 */
@SuppressWarnings({ "serial" })
public class ArtikelErstellen extends VerticalLayout implements View,
		ValueChangeListener {

	class Hr extends Label {
		Hr() {
			super("<hr/>", Label.CONTENT_XHTML);
		}
	}

	private static final Logger log = LoggerFactory
			.getLogger(ArtikelErstellen.class.getName());

	private VerticalLayout box = new VerticalLayout();
	private HorizontalLayout control = new HorizontalLayout();

	private Label headline;
	private Label information;
	private Label b_information;
	private Label z_information;

	private TextField name = new TextField("Artikelname");
	private TextField preis = new TextField("Preis");
	private TextField artnr = new TextField("Artikelnummer");
	private TextField kochGebinde = new TextField("Gebinde");
	private TextField bestellGebinde = new TextField("Gebinde");
	private TextField notiz = new TextField("Notiz");

	private IntStepper durchschnitt = new IntStepper("Gebindeanzahl");
	private NativeSelect lieferant = new NativeSelect("Lieferant");
	private NativeSelect mengeneinheitBesstellung = new NativeSelect(
			"Mengeneinheit");
	private NativeSelect mengeneinheitKoch = new NativeSelect("Mengeneinheit");
	private NativeSelect kategorie = new NativeSelect("Kategorie");

	private CheckBox standard = new CheckBox("Standard");
	private CheckBox grundbedarf = new CheckBox("Grundbedarf");
	private CheckBox nonfood = new CheckBox("Non-food");
	private CheckBox fuerRezepte = new CheckBox("Für Rezepte geeignet");
	private CheckBox autoBestellung = new CheckBox(
			"Automatisch bestellen (bei Grundbedarf)");

	private TextField mename = new TextField("Name");
	private TextField mekurz = new TextField("Kurz");
	private TextField kaname = new TextField("Name");

	private Button speichern = new Button(IConstants.BUTTON_SAVE);
	private Button verwerfen = new Button(IConstants.BUTTON_DISCARD);
	private Button deaktivieren = new Button(IConstants.BUTTON_DEAKTIVIEREN);
	private Button addLieferant = new Button(IConstants.BUTTON_NEW);
	private Button addMengeneinheit = new Button(IConstants.BUTTON_NEW);
	private Button addKategorie = new Button(IConstants.BUTTON_NEW);
	private Button update = new Button(IConstants.BUTTON_SAVE);
	private Button meSpeichern = new Button(IConstants.BUTTON_SAVE);
	private Button kaSpeichern = new Button(IConstants.BUTTON_SAVE);

	private Artikel artikel;
	List<Mengeneinheit> mengen;
	List<Kategorie> kategorien;
	List<Lieferant> lieferanten;

	/**
	 * Der Konstruktor wird automatisch von dem ViewHandler aufgerufen. Er
	 * erzeugt das Layout, befuellt die Comboboxen und stellt die Funktionen
	 * bereit.
	 */
	public ArtikelErstellen() {
		super();

		durchschnitt.setMaxValue(25);
		durchschnitt.setMinValue(1);
		durchschnitt.setStyleName("stepper-palaver");
		durchschnitt.setWidth("90%");
		durchschnitt.setEnabled(false);
		durchschnitt.setDescription("test");

		this.setSizeFull();
		this.setMargin(true);

		headline = new Label("Neuer Artikel");
		headline.setStyleName("ViewHeadline");
		information = new Label("Allgemeine Information");
		information.setStyleName("SubTitle");
		b_information = new Label("Information zur Bestellung");
		b_information.setStyleName("SubTitle");
		z_information = new Label("Zusätzliche Information");
		z_information.setStyleName("SubTitle");

		name.setWidth("100%");
		name.setImmediate(true);
		name.setRequired(true);
		name.addValueChangeListener(this);

		preis.setWidth("100%");
		preis.setImmediate(true);

		artnr.setWidth("100%");
		artnr.setImmediate(true);

		bestellGebinde.setWidth("95%");
		bestellGebinde.setImmediate(true);
		bestellGebinde.setRequired(true);
		bestellGebinde.addValueChangeListener(this);

		lieferant.setWidth("100%");
		lieferant.setRequired(true);
		lieferant.setImmediate(true);
		lieferant.addValueChangeListener(this);

		kochGebinde.setWidth("97%");
		kochGebinde.setRequired(false);
		kochGebinde.setEnabled(false);
		kochGebinde.setImmediate(true);
		kochGebinde.addValueChangeListener(this);
		
		mengeneinheitKoch.setWidth("100%");
		mengeneinheitKoch.setRequired(false);
		mengeneinheitKoch.addValueChangeListener(this);
		mengeneinheitKoch.setNullSelectionAllowed(false);		
	
		mengeneinheitBesstellung.setWidth("100%");
		mengeneinheitBesstellung.setRequired(true);
		mengeneinheitBesstellung.setImmediate(true);
		mengeneinheitBesstellung.addValueChangeListener(this);

		kategorie.setWidth("100%");
		kategorie.setRequired(true);
		kategorie.setImmediate(true);
		kategorie.addValueChangeListener(this);

		notiz.setWidth("100%");
		notiz.setValue("");
		notiz.setImmediate(true);
		notiz.addValueChangeListener(this);

		nonfood.setValue(false);
		autoBestellung.setVisible(false);
		autoBestellung.setValue(true);

		addLieferant.setIcon(new ThemeResource(IConstants.BUTTON_NEW_ICON));
		addMengeneinheit.setIcon(new ThemeResource(IConstants.BUTTON_NEW_ICON));
		addKategorie.setIcon(new ThemeResource(IConstants.BUTTON_NEW_ICON));

		box.setWidth("450px");
		box.setSpacing(true);

		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);

		/** START */
		box.addComponent(headline);
		/** BLOCK 1 */
		box.addComponent(information);
		box.addComponent(new Hr());

		// NAME + NONFOOD
		HorizontalLayout nameLayout = new HorizontalLayout();
		name.setWidth("95%");
		nameLayout.setWidth("100%");
		nameLayout.addComponent(name);
		nameLayout.addComponent(nonfood);
		nameLayout.setExpandRatio(name, 2);
		nameLayout.setExpandRatio(nonfood, 1);
		nameLayout.setComponentAlignment(nonfood, Alignment.BOTTOM_LEFT);

		box.addComponent(nameLayout);

		// ANDERE KOMPONENTE
		box.addComponent(artnr);
		box.addComponent(preis);
		box.addComponent(notiz);

		// KATEGORIE
		HorizontalLayout kategorieLayout = new HorizontalLayout();
		kategorieLayout.setWidth("100%");
		kategorie.setWidth("99%");
		kategorieLayout.addComponent(kategorie);
		kategorieLayout.addComponent(addKategorie);
		kategorieLayout.setExpandRatio(kategorie, 1);
		kategorieLayout.setComponentAlignment(addKategorie,
				Alignment.BOTTOM_RIGHT);
		box.addComponent(kategorieLayout);

		// Checkboxs
		isLebensmittel(nonfood.getValue());
		VerticalLayout sub_Vertical = new VerticalLayout();
		sub_Vertical.addComponent(standard);
		sub_Vertical.addComponent(grundbedarf);
		sub_Vertical.addComponent(autoBestellung);
		sub_Vertical.addComponent(fuerRezepte);
		sub_Vertical.setSpacing(true);
		box.addComponent(sub_Vertical);

		// ME für Köcher + Gebinde
		HorizontalLayout koecheLayout = new HorizontalLayout();
		isKochMittel(fuerRezepte.getValue());
		koecheLayout.setWidth("100%");
		koecheLayout.addComponent(kochGebinde);
		koecheLayout.addComponent(mengeneinheitKoch);
		koecheLayout.setExpandRatio(kochGebinde, 2);
		koecheLayout.setExpandRatio(mengeneinheitKoch, 1);
		box.addComponent(koecheLayout);

		/** BLOCK 2 */
		box.addComponent(b_information);
		box.addComponent(new Hr());
		// ME
		HorizontalLayout mengeneinheitLayout = new HorizontalLayout();
		mengeneinheitLayout.setWidth("100%");
		mengeneinheitLayout.addComponent(bestellGebinde);
		mengeneinheitLayout.addComponent(mengeneinheitBesstellung);
		mengeneinheitLayout.addComponent(addMengeneinheit);
		mengeneinheitLayout.setExpandRatio(bestellGebinde, 2);
		mengeneinheitLayout.setExpandRatio(mengeneinheitBesstellung, 1);
		mengeneinheitLayout.setComponentAlignment(addMengeneinheit,
				Alignment.BOTTOM_RIGHT);
		box.addComponent(mengeneinheitLayout);

		// Lieferant
		HorizontalLayout lieferantLayout = new HorizontalLayout();
		lieferantLayout.setWidth("100%");
		lieferantLayout.addComponent(lieferant);
		lieferantLayout.addComponent(addLieferant);
		lieferantLayout.setExpandRatio(lieferant, 1);
		lieferantLayout.setComponentAlignment(addLieferant,
				Alignment.BOTTOM_RIGHT);
		box.addComponent(lieferantLayout);

		HorizontalLayout subBox11 = new HorizontalLayout();
		subBox11.setWidth("100%");
		box.addComponent(subBox11);

		// subBox11.addComponent(durchschnitt);
		subBox11.addComponent(durchschnitt);

		/** ENDE */

		box.addComponent(new Hr());
		control.setSpacing(true);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		deaktivieren.setVisible(false);
		control.addComponent(verwerfen);
		control.addComponent(speichern);
		control.addComponent(deaktivieren);
		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		deaktivieren.setIcon(new ThemeResource(IConstants.BUTTON_DELETE_ICON));

		/**
		 * Methoden
		 */
		/* Grundbedarf checkbox */
		nonfood.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				isLebensmittel(nonfood.getValue());
			}
		});

		grundbedarf.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				durchschnitt.setEnabled(!durchschnitt.isEnabled());
				autoBestellung.setVisible(!autoBestellung.isVisible());
			}
		});

		fuerRezepte.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				isKochMittel(fuerRezepte.getValue());
			}
		});

		/* Buttons */
		verwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (ArtikelErstellen.this.getParent() instanceof Window) {
					Window win = (Window) ArtikelErstellen.this.getParent();
					win.close();
				} else {
					ViewHandler.getInstance().switchView(ArtikelAnzeigen.class,
							new ViewDataObject<Artikel>(artikel));
				}
			}
		});
		deaktivieren.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					Artikelverwaltung.getInstance().deaktivireArtikel(artikel);
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_ARTIKEL_DEAKTIVIEREN);
					ViewHandler.getInstance().switchView(ArtikelAnzeigen.class,
							new ViewDataObject<Artikel>(artikel));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		speichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					addArtickelToDataBase(0);
				}
			}
		});
		addLieferant.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addLieferant();
			}
		});
		addMengeneinheit.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addMengeneinheit();
			}
		});
		addKategorie.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addKategorie();
			}
		});
		load();
	}

	/**
	 * Befuellt die Comboboxen mit Inhalt
	 */
	private void load() {
		lieferant.removeAllItems();
		kategorie.removeAllItems();
		try {
			lieferanten = Lieferantenverwaltung.getInstance()
					.getAllLieferanten();
			for (Lieferant e : lieferanten) {
				lieferant.addItem(e);
			}
			kategorien = Kategorienverwaltung.getInstance().getAllKategories();
			for (Kategorie e : kategorien) {
				kategorie.addItem(e);
			}
			mengen = Mengeneinheitverwaltung.getInstance()
					.getAllMengeneinheit();
			setMengeneinheit(mengen);
		} catch (Exception e) {
			log.error(e.toString());
		}
	}

	private void setMengeneinheit(List<Mengeneinheit> mengen) {
		mengeneinheitBesstellung.removeAllItems();
		for (Mengeneinheit e : mengen) {
			mengeneinheitBesstellung.addItem(e);
			if (e.getName().equals("Gramm") || e.getName().equals("Milliliter")
					|| e.getName().equals("Stück")) {
				mengeneinheitKoch.addItem(e);
				mengeneinheitKoch.setValue(e);
			}
		}
		
	}

	private void addArtickelToDataBase(int action) {
		artikel = new Artikel();
		artikel.setName(name.getValue());// name
		artikel.setArtikelnr(artnr.getValue()); // nr
		artikel.setPreis((preis.getValue() == "") ? 0F : Float.parseFloat(preis
				.getValue().replace(',', '.')));// prise
		artikel.setNotiz(notiz.getValue());// Notiz
		if(grundbedarf.getValue())
			artikel.setDurchschnitt(durchschnitt.getValue()); // GebindeAnzahl
		else
			artikel.setDurchschnitt(0);

		artikel.setKategorie((Kategorie) kategorie.getValue()); // kategorie
		artikel.setLieferant((Lieferant) lieferant.getValue()); // Lieferant
		
		artikel.setMengeneinheitBestellung((Mengeneinheit) mengeneinheitBesstellung
				.getValue()); // ME_Bestellung
		
		if(fuerRezepte.isVisible()) {
			artikel.setMengeneinheitKoch((Mengeneinheit)mengeneinheitKoch.getValue());// ME_Koch
		}

		if (bestellGebinde.getValue() != "") {
			artikel.setBestellgroesse(Double.parseDouble(bestellGebinde
					.getValue()));
		} else {
			artikel.setBestellgroesse(Double.parseDouble(kochGebinde.getValue()));
		}

		artikel.setGrundbedarf(grundbedarf.getValue()); // grundbedarf
		artikel.setNonfood(nonfood.getValue()); // nonfood
		artikel.setStandard(standard.getValue());
		artikel.setAutoBestellen(autoBestellung.getValue());
		artikel.setFuerRezept(fuerRezepte.getValue());
		artikel.setDurchschnitt(Integer.parseInt(durchschnitt.getValue().toString()));
		try {
			String message = null;
			if (action == 0) {
				Artikelverwaltung.getInstance().createArtikel(artikel);
				message = "Artikel Wurde gespeichert";
			} else if (action == 1) {
				Artikelverwaltung.getInstance().updateArtikel(artikel);
				message = "Artikel Wurde geändert";
			}
			Notification notification = new Notification(message);
			notification.setDelayMsec(1000);
			notification.show(Page.getCurrent());
			ViewHandler.getInstance().switchView(ArtikelAnzeigen.class);
		} catch (Exception e) {
			log.error(e.toString());
		}
	}

	/**
	 * Wenn ein Artikel uebergeben wird, werden die Felder mit den Daten
	 * gefuellt und der Speicher-Knopf durch einen Update-Knopf ersetzt.
	 * 
	 * @param ViewData
	 *            - ein Artikel in einem ViewData Objekt.
	 */
	@Override
	public void getViewParam(ViewData data) {
		artikel = (Artikel) ((ViewDataObject<?>) data).getData();
		deaktivieren.setVisible(true);
		control.replaceComponent(speichern, update);
		update.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		update.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addArtickelToDataBase(1);
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
		kategorie.select(artikel.getKategorie());
		fuerRezepte.setValue(artikel.isFuerRezept());
		if(fuerRezepte.getValue())
			kochGebinde.setValue(artikel.getBestellgroesse() + "");
		else
			bestellGebinde.setValue(artikel.getBestellgroesse() + "");
		standard.setValue(artikel.isStandard());
		grundbedarf.setValue(artikel.isGrundbedarf());
		nonfood.setValue(artikel.isNonfood());
		durchschnitt.setValue(artikel.getDurchschnitt());
		notiz.setValue(artikel.getNotiz());
		mengeneinheitBesstellung.select(artikel.getMengeneinheitBestellung());
		mengeneinheitKoch.select(artikel.getMengeneinheitKoch());
		autoBestellung.setValue(artikel.isAutoBestellen());
	}

	private void addMengeneinheit() {
		final Window win = new Window("Neue Mengeneinheit");
		win.setModal(true);
		win.setWidth("350px");
		win.setHeight("200px");
		win.center();
		win.setResizable(false);

		VerticalLayout box = new VerticalLayout();
		VerticalLayout frame = new VerticalLayout();

		Button verwerfen = new Button(IConstants.BUTTON_DISCARD);

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

		meSpeichern.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (validiereMengeneinheit()) {
					Mengeneinheit me = new Mengeneinheit();
					me.setName(mename.getValue());
					me.setKurz(mekurz.getValue());
					String notification = "Mengeneinheit gespeichert";
					try {
						Mengeneinheitverwaltung.getInstance()
								.createMengeneinheit(me);
						UI.getCurrent().removeWindow(win);
						((Application) UI.getCurrent().getData())
								.showDialog(notification);

					} catch (Exception e) {
						log.error(e.toString());
						if (e.toString().contains("INSERT INTO mengeneinheit")) {
							notification = "Dieser Name oder dieses Kürzel ist bereits vorhanden.";
							((Application) UI.getCurrent().getData())
									.showDialog(notification);
						} else
							notification = e.toString();
					}
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
		} else {
			return true;
		}
	}

	private void addKategorie() {
		final Window win = new Window("Neue Kategorie");
		win.setModal(true);
		win.setResizable(false);
		win.setWidth("350px");
		win.setHeight("200px");

		VerticalLayout box = new VerticalLayout();
		VerticalLayout frame = new VerticalLayout();

		Button verwerfen = new Button(IConstants.BUTTON_DISCARD);

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

		kaSpeichern.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (validiereKategorie()) {

					Kategorie ka = new Kategorie();
					ka.setName(kaname.getValue());
					String notification = "Kategorie gespeichert";
					try {
						Kategorienverwaltung.getInstance().createNewKategorie(
								ka);
						UI.getCurrent().removeWindow(win);
						((Application) UI.getCurrent().getData())
								.showDialog(notification);

					} catch (Exception e) {
						log.error(e.toString());
						if (e.toString().contains("INSERT INTO kategorie")) {
							notification = "Dieser Name ist bereits vorhanden.";
							((Application) UI.getCurrent().getData())
									.showDialog(notification);
						} else
							notification = e.toString();
					}
				}
			}
		});

		verwerfen.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().removeWindow(win);
			}
		});
	}

	protected boolean validiereKategorie() {

		if (kaname.isValid() == false) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_KATEGORIENAME);
			return false;
		} else {
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

	// =========================================== //
	// ============= Helpers private ============= //
	// =========================================== //

	private void isLebensmittel(boolean check) {
		if (!check) {
			standard.setEnabled(true);
			grundbedarf.setEnabled(true);
			fuerRezepte.setEnabled(true);
			if(grundbedarf.getValue())
				autoBestellung.setEnabled(true);
		} else {
			standard.setEnabled(false);
			grundbedarf.setEnabled(false);
			fuerRezepte.setEnabled(false);
			fuerRezepte.setValue(false);
			if(grundbedarf.getValue())
				autoBestellung.setEnabled(false);
		}
	}

	private void isKochMittel(boolean check) {
		if (check) {
			kochGebinde.setEnabled(true);
			kochGebinde.setRequired(true);
			bestellGebinde.setVisible(false);
			bestellGebinde.setRequired(false);
			mengeneinheitKoch.setEnabled(true);
			mengeneinheitKoch.setRequired(true);
		} else {
			kochGebinde.setEnabled(false);
			kochGebinde.setRequired(false);
			bestellGebinde.setVisible(true);
			mengeneinheitKoch.setEnabled(false);
			mengeneinheitKoch.setRequired(false);
			bestellGebinde.setRequired(true);
		}
	}

	private Boolean validiereEingabe() {
		if (!preis.getValue().isEmpty()) {
			try {
				Double.parseDouble(preis.getValue());
			} catch (NumberFormatException e) {
				System.out.print(1);
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_ARTIKEL_PREIS);
				return false;
			}
		}
		try {
			if (!fuerRezepte.getValue())
				Double.parseDouble(bestellGebinde.getValue());
			else 
				Double.parseDouble(kochGebinde.getValue());
				
		} catch (NumberFormatException e) {
			System.out.print(2);
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_GEBINDE);
			return false;
		}
		
		if (name.getValue() == "") {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_NAME);
			return false;
		}
		if (mengeneinheitBesstellung.getValue() == null) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_MENGENEINHEIT_B);
			return false;
		}
		if (mengeneinheitKoch.getValue() == null && fuerRezepte.getValue()) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_MENGENEINHEIT_K);
			return false;
		}
		if (kochGebinde.getValue() == null && fuerRezepte.getValue()) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_GEBINDE_K);
			return false;
		}
		if (kategorie.getValue() == null) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_ARTIKEL_KATEGORIE);
			return false;
		}		
		if(!fuerRezepte.getValue()){
			if (bestellGebinde.getValue() == null
					||  Double.parseDouble(bestellGebinde.getValue().toString()) < 0.1) {
				System.out.print(bestellGebinde.getValue().toString());
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_ARTIKEL_GEBINDE);
				return false;
			} 
		} else if (fuerRezepte.getValue()) {
			if (kochGebinde.getValue() == null
					|| Double.parseDouble(kochGebinde.getValue().toString()) < 0.1) {
				System.out.print(kochGebinde.getValue().toString());
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_ARTIKEL_GEBINDE);
				return false;
			}		
		}	
		return true;

	}
}

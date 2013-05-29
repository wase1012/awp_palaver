package de.bistrosoft.palaver.gui.view;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.data.MitarbeiterDAO;
import de.bistrosoft.palaver.data.RezeptDAO;
import de.bistrosoft.palaver.data.KuchenrezeptDAO;
import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.data.ZubereitungDAO;
import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.bistrosoft.palaver.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasZubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptartverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Zubereitungverwaltung;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenrezeptHasArtikel;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Kuchenrezeptverwaltung;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.IConstants;

/**
 * @author Christine Hartkorn
 * 
 */
@SuppressWarnings("serial")
public class KuchenrezeptAnlegen extends VerticalLayout implements View,
		ValueChangeListener {

	private VerticalLayout box = new VerticalLayout();
	private HorizontalLayout hol = new HorizontalLayout();
	private VerticalLayout eins = new VerticalLayout();
	private VerticalLayout zwei = new VerticalLayout();
	private VerticalLayout dummy = new VerticalLayout();
	private HorizontalLayout control = new HorizontalLayout();

	Kuchenrezept kuchenrezept;

	@SuppressWarnings("deprecation")
	private Label ueberschrift = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Kuchenrezept anlegen</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label ueberschrift2 = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Kuchenrezept bearbeiten</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label d1 = new Label("<div>&nbsp;&nbsp;&nbsp;</div>",
			Label.CONTENT_XHTML);

	private TextField name = new TextField("Bezeichnung");
	private ComboBox mitarbeiterCb = new ComboBox("Bäcker");

	public static Table tblArtikel = new Table("Zutaten");

	private TextArea kommentar = new TextArea("Kommentar");

	private Button speichern = new Button("Speichern");
	private Button verwerfen = new Button("Verwerfen");
	private Button zutatneu = new Button("Zutaten hinzufuegen");
	private Button update = new Button(IConstants.BUTTON_SAVE);

	private String nameInput;
	private String kommentarInput;
	private String mitarbeiterInput;

	public String valueString = new String();

	private List<KuchenrezeptHasArtikel> ausgArtikel = new ArrayList<KuchenrezeptHasArtikel>();
	List<KuchenrezeptHasArtikel> artikel = new ArrayList<KuchenrezeptHasArtikel>();

	@SuppressWarnings("deprecation")
	public KuchenrezeptAnlegen() {

		super();
		this.setSizeFull();
		this.setMargin(true);

		name.setWidth("100%");
		name.setImmediate(true);
		name.setInputPrompt(nameInput);

		mitarbeiterCb.setWidth("100%");
		mitarbeiterCb.setImmediate(true);
		mitarbeiterCb.setInputPrompt(mitarbeiterInput);
		mitarbeiterCb.setNullSelectionAllowed(false);

		kommentar.setWidth("100%");
		kommentar.setImmediate(true);

		box.setWidth("300px");
		box.setSpacing(true);

		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		box.addComponent(ueberschrift);
		box.addComponent(name);
		box.addComponent(mitarbeiterCb);
		box.addComponent(hol);
		box.setComponentAlignment(hol, Alignment.MIDDLE_CENTER);
		hol.addComponent(eins);
		hol.addComponent(dummy);
		hol.addComponent(zwei);
		dummy.addComponent(d1);
		box.addComponent(tblArtikel);
		box.addComponent(zutatneu);
		box.addComponent(kommentar);

		control.setSpacing(true);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		speichern.setEnabled(false);

		control.addComponent(verwerfen);
		control.addComponent(speichern);

		tblArtikel.setSizeUndefined();
		tblArtikel.setSelectable(true);
		tblArtikel.setMultiSelect(true);
		tblArtikel.setImmediate(true);
		tblArtikel.setEditable(true);
		tblArtikel.setVisible(false);

		zutatneu.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				WinSelectKuchenArtikel window = new WinSelectKuchenArtikel(tblArtikel,
						ausgArtikel);
				UI.getCurrent().addWindow(window);
				window.setModal(true);
				window.setWidth("50%");
				window.setHeight("50%");
				tblArtikel.setVisible(true);
			}
		});

		name.addValueChangeListener(this);

		mitarbeiterCb.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
				mitarbeiterInput = valueString;
			}
		});
		kommentar.addValueChangeListener(this);

		verwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().returnToDefault();
			}
		});

		speichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				Kuchenrezept kuchenrezept = new Kuchenrezept();

				kuchenrezept.setName(nameInput);

				java.util.Date date = new java.util.Date();
				Date date2 = new Date(date.getTime());

				kuchenrezept.setErstellt(date2);

				kuchenrezept.setKommentar(kommentarInput);
				try {
					kuchenrezept.setMitarbeiter(MitarbeiterDAO
							.getInstance()
							.getMitarbeiterById(
									Long.parseLong(mitarbeiterInput.toString())));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				try {
					Kuchenrezeptverwaltung.getInstance().createKuchenrezept(kuchenrezept);

				} catch (Exception e) {
					e.printStackTrace();
				}

//				// / Liste der Zubereitungen
				Kuchenrezept rez = null;

				BeanItemContainer<KuchenrezeptHasArtikel> bicArtikel = (BeanItemContainer<KuchenrezeptHasArtikel>) tblArtikel
						.getContainerDataSource();
				ausgArtikel = bicArtikel.getItemIds();
				rez.setArtikel(ausgArtikel);

				try {
					Kuchenrezeptverwaltung.getInstance().saveArtikel(rez);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Notification notification = new Notification(
						"Rezept wurde gespeichert!");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
				ViewHandler.getInstance().switchView(
						KuchenrezeptAnzeigen.class);
			}
		});

		load();
	}

	public void load() {

		mitarbeiterCb.removeAllItems();

		try {
			List<Mitarbeiter> mitarbeiter = Mitarbeiterverwaltung.getInstance()
					.getAllMitarbeiter();
			for (Mitarbeiter e : mitarbeiter) {
				mitarbeiterCb.addItem(e.getId());
				mitarbeiterCb.setItemCaption(e.getId(), e.getVorname());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void getViewParam(ViewData data) {

		kuchenrezept = (Kuchenrezept) ((ViewDataObject<?>) data).getData();

		control.replaceComponent(speichern, update);
		box.replaceComponent(ueberschrift, ueberschrift2);

		update.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		update.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				kuchenrezept.setName(name.getValue());

				try {
					kuchenrezept.setMitarbeiter(MitarbeiterDAO
							.getInstance()
							.getMitarbeiterById(
									Long.parseLong(mitarbeiterInput.toString())));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				kuchenrezept.setKommentar(kommentar.getValue());

				// Ã„nderungsdatum erfassen
				java.util.Date date = new java.util.Date();
				Date date3 = new Date(date.getTime());
				kuchenrezept.setErstellt(date3);

				String notification = "Rezept gespeichert";

				final Window dialog = new Window();
				dialog.setClosable(false);
				dialog.setWidth("300px");
				dialog.setHeight("150px");
				dialog.setModal(true);
				dialog.center();
				dialog.setResizable(false);
				dialog.setStyleName("dialog-window");

				try {
					Kuchenrezeptverwaltung.getInstance().updateKuchenrezept(kuchenrezept);
				} catch (Exception e) {
					e.printStackTrace();
					notification = e.toString();
				} 

			}

		});

		/**
		 * Daten in Felder schreiben
		 */

		tblArtikel.setVisible(true);

		try {
			name.setValue(KuchenrezeptDAO.getInstance().getKuchenrezeptById(kuchenrezept.getId())
					.getName().toString());
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}


		try {
			mitarbeiterCb.setValue(MitarbeiterDAO.getInstance()
					.getMitarbeiterByKuchenrezept(kuchenrezept.getId()).getId());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		BeanItemContainer<KuchenrezeptHasArtikel> artikelcontainer;
		List<KuchenrezeptHasArtikel> list = new ArrayList<KuchenrezeptHasArtikel>();

		try {
			list = Kuchenrezeptverwaltung.getInstance().getAllArtikelByKuchenrezeptId1(
					kuchenrezept.getId());
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		try {
			artikelcontainer = new BeanItemContainer<KuchenrezeptHasArtikel>(
					KuchenrezeptHasArtikel.class, list);
			tblArtikel.setContainerDataSource(artikelcontainer);
			tblArtikel
					.setVisibleColumns(new Object[] { "artikelName", "menge" });
			tblArtikel.sort(new Object[] { "artikelName" },
					new boolean[] { true });
		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		}

		// ausgArtikel = list;

		kuchenrezept.setArtikel(list);

		try {
			kommentar.setValue(KuchenrezeptDAO.getInstance()
					.getKuchenrezeptById(kuchenrezept.getId()).getKommentar().toString());
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		// name.addValueChangeListener(this);
		// portion.addValueChangeListener(this);
		// zubereitung.addValueChangeListener(this);
		// geschmackCb.addValueChangeListener(this);
		// rezeptartCb.addValueChangeListener(this);
		// mitarbeiterCb.addValueChangeListener(this);
		// kommentar.addValueChangeListener(this);
		nameInput = name.getValue();
		kommentarInput = kommentar.getValue();

		if (name.getValue() == "" || name.getValue() == null
				&& mitarbeiterCb.getValue() == "" || mitarbeiterCb.getValue() == null) {
			speichern.setEnabled(false);
		} else {
			speichern.setEnabled(true);
		}

	}
}

/**
 * 
 * Jan Lauinger -> FAIL
 * 18.04.2013 - 21:21:58
 *
 */
package de.bistrosoft.palaver.gui.view;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.data.validator.StringLengthValidator;
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

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.FussnoteDAO;
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.data.MitarbeiterDAO;
import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.data.ZubereitungDAO;
import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.bistrosoft.palaver.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
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
import de.bistrosoft.palaver.util.View;
import de.bistrosoft.palaver.util.ViewData;
import de.bistrosoft.palaver.util.ViewDataObject;
import de.bistrosoft.palaver.util.ViewHandler;
import de.hska.awp.palaver2.util.IConstants;

/**
 * @author Jan Lauinger
 * 
 */
@SuppressWarnings("serial")
public class RezeptAnlegen extends VerticalLayout implements View {

	private VerticalLayout box = new VerticalLayout();
	private HorizontalLayout hol = new HorizontalLayout();
	private VerticalLayout eins = new VerticalLayout();
	private VerticalLayout zwei = new VerticalLayout();
	private VerticalLayout dummy = new VerticalLayout();
	private HorizontalLayout control = new HorizontalLayout();

	Rezept rezept;

	@SuppressWarnings("deprecation")
	private Label ueberschrift = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Rezept anlegen</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label d1 = new Label("<div>&nbsp;&nbsp;&nbsp;</div>",
			Label.CONTENT_XHTML);

	private TextField name = new TextField("Bezeichnung");
	private TextField portion = new TextField("Portion");

	private TwinColSelect zubereitung = new TwinColSelect("Zubereitung");

	private ComboBox mitarbeiterCb = new ComboBox("Koch");
	private ComboBox rezeptartCb = new ComboBox("Rezeptart");
	private ComboBox geschmackCb = new ComboBox("Geschmack");

	private CheckBox favorit = new CheckBox("Favorit");
	private CheckBox aufwand = new CheckBox("Aufwand");

	public static Table tblArtikel = new Table("Zutaten");

	private TextArea kommentar = new TextArea("Kommentar");

	private Button speichern = new Button("Speichern");
	private Button verwerfen = new Button("Verwerfen");
	private Button zutatneu = new Button("Zutaten hinzufuegen");
	private Button update = new Button(IConstants.BUTTON_SAVE);

	private String nameInput;
	private String portionInput;
	private String kommentarInput;
	private String geschmackInput;
	private String rezeptartInput;
	private String mitarbeiterInput;

	public String valueString = new String();

	private List<RezeptHasArtikel> ausgArtikel = new ArrayList<RezeptHasArtikel>();
	List<Zubereitung> listzubereitung = new ArrayList<Zubereitung>();

	List<RezeptHasArtikel> artikel = new ArrayList<>();

	// private Button btAdd = new Button("Add");

	@SuppressWarnings("deprecation")
	public RezeptAnlegen() {

		super();
		this.setSizeFull();
		this.setMargin(true);

		name.setWidth("100%");
		name.setImmediate(true);
		name.addValidator(new StringLengthValidator(
				"Bezeichnung zu lang oder zu kurz: {0}", 2, 150, false));
		name.setInputPrompt(nameInput);

		portion.setWidth("100%");
		portion.setImmediate(true);
		portion.addValidator(new IntegerValidator("Keine Zahl! {0}"));
		portion.setInputPrompt(portionInput);

		zubereitung.setWidth("100%");
		zubereitung.setImmediate(true);

		mitarbeiterCb.setWidth("100%");
		mitarbeiterCb.setImmediate(true);
		mitarbeiterCb.setInputPrompt(mitarbeiterInput);
		mitarbeiterCb.setNullSelectionAllowed(false);

		rezeptartCb.setWidth("100%");
		rezeptartCb.setImmediate(true);
		rezeptartCb.setInputPrompt(rezeptartInput);
		rezeptartCb.setNullSelectionAllowed(false);

		geschmackCb.setWidth("100%");
		geschmackCb.setImmediate(true);
		geschmackCb.setInputPrompt(geschmackInput);
		geschmackCb.setNullSelectionAllowed(false);

		kommentar.setWidth("100%");
		kommentar.setImmediate(true);
		kommentar.addValidator(new StringLengthValidator(
				"Bezeichnung zu lang oder zu kurz: {0}", 2, 1000, false));

		aufwand.setWidth("100%");
		favorit.setWidth("100%");

		box.setWidth("300px");
		box.setSpacing(true);

		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		box.addComponent(ueberschrift);
		box.addComponent(name);
		box.addComponent(portion);
		box.addComponent(mitarbeiterCb);
		box.addComponent(rezeptartCb);
		box.addComponent(geschmackCb);
		box.addComponent(zubereitung);
		box.addComponent(hol);
		box.setComponentAlignment(hol, Alignment.MIDDLE_CENTER);
		hol.addComponent(eins);
		hol.addComponent(dummy);
		hol.addComponent(zwei);
		eins.addComponent(favorit);
		zwei.addComponent(aufwand);
		dummy.addComponent(d1);
		box.addComponent(tblArtikel);
		box.addComponent(zutatneu);
		// box.addComponent(btAdd);
		box.addComponent(kommentar);

		control.setSpacing(true);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));

		control.addComponent(verwerfen);
		control.addComponent(speichern);

		tblArtikel.setSizeUndefined();
		tblArtikel.setSelectable(true);
		tblArtikel.setMultiSelect(true);
		tblArtikel.setImmediate(true);
		tblArtikel.setEditable(true);
		tblArtikel.addContainerProperty("Name", String.class, null);
		tblArtikel.addContainerProperty("Menge", Long.class, null);
		tblArtikel.setEditable(true);

		zutatneu.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				WinSelectArtikel window = new WinSelectArtikel(tblArtikel,
						ausgArtikel);
				UI.getCurrent().addWindow(window);
				window.setModal(true);
				window.setWidth("50%");
				window.setHeight("50%");

				// ar.getId(ArtikelDAO.getInstance().getArtikelById(Long.parseLong(ausgArtikel.toString()))));
				// for( String k: WinSelectArtikel.ArtId )
				// {
				//
				// artikelT.addItem(new Object[] {
				// k, 3500}, new Integer(k));
				//
				// Notification.show(k);
				// }

			}
		});

		name.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				nameInput = valueString;
			}
		});

		portion.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				portionInput = valueString;
			}
		});

		zubereitung.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());

			}
		});

		kommentar.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				kommentarInput = valueString;
			}
		});

		verwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().returnToDefault();
			}
		});
		geschmackCb.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				geschmackInput = valueString;
			}
		});
		rezeptartCb.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				rezeptartInput = valueString;
			}
		});
		mitarbeiterCb.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				mitarbeiterInput = valueString;
			}
		});

		speichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Rezept rezept = new Rezept();

				rezept.setName(nameInput);
				rezept.setAufwand(aufwand.getValue());
				rezept.setFavorit(favorit.getValue());

				java.util.Date date = new java.util.Date();
				Date date2 = new Date(date.getTime());

				rezept.setErstellt(date2);

				try {
					rezept.setGeschmack(GeschmackDAO.getInstance()
							.getGeschmackById(
									Long.parseLong(geschmackInput.toString())));
				} catch (NumberFormatException | ConnectException
						| DAOException | SQLException e1) {
					e1.printStackTrace();
				}

				// rezept.setGeschmack((Geschmack) geschmackCb.getValue());
				// rezept.setMitarbeiter((Mitarbeiter)
				// mitarbeiterCb.getValue());
				// rezept.setRezeptart((Rezeptart) rezeptartCb.getValue());

				try {
					rezept.setRezeptart(RezeptartDAO.getInstance()
							.getRezeptartById(
									Long.parseLong(rezeptartInput.toString())));
				} catch (NumberFormatException | ConnectException
						| DAOException | SQLException e1) {
					e1.printStackTrace();
				}
				rezept.setKommentar(kommentarInput);
				rezept.setPortion(Integer.parseInt(portionInput.toString()));
				try {
					rezept.setMitarbeiter(MitarbeiterDAO
							.getInstance()
							.getMitarbeiterById(
									Long.parseLong(mitarbeiterInput.toString())));
				} catch (NumberFormatException | ConnectException
						| DAOException | SQLException e1) {
					e1.printStackTrace();
				}

				try {
					Rezeptverwaltung.getInstance().createRezept(rezept);

				} catch (ConnectException | DAOException | SQLException e) {
					e.printStackTrace();
				}

				// / Liste der Zubereitungen
				Rezept rez = null;
				try {
					System.out.println(nameInput);
					rez = Rezeptverwaltung.getInstance().getRezeptByName1(
							nameInput);
				} catch (ConnectException | DAOException | SQLException e1) {
					e1.printStackTrace();
				}
				System.out.println(rez);
				if (zubereitung.getValue().toString() != "[]") {
					List<String> ZubereitungId = Arrays.asList(valueString
							.substring(1, valueString.length() - 1).split(
									"\\s*,\\s*"));

					List<RezeptHasZubereitung> zubereitunglist = new ArrayList<RezeptHasZubereitung>();

					for (String sId : ZubereitungId) {
						Long id = null;
						try {
							id = Long.parseLong(sId.trim());

						} catch (NumberFormatException nfe) {

						}

						Zubereitung zubereitung1 = null;
						try {

							zubereitung1 = Zubereitungverwaltung.getInstance()
									.getZubereitungById(id);
							//
							RezeptHasZubereitung a = new RezeptHasZubereitung(
									zubereitung1, rez);
							zubereitunglist.add(a);
						} catch (ConnectException | DAOException | SQLException e) {
							e.printStackTrace();
						}

					}
					System.out.println(zubereitunglist);
					for (RezeptHasZubereitung i : zubereitunglist) {

						try {
							Rezeptverwaltung.getInstance().ZubereitungAdd(i);
						} catch (ConnectException | DAOException | SQLException e) {

							e.printStackTrace();
						}

					}
				}

				Notification notification = new Notification(
						"Rezept wurde gespeichert!");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
				System.out.println(ausgArtikel.size());

			}
		});

		load();
	}

	public void load() {

		geschmackCb.removeAllItems();
		mitarbeiterCb.removeAllItems();
		rezeptartCb.removeAllItems();
		zubereitung.removeAllItems();

		try {
			List<Geschmack> geschmack = Geschmackverwaltung.getInstance()
					.getAllGeschmack();
			for (Geschmack e : geschmack) {
				geschmackCb.addItem(e.getId());
				geschmackCb.setItemCaption(e.getId(), e.getName());

			}

			List<Mitarbeiter> mitarbeiter = Mitarbeiterverwaltung.getInstance()
					.getAllMitarbeiter();
			for (Mitarbeiter e : mitarbeiter) {
				mitarbeiterCb.addItem(e.getId());
				mitarbeiterCb.setItemCaption(e.getId(), e.getVorname());
			}

			List<Rezeptart> rezeptart = Rezeptartverwaltung.getInstance()
					.getAllRezeptart();
			for (Rezeptart e : rezeptart) {
				rezeptartCb.addItem(e.getId());
				rezeptartCb.setItemCaption(e.getId(), e.getName());
			}

			List<Zubereitung> zb = Zubereitungverwaltung.getInstance()
					.getAllZubereitung();
			for (Zubereitung z : zb) {
				zubereitung.addItem(z.getId());
				zubereitung.setItemCaption(z.getId(), z.getName());
			}

		} catch (ConnectException | DAOException | SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void getViewParam(ViewData data) {

		rezept = (Rezept) ((ViewDataObject<?>) data).getData();

		control.replaceComponent(speichern, update);

		update.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		update.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				rezept.setName(name.getValue());

				try {
					rezept.setGeschmack(GeschmackDAO.getInstance()
							.getGeschmackById(
									Long.parseLong(geschmackInput.toString())));
				} catch (NumberFormatException | ConnectException
						| DAOException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					rezept.setRezeptart(RezeptartDAO.getInstance()
							.getRezeptartById(
									Long.parseLong(rezeptartInput.toString())));
				} catch (NumberFormatException | ConnectException
						| DAOException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					rezept.setMitarbeiter(MitarbeiterDAO
							.getInstance()
							.getMitarbeiterById(
									Long.parseLong(mitarbeiterInput.toString())));
				} catch (NumberFormatException | ConnectException
						| DAOException | SQLException e1) {
					e1.printStackTrace();
				}

				rezept.setKommentar(kommentar.getValue());
				rezept.setAufwand(aufwand.getValue());
				rezept.setFavorit(favorit.getValue());

				// Änderungsdatum erfassen
				java.util.Date date = new java.util.Date();
				Date date3 = new Date(date.getTime());
				rezept.setErstellt(date3);

				String notification = "Rezept gespeichert";

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
				dialogContent.setComponentAlignment(okButton,
						Alignment.BOTTOM_RIGHT);

				UI.getCurrent().addWindow(dialog);

				okButton.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						UI.getCurrent().removeWindow(dialog);
						ViewHandler.getInstance().switchView(
								RezeptAnlegen.class);
					}
				});

				try {
					Rezeptverwaltung.getInstance().updateRezept(rezept);
				} catch (ConnectException | DAOException e) {
					e.printStackTrace();
					notification = e.toString();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		/**
		 * Daten in Felder schreiben
		 */

		ueberschrift.setValue("Rezept bearbeiten");

		name.setValue(rezept.getName());

		try {
			listzubereitung = ZubereitungDAO.getInstance()
					.getZubereitungByRezept(rezept.getId());
		} catch (ConnectException | DAOException | SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		if (listzubereitung != null) {

			for (Zubereitung zb : listzubereitung) {

				zubereitung.select(zb.getId());
			}

		} else {

		}

		try {
			mitarbeiterCb.setValue(MitarbeiterDAO.getInstance()
					.getMitarbeiterByRezept(rezept.getId()).getId());
		} catch (ConnectException | DAOException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			geschmackCb.setValue(GeschmackDAO.getInstance()
					.getGeschmackByRezept(rezept.getId()).getId());
		} catch (ConnectException | DAOException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			rezeptartCb.setValue(RezeptartDAO.getInstance()
					.getRezeptartByRezept(rezept.getId()).getId());
		} catch (ConnectException | DAOException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		portion.setValue("30");
		kommentar.setValue(rezept.getKommentar());
		favorit.setValue(rezept.getFavorit());
		aufwand.setValue(rezept.getAufwand());

	}
}

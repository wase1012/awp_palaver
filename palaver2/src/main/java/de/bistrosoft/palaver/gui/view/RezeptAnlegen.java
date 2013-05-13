/**
 * 
 * Jan Lauinger -> FAIL
 * 18.04.2013 - 21:21:58
 *
 */
package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
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

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.data.MitarbeiterDAO;
import de.bistrosoft.palaver.data.RezeptartDAO;
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
import de.bistrosoft.palaver.util.View;
import de.bistrosoft.palaver.util.ViewData;

/**
 * @author Jan Lauinger
 * 
 */
@SuppressWarnings("serial")
public class RezeptAnlegen extends VerticalLayout implements View {

	private VerticalLayout box = new VerticalLayout();

	@SuppressWarnings("deprecation")
	private Label ueberschrift = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Rezept anlegen</font><b></pre>",
			Label.CONTENT_XHTML);

	private TextField portion = new TextField("Portion");
	private TextField name = new TextField("Bezeichnung");

	private TwinColSelect zubereitung = new TwinColSelect("Zubereitung");

	private ComboBox mitarbeiterCb = new ComboBox("Koch");
	private ComboBox rezeptartCb = new ComboBox("Rezeptart");
	private ComboBox geschmackCb = new ComboBox("Geschmack");

	public static Table tblArtikel = new Table("Zutaten");

	private TextArea kommentar = new TextArea("Kommentar");

	private Button speichern = new Button("Speichern");
	private Button verwerfen = new Button("Verwerfen");
	private Button zutatneu = new Button("Zutaten hinzufuegen");
	private String nameInput;
	private String portionInput;
	private String kommentarInput;
	private String geschmackInput;
	private String rezeptartInput;
	private String mitarbeiterInput;

//	private Artikel ar;
//	private RezeptHasArtikel rhA;
	public String valueString = new String();

	private List<RezeptHasArtikel> ausgArtikel = new ArrayList<RezeptHasArtikel>();

	List<RezeptHasArtikel> artikel = new ArrayList<>();

//	private Button btAdd = new Button("Add");

	public RezeptAnlegen() {
		super();
		this.setSizeFull();
		this.setMargin(true);
		name.setWidth("100%");
		portion.setWidth("100%");
		zubereitung.setWidth("100%");
		mitarbeiterCb.setWidth("100%");
		rezeptartCb.setWidth("100%");
		geschmackCb.setWidth("100%");
		kommentar.setWidth("100%");

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
		box.addComponent(tblArtikel);
		box.addComponent(zutatneu);
		// box.addComponent(btAdd);
		box.addComponent(kommentar);
		// ///////////////////////////////////
//		BeanItemContainer<RezeptHasArtikel> container;

		// 1L, new Mengeneinheit(), new Kategorie(), new Lieferant(), 1, "name",
		// 1, 1.00, true, true, true, 1, true
		// try {
		// container = new
		// BeanItemContainer<RezeptHasArtikel>(RezeptHasArtikel.class, artikel);
		// artikelT.setContainerDataSource(container);
		// artikelT.setVisibleColumns(new Object[] { "name", "menge", "einheit"
		// });
		// artikelT.sort(new Object[] { "name" }, new boolean[] { true });
		// } catch (IllegalArgumentException e) {
		// e.printStackTrace();
		// }
		// BeanItemContainer<Artikel> container;
		//
		// // 1L, new Mengeneinheit(), new Kategorie(), new Lieferant(), 1,
		// "name",
		// // 1, 1.00, true, true, true, 1, true
		// try {
		// container = new BeanItemContainer<Artikel>(Artikel.class, artikel);
		// artikelT.setContainerDataSource(container);
		// artikelT.setVisibleColumns(new Object[] { "name", "Menge", "Einheit"
		// });
		// artikelT.sort(new Object[] { "name" }, new boolean[] { true });
		// } catch (IllegalArgumentException e) {
		// e.printStackTrace();
		// }
		// this.addComponent(artikelT);

		// artikelT.setContainerDataSource(container);
		// btAdd.addClickListener(new ClickListener() {

		// @Override
		// public void buttonClick(ClickEvent event) {
		// Artikel b = new Artikel();
		// artikel.add(b);
		//
		// }
		// });
		HorizontalLayout control = new HorizontalLayout();
		control.setSpacing(true);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);

		name.setImmediate(true);
		name.setInputPrompt(nameInput);
		name.setMaxLength(150);

		portion.setImmediate(true);
		portion.setInputPrompt(portionInput);
		portion.setMaxLength(150);

		zubereitung.setImmediate(true);

		tblArtikel.setSizeUndefined();
		tblArtikel.setSelectable(true);
		tblArtikel.setMultiSelect(true);
		tblArtikel.setImmediate(true);
		tblArtikel.setEditable(true);
		tblArtikel.addContainerProperty("Name", String.class, null);
		tblArtikel.addContainerProperty("Menge", Long.class, null);
		tblArtikel.setEditable(true);

		geschmackCb.setImmediate(true);
		geschmackCb.setInputPrompt(geschmackInput);
		geschmackCb.setNullSelectionAllowed(false);

		rezeptartCb.setImmediate(true);
		rezeptartCb.setInputPrompt(rezeptartInput);
		geschmackCb.setNullSelectionAllowed(false);

		mitarbeiterCb.setImmediate(true);
		mitarbeiterCb.setInputPrompt(mitarbeiterInput);
		geschmackCb.setNullSelectionAllowed(false);

		kommentar.setImmediate(true);
		kommentar.setInputPrompt(kommentarInput);
		kommentar.setMaxLength(1000);

		load();

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

		control.addComponent(verwerfen);
		control.addComponent(speichern);
		speichern.setIcon(new ThemeResource("img/save.ico"));
		verwerfen.setIcon(new ThemeResource("img/cross.ico"));

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
				// RezeptHasArtikel artikel = new RezeptHasArtikel();
//				RezeptHasZubereitung rhz = new RezeptHasZubereitung();

				rezept.setName(nameInput);

				try {
					rezept.setGeschmack(GeschmackDAO.getInstance()
							.getGeschmackById(
									Long.parseLong(geschmackInput.toString())));
				} catch (NumberFormatException | ConnectException
						| DAOException | SQLException e1) {
					e1.printStackTrace();
				}

				// / Liste der Zubereitungen
				Rezept rez = null;
				try {
					rez = Rezeptverwaltung.getInstance().getRezeptByName(
							nameInput);
				} catch (ConnectException | DAOException | SQLException e1) {
					e1.printStackTrace();
				}

				if (zubereitung.getValue().toString() != "[]") {
					List<String> ZubereitungId = Arrays.asList(valueString
							.substring(1, valueString.length() - 1).split(
									"\\s*,\\s*"));
//					for (String s : ZubereitungId) {
//						// System.out.println(s);
//					}
					// valueString.split
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

					for (RezeptHasZubereitung i : zubereitunglist) {

						try {
							Rezeptverwaltung.getInstance().ZubereitungAdd(i);
						} catch (ConnectException | DAOException | SQLException e) {

							e.printStackTrace();
						}

					}
				}

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

				Notification notification = new Notification(
						"Rezept wurde gespeichert!");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
				System.out.println(ausgArtikel.size());

			}
		});
	}

	public void addArtikelMenge() {
		// RezeptHasArtikel rhA = new RezeptHasArtikel();
		// rhA.setMenge(menge);
		// tblArtikel.get
		// try {
		//
		//
		// rhA.setArtike(ArtikelDAO.getInstance().getArtikelByName(artikelInput));
		// } catch (NumberFormatException | ConnectException
		// | DAOException | SQLException e1) {
		// e1.printStackTrace();
		// }
	}

	public void load() {
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
				// mitarbeiterCb.addItem(e);
				mitarbeiterCb.addItem(e.getId());
				mitarbeiterCb.setItemCaption(e.getId(), e.getName());
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

		// public void addRezeptHasArtikel(RezeptHasArtikel artikel, Table
		// tbArtikel) {
		// // tbArtikel.getContainerDataSource()
		// }
	}

	@Override
	public void getViewParam(ViewData data) {

	}
}

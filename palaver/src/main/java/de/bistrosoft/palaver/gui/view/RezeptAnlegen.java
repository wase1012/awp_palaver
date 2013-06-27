package de.bistrosoft.palaver.gui.view;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Validator;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

import de.bistrosoft.palaver.data.RezeptDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasZubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptartverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Zubereitungverwaltung;
import de.bistrosoft.palaver.util.TwinColTouch;
import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.gui.view.ArtikelErstellen;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * @author Jan Lauinger
 * 
 */
@SuppressWarnings("serial")
public class RezeptAnlegen extends VerticalLayout implements View,
		ValueChangeListener {

	// Layouts
	private VerticalLayout vlBox = new VerticalLayout();
	private VerticalLayout vlDetailsLinks = new VerticalLayout();
	private VerticalLayout vlDetailsRechts = new VerticalLayout();

	private HorizontalLayout hlZutaten = new HorizontalLayout();
	private HorizontalLayout hlControl = new HorizontalLayout();
	private HorizontalLayout hlDetails = new HorizontalLayout();

	// Tabellen
	private Table zutatenTable;
	private FilterTable artikelTable;

	List<RezeptHasArtikel> tmpZutaten = new ArrayList<RezeptHasArtikel>();

	// BeanContainer
	private BeanItemContainer<Artikel> containerArtikel;
	private BeanItemContainer<RezeptHasArtikel> containerRezeptHasArtikel;

	// Ueberschriften
	private Label headlineAnlegen;
	private Label headlineUpdate;

	// TextFelder
	private TextField name = new TextField("Bezeichnung");

	// TwinCol
	private TwinColTouch zubereitung = new TwinColTouch("Zubereitung");

	// NativSelect
	private NativeSelect mitarbeiterNs = new NativeSelect("Koch");
	private NativeSelect rezeptartNs = new NativeSelect("Rezeptart");

	// TextArea
	private TextArea kommentar = new TextArea("Kommentar");

	// Buttons
	private Button btSpeichern = new Button(IConstants.BUTTON_SAVE);
	private Button btVerwerfen = new Button(IConstants.BUTTON_DISCARD);
	private Button btUpdate = new Button(IConstants.BUTTON_SAVE);
	private Button btMenue = new Button(IConstants.BUTTON_REZEPTMENUE);
	private Button btArtikel = new Button(
			IConstants.BUTTON_REZEPT_ARTIKEL_ANLEGEN);

	// Variablen
	private String nameInput;
	private String kommentarInput;
	public String valueString = new String();

	Rezept rezept;
	List<RezeptHasArtikel> ausgArtikel = new ArrayList<RezeptHasArtikel>();
	List<Zubereitung> listzubereitung = new ArrayList<Zubereitung>();
	List<RezeptHasArtikel> artikel = new ArrayList<RezeptHasArtikel>();

	// Konstruktor
	public RezeptAnlegen() {

		super();
		this.setSizeFull();
		this.setMargin(true);

		name.setWidth("100%");
		name.setImmediate(true);
		name.setInputPrompt(nameInput);
		name.setSizeFull();
		name.setMaxLength(200);
		name.addValidator(new StringLengthValidator(
				IConstants.INFO_REZEPT_NAME_VALID, 3, 200, false));

		zubereitung.setWidth("100%");
		zubereitung.setImmediate(true);

		mitarbeiterNs.setWidth("100%");
		mitarbeiterNs.setImmediate(true);
		mitarbeiterNs.setNullSelectionAllowed(false);

		rezeptartNs.setWidth("100%");
		rezeptartNs.setImmediate(true);
		rezeptartNs.setNullSelectionAllowed(false);
		rezeptartNs.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (rezeptartNs.getValue() == null) {
					throw new InvalidValueException(
							IConstants.INFO_REZEPT_REZEPTART_VALID);
				}
			}
		});

		kommentar.setWidth("100%");
		kommentar.setImmediate(true);
		kommentar.setMaxLength(5000);

		vlBox.setWidth("1000px");
		vlBox.setSpacing(true);

		btArtikel.setIcon(new ThemeResource(IConstants.BUTTON_NEW_ICON));

		this.addComponent(vlBox);
		this.setComponentAlignment(vlBox, Alignment.MIDDLE_CENTER);
		headlineAnlegen = new Label("Rezept anlegen");
		headlineAnlegen.setStyleName("ViewHeadline");
		vlBox.addComponent(headlineAnlegen);

		vlBox.addComponent(hlDetails);
		vlBox.addComponent(hlZutaten);
		vlBox.addComponent(hlControl);

		hlDetails.addComponent(vlDetailsLinks);
		hlDetails.addComponent(vlDetailsRechts);
		hlDetails.setWidth("1000px");
		hlDetails.setHeight("230px");

		vlBox.setComponentAlignment(headlineAnlegen, Alignment.MIDDLE_LEFT);

		vlDetailsLinks.addComponent(name);
		vlDetailsLinks.addComponent(mitarbeiterNs);
		vlDetailsLinks.addComponent(rezeptartNs);
		vlDetailsLinks.addComponent(kommentar);
		vlDetailsLinks.setWidth("450px");

		vlDetailsRechts.addComponent(zubereitung);

		vlDetailsRechts.setWidth("500px");

		hlZutaten.setWidth("1000px");
		hlZutaten.setHeight("393px");

		btSpeichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		btVerwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		btMenue.setIcon(new ThemeResource(IConstants.BUTTON_ADD_ICON));
		btSpeichern.setEnabled(true);
		btMenue.setEnabled(true);

		hlControl.addComponent(btMenue);
		hlControl.addComponent(btArtikel);
		hlControl.addComponent(btVerwerfen);
		hlControl.addComponent(btSpeichern);

		// ValueChangeListener
		name.addValueChangeListener(this);
		kommentar.addValueChangeListener(this);

		btVerwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (RezeptAnlegen.this.getParent() instanceof Window) {
					System.out
							.println("Bv RezeptAnlegen.this.getParent() aus BtVerwerfen:"
									+ RezeptAnlegen.this.getParent());
					Window win = (Window) RezeptAnlegen.this.getParent();
					win.close();
				} else {
					ViewHandler.getInstance().switchView(RezeptAnlegen.class);
				}
			}
		});

		btSpeichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				System.out
						.println("bS RezeptAnlegen.this.getParent() aus BtVerwerfen:"
								+ RezeptAnlegen.this.getParent());
				if (validiereEingabe()) {
					rezeptSpeichern();
					if (RezeptAnlegen.this.getParent() instanceof Window) {
						Window win = (Window) RezeptAnlegen.this.getParent();
						win.close();
					} else {
						ViewHandler.getInstance().switchView(
								RezeptAnzeigenTabelle.class);
					}
				}
			}
		});

		btMenue.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("rzArt : " + rezeptartNs.getValue());
				if (validiereEingabe()) {
					if (!(RezeptAnlegen.this.getParent() instanceof Window)) {
						if (rezeptartNs.getValue() != "Beilage") {
							ViewHandler.getInstance().switchView(
									MenueAnlegen.class,
									new ViewDataObject<Rezept>(
											rezeptSpeichern()));
						} else {
							((Application) UI.getCurrent().getData())
									.showDialog((IConstants.INFO_REZEPT_MENUE_SAVE));
						}
					} else {
						((Application) UI.getCurrent().getData())
								.showDialog((IConstants.INFO_REZEPT_MENUE_SAVE_WINDOW));
					}
				}
			}
		});

		btArtikel.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				addArtikel();
			}
		});

		zutatenTable = new Table();
		zutatenTable.setSizeFull();
		zutatenTable.setStyleName("palaverTable");
		// zutatenTable.setPageLength(16);
		// zutatenTable.setWidth("650px");
		zutatenTable.setImmediate(true);
		// zutatenTable.setColumnWidth("artikelname", 200);
		// zutatenTable.setColumnWidth("menge", 100);
		// zutatenTable.setColumnWidth("einheit", 90);
		// zutatenTable.setColumnWidth("notiz", 500);

		artikelTable = new FilterTable();
		artikelTable.setSizeFull();
		// artikelTable.setSizeUndefined();
		// artikelTable.setWidth("350px");
		artikelTable.setStyleName("palaverTable");
		artikelTable.setFilterBarVisible(true);
		artikelTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);
		artikelTable.setSelectable(true);

		containerRezeptHasArtikel = new BeanItemContainer<RezeptHasArtikel>(
				RezeptHasArtikel.class);
		zutatenTable.setContainerDataSource(containerRezeptHasArtikel);
		zutatenTable.setVisibleColumns(new Object[] { "artikelname", "menge",
				"einheit", "notiz" });
		zutatenTable.setEditable(true);

		hlZutaten.addComponent(zutatenTable);
		hlZutaten.addComponent(artikelTable);

		hlZutaten.setExpandRatio(artikelTable, 3);
		hlZutaten.setExpandRatio(zutatenTable, 7);

		vlBox.setComponentAlignment(hlControl, Alignment.MIDDLE_RIGHT);

		artikelTable.setCaption("Artikel");
		zutatenTable.setCaption("Zutatenliste");

		// Drag&Drop
		artikelTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Zutat loeschen und Artikel wieder in Liste setzen.
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof RezeptHasArtikel) {
					RezeptHasArtikel selected = (RezeptHasArtikel) t
							.getData("itemId");
					containerRezeptHasArtikel.removeItem(selected);
					tmpZutaten.remove(selected);
					containerArtikel.addItem(selected.getArtikel());
					artikelTable.markAsDirty();
					zutatenTable.markAsDirty();
				}
			}
		});

		zutatenTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);

		// Drag&Drop
		zutatenTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Verschiebt einen Artikel in die Zutatenliste.
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof Artikel) {
					Artikel selected = (Artikel) t.getData("itemId");
					containerArtikel.removeItem(selected);
					RezeptHasArtikel tmp = new RezeptHasArtikel(selected);
					tmpZutaten.add(tmp);
					containerRezeptHasArtikel.addItem(tmp);
				}
				artikelTable.markAsDirty();
				zutatenTable.markAsDirty();
			}
		});

		ladeArtikel();

		load();

		// Koch auf aktuellen User setzen
		mitarbeiterNs.select(((Application) UI.getCurrent().getData())
				.getUser());
	}

	private void ladeArtikel() {
		try {
			containerArtikel = new BeanItemContainer<Artikel>(Artikel.class,
					Artikelverwaltung.getInstance().getAllArtikel());
			artikelTable.setContainerDataSource(containerArtikel);
			artikelTable.setVisibleColumns(new Object[] { "name" });
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void load() {
		zubereitung.removeAllItems();

		try {
			List<Mitarbeiter> mitarbeiter = Mitarbeiterverwaltung.getInstance()
					.getAllMitarbeiter();
			for (Mitarbeiter e : mitarbeiter) {
				mitarbeiterNs.addItem(e);
			}

			List<Zubereitung> zb = Zubereitungverwaltung.getInstance()
					.getAllZubereitung();
			for (Zubereitung z : zb) {
				zubereitung.addItem(z);
			}
			List<Rezeptart> rezeptart = Rezeptartverwaltung.getInstance()
					.getAllRezeptart();
			for (Rezeptart ra : rezeptart) {
				rezeptartNs.addItem(ra);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void getViewParam(ViewData data) {
		rezept = (Rezept) ((ViewDataObject<?>) data).getData();
		if (rezept.getArtikel() != null) {
			tmpZutaten = rezept.getArtikel();
		} else {
			tmpZutaten = new ArrayList<RezeptHasArtikel>();
		}
		mitarbeiterNs.select(rezept.getMitarbeiter());
		rezeptartNs.select(rezept.getRezeptart());
		hlControl.replaceComponent(btSpeichern, btUpdate);

		headlineUpdate = new Label("Rezept bearbeiten");
		headlineUpdate.setStyleName("ViewHeadline");

		vlBox.replaceComponent(headlineAnlegen, headlineUpdate);
		vlBox.setComponentAlignment(headlineUpdate, Alignment.MIDDLE_LEFT);

		btUpdate.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		btUpdate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					update();
					ViewHandler.getInstance().switchView(
							RezeptAnzeigenTabelle.class);
				}
			}
		});

		/**
		 * Daten in Felder schreiben
		 */
		try {
			name.setValue(RezeptDAO.getInstance().getRezeptById(rezept.getId())
					.getName().toString());
		} catch (Exception e3) {
			e3.printStackTrace();
		}

		for (int i = 0; i < rezept.getZubereitung().size(); i++) {
			zubereitung.select(rezept.getZubereitung().get(i));
		}

		BeanItemContainer<RezeptHasArtikel> artikelcontainer;
		List<RezeptHasArtikel> list = new ArrayList<RezeptHasArtikel>();

		try {
			list = Rezeptverwaltung.getInstance().getAllArtikelByRezeptId1(
					rezept.getId());
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		try {
			artikelcontainer = new BeanItemContainer<RezeptHasArtikel>(
					RezeptHasArtikel.class, rezept.getArtikel());

			zutatenTable = null;
			zutatenTable = new Table();
			zutatenTable.setSizeFull();
			zutatenTable.setStyleName("palaverTable2");
			zutatenTable.setImmediate(true);
			zutatenTable.setContainerDataSource(artikelcontainer);
			zutatenTable.setVisibleColumns(new Object[] { "artikelname",
					"menge", "einheit" });
			tmpZutaten = list;
			for (RezeptHasArtikel rha : list) {
				containerArtikel.removeItem(rha.getArtikel());
				containerRezeptHasArtikel.addItem(rha);
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		}
		rezept.setArtikel(list);

		try {
			kommentar.setValue(RezeptDAO.getInstance()
					.getRezeptById(rezept.getId()).getKommentar().toString());
		} catch (ReadOnlyException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		nameInput = name.getValue();
		kommentarInput = kommentar.getValue();
	}

	// Funktion zum Speichern eines Rezeptes
	public Rezept rezeptSpeichern() {
		Rezept rezept = new Rezept();
		rezept.setName(nameInput);
		java.util.Date date = new java.util.Date();
		Date date2 = new Date(date.getTime());

		rezept.setErstellt(date2);
		rezept.setKommentar(kommentarInput);

		rezept.setRezeptart((Rezeptart) rezeptartNs.getValue());

		try {
			rezept.setMitarbeiter((Mitarbeiter) mitarbeiterNs.getValue());

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			Rezeptverwaltung.getInstance().createRezept(rezept);
			Rezept rezeptNeu = null;

			try {
				rezeptNeu = Rezeptverwaltung.getInstance().getRezeptByName1(
						nameInput);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			// Liste der Zubereitungen
			if (zubereitung.getValue().toString() != "[]") {
				List<String> ZubereitungId = Arrays.asList(zubereitung
						.getValue()
						.toString()
						.substring(1,
								zubereitung.getValue().toString().length() - 1)
						.split("\\s*,\\s*"));

				List<RezeptHasZubereitung> zubereitunglist = new ArrayList<RezeptHasZubereitung>();
				for (String sId : ZubereitungId) {
					Zubereitung zubereitung1 = new Zubereitung();
					try {
						zubereitung1 = Zubereitungverwaltung.getInstance()
								.getZubereitungByName(sId);
						RezeptHasZubereitung a = new RezeptHasZubereitung(
								zubereitung1, rezeptNeu);
						zubereitunglist.add(a);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for (RezeptHasZubereitung i : zubereitunglist) {
					try {
						Rezeptverwaltung.getInstance().ZubereitungAdd(i);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			@SuppressWarnings("unchecked")
			BeanItemContainer<RezeptHasArtikel> bicArtikel = (BeanItemContainer<RezeptHasArtikel>) zutatenTable
					.getContainerDataSource();
			ausgArtikel = bicArtikel.getItemIds();
			rezeptNeu.setArtikel(ausgArtikel);

			try {
				Rezeptverwaltung.getInstance().saveArtikel(rezeptNeu);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		((Application) UI.getCurrent().getData())
				.showDialog(IConstants.INFO_REZEPT_SAVE);

		return rezept;
	}

	// Methode zum �ndern eines Rezepts
	private void update() {
		// setzt Rezeptname
		rezept.setName(nameInput);

		// setzt �nderungsdatum
		java.util.Date date = new java.util.Date();
		Date date2 = new Date(date.getTime());
		rezept.setErstellt(date2);

		// setzt Kommentar
		rezept.setKommentar(kommentarInput);

		// setzt Rezeptart
		try {
			rezept.setRezeptart((Rezeptart) rezeptartNs.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// setzt Mitarbeiter
		try {
			rezept.setMitarbeiter((Mitarbeiter) mitarbeiterNs.getValue());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// Setze Artikel
		rezept.setArtikel(tmpZutaten);

		try {
			Rezeptverwaltung.getInstance().updateRezept(rezept);
			Rezeptverwaltung.getInstance().deleteZutatenZuRezept(rezept);
			Rezeptverwaltung.getInstance().saveArtikel(rezept);
			Rezeptverwaltung.getInstance().deleteZubereitungZuRezept(rezept);
			// TODO: Methode wie saveArtikel für Zubereitungen
			// Rezeptverwaltung.getInstance().saveZubereitungen(rezept);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Liste der Zubereitungen
		if (zubereitung.getValue().toString() != "[]") {
			List<String> ZubereitungId = Arrays.asList(zubereitung
					.getValue()
					.toString()
					.substring(1,
							zubereitung.getValue().toString().length() - 1)
					.split("\\s*,\\s*"));

			List<RezeptHasZubereitung> zubereitunglist = new ArrayList<RezeptHasZubereitung>();
			for (String sId : ZubereitungId) {
				Zubereitung zubereitung1 = new Zubereitung();
				try {
					zubereitung1 = Zubereitungverwaltung.getInstance()
							.getZubereitungByName(sId);
					RezeptHasZubereitung a = new RezeptHasZubereitung(
							zubereitung1, rezept);
					zubereitunglist.add(a);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			for (RezeptHasZubereitung i : zubereitunglist) {
				try {
					Rezeptverwaltung.getInstance().ZubereitungAdd(i);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Boolean validiereEingabe() {
		if (name.getValue().isEmpty()) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_REZEPT_NAME);
			return false;
		}
		if (mitarbeiterNs.getValue() == null) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_REZEPT_KOCH);
			return false;
		}
		if (rezeptartNs.getValue() == null) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_REZEPT_REZEPTART);
			return false;
		}
		if (tmpZutaten.isEmpty() == false || tmpZutaten.size() != 0) {
			for (RezeptHasArtikel rha : tmpZutaten) {
				if (rha.getMenge() >= 100000.0) {
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_REZEPT_MENGE);
					return false;
				}
			}
		} else {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_REZEPT_ZUTATEN);
			return false;
		}

		return true;

	}

	private void addArtikel() {
		final Window win = new Window("Neuer Artikel");
		win.setModal(true);
		win.setResizable(false);
		win.setWidth("400px");
		win.setHeight("600px");

		ArtikelErstellen ae = new ArtikelErstellen();
		addComponent(ae);

		win.setContent(ae);
		UI.getCurrent().addWindow(win);
		win.addCloseListener(new Window.CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {
				ladeArtikel();

			}
		});
	}
}

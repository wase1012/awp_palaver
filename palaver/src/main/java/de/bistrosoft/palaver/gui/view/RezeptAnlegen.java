package de.bistrosoft.palaver.gui.view;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.data.RezeptDAO;
import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasZubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptartverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Zubereitungverwaltung;
import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
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
	@SuppressWarnings("deprecation")
	private Label ueberschrift = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Rezept anlegen</font><b></pre>",
			Label.CONTENT_XHTML);
	@SuppressWarnings("deprecation")
	private Label ueberschrift2 = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Rezept bearbeiten</font><b></pre>",
			Label.CONTENT_XHTML);

	// TextFelder
	private TextField name = new TextField("Bezeichnung");

	// TwinCol
	private TwinColSelect zubereitung = new TwinColSelect("Zubereitung");

	private NativeSelect mitarbeiterNs = new NativeSelect("Koch");

	// OptionGroup
	private OptionGroup rezeptartOg = new OptionGroup();

	// TextArea
	private TextArea kommentar = new TextArea("Kommentar");

	// Buttons
	private Button btSpeichern = new Button(IConstants.BUTTON_SAVE);
	private Button btVerwerfen = new Button(IConstants.BUTTON_DISCARD);
	private Button btUpdate = new Button(IConstants.BUTTON_SAVE);
	private Button btMenue = new Button(IConstants.BUTTON_REZEPTSAVEASMENUE);

	// Variablen
	private String nameInput;
	private String kommentarInput;
	private String rezeptartInput;
	private String mitarbeiterInput;
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

		rezeptartOg.addItem("Hauptgericht");
		rezeptartOg.addItem("Beilage");

		rezeptartOg.select(2);
		rezeptartOg.setNullSelectionAllowed(false);
		rezeptartOg.setHtmlContentAllowed(true);
		rezeptartOg.setImmediate(true);

		name.setWidth("100%");
		name.setImmediate(true);
		name.setInputPrompt(nameInput);
		name.setSizeFull();
		name.setMaxLength(200);
		name.setRequired(true);

		zubereitung.setWidth("100%");
		zubereitung.setImmediate(true);

		mitarbeiterNs.setWidth("100%");
		mitarbeiterNs.setImmediate(true);
		mitarbeiterNs.setData(mitarbeiterInput);
		mitarbeiterNs.setNullSelectionAllowed(false);
		mitarbeiterNs.setRequired(true);

		kommentar.setWidth("100%");
		kommentar.setImmediate(true);
		kommentar.setMaxLength(5000);

		vlBox.setWidth("1000px");
		vlBox.setSpacing(true);

		this.addComponent(vlBox);
		vlBox.addComponent(hlDetails);
		vlBox.addComponent(hlZutaten);
		vlBox.addComponent(hlControl);

		hlDetails.addComponent(vlDetailsLinks);
		hlDetails.addComponent(vlDetailsRechts);
		hlDetails.setWidth("800px");
		hlDetails.setHeight("285px");

		vlDetailsLinks.addComponent(ueberschrift);
		vlDetailsLinks.addComponent(name);
		vlDetailsLinks.addComponent(mitarbeiterNs);
		vlDetailsLinks.addComponent(rezeptartOg);
		vlDetailsLinks.setWidth("350px");

		vlDetailsRechts.addComponent(zubereitung);
		vlDetailsRechts.addComponent(kommentar);
		vlDetailsRechts.setWidth("350px");

		hlZutaten.setWidth("1000px");
		hlZutaten.setHeight("393px");

		hlControl.setSpacing(true);
		vlBox.setComponentAlignment(hlControl, Alignment.MIDDLE_CENTER);
		btSpeichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		btVerwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		btMenue.setIcon(new ThemeResource(IConstants.BUTTON_ADD_ICON));
		btSpeichern.setEnabled(true);
		btMenue.setEnabled(true);

		hlControl.addComponent(btMenue);
		hlControl.addComponent(btVerwerfen);
		hlControl.addComponent(btSpeichern);

		// ValueChangeListener
		name.addValueChangeListener(this);
		kommentar.addValueChangeListener(this);
		rezeptartOg.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
				rezeptartInput = valueString;
			}
		});

		btVerwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(RezeptAnlegen.class);
			}
		});

		btSpeichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					rezeptSpeichern();
				}
			}
		});

		btMenue.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe() && rezeptartInput == "Hauptgericht") {
					rezeptSpeichern();
					ViewHandler.getInstance().switchView(MenueAnlegen.class);
				} else {
					showNotification(IConstants.INFO_REZEPT_MENUE_SAVE);
				}
			}
		});

		zutatenTable = new Table();
		zutatenTable.setSizeFull();
		zutatenTable.setStyleName("palaverTable");
		zutatenTable.setPageLength(16);
		zutatenTable.setImmediate(true);
		zutatenTable.setColumnWidth("artikelname", 200);
		zutatenTable.setColumnWidth("menge", 100);
		zutatenTable.setColumnWidth("einheit", 90);

		artikelTable = new FilterTable();
		artikelTable.setSizeUndefined();
		artikelTable.setStyleName("palaverTable");
		artikelTable.setFilterBarVisible(true);
		artikelTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);

		containerRezeptHasArtikel = new BeanItemContainer<RezeptHasArtikel>(
				RezeptHasArtikel.class);
		zutatenTable.setContainerDataSource(containerRezeptHasArtikel);
		zutatenTable.setVisibleColumns(new Object[] { "artikelname", "menge",
				"einheit" });
		zutatenTable.setEditable(true);

		hlZutaten.addComponent(zutatenTable);
		hlZutaten.addComponent(artikelTable);

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
				RezeptHasArtikel selected = (RezeptHasArtikel) t
						.getData("itemId");
				containerRezeptHasArtikel.removeItem(selected);
				tmpZutaten.remove(selected);
				containerArtikel.addItem(selected.getArtikel());
				artikelTable.markAsDirty();
				zutatenTable.markAsDirty();
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

		load();

		// Koch auf aktuellen User setzen
		mitarbeiterNs.select(Application.getInstance().getUser());

	}

	public void load() {
		zubereitung.removeAllItems();
		// mitarbeiterNs.removeAllItems();

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
		hlControl.replaceComponent(btSpeichern, btUpdate);
		vlDetailsLinks.replaceComponent(ueberschrift, ueberschrift2);

		btUpdate.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		btUpdate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					update();
					ViewHandler.getInstance().switchView(RezeptAnzeigenTabelle.class);
				}
			}
		});

		/**
		 * Daten in Felder schreiben
		 */
		try {
			name.setValue(RezeptDAO.getInstance().getRezeptById(rezept.getId())
					.getName().toString());
			if (rezept.getRezeptart().getName().equals("Hauptgericht")) {
				rezeptartOg.select("Hauptgericht");
			} else {
				rezeptartOg.select("Beilage");
			}
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
					RezeptHasArtikel.class);

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
				containerArtikel.removeItem(rha);
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
	public void rezeptSpeichern() {
		Rezept rezept = new Rezept();
		rezept.setName(nameInput);
		java.util.Date date = new java.util.Date();
		Date date2 = new Date(date.getTime());

		try {
			rezept.setRezeptart(Rezeptartverwaltung.getInstance()
					.getRezeptartByNameB(rezeptartInput));
		} catch (Exception e) {
			e.printStackTrace();
		}
		rezept.setErstellt(date2);
		rezept.setKommentar(kommentarInput);

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

		Notification notification = new Notification(
				IConstants.INFO_REZEPT_SAVE);
		notification.setDelayMsec(500);
		notification.show(Page.getCurrent());
		ViewHandler.getInstance().switchView(RezeptAnzeigenTabelle.class);
	}

	// private void rezeptAlsHauptgerichtSpeichern() {
	// if (ausgArtikel.isEmpty()) {
	// return;
	// }
	// Menue menue = new Menue();
	//
	// menue.setName(nameInput);
	// try {
	// menue.setKoch(MitarbeiterDAO.getInstance().getMitarbeiterById(
	// Long.parseLong(mitarbeiterInput.toString())));
	// } catch (Exception e1) {
	// e1.printStackTrace();
	// }
	//
	// try {
	// Menueverwaltung.getInstance().createRezeptAlsMenue(menue);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// private void rezeptAlsMenuSpeichern() {
	// if (ausgArtikel.isEmpty()) {
	// return;
	// }
	// MenueHasRezept mhr = new MenueHasRezept();
	// try {
	// mhr.setMenue(MenueDAO.getInstance().getMenueIdByName(nameInput));
	// } catch (Exception e1) {
	// e1.printStackTrace();
	// }
	// try {
	// mhr.setRezept(RezeptDAO.getInstance().getRezeptByName1(nameInput));
	// } catch (Exception e1) {
	// e1.printStackTrace();
	// }
	// try {
	// Menueverwaltung.getInstance().RezepteAdd(mhr);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// mhr.setHauptgericht(true);
	// Notification notification = new Notification(
	// "Rezept wurde als Menü gespeichert!");
	// notification.setDelayMsec(500);
	// notification.show(Page.getCurrent());
	// }

	// Methode zum �ndern eines Rezepts
	private void update() {
		// setzt Rezeptname
		rezept.setName(nameInput);

		// setzt Änderungsdatum
		java.util.Date date = new java.util.Date();
		Date date2 = new Date(date.getTime());
		rezept.setErstellt(date2);

		// setzt Kommentar
		rezept.setKommentar(kommentarInput);

		// setzt Rezeptart
		try {
			rezept.setRezeptart(Rezeptartverwaltung.getInstance()
					.getRezeptartByNameB(rezeptartInput));
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

	private void showNotification(String text) {
		Notification notification = new Notification(text);
		notification.setDelayMsec(500);
		notification.show(Page.getCurrent());
	}

	private Boolean validiereEingabe() {
		if (name.getValue().isEmpty()) {
			showNotification(IConstants.INFO_REZEPT_NAME);
			return false;
		}
		if (mitarbeiterNs.getValue() == null) {
			showNotification(IConstants.INFO_REZEPT_KOCH);
			return false;
		}
		if (rezeptartOg.getValue() == null) {
			showNotification(IConstants.INFO_REZEPT_REZEPTART);
			return false;
		}
		if (tmpZutaten.isEmpty() == false || tmpZutaten.size() != 0) {
			for (RezeptHasArtikel rha : tmpZutaten) {
				if (rha.getMenge() >= 100000.0) {
					showNotification(IConstants.INFO_REZEPT_MENGE);
					return false;
				}
			}
		} else {
			showNotification(IConstants.INFO_REZEPT_ZUTATEN);
			return false;
		}

		return true;

	}
}

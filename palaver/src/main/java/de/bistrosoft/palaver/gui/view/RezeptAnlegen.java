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
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.bistrosoft.palaver.data.MenueDAO;
import de.bistrosoft.palaver.data.MitarbeiterDAO;
import de.bistrosoft.palaver.data.RezeptDAO;
import de.bistrosoft.palaver.data.ZubereitungDAO;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenrezeptHasArtikel;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasRezept;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueverwaltung;
import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.bistrosoft.palaver.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasZubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
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
	private HorizontalLayout box = new HorizontalLayout();
	private VerticalLayout dummy = new VerticalLayout();
	private HorizontalLayout control = new HorizontalLayout();

	private HorizontalLayout hlRezeptdetails = new HorizontalLayout();
	private VerticalLayout vlRezeptdetailsLinks = new VerticalLayout();
	private VerticalLayout vlRezeptdetailsRechts = new VerticalLayout();
	private HorizontalLayout hlRezeptZutaten = new HorizontalLayout();

	// Tabellen
	private Table zutatenTable;
	private FilterTable artikelTable;

	// BeanContainer
	private BeanItemContainer<Artikel> containerArtikel;
	private BeanItemContainer<RezeptHasArtikel> containerRezeptHasArtikel;

	// Ueberschriften
	@SuppressWarnings("deprecation")
	private Label ueberschrift = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Rezept anlegen</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label ueberschrift2 = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Rezept bearbeiten</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label dummyl = new Label("<div>&nbsp;&nbsp;&nbsp;</div>",
			Label.CONTENT_XHTML);

	// TextFelder
	private TextField name = new TextField("Bezeichnung");
	private TextField portion = new TextField("%");

	// TwinCol
	private TwinColSelect zubereitung = new TwinColSelect("Zubereitung");

	// ComboBoxen
	private ComboBox mitarbeiterCb = new ComboBox("Koch");
	private ComboBox rezeptartCb = new ComboBox("Rezeptart");

	// CheckBox
	private CheckBox menueCbx = new CheckBox("Rezept als Menü speichern");

	// OptionGroup
	private OptionGroup rezeptartOg = new OptionGroup();

	// TextArea
	private TextArea kommentar = new TextArea("Kommentar");

	// Buttons
	private Button btSpeichern = new Button(IConstants.BUTTON_SAVE);
	private Button btVerwerfen = new Button(IConstants.BUTTON_DISCARD);
	private Button btUpdate = new Button(IConstants.BUTTON_SAVE);

	// Variablen
	private String nameInput;
	private String portionInput;
	private String kommentarInput;
	private String rezeptartInput;
	private String mitarbeiterInput;
	private String menueInput;
	private Object zutatenInput;
	public String valueString = new String();

	Rezept rezept;
	List<RezeptHasArtikel> ausgArtikel = new ArrayList<RezeptHasArtikel>();
	List<Zubereitung> listzubereitung = new ArrayList<Zubereitung>();
	List<RezeptHasArtikel> artikel = new ArrayList<RezeptHasArtikel>();

	// Konstruktor
	@SuppressWarnings("deprecation")
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

		portion.setWidth("100%");
		portion.setImmediate(true);
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

		kommentar.setWidth("100%");
		kommentar.setImmediate(true);

		box.setWidth("800px");
		box.setSpacing(true);

		this.addComponent(box);
		box.addComponent(vlRezeptdetailsLinks);
		box.addComponent(vlRezeptdetailsRechts);
		this.addComponent(hlRezeptZutaten);
		vlRezeptdetailsLinks.addComponent(ueberschrift);
		vlRezeptdetailsLinks.addComponent(name);
		vlRezeptdetailsLinks.addComponent(portion);
		vlRezeptdetailsLinks.addComponent(mitarbeiterCb);
		vlRezeptdetailsLinks.addComponent(rezeptartOg);
		vlRezeptdetailsLinks.addComponent(dummyl);
		vlRezeptdetailsLinks.addComponent(menueCbx);

		vlRezeptdetailsRechts.addComponent(zubereitung);
		vlRezeptdetailsRechts.addComponent(kommentar);

		hlRezeptZutaten.setSizeFull();

		control.setSpacing(true);
		this.addComponent(control);
		this.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		btSpeichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		btVerwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		btSpeichern.setEnabled(true);

		control.addComponent(btVerwerfen);
		control.addComponent(btSpeichern);

		// ValueChangeListener
		name.addValueChangeListener(this);
		portion.addValueChangeListener(this);
		zubereitung.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
			}
		});
		rezeptartOg.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
				rezeptartInput = valueString;
			}
		});
		mitarbeiterCb.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
				mitarbeiterInput = valueString;
			}
		});
		menueCbx.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
				menueInput = valueString;
			}
		});
		kommentar.addValueChangeListener(this);

		btVerwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().returnToDefault();
			}
		});

		btSpeichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				speichern();
			}
		});

		zutatenTable = new Table();
		zutatenTable.setSizeFull();
		zutatenTable.setStyleName("palaverTable");
		zutatenTable.setImmediate(true);

		artikelTable = new FilterTable();
		artikelTable.setSizeFull();
		artikelTable.setStyleName("palaverTable");

		artikelTable.setFilterBarVisible(true);
		artikelTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);

		containerRezeptHasArtikel = new BeanItemContainer<RezeptHasArtikel>(
				RezeptHasArtikel.class);
		zutatenTable.setContainerDataSource(containerRezeptHasArtikel);
		zutatenTable.setVisibleColumns(new Object[] { "artikelname", "menge",
				"einheit" });
		zutatenTable.setEditable(true);

		hlRezeptZutaten.addComponent(zutatenTable);
		hlRezeptZutaten.addComponent(artikelTable);

		hlRezeptZutaten.setExpandRatio(zutatenTable, 2);
		hlRezeptZutaten.setExpandRatio(artikelTable, 1);
		hlRezeptZutaten.setSpacing(true);

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

					containerRezeptHasArtikel.addItem(new RezeptHasArtikel(
							selected));
				}

				artikelTable.markAsDirty();
				zutatenTable.markAsDirty();
			}
		});

		try {
			containerArtikel = new BeanItemContainer<Artikel>(Artikel.class,
					Artikelverwaltung.getInstance().getAllArtikel());
			artikelTable.setContainerDataSource(containerArtikel);
			artikelTable
					.setVisibleColumns(new Object[] { "name", "artikelnr" });
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// ////////////

		load();
	}

	public void load() {

		mitarbeiterCb.removeAllItems();
		rezeptartCb.removeAllItems();
		zubereitung.removeAllItems();

		try {
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

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void getViewParam(ViewData data) {

		rezept = (Rezept) ((ViewDataObject<?>) data).getData();

		control.replaceComponent(btSpeichern, btUpdate);
		box.replaceComponent(ueberschrift, ueberschrift2);

		btUpdate.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		btUpdate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				speichern();
			}
			// rezept.setName(name.getValue());
			//
			// try {
			// rezept.setMitarbeiter(MitarbeiterDAO
			// .getInstance()
			// .getMitarbeiterById(
			// Long.parseLong(mitarbeiterInput.toString())));
			// } catch (Exception e1) {
			// e1.printStackTrace();
			// }
			//
			// // Ã„nderungsdatum erfassen
			// java.util.Date date = new java.util.Date();
			// Date date3 = new Date(date.getTime());
			// rezept.setErstellt(date3);
			//
			// final Window dialog = new Window();
			// dialog.setClosable(false);
			// dialog.setWidth("300px");
			// dialog.setHeight("150px");
			// dialog.setModal(true);
			// dialog.center();
			// dialog.setResizable(false);
			// dialog.setStyleName("dialog-window");
			//
			// try {
			// Rezeptverwaltung.getInstance().updateRezept(rezept);
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			//
			// try {
			// Rezeptverwaltung.getInstance().ZubereitungenDelete(rezept);
			// } catch (Exception e1) {
			// e1.printStackTrace();
			// }
			//
			// if (zubereitung.getValue().toString() != "[]") {
			// List<String> ZubereitungId = Arrays.asList(valueString
			// .substring(1, valueString.length() - 1).split(
			// "\\s*,\\s*"));
			//
			// List<RezeptHasZubereitung> zubereitunglist = new
			// ArrayList<RezeptHasZubereitung>();
			//
			// for (String sId : ZubereitungId) {
			// Long id = null;
			// try {
			// id = Long.parseLong(sId.trim());
			//
			// } catch (NumberFormatException nfe) {
			//
			// }
			//
			// Zubereitung zubereitung1 = null;
			// try {
			//
			// zubereitung1 = Zubereitungverwaltung.getInstance()
			// .getZubereitungById(id);
			// //
			// RezeptHasZubereitung a = new RezeptHasZubereitung(
			// zubereitung1, rezept);
			// zubereitunglist.add(a);
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			//
			// }
			// System.out.println(zubereitunglist);
			// for (RezeptHasZubereitung i : zubereitunglist) {
			//
			// try {
			// Rezeptverwaltung.getInstance().ZubereitungAdd(i);
			// } catch (Exception e) {
			//
			// e.printStackTrace();
			// }
			//
			// }
			// } else {
			// System.out.println("zubereitungsliste ist leer");
			// }
			//
			// Notification notification1 = new Notification(
			// "Rezept wurde geÃ¤ndert!");
			// notification1.setDelayMsec(500);
			// notification1.show(Page.getCurrent());
			// ViewHandler.getInstance().switchView(
			// RezeptAnzeigenTabelle.class);
			// }

		});

		/**
		 * Daten in Felder schreiben
		 */
		System.out.println("name zu rezept: " + name);
		try {
			name.setValue(RezeptDAO.getInstance().getRezeptById(rezept.getId())
					.getName().toString());
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		System.out.println("name zu rezept nach ca: " + name);

		try {
			listzubereitung = ZubereitungDAO.getInstance()
					.getZubereitungByRezept(rezept.getId());
		} catch (Exception e2) {
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
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		BeanItemContainer<RezeptHasArtikel> artikelcontainer;
		List<RezeptHasArtikel> list = new ArrayList<RezeptHasArtikel>();

		try {
			list = Rezeptverwaltung.getInstance().getAllArtikelByRezeptId1(
					rezept.getId());
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		// try {
		// artikelcontainer = new BeanItemContainer<KuchenrezeptHasArtikel>(
		// KuchenrezeptHasArtikel.class);
		//
		// zutatenTable = null;
		// zutatenTable = new Table();
		// zutatenTable.setSizeFull();
		// zutatenTable.setStyleName("palaverTable2");
		// zutatenTable.setImmediate(true);
		//
		// zutatenTable.setContainerDataSource(artikelcontainer);
		// zutatenTable.setVisibleColumns(new Object[] { "artikelname", "menge",
		// "einheit" });
		//
		// for(KuchenrezeptHasArtikel kha : list){
		// containerArtikel.removeItem(kha);
		// System.out.println(kha.getArtikelname());
		// containerKuchenrezeptHasArtikel.addItem(kha);
		// }
		//
		// } catch (IllegalArgumentException e) {
		// e.printStackTrace();
		//
		// }
		//
		// kuchenrezept.setArtikel(list);

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

			for (RezeptHasArtikel rha : list) {
				containerArtikel.removeItem(rha);
				System.out.println(rha.getArtikelname());
				containerRezeptHasArtikel.addItem(rha);
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		}
		rezept.setArtikel(list);

		// rezeptart.setValue(RezeptDAO.getInstance().getRezeptById(rezept.getId()).getRezeptart().);

		portion.setValue("100");
		try {
			kommentar.setValue(RezeptDAO.getInstance()
					.getRezeptById(rezept.getId()).getKommentar().toString());
		} catch (ReadOnlyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//
	// System.out.println("zutaten TAbelle vor container: " + zutatenTable );
	// try {
	// artikelcontainer = new BeanItemContainer<RezeptHasArtikel>(
	// RezeptHasArtikel.class, list);
	// System.out.println("artikelcontainer vor set in container: " +
	// artikelcontainer );
	// System.out.println("zutatenTable vor set vor container: " + zutatenTable
	// );
	//
	// zutatenTable.setContainerDataSource(artikelcontainer);
	// System.out.println("zutatenTable vor set nach container: " + zutatenTable
	// );
	//
	// zutatenTable.setVisibleColumns(new Object[] { "artikelname",
	// "menge", "einheit" });
	// System.out.println("artikelcontainer in container: " + artikelcontainer
	// );
	//
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	//
	// }
	// System.out.println("artikelcontainer nach container: " + zutatenTable );
	//
	//
	// rezept.setArtikel(list);
	//
	// portion.setValue("100");
	//
	// try {
	// kommentar.setValue(RezeptDAO.getInstance()
	// .getRezeptById(rezept.getId()).getKommentar().toString());
	// } catch (Exception e3) {
	// e3.printStackTrace();
	// }
	// }

	@Override
	public void valueChange(ValueChangeEvent event) {
		// name.addValueChangeListener(this);
		// portion.addValueChangeListener(this);
		// zubereitung.addValueChangeListener(this);
		// geschmackCb.addValueChangeListener(this);
		// rezeptartCb.addValueChangeListener(this);
		// mitarbeiterCb.addValueChangeListener(this);
		// kommentar.addValueChangeListener(this);
		nameInput = name.getValue();
		portionInput = portion.getValue();
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
		rezept.setPortion(Integer.parseInt(portionInput.toString()));
		try {
			rezept.setMitarbeiter(MitarbeiterDAO.getInstance()
					.getMitarbeiterById(
							Long.parseLong(mitarbeiterInput.toString())));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// Liste der Zubereitungen
		Rezept rez = null;
		try {
			rez = Rezeptverwaltung.getInstance().getRezeptByName1(nameInput);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (zubereitung.getValue().toString() != "[]") {
			List<String> ZubereitungId = Arrays.asList(valueString.substring(1,
					valueString.length() - 1).split("\\s*,\\s*"));

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
		String aus = "1";
		try {
			if (aus == "2") {
				aus = "1";
				return;
			} else {
				Rezeptverwaltung.getInstance().createRezept(rezept);
				System.out
						.println("rezeptAnlegen nach speichern createRezept id: "
								+ rezept.getId());

			}
			try {
				rez = Rezeptverwaltung.getInstance().getRezeptByName1(nameInput);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			@SuppressWarnings("unchecked")
			BeanItemContainer<RezeptHasArtikel> bicArtikel = (BeanItemContainer<RezeptHasArtikel>) zutatenTable
					.getContainerDataSource();
			System.out.println("ausgArtikel vor bicArtikel: " + ausgArtikel);
			ausgArtikel = bicArtikel.getItemIds();
			System.out.println("ausgArtikel: " + ausgArtikel);
			System.out.println("rez: " + rez);
			rez.setArtikel(ausgArtikel);

			if (ausgArtikel.isEmpty()) {
				Notification notification = new Notification(
						"Bitte Zutaten eintragen");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
				aus = "2";
				return;
			}



			try {
				System.out.println("rezeptAnlegen speichern id: "
						+ rezept.getId());
				Rezeptverwaltung.getInstance().saveArtikel(rez);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Notification notification = new Notification(
				"Rezept wurde gespeichert!");
		notification.setDelayMsec(500);
		notification.show(Page.getCurrent());
		ViewHandler.getInstance().switchView(RezeptAnzeigenTabelle.class);
	}

	private void speichern() {
		if (menueInput == "true") {
			if (rezeptartInput == "Hauptgericht") {
				if (nameInput == "" || portionInput == null
						|| mitarbeiterInput == null || rezeptartInput == null) {
					Notification notification = new Notification(
							"Bitte alle Felder befüllen");
					notification.setDelayMsec(500);
					notification.show(Page.getCurrent());
				} else {
					rezeptSpeichern();
					rezeptAlsMenuSpeichern();
					rezeptAlsHauptgerichtSpeichern();
					System.out.println("Rezept wurde als Menü gespeichert");
				}
			} else {
				Notification notification = new Notification(
						"Rezept für Menue nur als Hauptgericht speicherbar!");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
			}
		} else {
			if (nameInput == "" || portionInput == null
					|| mitarbeiterInput == null || rezeptartInput == null) {
				Notification notification = new Notification(
						"Bitte alle Felder befüllen");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
			} else {
				// System.out.println("rezeptAnlegen in Methode speichern ID: "
				// + rezept.getId());
				rezeptSpeichern();
				// Plausibiltätasprüfung für Zutatenliste läuft in der Methode
				// Speichern
			}
		}
	}

	private void rezeptAlsHauptgerichtSpeichern() {
		if (ausgArtikel.isEmpty()) {
			return;
		}
		Menue menue = new Menue();

		menue.setName(nameInput);
		try {
			menue.setKoch(MitarbeiterDAO.getInstance().getMitarbeiterById(
					Long.parseLong(mitarbeiterInput.toString())));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			Menueverwaltung.getInstance().createRezeptAlsMenue(menue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void rezeptAlsMenuSpeichern() {
		if (ausgArtikel.isEmpty()) {
			return;
		}
		MenueHasRezept mhr = new MenueHasRezept();
		try {
			mhr.setMenue(MenueDAO.getInstance().getMenueIdByName(nameInput));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			mhr.setRezept(RezeptDAO.getInstance().getRezeptByName1(nameInput));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			Menueverwaltung.getInstance().RezepteAdd(mhr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mhr.setHauptgericht(true);
		Notification notification = new Notification(
				"Rezept wurde als Menü gespeichert!");
		notification.setDelayMsec(500);
		notification.show(Page.getCurrent());
	}

}

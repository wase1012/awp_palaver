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
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.bistrosoft.palaver.data.MitarbeiterDAO;
import de.bistrosoft.palaver.data.KuchenrezeptDAO;
import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.bistrosoft.palaver.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
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
	private HorizontalLayout hlRezeptZutaten = new HorizontalLayout();

	private Table zutatenTable;
	private FilterTable artikelTable;
	
	private BeanItemContainer<Artikel> containerArtikel;
	private BeanItemContainer<KuchenrezeptHasArtikel> containerKuchenrezeptHasArtikel;
	
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
	private ComboBox mitarbeiterCb = new ComboBox("B�cker");

	private TextArea kommentar = new TextArea("Kommentar");

	private Button btSpeichern = new Button("Speichern");
	private Button verwerfen = new Button("Verwerfen");
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

//		kommentar.setWidth("100%");
		kommentar.setImmediate(true);

		box.setWidth("90%");
		box.setSpacing(true);

		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		box.addComponent(ueberschrift);
		ueberschrift.setWidth("300px");
		box.setComponentAlignment(ueberschrift, Alignment.MIDDLE_CENTER);
		box.addComponent(name);
		name.setWidth("300px");
		box.setComponentAlignment(name, Alignment.MIDDLE_CENTER);
		box.addComponent(mitarbeiterCb);
		mitarbeiterCb.setWidth("300px");
		box.setComponentAlignment(mitarbeiterCb, Alignment.MIDDLE_CENTER);
//		box.addComponent(hol);
//		box.setComponentAlignment(hol, Alignment.MIDDLE_CENTER);
//		hol.addComponent(eins);
//		hol.addComponent(dummy);
//		hol.addComponent(zwei);
//		dummy.addComponent(d1);
		box.addComponent(hlRezeptZutaten);
		box.setComponentAlignment(hlRezeptZutaten, Alignment.MIDDLE_CENTER);
		box.addComponent(kommentar);

		control.setSpacing(true);
		this.addComponent(control);
		this.setComponentAlignment(control, Alignment.BOTTOM_RIGHT);
		btSpeichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		btSpeichern.setEnabled(false);

		control.addComponent(verwerfen);
		control.addComponent(btSpeichern);

		name.addValueChangeListener(this);

		mitarbeiterCb.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
				mitarbeiterInput = valueString;
			}
		});
		kommentar.setHeight("70px");
		kommentar.setWidth("300px");
		box.setComponentAlignment(kommentar, Alignment.MIDDLE_CENTER);
		kommentar.addValueChangeListener(this);

		verwerfen.addClickListener(new ClickListener() {
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
		
//		hlRezeptZutaten.setSizeFull();
		hlRezeptZutaten.setWidth("100%");
		hlRezeptZutaten.setHeight("200px");		
		
		zutatenTable = new Table();
		zutatenTable.setSizeFull();
		zutatenTable.setStyleName("palaverTable2");
		zutatenTable.setImmediate(true);

		artikelTable = new FilterTable();
		artikelTable.setSizeFull();
		artikelTable.setStyleName("palaverTable2");

		artikelTable.setFilterBarVisible(true);
		artikelTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);

		containerKuchenrezeptHasArtikel = new BeanItemContainer<KuchenrezeptHasArtikel>(
				KuchenrezeptHasArtikel.class);
		zutatenTable.setContainerDataSource(containerKuchenrezeptHasArtikel);
		zutatenTable.setVisibleColumns(new Object[] { "artikelname","menge", "einheit" });
		zutatenTable.setEditable(true);

		/**
		 * Drag n Drop
		 */
		artikelTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Bestellposition loeschen und Artikel wieder in Liste setzen.
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				KuchenrezeptHasArtikel selected = (KuchenrezeptHasArtikel) t
						.getData("itemId");
				containerKuchenrezeptHasArtikel.removeItem(selected);
				containerArtikel.addItem(selected.getArtikel());
				artikelTable.markAsDirty();
				zutatenTable.markAsDirty();
			}
		});

		zutatenTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		/**
		 * Drag n Drop
		 */
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

					containerKuchenrezeptHasArtikel.addItem(new KuchenrezeptHasArtikel(
							selected));
				}

				artikelTable.markAsDirty();
				zutatenTable.markAsDirty();
			}
		});

		hlRezeptZutaten.addComponent(zutatenTable);
		hlRezeptZutaten.addComponent(artikelTable);

		hlRezeptZutaten.setExpandRatio(zutatenTable, 3);
		hlRezeptZutaten.setExpandRatio(artikelTable, 2);
		hlRezeptZutaten.setSpacing(true);
		
		artikelTable.setCaption("Artikel");
		zutatenTable.setCaption("Zutatenliste");

		try {
			containerArtikel = new BeanItemContainer<Artikel>(Artikel.class,
					Artikelverwaltung.getInstance().getAllArtikel());
			artikelTable.setContainerDataSource(containerArtikel);
			artikelTable
					.setVisibleColumns(new Object[] { "name", "artikelnr" });
		} catch (IllegalArgumentException e) {
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

		control.replaceComponent(btSpeichern, update);
		box.replaceComponent(ueberschrift, ueberschrift2);
		ueberschrift2.setWidth("300px");
		box.setComponentAlignment(ueberschrift2, Alignment.MIDDLE_CENTER);

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

//				kuchenrezept.setKommentar(kommentar.getValue());

				// Änderungsdatum erfassen
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
				
				Notification notification1 = new Notification(
						"Rezept wurde ge�ndert!");
				notification1.setDelayMsec(500);
				notification1.show(Page.getCurrent());
				ViewHandler.getInstance().switchView(
						KuchenrezeptAnzeigen.class);

			}

		});

		/**
		 * Daten in Felder schreiben
		 */

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
			System.out.println(list);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		try {
			artikelcontainer = new BeanItemContainer<KuchenrezeptHasArtikel>(
					KuchenrezeptHasArtikel.class);
			
			zutatenTable = null;
			zutatenTable = new Table();
			zutatenTable.setSizeFull();
			zutatenTable.setStyleName("palaverTable2");
			zutatenTable.setImmediate(true);
			
			zutatenTable.setContainerDataSource(artikelcontainer);
			zutatenTable.setVisibleColumns(new Object[] { "artikelname", "menge", "einheit" });
			
			for(KuchenrezeptHasArtikel kha : list){
				containerArtikel.removeItem(kha);
				System.out.println(kha.getArtikelname());
				containerKuchenrezeptHasArtikel.addItem(kha);				
			}
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		}

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
			btSpeichern.setEnabled(false);
		} else {
			btSpeichern.setEnabled(true);
		}

	}
	
	
	private void speichern(){
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


		Kuchenrezept rez = null;
		try {
			System.out.println(nameInput);
			rez = Kuchenrezeptverwaltung.getInstance().getKuchenrezeptByName1(
					nameInput);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

//			@SuppressWarnings("unchecked")
		BeanItemContainer<KuchenrezeptHasArtikel> bicArtikel = (BeanItemContainer<KuchenrezeptHasArtikel>) zutatenTable.getContainerDataSource();
		ausgArtikel = bicArtikel.getItemIds();
		System.out.println(ausgArtikel);
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
	
}

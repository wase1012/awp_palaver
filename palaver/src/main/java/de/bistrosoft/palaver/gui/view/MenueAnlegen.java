package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tepi.filtertable.FilterTable;

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
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.data.MenueDAO;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueart;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueartverwaltung;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class MenueAnlegen extends VerticalLayout implements View,
		ValueChangeListener {

	private Label ueberschriftAnlegen = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Menue anlegen</font><b></pre>",
			ContentMode.HTML);
	private Label ueberschriftUpdate = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Menue Ändern</font><b></pre>",
			ContentMode.HTML);

	private VerticalLayout vlBox = new VerticalLayout();
	private HorizontalLayout hlUeberschrift = new HorizontalLayout();
	private HorizontalLayout hlDetails = new HorizontalLayout();
	private VerticalLayout vlDetailsLinks = new VerticalLayout();
	private VerticalLayout vlDetailsRechts = new VerticalLayout();
	private HorizontalLayout hlRezepte = new HorizontalLayout();
	private HorizontalLayout hlControl = new HorizontalLayout();

	private TextField tfMenuename = new TextField("Menuename");

	private TwinColSelect tcsFussnoten = new TwinColSelect("Fussnoten");

	private NativeSelect nSKoch = new NativeSelect("Koch");
	private NativeSelect nSMenueart = new NativeSelect("Menueart");
	private NativeSelect nSGeschmack = new NativeSelect("Geschmack");

	private CheckBox chbFavorit = new CheckBox("Menü ist ein Favorit");
	private CheckBox chbAufwand = new CheckBox("Menü hat hohen Aufwand");

	private Button btSpeichern = new Button("Speichern");
	private Button btVerwerfen = new Button("Verwerfen");
	private Button btUpdate = new Button("Ä„ndern");
	private Button btNeuesRezept = new Button("neues Rezeptanlegen");

	private Table tblMenue = new Table();
	private BeanItemContainer<Rezept> ctMenue;
	private FilterTable tblRezepte = new FilterTable();
	private BeanItemContainer<Rezept> ctRezepte;

	private List<Rezept> tmpRezepte = new ArrayList<Rezept>();
	List<Rezept> listrezept = new ArrayList<Rezept>();
	List<Fussnote> listfussnote = new ArrayList<Fussnote>();
	Menue menue = new Menue();

	public MenueAnlegen() {
		super();

		tblRezepte = new FilterTable();
		tblRezepte.setSizeFull();
		tblRezepte.setStyleName("palaverTable");
		tblRezepte.setImmediate(true);
		tblRezepte.setFilterBarVisible(true);
		tblRezepte.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);
		tblRezepte.setCaption("Rezepte");

		tblMenue = new Table();
		tblMenue.setSizeFull();
		tblMenue.setStyleName("palaverTable");
		tblMenue.setImmediate(true);
		tblMenue.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		tblMenue.setCaption("Rezepte in Menü");

		load();
		this.setSizeFull();
		this.setMargin(true);

		// Komponenten einfuegen
		this.addComponent(vlBox);
		vlBox.addComponent(hlUeberschrift);
		hlUeberschrift.addComponent(ueberschriftAnlegen);
		vlBox.addComponent(hlDetails);
		hlDetails.addComponent(vlDetailsLinks);
		vlDetailsLinks.addComponent(tfMenuename);
		vlDetailsLinks.addComponent(nSKoch);
		vlDetailsLinks.addComponent(nSMenueart);
		vlDetailsLinks.addComponent(nSGeschmack);
		vlDetailsLinks.addComponent(chbFavorit);
		vlDetailsLinks.addComponent(chbAufwand);
		hlDetails.addComponent(vlDetailsRechts);
		vlDetailsRechts.addComponent(tcsFussnoten);
		vlBox.addComponent(hlRezepte);
		hlRezepte.addComponent(tblMenue);
		hlRezepte.addComponent(tblRezepte);
		vlBox.addComponent(hlControl);
		hlControl.addComponent(btVerwerfen);
		hlControl.addComponent(btSpeichern);

		// Komponenten formatieren
		hlDetails.setWidth("525px");
		hlDetails.setComponentAlignment(vlDetailsLinks, Alignment.TOP_LEFT);
		hlDetails.setComponentAlignment(vlDetailsRechts, Alignment.TOP_RIGHT);
		hlRezepte.setWidth("900px");
		hlRezepte.setComponentAlignment(tblMenue, Alignment.TOP_LEFT);
		hlRezepte.setComponentAlignment(tblRezepte, Alignment.TOP_RIGHT);
		tblRezepte.setVisibleColumns(new Object[] { "name", "rezeptart",
				"mitarbeiter" });
		tblMenue.setVisibleColumns(new Object[] { "name", "rezeptart",
				"mitarbeiter" });

		tcsFussnoten.setWidth("100%");
		tfMenuename.setWidth("90%");
		tfMenuename.setMaxLength(200);

		nSGeschmack.setNullSelectionAllowed(false);
		nSMenueart.setNullSelectionAllowed(false);
		tfMenuename.setImmediate(true);
		nSKoch.setImmediate(true);
		nSKoch.setNullSelectionAllowed(false);

		tcsFussnoten.setImmediate(true);
		tcsFussnoten.setWidth("400");

		btSpeichern.setIcon(new ThemeResource("img/save.ico"));
		btVerwerfen.setIcon(new ThemeResource("img/cross.ico"));
		btUpdate.setIcon(new ThemeResource("img/cross.ico"));

		// Drag&Drop
		tblMenue.setDropHandler(new DropHandler() {

			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				Rezept selected = (Rezept) t.getData("itemId");
				ctRezepte.removeItem(selected);
				tmpRezepte.add(selected);
				ctMenue.addItem(selected);
				tblMenue.markAsDirty();
				tblRezepte.markAsDirty();
			}
		});

		tblRezepte.setDropHandler(new DropHandler() {

			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				Rezept selected = (Rezept) t.getData("itemId");
				ctRezepte.addItem(selected);
				tmpRezepte.remove(selected);
				ctMenue.removeItem(selected);
				tblMenue.markAsDirty();
				tblRezepte.markAsDirty();
			}
		});

		btVerwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(MenueAnlegen.class);
			}
		});

		btSpeichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					speichern();
					ViewHandler.getInstance().switchView(
							MenueAnzeigenTabelle.class);
				}
			}
		});

		// TODO Button implementieren
		btNeuesRezept.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(RezeptAnlegen.class);
			}
		});

	}

	public void load() {

		
		// Inhalte laden
		try {
			ctRezepte = new BeanItemContainer<Rezept>(Rezept.class,
					Rezeptverwaltung.getInstance().getAllRezepte());
			tblRezepte.setContainerDataSource(ctRezepte);

			ctMenue = new BeanItemContainer<Rezept>(Rezept.class);
			tblMenue.setContainerDataSource(ctMenue);

			List<Mitarbeiter> mitarbeiter = Mitarbeiterverwaltung.getInstance()
					.getAllMitarbeiter();
			for (Mitarbeiter e : mitarbeiter) {
				nSKoch.addItem(e);
			}
	
			List<Geschmack> geschmack = Geschmackverwaltung.getInstance()
					.getAllGeschmackAktiv();
			for (Geschmack e : geschmack) {
				nSGeschmack.addItem(e);

			}

			List<Menueart> menueart = Menueartverwaltung.getInstance()
					.getAllMenueart();
			for (Menueart e : menueart) {
				nSMenueart.addItem(e);

			}

			List<Fussnote> fussnote = Fussnotenverwaltung.getInstance()
					.getAllFussnote();
			for (Fussnote e : fussnote) {
				tcsFussnoten.addItem(e);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getViewParam(ViewData data) {

		menue = (Menue) ((ViewDataObject<?>) data).getData();
		Long id = menue.getId();
		menue=null;
		try {
			menue = Menueverwaltung.getInstance().getMenueById(id);
		} catch (ConnectException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			menue = MenueDAO.getInstance().getMenueById(menue.getId());
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// vorhandene Rezepte setzen
		if (menue.getRezepte() != null) {
			tmpRezepte = menue.getRezepte();
		} else {
			tmpRezepte = new ArrayList<Rezept>();
		}

		tfMenuename.setValue(menue.getName());

		nSKoch.select(menue.getKoch());

		nSMenueart.select(menue.getMenueart());

		nSGeschmack.select(menue.getGeschmack());

		chbAufwand.setValue(menue.getAufwand());

		chbFavorit.setValue(menue.getFavorit());

		for (int i = 0; i < menue.getFussnoten().size(); i++) {
			tcsFussnoten.select(menue.getFussnoten().get(i));
		}



		tblMenue = null;
		tblMenue = new Table();
		tblMenue.setSizeFull();
		tblMenue.setStyleName("palaverTable2");
		tblMenue.setImmediate(true);

		tblMenue.setContainerDataSource(ctRezepte);
		tmpRezepte = menue.getRezepte();
		for (Rezept r : tmpRezepte) {
			ctRezepte.removeItem(r);
			ctMenue.addItem(r);
			
		}

		
		
		menue.setRezepte(tmpRezepte);

		hlControl.replaceComponent(btSpeichern, btUpdate);
		hlUeberschrift
				.replaceComponent(ueberschriftAnlegen, ueberschriftUpdate);
		btUpdate.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		btUpdate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					update();
					ViewHandler.getInstance().switchView(
							MenueAnzeigenTabelle.class);
				}
			}
		});
	}

	private void speichern() {

		erstelleMenue();
		try {
			Menueverwaltung.getInstance().speicherMenue(menue);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void erstelleMenue() {

		if (menue == null) {
			menue = new Menue();
		}

		menue.setName(tfMenuename.getValue());
		menue.setKoch((Mitarbeiter) nSKoch.getValue());
		menue.setMenueart((Menueart) nSMenueart.getValue());
		menue.setGeschmack((Geschmack) nSGeschmack.getValue());

		if (chbFavorit.getValue()) {
			menue.setFavorit(true);
		} else
			menue.setFavorit(false);

		if (chbAufwand.getValue()) {
			menue.setAufwand(true);
		} else
			menue.setAufwand(false);

		menue.setFussnoten(ladeFussnotenAusTCS());

		menue.setRezepte(tmpRezepte);
	}

	private List<Fussnote> ladeFussnotenAusTCS() {
		List<Fussnote> fussnoten = new ArrayList<Fussnote>();

		if (tcsFussnoten.getValue().toString() != "[]") {
			List<String> sFussnoten = Arrays.asList(tcsFussnoten
					.getValue()
					.toString()
					.substring(1,
							tcsFussnoten.getValue().toString().length() - 1)
					.split("\\s*,\\s*"));

			for (String sFussnote : sFussnoten) {
				try {
					fussnoten.add(Fussnotenverwaltung.getInstance()
							.getFussnoteByName(sFussnote));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return fussnoten;
	}

	private void update() {

		erstelleMenue();

		try {
			Menueverwaltung.getInstance().updateMenue(menue);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void showNotification(String text) {
		Notification notification = new Notification(text);
		notification.setDelayMsec(500);
		notification.show(Page.getCurrent());
	}

	private Boolean validiereEingabe() {
		if (tfMenuename.getValue().isEmpty()) {
			showNotification("Bitte Namen eingeben!");
			return false;
		}
		if (nSKoch.getValue() == null) {
			showNotification("Bitte Koch wÃ¤hlen!");
			return false;
		}
		if (nSMenueart.getValue() == null) {
			showNotification("Bitte Menüart wählen!");
			return false;
		}
		if (nSGeschmack.getValue() == null) {
			showNotification("Bitte Geschmack wälen!");
			return false;
		}
		if (tmpRezepte == null || tmpRezepte.size() == 0) {
			showNotification("Bitte Rezept wählen!");
			return false;
		}
		int countHauptmenue = 0;
		for (Rezept rezept : tmpRezepte) {
			if (rezept.getRezeptart().getId() == 1L) {
				++countHauptmenue;
			}
		}
		if (countHauptmenue == 0) {
			showNotification("Bitte ein Hauptgericht wählen!");
			return false;
		}
		if (countHauptmenue > 1) {
			showNotification("Bitte nur ein Hauptgericht wählen!");
			return false;
		}
		return true;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {

	}
}
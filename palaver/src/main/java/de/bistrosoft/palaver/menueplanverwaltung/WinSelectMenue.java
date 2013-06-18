package de.bistrosoft.palaver.menueplanverwaltung;

import java.sql.SQLException;
import java.util.List;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.bistrosoft.palaver.gui.view.MenueAnlegen;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueverwaltung;
import de.bistrosoft.palaver.regelverwaltung.domain.Regel;
import de.bistrosoft.palaver.regelverwaltung.service.Regelverwaltung;
import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.util.IConstants;
import fi.jasoft.dragdroplayouts.DDGridLayout;

@SuppressWarnings("serial")
public class WinSelectMenue extends Window {

	Window subwindow = this;

	Menue menue;
	private FilterTable ftMenueList = new FilterTable();
	private FilterTable ftFehler = new FilterTable();

	private TextField tfId = new TextField("Id");
	private TextField tfAngezName = new TextField("Angezeigter Name");
	private TextField tfName = new TextField("Name");
	private TextField tfKoch = new TextField("Koch");
	private TextField tfMenueart = new TextField("Menueart");
	private TextField tfGeschmack = new TextField("Geschmack");
	private TextField tfPortion = new TextField("Portion in %");
	private CheckBox chbAufwand = new CheckBox("Aufwand");
	private CheckBox chbFavorit = new CheckBox("Favorit");
	private Button btNewMenue = new Button("Neu");
	private Button btSelect = new Button(IConstants.BUTTON_SELECT);
	private FormLayout flEditorLayout = new FormLayout();
	
	List<Regel> regeln = null;
	
	private Integer curCol;
	private Integer curRow;

	private Component destComp;
	private int destRow;
	private int destCol;
	private MenueplanGridLayout menueplan;
	DDGridLayout menueGrid;

	BeanItemContainer<Menue> menueContainer;
	BeanItemContainer<Regel> ctRegeln;

	VerticalLayout vlLeftLayout = new VerticalLayout();

	public WinSelectMenue(MenueplanGridLayout menueplan, Component destComp,
			int destRow, int destCol) {
		setCaption("Menü einfügen");
		this.menueplan = menueplan;
		this.menueGrid = menueplan.layout;
		this.destComp = destComp;
		this.destCol = destCol;
		this.destRow = destRow;

		regeln = Regelverwaltung.getInstance().getAllAktivRegeln();
		
		initLayout();
		initMenueList();
		initEditor();
		initOKButton();
		setRowCcol();
	}

	private void setRowCcol() {
		// TODO Auto-generated method stub
		final int COLUMNS = menueGrid.getColumns();
		final int ROWS = menueGrid.getRows();

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				if (destComp.equals(menueGrid.getComponent(col, row))) {
					curCol = col;
					curRow = row;
				}
			}
		}
		
	}

	private void initLayout() {
		// SplitPanel erstellen
		// HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		HorizontalLayout hlBox = new HorizontalLayout();

		// splitPanel.setWidth("800px");
		// Splitpanel die Layouts zufï¿½gen
		hlBox.addComponent(vlLeftLayout);
		hlBox.addComponent(flEditorLayout);
		// Der linken Seite Tabelle hinzufügen und Layout für Suchfeld und
		// "Neu" Button
		vlLeftLayout.addComponent(ftMenueList);
		// leftLayout.addComponent(bottomLeftLayout);

		// Suchfeld und "Neu" Button hinzufï¿½gen
		vlLeftLayout.addComponent(btNewMenue);

		btNewMenue.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addNewMenue();
			}

		});

		// Linke Seite Höhe und Breite auf 100% setzen
		vlLeftLayout.setHeight("100%");
		flEditorLayout.setHeight("100%");

		flEditorLayout.setMargin(true);
		flEditorLayout.setVisible(false);
		setContent(hlBox);
	}

	private void initEditor() {

		HorizontalLayout hlEditorChB = new HorizontalLayout();
		hlEditorChB.addComponents(chbFavorit, chbAufwand);
		flEditorLayout.addComponents(tfAngezName, tfName, tfKoch, tfMenueart,
				tfGeschmack, tfPortion, hlEditorChB);
		
		flEditorLayout.addComponent(ftFehler);
		ftFehler.setVisible(false);

		flEditorLayout.addComponent(btSelect);
	}

	private void initOKButton() {
		btSelect.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				// aktuelle Column und Row ermitteln
				Integer sourceRow = -1;
				Integer sourceColumn = -1;

				final int COLUMNS = menueGrid.getColumns();
				final int ROWS = menueGrid.getRows();

				for (int row = 0; row < ROWS; row++) {
					for (int col = 0; col < COLUMNS; col++) {
						if (destComp.equals(menueGrid.getComponent(col, row))) {
							sourceColumn = col;
							sourceRow = row;
						}
					}
				}
				menueplan.removeMenue(destComp);

				// Menübezeichnung des ausgewï¿½hlten Menï¿½s
				try {
					Menue menue = (Menue) ftMenueList.getValue();
					// Neue Menï¿½komponente aus ausgewï¿½hltem Menï¿½ erstellen
					// und hinzufï¿½gen
					Integer iPortion = Integer.parseInt(tfPortion.getValue());
					MenueComponent menueComp = new MenueComponent(menue,
							tfAngezName.getValue(), menueplan, menueGrid,
							sourceRow, sourceColumn, true, iPortion);
					menueplan.addMenue(menueComp, sourceColumn, sourceRow);
					menueGrid.setComponentAlignment(menueComp,
							Alignment.MIDDLE_CENTER);

				} catch (Exception e) {

					e.printStackTrace();
				}

				// Window schließen
				subwindow.close();
			}
		});
	}

	private void initMenueList() {

		ladeMenues();
		ftMenueList.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					menue = (Menue) event.getProperty().getValue();
					tfId.setValue(menue.getId().toString());
					tfId.setVisible(false);
					tfAngezName.setValue(menue.getName());
					tfName.setValue(menue.getName());
					tfName.setEnabled(false);
					tfKoch.setValue(menue.getKochname());
					tfKoch.setEnabled(false);
					if (menue.getMenueart() != null) {
						tfMenueart.setValue(menue.getMenueart()
								.getBezeichnung());
					}
					tfMenueart.setEnabled(false);
					if (menue.getGeschmack() != null) {
						tfGeschmack.setValue(menue.getGeschmack()
								.getBezeichnung());
					}
					tfGeschmack.setEnabled(false);
					chbFavorit.setValue(menue.getFavorit());
					chbAufwand.setValue(menue.getAufwand());
					chbFavorit.setEnabled(false);
					chbAufwand.setEnabled(false);
					tfPortion.setValue("100");
					flEditorLayout.setVisible(true);

					// ////////////////////
					//TODO Regeln
//					MenueComponent mcTmp = new MenueComponent(menue, "",
//							menueplan, menueGrid, destRow, destCol, null, null);
//					regeln=Regelverwaltung.getInstance().getAllAktivRegeln();
//					System.out.println(regeln.size());
//					for (Regel r : regeln) {
//						r.check(mcTmp, menueplan);
//					}
//					System.out.println(mcTmp.getFehlerRegeln());
//					if(mcTmp.getFehlerRegeln()!=null){
//						ctRegeln = new BeanItemContainer<Regel>(Regel.class,mcTmp.getFehlerRegeln());
//						ftFehler.setContainerDataSource(ctRegeln);
//						ftFehler.setVisible(true);
//					}
					simuliereAdd();

				}

			}
		});

		ftMenueList.setFilterFieldValue("kochname", ((Application) UI
				.getCurrent().getData()).getUser().getVorname());

		ftMenueList.setColumnWidth("name", 200);
		ftMenueList.setColumnWidth("kochname", 80);
		ftMenueList.setColumnWidth("geschmack", 80);
		ftMenueList.setColumnWidth("menueart", 80);

		// Spaltenbezeichnung angeben
		ftMenueList.setSelectable(true);
		ftMenueList.setFilterBarVisible(true);
		ftMenueList.setImmediate(true);

		// bei Ãndern Komponente aus Menï¿½plan selektieren
		if (menueGrid.getComponent(destCol, destRow) instanceof MenueComponent) {
			MenueComponent mc = (MenueComponent) menueGrid.getComponent(
					destCol, destRow);
			ftMenueList.select(mc.getMenue());
			tfAngezName.setValue(mc.getAngezeigterName());
			tfPortion.setValue(mc.getPortion().toString());
		}

	}

	private void ladeMenues() {

		// Container fï¿½r Menï¿½liste festlegen
		try {
			menueContainer = new BeanItemContainer<Menue>(Menue.class,
					Menueverwaltung.getInstance().getAllMenuesTabelleAktiv());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ftMenueList.setContainerDataSource(menueContainer);

		// Spalten festlegen
		ftMenueList.setVisibleColumns(new Object[] { "name", "kochname",
				"geschmack", "menueart" });
		ftMenueList.sort(new Object[] { "name" }, new boolean[] { true });
	}

	private void addNewMenue() {
		final Window win = new Window("Neues Menü");
		win.setModal(true);
		win.setResizable(false);
		win.setWidth("1100px");
		win.setHeight("850px");

		MenueAnlegen me = new MenueAnlegen();
		win.setContent(me);
		// addComponent(me);

		win.setContent(me);
		UI.getCurrent().addWindow(win);
		win.addCloseListener(new Window.CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {
				ladeMenues();

			}
		});
	}

	private void simuliereAdd(){
//		// aktuelle Column und Row ermitteln
//		Integer sourceRow = -1;
//		Integer sourceColumn = -1;
//
//		final int COLUMNS = menueGrid.getColumns();
//		final int ROWS = menueGrid.getRows();
//
//		for (int row = 0; row < ROWS; row++) {
//			for (int col = 0; col < COLUMNS; col++) {
//				if (destComp.equals(menueGrid.getComponent(col, row))) {
//					sourceColumn = col;
//					sourceRow = row;
//				}
//			}
//		}
		// aktuelle Comp sichern
//		Component old = menueplan.layout.getComponent(curCol, curRow);
		System.out.println("old: "+ menueplan.layout);
		System.out.println("old: "+ getParent());
//		System.out.println("old: "+ old);
		System.out.println("old: "+ curCol);
		System.out.println("old: "+ curRow);
//		
//		menueplan.removeMenue(destComp);
//		// neue Comp einfügen
//		Integer iPortion = Integer.parseInt(tfPortion.getValue());
//		MenueComponent menueComp = new MenueComponent(menue,
//				tfAngezName.getValue(), menueplan, menueGrid,
//				curRow, curCol, true, iPortion);
//		menueplan.addMenue(menueComp, curCol, curRow);
//		
//		// regeln prüfen
//		for (Regel r : regeln) {
//			r.check(menueComp, menueplan);
//		}
//
//		// neue Comp löschen
//		//aktuelle comp einfügen
//		menueGrid.replaceComponent(menueComp, old);

		// Menübezeichnung des ausgewï¿½hlten Menï¿½s
//		try {
//			Menue menue = (Menue) ftMenueList.getValue();
//			 Neue Menï¿½komponente aus ausgewï¿½hltem Menï¿½ erstellen
//			 und hinzufï¿½gen
//			Integer iPortion = Integer.parseInt(tfPortion.getValue());
//			MenueComponent menueComp = new MenueComponent(menue,
//					tfAngezName.getValue(), menueplan, menueGrid,
//					curRow, curCol, true, iPortion);
//			menueplan.addMenue(menueComp, curCol, curRow);
//			menueGrid.setComponentAlignment(menueComp,
//					Alignment.MIDDLE_CENTER);
//			for (Regel r : regeln) {
//				r.check(menueComp, menueplan);
//			}
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
//		
//		
//
	}
}

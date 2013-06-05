package de.bistrosoft.palaver.menueplanverwaltung;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.bistrosoft.palaver.data.RegelDAO;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueplan;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueplanverwaltung;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.bistrosoft.palaver.regelverwaltung.domain.Regel;
import de.bistrosoft.palaver.regelverwaltung.service.Regelverwaltung;
import de.bistrosoft.palaver.util.CalendarWeek;
import de.bistrosoft.palaver.util.Week;
import fi.jasoft.dragdroplayouts.DDGridLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;

@SuppressWarnings("serial")
public class MenueplanGridLayout extends CustomComponent {

	// Konstanten
	MenueplanGridLayout instance = this;
	private static final int ROWS = 8;
	private static final int COLUMNS = 6;
	public DDGridLayout layout = null;
	private Menueplan menueplan = null;
//	List<Regel> regeln = Regel.getTestRegeln();
	List<Regel> regeln = Regelverwaltung.getInstance().getAllAktivRegeln();
	 

	public Menueplan getMenueplan() {
		return menueplan;
	}

	public void setMenueplan(Menueplan menueplan) {
		this.menueplan = menueplan;
	}

	// Seitenlayout erstellen
	public MenueplanGridLayout(int week, int year) {
		menueplan = Menueplanverwaltung.getInstance().getMenueplanByWeekWithItems(new Week(week, year));
		if (menueplan == null) {
			menueplan = new Menueplan(new Week(week, year));
		}
		// setCaption("Kalenderwoche: " + week +"/"+year);
		setSizeFull();

		// vertikales Layout anlegen
		VerticalLayout outer = new VerticalLayout();
		outer.setSizeFull();
		setCompositionRoot(outer);

		// DragDropGridLayout erstellen
		layout = new DDGridLayout(COLUMNS, ROWS);
		int width = COLUMNS * 150;
		int height = ((ROWS - 2) * 90) + 30 + 50;
		layout.setWidth(width + "px");
		layout.setHeight(height + "px");
		layout.setStyleName("menueplan-grid");

		// Verschieben nur in die Zellenmitte erlauben
		layout.setComponentHorizontalDropRatio(0);
		layout.setComponentVerticalDropRatio(0);

		// DragDropGridLayout zu Seitenlayout hinzufügen
		outer.addComponent(layout);
		outer.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
		outer.setExpandRatio(layout, 1);

		// Wegschieben der DragDropGridLayout-Komponenten ermöglichen
		layout.setDragMode(LayoutDragMode.CLONE);

		// Hinschieben der DragDropGridLayout-Komponenten ermöglichen
		layout.setDropHandler(new MenueplanGridDropHandler());

		// Limit dragging to only buttons
		layout.setDragFilter(new DragFilter() {
			public boolean isDraggable(Component component) {
				return component instanceof MenueComponent;
			}
		});

		// Fülle Überschriftenspalte mit formatierten Labels
		@SuppressWarnings("deprecation")
		Label[] arlbUeb = {
				new Label(
						"<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">     Datum</font></pre>",
						Label.CONTENT_XHTML),
				new Label(
						"<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r     Köche</font></pre>",
						Label.CONTENT_XHTML),
				new Label(
						"<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \n     Fleischgericht</font></pre>",
						Label.CONTENT_XHTML),
				new Label(
						"<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \n     Hauptgericht</font></pre>",
						Label.CONTENT_XHTML),
				new Label(
						"<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \n     Hauptgericht</font></pre>",
						Label.CONTENT_XHTML),
				new Label(
						"<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \n     Pastagericht</font></pre>",
						Label.CONTENT_XHTML),
				new Label(
						"<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \n     Suppe / Salat</font></pre>",
						Label.CONTENT_XHTML),
				new Label(
						"<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \n     Dessert</font></pre>",
						Label.CONTENT_XHTML) };
		for (int i = 0; i < arlbUeb.length; i++) {
			arlbUeb[i].setWidth("150px");
			arlbUeb[i].setHeight("90px");
			layout.addComponent(arlbUeb[i], 0, i);
			layout.setComponentAlignment(arlbUeb[i], Alignment.MIDDLE_CENTER);
		}
		arlbUeb[0].setHeight("30px");
		arlbUeb[1].setHeight("50px");

		// Fülle Datumszeile mit Wochentag und Datum
		ArrayList<GregorianCalendar> dates = CalendarWeek.getDatesOfWeek(week,
				year);
		for (int col = 1; col < COLUMNS; col++) {
			GregorianCalendar date = dates.get(col - 1);
			String strDay = date.getDisplayName(Calendar.DAY_OF_WEEK, 2,
					Locale.GERMANY);

			String strDate = date.get(Calendar.DAY_OF_MONTH) + "."
					+ (date.get(Calendar.MONTH) + 1) + "."
					+ date.get(Calendar.YEAR);

			@SuppressWarnings("deprecation")
			Label lbTmp = new Label("<div align=center><B>" + "\r\n" + strDay
					+ "\r\n" + strDate + "</B></div>", Label.CONTENT_XHTML);
			lbTmp.setHeight("30px");
			lbTmp.setWidth("149px");
			layout.addComponent(lbTmp, col, 0);
			layout.setComponentAlignment(lbTmp, Alignment.MIDDLE_CENTER);
		}

		// Fülle Zeile für Köche mit DropDowm-Boxen für Namen
		for (int col = 1; col < COLUMNS; col++) {
			VerticalLayout vl = new VerticalLayout();
			ComboBox koch1 = new ComboBox();
			ComboBox koch2 = new ComboBox();
			try {
				List<Mitarbeiter> mitarbeiter = Mitarbeiterverwaltung
						.getInstance().getAllMitarbeiter();
				for (Mitarbeiter e : mitarbeiter) {
					// mitarbeiterCb.addItem(e);
					koch1.addItem(e.getId());
					koch1.setItemCaption(e.getId(), e.getVorname());
					koch2.addItem(e.getId());
					koch2.setItemCaption(e.getId(), e.getVorname());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			koch1.setWidth("140px");
			koch2.setWidth("140px");
			vl.addComponent(koch1);
			vl.addComponent(koch2);
			vl.setWidth("149px");
			vl.setHeight("50px");
			vl.setComponentAlignment(koch1, Alignment.MIDDLE_CENTER);
			vl.setComponentAlignment(koch2, Alignment.MIDDLE_CENTER);
			layout.addComponent(vl, col, 1);
			layout.setComponentAlignment(vl, Alignment.MIDDLE_CENTER);
		}

		// Füge MenueItems ein
		if (menueplan != null) {
			if (menueplan.getMenues() != null) {
				for (MenueComponent mc : menueplan.getMenues()) {
					mc.setMenueGrid(layout);
					mc.setMenueplan(this);
					layout.addComponent(mc, mc.getCol(), mc.getRow());
					this.pruefeRegeln(mc);
				}
			}
		}

		// Füge ADD Buttons in noch leere Felder ein
		for (int row = 2; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				if (layout.getComponent(col, row) == null) {
					Button btn = new Button();
					btn.setStyleName(BaseTheme.BUTTON_LINK);
					btn.setIcon(new ThemeResource("img/Menue.png"));
					btn.addStyleName("menueplan-add");
					btn.addClickListener(new ClickListener() {

						// Click-Listener für ADD_Buttons
						@Override
						public void buttonClick(ClickEvent event) {
							Button tmp = event.getButton();
							for (int row = 0; row < ROWS; row++) {
								for (int col = 0; col < COLUMNS; col++) {
									if (tmp.equals(layout
											.getComponent(col, row))) {
										WinSelectMenue window = new WinSelectMenue(
												instance, tmp, row, col);
										UI.getCurrent().addWindow(window);
										window.setModal(true);
										window.setWidth("50%");
										window.setHeight("50%");
									}
								}
							}
						}
					});

					btn.setHeight("90px");
					btn.setWidth("149px");
					layout.addComponent(btn, col, row);
					layout.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
				}

			}
		}
	}

	public void speichern() {

		// Extrahiere Menüs
		List<MenueComponent> menues = new ArrayList<MenueComponent>();
		for (int col = 0; col < COLUMNS; ++col) {
			for (int row = 0; row < ROWS; ++row) {
				Component comp = layout.getComponent(col, row);
				if (MenueComponent.class.isInstance(comp)) {
					MenueComponent menueComp = (MenueComponent) comp;
					menueComp.setCol(col);
					menueComp.setRow(row);
					menues.add(menueComp);
				}
			}
		}
		menueplan.setMenues(menues);

		// Extrahiere Köche
		// TODO: Köche extrahieren
		List<KochInMenueplan> koeche = new ArrayList<KochInMenueplan>();
		for (int col = 2; col < COLUMNS; ++col) {
			VerticalLayout vl = (VerticalLayout) layout.getComponent(col, 1);
			ComboBox cb1 = (ComboBox) vl.getComponent(0);
			ComboBox cb2 = (ComboBox) vl.getComponent(1);
		}
		menueplan.setKoeche(koeche);

		Menueplanverwaltung.getInstance().persist(menueplan);
	}

	public void removeMenue(Component destComp) {
		this.layout.removeComponent(destComp);
		this.pruefeMenueRegeln();
	}
	
	public void addMenue(MenueComponent comp, Integer col, Integer row) {
		
		layout.addComponent(comp, col, row);
		pruefeRegeln(comp);
		
	}
	
	public void vertauscheMenue(Component sourceComp, Component comp, Integer col, Integer row) {	
		layout.removeComponent(sourceComp);
		layout.removeComponent(comp);
		pruefeMenueRegeln();
		layout.addComponent(sourceComp,col,row);

		layout.setComponentAlignment(sourceComp, Alignment.MIDDLE_CENTER);

	}

	public void pruefeRegeln(MenueComponent mc) {
		for (Regel r : regeln) {
			r.check(mc, this); 
		}
	}
	
	public void pruefeMenueRegeln(){
		for (int col = 0; col < COLUMNS; ++col) {
			for (int row = 0; row < ROWS; ++row) {
				Component comp=layout.getComponent(col, row);
				if(comp instanceof MenueComponent){
					MenueComponent menue = (MenueComponent) comp;
					menue.pruefeRegeln(this);
				}
			}
		}
	}
}

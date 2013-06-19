package de.bistrosoft.palaver.gui.view;

//import org.vaadin.virkki.carousel.HorizontalCarousel;
//import org.vaadin.virkki.carousel.client.widget.gwt.ArrowKeysMode;
//import org.vaadin.virkki.carousel.client.widget.gwt.CarouselLoadMode;

import java.util.Date;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

import de.bistrosoft.palaver.menueplanverwaltung.MenueComponent;
import de.bistrosoft.palaver.menueplanverwaltung.MenueplanGridLayout;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueplanverwaltung;
import de.bistrosoft.palaver.util.CalendarWeek;
import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;

@SuppressWarnings("serial")
public class MenueplanAnzeigen extends VerticalLayout implements View {

	// Variablen und Komponenten
	private VerticalLayout box = new VerticalLayout();

	Week curWeek = CalendarWeek.getCurrentWeek();
	final int week = curWeek.getWeek();
	final int year = curWeek.getYear();

	MenueplanGridLayout ersteMenueplan = new MenueplanGridLayout(week, year);
	MenueplanGridLayout zweiteMenueplan = new MenueplanGridLayout(week + 1,
			year);
	MenueplanGridLayout dritteMenueplan = new MenueplanGridLayout(week + 2,
			year);
	MenueplanGridLayout vierteMenueplan = new MenueplanGridLayout(week + 3,
			year);

	HorizontalLayout hlChangeWeek = new HorizontalLayout();
	private Button btForeWeek = new Button();
	private Button btNextWeek = new Button();
	private Button platzhalter1 = new Button();
	private Button platzhalter2 = new Button();
	private String strKW = new String("Kalenderwoche: " + (week + 1) + "/"
			+ year);

	HorizontalLayout hlControl = new HorizontalLayout();

	Button btEnableDelete = new Button("Elemente löschen");
	Button btSubmitDelete = new Button("Löschen bestätigen");
	Button btEnableDragging = new Button("Verschieben aktiv");
	Button btSpeichern = new Button("Speichern");
	Button btFreigeben = new Button("Freigeben");
	Button btKopieren = new Button("Plan importieren");
	private Button btDeletePlan = new Button("Gesamten Plan löschen");

	private Label lbKW = new Label(
			"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
					+ strKW + "</pre>", ContentMode.HTML);
	MenueplanGridLayout shownMenueplan = zweiteMenueplan;

	// /////////////////
	Label lbMplWeek;

	public MenueplanAnzeigen() {
		super();
		this.setSizeFull();
		this.setMargin(true);

		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);

		platzhalter1.setStyleName(BaseTheme.BUTTON_LINK);
		platzhalter1.setIcon(new ThemeResource("img/platzhalter.png"));
		platzhalter1.addStyleName("menueplan-lastweek");
		platzhalter2.setStyleName(BaseTheme.BUTTON_LINK);
		platzhalter2.setIcon(new ThemeResource("img/platzhalter.png"));
		platzhalter2.addStyleName("menueplan-nextweek");

		// Pfeil zum Wechseln zur vorherigen KW und Anzeige der Wochen-Nr
		final HorizontalLayout left = new HorizontalLayout();
		final HorizontalLayout right = new HorizontalLayout();

		btForeWeek.setStyleName(BaseTheme.BUTTON_LINK);
		btForeWeek.setIcon(new ThemeResource("img/woche_vorherklein.png"));
		btForeWeek.addStyleName("menueplan-lastweek");
		btForeWeek.addClickListener(new ClickListener() {

			// Click-Listener für eine Woche vorher
			@Override
			public void buttonClick(ClickEvent event) {
				if (shownMenueplan == zweiteMenueplan) {
					box.replaceComponent(shownMenueplan, ersteMenueplan);
					shownMenueplan = ersteMenueplan;
					strKW = "Kalenderwoche: " + (week) + "/" + year;

					Label lbForeWeek = new Label(
							"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
									+ strKW + "</pre>", ContentMode.HTML);
					hlChangeWeek.replaceComponent(lbKW, lbForeWeek);
					lbKW = lbForeWeek;
					left.replaceComponent(btForeWeek, platzhalter1);

					checkRollen();
				} else if (shownMenueplan == dritteMenueplan) {
					box.replaceComponent(shownMenueplan, zweiteMenueplan);
					shownMenueplan = zweiteMenueplan;
					strKW = "Kalenderwoche: " + (week + 1) + "/" + year;

					Label lbForeWeek = new Label(
							"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
									+ strKW + "</pre>", ContentMode.HTML);
					hlChangeWeek.replaceComponent(lbKW, lbForeWeek);
					lbKW = lbForeWeek;
					left.replaceComponent(platzhalter1, btForeWeek);
					right.replaceComponent(platzhalter2, btNextWeek);

					checkRollen();

				} else if (shownMenueplan == vierteMenueplan) {
					box.replaceComponent(shownMenueplan, dritteMenueplan);
					shownMenueplan = dritteMenueplan;
					strKW = "Kalenderwoche: " + (week + 2) + "/" + year;

					Label lbForeWeek = new Label(
							"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
									+ strKW + "</pre>", ContentMode.HTML);
					hlChangeWeek.replaceComponent(lbKW, lbForeWeek);
					lbKW = lbForeWeek;
					left.replaceComponent(platzhalter1, btForeWeek);
					right.replaceComponent(platzhalter2, btNextWeek);

					checkRollen();
				}
				
				if (shownMenueplan.getMenueplan().getFreigegeben()) {
					btFreigeben.setIcon(new ThemeResource(IConstants.ICON_YES));
				} else {
					btFreigeben.setIcon(new ThemeResource(IConstants.ICON_NO));
				}

			}

		});

		// Pfeil zum Wechseln zur nächsten KW und Anzeige der Wochen-Nr
		btNextWeek.setStyleName(BaseTheme.BUTTON_LINK);
		btNextWeek.setIcon(new ThemeResource("img/woche_spaterklein.png"));
		btNextWeek.addStyleName("menueplan-nextweek");
		btNextWeek.addClickListener(new ClickListener() {

			// Click-Listener für eine Woche später
			@Override
			public void buttonClick(ClickEvent event) {
				if (shownMenueplan == ersteMenueplan) {
					box.replaceComponent(shownMenueplan, zweiteMenueplan);
					shownMenueplan = zweiteMenueplan;
					strKW = "Kalenderwoche: " + (week + 1) + "/" + year;

					Label lbNextWeek = new Label(
							"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
									+ strKW + "</pre>", ContentMode.HTML);
					hlChangeWeek.replaceComponent(lbKW, lbNextWeek);
					lbKW = lbNextWeek;
					right.replaceComponent(platzhalter2, btNextWeek);
					left.replaceComponent(platzhalter1, btForeWeek);

					checkRollen();
				} else if (shownMenueplan == zweiteMenueplan) {
					box.replaceComponent(shownMenueplan, dritteMenueplan);
					shownMenueplan = dritteMenueplan;
					strKW = "Kalenderwoche: " + (week + 2) + "/" + year;

					Label lbNextWeek = new Label(
							"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
									+ strKW + "</pre>", ContentMode.HTML);
					hlChangeWeek.replaceComponent(lbKW, lbNextWeek);
					lbKW = lbNextWeek;
					right.replaceComponent(platzhalter2, btNextWeek);
					left.replaceComponent(platzhalter1, btForeWeek);

					checkRollen();
				} else if (shownMenueplan == dritteMenueplan) {
					box.replaceComponent(shownMenueplan, vierteMenueplan);
					shownMenueplan = vierteMenueplan;
					strKW = "Kalenderwoche: " + (week + 3) + "/" + year;

					Label lbNextWeek = new Label(
							"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
									+ strKW + "</pre>", ContentMode.HTML);
					hlChangeWeek.replaceComponent(lbKW, lbNextWeek);
					lbKW = lbNextWeek;
					right.replaceComponent(btNextWeek, platzhalter2);

					checkRollen();
				}

				if (shownMenueplan.getMenueplan().getFreigegeben()) {
					btFreigeben.setIcon(new ThemeResource(IConstants.ICON_YES));
				} else {
					btFreigeben.setIcon(new ThemeResource(IConstants.ICON_NO));
				}
			}

		});

		// Hinzufügen und Anordnen der Komponenten
		left.addComponent(btForeWeek);
		left.setComponentAlignment(btForeWeek, Alignment.TOP_LEFT);
		right.addComponent(btNextWeek);
		right.setComponentAlignment(btNextWeek, Alignment.TOP_RIGHT);

		hlChangeWeek.addComponents(left, lbKW, right);
		hlChangeWeek.setComponentAlignment(left, Alignment.TOP_LEFT);
		hlChangeWeek.setComponentAlignment(lbKW, Alignment.TOP_CENTER);
		hlChangeWeek.setComponentAlignment(right, Alignment.TOP_RIGHT);
		box.addComponent(hlChangeWeek);
		box.setComponentAlignment(hlChangeWeek, Alignment.TOP_CENTER);

		if (shownMenueplan.getMenueplan().getFreigegeben()) {
			btFreigeben.setIcon(new ThemeResource(IConstants.ICON_YES));
		} else {
			btFreigeben.setIcon(new ThemeResource(IConstants.ICON_NO));
		}

		btSubmitDelete.addClickListener(new ClickListener() {
			// Click-Listener zum Speichern
			@Override
			public void buttonClick(ClickEvent event) {
				if (shownMenueplan.layout.getDragMode() == LayoutDragMode.CLONE) {
					shownMenueplan.layout.setDragMode(LayoutDragMode.NONE);
				} else {
					shownMenueplan.layout.setDragMode(LayoutDragMode.CLONE);
				}

				Integer rows = shownMenueplan.layout.getRows();
				Integer cols = shownMenueplan.layout.getColumns();
				for (int x = 0; x < cols; ++x) {
					for (int y = 0; y < rows; ++y) {
						if (shownMenueplan.layout.getComponent(x, y) instanceof MenueComponent) {
							MenueComponent mc = (MenueComponent) shownMenueplan.layout
									.getComponent(x, y);
							if (mc.isMarkiert()) {
								mc.remove();
							}
						}
					}
				}
				MenueplanAnzeigen.this.btEnableDelete.click();
			}
		});

		btEnableDragging.addClickListener(new ClickListener() {
			// Click-Listener zum Speichern
			@Override
			public void buttonClick(ClickEvent event) {
				if (shownMenueplan.layout.getDragMode() == LayoutDragMode.CLONE) {
					shownMenueplan.layout.setDragMode(LayoutDragMode.NONE);
					btEnableDragging.setCaption("Verschieben inaktiv");
				} else {
					shownMenueplan.layout.setDragMode(LayoutDragMode.CLONE);
					btEnableDragging.setCaption("Verschieben aktiv");
				}

			}
		});

		btEnableDelete.addClickListener(new ClickListener() {
			// Click-Listener zum Speichern
			@Override
			public void buttonClick(ClickEvent event) {
				if (btSubmitDelete.isVisible()) {
					shownMenueplan.layout.setDragMode(LayoutDragMode.CLONE);
					btSubmitDelete.setVisible(false);
					btDeletePlan.setVisible(false);
					btSpeichern.setVisible(true);
					// btFreigeben.setVisible(true);
					btEnableDragging.setVisible(true);
					btEnableDelete.setCaption("Elemente löschen");
				} else {
					shownMenueplan.layout.setDragMode(LayoutDragMode.NONE);
					btSubmitDelete.setVisible(true);
					btDeletePlan.setVisible(true);
					btSpeichern.setVisible(false);
					// btFreigeben.setVisible(false);
					btEnableDragging.setVisible(false);
					btEnableDelete.setCaption("Zurück");
				}

				Integer rows = shownMenueplan.layout.getRows();
				Integer cols = shownMenueplan.layout.getColumns();
				for (int x = 0; x < cols; ++x) {
					for (int y = 0; y < rows; ++y) {
						if (shownMenueplan.layout.getComponent(x, y) instanceof MenueComponent) {
							MenueComponent mc = (MenueComponent) shownMenueplan.layout
									.getComponent(x, y);
							mc.setCbDelete();
						}
						if (shownMenueplan.layout.getComponent(x, y) instanceof Button) {
							Button bt = (Button) shownMenueplan.layout
									.getComponent(x, y);
							if (bt.isEnabled()) {
								bt.setEnabled(false);
							} else {
								bt.setEnabled(true);
							}
						}
					}
				}
			}
		});

		btKopieren.addClickListener(new ClickListener() {
			// Click-Listener zum Speichern
			@Override
			public void buttonClick(ClickEvent event) {
				Window fvj = new Window();
				VerticalLayout vlWinBox = new VerticalLayout();
				final PopupDateField date = new PopupDateField("Datum wählen:") {
					@Override
					protected Date handleUnparsableDateString(String dateString)
							throws ConversionException {
						// Notification bei Fehleingabe
						Notification notification = new Notification(
								"Falsches Datumsformat.");
						notification.setDelayMsec(500);
						notification.show(Page.getCurrent());
						throw new ConversionException("Falsches Datumsformat");
					}
				};

				date.addValueChangeListener(new ValueChangeListener() {

					@Override
					public void valueChange(ValueChangeEvent event) {
						Date datum = date.getValue();
						Week selectedWeek = CalendarWeek.getWeekFromDate(datum);
						System.out.println("Date Change");
						lbMplWeek.setCaption(lbMplWeek.getCaption()
								+ " ersetzen mit " + selectedWeek.getWeek()
								+ "/" + selectedWeek.getYear());
					}
				});
				date.setWidth("150px");
				date.setDateFormat("dd.MM.yyyy");
				date.setLenient(true);
				date.setShowISOWeekNumbers(true);
				vlWinBox.addComponent(date);
				vlWinBox.setComponentAlignment(date, Alignment.TOP_CENTER);
				Week mplWeek = shownMenueplan.getMenueplan().getWeek();
				lbMplWeek = new Label("Menüplan " + mplWeek.getWeek() + "/"
						+ mplWeek.getYear());
				vlWinBox.addComponent(lbMplWeek);

				fvj.setContent(vlWinBox);
				UI.getCurrent().addWindow(fvj);
				// Menueplan imp =
				// Menueplanverwaltung.getInstance().getMenueplanByWeekWithItems(new
				// Week(27,2013));
				// Week newWeek = new Week(28, 2013);
				// imp.setWeek(newWeek);
				// imp.setId(null);
				// MenueplanGridLayout mgl = new MenueplanGridLayout(imp);
				// mgl.speichern();
				// ViewHandler.getInstance().switchView(MenueplanAnzeigen.class);
				// nextMenueplan = new MenueplanGridLayout(imp);
			}
		});

		btSpeichern.addClickListener(new ClickListener() {
			// Click-Listener zum Speichern
			@Override
			public void buttonClick(ClickEvent event) {
				// alle Felder durchgehen, prüfen ob menuecomponent vorhanden
				// ist und wenn ja speichern
				shownMenueplan.speichern();
				int week = shownMenueplan.getMenueplan().getWeek().getWeek();
				int year = shownMenueplan.getMenueplan().getWeek().getYear();
				Notification notification = new Notification(
						"Menüplan für Kalenderwoche " + week + "/" + year
								+ " wurde gespeichert");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
			}
		});

		btDeletePlan.addClickListener(new ClickListener() {
			// Click-Listener zum Speichern
			@Override
			public void buttonClick(ClickEvent event) {
				Menueplanverwaltung.getInstance().delete(
						shownMenueplan.getMenueplan());
			}
		});

		btFreigeben.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				shownMenueplan.speichern();
				shownMenueplan.freigeben();
				
				if (shownMenueplan.getMenueplan().getFreigegeben()) {
					btFreigeben.setIcon(new ThemeResource(IConstants.ICON_YES));
				} else {
					btFreigeben.setIcon(new ThemeResource(IConstants.ICON_NO));
				}
			}
		});

		// Fußnoten
		Label lbFussnoten = new Label(
				"<div align=center>ohne Gewähr &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (v) = vegan &nbsp;&nbsp; (vm) = vegan mögl. &nbsp;&nbsp; (veg.m) = vegetarisch mögl. &nbsp;&nbsp; (Z) = ohne Zwiebel &nbsp;&nbsp; (Zm) = ohne Zwiebel mögl. <BR> (K) = ohne Knoblauch &nbsp;&nbsp; (Km) = ohne Knoblauch mögl. &nbsp;&nbsp; (W) = ohne Weizen &nbsp;&nbsp; (Wm) = ohne Weizen mögl. &nbsp;&nbsp; (M) = ohne KuhMilch &nbsp;&nbsp; (Mm) = ohne KuhMilch mögl.</div>",
				ContentMode.HTML);

		// Hinzufügen und Anordnen weiterer Komponenten
		Label lbPlatzhalter = new Label(" ");
		lbPlatzhalter.setHeight("60px");
		btFreigeben.setVisible(false);
		hlControl.addComponent(btSpeichern);
		hlControl.addComponent(btFreigeben);
		hlControl.addComponent(btEnableDragging);
		hlControl.addComponent(btEnableDelete);
		hlControl.addComponent(btSubmitDelete);
		hlControl.setSpacing(true);
		btSubmitDelete.setVisible(false);
		hlControl.addComponent(btDeletePlan);
		btDeletePlan.setVisible(false);
		hlControl.addComponent(btKopieren);
		box.setSpacing(true);
		box.addComponent(hlControl);
		box.setComponentAlignment(hlControl, Alignment.MIDDLE_CENTER);
		box.addComponent(zweiteMenueplan);
		box.addComponent(lbFussnoten);
		box.addComponent(lbPlatzhalter);
		box.setComponentAlignment(zweiteMenueplan, Alignment.MIDDLE_CENTER);
		box.setComponentAlignment(lbFussnoten, Alignment.BOTTOM_CENTER);
		box.setComponentAlignment(lbPlatzhalter, Alignment.BOTTOM_CENTER);

		checkRollen();
	}

	public void checkRollen() {
		if (((Application) UI.getCurrent().getData())
				.userHasPersmission(Rollen.ADMINISTRATOR)) {
			btFreigeben.setVisible(true);
		}
		if (!((Application) UI.getCurrent().getData())
				.userHasPersmission(Rollen.ADMINISTRATOR)) {
			if (shownMenueplan.getMenueplan().getFreigegeben() == null
					|| shownMenueplan.getMenueplan().getFreigegeben() == false) {
				btSpeichern.setVisible(true);
			} else if (shownMenueplan.getMenueplan().getFreigegeben() == true) {
				btSpeichern.setVisible(false);
			}
		}

	}

	public static void switchMenueplan() {

	}

	@Override
	public void getViewParam(ViewData data) {

	}
}

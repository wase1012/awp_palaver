package de.bistrosoft.palaver.gui.view;

//import org.vaadin.virkki.carousel.HorizontalCarousel;
//import org.vaadin.virkki.carousel.client.widget.gwt.ArrowKeysMode;
//import org.vaadin.virkki.carousel.client.widget.gwt.CarouselLoadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.menueplanverwaltung.MenueplanGridLayout;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueplanverwaltung;
import de.bistrosoft.palaver.util.CalendarWeek;
import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;

@SuppressWarnings("serial")
public class MenueplanHistorie extends VerticalLayout implements View {

	// Variablen und Komponenten
	private VerticalLayout box = new VerticalLayout();
	MenueplanGridLayout Menueplan = null;
	Label lbKW = null;
	Label lbPlatzhalter1 = new Label();
	Label lbPlatzhalter2 = new Label();
	// Fußnoten
	@SuppressWarnings("deprecation")
	Label lbFussnoten = new Label(
			"<div align=center>ohne Gewähr &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (v) = vegan &nbsp;&nbsp; (vm) = vegan mögl. &nbsp;&nbsp; (veg.m) = vegetarisch mögl. &nbsp;&nbsp; (Z) = ohne Zwiebel &nbsp;&nbsp; (Zm) = ohne Zwiebel mögl. <BR> (K) = ohne Knoblauch &nbsp;&nbsp; (Km) = ohne Knoblauch mögl. &nbsp;&nbsp; (W) = ohne Weizen &nbsp;&nbsp; (Wm) = ohne Weizen mögl. &nbsp;&nbsp; (M) = ohne KuhMilch &nbsp;&nbsp; (Mm) = ohne KuhMilch mögl.</div>",
			Label.CONTENT_XHTML);
	Label lbPlatzhalter = new Label();

	public MenueplanHistorie() {
		super();
		this.setSizeFull();
		this.setMargin(true);

		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.TOP_CENTER);

		// Kalender zur Datums-Auswahl
		final PopupDateField date = new PopupDateField("Datum wählen:");
		date.setWidth("150px");
		date.setDateFormat("dd.MM.yyyy");
		date.setLenient(true);
		box.addComponent(date);
		box.setComponentAlignment(date, Alignment.TOP_CENTER);
		final Button btDatumsauswahl = new Button("Menüplan anzeigen");
		btDatumsauswahl.addClickListener(new ClickListener() {
			// Click-Listener zur Datumsauswahl
			@Override
			public void buttonClick(ClickEvent event) {
				// Datum in Woche und Jahr aufteilen
				SimpleDateFormat sdf;
				Calendar cal;
				Date datum = null;
				int week;
				String sample = date.getValue().toString();
				sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy",
						Locale.ENGLISH);
				try {
					datum = sdf.parse(sample);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cal = Calendar.getInstance();
				cal.setTime(datum);
				week = cal.get(Calendar.WEEK_OF_YEAR);
				@SuppressWarnings("deprecation")
				int year = date.getValue().getYear() + 1900;
				// alte Anzeigen löschen
				if (Menueplan != null) {
					box.removeComponent(Menueplan);
					box.removeComponent(lbKW);
					box.removeComponent(lbPlatzhalter1);
					box.removeComponent(lbPlatzhalter2);
				}
				// nur alte Pläne
				Week curWeek = CalendarWeek.getCurrentWeek();
				final int woche = curWeek.getWeek();
				if (week < woche) {
					// nur gespeicherte Pläne
					if (Menueplanverwaltung.getInstance().getMenueplanByWeek(
							new Week(week, year)) == null) {
						Notification notification = new Notification(
								"Kein Menüplan vorhanden.");
						notification.setDelayMsec(500);
						notification.show(Page.getCurrent());
					} else {
						// Anzeige
						Menueplan = new MenueplanGridLayout(week, year);
						Menueplan.layout.setDragMode(LayoutDragMode.NONE);
						String strKW = new String("Kalenderwoche: " + week
								+ "/" + year);
						@SuppressWarnings("deprecation")
						Label lbKW2 = new Label(
								"<pre><div align=center><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
										+ strKW + "</div></pre>",
								Label.CONTENT_XHTML);
						lbKW = lbKW2;
						lbPlatzhalter1.setHeight("30px");
						box.addComponent(lbPlatzhalter1);
						box.setComponentAlignment(lbPlatzhalter1,
								Alignment.TOP_CENTER);
						box.addComponent(lbKW);
						box.setComponentAlignment(lbKW, Alignment.TOP_CENTER);
						lbPlatzhalter2.setHeight("30px");
						box.addComponent(lbPlatzhalter2);
						box.setComponentAlignment(lbPlatzhalter2,
								Alignment.TOP_CENTER);
						box.addComponent(Menueplan);
						box.setComponentAlignment(Menueplan,
								Alignment.MIDDLE_CENTER);
						box.addComponent(lbFussnoten);
						box.setComponentAlignment(lbFussnoten,
								Alignment.BOTTOM_CENTER);
					}
				} else {
					Notification notification = new Notification(
							"Menüplan muss in der Vergangenheit liegen.");
					notification.setDelayMsec(500);
					notification.show(Page.getCurrent());
				}
			}
		});

		lbPlatzhalter.setHeight("30px");
		box.addComponent(lbPlatzhalter);
		box.setComponentAlignment(lbPlatzhalter, Alignment.TOP_CENTER);
		box.addComponent(btDatumsauswahl);
		box.setComponentAlignment(btDatumsauswahl, Alignment.TOP_CENTER);

	}

	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub

	}

}

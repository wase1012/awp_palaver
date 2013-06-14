package de.bistrosoft.palaver.gui.view;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

import de.bistrosoft.palaver.kuchenrezeptverwaltung.KuchenplanLayout;
import de.bistrosoft.palaver.util.CalendarWeek;
import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;

/**
 * @author Christine Hartkorn
 * 
 */
@SuppressWarnings("serial")
public class KuchenplanAnzeigen extends VerticalLayout implements View {


	// Variablen und Komponenten
	private VerticalLayout box = new VerticalLayout();

	Week curWeek = CalendarWeek.getCurrentWeek();
	final int week = curWeek.getWeek()  + 2;
	final int year = curWeek.getYear();

	KuchenplanLayout curKuchenplan = new KuchenplanLayout(week, year);
	KuchenplanLayout nextKuchenplan = new KuchenplanLayout(week + 1, year);
	KuchenplanLayout prevKuchenplan = new KuchenplanLayout(week - 1, year);

	HorizontalLayout hlChangeWeek = new HorizontalLayout();
	private Button btForeWeek = new Button();
	private Button btNextWeek = new Button();
	private Button platzhalter1 = new Button();
	private Button platzhalter2 = new Button();
	private String strKW = new String("Kalenderwoche: " + week + "/" + year);

	private Label lbKW = new Label(
			"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
					+ strKW + "</pre>", ContentMode.HTML);
	KuchenplanLayout shownKuchenplan = curKuchenplan;

	public KuchenplanAnzeigen() {
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

			// Click-Listener f�r eine Woche vorher
			@Override
			public void buttonClick(ClickEvent event) {
				if (shownKuchenplan == curKuchenplan) {
					box.replaceComponent(shownKuchenplan, prevKuchenplan);
					shownKuchenplan = prevKuchenplan;
					strKW = "Kalenderwoche: " + (week - 1) + "/" + year;

					Label lbForeWeek = new Label(
							"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
									+ strKW + "</pre>", ContentMode.HTML);
					hlChangeWeek.replaceComponent(lbKW, lbForeWeek);
					lbKW = lbForeWeek;
					left.replaceComponent(btForeWeek, platzhalter1);

				}
				if (shownKuchenplan == nextKuchenplan) {
					box.replaceComponent(shownKuchenplan, curKuchenplan);
					shownKuchenplan = curKuchenplan;
					strKW = "Kalenderwoche: " + (week) + "/" + year;

					Label lbForeWeek = new Label(
							"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
									+ strKW + "</pre>", ContentMode.HTML);
					hlChangeWeek.replaceComponent(lbKW, lbForeWeek);
					lbKW = lbForeWeek;
					left.replaceComponent(platzhalter1, btForeWeek);
					right.replaceComponent(platzhalter2, btNextWeek);
				}

			}

		});

		// Pfeil zum Wechseln zur nächsten KW und Anzeige der Wochen-Nr
		btNextWeek.setStyleName(BaseTheme.BUTTON_LINK);
		btNextWeek.setIcon(new ThemeResource("img/woche_spaterklein.png"));
		btNextWeek.addStyleName("menueplan-nextweek");
		btNextWeek.addClickListener(new ClickListener() {

			// Click-Listener f�r eine Woche später
			@Override
			public void buttonClick(ClickEvent event) {
				if (shownKuchenplan == curKuchenplan) {
					box.replaceComponent(shownKuchenplan, nextKuchenplan);
					shownKuchenplan = nextKuchenplan;
					strKW = "Kalenderwoche: " + (week + 1) + "/" + year;

					Label lbNextWeek = new Label(
							"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
									+ strKW + "</pre>", ContentMode.HTML);
					hlChangeWeek.replaceComponent(lbKW, lbNextWeek);
					lbKW = lbNextWeek;
					right.replaceComponent(btNextWeek, platzhalter2);
				}
				if (shownKuchenplan == prevKuchenplan) {
					box.replaceComponent(shownKuchenplan, curKuchenplan);
					shownKuchenplan = curKuchenplan;
					strKW = "Kalenderwoche: " + (week) + "/" + year;

					Label lbNextWeek = new Label(
							"<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
									+ strKW + "</pre>", ContentMode.HTML);
					hlChangeWeek.replaceComponent(lbKW, lbNextWeek);
					lbKW = lbNextWeek;
					right.replaceComponent(platzhalter2, btNextWeek);
					left.replaceComponent(platzhalter1, btForeWeek);
				}
			}

		});

		// Hinzuf�gen und Anordnen der Komponenten
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

		// Button zum Speichern des Men�plans
		Button btSpeichern = new Button("Speichern");

		btSpeichern.addClickListener(new ClickListener() {
			// Click-Listener zum Speichern
			@Override
			public void buttonClick(ClickEvent event) {
				// alle Felder durchgehen, pr�fen ob menuecomponent vorhanden
				// ist und wenn ja speichern
				int week = shownKuchenplan.getKuchenplan().getWeek().getWeek();
				int year = shownKuchenplan.getKuchenplan().getWeek().getYear();
				shownKuchenplan.speichern(week, year);
			}
		});

				// Hinzuf�gen und Anordnen weiterer Komponenten
		Label lbPlatzhalter = new Label(" ");
		lbPlatzhalter.setHeight("60px");
		box.addComponent(btSpeichern);
		box.addComponent(curKuchenplan);
		box.addComponent(lbPlatzhalter);
		box.setComponentAlignment(curKuchenplan, Alignment.MIDDLE_CENTER);
		box.setComponentAlignment(btSpeichern, Alignment.MIDDLE_LEFT);
		box.setComponentAlignment(lbPlatzhalter, Alignment.BOTTOM_CENTER);
	}

	public static void switchKuchenplan() {

	}

	@Override
	public void getViewParam(ViewData data) {

	}
}
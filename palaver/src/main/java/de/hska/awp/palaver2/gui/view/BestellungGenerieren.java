package de.hska.awp.palaver2.gui.view;

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
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.menueplanverwaltung.service.Menueplanverwaltung;
import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellverwaltung;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * 
 * @author PhilippT
 * 
 */

@SuppressWarnings("serial")
public class BestellungGenerieren extends VerticalLayout implements View {

	private VerticalLayout form = new VerticalLayout();

	private Button bg = new Button("Bestellungen aus dem Menüplan generieren");

	public BestellungGenerieren() {
		super();

		this.setSizeFull();
		this.setMargin(true);

		form.setSizeFull();
		form.setSpacing(true);

		final PopupDateField date = new PopupDateField("Datum wählen:");
		date.setWidth("150px");
		date.setDateFormat("dd.MM.yyyy");
		date.setLenient(true);
		form.addComponent(date);
		form.setComponentAlignment(date, Alignment.TOP_CENTER);

		form.addComponent(bg);
		form.setComponentAlignment(bg, Alignment.TOP_CENTER);
		this.addComponent(form);

		bg.addClickListener(new ClickListener() {

			public void buttonClick(ClickEvent event) {
				SimpleDateFormat sdf;
				Calendar cal;
				Date datum = null;
				int week;
				String sample = null;

				if (date.getValue() == null) {
					Notification notification = new Notification("Es wurde kein Datum ausgewählt!");
					notification.setDelayMsec(500);
					notification.show(Page.getCurrent());
				} else {
					sample = date.getValue().toString();

					sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.ENGLISH);
					try {
						datum = sdf.parse(sample);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					cal = Calendar.getInstance();
					cal.setTime(datum);
					week = cal.get(Calendar.WEEK_OF_YEAR);
					@SuppressWarnings("deprecation")
					int year = date.getValue().getYear() + 1900;

					if (Menueplanverwaltung.getInstance().getMenueplanByWeekWithItems(new Week(week, year)) == null) {
						Notification notification = new Notification("Kein Menüplan vorhanden!");
						notification.setDelayMsec(500);
						notification.show(Page.getCurrent());
					} else {

						try {

							Bestellverwaltung.getInstance().generateAllBestellungenByMenueplanAndGrundbedarf(new Week(week, year));

						} catch (Exception e) {
							e.printStackTrace();
						}

						ViewHandler.getInstance().switchView(BestellungBearbeitenAuswaehlen.class);
					}
				}
			}
		});
	}

	@Override
	public void getViewParam(ViewData data) {

	}

}

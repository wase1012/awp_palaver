package de.hska.awp.palaver2.gui.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Kuchenplanverwaltung;
import de.bistrosoft.palaver.menueplanverwaltung.ArtikelBedarf;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueplan;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueplanverwaltung;
import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellverwaltung;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * Die View ermÃ¶glicht die Auswahl eines Kalendertags und die Generierung und
 * Speicherung der Bestellungen zu der entsprechenden Kalenderwoche.
 * 
 * @author Christian Barth
 * 
 */
@SuppressWarnings("serial")
public class BestellungGenerieren extends VerticalLayout implements View {
	
	private static final Logger	log	= LoggerFactory.getLogger(BestellungLieferantAuswaehlen.class.getName());

	private VerticalLayout form = new VerticalLayout();

	private Button bg = new Button("Bestellungen aus dem Menüplan generieren");

	public BestellungGenerieren() {
		super();

		this.setSizeFull();
		this.setMargin(true);

		form.setSizeFull();
		form.setHeight("200px");
		form.setSpacing(true);

		final PopupDateField date = new PopupDateField("Kalenderwoche des Menüplans auswählen:");
		date.setWidth("150px");
		date.setDateFormat("dd.MM.yyyy");
		date.setLenient(true);
		date.setShowISOWeekNumbers(true);
		form.addComponent(date);
		form.setComponentAlignment(date, Alignment.MIDDLE_CENTER);

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
						log.error(e.toString());
					}
					cal = Calendar.getInstance();
					cal.setTime(datum);
					week = cal.get(Calendar.WEEK_OF_YEAR);
					@SuppressWarnings("deprecation")
					int year = date.getValue().getYear() + 1900;
					
					Menueplan m = null;
					
					//TODO Ändern in RezeptHasArtikel
					List<ArtikelBedarf> a = null;
					try {
						m = Menueplanverwaltung.getInstance().getMenueplanByWeekWithItems(new Week(week, year));
						//TODO
//						a = Kuchenplanverwaltung.getInstance().getKuchenartikelByWeek(new Week(week, year));
					} catch (Exception e) {
						log.error(e.toString());
					}
					if (m == null) {
						Notification notification = new Notification("Kein Menüplan vorhanden!");
						notification.setDelayMsec(500);
						notification.show(Page.getCurrent());
					} else {
						//TODO != in == ändern, wenn Methode auslesen richtig geht
						if (a != null){	
							Notification notification = new Notification("Kein Kuchenbedarf vorhanden!");
							notification.setDelayMsec(500);
							notification.show(Page.getCurrent());
						} else {
							try {
								Bestellverwaltung.getInstance().generateAllBestellungenByMenueplanAndGrundbedarf(m, a, new Week(week, year));
							} catch (Exception e) {
								log.error(e.toString());
								Notification notification = new Notification("Bestellungen konnte nicht erzeugt werden!");
								notification.setDelayMsec(500);
								notification.show(Page.getCurrent());
							}

							ViewHandler.getInstance().switchView(BestellungBearbeitenAuswaehlen.class);
						}
					}
				}
			}
		});
	}

	@Override
	public void getViewParam(ViewData data) {

	}

}

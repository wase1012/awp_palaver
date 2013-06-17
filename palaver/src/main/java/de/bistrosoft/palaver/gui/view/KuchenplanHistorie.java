package de.bistrosoft.palaver.gui.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.kuchenrezeptverwaltung.KuchenplanLayout;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Kuchenplanverwaltung;
import de.bistrosoft.palaver.util.CalendarWeek;
import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;

/**
 * @author Christine Hartkorn
 * 
 */
@SuppressWarnings("serial")
public class KuchenplanHistorie extends VerticalLayout implements View {


	// Variablen und Komponenten
	private VerticalLayout box = new VerticalLayout();
	KuchenplanLayout kuchenplan = null;
	Label lbKW = null;
	Label lbPlatzhalter1 = new Label();
	Label lbPlatzhalter2 = new Label();
	
	int exception = 0;
	
	// FußŸnoten
	Label lbFussnoten = new Label(
			"<div align=center>ohne Gewähr &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (oWe) = weizenfrei &nbsp;&nbsp; (oG) = glutenfrei &nbsp;&nbsp; (oE) = eifrei &nbsp;&nbsp; (oL) = laktosefrei <BR> (mM) = mitMandeln &nbsp;&nbsp; (mWa) = mit Walnüssen &nbsp;&nbsp; (mH) = mit Haselnüssen &nbsp;&nbsp; (mA) = mit Alkohol &nbsp;&nbsp;</div>",
			ContentMode.HTML);
	Label lbPlatzhalter = new Label();

	public KuchenplanHistorie() {
		super();
		this.setSizeFull();
		this.setMargin(true);

		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		
		// Kalender zur Datums-Auswahl
		final PopupDateField date = new PopupDateField("Datum wählen:") {
			@Override
			protected Date handleUnparsableDateString(String dateString)
					throws ConversionException {
				// Notification bei Fehleingabe
				Notification notification = new Notification(
						"Falsches Datumsformat.");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
				exception = 1;
				throw new ConversionException("Falsches Datumsformat");
			}
		};
		date.setWidth("150px");
		date.setDateFormat("dd.MM.yyyy");
		date.setLenient(true);
		date.setShowISOWeekNumbers(true);
		box.addComponent(date);
		box.setComponentAlignment(date, Alignment.TOP_CENTER);
		final Button btDatumsauswahl = new Button("Kuchenplan anzeigen");
		btDatumsauswahl.addClickListener(new ClickListener() {
			// Click-Listener zur Datumsauswahl
			@Override
			public void buttonClick(ClickEvent event) {
				if (exception == 0) {
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
						e.printStackTrace();
					}
					cal = Calendar.getInstance();
					cal.setTime(datum);
					week = cal.get(Calendar.WEEK_OF_YEAR);
					@SuppressWarnings("deprecation")
					int year = date.getValue().getYear() + 1900;
					// alte Anzeigen lÃ¶schen
					if (kuchenplan != null) {
						box.removeComponent(kuchenplan);
						box.removeComponent(lbKW);
						box.removeComponent(lbPlatzhalter1);
						box.removeComponent(lbPlatzhalter2);
					}
					// nur alte Pläne
					Week curWeek = CalendarWeek.getCurrentWeek();
					final int woche = curWeek.getWeek();
					if (week < (woche + 1)) {
						// nur gespeicherte Pläne
						if (Kuchenplanverwaltung.getInstance()
								.getKuchenplanByWeekWithItems(
										new Week(week, year)) == null) {
							Notification notification = new Notification(
									"Kein Kuchenplan vorhanden.");
							notification.setDelayMsec(500);
							notification.show(Page.getCurrent());
						} else {
							// Anzeige
							kuchenplan = new KuchenplanLayout(week, year);
							kuchenplan.kuchenTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.NONE);
							kuchenplan.itemMoTable.setDragMode(com.vaadin.ui.Table.TableDragMode.NONE);
							kuchenplan.itemDiTable.setDragMode(com.vaadin.ui.Table.TableDragMode.NONE);
							kuchenplan.itemMiTable.setDragMode(com.vaadin.ui.Table.TableDragMode.NONE);
							kuchenplan.itemDoTable.setDragMode(com.vaadin.ui.Table.TableDragMode.NONE);
							kuchenplan.itemFrTable.setDragMode(com.vaadin.ui.Table.TableDragMode.NONE);
							kuchenplan.itemSaTable.setDragMode(com.vaadin.ui.Table.TableDragMode.NONE);
							kuchenplan.itemSoTable.setDragMode(com.vaadin.ui.Table.TableDragMode.NONE);
							String strKW = new String("Kalenderwoche: " + week
									+ "/" + year);

							Label lbKW2 = new Label(
									"<pre><div align=center><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"
											+ strKW + "</div></pre>",
									ContentMode.HTML);
							lbKW = lbKW2;
							lbPlatzhalter1.setHeight("30px");
							box.addComponent(lbPlatzhalter1);
							box.setComponentAlignment(lbPlatzhalter1,
									Alignment.TOP_CENTER);
							box.addComponent(lbKW);
							box.setComponentAlignment(lbKW,
									Alignment.TOP_CENTER);
							lbPlatzhalter2.setHeight("30px");
							box.addComponent(lbPlatzhalter2);
							box.setComponentAlignment(lbPlatzhalter2,
									Alignment.TOP_CENTER);
							box.addComponent(kuchenplan);
							box.setComponentAlignment(kuchenplan,
									Alignment.MIDDLE_CENTER);
							box.addComponent(lbFussnoten);
							box.setComponentAlignment(lbFussnoten,
									Alignment.BOTTOM_CENTER);
						}
					} else {
						Notification notification = new Notification(
								"Anzeige nur von älteren Kuchenplänen möglich.");
						notification.setDelayMsec(500);
						notification.show(Page.getCurrent());
					}
				}
				exception = 0;
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

	}
}
package de.bistrosoft.palaver.gui.view;

//import org.vaadin.virkki.carousel.HorizontalCarousel;
//import org.vaadin.virkki.carousel.client.widget.gwt.ArrowKeysMode;
//import org.vaadin.virkki.carousel.client.widget.gwt.CarouselLoadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.vaadin.client.metadata.Property;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.Label;

import de.bistrosoft.palaver.menueplanverwaltung.MenueplanGridLayout;
import de.bistrosoft.palaver.util.CalendarWeek;
import de.bistrosoft.palaver.util.ViewHandler;
import de.bistrosoft.palaver.util.Week;


@SuppressWarnings("serial")
public class MenueplanHistorie extends VerticalLayout{

	// Variablen und Komponenten
	private VerticalLayout	box = new VerticalLayout();
	

	public MenueplanHistorie()
	{
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.TOP_CENTER);
		

		//Kalender zur Datums-Auswahl
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
				//TODO: Woche und Jahr aus Datum; nur Vergangenheitswerte zulassen; Meldung, wenn nichts in DB
				 SimpleDateFormat sdf;
		         Calendar cal;
		         Date datum = null;
		         int week;
		         String sample = ""+date.getValue();
		         sdf = new SimpleDateFormat("dd.MM.yyyy");
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
				int year = date.getValue().getYear()+1900;
				MenueplanGridLayout Menueplan = new MenueplanGridLayout(week, year);
				String strKW= new String("Kalenderwoche: " + week +"/"+year);
				@SuppressWarnings("deprecation")
				Label lbKW = new Label("<pre><div align=center><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"+strKW+"</div></pre>", Label.CONTENT_XHTML);
				box.replaceComponent(date, lbKW);
				box.setComponentAlignment(lbKW, Alignment.TOP_CENTER);
				box.removeComponent(btDatumsauswahl);
				box.addComponent(Menueplan);
				box.setComponentAlignment(Menueplan, Alignment.MIDDLE_CENTER);
			}
		});
		Label lbPlatzhalter =new Label();
		lbPlatzhalter.setHeight("30px");
        box.addComponent(lbPlatzhalter);
        box.setComponentAlignment(lbPlatzhalter, Alignment.TOP_CENTER);
        box.addComponent(btDatumsauswahl);
        box.setComponentAlignment(btDatumsauswahl, Alignment.TOP_CENTER);
	}
	
}

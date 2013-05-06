package de.bistrosoft.palaver.gui.view;

//import org.vaadin.virkki.carousel.HorizontalCarousel;
//import org.vaadin.virkki.carousel.client.widget.gwt.ArrowKeysMode;
//import org.vaadin.virkki.carousel.client.widget.gwt.CarouselLoadMode;

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
import de.bistrosoft.palaver.util.Week;


@SuppressWarnings("serial")
public class Menueplan extends VerticalLayout{

	// Variablen und Komponenten
	private VerticalLayout	box = new VerticalLayout();
	
	Week curWeek = CalendarWeek.getCurrentWeek(); 
	final int week = curWeek.getWeek();
	final int year = curWeek.getYear();
	
	MenueplanGridLayout curMenueplan = new MenueplanGridLayout(week, year);
	MenueplanGridLayout nextMenueplan = new MenueplanGridLayout(week+1, year);
	MenueplanGridLayout prevMenueplan = new MenueplanGridLayout(week-1, year);
	
	HorizontalLayout hlChangeWeek = new HorizontalLayout();
	private Button btForeWeek = new Button();
	private Button btNextWeek = new Button();
	private Button platzhalter1 = new Button();
	private Button platzhalter2 = new Button();
	private String strKW= new String("Kalenderwoche: " + week +"/"+year);
	@SuppressWarnings("deprecation")
	private Label lbKW = new Label("<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"+strKW+"</pre>", Label.CONTENT_XHTML);
	MenueplanGridLayout shownMenueplan = curMenueplan;

	public Menueplan()
	{
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
				if(shownMenueplan == curMenueplan) {
					box.replaceComponent(shownMenueplan, prevMenueplan);
					shownMenueplan=prevMenueplan;
					strKW= "Kalenderwoche: " + (week-1) +"/"+year;
					@SuppressWarnings("deprecation")
					Label lbForeWeek = new Label("<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"+strKW+"</pre>", Label.CONTENT_XHTML);
					hlChangeWeek.replaceComponent(lbKW,lbForeWeek);
					lbKW=lbForeWeek;
					left.replaceComponent(btForeWeek, platzhalter1);
					
				}
				if(shownMenueplan == nextMenueplan) {
					box.replaceComponent(shownMenueplan, curMenueplan);
					shownMenueplan=curMenueplan;
					strKW= "Kalenderwoche: " + (week) +"/"+year;
					@SuppressWarnings("deprecation")
					Label lbForeWeek = new Label("<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"+strKW+"</pre>", Label.CONTENT_XHTML);
					hlChangeWeek.replaceComponent(lbKW,lbForeWeek);
					lbKW=lbForeWeek;
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
			
        	// Click-Listener für eine Woche später
			@Override
			public void buttonClick(ClickEvent event) {
				if(shownMenueplan == curMenueplan) {
					box.replaceComponent(shownMenueplan, nextMenueplan);
					shownMenueplan=nextMenueplan;
					strKW= "Kalenderwoche: " + (week+1) +"/"+year;
					@SuppressWarnings("deprecation")
					Label lbNextWeek = new Label("<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"+strKW+"</pre>", Label.CONTENT_XHTML);
					hlChangeWeek.replaceComponent(lbKW,lbNextWeek);
					lbKW=lbNextWeek;
					right.replaceComponent(btNextWeek, platzhalter2);
				}
				if(shownMenueplan == prevMenueplan) {
				box.replaceComponent(shownMenueplan, curMenueplan);
				shownMenueplan=curMenueplan; 
				strKW= "Kalenderwoche: " + (week) +"/"+year;
				@SuppressWarnings("deprecation")
				Label lbNextWeek = new Label("<pre><font style=\"font-size: large\" face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">"+strKW+"</pre>", Label.CONTENT_XHTML);
				hlChangeWeek.replaceComponent(lbKW,lbNextWeek);
				lbKW=lbNextWeek;
				right.replaceComponent(platzhalter2, btNextWeek);
				left.replaceComponent(platzhalter1, btForeWeek);
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
		
		
		// Button zum Speichern des Menüplans
		Button btSpeichern = new Button("Speichern");
        
		btSpeichern.addClickListener(new ClickListener() {
			// Click-Listener zum Speichern
			@Override
			public void buttonClick(ClickEvent event) {
				//alle Felder durchgehen, prüfen ob menuecomponent vorhanden ist und wenn ja speichern
				shownMenueplan.speichern();
				int week = shownMenueplan.getMenueplan().getWeek().getWeek();
				int year = shownMenueplan.getMenueplan().getWeek().getYear();
				Notification notification = new Notification("Menüplan für Kalenderwoche " + week + "/" + year + " wurde gespeichert");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
			}
		});
		
		// Hinzufügen und Anordnen weiterer Komponenten		
		Label lbPlatzhalter = new Label(" ");
		lbPlatzhalter.setHeight("60px");
		box.addComponent(btSpeichern);
		box.addComponent(curMenueplan);
		box.addComponent(lbPlatzhalter);
		box.setComponentAlignment(curMenueplan, Alignment.MIDDLE_CENTER);
		box.setComponentAlignment(btSpeichern, Alignment.MIDDLE_LEFT);
		box.setComponentAlignment(lbPlatzhalter, Alignment.BOTTOM_CENTER);
		
		
//		HorizontalCarousel carousel = new HorizontalCarousel();
//		carousel.setArrowKeysMode(ArrowKeysMode.FOCUS);
//		carousel.setLoadMode(CarouselLoadMode.LAZY);
//		carousel.setTransitionDuration(500);
//		carousel.addComponent(prevMenueplan);
//		carousel.addComponent(curMenueplan);
//		carousel.addComponent(nextMenueplan);
//		box.addComponent(carousel);
		
	}
	
	public static void switchMenueplan(){
		
	}
}

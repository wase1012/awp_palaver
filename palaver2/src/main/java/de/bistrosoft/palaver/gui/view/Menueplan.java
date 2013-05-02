package de.bistrosoft.palaver.gui.view;

//import org.vaadin.virkki.carousel.HorizontalCarousel;
//import org.vaadin.virkki.carousel.client.widget.gwt.ArrowKeysMode;
//import org.vaadin.virkki.carousel.client.widget.gwt.CarouselLoadMode;

import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.Label;

import de.bistrosoft.palaver.menueplanverwaltung.MenueplanGridLayout;
import de.bistrosoft.palaver.util.CalendarWeek;
import de.bistrosoft.palaver.util.Week;


@SuppressWarnings("serial")
public class Menueplan extends VerticalLayout{

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
		
		/////////////
		HorizontalLayout left = new HorizontalLayout();
		HorizontalLayout right = new HorizontalLayout();
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
					Label lbForeWeek = new Label("Kalenderwoche: " + (week-1) +"/"+year);
					hlChangeWeek.replaceComponent(lbKW,lbForeWeek);
					lbKW=lbForeWeek;
				}
				if(shownMenueplan == nextMenueplan) {
					box.replaceComponent(shownMenueplan, curMenueplan);
					shownMenueplan=curMenueplan;
					Label lbForeWeek = new Label("Kalenderwoche: " + (week) +"/"+year);
					hlChangeWeek.replaceComponent(lbKW,lbForeWeek);
					lbKW=lbForeWeek;
				}
				
			}
			
		});
		

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
					Label lbNextWeek = new Label("Kalenderwoche: " + (week+1) +"/"+year);
					hlChangeWeek.replaceComponent(lbKW,lbNextWeek);
					lbKW=lbNextWeek;
				}
				if(shownMenueplan == prevMenueplan) {
				box.replaceComponent(shownMenueplan, curMenueplan);
				shownMenueplan=curMenueplan; 
				Label lbNextWeek = new Label("Kalenderwoche: " + (week) +"/"+year);
				hlChangeWeek.replaceComponent(lbKW,lbNextWeek);
				lbKW=lbNextWeek;
				}
			}
			
		});

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
		Button btSpeichern = new Button("Speichern");
        
		btSpeichern.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				//alle felder durchgehen, prüfen ob menuecomponent vorhanden ist und wenn ja speichern
				shownMenueplan.speichern();
				int week = shownMenueplan.getMenueplan().getWeek().getWeek();
				int year = shownMenueplan.getMenueplan().getWeek().getYear();
				Notification notification = new Notification("Menüplan für Kalenderwoche " + week + "/" + year + " wurde gespeichert");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
			}
		});
		
		box.addComponent(btSpeichern);
		/////////////
		
		
		box.addComponent(curMenueplan);
		box.setComponentAlignment(curMenueplan, Alignment.MIDDLE_CENTER);
		
		
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

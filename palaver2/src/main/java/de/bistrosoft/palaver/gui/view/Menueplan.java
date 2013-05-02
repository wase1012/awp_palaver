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
	
	private Button btForeWeek = new Button();
	private Button btNextWeek = new Button();
	MenueplanGridLayout shownMenueplan = curMenueplan;

	public Menueplan()
	{
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		
		/////////////
		HorizontalLayout hlChangeWeek = new HorizontalLayout();
		HorizontalLayout left = new HorizontalLayout();
		HorizontalLayout right = new HorizontalLayout();
		btForeWeek.setStyleName(BaseTheme.BUTTON_LINK);
		btForeWeek.setIcon(new ThemeResource("img/woche_vorherklein.png"));
		btForeWeek.addStyleName("menueplan-add");
		btForeWeek.addClickListener(new ClickListener() {
			
        	// Click-Listener für eine Woche vorher
			@Override
			public void buttonClick(ClickEvent event) {
				if(shownMenueplan == curMenueplan) {
					box.replaceComponent(shownMenueplan, prevMenueplan);
					shownMenueplan=prevMenueplan;
				}
				if(shownMenueplan == nextMenueplan) {
					box.replaceComponent(shownMenueplan, curMenueplan);
					shownMenueplan=curMenueplan;	
				}
			}
			
		});
		

        btNextWeek.setStyleName(BaseTheme.BUTTON_LINK);
        btNextWeek.setIcon(new ThemeResource("img/woche_spaterklein.png"));
        btNextWeek.addStyleName("menueplan-add");
        btNextWeek.addClickListener(new ClickListener() {
			
        	// Click-Listener für eine Woche später
			@Override
			public void buttonClick(ClickEvent event) {
				if(shownMenueplan == curMenueplan) {
					box.replaceComponent(shownMenueplan, nextMenueplan);
					shownMenueplan=nextMenueplan; 
				}
				if(shownMenueplan == prevMenueplan) {
				box.replaceComponent(shownMenueplan, curMenueplan);
				shownMenueplan=curMenueplan; 
				}
			}
			
		});

        left.addComponent(btForeWeek);
        left.setComponentAlignment(btForeWeek, Alignment.TOP_LEFT);
		right.addComponent(btNextWeek);
		right.setComponentAlignment(btNextWeek, Alignment.TOP_RIGHT);
        hlChangeWeek.addComponents(left, right);
		box.addComponent(hlChangeWeek);
//		box.setComponentAlignment(hlChangeWeek, Alignment.TOP_CENTER);
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

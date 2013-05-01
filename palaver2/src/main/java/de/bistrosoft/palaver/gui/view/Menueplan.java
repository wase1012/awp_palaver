package de.bistrosoft.palaver.gui.view;

//import org.vaadin.virkki.carousel.HorizontalCarousel;
//import org.vaadin.virkki.carousel.client.widget.gwt.ArrowKeysMode;
//import org.vaadin.virkki.carousel.client.widget.gwt.CarouselLoadMode;

import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

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
		Button btCurWeek = new Button("Aktuelle Woche");
		btCurWeek.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				box.replaceComponent(shownMenueplan, curMenueplan);
				shownMenueplan=curMenueplan;
			}
		});
		
		Button btPrevWeek = new Button("Vorherige Woche");
		btPrevWeek.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				box.replaceComponent(shownMenueplan, prevMenueplan);
				shownMenueplan=prevMenueplan;
			}
		});
		
		Button btNextWeek = new Button("Nächste Woche");
		btNextWeek.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				box.replaceComponent(shownMenueplan, nextMenueplan);
				shownMenueplan=nextMenueplan; 
			}
		});
		hlChangeWeek.addComponent(btPrevWeek);
		hlChangeWeek.addComponent(btCurWeek);
		hlChangeWeek.addComponent(btNextWeek);
		box.addComponent(hlChangeWeek);
		Button btSpeichern = new Button("Speichern");
        
		btSpeichern.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				//alle felder durchgehen, prüfen ob menuecomponent vorhanden ist und wenn ja speichern
				shownMenueplan.speichern();
				int week = shownMenueplan.getMenueplan().getWeek().getWeek();
				int year = shownMenueplan.getMenueplan().getWeek().getYear();
				Notification notification = new Notification("Menüplan für Kalenderwoche " + week + "/" + year + " wurde gespeichert");
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

package de.bistrosoft.palaver.gui.view;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.bistrosoft.palaver.menueplanverwaltung.MenueplanGridLayout;
import de.bistrosoft.palaver.menueplanverwaltung.WinSelectMenue;
import de.bistrosoft.palaver.util.CalendarWeek;
import de.bistrosoft.palaver.util.Week;


@SuppressWarnings("serial")
public class Menueplan extends VerticalLayout{

	private VerticalLayout	box = new VerticalLayout();
	
	Week curWeek =CalendarWeek.getCurrentWeek(); 
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
				box.replaceComponent(shownMenueplan, nextMenueplan);
				shownMenueplan=nextMenueplan;
			}
		});
		
		Button btNextWeek = new Button("Nächste Woche");
		btNextWeek.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				box.replaceComponent(shownMenueplan, prevMenueplan);
				shownMenueplan=prevMenueplan; 
			}
		});
		hlChangeWeek.addComponent(btPrevWeek);
		hlChangeWeek.addComponent(btCurWeek);
		hlChangeWeek.addComponent(btNextWeek);
		box.addComponent(hlChangeWeek);
		/////////////
		
		
		
		box.addComponent(curMenueplan);
		box.setComponentAlignment(curMenueplan, Alignment.MIDDLE_CENTER);
	}
	
	public static void switchMenueplan(){
		
	}
}

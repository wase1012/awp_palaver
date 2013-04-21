package de.bistrosoft.palaver.menuplanverwaltung;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.vaadin.ui.Button;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import de.bistrosoft.palaver.util.CalendarWeek;

import fi.jasoft.dragdroplayouts.DDGridLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultGridLayoutDropHandler;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;

@SuppressWarnings("serial")
public class DragdropGridLayout extends CustomComponent{

    private static final int ROWS = 8;
    private static final int COLUMNS = 5;
   
    public DragdropGridLayout() {
    setCaption("Grid layout");
    setSizeFull();

    VerticalLayout outer = new VerticalLayout();
    outer.setSizeFull();
    Label lbl = new Label ("sfd");
    outer.addComponent(lbl);
    setCompositionRoot(outer);

    // Create a drag and droppable grid layout
    final DDGridLayout layout = new DDGridLayout(COLUMNS, ROWS);
    int width = COLUMNS*150; 
    int height = ROWS*100;
    layout.setWidth(width+"px");
    layout.setHeight(height+"px");
   
    // Only allow dropping in the center of the grid layout cell
    layout.setComponentHorizontalDropRatio(0);
    layout.setComponentVerticalDropRatio(0);

    outer.addComponent(layout);
    outer.setExpandRatio(layout, 1);

    // Enable dragging components
    layout.setDragMode(LayoutDragMode.CLONE);

    // Enable dropping components
    layout.setDropHandler(new MenueplanGridDropHandler());
    
    // Limit dragging to only buttons
    layout.setDragFilter(new DragFilter() {      
    public boolean isDraggable(Component component) {  
    	return component instanceof MenueComponent;
        }
            });
    //Fülle Datumszeile
    for (int col = 0; col < COLUMNS; col++) {
    	ArrayList<GregorianCalendar> dates = CalendarWeek.getDatesOfWeek(new Date());
    	GregorianCalendar date = dates.get(col);
    	String strDay = date.getDisplayName(Calendar.DAY_OF_WEEK, 2, Locale.GERMANY);

    	String strDate = date.get(Calendar.DAY_OF_MONTH) + "." + 
    						date.get(Calendar.MONTH) + "." + 
    						date.get(Calendar.YEAR);
    	
    	Label lbTmp = new Label(strDay +"\r\n"+strDate);
    	lbTmp.setWidth("140px");
		layout.addComponent(lbTmp,col,0);	
        layout.setComponentAlignment(lbTmp, Alignment.MIDDLE_CENTER);
    }
    
    //Füge ADD Buttons ein
    for (int row = 2; row < ROWS; row++) {
        for (int col = 0; col < COLUMNS; col++) {
                Button btn = new Button("ADD");
                btn.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						Button tmp = event.getButton();
						for (int row = 0; row < ROWS; row++) {
					        for (int col = 0; col < COLUMNS; col++) {
					        	if(tmp.equals(layout.getComponent(col, row))) {
					        		layout.removeComponent(tmp);
					        		
					        		WinSelectMenue window = new WinSelectMenue();
					        		UI.getCurrent().addWindow(window);
					        		window.setModal(true);
					        		
					        		MenueComponent menue = new MenueComponent("Pommes mit Schnitzel");
					        		menue.setWidth("140px");
					        		layout.addComponent(menue,col,row);
					        		layout.setComponentAlignment(menue, Alignment.MIDDLE_CENTER);
					        	}
					        }
						}
						
					}
				});
                
                layout.addComponent(btn, col, row);
                layout.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
        }
    }
}

}

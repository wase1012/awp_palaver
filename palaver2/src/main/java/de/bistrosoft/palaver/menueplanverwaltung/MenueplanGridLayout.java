package de.bistrosoft.palaver.menueplanverwaltung;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;

import de.bistrosoft.palaver.util.CalendarWeek;

import fi.jasoft.dragdroplayouts.DDGridLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;

@SuppressWarnings("serial")
public class MenueplanGridLayout extends CustomComponent{

    private static final int ROWS = 8;
    private static final int COLUMNS = 6;
       
    public MenueplanGridLayout(int week, int year) {
    setCaption("Kalenderwoche: " + week +"/"+year);
    setSizeFull();

    VerticalLayout outer = new VerticalLayout();
    outer.setSizeFull();
//    Label lbl = new Label ("sfd");
//    outer.addComponent(lbl);
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
    outer.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
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
    
    //Fülle Überschriftenspalte
    @SuppressWarnings("deprecation")
	Label[] arlbUeb = {new Label("<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \nDatum</font></pre>", Label.CONTENT_XHTML),
    					new Label("<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \nKöche</font></pre>", Label.CONTENT_XHTML),
    					new Label("<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \nHauptgericht 1</font></pre>", Label.CONTENT_XHTML),
    					new Label("<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \nHauptgericht 2</font></pre>", Label.CONTENT_XHTML),
    					new Label("<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \nVegetarisches Gericht</font></pre>", Label.CONTENT_XHTML),
    					new Label("<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \nPastagericht</font></pre>", Label.CONTENT_XHTML),
    					new Label("<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \nSalat / Suppe</font></pre>", Label.CONTENT_XHTML),
    					new Label("<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \nDessert</font></pre>", Label.CONTENT_XHTML)};
    for (int i = 0; i < arlbUeb.length; i++) {
    	HorizontalLayout hl = new HorizontalLayout();
    	VerticalLayout vl = new VerticalLayout();
    	@SuppressWarnings("deprecation")
		Label horizLinie = new Label ("<pre><div style=\"width:150px;height:1px;background-color:black;\"></div></pre>", Label.CONTENT_XHTML);
    	vl.addComponent(arlbUeb[i]);
    	vl.setComponentAlignment(arlbUeb[i], Alignment.MIDDLE_CENTER);
      	vl.addComponent(horizLinie);
        vl.setComponentAlignment(horizLinie, Alignment.BOTTOM_CENTER);
    	vl.setWidth("150px");
    	vl.setHeight("100px");
    	@SuppressWarnings("deprecation")
		Label vertLinie = new Label ("<pre><div style=\"width:1px;height:100px;background-color:black;\"></div></pre>", Label.CONTENT_XHTML);
    	hl.addComponent(vl);
    	hl.addComponent(vertLinie);
    	layout.addComponent(hl,0,i);
    }
        
    //Fülle Datumszeile
	ArrayList<GregorianCalendar> dates = CalendarWeek.getDatesOfWeek(week, year);
    for (int col = 1; col < COLUMNS; col++) {
    	GregorianCalendar date = dates.get(col-1);
    	String strDay = date.getDisplayName(Calendar.DAY_OF_WEEK, 2, Locale.GERMANY);

    	String strDate = date.get(Calendar.DAY_OF_MONTH) + "." + 
    						date.get(Calendar.MONTH) + "." + 
    						date.get(Calendar.YEAR);
    	
    	Label lbTmp = new Label(strDay +"\r\n"+strDate);
    	lbTmp.setWidth("140px");
    	
    	HorizontalLayout hl = new HorizontalLayout();
    	VerticalLayout vl = new VerticalLayout();
    	@SuppressWarnings("deprecation")
		Label horizLinie = new Label ("<pre><div style=\"width:150px;height:1px;background-color:black;\"></div></pre>", Label.CONTENT_XHTML);
    	Label platzhalter = new Label ("");
    	vl.addComponent(platzhalter);
    	vl.addComponent(lbTmp);
    	vl.addComponent(horizLinie);
    	vl.setComponentAlignment(lbTmp, Alignment.MIDDLE_CENTER);
    	vl.setComponentAlignment(horizLinie, Alignment.BOTTOM_CENTER);
    	vl.setWidth("150px");
    	vl.setHeight("100px");
    	@SuppressWarnings("deprecation")
		Label vertLinie = new Label ("<pre><div style=\"width:1px;height:100px;background-color:black;\"></div></pre>", Label.CONTENT_XHTML);
    	hl.addComponent(vl);
    	if (col < COLUMNS-1){
    		hl.addComponent(vertLinie);
    	}
    	layout.addComponent(hl,col,0);
    }
    
    //Fülle Zeile für Köche
    for (int col = 1; col < COLUMNS; col++) {
    	HorizontalLayout hl = new HorizontalLayout();
    	VerticalLayout vl = new VerticalLayout();
    	ComboBox koch1 = new ComboBox();
        ComboBox koch2 = new ComboBox();
        @SuppressWarnings("deprecation")
		Label horizLinie = new Label ("<pre><div style=\"width:150px;height:1px;background-color:black;\"></div></pre>", Label.CONTENT_XHTML);
        Label platzhalter = new Label ("");
        koch1.setWidth("140px");
    	koch1.addItem("Test");
    	koch2.setWidth("140px");
    	koch2.addItem("Test");
    	vl.addComponent(platzhalter);
    	vl.addComponent(koch1);
    	vl.addComponent(koch2);
    	vl.addComponent(horizLinie);
    	vl.setComponentAlignment(platzhalter, Alignment.MIDDLE_CENTER);
    	vl.setComponentAlignment(koch1, Alignment.MIDDLE_CENTER);
    	vl.setComponentAlignment(koch2, Alignment.MIDDLE_CENTER);
    	vl.setComponentAlignment(horizLinie, Alignment.BOTTOM_CENTER);
    	vl.setWidth("150px");
    	vl.setHeight("100px");
    	@SuppressWarnings("deprecation")
		Label vertLinie = new Label ("<pre><div style=\"width:1px;height:100px;background-color:black;\"></div></pre>", Label.CONTENT_XHTML);
    	hl.addComponent(vl);
    	if (col < COLUMNS-1){
    		hl.addComponent(vertLinie);
    	}
    	layout.addComponent(hl,col,1);
    }
    
    //Füge ADD Buttons ein
    for (int row = 2; row < ROWS; row++) {
        for (int col = 0; col < COLUMNS; col++) {
        	if(layout.getComponent(col, row)==null) {
        		Button btn = new Button("ADD");
                btn.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						Button tmp = event.getButton();
						for (int row = 0; row < ROWS; row++) {
					        for (int col = 0; col < COLUMNS; col++) {
					        	if(tmp.equals(layout.getComponent(col, row))) {
					        		WinSelectMenue window = new WinSelectMenue(layout, tmp, row, col);
					        		UI.getCurrent().addWindow(window);
					        		window.setModal(true);
					        		window.setWidth("50%");
					        		window.setHeight("50%");
					        		
					        	}
					        }
						}
					}
				});
                
                HorizontalLayout hl = new HorizontalLayout();
                VerticalLayout vl = new VerticalLayout();
            	@SuppressWarnings("deprecation")
        		Label horizLinie = new Label ("<pre><div style=\"width:150px;height:1px;background-color:black;\"></div></pre>", Label.CONTENT_XHTML);
            	Label platzhalter = new Label ("");
            	vl.addComponent(platzhalter);
            	vl.addComponent(btn);
            	vl.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
            	vl.addComponent(horizLinie);
                vl.setComponentAlignment(horizLinie, Alignment.BOTTOM_CENTER);
            	vl.setWidth("150px");
            	vl.setHeight("100px");
            	@SuppressWarnings("deprecation")
        		Label vertLinie = new Label ("<pre><div style=\"width:1px;height:100px;background-color:black;\"></div></pre>", Label.CONTENT_XHTML);
            	hl.addComponent(vl);
            	if (col < COLUMNS-1){
            		hl.addComponent(vertLinie);
            	}
            	layout.addComponent(hl,col,row);
 
        	}
                
        }
    }
}

}

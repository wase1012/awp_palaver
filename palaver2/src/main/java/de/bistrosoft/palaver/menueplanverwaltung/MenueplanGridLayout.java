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
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;

import de.bistrosoft.palaver.util.CalendarWeek;

import fi.jasoft.dragdroplayouts.DDGridLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;

@SuppressWarnings("serial")
public class MenueplanGridLayout extends CustomComponent{

    // Konstanten
	private static final int ROWS = 8;
    private static final int COLUMNS = 6;
       
    // Seitenlayout erstellen
    public MenueplanGridLayout(int week, int year) {
	    setCaption("Kalenderwoche: " + week +"/"+year);
	    setSizeFull();
	
	    // vertikales Layout anlegen
	    VerticalLayout outer = new VerticalLayout();
	    outer.setSizeFull();
	    setCompositionRoot(outer);
	
	    // DragDropGridLayout erstellen
	    final DDGridLayout layout = new DDGridLayout(COLUMNS, ROWS);
	    int width = COLUMNS*150; 
	    int height = ROWS*100;
	    layout.setWidth(width+"px");
	    layout.setHeight(height+"px");
		layout.setStyleName("menueplan-grid");
		
	   
	    // Verschieben nur in die Zellenmitte erlauben
	    layout.setComponentHorizontalDropRatio(0);
	    layout.setComponentVerticalDropRatio(0);
	
	    // DragDropGridLayout zu Seitenlayout hinzufügen
	    outer.addComponent(layout);
	    outer.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
	    outer.setExpandRatio(layout, 1);
	
	    // Wegschieben der DragDropGridLayout-Komponenten ermöglichen
	    layout.setDragMode(LayoutDragMode.CLONE);
	
	    // Hinschieben der DragDropGridLayout-Komponenten ermöglichen
	    layout.setDropHandler(new MenueplanGridDropHandler());
	    
	    // Limit dragging to only buttons
	    layout.setDragFilter(new DragFilter() {      
	    public boolean isDraggable(Component component) {  
	    	return component instanceof MenueComponent;
	        }
	            });
	    
	    // Fülle Überschriftenspalte mit formatierten Labels
	    @SuppressWarnings("deprecation")
		Label[] arlbUeb = {new Label("<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \n     Datum</font></pre>", Label.CONTENT_XHTML),
	    					new Label("<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \n     Köche</font></pre>", Label.CONTENT_XHTML),
	    					new Label("<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \n     Hauptgericht 1</font></pre>", Label.CONTENT_XHTML),
	    					new Label("<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \n     Hauptgericht 2</font></pre>", Label.CONTENT_XHTML),
	    					new Label("<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \n     Vegetarisches Gericht</font></pre>", Label.CONTENT_XHTML),
	    					new Label("<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \n     Pastagericht</font></pre>", Label.CONTENT_XHTML),
	    					new Label("<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \n     Salat / Suppe</font></pre>", Label.CONTENT_XHTML),
	    					new Label("<pre><font face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"> \r \n     Dessert</font></pre>", Label.CONTENT_XHTML)};
	    for (int i = 0; i < arlbUeb.length; i++) {
	    	arlbUeb[i].setWidth("150px");
	    	arlbUeb[i].setHeight("100px");
	    	layout.addComponent(arlbUeb[i],0,i);
	    	layout.setComponentAlignment(arlbUeb[i], Alignment.MIDDLE_CENTER);
	    }
	        
	    // Fülle Datumszeile mit Wochentag und Datum
		ArrayList<GregorianCalendar> dates = CalendarWeek.getDatesOfWeek(week, year);
	    for (int col = 1; col < COLUMNS; col++) {
	    	GregorianCalendar date = dates.get(col-1);
	    	String strDay = date.getDisplayName(Calendar.DAY_OF_WEEK, 2, Locale.GERMANY);
	
	    	String strDate = date.get(Calendar.DAY_OF_MONTH) + "." + 
	    						date.get(Calendar.MONTH) + "." + 
	    						date.get(Calendar.YEAR);
	    	
	    	@SuppressWarnings("deprecation")
	    	Label lbTmp = new Label("<BR><BR>&nbsp;&nbsp;&nbsp;&nbsp;"+"\r\n"+strDay +"\r\n"+strDate, Label.CONTENT_XHTML);
	    	lbTmp.setHeight("100px");
	    	lbTmp.setWidth("149px");
	    	layout.addComponent(lbTmp,col,0);
	        layout.setComponentAlignment(lbTmp, Alignment.MIDDLE_CENTER);
	    }
	    
	    // Fülle Zeile für Köche mit DropDowm-Boxen für Namen
	    for (int col = 1; col < COLUMNS; col++) {
	    	VerticalLayout vl = new VerticalLayout();
	    	ComboBox koch1 = new ComboBox();
	        ComboBox koch2 = new ComboBox();
	    	Label platzhalter=new Label();
	        koch1.setWidth("140px");
	    	koch1.addItem("Test");
	    	koch2.setWidth("140px");
	    	koch2.addItem("Test");
	    	platzhalter.setHeight("5px");
	    	vl.addComponent(koch1);
	    	vl.addComponent(koch2);
	    	vl.addComponent(platzhalter);
	    	vl.setWidth("149px");
	    	vl.setHeight("100px");
	    	vl.setComponentAlignment(koch1, Alignment.MIDDLE_CENTER);
	    	vl.setComponentAlignment(koch2, Alignment.MIDDLE_CENTER);
	    	vl.setComponentAlignment(platzhalter, Alignment.MIDDLE_CENTER);
	    	layout.addComponent(vl,col,1);
	        layout.setComponentAlignment(vl, Alignment.MIDDLE_CENTER);
	    }
	    
	    // Füge ADD Buttons in noch leere Felder ein
	    for (int row = 2; row < ROWS; row++) {
	        for (int col = 0; col < COLUMNS; col++) {
	        	if(layout.getComponent(col, row)==null) {
//	        		MenueAddComponent btn = new MenueAddComponent();
	        		Button btn = new Button("ADD");
	                btn.addClickListener(new ClickListener() {
						
	                	// Click-Listener für ADD_Buttons
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
	                
	    	    	btn.setWidth("149px");
	    	    	btn.setHeight("100px");
	        		layout.addComponent(btn, col, row);
	                //layout.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
	        	}
	                
	        }
	    }
	}

}

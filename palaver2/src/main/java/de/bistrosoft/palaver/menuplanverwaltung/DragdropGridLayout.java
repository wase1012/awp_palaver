package de.bistrosoft.palaver.menuplanverwaltung;

import com.vaadin.ui.Button;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

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
//    layout.addStyleName("gridLayoutBorders");
   
    // Only allow dropping in the center of the grid layout cell
    layout.setComponentHorizontalDropRatio(0);
    layout.setComponentVerticalDropRatio(0);

    outer.addComponent(layout);
    outer.setExpandRatio(layout, 1);

    // Enable dragging components
    layout.setDragMode(LayoutDragMode.CLONE);

    // Enable dropping components
    layout.setDropHandler(new DefaultGridLayoutDropHandler());
    
    // Limit dragging to only buttons
    layout.setDragFilter(new DragFilter() {      
    public boolean isDraggable(Component component) {  
    	return component instanceof Label;
        }
            });
   
    for (int row = 2; row < ROWS; row++) {
        for (int col = 0; col < COLUMNS; col++) {
                Button btn = new Button("ADD");
                btn.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						
						for (int row = 0; row < ROWS; row++) {
					        for (int col = 0; col < COLUMNS; col++) {
					        	Button tmp = event.getButton();
					        	if(tmp.equals(layout.getComponent(col, row))) {
					        		layout.removeComponent(tmp);
					        		
					        		WinSelectMenue window = new WinSelectMenue();
					        		UI.getCurrent().addWindow(window);
					        		window.setModal(true);
					        		
					        		Label lbTmp = new Label("Pommes mit Schnitzel");
					        		lbTmp.setWidth("140px");
					        		layout.addComponent(lbTmp,col,row);	
					                layout.setComponentAlignment(lbTmp, Alignment.MIDDLE_CENTER);
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

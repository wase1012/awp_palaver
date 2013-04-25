package de.bistrosoft.palaver.menueplanverwaltung;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.DDGridLayout;
import fi.jasoft.dragdroplayouts.DDGridLayout.GridLayoutTargetDetails;
import fi.jasoft.dragdroplayouts.drophandlers.AbstractDefaultLayoutDropHandler;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;

@SuppressWarnings("serial")
public class MenueplanGridDropHandler extends
        AbstractDefaultLayoutDropHandler {

    private final Alignment dropAlignment;

    /**
     * Default constructor
     */
    public MenueplanGridDropHandler(){
    	dropAlignment = Alignment.MIDDLE_CENTER;
    }
    
    /**
     * Constructor
     * 
     * @param dropCellAlignment
     *            The cell alignment of the component after it has been dropped
     */
    public MenueplanGridDropHandler(Alignment dropCellAlignment) {
        this.dropAlignment = Alignment.MIDDLE_CENTER;
    }

    @Override
    protected void handleComponentReordering(DragAndDropEvent event) {
        GridLayoutTargetDetails details = (GridLayoutTargetDetails) event
                .getTargetDetails();
        
        DDGridLayout layout = (DDGridLayout) details.getTarget();
        
        LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
                .getTransferable();
                
        //Finde Quellkomponente, Quellzeile, Quellspalte
        Component sourceComp = transferable.getComponent();
        Integer sourceRow =-1;
        Integer sourceColumn=-1;
        
        final int COLUMNS = layout.getColumns();
        final int ROWS = layout.getRows();
        
        for (int row = 0; row < ROWS; row++) {
	        for (int col = 0; col < COLUMNS; col++) {
	        	if(sourceComp.equals(layout.getComponent(col, row))) {
	        		sourceColumn=col;
	        		sourceRow=row;
	        	}
	        }
        }
        
        //Finde Zielkomponente, Zielzeile, Zielspalte
        Integer destRow = details.getOverRow();
        Integer destColumn = details.getOverColumn();
        Component destComp = details.getOverComponent();
        
        Boolean isDoDND = true;
        Boolean isSwitch = true;
        
        //Prüfe, dass Komponente nicht in die ersten zwei Zeilen gedropped wird
        //Prüfe, dass Zielkomponente ungleich Quelkomponente ist
        if (!(destRow<2) && (destComp!=sourceComp) && isDoDND) {
        	//Lösche Ziel- und Quellkomponente
        	layout.removeComponent(sourceComp);
            layout.removeComponent(destComp);
            //Füge Quellkomponente wieder ein
            layout.addComponent(sourceComp,destColumn,destRow);
            layout.setComponentAlignment(sourceComp, dropAlignment);
            
            //Wenn Zielkomponente vorhanden füge diese ein
            if (destComp!=null){
            	if(isSwitch){
            		layout.addComponent(destComp, sourceColumn, sourceRow);
                	layout.setComponentAlignment(destComp, dropAlignment);
            	}
            	else {
            		Button btAdd = new Button();
            		layout.addComponent(btAdd, sourceColumn, sourceRow);
                	layout.setComponentAlignment(btAdd, dropAlignment);
            	}
            	
            }
        }
    }

    @SuppressWarnings("deprecation")
	@Override
    protected void handleDropFromLayout(DragAndDropEvent event) {
        LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
                .getTransferable();
        GridLayoutTargetDetails details = (GridLayoutTargetDetails) event
                .getTargetDetails();
        DDGridLayout layout = (DDGridLayout) details.getTarget();
        Component source = event.getTransferable().getSourceComponent();
        Component comp = transferable.getComponent();

        if (comp == layout) {
            // Dropping myself on myself, if parent is absolute layout then
            // move
            if (comp.getParent() instanceof DDAbsoluteLayout) {
                MouseEventDetails mouseDown = transferable.getMouseDownEvent();
                MouseEventDetails mouseUp = details.getMouseEvent();
                int movex = mouseUp.getClientX() - mouseDown.getClientX();
                int movey = mouseUp.getClientY() - mouseDown.getClientY();

                DDAbsoluteLayout parent = (DDAbsoluteLayout) comp.getParent();
                ComponentPosition position = parent.getPosition(comp);

                float x = position.getLeftValue() + movex;
                float y = position.getTopValue() + movey;
                position.setLeft(x, Sizeable.UNITS_PIXELS);
                position.setTop(y, Sizeable.UNITS_PIXELS);

                return;
            }
            
        } else {

            // Check that we are not dragging an outer layout into an inner
            // layout
            Component parent = layout.getParent();
            while (parent != null) {
                if (parent == comp) {
                    return;
                }
                parent = parent.getParent();
            }

            // Remove component from its source
            if (source instanceof ComponentContainer) {
                ComponentContainer sourceLayout = (ComponentContainer) source;
                sourceLayout.removeComponent(comp);
            }
        }

        int row = details.getOverRow();
        int column = details.getOverColumn();
        addComponent(event, comp, column, row);
    }

	protected void addComponent(DragAndDropEvent event, Component component,
            int column, int row) {
    }
}

package de.bistrosoft.palaver.gui.view;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.bistrosoft.palaver.data.RegelDAO;
import de.bistrosoft.palaver.regelverwaltung.domain.Regel;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class RegelnAnzeigen extends VerticalLayout implements View {

	HorizontalLayout box = new HorizontalLayout();
	VerticalLayout oben = new VerticalLayout();
	HorizontalLayout unten = new HorizontalLayout();
	
	Button bearbeiten = new Button(IConstants.BUTTON_EDIT);
	Button neu = new Button(IConstants.BUTTON_NEW);
	Button löschen = new Button (IConstants.BUTTON_DELETE);
	Table table = new Table();
	private Label label = new Label("Alle Regeln");
	
	BeanItemContainer<Regel> container;

	Regel regel;
	 
    public RegelnAnzeigen() {

    	this.setSizeFull();
    	this.setMargin(true);
    	this.addComponent(box);
    	
        this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
        
    try {
		container = new BeanItemContainer<Regel>(Regel.class, RegelDAO.getInstance().getAllRegeln());
	} catch (Exception e) {
		e.printStackTrace();
	} 

	table.setContainerDataSource(container);
    table.setSelectable(true);
    table.setVisibleColumns(new Object[] {"zeile", "spalte", "regeltyp", "operator", "kriterien", "fehlermeldung", "aktiv"});
    
//	table.addGeneratedColumn("AKTIV", new ColumnGenerator() {
//		
//		@Override
//		public Object generateCell(Table source, Object itemId, Object columnId) {
//			CheckBox b = new CheckBox();
//			int s = container.size();
//			for(int i = 1; i<s; i++) {
//				System.out.println(container.getContainerProperty(i, "aktiv").getValue());
//				if((Boolean) container.getContainerProperty(i, "aktiv").getValue()) {
//					System.out.print((Boolean) container.getContainerProperty(i, "aktiv").getValue());
//					b.setValue(true);
//					System.out.println("TRUE");
//				}
//				else {
//					System.out.println("FALSE");
//				}
//			}
//			
//			
//			return b;
//		}
//	});
	label.setStyleName("ViewHeadline");
    oben.addComponents(label, table);
    oben.setSpacing(true);
    box.addComponents(oben);
    löschen.setIcon(new ThemeResource(IConstants.BUTTON_DELETE_ICON));
    löschen.setVisible(false);
    bearbeiten.setVisible(false);
    neu.setIcon(new ThemeResource(IConstants.BUTTON_NEW_ICON));
    bearbeiten.setIcon(new ThemeResource(IConstants.BUTTON_EDIT_ICON));
    
    unten.addComponents(neu, bearbeiten, löschen);
    oben.addComponent(unten);
    box.setComponentAlignment(oben, Alignment.MIDDLE_CENTER);
    oben.setComponentAlignment(unten, Alignment.MIDDLE_RIGHT);

    neu.addClickListener(new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			ViewHandler.getInstance().switchView(RegelAnlegen.class);

		}
	});
    
    table.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					regel = (Regel) event.getProperty().getValue();

				}

			}
		});
    
	table.addItemClickListener(new ItemClickListener() {	
		@Override
		public void itemClick(ItemClickEvent event) {
			
			bearbeiten.setVisible(true);
			löschen.setVisible(true);
			if(event.isDoubleClick()){
				ViewHandler.getInstance().switchView(RegelAnlegen.class, new ViewDataObject<Regel>(regel));
			}
			
		}
	});
    
    bearbeiten.addClickListener(new ClickListener() {
    	
		@Override
		public void buttonClick(ClickEvent event) {
			Regel itemId = (Regel) table.getValue();
			ViewHandler.getInstance().switchView(RegelAnlegen.class, new ViewDataObject<Regel>(itemId));
			
		}
	});
    
    löschen.addClickListener(new ClickListener() {
		
    	
		@Override
		public void buttonClick(final ClickEvent event) {
			Regel.löschen(regel);
			ViewHandler.getInstance().switchView(RegelnAnzeigen.class);
			
		}
	});
    
    }

	@Override
	public void getViewParam(ViewData data) {
	}	

}

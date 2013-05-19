package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.bistrosoft.palaver.data.RegelDAO;
import de.bistrosoft.palaver.regelverwaltung.WinCreateRegel;
import de.bistrosoft.palaver.regelverwaltung.domain.Regel;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;

@SuppressWarnings("serial")
public class RegelnAnzeigen extends VerticalLayout implements View{

	BeanItemContainer<Regel> container;
	Table table = new Table();
	Button bt = new Button("Neue Regel");
	HorizontalLayout box = new HorizontalLayout();
	VerticalLayout oben = new VerticalLayout();
	VerticalLayout unten = new VerticalLayout();
	 
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
    table.setVisibleColumns(new Object[] {"zeile", "spalte", "regeltyp", "operator", "kriterien", "fehlermeldung"});
    oben.addComponents(table);
    oben.setSpacing(true);
    box.addComponents(oben);
    unten.addComponent(bt);
    oben.addComponent(unten);
    box.setComponentAlignment(oben, Alignment.MIDDLE_CENTER);
    oben.setComponentAlignment(unten, Alignment.MIDDLE_RIGHT);

    
    bt.addClickListener(new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			WinCreateRegel window = new WinCreateRegel();
			UI.getCurrent().addWindow(window);
    		window.setModal(true);
    		window.setWidth("60%");
    		window.setHeight("60%");
		}
	});
    
    table.refreshRowCache();
    
    }
    

    
	@Override
	public void getViewParam(ViewData data) {
	}	

}

package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.artikelverwaltung.service.Kategorienverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;
/**
 * 
 * @author Mihail Boehm
 *
 */
@SuppressWarnings("serial")
public class KategorienAnzeigen  extends VerticalLayout  implements View{

	private VerticalLayout layout = new VerticalLayout();	
	private Button hinzufuegen = new Button(IConstants.BUTTON_ADD);
	private Table table;
	
	public KategorienAnzeigen(){
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		layout.setWidth("40%");
		layout.setMargin(true);
		layout.setSpacing(true);
		table = new Table();
		table.setSizeFull();
		layout.addComponent(table);
		layout.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
		layout.addComponent(hinzufuegen);
		layout.setComponentAlignment(hinzufuegen, Alignment.MIDDLE_RIGHT);
		
		hinzufuegen.setIcon(new ThemeResource(IConstants.BUTTON_ADD_ICON));
		
		BeanItemContainer<Kategorie> container;
		try
		{
			container = new BeanItemContainer<Kategorie>(Kategorie.class, Kategorienverwaltung.getInstance().getAllKategories());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "name"});
			table.sort(new Object[] {"name"}, new boolean[] {true});
		} 
		catch (IllegalArgumentException | ConnectException | DAOException
				| SQLException e)
		{
			e.printStackTrace();
		}	
		
		this.addComponent(layout);
		this.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
		
		hinzufuegen.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(KategorieErstellen.class);				
			}
		});
	}
	
	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}

}

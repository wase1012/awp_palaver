/**
 * 
 */
package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.bistrosoft.palaver.util.customFilter;
import de.bistrosoft.palaver.util.customFilterDecorator;

/**
 * @author Android
 * 
 */
@SuppressWarnings("serial")
public class RezeptAnzeigenTabelle extends VerticalLayout {

	private FilterTable table;

	public RezeptAnzeigenTabelle() {
		super();
		this.setSizeFull();
		this.setMargin(true);
		table = new FilterTable();
		table.setSizeFull();
		table.setSelectable(true);
		table.setFilterBarVisible(true);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());

		BeanItemContainer<Rezept> container;
		try{
			container = new BeanItemContainer<Rezept>(Rezept.class, Rezeptverwaltung.getInstance().getAllRezepte());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] {"name", "rezeptart", "geschmack", "mitarbeiter"});
			table.sort(new Object[] {"name"}, new boolean[] {true});
			}
			catch (IllegalArgumentException  | ConnectException | DAOException | SQLException  e){
				e.printStackTrace();
			}
			this.addComponent(table);
		
	
		}
}
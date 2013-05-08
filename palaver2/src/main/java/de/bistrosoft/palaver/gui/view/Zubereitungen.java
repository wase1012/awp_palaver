package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Zubereitungverwaltung;
import de.bistrosoft.palaver.util.customFilter;
import de.bistrosoft.palaver.util.customFilterDecorator;

/**
 * @author Michael Marschall
 *
 */
public class Zubereitungen extends VerticalLayout{

	private static final long serialVersionUID = 2474121007841510011L;
	
	private FilterTable table;

	public Zubereitungen() {
		super();
		table = new FilterTable();
		table.setSizeFull();
		table.setSelectable(true);
		table.setFilterBarVisible(true);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());

		BeanItemContainer<Zubereitung> container;
		try{
			container = new BeanItemContainer<Zubereitung>(Zubereitung.class, Zubereitungverwaltung.getInstance().getAllZubereitung());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] {"id", "name"});
			table.sort(new Object[] {"name"}, new boolean[] {true});
			}
			catch (IllegalArgumentException  | ConnectException | DAOException | SQLException  e){
				e.printStackTrace();
			}
		this.addComponent(table);
		
	
		}
}

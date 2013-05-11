package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Zubereitungverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

/**
 * @author Michael Marschall
 *
 */
public class Zubereitungen extends VerticalLayout implements View{

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

	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}
}

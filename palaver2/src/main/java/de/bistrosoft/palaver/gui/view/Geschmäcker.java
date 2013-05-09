package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import de.bistrosoft.palaver.util.View;
import de.bistrosoft.palaver.util.ViewData;
import de.bistrosoft.palaver.util.customFilter;
import de.bistrosoft.palaver.util.customFilterDecorator;

/**
 * @author Michael Marschall
 * 
 */
public class Geschmäcker extends VerticalLayout implements View {

	private static final long serialVersionUID = 2474121007841510011L;

	private FilterTable table;

	public Geschmäcker() {
		super();
		table = new FilterTable();
		table.setSizeFull();
		table.setSelectable(true);
		table.setFilterBarVisible(true);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());

		BeanItemContainer<Geschmack> container;
		try {
			container = new BeanItemContainer<Geschmack>(Geschmack.class,
					Geschmackverwaltung.getInstance().getAllGeschmack());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "id", "name" });
			table.sort(new Object[] { "name" }, new boolean[] { true });
		} catch (IllegalArgumentException | ConnectException | DAOException
				| SQLException e) {
			e.printStackTrace();
		}
		this.addComponent(table);

	}

	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}
}
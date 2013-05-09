package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptartverwaltung;
import de.bistrosoft.palaver.util.View;
import de.bistrosoft.palaver.util.ViewData;
import de.bistrosoft.palaver.util.customFilter;
import de.bistrosoft.palaver.util.customFilterDecorator;

/**
 * @author Michael Marschall
 * 
 */
public class Rezeptarten extends VerticalLayout implements View {

	private static final long serialVersionUID = 2474121007841510011L;

	private FilterTable table;

	public Rezeptarten() {
		super();
		table = new FilterTable();
		table.setSizeFull();
		table.setSelectable(true);
		table.setFilterBarVisible(true);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());

		BeanItemContainer<Rezeptart> container;
		try {
			container = new BeanItemContainer<Rezeptart>(Rezeptart.class,
					Rezeptartverwaltung.getInstance().getAllRezeptart());
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
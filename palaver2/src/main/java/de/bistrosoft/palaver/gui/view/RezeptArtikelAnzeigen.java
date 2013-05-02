package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import de.bistrosoft.palaver.artikelverwaltung.domain.Artikel;
import de.bistrosoft.palaver.artikelverwaltung.service.Artikelverwaltung;
import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.util.IConstants;
import de.bistrosoft.palaver.util.customFilter;
import de.bistrosoft.palaver.util.customFilterDecorator;

/**
 * 
 * @author Sebastian Walz
 * 
 */

// von der anderen Gruppe

@SuppressWarnings("serial")
public class RezeptArtikelAnzeigen extends VerticalLayout {
	private FilterTable table;

	private Button showFilter;

	public RezeptArtikelAnzeigen() {
		super();

		this.setSizeFull();
		this.setMargin(true);

		showFilter = new Button("Filter anzeigen");

		table = new FilterTable();
		table.setSizeFull();
		table.setFilterBarVisible(false);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());

		BeanItemContainer<Artikel> container;
		try {
			container = new BeanItemContainer<Artikel>(Artikel.class,
					Artikelverwaltung.getInstance().getAllArtikelName());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "name" });
			table.sort(new Object[] { "name" }, new boolean[] { true });
		} catch (IllegalArgumentException | ConnectException | DAOException
				| SQLException e) {
			e.printStackTrace();
		}

		this.addComponent(showFilter);
		this.setComponentAlignment(showFilter, Alignment.MIDDLE_RIGHT);
		this.addComponent(table);
		this.setExpandRatio(table, 1);

		showFilter.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (table.isFilterBarVisible()) {
					table.setFilterBarVisible(false);
					table.resetFilters();
					showFilter.setCaption("Filter anzeigen");

				} else {
					table.setFilterBarVisible(true);
					showFilter.setCaption("Filter verbergen");

				}
			}
		});
	}
}
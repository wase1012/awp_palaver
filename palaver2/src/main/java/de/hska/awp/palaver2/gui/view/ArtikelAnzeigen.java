/**
 * Created by Sebastian Walz
 * 24.04.2013 16:03:13
 */
package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

@SuppressWarnings("serial")
public class ArtikelAnzeigen extends VerticalLayout
{
	private FilterTable		table;
	
	public ArtikelAnzeigen()
	{
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		table = new FilterTable();
		table.setSizeFull();
		table.setFilterBarVisible(true);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());
		
		BeanItemContainer<Artikel> container;
		try
		{
			container = new BeanItemContainer<Artikel>(Artikel.class, Artikelverwaltung.getInstance().getAllArtikel());
			table.setContainerDataSource(container);
		} 
		catch (IllegalArgumentException | ConnectException | DAOException
				| SQLException e)
		{
			e.printStackTrace();
		}	
		
		this.addComponent(table);
	}
}

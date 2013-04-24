/**
 * Created by Sebastian Walz
 * 24.04.2013 16:03:13
 */
package de.hska.awp.palaver2.gui.view;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;

@SuppressWarnings("serial")
public class ArtikelAnzeigen extends VerticalLayout
{
	private Table		table;
	
	public ArtikelAnzeigen()
	{
		super();
		
		this.setSizeFull();
		table = new Table();
		table.setSizeFull();
		
		BeanItemContainer<Artikel> container = new BeanItemContainer<Artikel>(Artikel.class, Artikelverwaltung.getInstance().getAllArtikel());
		table.setContainerDataSource(container);
		
		this.addComponent(table);
	}
}

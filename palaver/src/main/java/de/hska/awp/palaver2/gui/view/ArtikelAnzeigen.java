/**
 * Created by Sebastian Walz
 * 24.04.2013 16:03:13
 */
package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

/**
 * @author Sebastian Walz
 *	Diese Klasse gibt eine Tabelle aus, in der alle Artikel angezeigt werden. 
 *	Klick man doppelt auf einen, kommt man direkt zur UpdateForm.
 */
@SuppressWarnings("serial")
public class ArtikelAnzeigen extends VerticalLayout  implements View
{
	private FilterTable		table;
	
	private Button			showFilter;
	
	private Artikel			artikel;
	
	public ArtikelAnzeigen()
	{
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		
		showFilter = new Button(IConstants.BUTTON_SHOW_FILTER);
		showFilter.setIcon(new ThemeResource("img/filter.ico"));
		
		table = new FilterTable();
		table.setCaption("Alle Artikel");
		table.setStyleName("palaverTable");
		table.setSizeFull();
		table.setFilterBarVisible(false);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());
		table.setSelectable(true);
		
		table.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(event.getProperty().getValue() != null) {
					artikel = (Artikel) event.getProperty().getValue();
				}
			}
		});
		
		table.addItemClickListener(new ItemClickListener() {	
			@Override
			public void itemClick(ItemClickEvent event) {
				if(event.isDoubleClick()){
					ViewHandler.getInstance().switchView(ArtikelErstellen.class, new ViewDataObject<Artikel>(artikel));
				}
				
			}
		});
		
		BeanItemContainer<Artikel> container;
		try
		{
			container = new BeanItemContainer<Artikel>(Artikel.class, Artikelverwaltung.getInstance().getAllArtikel());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] {"name", "artikelnr", "lieferant", "kategorie", "preis", "bestellgroesse"});
			table.sort(new Object[] {"name"}, new boolean[] {true});
		} 
		catch (IllegalArgumentException | ConnectException | DAOException
				| SQLException e)
		{
			e.printStackTrace();
		}	
		
		this.addComponent(showFilter);
		this.setComponentAlignment(showFilter, Alignment.MIDDLE_RIGHT);
		this.addComponent(table);
		this.setExpandRatio(table, 1);
		
		showFilter.addClickListener(new ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				if (table.isFilterBarVisible())
				{
					table.setFilterBarVisible(false);
					table.resetFilters();
					showFilter.setCaption(IConstants.BUTTON_SHOW_FILTER);
					showFilter.setIcon(new ThemeResource("img/filter.ico"));
				}
				else
				{
					table.setFilterBarVisible(true);
					showFilter.setCaption(IConstants.BUTTON_HIDE_FILTER);
					showFilter.setIcon(new ThemeResource("img/disable_filter.ico"));
				}
			}
		});
	}

	@Override
	public void getViewParam(ViewData data)
	{
	}
}

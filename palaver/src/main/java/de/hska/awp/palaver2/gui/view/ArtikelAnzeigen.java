/**
 * Created by Sebastian Walz
 * 24.04.2013 16:03:13
 */
package de.hska.awp.palaver2.gui.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.CustomTable.CellStyleGenerator;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
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
	private static final Logger	log	= LoggerFactory.getLogger(ArtikelAnzeigen.class.getName());
	
	private FilterTable			table;
	
	private Button				showFilter;
	
	private Label				headline;
	
	private HorizontalLayout	head;
	
	private Artikel				artikel;
	
	public ArtikelAnzeigen()
	{
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		
		showFilter = new Button(IConstants.BUTTON_SHOW_FILTER);
		showFilter.setIcon(new ThemeResource("img/filter.ico"));
		
		headline = new Label("Alle Artikel");
		headline.setStyleName("ViewHeadline");
		
		head = new HorizontalLayout();
		head.setWidth("100%");
		
		head.addComponent(headline);
		head.setComponentAlignment(headline, Alignment.MIDDLE_LEFT);
		head.addComponent(showFilter);
		head.setComponentAlignment(showFilter, Alignment.MIDDLE_RIGHT);
		head.setExpandRatio(headline, 1);
		
		table = new FilterTable();
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
			table.setVisibleColumns(new Object[] {"name", "artikelnr", "lieferant", "kategorie", "preis", "standard", "grundbedarf", "bio", "bestellgroesse"});
			table.sort(new Object[] {"name"}, new boolean[] {true});
			
			table.setCellStyleGenerator(new CellStyleGenerator()
			{
				
				@Override
				public String getStyle(CustomTable source, Object itemId, Object propertyId)
				{
					Artikel artikel = (Artikel) itemId;
					if ("standard".equals(propertyId))
					{
						return artikel.isStandard() ? "check" : "cross";
					}
					if ("grundbedarf".equals(propertyId))
					{
						return artikel.isGrundbedarf() ? "check" : "cross";
					}
					if ("bio".equals(propertyId))
					{
						return artikel.isBio() ? "check" : "cross";
					}
					return "";
				}
			});
			table.setColumnWidth("standard", 60);
			table.setColumnWidth("grundbedarf", 80);
			table.setColumnWidth("bio", 30);
		} 
		catch (Exception e)
		{
			log.error(e.toString());
		}	
		
		this.addComponent(head);
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

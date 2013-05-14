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
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.bestellverwaltung.service.Bestellverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

@SuppressWarnings("serial")
public class BestellungLieferantAuswaehlen extends VerticalLayout implements View{

	private FilterTable		table;
	
	private Button			showFilter;
	private Lieferant 		lieferant;
	
	public BestellungLieferantAuswaehlen()	{
		
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		
		showFilter = new Button(IConstants.BUTTON_SHOW_FILTER);
		showFilter.setIcon(new ThemeResource("img/filter.ico"));
		
		table = new FilterTable();
		table.setCaption("Alle Lieferanten");
		table.setStyleName("palaverTable");
		table.setWidth("400px");
		table.setFilterBarVisible(false);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());
		table.setSelectable(true);
		
		table.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(event.getProperty().getValue() != null) {
					lieferant = (Lieferant) event.getProperty().getValue();
				}
			}
		});

		table.addItemClickListener(new ItemClickListener() {	
			@Override
			public void itemClick(ItemClickEvent event) {
				if(event.isDoubleClick()){
					ViewHandler.getInstance().switchView(ManuelleBestellungErstellen.class, new ViewDataObject<Lieferant>(lieferant));
				}
				
			}
		});
		
		BeanItemContainer<Lieferant> container;
		try
		{
			container = new BeanItemContainer<Lieferant>(Lieferant.class, Bestellverwaltung.getInstance().getLieferantenWithArtikel());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] {"name"});
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
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}

}

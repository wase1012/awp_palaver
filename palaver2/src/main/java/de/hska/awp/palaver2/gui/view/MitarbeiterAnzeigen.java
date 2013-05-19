/**
 * Created by Elena W
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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

@SuppressWarnings("serial")
public class MitarbeiterAnzeigen extends VerticalLayout  implements View {

		private FilterTable			table;
		
		private Button				showFilter;
		private Label				headline;
		
		private HorizontalLayout 	head;
		private Mitarbeiter 		mitarbeiter;
		
		public MitarbeiterAnzeigen()
		{
			super();
			
			this.setSizeFull();
			this.setMargin(true);
			
			showFilter = new Button(IConstants.BUTTON_SHOW_FILTER);
			showFilter.setIcon(new ThemeResource("img/filter.ico"));
			
			headline = new Label("Alle Mitarbeiter");
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
						mitarbeiter = (Mitarbeiter) event.getProperty().getValue();
					}
				}
			});
	
			table.addItemClickListener(new ItemClickListener() {	
				@Override
				public void itemClick(ItemClickEvent event) {
					if(event.isDoubleClick()){
						ViewHandler.getInstance().switchView(MitarbeiterSuche.class, new ViewDataObject<Mitarbeiter>(mitarbeiter));
					}
					
				}
			});
			
			BeanItemContainer<Mitarbeiter> container;
			try
			{
				container = new BeanItemContainer<Mitarbeiter>(Mitarbeiter.class, Mitarbeiterverwaltung.getInstance().getAllMitarbeiter());
				table.setContainerDataSource(container);
				table.setVisibleColumns(new Object[] {"name", "kundennummer", "bezeichnung", "strasse", "plz", "ort", "email", "telefon", "fax", "notiz"});
				table.sort(new Object[] {"name"}, new boolean[] {true});
			} 
			catch (IllegalArgumentException | ConnectException | DAOException
					| SQLException e)
			{
				e.printStackTrace();
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

		/* (non-Javadoc)
		 * @see de.hska.awp.palaver2.util.View#getViewParam(de.hska.awp.palaver2.util.ViewData)
		 */
		@Override
		public void getViewParam(ViewData data)
		{
		}
	}
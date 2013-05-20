package main.java.de.bistrosoft.palaver.gui.view;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

import main.java.de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import main.java.de.bistrosoft.palaver.menueplanverwaltung.service.Menueverwaltung;
import main.java.de.hska.awp.palaver2.util.IConstants;
import main.java.de.hska.awp.palaver2.util.View;
import main.java.de.hska.awp.palaver2.util.ViewData;
import main.java.de.hska.awp.palaver2.util.ViewDataObject;
import main.java.de.hska.awp.palaver2.util.ViewHandler;
import main.java.de.hska.awp.palaver2.util.customFilter;
import main.java.de.hska.awp.palaver2.util.customFilterDecorator;

@SuppressWarnings("serial")
public class MenueAnzeigenTabelle extends VerticalLayout implements View {

	private FilterTable table;

	private Button showFilter;
	private Menue menue;
	private Menue menue1;
 private String i;
	public MenueAnzeigenTabelle() {
		super();

		this.setSizeFull();
		this.setMargin(true);

		showFilter = new Button(IConstants.BUTTON_SHOW_FILTER);
		// showFilter.setIcon(new ThemeResource("img/filter.ico"));

		table = new FilterTable();
		table.setSizeFull();
		table.setFilterBarVisible(false);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());
		table.setSelectable(true);

		
		table.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					menue = (Menue) event.getProperty().getValue();
					
				}

			}
		});
		
		

		table.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				
				
				
				if(event.isDoubleClick()){
				ViewHandler.getInstance().switchView(MenueAnlegen.class,
						new ViewDataObject<Menue>(menue));
				}
				else {
					
				}

			}
		});

		BeanItemContainer<Menue> container;

		try {
			container = new BeanItemContainer<Menue>(Menue.class,
					Menueverwaltung.getInstance().getAllMenues());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "id", "name",
					"kochname" });
			table.sort(new Object[] { "name" }, new boolean[] { true });
		} catch (IllegalArgumentException e) {
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
					showFilter.setCaption(IConstants.BUTTON_SHOW_FILTER);
					// showFilter.setIcon(new ThemeResource("img/filter.ico"));
				} else {
					table.setFilterBarVisible(true);
					showFilter.setCaption(IConstants.BUTTON_HIDE_FILTER);
					// showFilter.setIcon(new
					// ThemeResource("img/disable_filter.ico"));
				}
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * main.java.de.hska.awp.palaver2.util.View#getViewParam(main.java.de.hska.awp.palaver2.util
	 * .ViewData)
	 */
	@Override
	public void getViewParam(ViewData data) {
	}

}
package de.bistrosoft.palaver.gui.view;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

@SuppressWarnings("serial")
public class MenueAnzeigenTabelle extends VerticalLayout implements View {

	private FilterTable table;

	private Button showFilter;
	private Menue menue;

	private Button btAuswaehlen;

	public MenueAnzeigenTabelle() {
		super();
		btAuswaehlen = new Button(IConstants.BUTTON_SELECT);
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
				if (event.isDoubleClick()) {
					ViewHandler.getInstance().switchView(MenueAnlegen.class,
							new ViewDataObject<Menue>(menue));
				} else {

				}
			}
		});

		BeanItemContainer<Menue> container;

		try {
			container = new BeanItemContainer<Menue>(Menue.class,
					Menueverwaltung.getInstance().getAllMenuesTabelle());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "id", "name", "kochname",
					"geschmack", "menueart" });
			table.setFilterBarVisible(true);
			table.sort(new Object[] { "id" }, new boolean[] { true });
			table.markAsDirty();
			table.setFilterFieldValue("kochname", ((Application) UI
					.getCurrent().getData()).getUser().getVorname());

			// TODO Filter setzen
			// Mitarbeiterverwaltung.getInstance().getMitarbeiterByBenutzername("Shanta")));

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ConversionException e) {
			e.printStackTrace();
		}

		this.addComponent(showFilter);
		this.setComponentAlignment(showFilter, Alignment.MIDDLE_RIGHT);
		this.addComponent(table);
		this.setExpandRatio(table, 1);
		this.addComponent(btAuswaehlen);

		showFilter.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				table.resetFilters();
			}
		});

		btAuswaehlen.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (table.getValue() != null
						&& table.getValue() instanceof Menue) {

					Menue menueAusTb = (Menue) table.getValue();
					ViewHandler.getInstance().switchView(MenueAnlegen.class,
							new ViewDataObject<Menue>(menueAusTb));
				}
				// TODO Fehlermeldung
				else
					;

			}
		});

	}

	@Override
	public void getViewParam(ViewData data) {
	}

}
package de.bistrosoft.palaver.gui.view;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Kuchenrezeptverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

/**
 * @author Christine Hartkorn
 * 
 */
@SuppressWarnings("serial")
public class KuchenrezeptAnzeigen extends VerticalLayout implements View {

	private FilterTable table;

	private Label ueberschrift = new Label(
			"<pre><font size='4px' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Kuchenrezepte</font></pre>",
			ContentMode.HTML);

	private Button showFilter;
	private Kuchenrezept kuchenrezept;

	private Button btAuswaehlen;

	public KuchenrezeptAnzeigen() {
		super();

		btAuswaehlen = new Button(IConstants.BUTTON_SELECT);

		this.setSizeFull();
		this.setMargin(true);

		showFilter = new Button(IConstants.BUTTON_HIDE_FILTER);

		table = new FilterTable();
		table.setSizeFull();
		table.setFilterBarVisible(true);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());
		table.setSelectable(true);

		table.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					kuchenrezept = (Kuchenrezept) event.getProperty()
							.getValue();
				}

			}
		});

		table.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					ViewHandler.getInstance().switchView(
							KuchenrezeptAnlegen.class,
							new ViewDataObject<Kuchenrezept>(kuchenrezept));
				}

			}
		});

		BeanItemContainer<Kuchenrezept> container;

		try {
			container = new BeanItemContainer<Kuchenrezept>(Kuchenrezept.class,
					Kuchenrezeptverwaltung.getInstance().getAllKuchenrezepte(
							false));
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "name", "erstellt" });
			table.sort(new Object[] { "name" }, new boolean[] { true });
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.addComponent(ueberschrift);
		this.addComponent(showFilter);
		this.setComponentAlignment(showFilter, Alignment.MIDDLE_RIGHT);
		this.addComponent(table);
		this.setExpandRatio(table, 1);
		this.addComponent(btAuswaehlen);

		showFilter.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (table.isFilterBarVisible()) {
					table.setFilterBarVisible(false);
					table.resetFilters();
					showFilter.setCaption(IConstants.BUTTON_SHOW_FILTER);
				} else {
					table.setFilterBarVisible(true);
					showFilter.setCaption(IConstants.BUTTON_HIDE_FILTER);
				}
			}
		});

		btAuswaehlen.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (table.getValue() != null
						&& table.getValue() instanceof Kuchenrezept) {

					Kuchenrezept kuchenrezeptAusTb = (Kuchenrezept) table
							.getValue();
					ViewHandler.getInstance()
							.switchView(
									KuchenrezeptAnlegen.class,
									new ViewDataObject<Kuchenrezept>(
											kuchenrezeptAusTb));
				} else {
					Notification notification = new Notification(
							"Bitte wählen Sie ein Rezept aus!");
					notification.setDelayMsec(500);
					notification.show(Page.getCurrent());
				}

			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hska.awp.palaver2.util.View#getViewParam(de.hska.awp.palaver2.util
	 * .ViewData)
	 */
	@Override
	public void getViewParam(ViewData data) {
	}

}
package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Kuchenrezeptverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.hska.awp.palaver.Application;
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
 * @author Christine Hartkorn
 * 
 */
@SuppressWarnings("serial")
public class KuchenrezeptAnzeigen extends VerticalLayout implements View {

	private HorizontalLayout hlControl = new HorizontalLayout();
	
	private FilterTable table;

	private Label ueberschrift = new Label(
			"<pre><font size='4px' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Kuchenrezepte</font></pre>",
			ContentMode.HTML);

	private Button btFilterLeeren;
	private Kuchenrezept kuchenrezept;

	private Button btAuswaehlen;
	private Button btLoeschen;
	private Button btNeuesRezept;

	public KuchenrezeptAnzeigen() {
		super();

		btAuswaehlen = new Button(IConstants.BUTTON_SELECT);
		btLoeschen = new Button(IConstants.BUTTON_DELETE);
		btLoeschen.setIcon(new ThemeResource("img/cross.ico"));
		btNeuesRezept=new Button(IConstants.BUTTON_NEW);
		btNeuesRezept.setIcon(new ThemeResource("img/new.ico"));

		this.setSizeFull();
		this.setMargin(true);

		btFilterLeeren = new Button(IConstants.BUTTON_CLEAR_FILTER);
		btFilterLeeren.setIcon(new ThemeResource("img/disable_filter.ico"));

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
		this.addComponent(btFilterLeeren);
		this.setComponentAlignment(btFilterLeeren, Alignment.MIDDLE_RIGHT);
		this.addComponent(table);
		this.setExpandRatio(table, 1);
		this.addComponent(hlControl);
		this.setComponentAlignment(hlControl, Alignment.MIDDLE_RIGHT);
		hlControl.addComponent(btNeuesRezept);
		hlControl.addComponent(btLoeschen);
		hlControl.addComponent(btAuswaehlen);

		btFilterLeeren.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				table.resetFilters();
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
		
		btLoeschen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (table.getValue() != null
						&& table.getValue() instanceof Kuchenrezept) {

					Kuchenrezept kuchenrezeptAusTb = (Kuchenrezept) table.getValue();
					try {
						Kuchenrezeptverwaltung.getInstance().setKuchenrezeptDisabled(
								kuchenrezeptAusTb);
					} catch (ConnectException e) {
						e.printStackTrace();
					} catch (DAOException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}

					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_KUCHENREZEPT_DELETE);

					ViewHandler.getInstance().switchView(
							KuchenrezeptAnzeigen.class);
				}
			}
		});
		
		btNeuesRezept.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(KuchenrezeptAnlegen.class);
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
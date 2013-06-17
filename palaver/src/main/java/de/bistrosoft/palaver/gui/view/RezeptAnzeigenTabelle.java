package de.bistrosoft.palaver.gui.view;

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
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

/**
 * @author Jan Lauinger
 * 
 */
@SuppressWarnings("serial")
public class RezeptAnzeigenTabelle extends VerticalLayout implements View {

	private FilterTable table;

	private Button btFilterLeeren;
	private Rezept rezept;

	private Button btAuswaehlen;

	private Label headline;
	
	public RezeptAnzeigenTabelle() {
		super();

		btAuswaehlen = new Button(IConstants.BUTTON_SELECT);

		this.setSizeFull();
		this.setMargin(true);

		btFilterLeeren = new Button(IConstants.BUTTON_CLEAR_FILTER);

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
					rezept = (Rezept) event.getProperty().getValue();
				}

			}
		});

		table.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					ViewHandler.getInstance().switchView(RezeptAnlegen.class,
							new ViewDataObject<Rezept>(rezept));
				}
			}
		});

		BeanItemContainer<Rezept> container;

		try {
			container = new BeanItemContainer<Rezept>(Rezept.class,
					Rezeptverwaltung.getInstance().getAllRezepteTabelle());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "name", "rezeptart",
					"mitarbeiter", "erstellt" });
			table.sort(new Object[] { "name" }, new boolean[] { true });
			table.setFilterFieldValue("mitarbeiter", ((Application) UI
					.getCurrent().getData()).getUser().getVorname());

		} catch (Exception e) {
			e.printStackTrace();
		}

		btAuswaehlen.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (table.getValue() != null
						&& table.getValue() instanceof Rezept) {

					Rezept rezeptAusTb = (Rezept) table.getValue();
					ViewHandler.getInstance().switchView(RezeptAnlegen.class,
							new ViewDataObject<Rezept>(rezeptAusTb));
				} else {
					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_REZEPTANZEIGEN_SELECT);
				}
			}
		});

		headline = new Label("Alle Rezepte");
		headline.setStyleName("ViewHeadline");
		
		this.addComponent(headline);
		this.setComponentAlignment(headline, Alignment.MIDDLE_LEFT);

		this.addComponent(btFilterLeeren);
		this.setComponentAlignment(btFilterLeeren, Alignment.MIDDLE_RIGHT);
		this.addComponent(table);
		this.setExpandRatio(table, 1);
		this.addComponent(btAuswaehlen);
		this.setComponentAlignment(btAuswaehlen, Alignment.MIDDLE_RIGHT);

		btFilterLeeren.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				table.resetFilters();
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
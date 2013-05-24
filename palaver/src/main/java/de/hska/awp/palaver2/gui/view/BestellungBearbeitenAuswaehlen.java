package de.hska.awp.palaver2.gui.view;

import java.sql.Date;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomTable.CellStyleGenerator;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellpositionverwaltung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellverwaltung;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class BestellungBearbeitenAuswaehlen extends VerticalLayout implements
		View {

	private VerticalLayout fenster = new VerticalLayout();
	
	private Button auswaehlen = new Button("auswählen");

	private FilterTable bestellungen = new FilterTable("Bestellung");
	private Bestellung bestellung;

	public BestellungBearbeitenAuswaehlen() {
		super();

		this.setSizeFull();
		this.setMargin(true);

		fenster.setSizeFull();
		fenster.setSpacing(true);
		fenster.addComponentAsFirst(bestellungen);
		
	
		fenster.addComponent(auswaehlen);
		fenster.setComponentAlignment(auswaehlen, Alignment.TOP_RIGHT);
		
		auswaehlen.addClickListener(new ClickListener()
		{
			@SuppressWarnings("unchecked")
			public void buttonClick(ClickEvent event)
			{
				ViewHandler.getInstance().switchView(BestellungBearbeiten.class, new ViewDataObject<Bestellung>(bestellung));
			}
		});

		bestellungen.setSizeFull();

		fenster.setExpandRatio(bestellungen, 1);

		bestellungen.setSelectable(true);
		bestellungen.setFilterBarVisible(true);

		BeanItemContainer<Bestellung> container;
		try {
			container = new BeanItemContainer<Bestellung>(Bestellung.class,
					Bestellverwaltung.getInstance().getAllBestellungen());
			bestellungen.setContainerDataSource(container);
			bestellungen.setVisibleColumns(new Object[] { "bestellt",
					"lieferant", "datum", "lieferdatum" });
			bestellungen.sort(new Object[] { "id" }, new boolean[] { true });
			bestellungen.setCellStyleGenerator(new CellStyleGenerator() {

				@Override
				public String getStyle(CustomTable source, Object itemId,
						Object propertyId) {
					Bestellung b = (Bestellung) itemId;
					if ("bestellt".equals(propertyId)) {
						return b.isBestellt() ? "check" : "cross";
					}

					return "";
				}
			});
			bestellungen.setColumnWidth("bestellt", 50);
		} catch (Exception e) {
			e.printStackTrace();
		}

		bestellungen.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					bestellung = (Bestellung) event.getProperty().getValue();
				}
			}
			
		});

		bestellungen.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					auswaehlen.click();
					
			}
			}
		});

		this.addComponent(fenster);
		this.setComponentAlignment(fenster, Alignment.MIDDLE_CENTER);
		
		
		
	}

	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub

	}

}

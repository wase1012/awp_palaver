package de.hska.awp.palaver2.gui.view;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomTable.CellStyleGenerator;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellpositionverwaltung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellverwaltung;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;

/**
 * 
 * @author PhilippT
 *
 */

@SuppressWarnings("serial")
public class BestellungAnzeigen extends VerticalLayout implements View{
	
	private VerticalLayout		form	= new VerticalLayout();
	private HorizontalLayout 	fenster = new HorizontalLayout();
	
	private FilterTable 		bestellungen = new FilterTable("Bestellung");
	private FilterTable			bpositionen = new FilterTable("Bestellpositionen");
	private Bestellung 			bestellung;
	
	private Button				allBestellungen = new Button("Alle Bestellungen");
	
	public BestellungAnzeigen () {
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		
		form.setSizeFull();
		form.setSpacing(true);
		
		fenster.setSizeFull();
		fenster.setSpacing(true);
		fenster.addComponentAsFirst(bestellungen);
		fenster.addComponent(bpositionen);
		
		bestellungen.setSizeFull();
		bpositionen.setSizeFull();
		
		fenster.setExpandRatio(bestellungen, 1);
		fenster.setExpandRatio(bpositionen, 2);
		
		bestellungen.setSelectable(true);
		bestellungen.setFilterBarVisible(true);
		
		bpositionen.setImmediate(true);
		bpositionen.setFilterBarVisible(true);
		bpositionen.setVisible(false);
		
		BeanItemContainer<Bestellung> container;
		try
		{
			container = new BeanItemContainer<Bestellung>(Bestellung.class, Bestellverwaltung.getInstance().getBestellungenLTWeeks());
			bestellungen.setContainerDataSource(container);
			bestellungen.setVisibleColumns(new Object[] {"bestellt", "lieferant", "datum", "lieferdatum", "lieferdatum2"});
			bestellungen.sort(new Object[] {"id"}, new boolean[] {true});
			bestellungen.setCellStyleGenerator(new CellStyleGenerator()
			{
				
				@Override
				public String getStyle(CustomTable source, Object itemId, Object propertyId)
				{
					Bestellung b = (Bestellung) itemId;
					if ("bestellt".equals(propertyId))
					{
						return b.isBestellt() ? "check" : "cross";
					}

					return "";
				}
			});
			bestellungen.setColumnWidth("bestellt", 50);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		
		bestellungen.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(event.getProperty().getValue() != null) {
					bestellung = (Bestellung) event.getProperty().getValue();
				}
				
			}
		});
		
		bestellungen.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					bpositionen.setVisible(true);
					BeanItemContainer<Bestellposition> bpcontainer;
					try
					{
						if(bestellung.getLieferant().getMehrereliefertermine()==false){
							bpcontainer = new BeanItemContainer<Bestellposition>(Bestellposition.class, Bestellpositionverwaltung.getInstance().getBestellpositionenByBestellungId(bestellung.getId()));
							bpositionen.setContainerDataSource(bpcontainer);
							bpositionen.setVisibleColumns(new Object[] {"artikelName", "durchschnitt", "kantine", "gesamt", "geliefert"});
							bpositionen.sort(new Object[] {"id"}, new boolean[] {true});
						} else {
							bpcontainer = new BeanItemContainer<Bestellposition>(Bestellposition.class, Bestellpositionverwaltung.getInstance().getBestellpositionenByBestellungId(bestellung.getId()));
							bpositionen.setContainerDataSource(bpcontainer);
							bpositionen.setVisibleColumns(new Object[] {"artikelName", "durchschnitt", "kantine", "gesamt", "freitag", "montag", "geliefert"});
							bpositionen.sort(new Object[] {"id"}, new boolean[] {true});
						}
						
					} 
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		
		form.addComponent(fenster);
		form.setComponentAlignment(fenster, Alignment.MIDDLE_CENTER);
		form.addComponent(allBestellungen);
		form.setComponentAlignment(allBestellungen, Alignment.MIDDLE_RIGHT);
		form.setExpandRatio(fenster, 9);
		form.setExpandRatio(allBestellungen, 1);
		
		this.addComponent(form);
		this.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
		
		allBestellungen.addClickListener( new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				BeanItemContainer<Bestellung> ncontainer;
				try
				{
					ncontainer = new BeanItemContainer<Bestellung>(Bestellung.class, Bestellverwaltung.getInstance().getAllBestellungen());
					bestellungen.setContainerDataSource(ncontainer);
					bestellungen.setVisibleColumns(new Object[] {"bestellt", "lieferant", "datum", "lieferdatum", "lieferdatum2"});
					bestellungen.sort(new Object[] {"id"}, new boolean[] {true});
					bestellungen.setCellStyleGenerator(new CellStyleGenerator()
					{
						
						@Override
						public String getStyle(CustomTable source, Object itemId, Object propertyId)
						{
							Bestellung b = (Bestellung) itemId;
							if ("bestellt".equals(propertyId))
							{
								return b.isBestellt() ? "check" : "cross";
							}

							return "";
						}
					});
					bestellungen.setColumnWidth("bestellt", 50);
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}	
				
			}
		});
	}
	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}

}

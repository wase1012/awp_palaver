package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;

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
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.CustomTable.CellStyleGenerator;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellpositionverwaltung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

/**
 * 
 * @author PhilippT
 *
 */

@SuppressWarnings("serial")
public class BestellungAnzeigen extends VerticalLayout implements View{
	
	private VerticalLayout		form	= new VerticalLayout();
	private HorizontalLayout 	fenster = new HorizontalLayout();
	private HorizontalLayout	control = new HorizontalLayout();
	
	private FilterTable 		bestellungen = new FilterTable("Bestellung");
	private FilterTable			bpositionen = new FilterTable("Bestellpositionen");
	private Bestellung 			bestellung;
	private Bestellposition		bestellposition;
	
	private BeanItemContainer<Bestellposition> bpcontainer;
	
	private Button				allBestellungen = new Button("Alle Bestellungen");
	private Button				zurueck = new Button("zurück");
	
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
		bestellungen.setFilterGenerator(new customFilter());
		bestellungen.setFilterDecorator(new customFilterDecorator());
		
		bpositionen.setImmediate(true);
		bpositionen.setFilterBarVisible(true);
		bpositionen.setVisible(false);
		bpositionen.setFilterGenerator(new customFilter());
		bpositionen.setFilterDecorator(new customFilterDecorator());
		bpositionen.setSelectable(true);
		
		BeanItemContainer<Bestellung> container;
		try
		{
			container = new BeanItemContainer<Bestellung>(Bestellung.class, Bestellverwaltung.getInstance().getBestellungenLTWeeks());
			bestellungen.setContainerDataSource(container);
			bestellungen.setVisibleColumns(new Object[] {"bestellt", "lieferant", "datumS", "lieferdatumS", "lieferdatum2S"});
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
					try
					{
						if(bestellung.getLieferant().getMehrereliefertermine()==false)
						{
							bpcontainer = new BeanItemContainer<Bestellposition>(Bestellposition.class, Bestellpositionverwaltung.getInstance().getBestellpositionenByBestellungId(bestellung.getId()));
							bpcontainer.sort(new Object[] {"id"}, new boolean[] {true});
							bpositionen.setContainerDataSource(bpcontainer);
							bpositionen.setVisibleColumns(new Object[] {"artikelName","bestellgroesse", "durchschnitt", "kantine", "gesamt", "geliefert"});
							bpositionen.sort(new Object[] {"id"}, new boolean[] {true});
							bpositionen.setCellStyleGenerator(new CellStyleGenerator()
							{
								
								@Override
								public String getStyle(CustomTable source, Object itemId, Object propertyId)
								{
									Bestellposition bp = (Bestellposition) itemId;
									return bp.isGeliefert() ? "status-1" : "status-none";
								}
							});
						} 
						else 
						{
							bpcontainer = new BeanItemContainer<Bestellposition>(Bestellposition.class, Bestellpositionverwaltung.getInstance().getBestellpositionenByBestellungId(bestellung.getId()));
							bpcontainer.sort(new Object[] {"id"}, new boolean[] {true});
							bpositionen.setContainerDataSource(bpcontainer);
							bpositionen.setVisibleColumns(new Object[] {"artikelName", "bestellgroesse", "durchschnitt", "kantine", "gesamt", "freitag", "montag", "geliefert"});
							bpositionen.sort(new Object[] {"id"}, new boolean[] {true});
							bpositionen.setCellStyleGenerator(new CellStyleGenerator()
							{
								
								@Override
								public String getStyle(CustomTable source, Object itemId, Object propertyId)
								{
									Bestellposition bp = (Bestellposition) itemId;
									return bp.isGeliefert() ? "status-1" : "status-none";
								}
							});
						}
						bpositionen.addValueChangeListener(new ValueChangeListener()
						{
							@Override
							public void valueChange(ValueChangeEvent event)
							{
								if(event.getProperty().getValue() != null) 
								{
									bestellposition = (Bestellposition) event.getProperty().getValue();
									bestellposition.setGeliefert(!bestellposition.isGeliefert());
									bpcontainer.removeItem(bestellposition);
									bpcontainer.addBean(bestellposition);
									bpcontainer.sort(new Object[] {"id"}, new boolean[] {true});
									try
									{
										Bestellpositionverwaltung.getInstance().updateBestellposition(bestellposition);
									} 
									catch (ConnectException e)
									{
										e.printStackTrace();
									} 
									catch (DAOException e)
									{
										e.printStackTrace();
									} 
									catch (SQLException e)
									{
										e.printStackTrace();
									}
									bpositionen.markAsDirtyRecursive();
								}
							}
						});
						bpositionen.addItemClickListener(new ItemClickListener()
						{
							@Override
							public void itemClick(ItemClickEvent event)
							{
								bpositionen.markAsDirty();
							}
						});
					} 
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		
		control.addComponent(zurueck);
		control.addComponent(allBestellungen);
		form.addComponent(fenster);
		form.setComponentAlignment(fenster, Alignment.MIDDLE_CENTER);
		form.addComponent(control);
		form.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		form.setExpandRatio(fenster, 9);
		form.setExpandRatio(control, 1);
		
		this.addComponent(form);
		this.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
		
		zurueck.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(BestellungAnzeigen.class);
				
			}
		});
		
		allBestellungen.addClickListener( new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				BeanItemContainer<Bestellung> ncontainer;
				try
				{
					ncontainer = new BeanItemContainer<Bestellung>(Bestellung.class, Bestellverwaltung.getInstance().getAllBestellungen());
					bestellungen.setContainerDataSource(ncontainer);
					bestellungen.setVisibleColumns(new Object[] {"bestellt", "lieferant", "datumS", "lieferdatumS", "lieferdatum2S"});
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

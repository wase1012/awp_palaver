package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellpositionverwaltung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;

/**
 * 
 * @author PhilippT
 *
 */

@SuppressWarnings("serial")
public class BestellungAnzeigen extends VerticalLayout implements View{
	
	private HorizontalLayout 	fenster = new HorizontalLayout();
	
	private FilterTable 		bestellungen = new FilterTable("Bestellung");
	private FilterTable			bpositionen = new FilterTable("Bestellpositionen");
	
	private Bestellung 			bestellung;
	
	public BestellungAnzeigen () {
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		
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
			container = new BeanItemContainer<Bestellung>(Bestellung.class, Bestellverwaltung.getInstance().getAllBestellungen());
			bestellungen.setContainerDataSource(container);
			bestellungen.setVisibleColumns(new Object[] {"lieferant", "datum", "lieferdatum"});
			bestellungen.sort(new Object[] {"id"}, new boolean[] {true});
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
						bpcontainer = new BeanItemContainer<Bestellposition>(Bestellposition.class, Bestellpositionverwaltung.getInstance().getBestellpositionenByBestellungId(bestellung.getId()));
						bpositionen.setContainerDataSource(bpcontainer);
						bpositionen.setVisibleColumns(new Object[] {"artikelName", "durchschnitt", "kantine", "gesamt"});
						bpositionen.sort(new Object[] {"id"}, new boolean[] {true});
		
					} 
					catch (Exception e)
					{
						e.printStackTrace();
					}
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

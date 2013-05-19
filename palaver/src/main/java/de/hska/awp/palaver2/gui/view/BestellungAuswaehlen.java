package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class BestellungAuswaehlen extends VerticalLayout implements View {

	private HorizontalLayout box = new HorizontalLayout();
	private VerticalLayout rechts = new VerticalLayout();
	
	private Table 		liefTable = new Table();
	private Lieferant 	lieferant = null;
	private Button 		bestellen = new Button("bestellen");
	
	public BestellungAuswaehlen() {
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		
		box.setSizeFull();
		liefTable.setSizeFull();
		liefTable.setSelectable(true);
		
		bestellen.setEnabled(false);
		
		rechts.setSpacing(true);
		rechts.addComponent(liefTable);
		rechts.addComponent(bestellen);
		rechts.setComponentAlignment(liefTable, Alignment.MIDDLE_RIGHT);
		rechts.setComponentAlignment(bestellen, Alignment.BOTTOM_RIGHT);
		
		box.addComponent(rechts);
		
		List<Lieferant> list = new ArrayList<Lieferant>();
		List<Artikel> artList = new ArrayList<Artikel>();
		
		try {
			artList = Artikelverwaltung.getInstance().getArtikelByGrundbedarf();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			list = Bestellverwaltung.getInstance().getAllLieferantenByArtikellist(artList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BeanItemContainer<Lieferant> container = new BeanItemContainer<Lieferant>(Lieferant.class, list);
		liefTable.setContainerDataSource(container);
		liefTable.setVisibleColumns(new Object[] {"name"});
		
		this.addComponent(box);
		
		liefTable.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(event.getProperty().getValue() != null) {
					lieferant = (Lieferant) event.getProperty().getValue();
				}
				if(event.getProperty().getValue() == null) {
					bestellen.setEnabled(false);
				}
				else {
					bestellen.setEnabled(true);
				}
			}
		});
		
		liefTable.addItemClickListener(new ItemClickListener()
		{
			@Override
			public void itemClick(ItemClickEvent event)
			{
				if (event.isDoubleClick())
				{
					bestellen.click();
				}
			}
		});
		
		bestellen.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(BestellungErstellen.class, new ViewDataObject<Lieferant>(lieferant));
				
			}
		});
	}
	
	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}

}

/**
 * Created by Sebastian Walz
 * 06.05.2013 12:17:45
 */
package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.CustomTable.TableDragMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.BestellungData;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;

@SuppressWarnings("serial")
public class BestellungAnzeigen extends VerticalLayout implements View
{
	private Table 								bestellungTable;
	
	private FilterTable							artikelTable;
	
	private HorizontalLayout					form;
	
	private Lieferant 							lieferant;
	
	private BeanItemContainer<BestellungData> 	containerBestellung;
	private BeanItemContainer<Artikel> 			containerArtikel;
	
	public BestellungAnzeigen()
	{
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		
		form = new HorizontalLayout();
		form.setSizeFull();
		
		this.addComponent(form);
		
		bestellungTable = new Table();
		bestellungTable.setSizeFull();
		
		artikelTable = new FilterTable();
		artikelTable.setSizeFull();
		artikelTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);
		artikelTable.setDropHandler(new DropHandler()
		{
			
			@Override
			public AcceptCriterion getAcceptCriterion()
			{
				return AcceptAll.get();
			}
			
			@Override
			public void drop(DragAndDropEvent event)
			{

			}
		});
		
		bestellungTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		bestellungTable.setDropHandler(new DropHandler()
		{
			
			@Override
			public AcceptCriterion getAcceptCriterion()
			{
				return AcceptAll.get();
			}
			
			@Override
			public void drop(DragAndDropEvent event)
			{
				Transferable t = event.getTransferable();
				Artikel selected = (Artikel) t.getData("itemId");
				System.out.println("Ausgwählt: " + selected);
				containerArtikel.removeItem(selected);
				BestellungData newData = new BestellungData(selected);
				containerBestellung.addItem(newData);
				artikelTable.markAsDirty();
				bestellungTable.markAsDirty();
			}
		});
		
		form.addComponent(bestellungTable);
		form.addComponent(artikelTable);
		
		form.setExpandRatio(bestellungTable, 2);
		form.setExpandRatio(artikelTable, 1);
		form.setSpacing(true);
		
	}

	/* (non-Javadoc)
	 * @see de.hska.awp.palaver2.util.View#getViewParam(de.hska.awp.palaver2.util.ViewData)
	 */
	@Override
	public void getViewParam(ViewData data)
	{
		lieferant = (Lieferant) ((ViewDataObject<?>)data).getData();
		
		List<BestellungData> list = new ArrayList<BestellungData>();
		List<Artikel> artikel = new ArrayList<Artikel>();
//		list.add(new BestellungData("Mehl", "1 KG", new Kategorie(1L, "Grundbedarf"), 10, 20));
		List<Artikel> artikelListe = null;
		try
		{
			artikelListe = Artikelverwaltung.getInstance().getAllArtikelByLieferantId(lieferant.getId());
		} catch (ConnectException | DAOException | SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Artikel e : artikelListe)
		{
//			list.add(new BestellungData(e));
			artikel.add(e);
		}
		
		containerBestellung = new BeanItemContainer<BestellungData>(BestellungData.class, list);
		bestellungTable.setContainerDataSource(containerBestellung);
		bestellungTable.setVisibleColumns(new Object[] {"name", "gebinde", "kategorie", "durchschnitt", "kantine", "gesamt", "freitag", "montag"});
		
		containerArtikel = new BeanItemContainer<Artikel>(Artikel.class, artikel);
		artikelTable.setContainerDataSource(containerArtikel);
		artikelTable.setVisibleColumns(new Object[] {"name", "artikelnr"});
	}
}

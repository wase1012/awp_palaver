/**
 * Created by Sebastian Walz
 * 06.05.2013 12:17:45
 */
package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.util.BestellungData;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;

@SuppressWarnings("serial")
public class BestellungAnzeigen extends VerticalLayout implements View
{
	private Table 	table;
	
	public BestellungAnzeigen()
	{
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		
		table = new Table();
		table.setSizeFull();
		
		List<BestellungData> list = new ArrayList<BestellungData>();
//		list.add(new BestellungData("Mehl", "1 KG", new Kategorie(1L, "Grundbedarf"), 10, 20));
		List<Artikel> artikelListe = null;
		try
		{
			artikelListe = Artikelverwaltung.getInstance().getAllArtikel();
		} catch (ConnectException | DAOException | SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Artikel e : artikelListe)
		{
			list.add(new BestellungData(e));
		}
		
		BeanItemContainer<BestellungData> container = new BeanItemContainer<BestellungData>(BestellungData.class, list);
		table.setContainerDataSource(container);
		table.setVisibleColumns(new Object[] {"name", "gebinde", "kategorie", "durchschnitt", "kantine", "gesamt", "freitag", "montag"});
		this.addComponent(table);
	}

	/* (non-Javadoc)
	 * @see de.hska.awp.palaver2.util.View#getViewParam(de.hska.awp.palaver2.util.ViewData)
	 */
	@Override
	public void getViewParam(ViewData data)
	{
	}
}

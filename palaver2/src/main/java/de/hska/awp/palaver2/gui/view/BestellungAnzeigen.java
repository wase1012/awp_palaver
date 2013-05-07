/**
 * Created by Sebastian Walz
 * 06.05.2013 12:17:45
 */
package de.hska.awp.palaver2.gui.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

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
		list.add(new BestellungData("Test", "12345", 1));
		list.add(new BestellungData("Best", "98324", 6));
		
		BeanItemContainer<BestellungData> container = new BeanItemContainer<BestellungData>(BestellungData.class, list);
		table.setContainerDataSource(container);
		table.setVisibleColumns(new Object[] {"name", "artnr", "menge"});
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

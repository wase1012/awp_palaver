package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.artikelverwaltung.service.Mengeneinheitverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;

/*
 * @Author PhilippT
 */

@SuppressWarnings("serial")
public class MengeneinheitenAnzeigen extends VerticalLayout {
	
	private Table table;
	
	public MengeneinheitenAnzeigen()
	{
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		table = new Table();
		table.setSizeFull();
		
		BeanItemContainer<Mengeneinheit> container;
		try
		{
			container = new BeanItemContainer<Mengeneinheit>(Mengeneinheit.class, Mengeneinheitverwaltung.getInstance().getAllMengeneinheit());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] {"id", "name", "kurz"});
			table.sort(new Object[] {"id"}, new boolean[] {true});
		} 
		catch (IllegalArgumentException | ConnectException | DAOException
				| SQLException e)
		{
			e.printStackTrace();
		}	
		
		this.addComponent(table);
	}

}

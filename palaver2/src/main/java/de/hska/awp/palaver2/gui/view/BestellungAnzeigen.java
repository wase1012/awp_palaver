package de.hska.awp.palaver2.gui.view;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

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
	
	private Table 				bestellungen = new Table("Bestellung");
	private Table				bpositionen = new Table("Bestellpositionen");
	

	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}

}

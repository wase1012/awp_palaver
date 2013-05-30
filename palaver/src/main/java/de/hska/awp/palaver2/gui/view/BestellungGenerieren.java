package de.hska.awp.palaver2.gui.view;

import java.sql.Date;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellpositionverwaltung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellverwaltung;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * 
 * @author PhilippT
 *
 */

@SuppressWarnings("serial")
public class BestellungGenerieren extends VerticalLayout implements View {
	
	private VerticalLayout		form	= new VerticalLayout();
	
	private Button				bg = new Button("Bestellungen aus dem Menüplan generieren");
	
	public BestellungGenerieren () {
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		
		form.setSizeFull();
		form.setSpacing(true);
		
		form.addComponent(bg);
		this.addComponent(form);
		
		bg.addClickListener(new ClickListener()
		{
			@SuppressWarnings("unchecked")
			public void buttonClick(ClickEvent event)
			{
				try {
				
				Bestellverwaltung.getInstance().generateAllBestellungenByMenueplanAndGrundbedarf();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				ViewHandler.getInstance().switchView(BestellungAuswaehlen.class);
			}
		});
	}
	
	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}

}


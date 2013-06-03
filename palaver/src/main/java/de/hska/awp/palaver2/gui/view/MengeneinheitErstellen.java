package de.hska.awp.palaver2.gui.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.artikelverwaltung.service.Mengeneinheitverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;

/*
 * @Author PhilippT
 */

@SuppressWarnings("serial")
public class MengeneinheitErstellen extends VerticalLayout  implements View{
	
	private static final Logger	log	= LoggerFactory.getLogger(MengeneinheitErstellen.class.getName());

	private VerticalLayout	box = new VerticalLayout();
	
	private TextField		name = new TextField("Name");
	private TextField		kurz = new TextField("Kurz");
	
	private Button			speichern = new Button(IConstants.BUTTON_SAVE);
	private Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
	
	public MengeneinheitErstellen()
	{
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		name.setWidth("100%");
		kurz.setWidth("100%");
		
		name.setRequired(true);
		kurz.setRequired(true);
		
		box.setSpacing(true);

		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);		
		box.addComponent(name);
		box.addComponent(kurz);
	
		HorizontalLayout control = new HorizontalLayout();
		control.setSpacing(true);
		box.setWidth("300px");
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);	
		control.addComponent(verwerfen);
		control.addComponent(speichern);
		speichern.setEnabled(false);
		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		
		name.setImmediate(true);
		kurz.setImmediate(true);
		
		verwerfen.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(MengeneinheitenAnzeigen.class);							
			}
		});

		speichern.addClickListener(new ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				Mengeneinheit me = new Mengeneinheit();
				me.setName(name.getValue());
				me.setKurz(kurz.getValue());
				try {
					Mengeneinheitverwaltung.getInstance().createMengeneinheit(me);
				} catch (Exception e) {
//					throw new NullPointerException("Bitte g�ltige Werte eingeben");
					log.error(e.toString());
				}
				ViewHandler.getInstance().switchView(MengeneinheitenAnzeigen.class);
			}
		});


        ValueChangeListener vcl = new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(name.isValid() == false || kurz.isValid() == false )
				{
					speichern.setEnabled(false);
				}
				else
				{
					speichern.setEnabled(true);
				}
				
			}
		};
		name.addValueChangeListener(vcl);
		kurz.addValueChangeListener(vcl);
	}

	/* (non-Javadoc)
	 * @see de.hska.awp.palaver2.util.View#getViewParam(de.hska.awp.palaver2.util.ViewData)
	 */
	@Override
	public void getViewParam(ViewData data)
	{
	}
}

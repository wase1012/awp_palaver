package main.java.de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import main.java.de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import main.java.de.hska.awp.palaver2.artikelverwaltung.service.Mengeneinheitverwaltung;
import main.java.de.hska.awp.palaver2.data.ConnectException;
import main.java.de.hska.awp.palaver2.data.DAOException;
import main.java.de.hska.awp.palaver2.util.IConstants;
import main.java.de.hska.awp.palaver2.util.View;
import main.java.de.hska.awp.palaver2.util.ViewData;
import main.java.de.hska.awp.palaver2.util.ViewHandler;

/*
 * @Author PhilippT
 */

@SuppressWarnings("serial")
public class MengeneinheitErstellen extends VerticalLayout  implements View{

	private VerticalLayout	box = new VerticalLayout();
	
	private TextField		name = new TextField("Name");
	private TextField		kurz = new TextField("Kurz");
	private String			nameText;
	private String			kurzText;
	
	private Button			speichern = new Button(IConstants.BUTTON_SAVE);
	private Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
	
	public MengeneinheitErstellen()
	{
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		name.setWidth("100%");
		kurz.setWidth("100%");
		
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
		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		
		name.setImmediate(true);
		name.setMaxLength(15);
		name.addValidator(new StringLengthValidator("Bitte gültigen Namen eingeben", 4,15, false));
		
		kurz.setImmediate(true);
		kurz.setMaxLength(4);	
		kurz.addValidator(new StringLengthValidator("Bitte gültiges Kürzel eingeben", 1,4, false));
		
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
				me.setName(nameText);
				me.setKurz(kurzText);
				try {
					Mengeneinheitverwaltung.getInstance().createNewMengeneinheit(me);
				} catch (Exception e) {
					throw new NullPointerException("Bitte gültige Werte eingeben");
				}
				ViewHandler.getInstance().switchView(MengeneinheitenAnzeigen.class);
			}
		});


        name.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                nameText = valueString;
            }
        });
        
        kurz.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());
                kurzText = valueString;
            }
        });
	}

	/* (non-Javadoc)
	 * @see main.java.de.hska.awp.palaver2.util.View#getViewParam(main.java.de.hska.awp.palaver2.util.ViewData)
	 */
	@Override
	public void getViewParam(ViewData data)
	{
	}
}

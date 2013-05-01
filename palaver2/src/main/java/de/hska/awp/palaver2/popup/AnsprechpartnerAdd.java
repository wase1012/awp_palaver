package de.hska.awp.palaver2.popup;

import java.sql.SQLException;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.gui.layout.DefaultView;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Ansprechpartnerverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.PopUp;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * 
 * @author PhilippT
 *
 */

@Theme("palaver")
@SuppressWarnings("serial")
public class AnsprechpartnerAdd extends UI {

	private VerticalLayout	box = new VerticalLayout();
	
	private TextField		name = new TextField("Name");
	private TextField		telefon = new TextField("Telefon");
	private TextField		handy = new TextField("Handy");
	private TextField		fax = new TextField("Fax");
	
	private String			nameInput;
	private String			telefonInput;
	private String 			handyInput;
	private String 			faxInput;
	private Lieferant 		lieferant;

	
	private Button			speichern = new Button(IConstants.BUTTON_SAVE);
	private Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);


	@Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Ansprechpartner hinzufügen");
        
        setContent(box);
		this.setSizeFull();
		
		name.setWidth("100%");
		telefon.setWidth("100%");
		handy.setWidth("100%");
		fax.setWidth("100%");
		
		VerticalLayout feld = new VerticalLayout();
		
		box.setSpacing(true);
	
		feld.addComponent(name);
		feld.addComponent(telefon);
		feld.addComponent(handy);
		feld.addComponent(fax);
		
		box.addComponentAsFirst(feld);
		box.setComponentAlignment(feld, Alignment.MIDDLE_CENTER);

		HorizontalLayout control = new HorizontalLayout();
		control.setSpacing(true);
		box.setWidth("300px");
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.BOTTOM_RIGHT);	
		control.addComponent(verwerfen);
		control.addComponent(speichern);
		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		
		name.setImmediate(true);
		name.setInputPrompt(nameInput);
		name.setMaxLength(15);
		
		telefon.setImmediate(true);
		telefon.setInputPrompt(telefonInput);
		telefon.setMaxLength(10);	
		
        
		speichern.addClickListener(new ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				Ansprechpartner ans = new Ansprechpartner();
				ans.setName(nameInput);
				ans.setTelefon(telefonInput);
				ans.setHandy(handyInput);
				ans.setFax(faxInput);
				ans.setLieferant(lieferant);
				try {
					Ansprechpartnerverwaltung.getInstance().createAnsprechpartner(ans);
				} catch (ConnectException | DAOException | SQLException e) {
					throw new NullPointerException("Bitte gültige Werte eingeben");
				}
				ViewHandler.getInstance().switchView(DefaultView.class);
				/*
				 * später ersetzten durch suche mit der angelegten mengeneinheit
				 */
			}
		});


        name.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                nameInput = valueString;
            }
        });
        
        telefon.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());
                telefonInput = valueString;
            }
        });
        
        handy.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                handyInput = valueString;
            }
        });
        
        fax.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                faxInput = valueString;
            }
        });
    }
	
	public AnsprechpartnerAdd()
	{
		super();

	}

}

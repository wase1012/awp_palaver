package main.java.de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import main.java.de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import main.java.de.hska.awp.palaver2.artikelverwaltung.service.Kategorienverwaltung;
import main.java.de.hska.awp.palaver2.data.ConnectException;
import main.java.de.hska.awp.palaver2.data.DAOException;
import main.java.de.hska.awp.palaver2.util.IConstants;
import main.java.de.hska.awp.palaver2.util.View;
import main.java.de.hska.awp.palaver2.util.ViewData;
import main.java.de.hska.awp.palaver2.util.ViewHandler;

/**
 * 
 * @author Mihail Boehm
 *
 */
@SuppressWarnings("serial")
public class KategorieErstellen extends VerticalLayout  implements View{

	private VerticalLayout	box = new VerticalLayout();
	
	private TextField		name = new TextField("Name");
	private String			nameText;
	
	private Button			speichern = new Button(IConstants.BUTTON_SAVE);
	private Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
	
	
	
	public KategorieErstellen() {
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		name.setWidth("100%");	
		box.setSpacing(true);

		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);		
		box.addComponent(name);
	
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
		
		verwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(KategorienAnzeigen.class);							
			}
		});

		speichern.addClickListener(new ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				Kategorie ka = new Kategorie();
				ka.setName(nameText);
				try {
					Kategorienverwaltung.getInstance().createNewKategorie(ka);
				} catch (Exception e) {
					throw new NullPointerException("Bitte gültige Werte eingeben");
				}
				ViewHandler.getInstance().switchView(KategorienAnzeigen.class);
			}
		});


        name.addValueChangeListener(new ValueChangeListener() {
        	
            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                nameText = valueString;
            }
        });
        }

	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}

}

package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;

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
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.gui.layout.DefaultView;
import de.hska.awp.palaver2.util.ViewHandler;

/*
 * @Author PhilippT
 */

@SuppressWarnings("serial")
public class MengeneinheitErstellen extends VerticalLayout {

	private VerticalLayout	box = new VerticalLayout();
	
	private TextField		name = new TextField("Name");
	private TextField		kurz = new TextField("Kurz");
	private String			nameText;
	private String			kurzText;
	
	private Button			speichern = new Button("Speichern");
	private Button			verwerfen = new Button("Verwerfen");
	
	public MengeneinheitErstellen()
	{
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		name.setWidth("100%");
		kurz.setWidth("100%");

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
		speichern.setIcon(new ThemeResource("img/save.ico"));
		verwerfen.setIcon(new ThemeResource("img/cross.ico"));
		
		name.setImmediate(true);
		name.setInputPrompt(nameText);
		name.setMaxLength(15);
		
		kurz.setImmediate(true);
		kurz.setInputPrompt(kurzText);
		kurz.setMaxLength(4);	
        
		speichern.addClickListener(new ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				Mengeneinheit me = new Mengeneinheit();
				me.setName(nameText);
				me.setKurz(kurzText);
				try {
					Mengeneinheitverwaltung.getInstance().createNewMengeneinheit(me);
				} catch (ConnectException | DAOException | SQLException e) {
					throw new NullPointerException("Bitte gültige Werte eingeben"+ nameText + kurzText);
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
}

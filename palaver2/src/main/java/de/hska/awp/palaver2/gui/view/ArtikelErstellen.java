/**
 * Created by Sebastian
 * 17.04.2013 - 16:24:51
 */
package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.artikelverwaltung.service.Mengeneinheitverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * @author Sebastian Walz
 *
 */
@SuppressWarnings("serial")
public class ArtikelErstellen extends VerticalLayout
{
	private VerticalLayout	box = new VerticalLayout();
	
	private TextField		name = new TextField("Name");
	private TextField		preis = new TextField("Preis");
	private TextField		artnr = new TextField("Artikelnummer");
	private TextField		durchschnitt = new TextField("Durchschnitt");
	private TextField		bestellung = new TextField("Bestellgröße");
	
	private ComboBox		lieferant = new ComboBox("Lieferant");
	private ComboBox		mengeneinheit = new ComboBox("Mengeneinheit");
	private ComboBox		kategorie = new ComboBox("Kategorie");
	
	private CheckBox		bio = new CheckBox("Bio");
	private CheckBox		standard = new CheckBox("Standard");
	private CheckBox		grundbedarf = new CheckBox("Grundbedarf");
	private CheckBox		lebensmittel = new CheckBox("Lebensmittel");
	
	private Button			speichern = new Button(IConstants.BUTTON_SAVE);
	private Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
	
	/**
	 * 
	 */
	public ArtikelErstellen()
	{
		super();
		this.setSizeFull();
		this.setMargin(true);
		
		name.setWidth("100%");
		preis.setWidth("100%");
		artnr.setWidth("100%");
		durchschnitt.setWidth("100%");
		bestellung.setWidth("100%");
		lieferant.setWidth("100%");
		mengeneinheit.setWidth("100%");
		kategorie.setWidth("100%");
		
		durchschnitt.setEnabled(false);
		
		box.setWidth("300px");
//		box.setHeight("100%");
		box.setSpacing(true);
		
		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		
		box.addComponent(name);
		box.addComponent(preis);
		box.addComponent(lieferant);
		box.addComponent(artnr);
		box.addComponent(mengeneinheit);
		box.addComponent(kategorie);
		
		HorizontalLayout subBox = new HorizontalLayout();
		subBox.setWidth("100%");
		box.addComponent(subBox);
		
		subBox.addComponent(bio);
		subBox.addComponent(grundbedarf);
		
		HorizontalLayout subBox2 = new HorizontalLayout();
		subBox2.setWidth("100%");
		box.addComponent(subBox2);
		
		VerticalLayout subBox2_Vertical = new VerticalLayout();
		
		subBox2_Vertical.addComponent(standard);
		subBox2_Vertical.addComponent(lebensmittel);
		
		subBox2.addComponent(subBox2_Vertical);
		subBox2.addComponent(durchschnitt);
		
		box.addComponent(bestellung);
		
		HorizontalLayout control = new HorizontalLayout();
//		control.setWidth("100%");
		control.setSpacing(true);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		
		control.addComponent(verwerfen);
		control.addComponent(speichern);
		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		
		grundbedarf.addValueChangeListener(new ValueChangeListener() 
		{
            @Override
            public void valueChange(final ValueChangeEvent event) 
            {
            	durchschnitt.setEnabled(!durchschnitt.isEnabled());
            }
        });
		speichern.addClickListener(new ClickListener()
		{			
			@Override
			public void buttonClick(ClickEvent event)
			{
				final Window dialog = new Window();
				dialog.setClosable(false);
				dialog.setWidth("300px");
				dialog.setHeight("150px");
				dialog.setModal(true);
				dialog.center();
				dialog.setResizable(false);
				dialog.setStyleName("dialog-window");
				
				Label message = new Label("Artikel gespeichert");
				
				Button okButton = new Button("OK");
				
				VerticalLayout dialogContent = new VerticalLayout();
				dialogContent.setSizeFull();
				dialogContent.setMargin(true);
				dialog.setContent(dialogContent);
				
				dialogContent.addComponent(message);
				dialogContent.addComponent(okButton);
				dialogContent.setComponentAlignment(okButton, Alignment.BOTTOM_RIGHT);
				
				UI.getCurrent().addWindow(dialog);
				
				okButton.addClickListener(new ClickListener()
				{	
					@Override
					public void buttonClick(ClickEvent event)
					{
						UI.getCurrent().removeWindow(dialog);
						ViewHandler.getInstance().returnToDefault();
					}
				});
			}
		});
		load();
	}
	
	/**
	 * 
	 */
	public void load()
	{
		try
		{
			List<Lieferant> lieferanten = Lieferantenverwaltung.getInstance().getAllLieferanten();
			for (Lieferant e : lieferanten)
			{
				lieferant.addItem(e);
			}
			List<Kategorie> kategorien = Artikelverwaltung.getInstance().getAllKategorien();
			for (Kategorie e : kategorien)
			{
				kategorie.addItem(e);
			}
			List<Mengeneinheit> mengen = Mengeneinheitverwaltung.getInstance().getAllMengeneinheit();
			for (Mengeneinheit e : mengen)
			{
				mengeneinheit.addItem(e);
			}
		} 
		catch (ConnectException | DAOException | SQLException e)
		{
			e.printStackTrace();
		}
	}
}


/**
 * Created by Sebastian
 * 17.04.2013 - 16:24:51
 */
package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;

/**
 * @author Sebastian
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
	private TextField		bestellung = new TextField("Bestellung");
	
	private ComboBox		lieferant = new ComboBox("Lieferant");
	private ComboBox		mengeneinheit = new ComboBox("Mengeneinheit");
	private ComboBox		kategorie = new ComboBox("Kategorie");
	
	private CheckBox		bio = new CheckBox("Bio");
	private CheckBox		standard = new CheckBox("Standard");
	private CheckBox		grundbedarf = new CheckBox("Grundbedarf");
	
	private Button			speichern = new Button("Speichern");
	private Button			verwerfen = new Button("Verwerfen");
	
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
		
		subBox2.addComponent(standard);
		subBox2.addComponent(durchschnitt);
		
		box.addComponent(bestellung);
		
		HorizontalLayout control = new HorizontalLayout();
//		control.setWidth("100%");
		control.setSpacing(true);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		
		control.addComponent(verwerfen);
		control.addComponent(speichern);
		
		grundbedarf.addValueChangeListener(new ValueChangeListener() 
		{
            @Override
            public void valueChange(final ValueChangeEvent event) 
            {
            	durchschnitt.setEnabled(!durchschnitt.isEnabled());
            }
        });
		load();
	}
	
	public void load()
	{
		try
		{
			List<Lieferant> lieferanten = Lieferantenverwaltung.getInstance().getAllLieferanten();
			for (Lieferant e : lieferanten)
			{
				lieferant.addItem(e);
			}
		} 
		catch (ConnectException | DAOException | SQLException e)
		{
			e.printStackTrace();
		}
	}
}


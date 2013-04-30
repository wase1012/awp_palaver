package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;

@SuppressWarnings("serial")
public class LieferantSuche extends VerticalLayout{
	
	private HorizontalLayout	box = new HorizontalLayout();
	
	private TextField			name = new TextField("Name");
	private TextField			bezeichnung = new TextField("Bezeichnung");
	private TextField			kundennummer = new TextField("Kundennummer");
	private TextField			strasse = new TextField("Staße");
	private TextField			plz = new TextField("PLZ");
	private TextField			ort = new TextField("Ort");
	private TextField			email = new TextField("E-Mail");
	private TextField			telefon = new TextField("Telefon");
	private TextField			fax = new TextField("Telefax");
	
	private Table ansprechpartner;
	

	
	public LieferantSuche() {
		
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		
		name.setWidth("100%");
		bezeichnung.setWidth("100%");
		kundennummer.setWidth("100%");
		strasse.setWidth("100%");
		plz.setWidth("100%");
		ort.setWidth("100%");
		email.setWidth("100%");
		telefon.setWidth("100%");
		fax.setWidth("100%");
		
		box.setWidth("610px");
		box.setSpacing(true);
		
		VerticalLayout links = new VerticalLayout();
		VerticalLayout rechts = new VerticalLayout();
		links.setWidth("300px");
		rechts.setWidth("300px");
		links.setSpacing(true);
		rechts.setSpacing(true);
		box.addComponent(links);
		box.addComponent(rechts);
		
		links.addComponent(name);
		links.addComponent(bezeichnung);
		links.addComponent(kundennummer);
		links.addComponent(strasse);
		links.addComponent(plz);
		links.addComponent(ort);
		links.addComponent(email);
		links.addComponent(telefon);
		links.addComponent(fax);
		
		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		Lieferant lieferant;

		try {
			lieferant = Lieferantenverwaltung.getInstance().getLastLieferant();
			
			name.setValue(lieferant.getName());
			name.setReadOnly(true);
			
			bezeichnung.setValue(lieferant.getBezeichnung());
			bezeichnung.setReadOnly(true);
			
			kundennummer.setValue(lieferant.getKundennummer());
			kundennummer.setReadOnly(true);
			
			strasse.setValue(lieferant.getStrasse());
			strasse.setReadOnly(true);
			
			plz.setValue(lieferant.getPlz());
			plz.setReadOnly(true);
			
			ort.setValue(lieferant.getOrt());
			ort.setReadOnly(true);
			
			email.setValue(lieferant.getEmail());
			email.setReadOnly(true);
			
			telefon.setValue(lieferant.getTelefon());
			telefon.setReadOnly(true);
			
			fax.setValue(lieferant.getFax());
			fax.setReadOnly(true);

			
		} catch (ConnectException | DAOException | SQLException e) {
			// TODO Auto-generated catch block
			throw new NullPointerException();
		}

				
	}

}

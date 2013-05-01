package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.artikelverwaltung.service.Mengeneinheitverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Ansprechpartnerverwaltung;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;
import de.hska.awp.palaver2.popup.AnsprechpartnerAdd;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.ViewHandler;

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
	
	private Button 				ansprAdd = new Button(IConstants.BUTTON_ADD);
	private Table 				ansprechpartner = new Table();
	

	
	public LieferantSuche() throws ConnectException, DAOException, SQLException {
		
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

		ansprechpartner.setWidth("100%");
		
		box.setWidth("610px");
		box.setSpacing(true);
		
		VerticalLayout links = new VerticalLayout();
		VerticalLayout rechts = new VerticalLayout();
		links.setWidth("300px");
		rechts.setWidth("300px");
		links.setSpacing(true);
		rechts.setSpacing(true);
		box.addComponentAsFirst(links);
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
		
		rechts.addComponent(ansprAdd);
		
		ansprAdd.setIcon(new ThemeResource(IConstants.BUTTON_ADD_ICON));
		
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

		
		BeanItemContainer<Ansprechpartner> container;

			try {
				container = new BeanItemContainer<Ansprechpartner>(Ansprechpartner.class, Ansprechpartnerverwaltung.getInstance().getAnsprechpartnerByLieferant(lieferant));
				ansprechpartner.setContainerDataSource(container);
				ansprechpartner.setVisibleColumns(new Object[] {"name"});
				ansprechpartner.sort(new Object[] {"id"}, new boolean[] {true});
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		rechts.addComponent(ansprechpartner);	
		
		ansprAdd.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				BrowserWindowOpener opener = new BrowserWindowOpener(AnsprechpartnerAdd.class);
					opener.setFeatures("height=400,width=600");
					 
					// Attach it to a button
					opener.extend(ansprAdd);
				
			}
		});
	}

}

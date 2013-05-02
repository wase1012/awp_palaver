package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
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
	
	private String			nameInput;
	private String			telefonInput;
	private String 			handyInput;
	private String 			faxInput;
	private Lieferant 		lieferant;

	
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
			throw new NullPointerException();
		}

		
		BeanItemContainer<Ansprechpartner> container;

			try {
				container = new BeanItemContainer<Ansprechpartner>(Ansprechpartner.class, Ansprechpartnerverwaltung.getInstance().getAnsprechpartnerByLieferant(lieferant));
				ansprechpartner.setContainerDataSource(container);
				ansprechpartner.setVisibleColumns(new Object[] {"name", "telefon"});
				ansprechpartner.sort(new Object[] {"id"}, new boolean[] {true});
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		rechts.addComponent(ansprechpartner);	
		
		ansprAdd.addClickListener(new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			final Window anspr = new Window();
			anspr.setClosable(false);
			anspr.setWidth("400px");
			anspr.setHeight("280px");
			anspr.setModal(true);
			anspr.center();
			anspr.setResizable(false);
			anspr.setStyleName("dialog-window");

			
			UI.getCurrent().addWindow(anspr);
			
			Label message = new Label("Ansprechpartner hinzufügen");
			
			VerticalLayout	layout = new VerticalLayout();
			layout.setMargin(true);
			layout.setWidth("100%");
			layout.setSpacing(true);
			
			TextField		nameAnspr = new TextField("Name");
			TextField		telefon = new TextField("Telefon");
			TextField		handy = new TextField("Handy");
			TextField		fax = new TextField("Fax");

			Button			speichern = new Button(IConstants.BUTTON_SAVE);
			Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
			
			nameAnspr.setWidth("100%");
			telefon.setWidth("100%");
			handy.setWidth("100%");
			fax.setWidth("100%");
			
			VerticalLayout feld = new VerticalLayout();
			
			box.setSpacing(true);
		
			feld.addComponent(nameAnspr);
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
			
			layout.addComponent(message);
			layout.setComponentAlignment(message, Alignment.TOP_CENTER);
			layout.addComponent(feld);
			layout.setComponentAlignment(feld, Alignment.MIDDLE_CENTER);
			layout.addComponent(control);
			layout.setComponentAlignment(control, Alignment.BOTTOM_CENTER);
			anspr.setContent(layout);
			
			nameAnspr.setImmediate(true);
			nameAnspr.setInputPrompt(nameInput);
			nameAnspr.setMaxLength(15);
			
			telefon.setImmediate(true);
			telefon.setInputPrompt(telefonInput);
			telefon.setMaxLength(10);	
			
			handy.setImmediate(true);
			handy.setInputPrompt(handyInput);
			handy.setMaxLength(10);
			
			fax.setImmediate(true);
			fax.setInputPrompt(faxInput);
			fax.setMaxLength(10);
			  
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
					
					UI.getCurrent().removeWindow(anspr);
					ViewHandler.getInstance().switchView(LieferantSuche.class);
				}
			});

	        nameAnspr.addValueChangeListener(new ValueChangeListener() {

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
	});

	}
}

package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Ansprechpartnerverwaltung;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class LieferantSuche extends VerticalLayout  implements View{
	
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
	
	private Button				okButton = new Button("Ok");
	private Button 				ansprAdd = new Button(IConstants.BUTTON_ADD);
	private Table 				ansprechpartner = new Table();
	
	private String				nameInput;
	private String				telefonInput;
	private String 				handyInput;
	private String 				faxInput;
	private Lieferant 			lieferant;
	
	private TextField			nameAnspr = new TextField("Name");
	private TextField			telefonAnspr = new TextField("Telefon");
	private TextField			handyAnspr = new TextField("Handy");
	private TextField			faxAnspr = new TextField("Fax");
	
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
		links.setWidth("250px");
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

		rechts.addComponent(ansprechpartner);
		rechts.addComponent(okButton);
		rechts.setComponentAlignment(okButton, Alignment.BOTTOM_RIGHT);
		
        okButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(LieferantAnzeigen.class);					
			}
		});
		
		ansprAdd.addClickListener(new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			final Window anspr = new Window();
			anspr.setClosable(false);
			anspr.setWidth("400px");
			anspr.setHeight("270px");
			anspr.setModal(true);
			anspr.center();
			anspr.setResizable(false);
			anspr.setCaption("Ansprechpartner hinzufügen");
			
			UI.getCurrent().addWindow(anspr);
			
			VerticalLayout	layout = new VerticalLayout();
			layout.setMargin(true);
			layout.setWidth("100%");
			layout.setSpacing(true);

			Button			speichern = new Button(IConstants.BUTTON_SAVE);
			Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
			
			nameAnspr.setWidth("100%");
			telefonAnspr.setWidth("100%");
			handyAnspr.setWidth("100%");
			faxAnspr.setWidth("100%");
			
			VerticalLayout feld = new VerticalLayout();
		
			feld.addComponent(nameAnspr);
			feld.addComponent(telefonAnspr);
			feld.addComponent(handyAnspr);
			feld.addComponent(faxAnspr);

			HorizontalLayout control = new HorizontalLayout();
			control.setSpacing(true);
			control.addComponent(verwerfen);
			control.addComponent(speichern);
			speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
			verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));

			layout.addComponent(feld);
			layout.setComponentAlignment(feld, Alignment.MIDDLE_CENTER);
			layout.addComponent(control);
			layout.setComponentAlignment(control, Alignment.BOTTOM_RIGHT);
			anspr.setContent(layout);
			
			nameAnspr.setImmediate(true);
			nameAnspr.setInputPrompt(nameInput);
			nameAnspr.setMaxLength(15);
			
			telefonAnspr.setImmediate(true);
			telefonAnspr.setInputPrompt(telefonInput);
			telefonAnspr.setMaxLength(10);	
			
			handyAnspr.setImmediate(true);
			handyAnspr.setInputPrompt(handyInput);
			handyAnspr.setMaxLength(10);
			
			faxAnspr.setImmediate(true);
			faxAnspr.setInputPrompt(faxInput);
			faxAnspr.setMaxLength(10);
			
			verwerfen.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					UI.getCurrent().removeWindow(anspr);							
				}
			});
			  
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
						System.out.println(e);
						throw new NullPointerException("Bitte gültige Werte eingeben");

					}				
					
					UI.getCurrent().removeWindow(anspr);
					ViewHandler.getInstance().switchView(LieferantSuche.class, new ViewDataObject<Lieferant>(lieferant));				}
			});

	        nameAnspr.addValueChangeListener(new ValueChangeListener() {

	            public void valueChange(final ValueChangeEvent event) {
	                final String valueString = String.valueOf(event.getProperty()
	                        .getValue());

	                nameInput = valueString;
	            }
	        });
	        
	        telefonAnspr.addValueChangeListener(new ValueChangeListener() {
	            @Override
	            public void valueChange(final ValueChangeEvent event) {
	                final String valueString = String.valueOf(event.getProperty()
	                        .getValue());
	                telefonInput = valueString;
	            }
	        });
	        
	        handyAnspr.addValueChangeListener(new ValueChangeListener() {

	            public void valueChange(final ValueChangeEvent event) {
	                final String valueString = String.valueOf(event.getProperty()
	                        .getValue());

	                handyInput = valueString;
	            }
	        });
	        
	        faxAnspr.addValueChangeListener(new ValueChangeListener() {

	            public void valueChange(final ValueChangeEvent event) {
	                final String valueString = String.valueOf(event.getProperty()
	                        .getValue());

	                faxInput = valueString;
	            }
	        });
		}
	});
	}

	/* (non-Javadoc)
	 * @see de.hska.awp.palaver2.util.View#getViewParam(de.hska.awp.palaver2.util.ViewData)
	 */
	@Override
	public void getViewParam(ViewData data)
	{
		lieferant = (Lieferant) ((ViewDataObject<?>)data).getData();
		if(lieferant.getId() == null) {
			try {
				lieferant = Lieferantenverwaltung.getInstance().getLastLieferant();
			} catch (ConnectException | DAOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		name.setValue(lieferant.getName());
		name.setEnabled(false);
			
		bezeichnung.setValue(lieferant.getBezeichnung());
		bezeichnung.setEnabled(false);
			
		kundennummer.setValue(lieferant.getKundennummer());
		kundennummer.setEnabled(false);
			
		strasse.setValue(lieferant.getStrasse());
		strasse.setEnabled(false);
			
		plz.setValue(lieferant.getPlz());
		plz.setEnabled(false);
						ort.setValue(lieferant.getOrt());
		ort.setEnabled(false);
			
		email.setValue(lieferant.getEmail());
		email.setEnabled(false);
			
		telefon.setValue(lieferant.getTelefon());
		telefon.setEnabled(false);
			
		fax.setValue(lieferant.getFax());
		fax.setEnabled(false);
		
		BeanItemContainer<Ansprechpartner> container;

		try {
			container = new BeanItemContainer<Ansprechpartner>(Ansprechpartner.class, Ansprechpartnerverwaltung.getInstance().getAnsprechpartnerByLieferant(lieferant));
			ansprechpartner.setContainerDataSource(container);
			ansprechpartner.setVisibleColumns(new Object[] {"name", "telefon", "handy", "fax"});
			ansprechpartner.sort(new Object[] {"id"}, new boolean[] {true});
			ansprechpartner.setColumnCollapsingAllowed(true);
			ansprechpartner.setColumnCollapsed(handyAnspr, false);
			ansprechpartner.setColumnCollapsed(faxAnspr, false);				
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

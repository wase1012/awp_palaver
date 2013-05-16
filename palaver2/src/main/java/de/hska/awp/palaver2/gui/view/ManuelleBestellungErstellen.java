/**
 * Created by Sebastian Walz
 * 06.05.2013 12:17:45
 */
package de.hska.awp.palaver2.gui.view;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.tepi.filtertable.FilterTable;

import com.vaadin.server.UserError;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellposition;
import de.hska.awp.palaver2.bestellverwaltung.domain.Bestellung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellpositionverwaltung;
import de.hska.awp.palaver2.bestellverwaltung.service.Bestellverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.util.BestellungData;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class ManuelleBestellungErstellen extends VerticalLayout implements View
{
	private Table 								bestellungTable;
	
	private FilterTable							artikelTable;
	
	private VerticalLayout						fenster;
	
	private HorizontalLayout					form;
	
	private HorizontalLayout					control;
	
	private Bestellung							bestellung;
	
	private Lieferant 							lieferant;
	
	private DateField							lieferdatum = new DateField("Lieferdatum");
	
	private List<Bestellposition>				bestellpositionen;
	private List<BestellungData>				bestellData = new ArrayList<BestellungData>();;
	
	private Button								speichern;
	private Button								verwerfen;
	
	private BeanItemContainer<BestellungData> 	containerBestellung;
	private BeanItemContainer<Artikel> 			containerArtikel;
	
	public ManuelleBestellungErstellen()
	{
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		
		lieferdatum.setVisible(false);
		lieferdatum.setImmediate(true);
		lieferdatum.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				java.util.Date date2 = new java.util.Date();
				if(date2.before(lieferdatum.getValue()) == false || lieferdatum.getValue() == null) {
					lieferdatum.setComponentError(new UserError("Bitte gültiges Datum eingeben"));
				}
			}
		});
		
		fenster = new VerticalLayout();
		fenster.setSizeFull();
		
		form = new HorizontalLayout();
		form.setSizeFull();
		
		control = new HorizontalLayout();
		control.setSpacing(true);
		
		this.addComponent(fenster);
		
		speichern = new Button(IConstants.BUTTON_SAVE);
		verwerfen = new Button(IConstants.BUTTON_DISCARD);
		
		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		
		control.addComponent(verwerfen);
		control.setComponentAlignment(verwerfen, Alignment.TOP_RIGHT);
		control.addComponent(speichern);
		control.setComponentAlignment(speichern, Alignment.TOP_RIGHT);	
	
		bestellungTable = new Table();
		bestellungTable.setSizeFull();
		bestellungTable.setStyleName("palaverTable");
		bestellungTable.setImmediate(true);
		
		artikelTable = new FilterTable();
		artikelTable.setSizeFull();
		artikelTable.setStyleName("palaverTable");
		artikelTable.setFilterBarVisible(true);
		artikelTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);
		/**
		 * Darg n Drop
		 */
		artikelTable.setDropHandler(new DropHandler()
		{
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion()
			{
				return AcceptAll.get();
			}
			
			/**
			 * Bestellposition loeschen und Artikel wieder in Liste setzen.
			 */
			@Override
			public void drop(DragAndDropEvent event)
			{
				Transferable t = event.getTransferable();
				BestellungData selected = (BestellungData) t.getData("itemId");
				containerBestellung.removeItem(selected);
				containerArtikel.addItem(selected.getBestellungArtikel());
				artikelTable.markAsDirty();
				bestellungTable.markAsDirty();
			}
		});
		
		bestellungTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		/**
		 * Drag n Drop
		 */
		bestellungTable.setDropHandler(new DropHandler()
		{
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion()
			{
				return AcceptAll.get();
			}
			
			/**
			 * Verschiebt einen Artikel in die Bestellliste.
			 */
			@Override
			public void drop(DragAndDropEvent event)
			{
				Transferable t = event.getTransferable();
				Artikel selected = (Artikel) t.getData("itemId");
				containerArtikel.removeItem(selected);
				containerBestellung.addItem(new BestellungData(selected));
				artikelTable.markAsDirty();
				bestellungTable.markAsDirty();
			}
		});
		
		form.addComponent(bestellungTable);
		form.addComponent(artikelTable);
		
		form.setExpandRatio(bestellungTable, 2);
		form.setExpandRatio(artikelTable, 1);
		form.setSpacing(true);
		
		fenster.addComponent(lieferdatum);
		fenster.setComponentAlignment(lieferdatum, Alignment.MIDDLE_LEFT);
		fenster.addComponent(form);
		fenster.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
		fenster.addComponent(control);
		fenster.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);
		fenster.setSpacing(true);
		
		fenster.setExpandRatio(form, 8);
		fenster.setExpandRatio(control, 1);
		
		verwerfen.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(BestellungLieferantAuswaehlen.class);						
			}
		});
		
		speichern.addClickListener(new ClickListener()
		{
			@SuppressWarnings("unchecked")
			public void buttonClick(ClickEvent event)
			{
				bestellData = containerBestellung.getItemIds();
				bestellpositionen = Bestellpositionverwaltung.getInstance().getBestellpositionen(bestellData);
				int ii = 0;
				for(int i = 0; i < (bestellpositionen.size()); i++){
					
					if(bestellpositionen.get(i).getGesamt()==0){
						bestellpositionen.remove(i);
						ii = ii + 1;
					}
				}
				
				java.util.Date date2 = new java.util.Date();
				Date date = new Date(date2.getTime());
				bestellung = new Bestellung();
				bestellung.setLieferant(lieferant);
				bestellung.setDatum(date);
				bestellung.setBestellpositionen(bestellpositionen);
				
				String testDatum = "Freitag";
				if(lieferant.getMehrereliefertermine() == true) {
					bestellung.setLieferdatum(testDatum);
				}
				else {
					bestellung.setLieferdatum(lieferdatum.getValue().toString());				
				}
				
				try {
					Bestellverwaltung.getInstance().createBestellung(bestellung);
				} catch (ConnectException | DAOException | SQLException
						| ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ViewHandler.getInstance().switchView(BestellungLieferantAuswaehlen.class);
			}
		});
		
	}

	/**
	 * Uebergibt den Lieferanten und fuellt die Tabellen
	 */
	@Override
	public void getViewParam(ViewData data)
	{
		lieferant = (Lieferant) ((ViewDataObject<?>)data).getData();
		
		bestellungTable.setCaption("Bestellung " + lieferant.getName());
		artikelTable.setCaption("Artikel");
		
		List<BestellungData> list = new ArrayList<BestellungData>();
		List<Artikel> artikel = new ArrayList<Artikel>();
		List<Artikel> artikelListe = null;
		try
		{
			artikelListe = Artikelverwaltung.getInstance().getAllArtikelByLieferantId(lieferant.getId());
		} catch (ConnectException | DAOException | SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Artikel e : artikelListe)
		{
			artikel.add(e);
		}
		
		containerBestellung = new BeanItemContainer<BestellungData>(BestellungData.class, list);
		bestellungTable.setContainerDataSource(containerBestellung);
		
		if(lieferant.getMehrereliefertermine()==true){
		bestellungTable.setVisibleColumns(new Object[] {"name", "gebinde", "kategorie", "durchschnitt", "kantine", "gesamt", "freitag", "montag"});
		} else {
			bestellungTable.setVisibleColumns(new Object[] {"name", "gebinde", "kategorie", "durchschnitt", "kantine", "gesamt"});
			lieferdatum.setVisible(true);
		}
		
		containerArtikel = new BeanItemContainer<Artikel>(Artikel.class, artikel);
		artikelTable.setContainerDataSource(containerArtikel);
		artikelTable.setVisibleColumns(new Object[] {"name", "artikelnr"});
	}
}

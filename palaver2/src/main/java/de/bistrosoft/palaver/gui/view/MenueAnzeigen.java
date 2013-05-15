package de.bistrosoft.palaver.gui.view;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnResizeEvent;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.bistrosoft.palaver.artikelverwaltung.domain.Artikel;
import de.bistrosoft.palaver.artikelverwaltung.service.Artikelverwaltung;
import de.bistrosoft.palaver.data.ArtikelDAO;
import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.data.MenueDAO;
import de.bistrosoft.palaver.data.MitarbeiterDAO;
import de.bistrosoft.palaver.data.RezeptDAO;
import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.menueplanverwaltung.WinSelectMenue;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasFussnote;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasRezept;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueverwaltung;
import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.bistrosoft.palaver.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptartverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.bistrosoft.palaver.util.View;
import de.bistrosoft.palaver.util.ViewData;
import de.bistrosoft.palaver.util.ViewDataObject;
import de.bistrosoft.palaver.util.ViewHandler;
import de.bistrosoft.palaver.gui.view.WinSelectArtikel;


@SuppressWarnings("serial")
public class MenueAnzeigen extends VerticalLayout implements View {

	private VerticalLayout box = new VerticalLayout();
	private VerticalLayout box1 = new VerticalLayout();
	private HorizontalLayout horizont1 = new HorizontalLayout();
	private HorizontalLayout horizont2 = new HorizontalLayout();
	private HorizontalLayout horizont3 = new HorizontalLayout();

	Menue menue;
	
	Integer z = 0;
	
	private Label ueberschrift = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Menü Anzeigen</font><b></pre>",
			Label.CONTENT_XHTML);

	
    private TextField menuename = new TextField();
    private TextField ersteller = new TextField();
	private TextField hauptgericht = new TextField();
	private TextField beilage1 = new TextField();
	private TextField beilage2 = new TextField();
	private Table tabel = new Table("Fussnoten");
	
	private Label uMenuename = new Label(
			"<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Menuename</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label uErsteller = new Label(
			"<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Koch</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label uHauptgericht = new Label(
			"<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Hauptgericht</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label uBeilage1 = new Label(
			"<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Beilage1</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label uBeilage2 = new Label(
			"<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Beilage2</font><b></pre>",
			Label.CONTENT_XHTML);
	
    
	private TwinColSelect fussnoten = new TwinColSelect("Fussnoten");
	

	List<Rezept> listrezept = new ArrayList<Rezept>();
	
	
	private String hauptgerichtInput;
	private String beilage1Input;
	private String beilage2Input;
	private String menuenameInput;
	private String erstellerInput;
	public String valueString = new String();
	
	private MenueHasFussnote mhf;
	
	private List<MenueHasFussnote> fusstontenlist = new ArrayList<MenueHasFussnote>();

	private Button neuRezept = new Button("neues Rezept anlegen");

	private Button btAdd = new Button("Add");

	public MenueAnzeigen() {
		super();
		this.setSizeFull();
		this.setMargin(true);
		hauptgericht.setWidth("100%");
		beilage1.setWidth("100%");
		beilage2.setWidth("100%");
		fussnoten.setWidth("100%");
		menuename.setWidth("100%");
	
		
		
		
		box.setWidth("300px");
		box.setSpacing(true);

		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		box.addComponent(ueberschrift);
		box.addComponent(menuename);
		box.addComponent(ersteller);
		box.addComponent(horizont1);
		box.addComponent(horizont2);
		box.addComponent(horizont3);
		box.setComponentAlignment(horizont1, Alignment.MIDDLE_CENTER);
		box.setComponentAlignment(horizont2, Alignment.MIDDLE_CENTER);
		box.setComponentAlignment(horizont3, Alignment.MIDDLE_CENTER);
		horizont1.addComponent(box1);
		horizont1.setComponentAlignment(box1, Alignment.MIDDLE_CENTER);
		box1.addComponent(ueberschrift);
		box1.addComponent(uMenuename);
		box1.addComponent(menuename);
		box1.addComponent(uErsteller);
		box1.addComponent(ersteller);
		box1.addComponent(uHauptgericht);
		box1.addComponent(hauptgericht);
		box1.addComponent(uBeilage1);
		box1.addComponent(beilage1);
		box1.addComponent(uBeilage2);
		box1.addComponent(beilage2);
		
//		
		// ///////////////////////////////////
		BeanItemContainer<RezeptHasArtikel> container;

		
		HorizontalLayout control = new HorizontalLayout();
		control.setSpacing(true);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);

		hauptgericht.setImmediate(true);
		hauptgericht.setInputPrompt(hauptgerichtInput);
		
	
		menuename.setImmediate(true);
		menuename.setInputPrompt(menuenameInput);
		ersteller.setImmediate(true);
		ersteller.setInputPrompt(erstellerInput);
		
		beilage1.setImmediate(true);
		beilage1.setInputPrompt(beilage1Input);	
		

		beilage2.setImmediate(true);
		beilage2.setInputPrompt(beilage2Input);
	
		fussnoten.setImmediate(true);
	
		
		load();

		

		hauptgericht.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());
               
				hauptgerichtInput = valueString;
				
			}
		});
		beilage1.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				beilage1Input = valueString;
			}
		});
		beilage2.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				beilage2Input = valueString;
			}
		});
		
		ersteller.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				erstellerInput = valueString;
			}
		});
		
		menuename.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				menuenameInput = valueString;
			}
		});
		
		fussnoten.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String
						.valueOf(event.getProperty().getValue());

			}
		});	
		
		
	}
		

		



	public void load() {
		
	


	}
	@Override
	public void getViewParam(ViewData data)
	{
	menue = new Menue();
	menue = (Menue) ((ViewDataObject<?>)data).getData();



	menuename.setValue(menue.getName());
	menuename.setReadOnly(true);
	ersteller.setValue(menue.getKochname());
	ersteller.setReadOnly(true);
	try {
		hauptgericht.setValue(MenueDAO.getInstance().getHauptgerichtByMenue(menue.getId()));
	} catch (ReadOnlyException | ConnectException | DAOException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	try {
		listrezept = MenueDAO.getInstance().getBeilagenByMenue(menue.getId());
	} catch (ConnectException | DAOException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	if(listrezept != null) {
		z = listrezept.size();
	System.out.println(z);
	if(z < 1){
	//	System.out.println("wwwww");
	}
	
	else {
		Integer j = 0;
		if(z == 2){
			
			for (Rezept x : listrezept) {
				
				if(j == 0) {
					beilage1.setValue(x.getName());
					j = j + 1;
				}
				else{
					beilage2.setValue(x.getName());
					
				}
				
			}
			
			
		}
		else{
			for (Rezept t : listrezept) {
				beilage1.setValue(t.getName());
			//	System.out.println("eine");
			}
		}
	}
	}
	else {
	//	System.out.println("sssssss");
	}
	hauptgericht.setReadOnly(true);
	beilage1.setReadOnly(true);
	beilage2.setReadOnly(true);
//	if(listrezept.isEmpty() != true) {
//		
//		
//		
//	}
//	artnr.setValue(artikel.getArtikelnr());
//	preis.setValue(artikel.getPreis() + "");
//	lieferant.select(artikel.getLieferant());
//	mengeneinheit.select(artikel.getMengeneinheit());
//	kategorie.select(artikel.getKategorie());
//	bestellung.setValue(artikel.getBestellgroesse() + "");
//	standard.setValue(artikel.isStandard());
//	grundbedarf.setValue(artikel.isGrundbedarf());
//	bio.setValue(artikel.isBio());
//	lebensmittel.setValue(artikel.isLebensmittel());
//	durchschnitt.setValue(artikel.getDurchschnitt() + "");
	}
}

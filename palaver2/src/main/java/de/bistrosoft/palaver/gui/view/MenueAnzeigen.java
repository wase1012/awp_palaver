package de.bistrosoft.palaver.gui.view;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import de.bistrosoft.palaver.util.ViewHandler;
import de.bistrosoft.palaver.gui.view.WinSelectArtikel;


@SuppressWarnings("serial")
public class MenueAnzeigen extends VerticalLayout implements View {

	private VerticalLayout box = new VerticalLayout();
	private HorizontalLayout horizont1 = new HorizontalLayout();
	private HorizontalLayout horizont2 = new HorizontalLayout();
	private HorizontalLayout horizont3 = new HorizontalLayout();

	
	
	
	
	private Label ueberschrift = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Menue anlegen</font><b></pre>",
			Label.CONTENT_XHTML);

	
    private TextField menuename = new TextField("Menuename");
    private ComboBox ersteller = new ComboBox("Menueersteller");
	private ComboBox hauptgericht = new ComboBox("Hauptgericht");
	private ComboBox beilage1 = new ComboBox("Beilage 1");
	private ComboBox beilage2 = new ComboBox("Beilage 2");
    
	private TwinColSelect fussnoten = new TwinColSelect("Fussnoten");
	

	
	
	
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
		
		
//		
		// ///////////////////////////////////
		BeanItemContainer<RezeptHasArtikel> container;

		
		HorizontalLayout control = new HorizontalLayout();
		control.setSpacing(true);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);

		hauptgericht.setImmediate(true);
		hauptgericht.setInputPrompt(hauptgerichtInput);
		hauptgericht.setNullSelectionAllowed(false);
	
		menuename.setImmediate(true);
		menuename.setInputPrompt(menuenameInput);
		ersteller.setImmediate(true);
		ersteller.setInputPrompt(erstellerInput);
		ersteller.setNullSelectionAllowed(false);
		beilage1.setImmediate(true);
		beilage1.setInputPrompt(beilage1Input);	
		beilage1.setNullSelectionAllowed(false);

		beilage2.setImmediate(true);
		beilage2.setInputPrompt(beilage2Input);
		beilage2.setNullSelectionAllowed(false);

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
		try {
			
			
			List<Mitarbeiter> mitarbeiter = Mitarbeiterverwaltung.getInstance()
					.getAllMitarbeiter();
			for (Mitarbeiter e : mitarbeiter) {
				ersteller.addItem(e.getId());
				ersteller.setItemCaption(e.getId(), e.getName());

			}
//
			List<Rezept> rezept = Rezeptverwaltung.getInstance()
					.getAllRezepteM();
			for (Rezept e : rezept) {
				hauptgericht.addItem(e.getId());
				hauptgericht.setItemCaption(e.getId(), e.getName());
			}
//
			List<Rezept> rezept1 = Rezeptverwaltung.getInstance()
					.getAllRezepteM();
			for (Rezept e : rezept1) {
				// mitarbeiterCb.addItem(e);
				beilage1.addItem(e.getId());
				beilage1.setItemCaption(e.getId(), e.getName());
			}
			
			List<Rezept> rezept2 = Rezeptverwaltung.getInstance()
					.getAllRezepteM();
			for (Rezept e : rezept2) {
				// mitarbeiterCb.addItem(e);
				beilage2.addItem(e.getId());
				beilage2.setItemCaption(e.getId(), e.getName());
			}
			
			List<Fussnote> fussnote = Fussnotenverwaltung.getInstance().getAllFussnote();
			for (Fussnote e : fussnote) {
				fussnoten.addItem(e.getId());
				fussnoten.setItemCaption(e.getId(), e.getName());
			}
			
			

		} catch (ConnectException | DAOException | SQLException e) {
			e.printStackTrace();
		}
	


	}
	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}
}

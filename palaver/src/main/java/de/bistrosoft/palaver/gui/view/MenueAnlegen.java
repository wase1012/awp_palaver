package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tepi.filtertable.FilterTable;


import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
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
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.data.FussnoteDAO;
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.data.MenueDAO;
import de.bistrosoft.palaver.data.MenueartDAO;
import de.bistrosoft.palaver.data.MitarbeiterDAO;
import de.bistrosoft.palaver.data.RezeptDAO;
import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasFussnote;
import de.bistrosoft.palaver.menueplanverwaltung.domain.MenueHasRezept;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueart;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueartverwaltung;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueverwaltung;
import de.bistrosoft.palaver.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.bistrosoft.palaver.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptartverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;



@SuppressWarnings("serial")
public class MenueAnlegen extends VerticalLayout implements View,
               ValueChangeListener {

	
	List<Rezept> listrezept = new ArrayList<Rezept>();
	List<Fussnote> listfussnote = new ArrayList<Fussnote>();
	Menue menue2;
	Integer z = 0;
	private VerticalLayout box = new VerticalLayout();
	private HorizontalLayout horizont1 = new HorizontalLayout();
	private HorizontalLayout horizont2 = new HorizontalLayout();
	private HorizontalLayout horizont3 = new HorizontalLayout();
	private VerticalLayout links = new VerticalLayout();
	private VerticalLayout mitte = new VerticalLayout();
	private VerticalLayout rechts = new VerticalLayout();
	private VerticalLayout links2 = new VerticalLayout();
	private VerticalLayout mitte2 = new VerticalLayout();
	private VerticalLayout rechts2 = new VerticalLayout();
	private VerticalLayout links3 = new VerticalLayout();
	private VerticalLayout mitte3 = new VerticalLayout();
	private VerticalLayout rechts3 = new VerticalLayout();
	private VerticalLayout blank2 = new VerticalLayout();
	private VerticalLayout b1 = new VerticalLayout();
	private VerticalLayout b2 = new VerticalLayout();
	private VerticalLayout b3 = new VerticalLayout();
	private HorizontalLayout control = new HorizontalLayout();
	private HorizontalLayout cbs = new HorizontalLayout();
	
	private Button update = new Button("Ändern");
	
	private Label ueberschrift = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Menue anlegen</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label ueberschrift2 = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Menue ändern</font><b></pre>",
			Label.CONTENT_XHTML);

	private Label dummy = new Label(
			"<div>&nbsp;&nbsp;&nbsp;</div>",
			Label.CONTENT_XHTML);
	private Label dummy1 = new Label(
			"<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>",
			Label.CONTENT_XHTML);
	private Label dummy2 = new Label(
			"<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>",
			Label.CONTENT_XHTML);
	private Label dummy3 = new Label(
			"<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>",
			Label.CONTENT_XHTML);
	private Label dummy4 = new Label(
			"<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>",
			Label.CONTENT_XHTML);
	private Label d1 = new Label(
			"<div>&nbsp;&nbsp;&nbsp;</div>",
			Label.CONTENT_XHTML);
	private Label d2 = new Label(
			"<div>&nbsp;&nbsp;&nbsp;</div>",
			Label.CONTENT_XHTML);
	private Label d3 = new Label(
			"<div>&nbsp;&nbsp;&nbsp;</div>",
			Label.CONTENT_XHTML);
	private Label d4 = new Label(
			"<div>&nbsp;&nbsp;&nbsp;</div>",
			Label.CONTENT_XHTML);
    private TextField menuename = new TextField("Menuename");
    private ComboBox ersteller = new ComboBox("Menueersteller");
	private TextField hauptgericht = new TextField("Hauptgericht");
	private TextField beilage1 = new TextField("Beilage 1");
	private TextField beilage2 = new TextField("Beilage 2");
    
	private TwinColSelect fussnoten = new TwinColSelect("Fussnoten");
	
	
	private ComboBox menueartCb = new ComboBox("Menueart");
	private ComboBox geschmackCb = new ComboBox("Geschmack");

	private CheckBox favorit = new CheckBox("Favorit");
	private CheckBox aufwand = new CheckBox("Aufwand");
	

	private Button speichern = new Button("Speichern");
	private Button verwerfen = new Button("Verwerfen");
	private Button neuesRezept = new Button("neues Rezeptanlegen");
	private Button hAnsehen = new Button("Hauptegricht ansehen");
	private Button b1Ansehen = new Button("Beilage1 ansehen    ");
	private Button b2Ansehen = new Button("Beilage2 ansehen    ");
	
	
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

	
	private String geschmackInput;
	private String menueartInput;
	
	
	/////
	private FilterTable hauptgerichttable;
	BeanItemContainer<Rezept> hauptrezeptcontainer;
	private Button showFilter;
	private Rezept hauptrezept;
	
	private FilterTable beilage1table;
	BeanItemContainer<Rezept> beilage1container;
	private Button beilage1showFilter;
	private Rezept beilage1rezept;
	
	
	private FilterTable beilage2table;
	BeanItemContainer<Rezept> beilage2container;
	private Button beilage2showFilter;
	private Rezept beilage2rezept;
	
	private String beilageid;
	private String beilage2id;
	private String hauptgerichtid;
	//////
	
	public MenueAnlegen() {
		super();
		this.setSizeFull();
		this.setMargin(true);
		hauptgericht.setWidth("100%");
		beilage1.setWidth("100%");
		beilage2.setWidth("100%");
		fussnoten.setWidth("100%");
		menuename.setWidth("100%");
        b1Ansehen.setWidth("150");
        b2Ansehen.setWidth("150");
        hAnsehen.setWidth("150");
		
        hauptgerichttable = new FilterTable();
		hauptgerichttable.setSizeFull();
		hauptgerichttable.setFilterBarVisible(false);
		hauptgerichttable.setFilterGenerator(new customFilter());
		hauptgerichttable.setFilterDecorator(new customFilterDecorator());
		hauptgerichttable.setSelectable(true);
		showFilter = new Button(IConstants.BUTTON_SHOW_FILTER);
		
		beilage1table = new FilterTable();
		beilage1table.setSizeFull();
		beilage1table.setFilterBarVisible(false);
		beilage1table.setFilterGenerator(new customFilter());
		beilage1table.setFilterDecorator(new customFilterDecorator());
		beilage1table.setSelectable(true);
		beilage1showFilter = new Button(IConstants.BUTTON_SHOW_FILTER);
		
		
		beilage2table = new FilterTable();
		beilage2table.setSizeFull();
		beilage2table.setFilterBarVisible(false);
		beilage2table.setFilterGenerator(new customFilter());
		beilage2table.setFilterDecorator(new customFilterDecorator());
		beilage2table.setSelectable(true);
		beilage2showFilter = new Button(IConstants.BUTTON_SHOW_FILTER);
		box.setWidth("300px");
		box.setSpacing(true);

		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		box.addComponent(ueberschrift);
		box.addComponent(menuename);
		box.addComponent(ersteller);
		box.addComponent(menueartCb);
		box.addComponent(geschmackCb);
		box.addComponent(cbs);
		box.setComponentAlignment(cbs, Alignment.MIDDLE_CENTER);
		cbs.addComponent(favorit);
		cbs.addComponent(d4);
		cbs.addComponent(aufwand);
		box.addComponent(horizont1);
		box.addComponent(horizont2);
		box.addComponent(horizont3);
		box.setComponentAlignment(horizont1, Alignment.MIDDLE_CENTER);
		box.setComponentAlignment(horizont2, Alignment.MIDDLE_CENTER);
		box.setComponentAlignment(horizont3, Alignment.MIDDLE_CENTER);
		horizont1.addComponent(links);
		horizont1.addComponent(b1);
		horizont1.addComponent(mitte);
		horizont1.addComponent(rechts);
		horizont1.setComponentAlignment(links, Alignment.BOTTOM_CENTER);
		horizont1.setComponentAlignment(mitte, Alignment.MIDDLE_CENTER);
		horizont1.setComponentAlignment(rechts, Alignment.MIDDLE_CENTER);
		horizont2.addComponent(links2);
		horizont2.addComponent(b2);
		horizont2.addComponent(mitte2);
		horizont2.addComponent(blank2);
		horizont2.addComponent(rechts2);
		horizont2.setComponentAlignment(links2, Alignment.MIDDLE_CENTER);
		horizont2.setComponentAlignment(mitte2, Alignment.MIDDLE_CENTER);
		horizont2.setComponentAlignment(rechts2, Alignment.MIDDLE_CENTER);
		horizont3.addComponent(links3);
		horizont3.addComponent(b3);
		horizont3.addComponent(mitte3);
		horizont3.addComponent(rechts3);
		horizont3.setComponentAlignment(links3, Alignment.MIDDLE_CENTER);
		horizont3.setComponentAlignment(mitte3, Alignment.MIDDLE_RIGHT);
		horizont3.setComponentAlignment(rechts3, Alignment.MIDDLE_CENTER);
		links.addComponent(hauptgericht);
		links.addComponent(showFilter);
		links.setComponentAlignment(showFilter, Alignment.MIDDLE_RIGHT);
		links.addComponent(hauptgerichttable);
		links.setExpandRatio(hauptgerichttable, 1);		
		links2.addComponent(beilage1);
		links2.addComponent(beilage1showFilter);
		links2.setComponentAlignment(beilage1showFilter, Alignment.MIDDLE_RIGHT);
		links2.addComponent(beilage1table);
		links2.setExpandRatio(beilage1table, 1);
		links3.addComponent(beilage2);
		links3.addComponent(beilage2showFilter);
		links3.setComponentAlignment(beilage2showFilter, Alignment.MIDDLE_RIGHT);
		links3.addComponent(beilage2table);
		links3.setExpandRatio(beilage2table, 1);
		mitte.addComponent(dummy1);
		mitte2.addComponent(dummy2);
		mitte3.addComponent(dummy3);
		mitte.addComponent(hAnsehen);
		mitte2.addComponent(b1Ansehen);
		blank2.addComponent(dummy);
		mitte3.addComponent(b2Ansehen);
		rechts2.addComponent(dummy4);
		rechts2.addComponent(neuesRezept);
		box.addComponent(fussnoten);
		b1.addComponent(d1);
		b2.addComponent(d2);
		b3.addComponent(d3);
		
		
		/////Hauptgericht//////
		
		
		// showFilter.setIcon(new ThemeResource("img/filter.ico"));

		

		

		

		

	

		showFilter.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (hauptgerichttable.isFilterBarVisible()) {
					hauptgerichttable.setFilterBarVisible(false);
					hauptgerichttable.resetFilters();
					showFilter.setCaption(IConstants.BUTTON_SHOW_FILTER);
					// showFilter.setIcon(new ThemeResource("img/filter.ico"));
				} else {
					hauptgerichttable.setFilterBarVisible(true);
					showFilter.setCaption(IConstants.BUTTON_HIDE_FILTER);
					// showFilter.setIcon(new
					// ThemeResource("img/disable_filter.ico"));
				}
			}
		});
		beilage1showFilter.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (beilage1table.isFilterBarVisible()) {
					beilage1table.setFilterBarVisible(false);
					beilage1table.resetFilters();
					beilage1showFilter.setCaption(IConstants.BUTTON_SHOW_FILTER);
					// showFilter.setIcon(new ThemeResource("img/filter.ico"));
				} else {
					beilage1table.setFilterBarVisible(true);
					beilage1showFilter.setCaption(IConstants.BUTTON_HIDE_FILTER);
					// showFilter.setIcon(new
					// ThemeResource("img/disable_filter.ico"));
				}
			}
		});
		
		beilage2showFilter.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (beilage2table.isFilterBarVisible()) {
					beilage2table.setFilterBarVisible(false);
					beilage2table.resetFilters();
					beilage2showFilter.setCaption(IConstants.BUTTON_SHOW_FILTER);
					// showFilter.setIcon(new ThemeResource("img/filter.ico"));
				} else {
					beilage2table.setFilterBarVisible(true);
					beilage2showFilter.setCaption(IConstants.BUTTON_HIDE_FILTER);
					// showFilter.setIcon(new
					// ThemeResource("img/disable_filter.ico"));
				}
			}
		});

		
//		
		// ///////////////////////////////////
		

		
		
		control.setSpacing(true);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);

		hauptgericht.setImmediate(true);
		hauptgericht.setInputPrompt(hauptgerichtInput);
	
	
		menuename.setImmediate(true);
		menuename.setInputPrompt(menuenameInput);
		ersteller.setImmediate(true);
		ersteller.setInputPrompt(erstellerInput);
		ersteller.setNullSelectionAllowed(false);
		beilage1.setImmediate(true);
		beilage1.setInputPrompt(beilage1Input);	
		//beilage1.setNullSelectionAllowed(false);

		beilage2.setImmediate(true);
		beilage2.setInputPrompt(beilage2Input);
		//beilage2.setNullSelectionAllowed(false);

		fussnoten.setImmediate(true);
		fussnoten.setWidth("400");
	
		
		load();

		control.addComponent(verwerfen);
		control.addComponent(speichern);
		speichern.setIcon(new ThemeResource("img/save.ico"));
		verwerfen.setIcon(new ThemeResource("img/cross.ico"));
		
		
		
		hauptgerichttable.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					hauptrezept = (Rezept) event.getProperty().getValue();
				}

			}
		});

		hauptgerichttable.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					hauptgericht.setValue(hauptrezept.getName());
					hauptgerichtid = ((hauptrezept.getId()).toString());
				}

			}
		});
		
		beilage1table.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					beilage1rezept = (Rezept) event.getProperty().getValue();
				}

			}
		});

		beilage1table.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					beilage1.setValue(beilage1rezept.getName());
					beilageid = ((beilage1rezept.getId()).toString());
				}

			}
		});
		
		beilage2table.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					beilage2rezept = (Rezept) event.getProperty().getValue();
				}

			}
		});

		beilage2table.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					beilage2.setValue(beilage2rezept.getName());
					beilage2id = ((beilage2rezept.getId()).toString());
				}

			}
		});

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
		
		geschmackCb.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
				geschmackInput = valueString;
			}
		});
		menueartCb.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
				menueartInput = valueString;
			}
		});
		
		
		verwerfen.addClickListener(new ClickListener(){
			@Override
			public void buttonClick(ClickEvent event)
			{
			ViewHandler.getInstance().switchView(MenueAnlegen.class);
			}
		});
		
		neuesRezept.addClickListener(new ClickListener(){
			@Override
			public void buttonClick(ClickEvent event)
			{
			ViewHandler.getInstance().switchView(RezeptAnlegen.class);
			}
		});
		hAnsehen.addClickListener(new ClickListener(){
			@Override
			public void buttonClick(ClickEvent event)
			{
				if (hauptgericht.getValue() != null) {
					try {
						ViewHandler.getInstance().switchView(RezeptAnlegen.class,
								new ViewDataObject<Rezept>(RezeptDAO.getInstance().getRezept1(Long.parseLong(hauptgerichtInput.toString()))));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					Notification notification = new Notification("es wurde kein Rezept augewählt");
					notification.setDelayMsec(500);
					notification.show(Page.getCurrent());
					
				}
			
			
			
			}
		});
		b1Ansehen.addClickListener(new ClickListener(){
			@Override
			public void buttonClick(ClickEvent event)
			{
				
				if (beilage1.getValue() != null) {
					try {
						ViewHandler.getInstance().switchView(RezeptAnlegen.class,
								new ViewDataObject<Rezept>(RezeptDAO.getInstance().getRezept1(Long.parseLong(beilage1Input.toString()))));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					Notification notification = new Notification("es wurde kein Rezept augewählt");
					notification.setDelayMsec(500);
					notification.show(Page.getCurrent());
					
				}
			
			}
		});
		b2Ansehen.addClickListener(new ClickListener(){
			@Override
			public void buttonClick(ClickEvent event)
			{
				if (beilage2.getValue() != null) {
					try {
						ViewHandler.getInstance().switchView(RezeptAnlegen.class,
								new ViewDataObject<Rezept>(RezeptDAO.getInstance().getRezept1(Long.parseLong(beilage2Input.toString()))));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						}
						else {
							Notification notification = new Notification("es wurde kein Rezept augewählt");
							notification.setDelayMsec(500);
							notification.show(Page.getCurrent());
							
						}
			}
		});
		

		speichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				
				
				
				
				Menue menue = new Menue();
				MenueHasFussnote fussnote = new MenueHasFussnote();

				
if (menuename.getValue() != null) {
					
					
					if (ersteller.getValue() != null) {
				
				
				menue.setName(menuenameInput);
				menue.setAufwand(aufwand.getValue());
				menue.setFavorit(favorit.getValue());

				try {
					menue.setKoch(MitarbeiterDAO.getInstance()
							.getMitarbeiterById(
									Long.parseLong(erstellerInput.toString())));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				System.out.println(geschmackInput);
				System.out.println("kurz vor geschmack");
				try {
					menue.setGeschmack(GeschmackDAO.getInstance()
							.getGeschmackById(
									Long.parseLong(geschmackInput.toString())));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				System.out.println("menueartInput");
				System.out.println("kurz vor menueart");
				try {
					menue.setMenueart(MenueartDAO.getInstance()
							.getMenueartById(
									Long.parseLong(menueartInput.toString())));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				System.out.println("kurz vor speichern");
				
				try {
					Menueverwaltung.getInstance().createMenue(menue);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
/// Liste der Fussnoten
				System.out.println("Name des Menu");
				System.out.println(menuenameInput);
				Menue menue1 = null;
				try {
					menue1 = Menueverwaltung.getInstance().getMenueByName(menuenameInput);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("name des geholten menu");
				System.out.println(menue1.getName());
				

				if(fussnoten.getValue().toString() != "[]"){
					List<String> FussnoteId = Arrays.asList(valueString.substring(1,
							valueString.length() - 1).split("\\s*,\\s*"));
					for (String s : FussnoteId) {
	//					System.out.println(s);
					}
					
					// valueString.split
					BeanItemContainer<MenueHasFussnote> fussnotencontainer;
					List<MenueHasFussnote> fussnotelist = new ArrayList<MenueHasFussnote>();
	
					for (String sId : FussnoteId) {
						Long id = null;
						try {
							id = Long.parseLong(sId.trim());
	
						} catch (NumberFormatException nfe) {
	
						}
	
						Fussnote fussnote1 = null;
	//					Menue menue1 =  Menueverwaltung.getInstance()
	//							.getMenueByName(menuenameInput);
						try {
							
							
							fussnote1 = Fussnotenverwaltung.getInstance()
									.getFussnoteById(id);
	//						
							MenueHasFussnote a = new MenueHasFussnote(fussnote1,
									menue1);
							fussnotelist.add(a);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	
					}
					
					for (MenueHasFussnote i :fussnotelist) {
						
						
						try{
						Menueverwaltung.getInstance().FussnoteAdd(i);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					}
				}
				
				
				
///////////////	
//Hauptgericht
				if (hauptgericht.getValue() != null) {
				
				Rezept rezept = new Rezept();
				System.out.println(hauptgerichtInput);
				try {
					rezept = RezeptDAO.getInstance().getRezept1(Long.parseLong(hauptgerichtid.toString()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				MenueHasRezept mhr = new MenueHasRezept(menue1, rezept, true);
				
				try{
					Menueverwaltung.getInstance().RezepteAdd(mhr);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
				
				}
				
				else {
					
				}
////////	
				
///B1    
				
				if (beilageid != null) {
				
Rezept rezept1 = new Rezept();
				System.out.println(beilage1Input);
				try {
					rezept1 = RezeptDAO.getInstance().getRezept1(Long.parseLong(beilageid.toString()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				 if (rezept1 != null) {
				MenueHasRezept mhr1 = new MenueHasRezept(menue1, rezept1, false);
				
				try{
					Menueverwaltung.getInstance().RezepteAdd(mhr1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				 
				 else {
					 
				 }
//B2
				if (beilage2id != null) {	
Rezept rezept2 = new Rezept();
System.out.println(beilage2Input);
				try {
					rezept2 = RezeptDAO.getInstance().getRezept1(Long.parseLong(beilage2id.toString()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				MenueHasRezept mhr2 = new MenueHasRezept(menue1, rezept2, false);
				
				try{
					Menueverwaltung.getInstance().RezepteAdd(mhr2);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				else {
					
				}
				
				
				
				
				
				
				
			
				Notification notification = new Notification("Menue wurde gespeichert!");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
			//	ViewHandler.getInstance().switchView(MenueAnzeigenTabelle.class);
				//System.out.println(ausgArtikel.size());
					}
					else{
						Notification notification = new Notification("Bitte geben Sie den Menüersteller an einen Namen");
						notification.setDelayMsec(500);
						notification.show(Page.getCurrent());
						
					}
				}
				else{ 
					Notification notification = new Notification("Bitte geben sie dem Menue einen Namen");
					notification.setDelayMsec(500);
					notification.show(Page.getCurrent());
					
				}
			}
		});
	}



	public void load() {
		try {
			
			
			List<Mitarbeiter> mitarbeiter = Mitarbeiterverwaltung.getInstance()
					.getAllMitarbeiter();
			for (Mitarbeiter e : mitarbeiter) {
				ersteller.addItem(e.getId());
				ersteller.setItemCaption(e.getId(), e.getVorname());

			}
			
				List<Geschmack> geschmack = Geschmackverwaltung.getInstance()
						.getAllGeschmackAktiv();
				for (Geschmack e : geschmack) {
					geschmackCb.addItem(e.getId());
					geschmackCb.setItemCaption(e.getId(), e.getName());

				}
				
				List<Menueart> menueart = Menueartverwaltung.getInstance()
						.getAllMenueart();
				for (Menueart e : menueart) {
					menueartCb.addItem(e.getId());
					menueartCb.setItemCaption(e.getId(), e.getName());
				}

			
				BeanItemContainer<Rezept> hauptrezeptcontainer;
				System.out.println("Container erstellt");
				try {
					hauptrezeptcontainer = new BeanItemContainer<Rezept>(Rezept.class,
							Rezeptverwaltung.getInstance().getAllRezepte());
				
					hauptgerichttable.setContainerDataSource(hauptrezeptcontainer);
					
					hauptgerichttable.setVisibleColumns(new Object[] {"id", "name", "rezeptart",
							"geschmack", "mitarbeiter", "erstellt" });
				
					hauptgerichttable.sort(new Object[] { "name" }, new boolean[] { true });
				} catch (Exception e) {
					e.printStackTrace();
				}
			
				BeanItemContainer<Rezept> beilage1container;
				System.out.println("Container erstellt");
				try {
					beilage1container = new BeanItemContainer<Rezept>(Rezept.class,
							Rezeptverwaltung.getInstance().getAllRezepte());
				
					beilage1table.setContainerDataSource(beilage1container);
					
					beilage1table.setVisibleColumns(new Object[] {"id", "name", "rezeptart",
							"geschmack", "mitarbeiter", "erstellt" });
				
					beilage1table.sort(new Object[] { "name" }, new boolean[] { true });
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				BeanItemContainer<Rezept> beilage2container;
				System.out.println("Container erstellt");
				try {
					beilage2container = new BeanItemContainer<Rezept>(Rezept.class,
							Rezeptverwaltung.getInstance().getAllRezepte());
				
					beilage2table.setContainerDataSource(beilage2container);
					
					beilage2table.setVisibleColumns(new Object[] {"id", "name", "rezeptart",
							"geschmack", "mitarbeiter", "erstellt" });
				
					beilage2table.sort(new Object[] { "name" }, new boolean[] { true });
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
//
			
			List<Fussnote> fussnote = Fussnotenverwaltung.getInstance().getAllFussnote();
			for (Fussnote e : fussnote) {
				fussnoten.addItem(e.getId());
				fussnoten.setItemCaption(e.getId(), e.getName());
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	
		

	}
	@Override
	public void getViewParam(ViewData data) {
		menue2 = new Menue();
		menue2 = (Menue) ((ViewDataObject<?>)data).getData();
		control.replaceComponent(speichern, update);
		box.replaceComponent(ueberschrift, ueberschrift2);
		
		update.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		
		update.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				
				
				
				System.out.println("geaendert");
				Menue menue = new Menue();
				MenueHasFussnote fussnote = new MenueHasFussnote();

				
if (menuename.getValue() != null) {
					
					
					if (ersteller.getValue() != null) {
				
				menue.setId(menue2.getId());
				menue.setName(menuenameInput);
				menue.setAufwand(aufwand.getValue());
				menue.setFavorit(favorit.getValue());

				try {
					menue.setKoch(MitarbeiterDAO.getInstance()
							.getMitarbeiterById(
									Long.parseLong(erstellerInput.toString())));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				
				
				
				try {
					menue.setGeschmack(GeschmackDAO.getInstance()
							.getGeschmackById(
									Long.parseLong(geschmackInput.toString())));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				try {
					menue.setMenueart(MenueartDAO.getInstance()
							.getMenueartById(
									Long.parseLong(menueartInput.toString())));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				try {
					Menueverwaltung.getInstance().updateMenue(menue);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
/// Liste der Fussnoten
				
//				Menue menue1 = null;
//				try {
//					menue1 = Menueverwaltung.getInstance().getMenueByName(menuenameInput);
//				} catch (ConnectException | DAOException | SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				
				try {
					Menueverwaltung.getInstance().FussnoteDelete(menue2);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					Menueverwaltung.getInstance().RezepteDelete(menue2);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

				if(fussnoten.getValue().toString() != "[]"){
					List<String> FussnoteId = Arrays.asList(valueString.substring(1,
							valueString.length() - 1).split("\\s*,\\s*"));
					for (String s : FussnoteId) {
	//					System.out.println(s);
					}
					// valueString.split
					BeanItemContainer<MenueHasFussnote> fussnotencontainer;
					List<MenueHasFussnote> fussnotelist = new ArrayList<MenueHasFussnote>();
	
					for (String sId : FussnoteId) {
						Long id = null;
						try {
							id = Long.parseLong(sId.trim());
	
						} catch (NumberFormatException nfe) {
	
						}
	
						Fussnote fussnote1 = null;
	//					Menue menue1 =  Menueverwaltung.getInstance()
	//							.getMenueByName(menuenameInput);
						try {
							
							
							fussnote1 = Fussnotenverwaltung.getInstance()
									.getFussnoteById(id);
	//						
							MenueHasFussnote a = new MenueHasFussnote(fussnote1,
									menue2);
							fussnotelist.add(a);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	
					}
					
					for (MenueHasFussnote i :fussnotelist) {
						
						
						try{
						Menueverwaltung.getInstance().FussnoteAdd(i);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					}
				}
				
				
				
///////////////	
//Hauptgericht
				if (hauptgericht.getValue() != null) {
				
				Rezept rezept = new Rezept();
				
				try {
					rezept = RezeptDAO.getInstance().getRezept1(Long.parseLong(hauptgerichtInput.toString()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				MenueHasRezept mhr = new MenueHasRezept(menue2, rezept, true);
				
				try{
					Menueverwaltung.getInstance().RezepteAdd(mhr);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
				
				}
				
				else {
					
				}
////////	
				
///B1    
				
				if (beilage1.getValue() != null) {
				
Rezept rezept1 = new Rezept();
				
				try {
					rezept1 = RezeptDAO.getInstance().getRezept1(Long.parseLong(beilage1Input.toString()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				 if (rezept1 != null) {
				MenueHasRezept mhr1 = new MenueHasRezept(menue2, rezept1, false);
				
				try{
					Menueverwaltung.getInstance().RezepteAdd(mhr1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				 
				 else {
					 
				 }
//B2
				if (beilage2.getValue() != null) {	
Rezept rezept2 = new Rezept();
				
				try {
					rezept2 = RezeptDAO.getInstance().getRezept1(Long.parseLong(beilage2Input.toString()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				MenueHasRezept mhr2 = new MenueHasRezept(menue2, rezept2, false);
				
				try{
					Menueverwaltung.getInstance().RezepteAdd(mhr2);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				else {
					
				}
				
				
				
				
				
				
				
				
				Notification notification = new Notification("Menue wurde geaendert!");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
				ViewHandler.getInstance().switchView(MenueAnzeigenTabelle.class);
				//System.out.println(ausgArtikel.size());
					}
					else{
						Notification notification = new Notification("Bitte geben Sie dem Menü einen Namen");
						notification.setDelayMsec(500);
						notification.show(Page.getCurrent());
						
					}
				}
				else{ 
					Notification notification = new Notification("Bitte geben sie den Menüersteller an");
					notification.setDelayMsec(500);
					notification.show(Page.getCurrent());
					
				}
			}
		});
	
		
		try {
			listfussnote = FussnoteDAO.getInstance().getFussnoteByMenue(menue2.getId());
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		if(listfussnote != null) {
			
			for ( Fussnote fn: listfussnote) {
				
				fussnoten.select(fn.getId());
			}
			
		}
		else {
			
		}

		try {
			System.out.println(MitarbeiterDAO.getInstance().getMitarbeiterByMenue(menue2.getId()).getId());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		menuename.setValue(menue2.getName());
		try {
			ersteller.setValue(MitarbeiterDAO.getInstance().getMitarbeiterByMenue(menue2.getId()).getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			geschmackCb.setValue(GeschmackDAO.getInstance().getGeschmackByMenue(menue2.getId()).getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			hauptgericht.setValue(MenueDAO.getInstance().getHauptgerichtMenue(menue2.getId()).getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			listrezept = MenueDAO.getInstance().getBeilagenByMenue(menue2.getId());
		} catch (Exception e) {
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
		
	}



	@Override
	public void valueChange(ValueChangeEvent event) {
		
		
	}






	



	
}

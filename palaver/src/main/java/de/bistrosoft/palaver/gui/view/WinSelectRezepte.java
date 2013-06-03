package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;

public class WinSelectRezepte extends Window {
	
	// private IndexedContainer container;
			Window subwindow = this;

			// private FilterTable table;
			// private TextField searchField = new TextField();
			VerticalLayout box = new VerticalLayout();
			HorizontalLayout horizont1 = new HorizontalLayout();
			HorizontalLayout horizont2 = new HorizontalLayout();
			HorizontalLayout horizont3 = new HorizontalLayout();
			private FilterTable rezepttable;
			BeanItemContainer<Rezept>rezeptcontainer;
			private Button showFilter;
			private Rezept rezept;
			private TextField rezepttxt = new TextField("Rezept");
			private String rezeptid;
			private String rezeptInput;
			private Button speichern = new Button("Speichern");

			// private Button showFilter;
			

			// BeanItemContainer<Artikel> artikelContainer;

			
			
			
		

			public WinSelectRezepte(final int zahl ) {
				
				rezepttable = new FilterTable();
				rezepttable.setSizeFull();
				rezepttable.setFilterBarVisible(false);
				rezepttable.setFilterGenerator(new customFilter());
				rezepttable.setFilterDecorator(new customFilterDecorator());
				rezepttable.setSelectable(true);
				rezepttable.setWidth("100%");
				rezepttable.setHeight("100%");
				showFilter = new Button(IConstants.BUTTON_SHOW_FILTER);
				speichern.setIcon(new ThemeResource("img/save.ico"));
				initLayout();
				rezepttxt.setImmediate(true);
				rezepttxt.setInputPrompt(rezeptInput);
				System.out.println(zahl);
				if (zahl == 0) {
					System.out.println("HG");
					loadhauptgerichte();
					rezepttxt.setCaption("Hauptgericht");
				}
			
				else {
					System.out.println("b");
					loadbeilagen();
					rezepttxt.setCaption("Beilage");
				}
			
				rezepttable.addValueChangeListener(new ValueChangeListener() {

					@Override
					public void valueChange(ValueChangeEvent event) {
						if (event.getProperty().getValue() != null) {
							rezept = (Rezept) event.getProperty().getValue();
						}

					}
				});

				rezepttable.addItemClickListener(new ItemClickListener() {

					@Override
					public void itemClick(ItemClickEvent event) {
						if (event.isDoubleClick()) {
							rezepttxt.setValue(rezept.getName());
							rezeptid = ((rezept.getId()).toString());
						}

					}
				});
				
	
				rezepttxt.addValueChangeListener(new ValueChangeListener() {

					public void valueChange(final ValueChangeEvent event) {
						final String valueString = String.valueOf(event.getProperty()
								.getValue());
		               
						rezeptInput = valueString;
						
					}
				});

				showFilter.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						if (rezepttable.isFilterBarVisible()) {
							rezepttable.setFilterBarVisible(false);
							rezepttable.resetFilters();
							showFilter.setCaption(IConstants.BUTTON_SHOW_FILTER);
							// showFilter.setIcon(new ThemeResource("img/filter.ico"));
						} else {
							rezepttable.setFilterBarVisible(true);
							showFilter.setCaption(IConstants.BUTTON_HIDE_FILTER);
							// showFilter.setIcon(new
							// ThemeResource("img/disable_filter.ico"));
						}
					}
				});
				
				speichern.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						if (zahl == 0) {
							uebertragehauptgericht();
						}
						else {
							if (zahl == 1) {
								uebertragebeilage1();
							}
							else {
								uebertragebeilage2();
							}
						}
						subwindow.close();
					}
					});

			}
			
			private void loadhauptgerichte() {
				
				BeanItemContainer<Rezept> rezeptcontainer;
				System.out.println("Container erstellt");
				try {
					rezeptcontainer = new BeanItemContainer<Rezept>(Rezept.class,
							Rezeptverwaltung.getInstance().getAllRezepte());
				// noch getallrezepte aendern wenn methoden in rezeptdao fertig
					rezepttable.setContainerDataSource(rezeptcontainer);
					
					rezepttable.setVisibleColumns(new Object[] {"id", "name", "rezeptart",
							 "mitarbeiter", "erstellt" });
				
					rezepttable.sort(new Object[] { "name" }, new boolean[] { true });
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			private void loadbeilagen() {
				BeanItemContainer<Rezept> rezeptcontainer;
				System.out.println("Container erstellt");
				try {
					rezeptcontainer = new BeanItemContainer<Rezept>(Rezept.class,
							Rezeptverwaltung.getInstance().getAllRezepte());
				// noch getallrezepte aendern wenn methoden in rezeptdao fertig
					rezepttable.setContainerDataSource(rezeptcontainer);
					
					rezepttable.setVisibleColumns(new Object[] {"id", "name", "rezeptart",
							 "mitarbeiter", "erstellt" });
				
					rezepttable.sort(new Object[] { "name" }, new boolean[] { true });
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			private void initLayout() {
				final VerticalLayout box = new VerticalLayout();
				box.setSizeFull();
				box.setMargin(true);
				setContent(box);
				box.addComponent(horizont1);
				horizont1.addComponent(rezepttxt);
				horizont1.addComponent(speichern);
				box.addComponent(horizont2);
				box.addComponent(horizont3);
				horizont2.addComponent(showFilter);
				horizont2.setComponentAlignment(showFilter, Alignment.MIDDLE_RIGHT);
				horizont3.addComponent(rezepttable);
				horizont3.setExpandRatio(rezepttable, 1);	
				rezepttable.setSizeFull();

			}
			
			private void uebertragebeilage1() {
				MenueAnlegen.beilage1.setValue(rezeptInput);
				MenueAnlegen.beilageid = rezeptid;
			}
			
            private void uebertragebeilage2() {
            	MenueAnlegen.beilage2.setValue(rezeptInput);
            	MenueAnlegen.beilage2id = rezeptid;
			}
            
            private void uebertragehauptgericht() {
            	MenueAnlegen.hauptgericht.setValue(rezeptInput);
            	MenueAnlegen.hauptgerichtid = rezeptid;
            	//MenueAnlegen.hauptgerichtid = rezeptid;
}
			
}
		

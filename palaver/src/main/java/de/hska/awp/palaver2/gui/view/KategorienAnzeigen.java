package de.hska.awp.palaver2.gui.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;
import de.hska.awp.palaver2.artikelverwaltung.service.Kategorienverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;
/**
 * 
 * @author Mihail Boehm
 *
 */
@SuppressWarnings("serial")
public class KategorienAnzeigen  extends VerticalLayout  implements View{
	
	private static final Logger	log	= LoggerFactory.getLogger(KategorienAnzeigen.class.getName());

	private VerticalLayout layout = new VerticalLayout();	
	private Button hinzufuegen = new Button(IConstants.BUTTON_ADD);
	private Table table;
	
	private TextField			name = new TextField("Name");
	private TextField			nameUp = new TextField("Name");
	private String				nameText;
	
	private Label				headline;
	
	private Kategorie		kategorieUpdate;
	
	public KategorienAnzeigen(){
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		layout.setWidth("40%");
		layout.setMargin(true);
		layout.setSpacing(true);
		table = new Table();
		table.setSizeFull();
		table.setSelectable(true);
		
		headline = new Label("Alle Kategorien");
		headline.setStyleName("ViewHeadline");
		
		layout.addComponent(headline);
		layout.setComponentAlignment(headline, Alignment.MIDDLE_LEFT);
		
		table.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					kategorieUpdate = (Kategorie) event.getProperty()
							.getValue();
				}
			}
		});
		
		table.addItemClickListener(new ItemClickListener() {	
			@Override
			public void itemClick(ItemClickEvent event) {
				if(event.isDoubleClick()){
					final Window mengNeu = new Window();
					mengNeu.setClosable(false);
					mengNeu.setWidth("400px");
					mengNeu.setHeight("270px");
					mengNeu.setModal(true);
					mengNeu.center();
					mengNeu.setResizable(false);
					mengNeu.setCaption("Kategorie hinzuf�gen");
					
					UI.getCurrent().addWindow(mengNeu);
					
					VerticalLayout	layout = new VerticalLayout();
					layout.setMargin(true);
					layout.setWidth("100%");
					layout.setSpacing(true);

					Button			speichern = new Button(IConstants.BUTTON_SAVE);
					Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
					
					nameUp.setWidth("100%");

					VerticalLayout feld = new VerticalLayout();
				
					feld.addComponent(nameUp);

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
					mengNeu.setContent(layout);
					
					nameUp.setImmediate(true);
					nameUp.setValue(kategorieUpdate.getName());
					nameUp.setMaxLength(45);
					nameUp.addValidator(new StringLengthValidator("Bitte g�ltigen Namen eingeben", 4,45, false));
					
					verwerfen.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							UI.getCurrent().removeWindow(mengNeu);
							ViewHandler.getInstance().switchView(KategorienAnzeigen.class);							
						}
					});

					speichern.addClickListener(new ClickListener()
					{
						public void buttonClick(ClickEvent event)
						{
							kategorieUpdate.setName(nameUp.getValue());
							try {
								Kategorienverwaltung.getInstance().updateKategorie(kategorieUpdate);
							} catch (Exception e) {
//								throw new NullPointerException("Bitte g�ltige Werte eingeben");
								log.error(e.toString());
							}
							UI.getCurrent().removeWindow(mengNeu);
							ViewHandler.getInstance().switchView(KategorienAnzeigen.class);
						}
					});


			        nameUp.addValueChangeListener(new ValueChangeListener() {

			            public void valueChange(final ValueChangeEvent event) {
			                final String valueString = String.valueOf(event.getProperty()
			                        .getValue());

			                nameText = valueString;
			            }
			        });
			        
				}
				
			}
		});
		
		
		
		
		
		layout.addComponent(table);
		layout.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
		layout.addComponent(hinzufuegen);
		layout.setComponentAlignment(hinzufuegen, Alignment.MIDDLE_RIGHT);
		
		hinzufuegen.setIcon(new ThemeResource(IConstants.BUTTON_ADD_ICON));
		
		BeanItemContainer<Kategorie> container;
		try
		{
			container = new BeanItemContainer<Kategorie>(Kategorie.class, Kategorienverwaltung.getInstance().getAllKategories());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "name"});
			table.sort(new Object[] {"name"}, new boolean[] {true});
		} 
		catch (Exception e)
		{
			log.error(e.toString());
		}	
		
		this.addComponent(layout);
		this.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
		
		hinzufuegen.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				final Window mengNeu = new Window();
				mengNeu.setClosable(false);
				mengNeu.setWidth("400px");
				mengNeu.setHeight("270px");
				mengNeu.setModal(true);
				mengNeu.center();
				mengNeu.setResizable(false);
				mengNeu.setCaption("Kategorie hinzuf�gen");
				
				UI.getCurrent().addWindow(mengNeu);
				
				VerticalLayout	layout = new VerticalLayout();
				layout.setMargin(true);
				layout.setWidth("100%");
				layout.setSpacing(true);

				Button			speichern = new Button(IConstants.BUTTON_SAVE);
				Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
				
				name.setWidth("100%");

				VerticalLayout feld = new VerticalLayout();
			
				feld.addComponent(name);

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
				mengNeu.setContent(layout);
				
				name.setImmediate(true);
				name.setMaxLength(45);
				name.addValidator(new StringLengthValidator("Bitte g�ltigen Namen eingeben", 4,45, false));
				
				verwerfen.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						UI.getCurrent().removeWindow(mengNeu);
						ViewHandler.getInstance().switchView(KategorienAnzeigen.class);							
					}
				});

				speichern.addClickListener(new ClickListener()
				{
					public void buttonClick(ClickEvent event)
					{
						Kategorie me = new Kategorie();
						me.setName(nameText);
						try {
							Kategorienverwaltung.getInstance().createNewKategorie(me);
						} catch (Exception e) {
//							throw new NullPointerException("Bitte g�ltige Werte eingeben");
							log.error(e.toString());
						}
						UI.getCurrent().removeWindow(mengNeu);
						ViewHandler.getInstance().switchView(KategorienAnzeigen.class);
					}
				});


		        name.addValueChangeListener(new ValueChangeListener() {

		            public void valueChange(final ValueChangeEvent event) {
		                final String valueString = String.valueOf(event.getProperty()
		                        .getValue());

		                nameText = valueString;
		            }
		        });
		        
			}
		});
	}
	
	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}

}

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

import de.hska.awp.palaver2.artikelverwaltung.domain.Mengeneinheit;
import de.hska.awp.palaver2.artikelverwaltung.service.Mengeneinheitverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * @Author PhilippT
 */

@SuppressWarnings("serial")
public class MengeneinheitenAnzeigen extends VerticalLayout  implements View{
	
	private static final Logger	log	= LoggerFactory.getLogger(MengeneinheitenAnzeigen.class.getName());
	
	private VerticalLayout layout = new VerticalLayout();
	
	private Button hinzufuegen = new Button(IConstants.BUTTON_ADD);
	private Table table;
	
	private TextField			name = new TextField("Name");
	private TextField			kurz = new TextField("Kürzel");
	
	private TextField			nameUp = new TextField("Name");
	private TextField			kurzUp = new TextField("Kürzel");
	
	private String				nameText;
	private String				kurzText;
	
	private Label				headline;
	
	private Mengeneinheit		mengeUpdate;
	
	public MengeneinheitenAnzeigen()
	{
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		layout.setWidth("60%");
		layout.setMargin(true);
		layout.setSpacing(true);
		table = new Table();
		table.setSizeFull();
		table.setSelectable(true);
		
		headline = new Label("Alle Mengeneinheiten");
		headline.setStyleName("ViewHeadline");
		
		layout.addComponent(headline);
		layout.setComponentAlignment(headline, Alignment.MIDDLE_LEFT);
		
		table.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(event.getProperty().getValue() != null){
					mengeUpdate = (Mengeneinheit) event.getProperty().getValue();
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
					mengNeu.setCaption("Mengeneinheit hinzufügen");
					
					UI.getCurrent().addWindow(mengNeu);
					
					VerticalLayout	layout = new VerticalLayout();
					layout.setMargin(true);
					layout.setWidth("100%");
					layout.setSpacing(true);

					Button			speichern = new Button(IConstants.BUTTON_SAVE);
					Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
					
					nameUp.setWidth("100%");
					kurzUp.setWidth("100%");

					VerticalLayout feld = new VerticalLayout();
				
					feld.addComponent(nameUp);
					feld.addComponent(kurzUp);

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
					nameUp.setRequired(true);
					nameUp.setValue(mengeUpdate.getName());
					
					kurzUp.setImmediate(true);
					kurzUp.setRequired(true);
					kurzUp.setValue(mengeUpdate.getKurz());
					
					verwerfen.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							UI.getCurrent().removeWindow(mengNeu);
							ViewHandler.getInstance().switchView(MengeneinheitenAnzeigen.class);							
						}
					});

					speichern.addClickListener(new ClickListener()
					{
						public void buttonClick(ClickEvent event)
						{
							mengeUpdate.setName(nameUp.getValue());
							mengeUpdate.setKurz(kurzUp.getValue());
							try {
								Mengeneinheitverwaltung.getInstance().updateMengeneinheit(mengeUpdate);
							} catch (Exception e) {
//								throw new NullPointerException("Bitte g�ltige Werte eingeben");
								log.error(e.toString());
							}
							UI.getCurrent().removeWindow(mengNeu);
							ViewHandler.getInstance().switchView(MengeneinheitenAnzeigen.class);
						}
					});


			        nameUp.addValueChangeListener(new ValueChangeListener() {

			            public void valueChange(final ValueChangeEvent event) {
			                final String valueString = String.valueOf(event.getProperty()
			                        .getValue());

			                nameText = valueString;
			            }
			        });
			        
			        kurzUp.addValueChangeListener(new ValueChangeListener() {
			            @Override
			            public void valueChange(final ValueChangeEvent event) {
			                final String valueString = String.valueOf(event.getProperty()
			                        .getValue());
			                kurzText = valueString;
			            }
			        });				}
				
			}
		});
		
		layout.addComponent(table);
		layout.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
		layout.addComponent(hinzufuegen);
		layout.setComponentAlignment(hinzufuegen, Alignment.MIDDLE_RIGHT);
		
		hinzufuegen.setIcon(new ThemeResource(IConstants.BUTTON_ADD_ICON));
		
		BeanItemContainer<Mengeneinheit> container;
		try
		{
			container = new BeanItemContainer<Mengeneinheit>(Mengeneinheit.class, Mengeneinheitverwaltung.getInstance().getAllMengeneinheit());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "name", "kurz"});
			table.sort(new Object[] {"id"}, new boolean[] {true});
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
				mengNeu.setCaption("Mengeneinheit hinzufügen");
				
				UI.getCurrent().addWindow(mengNeu);
				
				VerticalLayout	layout = new VerticalLayout();
				layout.setMargin(true);
				layout.setWidth("100%");
				layout.setSpacing(true);

				Button			speichern = new Button(IConstants.BUTTON_SAVE);
				Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
				
				name.setWidth("100%");
				kurz.setWidth("100%");
				
				name.setRequired(true);
				kurz.setRequired(true);

				VerticalLayout feld = new VerticalLayout();
			
				feld.addComponent(name);
				feld.addComponent(kurz);

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
				name.setMaxLength(15);
				name.addValidator(new StringLengthValidator("Bitte gültigen Namen eingeben", 4,15, false));
				
				kurz.setImmediate(true);
				kurz.setMaxLength(4);	
				kurz.addValidator(new StringLengthValidator("Bitte gültiges Kürzel eingeben", 1,4, false));
				
				verwerfen.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						UI.getCurrent().removeWindow(mengNeu);
						ViewHandler.getInstance().switchView(MengeneinheitenAnzeigen.class);							
					}
				});

				speichern.addClickListener(new ClickListener()
				{
					public void buttonClick(ClickEvent event)
					{
						Mengeneinheit me = new Mengeneinheit();
						me.setName(nameText);
						me.setKurz(kurzText);
						String notification = "Mengeneinheit gespeichert";
						try {
							Mengeneinheitverwaltung.getInstance().createMengeneinheit(me);
						} catch (Exception e) {
							log.error(e.toString());
							if(e.toString().contains("INSERT INTO mengeneinheit"))
								notification = "diese Name oder dieses Kürzel sind bereits in der System vorhanden.";
							else
								notification = e.toString();
						}
						final Window dialog = new Window();
						dialog.setClosable(false);
						dialog.setWidth("300px");
						dialog.setHeight("150px");
						dialog.setModal(true);
						dialog.center();
						dialog.setResizable(false);
						dialog.setStyleName("dialog-window");
						
						Label message = new Label(notification);
						
						Button okButton = new Button("OK");
						
						VerticalLayout dialogContent = new VerticalLayout();
						dialogContent.setSizeFull();
						dialogContent.setMargin(true);
						dialog.setContent(dialogContent);
						
						dialogContent.addComponent(message);
						dialogContent.addComponent(okButton);
						dialogContent.setComponentAlignment(okButton, Alignment.BOTTOM_RIGHT);
						
						UI.getCurrent().addWindow(dialog);
						
						okButton.addClickListener(new ClickListener()
						{	
							@Override
							public void buttonClick(ClickEvent event)
							{
								UI.getCurrent().removeWindow(dialog);
								ViewHandler.getInstance().switchView(MengeneinheitenAnzeigen.class);
							}
						});
					}
				});


		        name.addValueChangeListener(new ValueChangeListener() {

		            public void valueChange(final ValueChangeEvent event) {
		                final String valueString = String.valueOf(event.getProperty()
		                        .getValue());

		                nameText = valueString;
		            }
		        });
		        
		        kurz.addValueChangeListener(new ValueChangeListener() {
		            @Override
		            public void valueChange(final ValueChangeEvent event) {
		                final String valueString = String.valueOf(event.getProperty()
		                        .getValue());
		                kurzText = valueString;
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

	}

}

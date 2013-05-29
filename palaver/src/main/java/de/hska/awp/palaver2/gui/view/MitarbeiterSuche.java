/**
 * Created by Elena W
 */
package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Rollenverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class MitarbeiterSuche extends VerticalLayout  implements View{
	
	private HorizontalLayout	box = new HorizontalLayout();
	private HorizontalLayout 	knoepfe = new HorizontalLayout();
	private VerticalLayout 		links = new VerticalLayout();
	private VerticalLayout		rechts = new VerticalLayout();
	private VerticalLayout		mitte = new VerticalLayout();
	
	private TextField			name = new TextField("Name");
	private TextField			vorname = new TextField("Vorname");
	private TextField			email = new TextField("E-Mail");
	//private TextField			rolle = new TextField("Rolle");
	private TextField			eintrittsdatum = new TextField("Eintrittsdatum");
	private TextField			austrittsdatum = new TextField("Austrittsdatum");
	private TextField			benutzername = new TextField("Benutzername");
		
	private Button				okButton = new Button("Ok");
	private Button 				rollenAdd = new Button(IConstants.BUTTON_ADD);
	private Button				updateB	= new Button(IConstants.BUTTON_EDIT);
	private Button				speichern = new Button(IConstants.BUTTON_SAVE);
	private Button				verwerfen = new Button(IConstants.BUTTON_DISCARD);
	private Table 				rollen = new Table();
	
	private String				nameInput;
	private Mitarbeiter 	    mitarbeiter = new Mitarbeiter();
	private Rollen		        rollenBean = new Rollen();
	
	private TextField			nameRollen = new TextField("Rollenname");
	
	
	
	public MitarbeiterSuche() throws ConnectException, DAOException, SQLException {
		
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		
		name.setWidth("100%");
		vorname.setWidth("100%");
		email.setWidth("100%");
		eintrittsdatum.setWidth("100%");
		austrittsdatum.setWidth("100%");
		benutzername.setWidth("100%");		

	    rollen.setWidth("100%");
		rollen.setHeight("230px");
		
		
		box.setWidth("900px");
		box.setHeight("90%");
		box.setSpacing(true);
		
		links.setWidth("250px");
		rechts.setWidth("320px");
		links.setSpacing(true);
		rechts.setSpacing(true);
		mitte.setWidth("250px");
		mitte.setSpacing(true);
		box.addComponentAsFirst(links);
		box.addComponent(mitte);
		box.addComponent(rechts);
		
		mitte.addComponent(name);
		mitte.addComponent(vorname);
		mitte.addComponent(benutzername);
		mitte.addComponent(email);
		mitte.addComponent(eintrittsdatum);
		mitte.addComponent(austrittsdatum);
				
		rechts.addComponent(rollenAdd);
		
		rollenAdd.setIcon(new ThemeResource(IConstants.BUTTON_ADD_ICON));
		
		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		
		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));

		rechts.addComponent(rollen);
		knoepfe.addComponent(okButton);
		knoepfe.addComponentAsFirst(updateB);
		updateB.setIcon(new ThemeResource(IConstants.BUTTON_EDIT_ICON));
		mitte.addComponent(knoepfe);
		mitte.setComponentAlignment(knoepfe, Alignment.BOTTOM_RIGHT);
		
        okButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(MitarbeiterAnzeigen.class);					
			}
		});
        
        nameRollen.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                nameInput = valueString;
            }
        });
       
        
        rollen.setSelectable(true);
        
        rollen.addValueChangeListener(new ValueChangeListener()
		{	
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				if (event.getProperty().getValue() != null)
				{
					rollenBean = (Rollen) event.getProperty().getValue();
				}
			}
		});
        
        
        
		updateB.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				name.setEnabled(true);
				vorname.setEnabled(true);
				email.setEnabled(true);
				//Passwort
				eintrittsdatum.setEnabled(true);
				austrittsdatum.setEnabled(true);
				benutzername.setEnabled(true);
				
								
				knoepfe.setVisible(false);
				HorizontalLayout 	knoNeu = new HorizontalLayout();
				
				knoNeu.addComponent(verwerfen);
				knoNeu.addComponent(speichern);
				mitte.addComponent(knoNeu);
				mitte.setComponentAlignment(knoNeu, Alignment.BOTTOM_RIGHT);
				
				verwerfen.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						ViewHandler.getInstance().switchView(MitarbeiterSuche.class, new ViewDataObject<Mitarbeiter>(mitarbeiter));						
					}
				});
				
				speichern.addClickListener(new ClickListener()
				{
					public void buttonClick(ClickEvent event)
					{
						mitarbeiter.setName(name.getValue());
						mitarbeiter.setVorname(vorname.getValue());
						mitarbeiter.setEmail(email.getValue());
						//mitarbeiter.setPasswort(passwort.getValue());
						mitarbeiter.setEintrittsdatum(eintrittsdatum.getValue());
						mitarbeiter.setAustrittsdatum(austrittsdatum.getValue());
						mitarbeiter.setBenutzername(benutzername.getValue());
						
						try {
							Mitarbeiterverwaltung.getInstance().updateMitarbeiter(mitarbeiter);
						} catch (Exception e) {
							throw new NullPointerException("Bitte g�ltige Werte eingeben");
						}
						ViewHandler.getInstance().switchView(MitarbeiterSuche.class, new ViewDataObject<Mitarbeiter>(mitarbeiter));
					}
				});
				
				
			}
		});
		
		rollen.addItemClickListener(new ItemClickListener() {	
			
			@Override
			public void itemClick(ItemClickEvent event) {
				if(event.isDoubleClick()){
					final Window rollen = new Window();
					rollen.setClosable(false);
					rollen.setWidth("400px");
					rollen.setHeight("270px");
					rollen.setModal(true);
					rollen.center();
					rollen.setResizable(false);
					rollen.setCaption("Rolle bearbeiten");
					
					
					UI.getCurrent().addWindow(rollen);
					
					VerticalLayout	layout = new VerticalLayout();
					layout.setMargin(true);
					layout.setWidth("100%");
					layout.setSpacing(true);

					Button			loeschen = new Button(IConstants.BUTTON_DELETE);
					Button			speichern = new Button(IConstants.BUTTON_SAVE);
					Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
					
					nameRollen.setWidth("100%");
					
					
					VerticalLayout feld = new VerticalLayout();
					
					nameRollen.setValue(rollenBean.getName());
										
					feld.addComponent(nameRollen);
					

					HorizontalLayout control = new HorizontalLayout();
					control.setSpacing(true);
					control.addComponent(loeschen);
					control.addComponent(verwerfen);
					control.addComponent(speichern);
					
					speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
					verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
					loeschen.setIcon(new ThemeResource(IConstants.ICON_DELETE));
					
					layout.addComponent(feld);
					layout.setComponentAlignment(feld, Alignment.MIDDLE_CENTER);
					layout.addComponent(control);
					layout.setComponentAlignment(control, Alignment.BOTTOM_RIGHT);
					rollen.setContent(layout);
					
					nameRollen.setImmediate(true);
					nameRollen.setInputPrompt(nameInput);
					nameRollen.setMaxLength(30);
					
										
					verwerfen.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							UI.getCurrent().removeWindow(rollen);							
						}
					});
					
					loeschen.addClickListener(new ClickListener(){
						
						@Override
						public void buttonClick(ClickEvent event) {
							
							rollen.setStyleName("dialog-window");
							rollen.setClosable(false);
							rollen.setWidth("320px");
							rollen.setHeight("135px");
							rollen.setModal(true);
							rollen.center();
							rollen.setResizable(false);
							rollen.setCaption("");
							
							HorizontalLayout layout = new HorizontalLayout();
							layout.setMargin(true);
//							layout.setWidth("300px");
							layout.setSizeFull();
							layout.setSpacing(true);
							
//							VerticalLayout verticallayout = new VerticalLayout();
////							verticallayout.setWidth("280px");
//														
//							Label label = new Label("Wollen Sie die Rolle wirklich löschen?");
//							verticallayout.addComponent(label);
//							verticallayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
//							
//							HorizontalLayout horizontallayout = new HorizontalLayout();
//							horizontallayout.setMargin(true);
////							horizontallayout.setWidth("280px");
//							horizontallayout.setSpacing(true);
//							
//							Button ja = new Button(IConstants.BUTTON_JA);
//							Button nein = new Button(IConstants.BUTTON_NEIN);
//							
//							ja.setIcon(new ThemeResource(IConstants.ICON_YES));
//							nein.setIcon(new ThemeResource(IConstants.ICON_NO));
//							
//							horizontallayout.addComponent(ja);
//							horizontallayout.addComponent(nein);
//							
//							verticallayout.addComponent(horizontallayout);
//							verticallayout.setComponentAlignment(horizontallayout, Alignment.MIDDLE_CENTER);
//							
//							layout.addComponent(verticallayout);
//							layout.setComponentAlignment(verticallayout, Alignment.MIDDLE_CENTER);
//							
//							rollen.setContent(layout);							
//							
//							ja.addClickListener(new ClickListener()
//							{
//								public void buttonClick(ClickEvent event){
//									try {
//									Rollenverwaltung.getInstance().deleteRollen(rollenBean.getId());
//									} catch (ConnectException | DAOException | SQLException e) {
//									System.out.println(e);
//									}
//							
//								UI.getCurrent().removeWindow(rollen);
//								ViewHandler.getInstance().switchView(MitarbeiterSuche.class, new ViewDataObject<Mitarbeiter>(mitarbeiter));
//								}
//							});
//							
//							nein.addClickListener(new ClickListener()
//							{
//								public void buttonClick(ClickEvent event){
//									
//								UI.getCurrent().removeWindow(rollen);
//								ViewHandler.getInstance().switchView(MitarbeiterSuche.class, new ViewDataObject<Mitarbeiter>(mitarbeiter));
//								}
//							});
						}						
					});
					
					speichern.addClickListener(new ClickListener()
					{
						public void buttonClick(ClickEvent event)
						{							
							rollenBean.setName(nameInput);
							
							try {
								Rollenverwaltung.getInstance().updateRollen(rollenBean);
							} catch (Exception e) {
							System.out.println(e);
								throw new NullPointerException("Bitte g�ltige Werte eingeben");

							}				
							
							UI.getCurrent().removeWindow(rollen);
							ViewHandler.getInstance().switchView(MitarbeiterSuche.class, new ViewDataObject<Mitarbeiter>(mitarbeiter));				
							}
					});
					
					

			        nameRollen.addValueChangeListener(new ValueChangeListener() {

			            public void valueChange(final ValueChangeEvent event) {
			                final String valueString = String.valueOf(event.getProperty()
			                        .getValue());

			                nameInput = valueString;
			            }
			        });
				}
			
				
				
			}
		});
			        
			       		       		
				
		rollenAdd.addClickListener(new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			final Window rollen = new Window();
			rollen.setClosable(false);
			rollen.setWidth("400px");
			rollen.setHeight("270px");
			rollen.setModal(true);
			rollen.center();
			rollen.setResizable(false);
			rollen.setCaption("Rolle hinzuf�gen");
			
			UI.getCurrent().addWindow(rollen);
			
			VerticalLayout	layout = new VerticalLayout();
			layout.setMargin(true);
			layout.setWidth("100%");
			layout.setSpacing(true);

			Button			speichern = new Button(IConstants.BUTTON_SAVE);
			Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
			
			nameRollen.setWidth("100%");
						
			VerticalLayout feld = new VerticalLayout();
		
			feld.addComponent(nameRollen);
			
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
			rollen.setContent(layout);
			
			nameRollen.setImmediate(true);
			nameRollen.setInputPrompt(nameInput);
			nameRollen.setMaxLength(30);
			
			verwerfen.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					UI.getCurrent().removeWindow(rollen);							
				}
			});
			  
			speichern.addClickListener(new ClickListener()
			{
				public void buttonClick(ClickEvent event)
				{
					Rollen rollen = new Rollen();
					rollen.setName(nameInput);
					try {
						Rollenverwaltung.getInstance().createRollen(rollen);
					} catch (Exception e) {
						System.out.println(e);
						throw new NullPointerException("Bitte g�ltige Werte eingeben");

					}				
					
//					UI.getCurrent().removeWindow(rollen);
					ViewHandler.getInstance().switchView(MitarbeiterSuche.class, new ViewDataObject<Mitarbeiter>(mitarbeiter));				
					}
			});
			
			

	        nameRollen.addValueChangeListener(new ValueChangeListener() {

	            public void valueChange(final ValueChangeEvent event) {
	                final String valueString = String.valueOf(event.getProperty()
	                        .getValue());

	                nameInput = valueString;
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
		mitarbeiter = (Mitarbeiter) ((ViewDataObject<?>)data).getData();
		if(mitarbeiter.getId() == null) {
		//	try {
			//mitarbeiter = Mitarbeiterverwaltung.getInstance().getMitarbeiterById();
			//} catch (ConnectException | DAOException | SQLException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
		}
		name.setValue(mitarbeiter.getName());
		name.setEnabled(false);
			
		vorname.setValue(mitarbeiter.getVorname());
		vorname.setEnabled(false);
			
		email.setValue(mitarbeiter.getEmail());
		email.setEnabled(false);
			
		eintrittsdatum.setValue(mitarbeiter.getEintrittsdatum());
		eintrittsdatum.setEnabled(false);
			
		austrittsdatum.setValue(mitarbeiter.getAustrittsdatum());
		austrittsdatum.setEnabled(false);
		
		benutzername.setValue(mitarbeiter.getBenutzername());
		benutzername.setEnabled(false);
		
		BeanItemContainer<Rollen> container;

		try {
			container = new BeanItemContainer<Rollen>(Rollen.class, Rollenverwaltung.getInstance().getRollenByMitarbeiterId(mitarbeiter.getId()));
			rollen.setContainerDataSource(container);
			rollen.setVisibleColumns(new Object[] {"name"});
			rollen.sort(new Object[] {"id"}, new boolean[] {true});
			rollen.setColumnCollapsingAllowed(true);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
		
	

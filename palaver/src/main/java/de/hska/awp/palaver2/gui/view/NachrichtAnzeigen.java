package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Rollenverwaltung;
import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;
import de.hska.awp.palaver2.nachrichtenverwaltung.service.Nachrichtenverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class NachrichtAnzeigen extends VerticalLayout  implements View {

	private HorizontalLayout horizontallayout = new HorizontalLayout();
	private VerticalLayout nachrichtanzeigenlayout = new VerticalLayout();
	private VerticalLayout nachrichterstellenlayout = new VerticalLayout();
	
	private VerticalLayout nachrichtverticallayout = new VerticalLayout();	
	HorizontalLayout nachrichterstellenlayoutbuttons = new HorizontalLayout();
	
	private Label von;
	private TextArea nachrichtentext;
	private TextArea neuernachrichtentext;
	
	private List<Nachricht> nl = new ArrayList<Nachricht>();
	private Nachricht nachricht = new Nachricht();
	
	private String neuernachrichtentextinput;
	
	
	public NachrichtAnzeigen() throws ConnectException, DAOException, SQLException {
		
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		this.addComponent(horizontallayout);
		
//		horizontallayout.setWidth("900px");
		horizontallayout.setSizeFull();
		horizontallayout.setHeight("90%");
		horizontallayout.setSpacing(true);

		nachrichtanzeigenlayout.setWidth("300px");
		nachrichtanzeigenlayout.setSpacing(true);
		nachrichterstellenlayout.setWidth("300px");
		nachrichterstellenlayout.setSpacing(true);

		
		Panel panel = new Panel("Nachrichten");
		panel.setWidth("450px");
		panel.setHeight("100%");

	    final VerticalLayout contentLayout = new VerticalLayout();
	    contentLayout.setWidth(400, Unit.PIXELS);
	    contentLayout.setMargin(true);
	    	      
	    horizontallayout.addComponent(panel);
//	    horizontallayout.setComponentAlignment(panel, Alignment.MIDDLE_LEFT);
	    horizontallayout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
	        
		horizontallayout.addComponent(nachrichterstellenlayout);
//		horizontallayout.setComponentAlignment(nachrichterstellenlayout, Alignment.MIDDLE_RIGHT);
		horizontallayout.setComponentAlignment(nachrichterstellenlayout, Alignment.MIDDLE_CENTER);
		
		
		
		//Nachrichtlayout zusammenbauen
		//TODO Auskommentierten Text anzeigen, wenn getUser() funktioniert
		try{
			
//		Mitarbeiter m = Application.getInstance().getUser();
		Mitarbeiter m = Mitarbeiterverwaltung.getInstance().getMitarbeiterById(Long.valueOf("1"));
		List<Rollen> rlist = m.getRollen();
		if(rlist!=null){
			for(int i = 0; i < rlist.size() ; i++){
				if(rlist.get(i).getNachrichten()!=null){
					for(int z = 0; z < rlist.get(i).getNachrichten().size();z++){
						nl.add(rlist.get(i).getNachrichten().get(z));
					}
				}
			}
			
		}
//			Nachrichtenverwaltung.getInstance().getAllNachricht();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(nl!=null){
			for(int i = 0; i < nl.size(); i++){
								
				von = new Label("Von:");
				von.setWidth("100%");
				von.setValue("Von: " + nl.get(i).getMitarbeiterBySenderFk().getName());
				
				
				final Button loeschbutton = new Button();
				loeschbutton.setIcon(new ThemeResource(IConstants.ICON_DELETE));
				String id = String.valueOf(nl.get(i).getId());
				
				System.out.print("Id des Löschbutton beim setzen");
				System.out.print("           ");
				System.out.print(id);
				System.out.print("           ");
				loeschbutton.setId(id);
				
				nachrichtentext = new TextArea("");
				nachrichtentext.setWidth("100%");
				nachrichtentext.setRows(4);
				nachrichtentext.setValue(nl.get(i).getNachricht());
				nachrichtentext.setReadOnly(true);
				
				nachrichtverticallayout = new VerticalLayout();
				nachrichtverticallayout.setStyleName("nachricht");
				nachrichtverticallayout.addComponent(von);			
				nachrichtverticallayout.addComponent(nachrichtentext);
				nachrichtverticallayout.addComponent(loeschbutton);
				nachrichtverticallayout.setComponentAlignment(loeschbutton, Alignment.TOP_RIGHT);
				
				contentLayout.addComponent(nachrichtverticallayout);
				panel.setContent(contentLayout);
				
				loeschbutton.addClickListener(new ClickListener()
				{
					public void buttonClick(ClickEvent event){
						try {
							System.out.print("Id des Löschbutton beim löschen");
							System.out.print(Long.valueOf(loeschbutton.getId()));
							System.out.print("    ");
						Nachrichtenverwaltung.getInstance().deleteNachricht(Long.valueOf(loeschbutton.getId()));
						} catch (Exception e) {
						System.out.println(e);
						}
						
					
					ViewHandler.getInstance().switchView(NachrichtAnzeigen.class);
					}
				});
			}
		}else {
			//TODO falls keine Nachrichten vorhanden sind
		}
	
		// RECHTE SEITE

		Label label = new Label("Neue Nachricht");
		label.setValue("Neue Nachricht");

		nachrichterstellenlayout.addComponent(label);

		final ComboBox combobox = new ComboBox();

		combobox.setWidth("60%");
		combobox.setImmediate(true);
		combobox.setNullSelectionAllowed(false);
		combobox.setRequired(true);

		List<Rollen> rollen = Rollenverwaltung.getInstance().getAllRollen();
		for (Rollen i : rollen) {
			combobox.addItem(i);
		}

		nachrichterstellenlayout.addComponent(combobox);

		neuernachrichtentext = new TextArea();
		neuernachrichtentext.setWidth("100%");
		neuernachrichtentext.setRows(4);
		neuernachrichtentext.setImmediate(true);
		neuernachrichtentext.setInputPrompt(neuernachrichtentextinput);

		nachrichterstellenlayout.addComponent(neuernachrichtentext);

		neuernachrichtentext.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				neuernachrichtentextinput = String.valueOf(event.getProperty()
						.getValue());
			}
		});

		Button speichern = new Button(IConstants.BUTTON_SAVE);
		Button verwerfen = new Button(IConstants.BUTTON_DISCARD);

		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
		
		nachrichterstellenlayoutbuttons.addComponent(verwerfen);
		nachrichterstellenlayoutbuttons.addComponent(speichern);

		nachrichterstellenlayout.addComponent(nachrichterstellenlayoutbuttons);

		speichern.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				nachricht.setNachricht(neuernachrichtentext.getValue());

				nachricht.setEmpfaengerRolle((Rollen) combobox.getValue());

				// TODO Sollte später funktionieren, andernfalls Mitarbeiter über username suchen und setzen
//				nachricht.setMitarbeiterBySenderFk(Application.getInstance().getUser());

				try {
					//TODO
					nachricht.setMitarbeiterBySenderFk(Mitarbeiterverwaltung.getInstance().getMitarbeiterById(Long.valueOf("1")));
					Nachrichtenverwaltung.getInstance().createNachricht(
							nachricht);
				} catch (Exception e) {
					throw new NullPointerException(
							"Bitte gültige Werte eingeben");
				}
				ViewHandler.getInstance().switchView(NachrichtAnzeigen.class);
			}
		});

		verwerfen.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(NachrichtAnzeigen.class);
			}
		});

	}

	@Override
	public void getViewParam(ViewData data) {
	}
}



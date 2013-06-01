package de.hska.awp.palaver2.gui.view;

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
public class NachrichtAnzeigen extends VerticalLayout  implements View, ValueChangeListener {

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
	
	private int NACHRICHT_MAXLENGTH = 300;
	
	private ComboBox combobox = new ComboBox();
	
	Button speichern = new Button(IConstants.BUTTON_SAVE);
	Button verwerfen = new Button(IConstants.BUTTON_DISCARD);
	
	public NachrichtAnzeigen() {
		
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		this.addComponent(horizontallayout);
		
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
	    horizontallayout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
	        
		horizontallayout.addComponent(nachrichterstellenlayout);
		horizontallayout.setComponentAlignment(nachrichterstellenlayout, Alignment.MIDDLE_LEFT);
		
		
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

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(nl!=null){
			
			//Sortieren der Nachrichten nach der größten ID
			final List<Nachricht> neu = new ArrayList<Nachricht>();
			if (nl != null) {
				for (int z = 0; z < nl.size(); z++) {
					long zahl = 0;
					int ind = 0;
					for (int i = 0; i < nl.size(); i++) {
						if (zahl < nl.get(i).getId()) {
							zahl = nl.get(i).getId();
							ind = i;
						}
					}

					neu.add(nl.get(ind));
					nl.remove(ind);
					z = z - 1;
				}
			}

			nl = neu;
			
			for(int i = 0; i < nl.size(); i++){
								
				von = new Label("Von:");
				von.setWidth("100%");
				von.setValue("Von: " + nl.get(i).getMitarbeiterBySenderFk().getName());
							
				final Button loeschbutton = new Button();
				loeschbutton.setIcon(new ThemeResource(IConstants.ICON_DELETE));
				
				loeschbutton.setId(String.valueOf(nl.get(i).getId()));
				
				nachrichtentext = new TextArea("");
				nachrichtentext.setWidth("100%");
				nachrichtentext.setRows(4);
				nachrichtentext.setValue(nl.get(i).getNachricht());
				nachrichtentext.setReadOnly(true);
				nachrichtentext.setImmediate(true);
				
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

		combobox.setWidth("60%");
		combobox.setImmediate(true);
		combobox.setNullSelectionAllowed(false);
		combobox.setRequired(true);
		try {
			List<Rollen> rollen = Rollenverwaltung.getInstance().getAllRollen();
			for (Rollen i : rollen) {
				combobox.addItem(i);
			}
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		

		nachrichterstellenlayout.addComponent(combobox);

		neuernachrichtentext = new TextArea();
		neuernachrichtentext.setWidth("100%");
		neuernachrichtentext.setRows(4);
		neuernachrichtentext.setImmediate(true);
		neuernachrichtentext.setMaxLength(NACHRICHT_MAXLENGTH);
		neuernachrichtentext.setRequired(true);

		nachrichterstellenlayout.addComponent(neuernachrichtentext);

		neuernachrichtentext.addValueChangeListener(this);
		nachrichtentext.addValueChangeListener(this);

		speichern.setEnabled(false);
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

	@Override
	public void valueChange(ValueChangeEvent event) {
		if (combobox.isValid() == false || neuernachrichtentext.isValid() == false) 
		{
			speichern.setEnabled(false);
		}
		else 
		{
			speichern.setEnabled(true);
		}
		
	}
}



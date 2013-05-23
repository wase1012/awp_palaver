package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Ansprechpartnerverwaltung;
import de.hska.awp.palaver2.nachrichtenverwaltung.domain.Nachricht;
import de.hska.awp.palaver2.nachrichtenverwaltung.service.Nachrichtenverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class NachrichtAnzeigen extends VerticalLayout  implements View {

	private HorizontalLayout horizontallayout = new HorizontalLayout();
	private VerticalLayout nachrichtanzeigenlayout = new VerticalLayout();
	private VerticalLayout nachrichterstellenlayout = new VerticalLayout();
	
	private VerticalLayout nachrichtverticallayout = new VerticalLayout();
	private HorizontalLayout nachrichthorizontallayout = new HorizontalLayout();
	
	private Label von;
	private Button löschbutton;
	private TextArea nachrichtentext;
	
	private List<Nachricht> nl = new ArrayList<Nachricht>();
	
	public NachrichtAnzeigen() throws ConnectException, DAOException, SQLException {
		
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		this.addComponent(horizontallayout);
		
		horizontallayout.setWidth("900px");
		horizontallayout.setHeight("90%");
		horizontallayout.setSpacing(true);

		nachrichtanzeigenlayout.setWidth("300px");
		nachrichtanzeigenlayout.setSpacing(true);
		nachrichterstellenlayout.setWidth("300px");
		nachrichterstellenlayout.setSpacing(true);

		
		Panel panel = new Panel("Nachrichten");
		panel.setHeight("100%");

	    final VerticalLayout contentLayout = new VerticalLayout();
	    contentLayout.setWidth(400, Unit.PIXELS);
	    contentLayout.setMargin(true);
	    	      
	    horizontallayout.addComponent(panel);
	    horizontallayout.setComponentAlignment(panel, Alignment.MIDDLE_LEFT);
	        
		horizontallayout.addComponent(nachrichterstellenlayout);
		horizontallayout.setComponentAlignment(nachrichterstellenlayout, Alignment.MIDDLE_RIGHT);
		
		
		
		//Nachrichtlayout zusammenbauen
		try{
		nl = Nachrichtenverwaltung.getInstance().getAllNachricht();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(nl!=null){
			for(int i = 0; i < nl.size(); i++){
								
				von = new Label("Von:");
				von.setWidth("100%");
				//TODO
				von.setValue("Von: " + nl.get(i).getMitarbeiterBySenderFk().getName());
				
				
				löschbutton = new Button();
//				Image papierkorb = new Image();
//				papierkorb.setSource(new ThemeResource(IConstants.ICON_DELETE));
				löschbutton.setIcon(new ThemeResource(IConstants.ICON_DELETE));
				String id = String.valueOf(nl.get(i).getId());
				
				System.out.print("Id des Löschbutton beim setzen");
				System.out.print("           ");
				System.out.print(nl.get(i).getId());
				System.out.print("           ");
				löschbutton.setId(id);
				
				nachrichtentext = new TextArea("");
				nachrichtentext.setWidth("100%");
				nachrichtentext.setRows(4);
				nachrichtentext.setValue(nl.get(i).getNachricht());
				nachrichtentext.setReadOnly(true);
				
				nachrichtverticallayout = new VerticalLayout();
				nachrichtverticallayout.setStyleName("nachricht");
				nachrichtverticallayout.addComponent(von);			
				nachrichtverticallayout.addComponent(nachrichtentext);
				nachrichtverticallayout.addComponent(löschbutton);
				nachrichtverticallayout.setComponentAlignment(löschbutton, Alignment.TOP_RIGHT);
				
				contentLayout.addComponent(nachrichtverticallayout);
				panel.setContent(contentLayout);
				
				löschbutton.addClickListener(new ClickListener()
				{
					public void buttonClick(ClickEvent event){
						try {
							System.out.print("Id des Löschbutton beim löschen");
							System.out.print(Long.valueOf(löschbutton.getId()));
							System.out.print("    ");
						Nachrichtenverwaltung.getInstance().deleteNachricht(Long.valueOf(löschbutton.getId()));
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
	}

	
	@Override
	public void getViewParam(ViewData data)
	{
	}

}

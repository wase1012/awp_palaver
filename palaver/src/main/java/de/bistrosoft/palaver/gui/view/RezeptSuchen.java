
 
package de.bistrosoft.palaver.gui.view;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;


public class RezeptSuchen extends VerticalLayout{
	
	private HorizontalLayout oben = new HorizontalLayout();
	private HorizontalLayout mitte = new HorizontalLayout();
	private HorizontalLayout unten = new HorizontalLayout();
	
	private VerticalLayout	raidio = new VerticalLayout();
	
	private VerticalLayout	eins = new VerticalLayout();
	private VerticalLayout	zwei = new VerticalLayout();
	private VerticalLayout	drei = new VerticalLayout();
	private VerticalLayout vier = new VerticalLayout();
	
	private VerticalLayout	eregbnis = new VerticalLayout();
	
	private Label ueberschrift = new Label("<pre><b><font size='4' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Rezept Suchen</font><b></pre>", ContentMode.HTML);
    private OptionGroup auswahl = new OptionGroup("Suchkriterium");
    
    
    private Label name = new Label("<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Nach Name suchen       </font><b></pre>", ContentMode.HTML);
    private Label ersteller = new Label("<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Nach Rezeptersteller suchen       </font><b></pre>", ContentMode.HTML);
    private Label geschmack = new Label("<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Nach Geschmack suchen       </font><b></pre>", ContentMode.HTML);
    private Label fussnote = new Label("<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Nach Fussnote suchen       </font><b></pre>", ContentMode.HTML);
 
	
	
	public RezeptSuchen()
	{
		super();
		this.setSizeFull();
	    this.setMargin(true);
	    
	    this.addComponent(oben);
		this.setComponentAlignment(oben, Alignment.MIDDLE_CENTER);
		
		oben.addComponent(raidio);
		oben.setComponentAlignment(raidio, Alignment.MIDDLE_CENTER);
		raidio.addComponent(ueberschrift);
		
		raidio.addComponent(auswahl);
		auswahl.addItem(1);
		auswahl.setItemCaption(1, "Name");
		auswahl.addItem(2);
		auswahl.setItemCaption(2, "Rezeptersteller");
		auswahl.addItem(3);
		auswahl.setItemCaption(3, "Geschmack");
		auswahl.addItem(4);
		auswahl.setItemCaption(4, "Fussnote");
		
		
		
		//--------------------------------------------------------------------
		
		 this.addComponent(mitte);
		this.setComponentAlignment(mitte, Alignment.MIDDLE_CENTER);
		mitte.addComponent(eins);
		mitte.addComponent(zwei);
		mitte.addComponent(drei);
		mitte.addComponent(vier);
		
		eins.addComponent(name);
		zwei.addComponent(ersteller);
		drei.addComponent(geschmack);
		vier.addComponent(fussnote);

	}

}

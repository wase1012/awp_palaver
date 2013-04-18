/**
 * 
 * Jan Lauinger
 * 18.04.2013 - 21:21:58
 *
 */
package de.bistrosoft.palaver.gui.view;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Jan Lauinger
 *
 */
public class RezeptAnlegen extends VerticalLayout{
	
	private VerticalLayout	box = new VerticalLayout();
	
	private TextField bezeichnung = new TextField("Bezeichnung");
	private TextField kommentar = new TextField("Kommentar");
	private TextField protion = new TextField("Prtion");
	
	private CheckBox geschmack = new CheckBox("Geschmack");
	private CheckBox art = new CheckBox("Art");
	private CheckBox zubereitung = new CheckBox("Zubereitung");
	
	
	
	

}

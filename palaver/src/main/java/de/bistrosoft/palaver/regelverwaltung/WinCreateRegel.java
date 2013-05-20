package de.bistrosoft.palaver.regelverwaltung;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.bistrosoft.palaver.regelverwaltung.domain.Regel;

@SuppressWarnings("serial")
public class WinCreateRegel extends Window {
	
	Window subwindow = this; 
	
	String regeltypinput;
	String operatorinput;
	String kriterieninput;
	String fehlermeldunginput;
	final ComboBox regeltyp = new ComboBox("Regeltyp");
	final ComboBox operator = new ComboBox("Operator");
	final ComboBox kriterien = new ComboBox("Kriterien");
	final TextField fehlermeldung = new TextField("Fehlermeldung");
	final CheckBox aktiv = new CheckBox("Aktiv");
	final Button speichern = new Button("Speichern");
	
	HorizontalLayout box = new HorizontalLayout();
	VerticalLayout links = new VerticalLayout();
	VerticalLayout mitte = new VerticalLayout();
	VerticalLayout rechts = new VerticalLayout();
	
	public WinCreateRegel() {
		
		layout();
		inputs();
		speichern();
		
		regeltyp.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				regeltypinput = valueString;
			}
		});
		
	}
	
	private void layout() {
		setContent(box);
		
		box.setSizeFull();
		box.setSpacing(true);
		box.addComponents(links, mitte, rechts);
		mitte.setSpacing(true);
		box.setComponentAlignment(mitte, Alignment.MIDDLE_CENTER);
	
		mitte.addComponents(regeltyp, operator, kriterien, fehlermeldung, aktiv, speichern);

	}
	
	
	private void speichern() {
		speichern.addClickListener(new ClickListener() {
	    	
			// Click-Listener zum Speichern
			@Override
			public void buttonClick(ClickEvent event) {
				// Regel speichern
				Regel.speichern(regeltypinput, "'d'",
						"'df'", operatorinput, kriterieninput,
						fehlermeldunginput, true);
				Notification notification = new Notification("Regel wurde gespeichert");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
				
				subwindow.close();
			}
		});
	}
	
	private void inputs() {
		
		regeltyp.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				regeltypinput = "'" + valueString + "'" ;
			}
		});
		
		operator.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				operatorinput = "'" + valueString + "'" ;
			}
		});
		
		kriterien.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				regeltypinput = "'" + valueString + "'";
			}
		});
		
		fehlermeldung.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				fehlermeldunginput = "'" + valueString + "'" ;
			}
		});
	}

}

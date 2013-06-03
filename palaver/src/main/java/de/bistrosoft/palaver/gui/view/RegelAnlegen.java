package de.bistrosoft.palaver.gui.view;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.bistrosoft.palaver.menueplanverwaltung.domain.Menueart;
import de.bistrosoft.palaver.menueplanverwaltung.service.Menueartverwaltung;
import de.bistrosoft.palaver.regelverwaltung.domain.Regel;
import de.bistrosoft.palaver.regelverwaltung.service.Regelverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Zubereitung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Zubereitungverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class RegelAnlegen extends VerticalLayout implements View, ValueChangeListener {
	// Variablen und Komponenten
		HorizontalLayout box = new HorizontalLayout();
		private VerticalLayout		fenster = new VerticalLayout();

		private ComboBox regeltyp = new ComboBox("Regeltyp");
		private ComboBox operator = new ComboBox("Operator");
		private TwinColSelect spalten = new TwinColSelect("Spalten");
		private TwinColSelect zeilen = new TwinColSelect("Zeilen");
		private TwinColSelect kriterien = new TwinColSelect("Kriterien");
		private TextArea fehlermeldung = new TextArea("Fehlermeldung");
		private CheckBox aktiv = new CheckBox("Aktiv");
		private Button speichern = new Button(IConstants.BUTTON_SAVE);
		private Button bearbeiten = new Button(IConstants.BUTTON_SAVE);
		private Label label;
		private Regel regel;
		
		String zeileninput;
		String spalteninput;
		String regeltypinput;
		String operatorinput;
		String kriterieninput;
		String fehlermeldunginput;
		
		BeanItemContainer<Zubereitung> zubereitungContainer;
		BeanItemContainer<Fussnote> fußnoteContainer;
		BeanItemContainer<Geschmack> geschmackContainer;
		BeanItemContainer<Menueart> menueartContainer;
		
		List<String> operatorinhalt = Arrays.asList("enthält", "enthält nicht", "maximal");
		List<String> regeltypinhalt = Arrays.asList("Zubereitung", "Fußnote", "Geschmack", "Menüart");
		List<String> zeileninhalt = Arrays.asList("Fleischgericht", "Hauptgericht", "Pastagericht", "Suppe/Salat", "Dessert");
		List<String> spalteninhalt = Arrays.asList("Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag");
		BeanItemContainer<String> operatorcontainer = new BeanItemContainer<String>(String.class, operatorinhalt);
		BeanItemContainer<String> regeltypcontainer = new BeanItemContainer<String>(String.class, regeltypinhalt);
		
		// Konstruktur der für das bearbeiten einer Regel verwendet wird
		public RegelAnlegen(Regel regel) {
			label = new Label("Regel bearbeiten");
			layout();
			container();
			ChangeListener();
			inhalt(regel);
			ChangeListener();
			speichern();
			
		}
		
		public RegelAnlegen() {
			label = new Label("Regel erstellen");
			layout();
			aktiv.setValue(true);
			ChangeListener();
			speichern();
			container();
		}
		
		private void layout() {
			this.setSizeFull();
			this.setMargin(true);
			
			label = new Label("Neue Regel");
			label.setStyleName("ViewHeadline");
			
			zeilen.setWidth("100%");
			spalten.setWidth("100%");
			regeltyp.setWidth("100%");
			operator.setWidth("100%");
			kriterien.setWidth("100%");
			fehlermeldung.setWidth("100%");
			
			fenster.setWidth("1000px");
			fenster.setSpacing(true);
			
			VerticalLayout links = new VerticalLayout();
			links.setWidth("300px");
			links.setSpacing(true);
			
			VerticalLayout mitte = new VerticalLayout();
			mitte.setWidth("300px");
			mitte.setSpacing(true);
			
			VerticalLayout rechts = new VerticalLayout();
			rechts.setWidth("300px");
			rechts.setSpacing(true);
			
			box.setWidth("1000px");
			box.setSpacing(true);
			box.addComponent(links);
			box.addComponent(mitte);
			
			
			links.addComponent(zeilen);
			links.addComponent(spalten);
			mitte.addComponent(regeltyp);
			mitte.addComponent(operator);
			mitte.addComponent(kriterien);
			rechts.addComponent(fehlermeldung);
			rechts.addComponent(aktiv);
			
			box.addComponent(rechts);
			
			HorizontalLayout control = new HorizontalLayout();
			control.setWidth("100%");
			control.setSpacing(true);

			control.addComponent(speichern);
			
			rechts.addComponent(control);
			rechts.setComponentAlignment(control, Alignment.TOP_RIGHT);
			
			speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
			speichern.setEnabled(false);
			
			fenster.addComponent(label);
			fenster.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
			fenster.addComponent(box);
			fenster.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
			
			this.addComponent(fenster);
			this.setComponentAlignment(fenster, Alignment.MIDDLE_CENTER);

			for (String z : zeileninhalt) {
				zeilen.addItem(z);
			}
			for (String z : spalteninhalt) {
				spalten.addItem(z);
			}
			regeltyp.setWidth("100%");
			regeltyp.setImmediate(true);
			regeltyp.setRequired(true);
			
			operator.setWidth("100%");
			operator.setImmediate(true);
			operator.setRequired(true);
			
			kriterien.setWidth("100%");
			kriterien.setImmediate(true);
			kriterien.setRequired(true);
			
			fehlermeldung.setWidth("100%");
			fehlermeldung.setImmediate(true);
			fehlermeldung.setRequired(true);

		}
		
		private void speichern() {
			speichern.addClickListener(new ClickListener() {
		    	
				// Click-Listener zum Speichern
				@Override
				public void buttonClick(ClickEvent event) {
					// Regel speichern
					Regel.speichern(regeltypinput, zeileninput,
							spalteninput, operatorinput, kriterieninput,
							fehlermeldunginput, true);
					Notification notification = new Notification("Regel wurde gespeichert");
					notification.setDelayMsec(500);
					notification.show(Page.getCurrent());
					ViewHandler.getInstance().switchView(RegelnAnzeigen.class);
				}
			});
		}
		
		private void container() {
			operator.setContainerDataSource(operatorcontainer);
			regeltyp.setContainerDataSource(regeltypcontainer);
			
		}
		
		private void inhalt(Regel regel) {
			String typ = regel.getKriterien();
			System.out.println(typ);
//			regeltyp.setValue(regel.getRegeltyp());
			regeltyp.setInputPrompt(regel.getRegeltyp());
//			regeltyp.select(regel.getRegeltyp());
			
					
			try {
				zubereitungContainer = new BeanItemContainer<Zubereitung>(Zubereitung.class, Zubereitungverwaltung.getInstance().getAllZubereitung());
				fußnoteContainer = new BeanItemContainer<Fussnote>(Fussnote.class, Fussnotenverwaltung.getInstance().getAllFussnote());
				menueartContainer = new BeanItemContainer<Menueart>(Menueart.class, Menueartverwaltung.getInstance().getAllMenueart());
				geschmackContainer = new BeanItemContainer<Geschmack>(Geschmack.class, Geschmackverwaltung.getInstance().getAllGeschmackAktiv());
				
				// Versuch container für bearbeiten zu setzen
				List<Zubereitung> zube = Zubereitungverwaltung.getInstance().getAllZubereitung();
				for(Zubereitung z: zube) {
					System.out.println(regel.getKriterien() + z.getName());
					if(regel.getKriterien() == z.getName()) {
						System.out.println("JA");
						kriterien.setContainerDataSource(zubereitungContainer); 					
					}
				}
				
				if(regeltyp.getValue() == "Zubereitung") {
					kriterien.setContainerDataSource(zubereitungContainer); 
				}
				if(regel.getRegeltyp() == "Fußnote"){
					kriterien.setContainerDataSource(fußnoteContainer);
				}
				if(regel.getRegeltyp() == "Geschmack") {
					kriterien.setContainerDataSource(geschmackContainer);
				}
				if(regel.getRegeltyp() == "Menüart") {
					kriterien.setContainerDataSource(menueartContainer);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			operator.setValue(regel.getOperator());
			fehlermeldung.setValue(regel.getFehlermeldung());

		}
		
		private void ChangeListener() {

			zeilen.addValueChangeListener(new ValueChangeListener() {
				
				@Override
				public void valueChange(ValueChangeEvent event) {
					String valueString = String.valueOf(event.getProperty()
							.getValue());
					valueString = valueString.replaceAll("\\[|\\]", "");
					zeileninput = valueString;
				}
			});
			
			spalten.addValueChangeListener(new ValueChangeListener() {
				
				@Override
				public void valueChange(ValueChangeEvent event) {
					String valueString = String.valueOf(event.getProperty()
							.getValue());
					valueString = valueString.replaceAll("\\[|\\]", "");
					spalteninput = valueString;
				}
			});
			
			regeltyp.addValueChangeListener(new ValueChangeListener() {

				public void valueChange(final ValueChangeEvent event) {
					final String valueString = String.valueOf(event.getProperty()
							.getValue());

						try {
							zubereitungContainer = new BeanItemContainer<Zubereitung>(Zubereitung.class, Zubereitungverwaltung.getInstance().getAllZubereitung());
							fußnoteContainer = new BeanItemContainer<Fussnote>(Fussnote.class, Fussnotenverwaltung.getInstance().getAllFussnote());
							menueartContainer = new BeanItemContainer<Menueart>(Menueart.class, Menueartverwaltung.getInstance().getAllMenueart());
							geschmackContainer = new BeanItemContainer<Geschmack>(Geschmack.class, Geschmackverwaltung.getInstance().getAllGeschmackAktiv());
							
							if(valueString == "Zubereitung") {
								kriterien.setContainerDataSource(zubereitungContainer); 
							}
							if(valueString == "Fußnote"){
								kriterien.setContainerDataSource(fußnoteContainer);
							}
							if(valueString == "Geschmack") {
								kriterien.setContainerDataSource(geschmackContainer);
							}
							if(valueString == "Menüart") {
								kriterien.setContainerDataSource(menueartContainer);
							}
						} catch (Exception e) {
							e.printStackTrace();
						} 
									
					regeltypinput = valueString;
					
					check();
				}
				
			});
			
			operator.addValueChangeListener(new ValueChangeListener() {

				public void valueChange(final ValueChangeEvent event) {
					final String valueString = String.valueOf(event.getProperty()
							.getValue());

					operatorinput = valueString;
					check();
				}
			});
			
			kriterien.addValueChangeListener(new ValueChangeListener() {

				public void valueChange(final ValueChangeEvent event) {
					String valueString = String.valueOf(event.getProperty()
							.getValue());
					valueString = valueString.replaceAll("\\[|\\]", "");
					kriterieninput = valueString;
					check();
				}
			});
			
			fehlermeldung.addValueChangeListener(new ValueChangeListener() {

				public void valueChange(final ValueChangeEvent event) {
					final String valueString = String.valueOf(event.getProperty()
							.getValue());

					fehlermeldunginput = valueString;
					check();
				}
			});

		}
		
		public void check() {
			if(regeltyp.getValue() == "" || regeltyp.getValue() == null || kriterien.getValue() == "" ||
					 kriterien.getValue() == null || operator.getValue() == null ||
					 operator.getValue() == "" || fehlermeldung.getValue() == null || fehlermeldung.getValue() == "" ) 
			{
				speichern.setEnabled(false);
			}
			else 
			{
				speichern.setEnabled(true);
			}
			
		}
		
		public void getViewParam(ViewData data) {
			regel = (Regel) ((ViewDataObject<?>)data).getData();
			
			try {
				zubereitungContainer = new BeanItemContainer<Zubereitung>(Zubereitung.class, Zubereitungverwaltung.getInstance().getAllZubereitung());
				fußnoteContainer = new BeanItemContainer<Fussnote>(Fussnote.class, Fussnotenverwaltung.getInstance().getAllFussnote());
				menueartContainer = new BeanItemContainer<Menueart>(Menueart.class, Menueartverwaltung.getInstance().getAllMenueart());
				geschmackContainer = new BeanItemContainer<Geschmack>(Geschmack.class, Geschmackverwaltung.getInstance().getAllGeschmackAktiv());
				
				label.setValue("Regel bearbeiten");
				regeltyp.setValue(regel.getRegeltyp());
				operator.setValue(regel.getOperator());
				fehlermeldung.setValue(regel.getFehlermeldung());
				aktiv.setValue(regel.getAktiv());
				
				List<String> sp = Arrays.asList(regel.getSpalte().split("\\s*,\\s*"));
				for (String z : sp) {
					spalten.select(z);
				}
				List<String> zl = Arrays.asList(regel.getZeile().split("\\s*,\\s*"));
				for (String z : zl) {
					zeilen.select(z);
				}

				zeilen.select(regel.getZeile());
				
				System.out.println(regeltyp.getValue() );
				if(regeltyp.getValue() == "Zubereitung") {
					kriterien.setContainerDataSource(zubereitungContainer); 
				}
				if(regeltyp.getValue() == "Fußnote"){
					kriterien.setContainerDataSource(fußnoteContainer);
				}
				if(regeltyp.getValue() == "Geschmack") {
					kriterien.setContainerDataSource(geschmackContainer);
				}
				if(regeltyp.getValue() == "Menüart") {
					kriterien.setContainerDataSource(menueartContainer);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
						
			bearbeiten.addClickListener(new ClickListener()
			{	
		
				@Override
				public void buttonClick(ClickEvent event)
				{
					regel.setRegeltyp(regeltyp.getValue().toString());
					regel.setOperator(operator.getValue().toString());
					regel.setKriterien(kriterien.getValue().toString());
					regel.setFehlermeldung(fehlermeldung.getValue());
					regel.setAktiv(aktiv.getValue());
					
					Notification notification = new Notification("Regel wurde gespeichert");
					notification.setDelayMsec(500);
					notification.show(Page.getCurrent());		

					try
					{
						Regelverwaltung.getInstance().updateRegel(regel);
					} 
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			});
		}

		@Override
		public void valueChange(ValueChangeEvent event) {
			
		}
}

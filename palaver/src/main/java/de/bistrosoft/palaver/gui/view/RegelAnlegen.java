package de.bistrosoft.palaver.gui.view;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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
import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class RegelAnlegen extends VerticalLayout implements View,
		ValueChangeListener {
	// Variablen und Komponenten
	HorizontalLayout box = new HorizontalLayout();
	private VerticalLayout fenster = new VerticalLayout();
	HorizontalLayout control = new HorizontalLayout();
	VerticalLayout links = new VerticalLayout();
	VerticalLayout mitte = new VerticalLayout();
	VerticalLayout rechts = new VerticalLayout();

	private NativeSelect regeltyp = new NativeSelect("Regeltyp");
	private NativeSelect operator = new NativeSelect("Operator");
	private TwinColSelect spalten = new TwinColSelect("Spalten");
	private TwinColSelect zeilen = new TwinColSelect("Zeilen");
	private TwinColSelect kriterienTwin = new TwinColSelect("Kriterien");
	private TextField kriterienText = new TextField("Kriterium");
	private TextArea fehlermeldung = new TextArea("Fehlermeldung");
	private CheckBox aktiv = new CheckBox("Aktiv");
	// private CheckBox harteRegel = new CheckBox("Ignorierbar");
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
	BeanItemContainer<Fussnote> fussnoteContainer;
	BeanItemContainer<Geschmack> geschmackContainer;
	BeanItemContainer<Menueart> menueartContainer;

	List<String> operatorinhalt = Arrays.asList(
			IConstants.INFO_REGEL_OPERATOR_ERLAUBT,
			IConstants.INFO_REGEL_OPERATOR_VERBOTEN,
			IConstants.INFO_REGEL_OPERATOR_MAXIMAL,
			IConstants.INFO_REGEL_OPERATOR_MINIMAL);
	List<String> regeltypinhalt = Arrays.asList(
			IConstants.INFO_REGEL_REGELTYP_ZUBEREITUNG,
			IConstants.INFO_REGEL_REGELTYP_FUSSNOTE,
			IConstants.INFO_REGEL_REGELTYP_GESCHMACK,
			IConstants.INFO_REGEL_REGELTYP_MENUEART,
			IConstants.INFO_REGEL_REGELTYP_AUFWAND,
			IConstants.INFO_REGEL_REGELTYP_MENUE);
	List<String> zeileninhalt = Arrays.asList("Fleischgericht", "Hauptgericht",
			"Pastagericht", "Suppe/Salat", "Dessert");
	List<String> spalteninhalt = Arrays.asList("Montag", "Dienstag",
			"Mittwoch", "Donnerstag", "Freitag");
	
	BeanItemContainer<String> operatorcontainer = new BeanItemContainer<String>(
			String.class, operatorinhalt);
	BeanItemContainer<String> regeltypcontainer = new BeanItemContainer<String>(
			String.class, regeltypinhalt);

	// Konstruktur der für das bearbeiten einer Regel verwendet wird
	public RegelAnlegen(Regel regel) {
		label = new Label("Regel bearbeiten");
		layout();
		ChangeListener();
		speichern();

	}
	
	// Konstruktor der für das Anlegen einer neuen Regel verwendet wird
	public RegelAnlegen() {
		label = new Label("Regel erstellen");
		layout();
		aktiv.setValue(true);
		ChangeListener();
		speichern();

	}
	
	// Methode um das Layout beim Regel anlegen und bearbeiten festzulegen
	private void layout() {
		this.setSizeFull();
		this.setMargin(true);

		label = new Label("Neue Regel");
		label.setStyleName("ViewHeadline");

		fenster.setWidth("1000px");
		fenster.setSpacing(true);

		links.setWidth("300px");
		links.setSpacing(true);

		mitte.setWidth("300px");
		mitte.setSpacing(true);

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
		rechts.addComponent(fehlermeldung);
		// rechts.addComponent(harteRegel);
		rechts.addComponent(aktiv);

		box.addComponent(rechts);

		control.setWidth("100%");
		control.setSpacing(true);

		control.addComponent(speichern);

		rechts.addComponent(control);
		rechts.setComponentAlignment(control, Alignment.TOP_RIGHT);

		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));

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

		zeilen.setWidth("100%");
		zeilen.setImmediate(true);
		zeilen.setRequired(true);

		spalten.setWidth("100%");
		spalten.setImmediate(true);
		spalten.setRequired(true);

		regeltyp.setWidth("100%");
		regeltyp.setImmediate(true);
		regeltyp.addValidator(new StringLengthValidator(
				IConstants.INFO_REGEL_REGELTYP, 3, 45, false));
		regeltyp.setContainerDataSource(regeltypcontainer);

		operator.setWidth("100%");
		operator.setImmediate(true);
		operator.addValidator(new StringLengthValidator(
				IConstants.INFO_REGEL_OPERATOR, 3, 45, false));
		operator.setContainerDataSource(operatorcontainer);

		kriterienTwin.setWidth("100%");
		kriterienTwin.setImmediate(true);
		kriterienTwin.setRequired(true);

		kriterienText.setWidth("100%");
		kriterienText.setImmediate(true);
		kriterienText.setRequired(true);

		fehlermeldung.setWidth("100%");
		fehlermeldung.setImmediate(true);
		fehlermeldung.setRequired(true);

	}

	// Clicklistener für das Speichern einer neuen Regel
	private void speichern() {
		speichern.addClickListener(new ClickListener() {

			// Click-Listener zum Speichern
			@Override
			public void buttonClick(ClickEvent event) {
				// Regel speichern

				if (validiereEingabe()) {
					Regel.speichern(regeltypinput, zeileninput, spalteninput,
							operatorinput, kriterieninput, fehlermeldunginput,
							true);
					
					((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_REGEL_SAVE);

					ViewHandler.getInstance().switchView(RegelnAnzeigen.class);
				}
			}
		});
	}

	// ChangeListener für die Felder 
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

				regeltypinput = valueString;
				
				// Falls Regeltyp Aufwand oder Menü ausgewählt wird, wird ein Textfeld eingefügt und der Container für das Feld Operator gesetzt
				if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_AUFWAND
						|| regeltypinput == IConstants.INFO_REGEL_REGELTYP_MENUE) {
					mitte.removeComponent(kriterienTwin);
					mitte.addComponent(kriterienText);
					List<String> inhalt = Arrays
							.asList(IConstants.INFO_REGEL_OPERATOR_MAXIMAL);
					BeanItemContainer<String> container = new BeanItemContainer<String>(
							String.class, inhalt);
					operator.setContainerDataSource(container);
				
				// Falls Regeltyp Zubereitung, Menüart, Geschmack oder Fußnote ist wird der Container für das Feld Operator gesetzt	
				} else if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_ZUBEREITUNG
						|| regeltypinput == IConstants.INFO_REGEL_REGELTYP_MENUEART
						|| regeltypinput == IConstants.INFO_REGEL_REGELTYP_GESCHMACK
						|| regeltypinput == IConstants.INFO_REGEL_REGELTYP_FUSSNOTE) {

					operator.setContainerDataSource(operatorcontainer);
					
					// Falls bei Operator "maximale Anzahl" oder "minimale Anzahl" ausgewählt wurde wird ein Textfeld eingefügt
					if (operatorinput == IConstants.INFO_REGEL_OPERATOR_MAXIMAL
							|| operatorinput == IConstants.INFO_REGEL_OPERATOR_MINIMAL) {

						mitte.removeComponent(kriterienTwin);
						mitte.addComponent(kriterienText);
						
					// Andernfalls wird ein TwinCol eingefügt	
					} else {

						operator.setContainerDataSource(operatorcontainer);
						mitte.removeComponent(kriterienText);
						mitte.addComponent(kriterienTwin);
						check();
					}
				}
			}

		});

		operator.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				operatorinput = valueString;
				
				// Falls beim Feld Operator "maximale Anzahl" oder "minimale Anzahl" ausgewählt wird, wird ein Textfeld eingefügt
				if (operatorinput == IConstants.INFO_REGEL_OPERATOR_MAXIMAL
						|| operatorinput == IConstants.INFO_REGEL_OPERATOR_MINIMAL) {
					mitte.removeComponent(kriterienTwin);
					mitte.addComponent(kriterienText);
					check();
				
				// Falls beim Regeltyp Aufwand oder Menü ausgewählt wird, so wird ein Textfeld eingefügt 
				} else if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_AUFWAND
						|| regeltypinput == IConstants.INFO_REGEL_REGELTYP_MENUE) {
					mitte.removeComponent(kriterienTwin);
					mitte.addComponent(kriterienText);
					
				// Andernfalls wird ein TwinCol eingefügt
				} else {
					mitte.removeComponent(kriterienText);
					mitte.addComponent(kriterienTwin);
					check();
				}
			}
		});

		kriterienTwin.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				String valueString = String.valueOf(event.getProperty()
						.getValue());
				valueString = valueString.replaceAll("\\[|\\]", "");
				kriterieninput = valueString;
			}
		});

		kriterienText.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				String valueString = String.valueOf(event.getProperty()
						.getValue());
				// valueString = valueString.replaceAll("\\[|\\]", "");
				kriterieninput = valueString;
			}
		});

		fehlermeldung.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				fehlermeldunginput = valueString;
			}
		});

	}

	// Diese Methode dient dazu, die Container je nach ausgewähltem Regeltyp zu setzen
	public void check() {
		try {
			
			// Container
			zubereitungContainer = new BeanItemContainer<Zubereitung>(
					Zubereitung.class, Zubereitungverwaltung.getInstance()
							.getAllZubereitung());
			fussnoteContainer = new BeanItemContainer<Fussnote>(Fussnote.class,
					Fussnotenverwaltung.getInstance().getAllFussnote());
			menueartContainer = new BeanItemContainer<Menueart>(Menueart.class,
					Menueartverwaltung.getInstance().getAllMenueart());
			geschmackContainer = new BeanItemContainer<Geschmack>(
					Geschmack.class, Geschmackverwaltung.getInstance()
							.getAllGeschmackAktiv());

			//Container setzen
			if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_ZUBEREITUNG) {
				kriterienTwin.setContainerDataSource(zubereitungContainer);
			}
			if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_FUSSNOTE) {
				kriterienTwin.setContainerDataSource(fussnoteContainer);
			}
			if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_GESCHMACK) {
				kriterienTwin.setContainerDataSource(geschmackContainer);
			}
			if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_MENUEART) {
				kriterienTwin.setContainerDataSource(menueartContainer);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Diese Methode wird aufgerufen, sobald man eine Regel per Doppelklick auswählt
	public void getViewParam(ViewData data) {
		// Angeklickte Regel 
		regel = (Regel) ((ViewDataObject<?>) data).getData();

		// Speichern Button mit Bearbeiten Button ersetzen
		control.replaceComponent(speichern, bearbeiten);
		
		// Werte setzen
		label.setValue("Regel bearbeiten");
		regeltyp.setValue(regel.getRegeltyp());
		operator.setValue(regel.getOperator());
		fehlermeldung.setValue(regel.getFehlermeldung());
		aktiv.setValue(regel.getAktiv());

		List<String> sp = Arrays.asList(regel.getSpalte().split("\\s*,\\s*"));
		for (String s : sp) {
			spalten.select(s);
		}
		List<String> zl = Arrays.asList(regel.getZeile().split("\\s*,\\s*"));
		for (String z : zl) {
			zeilen.select(z);
		}
		try {
			zubereitungContainer = new BeanItemContainer<Zubereitung>(
					Zubereitung.class, Zubereitungverwaltung.getInstance()
							.getAllZubereitung());
			fussnoteContainer = new BeanItemContainer<Fussnote>(Fussnote.class,
					Fussnotenverwaltung.getInstance().getAllFussnote());
			menueartContainer = new BeanItemContainer<Menueart>(Menueart.class,
					Menueartverwaltung.getInstance().getAllMenueart());
			geschmackContainer = new BeanItemContainer<Geschmack>(
					Geschmack.class, Geschmackverwaltung.getInstance()
							.getAllGeschmackAktiv());

			// List<String> kr =
			// Arrays.asList(regel.getKriterien().split("\\s*,\\s*"));
			// System.out.println(kr);
			if (regel.getRegeltyp().equals(
					IConstants.INFO_REGEL_REGELTYP_ZUBEREITUNG)
					&& regel.getOperator().equals(
							IConstants.INFO_REGEL_OPERATOR_VERBOTEN)
					|| regel.getRegeltyp().equals(
							IConstants.INFO_REGEL_REGELTYP_ZUBEREITUNG)
					&& regel.getOperator().equals(
							IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
				System.out.println("1");
				kriterienTwin.setContainerDataSource(zubereitungContainer);
				mitte.removeComponent(kriterienText);
				mitte.addComponent(kriterienTwin);
				// List<String> kr =
				// Arrays.asList(regel.getKriterien().split("\\s*,\\s*"));
				// for(String k : kr) {
				// kriterienTwin.select(k);
				// }
			}
			if (regel.getRegeltyp().equals(
					IConstants.INFO_REGEL_REGELTYP_FUSSNOTE)
					&& regel.getOperator().equals(
							IConstants.INFO_REGEL_OPERATOR_VERBOTEN)
					|| regel.getRegeltyp().equals(
							IConstants.INFO_REGEL_REGELTYP_FUSSNOTE)
					&& regel.getOperator().equals(
							IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
				System.out.println("2");
				// mitte.removeComponent(kriterienText);
				mitte.addComponent(kriterienTwin);
				kriterienTwin.setContainerDataSource(fussnoteContainer);
				// List<String> kr =
				// Arrays.asList(regel.getKriterien().split("\\s*,\\s*"));
				// for(String k : kr) {
				// System.out.println(k);
				// kriterienTwin.select(k);
				// }
			}
			if (regel.getRegeltyp().equals(
					IConstants.INFO_REGEL_REGELTYP_GESCHMACK)
					&& regel.getOperator().equals(
							IConstants.INFO_REGEL_OPERATOR_VERBOTEN)
					|| regel.getRegeltyp().equals(
							IConstants.INFO_REGEL_REGELTYP_GESCHMACK)
					&& regel.getOperator().equals(
							IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
				System.out.println("3");
				mitte.removeComponent(kriterienText);
				mitte.addComponent(kriterienTwin);
				kriterienTwin.setContainerDataSource(geschmackContainer);

				// List<String> kr =
				// Arrays.asList(regel.getKriterien().split("\\s*,\\s*"));
				// for(String k : kr) {
				// System.out.println(k);
				// kriterienTwin.select(k);
				// }
			}
			if (regel.getRegeltyp().equals(
					IConstants.INFO_REGEL_REGELTYP_MENUEART)
					&& regel.getOperator().equals(
							IConstants.INFO_REGEL_OPERATOR_VERBOTEN)
					|| regel.getRegeltyp().equals(
							IConstants.INFO_REGEL_REGELTYP_MENUEART)
					&& regel.getOperator().equals(
							IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
				System.out.println("4");
				kriterienTwin.setContainerDataSource(menueartContainer);
				mitte.removeComponent(kriterienText);
				mitte.addComponent(kriterienTwin);
				// List<String> kr =
				// Arrays.asList(regel.getKriterien().split("\\s*,\\s*"));
				// for(String k : kr) {
				// kriterienTwin.select(k);
				// }
			}
			if (regel.getRegeltyp().equals(
					IConstants.INFO_REGEL_REGELTYP_AUFWAND)
					|| regel.getRegeltyp().equals(
							IConstants.INFO_REGEL_REGELTYP_MENUE)) {
				System.out.println("5");
				mitte.removeComponent(kriterienTwin);
				mitte.addComponent(kriterienText);
				// kriterienText.setValue(regel.getKriterien());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (regel.getRegeltyp().equals(IConstants.INFO_REGEL_REGELTYP_AUFWAND)
				|| regel.getRegeltyp().equals(
						IConstants.INFO_REGEL_REGELTYP_MENUE)) {
			kriterienText.setValue(regel.getKriterien());
		} else {
			List<String> kr = Arrays.asList(regel.getKriterien().split(
					"\\s*,\\s*"));
			for (String k : kr) {
				System.out.println(k);
				kriterienTwin.select(k);
			}
		}

		bearbeiten.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					regel.setZeile(zeilen.getValue().toString());
					regel.setSpalte(spalten.getValue().toString());
					regel.setRegeltyp(regeltyp.getValue().toString());
					regel.setOperator(operator.getValue().toString());
					if (operator.getValue().toString()
							.equals(IConstants.INFO_REGEL_OPERATOR_VERBOTEN)
							|| operator
									.getValue()
									.toString()
									.equals(IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
						regel.setKriterien(kriterienTwin.getValue().toString());
					} else {
						regel.setKriterien(kriterienText.getValue());
					}
					regel.setFehlermeldung(fehlermeldung.getValue());
					regel.setAktiv(aktiv.getValue());

					Notification notification = new Notification(
							IConstants.INFO_REGEL_SAVE);
					notification.setDelayMsec(500);
					notification.show(Page.getCurrent());

					try {
						Regelverwaltung.getInstance().updateRegel(regel);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				ViewHandler.getInstance().switchView(RegelnAnzeigen.class);
			}
		});
	}

	private Boolean validiereEingabe() {
		if (regeltyp.getValue() == null) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_REGEL_REGELTYP);
			return false;
		}
		if (operator.getValue() == null) {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_REGEL_OPERATOR);
			return false;
		}
		return true;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {

	}
}

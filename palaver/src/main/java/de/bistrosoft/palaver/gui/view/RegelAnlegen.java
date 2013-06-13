package de.bistrosoft.palaver.gui.view;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.IntegerValidator;
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
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
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
import de.bistrosoft.palaver.util.TwinColTouch;
import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings({ "serial", "deprecation" })
public class RegelAnlegen extends VerticalLayout implements View,
		ValueChangeListener {
	// Variablen und Komponenten
	HorizontalLayout box = new HorizontalLayout();
	HorizontalLayout box2 = new HorizontalLayout();
	VerticalLayout links2 = new VerticalLayout();
	VerticalLayout mitte2 = new VerticalLayout();
	VerticalLayout rechts2 = new VerticalLayout();
	private VerticalLayout fenster = new VerticalLayout();
	HorizontalLayout control = new HorizontalLayout();
	VerticalLayout links = new VerticalLayout();
	VerticalLayout mitte = new VerticalLayout();
	VerticalLayout rechts = new VerticalLayout();

	private NativeSelect regeltyp = new NativeSelect("Regeltyp");
	private NativeSelect operator = new NativeSelect("Operator");
	private OptionGroup spalten = new OptionGroup("Spalten");
	private OptionGroup zeilen = new OptionGroup("Zeilen");
	private TwinColTouch kriterienTwin = new TwinColTouch("Kriterien");
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
	List<String> zeileninhalt = Arrays.asList(IConstants.INFO_REGEL_ZEILE_1,
			IConstants.INFO_REGEL_ZEILE_2, IConstants.INFO_REGEL_ZEILE_3,
			IConstants.INFO_REGEL_ZEILE_4, IConstants.INFO_REGEL_ZEILE_5);
	List<String> spalteninhalt = Arrays.asList(IConstants.INFO_REGEL_SPALTE_1,
			IConstants.INFO_REGEL_SPALTE_2, IConstants.INFO_REGEL_SPALTE_3,
			IConstants.INFO_REGEL_SPALTE_4, IConstants.INFO_REGEL_SPALTE_5);

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

		fenster.setWidth("900px");

		links.setWidth("100px");
		links.setSpacing(true);

		mitte.setWidth("300px");
		mitte.setSpacing(true);

		rechts.setWidth("300px");
		rechts.setSpacing(true);

		box.setWidth("900px");
		box.addComponent(links);
		box.setComponentAlignment(links, Alignment.TOP_CENTER);
		box.addComponent(mitte);
		box.setComponentAlignment(mitte, Alignment.TOP_LEFT);

		box2.setWidth("900px");
		links2.setWidth("100px");
		mitte2.setWidth("300px");
		rechts2.setWidth("300px");
		box2.addComponent(links2);
		box2.addComponent(mitte2);
		box2.addComponent(rechts2);
		
		links.addComponent(zeilen);
		links.addComponent(spalten);
		mitte.addComponent(regeltyp);
		mitte.addComponent(operator);
		rechts.addComponent(fehlermeldung);
		// rechts.addComponent(harteRegel);
		rechts.addComponent(aktiv);

		box.addComponent(rechts);
		box.setComponentAlignment(rechts, Alignment.TOP_LEFT);

		control.setWidth("100%");
		control.setSpacing(true);

		control.addComponent(speichern);
		control.setComponentAlignment(speichern, Alignment.BOTTOM_RIGHT);
		
		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));

		links2.addComponent(label);
		box2.setComponentAlignment(links2, Alignment.TOP_CENTER);
		fenster.addComponent(box2);
		fenster.addComponent(box);
		fenster.setComponentAlignment(box, Alignment.TOP_CENTER);
		fenster.addComponent(control);

		this.addComponent(fenster);
		this.setComponentAlignment(fenster, Alignment.MIDDLE_CENTER);

		for (String z : zeileninhalt) {
			zeilen.addItem(z);
		}
		for (String z : spalteninhalt) {
			spalten.addItem(z);
		}

		zeilen.setImmediate(true);
		zeilen.setMultiSelect(true);
		zeilen.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (zeilen.getValue().toString() == "[]") {
					throw new InvalidValueException(
							IConstants.INFO_REGEL_ZEILEN);
				}

			}
		});

		spalten.setImmediate(true);
		spalten.setMultiSelect(true);
		spalten.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (spalten.getValue().toString() == "[]") {
					throw new InvalidValueException(
							IConstants.INFO_REGEL_SPALTEN);
				}

			}
		});

		regeltyp.setWidth("95%");
		regeltyp.setImmediate(true);
		regeltyp.addValidator(new StringLengthValidator(
				IConstants.INFO_REGEL_REGELTYP, 3, 500, false));
		regeltyp.setContainerDataSource(regeltypcontainer);

		operator.setWidth("95%");
		operator.setImmediate(true);
		operator.addValidator(new StringLengthValidator(
				IConstants.INFO_REGEL_OPERATOR, 3, 500, false));
		operator.setContainerDataSource(operatorcontainer);

		kriterienTwin.setWidth("95%");
		kriterienTwin.setImmediate(true);
		kriterienTwin.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (kriterienTwin.getValue().toString() == "[]") {
					throw new InvalidValueException(
							IConstants.INFO_REGEL_KRITERIEN_TWIN);
				}

			}
		});

		kriterienText.setWidth("100%");
		kriterienText.setImmediate(true);
		kriterienText.addValidator(new IntegerValidator(
				IConstants.INFO_REGEL_KRITERIEN_ZAHL));

		fehlermeldung.setWidth("100%");
		fehlermeldung.setImmediate(true);
		fehlermeldung.addValidator(new StringLengthValidator(
				IConstants.INFO_REGEL_FEHLERMELDUNG, 3, 500, false));

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

					((Application) UI.getCurrent().getData())
							.showDialog(IConstants.INFO_REGEL_SAVE);

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

				// Falls Regeltyp Aufwand oder Menü ausgewählt wird, wird ein
				// Textfeld eingefügt und der Container für das Feld Operator
				// gesetzt
				if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_AUFWAND
						|| regeltypinput == IConstants.INFO_REGEL_REGELTYP_MENUE) {
					mitte.removeComponent(kriterienTwin);
					mitte.addComponent(kriterienText);
					List<String> inhalt = Arrays
							.asList(IConstants.INFO_REGEL_OPERATOR_MAXIMAL);
					BeanItemContainer<String> container = new BeanItemContainer<String>(
							String.class, inhalt);
					operator.setContainerDataSource(container);

					// Falls Regeltyp Zubereitung, Menüart, Geschmack oder
					// Fußnote ist wird der Container für das Feld Operator
					// gesetzt
				} else if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_ZUBEREITUNG
						|| regeltypinput == IConstants.INFO_REGEL_REGELTYP_MENUEART
						|| regeltypinput == IConstants.INFO_REGEL_REGELTYP_GESCHMACK
						|| regeltypinput == IConstants.INFO_REGEL_REGELTYP_FUSSNOTE) {

					operator.setContainerDataSource(operatorcontainer);

					// Falls bei Operator "maximale Anzahl" oder
					// "minimale Anzahl" ausgewählt wurde wird ein Textfeld
					// eingefügt
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

				// Falls beim Feld Operator "maximale Anzahl" oder
				// "minimale Anzahl" ausgewählt wird, wird ein Textfeld
				// eingefügt
				if (operatorinput == IConstants.INFO_REGEL_OPERATOR_MAXIMAL
						|| operatorinput == IConstants.INFO_REGEL_OPERATOR_MINIMAL) {
					mitte.removeComponent(kriterienTwin);
					mitte.addComponent(kriterienText);
					check();

					// Falls beim Regeltyp Aufwand oder Menü ausgewählt wird, so
					// wird ein Textfeld eingefügt
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

	// Diese Methode dient dazu, die Container je nach ausgewähltem Regeltyp zu
	// setzen
	public void check() {
		try {

			// Container
			List<Zubereitung> zb = Zubereitungverwaltung.getInstance()
					.getAllZubereitung();
			List<Fussnote> fn = Fussnotenverwaltung.getInstance().getAllFussnote();
			List<Menueart> ma = Menueartverwaltung.getInstance().getAllMenueart();
			List<Geschmack> g = Geschmackverwaltung.getInstance().getAllGeschmackAktiv();

			// Container setzen
			if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_ZUBEREITUNG) {
				for (Zubereitung z : zb) {
					kriterienTwin.addItem(z);
				}
			}
			if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_FUSSNOTE) {
				for (Fussnote f : fn) {
					kriterienTwin.addItem(f);
				}
			}
			if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_GESCHMACK) {
				for (Geschmack ge : g) {
					kriterienTwin.addItem(ge);
				}
			}
			if (regeltypinput == IConstants.INFO_REGEL_REGELTYP_MENUEART) {
				for (Menueart m : ma) {
					kriterienTwin.addItem(m);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Diese Methode wird aufgerufen, sobald man eine Regel per Doppelklick
	// auswählt
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
			List<Zubereitung> zb = Zubereitungverwaltung.getInstance()
					.getAllZubereitung();
			List<Fussnote> fn = Fussnotenverwaltung.getInstance().getAllFussnote();
			List<Menueart> ma = Menueartverwaltung.getInstance().getAllMenueart();
			List<Geschmack> g = Geschmackverwaltung.getInstance().getAllGeschmackAktiv();

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
				for (Zubereitung z : zb) {
					kriterienTwin.addItem(z);
				}
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
				for (Fussnote f : fn) {
					kriterienTwin.addItem(f);
				}
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
				for (Geschmack ge : g) {
					kriterienTwin.addItem(ge);
				}

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
				for (Menueart m : ma) {
					kriterienTwin.addItem(m);
				}
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
		if (zeilen.getValue().toString() == "[]") {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_REGEL_ZEILEN);
			return false;
		}
		if (spalten.getValue().toString() == "[]") {
			((Application) UI.getCurrent().getData())
					.showDialog(IConstants.INFO_REGEL_SPALTEN);
			return false;
		}
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
		if (mitte.getComponent(2) == kriterienText) {
			if (kriterienText.getValue().isEmpty()) {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_REGEL_KRITERIEN_ZAHL);
				return false;
			}
		}
		if (mitte.getComponent(2) == kriterienTwin) {
			if (kriterienTwin.getValue().toString() == "[]") {
				((Application) UI.getCurrent().getData())
						.showDialog(IConstants.INFO_REGEL_KRITERIEN_TWIN);
				return false;
			}
		}
		return true;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {

	}
}

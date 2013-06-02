/**
 * Created by Elena W
 */
package de.hska.awp.palaver2.gui.view;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Mitarbeiter;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Mitarbeiterverwaltung;
import de.hska.awp.palaver2.mitarbeiterverwaltung.service.Rollenverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.Util;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class MitarbeiterBearbeiten extends VerticalLayout implements View {
	private HorizontalLayout box = new HorizontalLayout();
	private VerticalLayout fenster = new VerticalLayout();

	private Label headline;

	private TextField name = new TextField("Name");
	private TextField vorname = new TextField("Vorname");
	private TextField email = new TextField("E-Mail");
	private PasswordField passwort = new PasswordField("Passwort");
	private TextField eintrittsdatum = new TextField("Eintrittsdatum");
	private TextField austrittsdatum = new TextField("Austrittsdatum");
	private TextField benutzername = new TextField("Benutzername");
	private TwinColSelect rollen = new TwinColSelect();

	private String nameInput;
	private String vornameInput;
	private String emailInput;
	private String passwortInput;
	private String eintrittsdatumInput;
	private String austrittsdatumInput;
	private String benutzernameInput;

	private Mitarbeiter mitarbeiter = new Mitarbeiter();
	private List<Rollen> rollenlist = new ArrayList<Rollen>();
	public String valueString = new String();

	private Button speichern = new Button(IConstants.BUTTON_SAVE);
	private Button verwerfen = new Button(IConstants.BUTTON_DISCARD);

	public MitarbeiterBearbeiten() {
		super();
		this.setSizeFull();
		this.setMargin(true);

		headline = new Label("Mitarbeiter bearbeiten");
		headline.setStyleName("ViewHeadline");

		name.setWidth("100%");
		vorname.setWidth("100%");
		email.setWidth("100%");
		passwort.setWidth("100%");
		eintrittsdatum.setWidth("100%");
		austrittsdatum.setWidth("100%");
		benutzername.setWidth("100%");

		fenster.setWidth("900px");
		fenster.setSpacing(true);

		VerticalLayout links = new VerticalLayout();
		links.setWidth("300px");
		links.setSpacing(true);

		VerticalLayout rechts = new VerticalLayout();
		rechts.setWidth("300px");
		rechts.setSpacing(true);

		box.setWidth("900px");
		box.setSpacing(true);
		box.addComponent(links);

		links.addComponent(name);
		links.addComponent(vorname);
		links.addComponent(benutzername);
		links.addComponent(passwort);
		links.addComponent(email);

		rechts.addComponent(eintrittsdatum);
		rechts.addComponent(austrittsdatum);

		rollen.setWidth("300px");
		rollen.setRows(5);
		rollen.setNullSelectionAllowed(true);
		rollen.setMultiSelect(true);
		rollen.setImmediate(true);
		rollen.setLeftColumnCaption("Verf�gbare Rollen");
		rollen.setRightColumnCaption("Ausgew�hlte Rollen");

		rollen.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				valueString = String.valueOf(event.getProperty().getValue());
			}
		});

		try {
			for (int i = 0; i < Rollenverwaltung.getInstance().getAllRollen().size(); i++) {
				rollen.addItem(Rollenverwaltung.getInstance().getAllRollen().get(i).getId());
				rollen.setItemCaption(Rollenverwaltung.getInstance().getAllRollen().get(i).getId(), Rollenverwaltung.getInstance().getAllRollen()
						.get(i).getName());
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		rechts.addComponent(rollen);

		box.addComponent(rechts);

		HorizontalLayout control = new HorizontalLayout();
		control.setWidth("100%");
		control.setSpacing(true);

		control.addComponent(verwerfen);
		control.addComponent(speichern);

		rechts.addComponent(control);
		rechts.setComponentAlignment(control, Alignment.TOP_CENTER);

		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		speichern.setEnabled(false);
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));

		fenster.addComponent(headline);
		fenster.setComponentAlignment(headline, Alignment.MIDDLE_CENTER);
		fenster.addComponent(box);
		fenster.setComponentAlignment(box, Alignment.MIDDLE_CENTER);

		this.addComponent(fenster);
		this.setComponentAlignment(fenster, Alignment.MIDDLE_CENTER);

		name.setImmediate(true);
		name.addValidator(new StringLengthValidator("Bitte g�ltigen Namen eingeben", 3, 45, false));
		name.setMaxLength(45);

		vorname.setImmediate(true);
		vorname.addValidator(new StringLengthValidator("Bitte g�ltigen Namen eingeben", 3, 45, false));
		vorname.setMaxLength(45);

		email.setImmediate(true);
		email.addValidator(new EmailValidator("Bitte g�ltige E-Mailadresse angeben"));
		email.setMaxLength(45);

		passwort.setImmediate(true);
		passwort.addValidator(new StringLengthValidator("Bitte g�ltigen Namen eingeben", 6, 45, false));
		passwort.setMaxLength(45);

		eintrittsdatum.setImmediate(true);
		eintrittsdatum.setMaxLength(45);

		austrittsdatum.setImmediate(true);
		austrittsdatum.setMaxLength(300);

		benutzername.setImmediate(true);
		benutzername.addValidator(new StringLengthValidator("Bitte g�ltigen Namen eingeben", 3, 45, false));
		benutzername.setMaxLength(45);

		name.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty().getValue());
				nameInput = valueString;
				if (name.isValid() == true) {
					speichern.setEnabled(true);
				} else {
					speichern.setEnabled(false);
				}

			}
		});

		vorname.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty().getValue());

				vornameInput = valueString;
			}
		});

		email.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty().getValue());

				emailInput = valueString;
			}
		});

		passwort.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty().getValue());

				passwortInput = valueString;
			}
		});

		eintrittsdatum.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty().getValue());

				eintrittsdatumInput = valueString;
			}
		});

		austrittsdatum.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty().getValue());

				austrittsdatumInput = valueString;
			}
		});

		benutzername.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty().getValue());

				benutzernameInput = valueString;
			}
		});

		verwerfen.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().returnToDefault();

			}
		});

		speichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				mitarbeiter.setName(nameInput);
				mitarbeiter.setVorname(vornameInput);
				mitarbeiter.setEmail(emailInput);
				try {
					mitarbeiter.setPasswort(Util.getMD5(passwortInput).toString());
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mitarbeiter.setEintrittsdatum(eintrittsdatumInput);
				mitarbeiter.setAustrittsdatum(austrittsdatumInput);
				mitarbeiter.setBenutzername(benutzernameInput);

				// Listbuilder: ValueChangeListener gibt einen String der IDs
				// zur�ck z.B. [1, 3]
				// String auseinander nehmen und die Objekte anhand der ID
				// suchen und der Liste hinzuf�gen
				List<String> rollenId = null;
				if (rollen.getValue().toString() != "[]") {
					rollenId = Arrays.asList(valueString.substring(1, valueString.length() - 1).split("\\s*,\\s*"));

					
					for (String sId : rollenId) {
						Long id = null;
						try {
							id = Long.parseLong(sId.trim());
							
						} catch (NumberFormatException nfe) {

						}

						Rollen rollen = null;
						try {
							rollen = Rollenverwaltung.getInstance().getRollenById(id);
							
							rollenlist.add(rollen);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
				
				mitarbeiter.setRollen(rollenlist);

				try {
					Mitarbeiterverwaltung.getInstance().updateMitarbeiter(mitarbeiter);
				} catch (Exception e) {
					e.printStackTrace();
				}

				ViewHandler.getInstance().switchView(MitarbeiterAnzeigen.class);

			}
		});
	}

	@Override
	public void getViewParam(ViewData data) {
		mitarbeiter = (Mitarbeiter) ((ViewDataObject<?>) data).getData();

		try {
			mitarbeiter = Mitarbeiterverwaltung.getInstance().getMitarbeiterById(mitarbeiter.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		name.setValue(mitarbeiter.getName());

		vorname.setValue(mitarbeiter.getVorname());

		email.setValue(mitarbeiter.getEmail());

		eintrittsdatum.setValue(mitarbeiter.getEintrittsdatum());

		austrittsdatum.setValue(mitarbeiter.getAustrittsdatum());

		benutzername.setValue(mitarbeiter.getBenutzername());
		
		passwort.setValue(mitarbeiter.getPasswort());

		for (int i = 0; i < mitarbeiter.getRollen().size(); i++) {
			rollen.select(mitarbeiter.getRollen().get(i).getId());

		}
	}
}

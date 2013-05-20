package main.java.de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;

import org.tepi.filtertable.FilterTable;

import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import main.java.de.hska.awp.palaver2.data.ConnectException;
import main.java.de.hska.awp.palaver2.data.DAOException;
import main.java.de.bistrosoft.palaver.data.GeschmackDAO;
import main.java.de.bistrosoft.palaver.menueplanverwaltung.MenueplanGridLayout;
import main.java.de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import main.java.de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import main.java.de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import main.java.de.hska.awp.palaver2.util.View;
import main.java.de.hska.awp.palaver2.util.ViewData;
import main.java.de.hska.awp.palaver2.util.ViewDataObject;
import main.java.de.hska.awp.palaver2.util.ViewHandler;
import main.java.de.hska.awp.palaver2.util.customFilter;
import main.java.de.hska.awp.palaver2.util.customFilterDecorator;

/**
 * @author Michael Marschall Jan Lauinger - Geschmack hinzufügen
 * 
 */
public class GeschmackEinst extends VerticalLayout implements View {

	private static final long serialVersionUID = 2474121007841510011L;

	private VerticalLayout box = new VerticalLayout();

	private Label ueberschrift = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Geschmack anlegen</font><b></pre>",
			Label.CONTENT_XHTML);

	private TextField name = new TextField("Geschmack");

	private Button speichern = new Button("Speichern");
	private Button aktiv = new Button("Aktiv");
	private Button loeschen = new Button("Inaktiv");

	private Boolean inaktiv = true;
	Geschmack geschmack2;

	private Label dummy = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"></font><b></pre>",
			Label.CONTENT_XHTML);
	
	private String nameInput;
	private FilterTable table;
	private Label hinweis = new Label( "<pre><font size='3' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"><b>Bedeutung:</b> false: aktiv | true: inaktiv</font></pre>",
			Label.CONTENT_XHTML);;

	public GeschmackEinst() {
		super();
		table = new FilterTable();
		name.setWidth("100%");
		table.setSizeFull();
		table.setSelectable(true);
		table.setFilterBarVisible(true);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());
		

		box.setWidth("300px");
		box.setSpacing(true);

		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		box.addComponent(dummy);
		box.addComponent(ueberschrift);
		box.addComponent(name);

		HorizontalLayout control = new HorizontalLayout();
		control.setSpacing(true);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_CENTER);

		name.setImmediate(true);
		name.setInputPrompt(nameInput);
		name.setMaxLength(150);

		control.addComponent(aktiv);
		control.addComponent(speichern);
		control.addComponent(loeschen);
		speichern.setIcon(new ThemeResource("img/save.ico"));
		aktiv.setIcon(new ThemeResource("img/plus.ico"));
		loeschen.setIcon(new ThemeResource("img/cross.ico"));

		name.addValueChangeListener(new ValueChangeListener() {
			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				nameInput = valueString;
			}
		});

		BeanItemContainer<Geschmack> container;
		try {
			container = new BeanItemContainer<Geschmack>(Geschmack.class,
					Geschmackverwaltung.getInstance().getAllGeschmack());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "id", "name", "inaktiv" });
			table.sort(new Object[] { "name" }, new boolean[] { true });
		} catch (Exception e) {
			e.printStackTrace();
		}
		box.addComponent(table);
		box.addComponent(hinweis);

		speichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Geschmack geschmack = new Geschmack();
				geschmack.setName(nameInput);
				try {
					Geschmackverwaltung.getInstance()
							.createGeschmack(geschmack);

				} catch (Exception e) {
					e.printStackTrace();
				}

				Notification notification = new Notification(
						"Geschmack wurde gespeichert!");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
				ViewHandler.getInstance().switchView(GeschmackEinst.class);

			}
		});

		aktiv.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				Geschmack geschmack = new Geschmack();
				Geschmack geschmack1 = new Geschmack();

				geschmack.setInaktiv(inaktiv);
				geschmack.setName(nameInput);
				try {
					geschmack1 = GeschmackDAO.getInstance().getGeschmackById(
							geschmack2.getId());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					Geschmackverwaltung.getInstance().updateGeschmackAktiv(
							geschmack1);
				} catch (Exception e) {
					e.printStackTrace();
				}

				Notification notification = new Notification(
						"Geschmack wurde aktiviert!");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
				ViewHandler.getInstance().switchView(GeschmackEinst.class);

			}
		});

		// ///////////////////////////

		loeschen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				Geschmack geschmack = new Geschmack();
				Geschmack geschmack1 = new Geschmack();

				geschmack.setInaktiv(inaktiv);
				geschmack.setName(nameInput);
				try {
					geschmack1 = GeschmackDAO.getInstance().getGeschmackById(
							geschmack2.getId());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(geschmack);
				System.out.println(geschmack1);
				System.out.println(nameInput);

				try {
					Geschmackverwaltung.getInstance().updateGeschmackInaktiv(
							geschmack1);
				} catch (Exception e) {
					e.printStackTrace();
				}

				Notification notification = new Notification(
						"Geschmack wurde gelöscht!");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
				ViewHandler.getInstance().switchView(GeschmackEinst.class);

			}
		});
		table.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					geschmack2 = (Geschmack) event.getProperty().getValue();
					System.out.println(geschmack2);
				}

			}
		});

		table.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {

				if (event.isDoubleClick()) {
					name.setValue(geschmack2.getName());
				} else {

				}

			}
		});

	}

	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub

	}
}
package de.bistrosoft.palaver.gui.view;

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

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.menueplanverwaltung.MenueplanGridLayout;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import de.bistrosoft.palaver.util.View;
import de.bistrosoft.palaver.util.ViewData;
import de.bistrosoft.palaver.util.ViewDataObject;
import de.bistrosoft.palaver.util.ViewHandler;
import de.bistrosoft.palaver.util.customFilter;
import de.bistrosoft.palaver.util.customFilterDecorator;

/**
 * @author Michael Marschall Jan Lauinger - Geschmack hinzufügen
 * 
 */
public class GeschmackEinst extends VerticalLayout implements View {

	private static final long serialVersionUID = 2474121007841510011L;

	private VerticalLayout box = new VerticalLayout();
	private VerticalLayout box1 = new VerticalLayout();
	private HorizontalLayout horizont = new HorizontalLayout();
	private VerticalLayout d1 = new VerticalLayout();
	private HorizontalLayout d2 = new HorizontalLayout();
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
			"<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>",
			Label.CONTENT_XHTML);

	private String nameInput;
	private FilterTable table;

	public GeschmackEinst() {
		super();
		table = new FilterTable();
		name.setWidth("100%");
		// table.setSizeFull();
		table.setSelectable(true);
		table.setFilterBarVisible(true);
		table.setFilterGenerator(new customFilter());
		table.setFilterDecorator(new customFilterDecorator());

		box.setWidth("300px");
		box.setSpacing(true);

		this.addComponent(d2);
		this.setComponentAlignment(d2, Alignment.MIDDLE_CENTER);
		d2.addComponent(dummy);
		this.addComponent(horizont);
		this.setComponentAlignment(horizont, Alignment.MIDDLE_CENTER);
		horizont.addComponent(box1);
		horizont.addComponent(d1);
		d1.addComponent(dummy);
		horizont.addComponent(box);
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
		} catch (IllegalArgumentException | ConnectException | DAOException
				| SQLException e) {
			e.printStackTrace();
		}
		box1.addComponent(table);
		
		speichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Geschmack geschmack = new Geschmack();
//				geschmack.setId(GeschmackDAO.getInstance().getGeschmackByName1(Long.parseLong(nameInput.toString())));
				geschmack.setName(nameInput);

				try {
					Geschmackverwaltung.getInstance()
							.createGeschmack(geschmack);

				} catch (ConnectException | DAOException | SQLException e) {
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

				//		geschmack.setId()
				geschmack.setInaktiv(inaktiv);
				geschmack.setName(nameInput);
				try {
					geschmack1 = GeschmackDAO.getInstance().getGeschmackById(geschmack2.getId());
				} catch (ConnectException | DAOException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(geschmack);
				System.out.println(geschmack1);
				System.out.println(nameInput);

			
//				final Boolean inaktiv = true;
//				geschmack.setInaktiv(inaktiv);

  				try {Geschmackverwaltung.getInstance()
							.updateGeschmackAktiv(geschmack1);
											} catch (ConnectException | DAOException | SQLException e) {
					e.printStackTrace();
				}

				Notification notification = new Notification(
						"Geschmack wurde aktiviert!");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
				ViewHandler.getInstance().switchView(GeschmackEinst.class);
 			
			}
		});
		
		/////////////////////////////

		
		loeschen.addClickListener(new ClickListener(){
			@Override
			public void buttonClick(final ClickEvent event) {
				Geschmack geschmack = new Geschmack();
				Geschmack geschmack1 = new Geschmack();

				//		geschmack.setId()
				geschmack.setInaktiv(inaktiv);
				geschmack.setName(nameInput);
				try {
					geschmack1 = GeschmackDAO.getInstance().getGeschmackById(geschmack2.getId());
				} catch (ConnectException | DAOException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(geschmack);
				System.out.println(geschmack1);
				System.out.println(nameInput);

			
//				final Boolean inaktiv = true;
//				geschmack.setInaktiv(inaktiv);

  				try {Geschmackverwaltung.getInstance()
							.updateGeschmackInaktiv(geschmack1);
											} catch (ConnectException | DAOException | SQLException e) {
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
				
				
				
				if(event.isDoubleClick()){
				name.setValue(geschmack2.getName());
				}
				else {
					
				}

			}
		});

		
//		private void GeschmackLoeschen(//ID setzen) {
//			
//		}
//
//		BeanItemContainer<Geschmack> container;
//		try {
//			container = new BeanItemContainer<Geschmack>(Geschmack.class,
//					Geschmackverwaltung.getInstance().getAllGeschmack());
//			table.setContainerDataSource(container);
//			table.setVisibleColumns(new Object[] { "id", "name" });
//			table.sort(new Object[] { "name" }, new boolean[] { true });
//		} catch (IllegalArgumentException | ConnectException | DAOException
//				| SQLException e) {
//			e.printStackTrace();
//		}
//		box1.addComponent(table);

	}

	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub

	}
}
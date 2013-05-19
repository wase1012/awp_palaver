package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptartverwaltung;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;
import de.hska.awp.palaver2.util.customFilter;
import de.hska.awp.palaver2.util.customFilterDecorator;

/**
 * @author Michael Marschall
 * 
 */
public class FussnoteEinst extends VerticalLayout implements View {

	private static final long serialVersionUID = 2474121007841510011L;

	private VerticalLayout box = new VerticalLayout();
	private Label ueberschrift = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Fussnote anlegen</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label dummy = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\"></font><b></pre>",
			Label.CONTENT_XHTML);

	private TextField name = new TextField("Fussnote");
	private TextField abkuerzung = new TextField("Abkuerzung");

	private Button speichern = new Button("Speichern");

	private String nameInput;
	private String abkuerzungInput;
	private FilterTable table;

	public FussnoteEinst() {
		super();
		table = new FilterTable();
		name.setWidth("100%");
		abkuerzung.setWidth("100%");
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
		box.addComponent(abkuerzung);

		HorizontalLayout control = new HorizontalLayout();
		control.setSpacing(true);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);

		name.setImmediate(true);
		name.setInputPrompt(nameInput);
		name.setMaxLength(150);

		abkuerzung.setImmediate(true);
		abkuerzung.setInputPrompt(abkuerzungInput);
		abkuerzung.setMaxLength(150);
		
		control.addComponent(speichern);
		speichern.setIcon(new ThemeResource("img/save.ico"));

		name.addValueChangeListener(new ValueChangeListener() {
			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				nameInput = valueString;
			}
		});
		
		abkuerzung.addValueChangeListener(new ValueChangeListener() {
			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());

				abkuerzungInput = valueString;
			}
		});

		speichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Fussnote fn = new Fussnote();
				fn.setName(nameInput);
				fn.setAbkuerzung(abkuerzungInput);

				try {
					Fussnotenverwaltung.getInstance()
							.createFussnote(fn);

				} catch (Exception e) {
					e.printStackTrace();
				}

				Notification notification = new Notification(
						"Fussnote wurde gespeichert!");
				notification.setDelayMsec(500);
				notification.show(Page.getCurrent());
				
				ViewHandler.getInstance().switchView(FussnoteEinst.class);

			}
		});

		BeanItemContainer<Fussnote> container;
		try {
			container = new BeanItemContainer<Fussnote>(Fussnote.class,
					Fussnotenverwaltung.getInstance().getAllFussnote());
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "id", "name", "abkuerzung" });
			table.sort(new Object[] { "name" }, new boolean[] { true });
		} catch (Exception e) {
			e.printStackTrace();
		}
		box.addComponent(table);

	}

	@Override
	public void getViewParam(ViewData data) {

	}
}
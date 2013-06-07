package de.bistrosoft.palaver.gui.view;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.bistrosoft.palaver.rezeptverwaltung.service.Fussnotenverwaltung;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * @author Michael Marschall
 * 
 */
@SuppressWarnings("serial")
public class FussnoteEinst extends VerticalLayout implements View {

	private static final long serialVersionUID = 2474121007841510011L;

	private Label lUeberschrift = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Fussnote anlegen</font><b></pre>",
			ContentMode.HTML);
	
	private VerticalLayout vlBox = new VerticalLayout();
	private HorizontalLayout hlUeberschrift = new HorizontalLayout();
	private HorizontalLayout hlControl = new HorizontalLayout();
	
	private TextField tfBezeichnung = new TextField("Bezeichnung");
	private TextField tfAbkuerzung = new TextField("Abkürzung");

	private Button btSpeichern = new Button("Speichern");

	private FilterTable tblZubereitung;
	
	Fussnote fn = new Fussnote();

	public FussnoteEinst() {
		super();
		tblZubereitung = new FilterTable();
		tfBezeichnung.setWidth("100%");
		tfAbkuerzung.setWidth("100%");
		tblZubereitung.setSizeFull();
		tblZubereitung.setSelectable(true);
		tblZubereitung.setFilterBarVisible(true);

		this.addComponent(vlBox);
		this.setComponentAlignment(vlBox, Alignment.MIDDLE_CENTER);
		
		vlBox.setWidth("300px");
		vlBox.setSpacing(true);
		
		vlBox.addComponent(hlUeberschrift);
		hlUeberschrift.addComponent(lUeberschrift);
		hlUeberschrift.setComponentAlignment(lUeberschrift, Alignment.MIDDLE_CENTER);
		
		vlBox.addComponent(tfBezeichnung);
		vlBox.addComponent(tfAbkuerzung);
		
		tfBezeichnung.setImmediate(true);
		tfBezeichnung.setMaxLength(150);

		tfAbkuerzung.setImmediate(true);
		tfAbkuerzung.setMaxLength(150);

		vlBox.addComponent(hlControl);
		vlBox.setComponentAlignment(hlControl, Alignment.MIDDLE_RIGHT);
		hlControl.addComponent(btSpeichern);
		btSpeichern.setIcon(new ThemeResource("img/save.ico"));
		hlControl.setSpacing(true);

		vlBox.addComponent(tblZubereitung);
		
		btSpeichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					speichern();
					ViewHandler.getInstance().switchView(FussnoteEinst.class);
				}

			}
		});

		BeanItemContainer<Fussnote> container;
		try {
			container = new BeanItemContainer<Fussnote>(Fussnote.class,
					Fussnotenverwaltung.getInstance().getAllFussnote());
			tblZubereitung.setContainerDataSource(container);
			tblZubereitung.setVisibleColumns(new Object[] { "id", "bezeichnung", "abkuerzung" });
			tblZubereitung.sort(new Object[] { "id" }, new boolean[] { true });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void speichern() {
		
		fn.setName(tfBezeichnung.getValue());
		fn.setAbkuerzung(tfAbkuerzung.getValue());

		try {
			Fussnotenverwaltung.getInstance().createFussnote(fn);

		} catch (Exception e) {
			e.printStackTrace();
		}

		showNotification("Fussnote wurde gespeichert!");
	}

	private void showNotification(String text) {
		Notification notification = new Notification(text);
		notification.setDelayMsec(500);
		notification.show(Page.getCurrent());
	}

	private Boolean validiereEingabe() {
		if (tfBezeichnung.getValue().isEmpty()) {
			showNotification("Bitte Bezeichnung eingeben!");
			return false;
		}
		if (tfAbkuerzung.getValue().isEmpty()) {
			showNotification("Bitte Abkürzung eingeben!");
			return false;
		}
		return true;
	}

	@Override
	public void getViewParam(ViewData data) {

	}
}
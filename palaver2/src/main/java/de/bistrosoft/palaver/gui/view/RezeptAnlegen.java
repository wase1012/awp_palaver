/**
 * 
 * Jan Lauinger
 * 18.04.2013 - 21:21:58
 *
 */
package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;
import java.util.List;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Geschmack;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezeptart;
import de.bistrosoft.palaver.rezeptverwaltung.service.Geschmackverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptartverwaltung;
import de.bistrosoft.palaver.util.ViewHandler;

/**
 * @author Jan Lauinger
 * 
 */
@SuppressWarnings("serial")
public class RezeptAnlegen extends VerticalLayout {

	private VerticalLayout box = new VerticalLayout();

	private Label ueberschrift = new Label(
			"<pre><b><font size='5' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Rezept anlegen</font><b></pre>",
			Label.CONTENT_XHTML);

	private TextField portion = new TextField("Portion");
	private TextField name = new TextField("Bezeichnung");

	private ComboBox mitarbeiter = new ComboBox("Koch");
	private ComboBox rezeptart = new ComboBox("Rezeptart");
	private ComboBox geschmack = new ComboBox("Geschmack");

	private TextArea kommentar = new TextArea("Kommentar");

	private Button speichern = new Button("Speichern");
	private Button verwerfen = new Button("Verwerfen");

	public RezeptAnlegen() {
		super();
		this.setSizeFull();
		this.setMargin(true);
		name.setWidth("100%");
		portion.setWidth("100%");
		mitarbeiter.setWidth("100%");
		rezeptart.setWidth("100%");
		geschmack.setWidth("100%");
		kommentar.setWidth("100%");

		portion.setEnabled(false);

		box.setWidth("300px");
		box.setSpacing(true);

		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		box.addComponent(ueberschrift);
		box.addComponent(name);
		box.addComponent(portion);
		box.addComponent(mitarbeiter);
		box.addComponent(rezeptart);
		box.addComponent(geschmack);
		box.addComponent(kommentar);

		HorizontalLayout control = new HorizontalLayout();
		control.setSpacing(true);
		box.addComponent(control);
		box.setComponentAlignment(control, Alignment.MIDDLE_RIGHT);

		control.addComponent(verwerfen);
		control.addComponent(speichern);
		speichern.setIcon(new ThemeResource("img/save.ico"));
		verwerfen.setIcon(new ThemeResource("img/cross.ico"));

		speichern.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				final Window dialog = new Window();
				dialog.setClosable(false);
				dialog.setWidth("300px");
				dialog.setHeight("150px");
				dialog.setModal(true);
				dialog.center();
				dialog.setResizable(false);
				dialog.setStyleName("dialog-window");

				Label message = new Label("Artikel gespeichert");

				Button okButton = new Button("OK");

				VerticalLayout dialogContent = new VerticalLayout();
				dialogContent.setSizeFull();
				dialogContent.setMargin(true);
				dialog.setContent(dialogContent);

				dialogContent.addComponent(message);
				dialogContent.addComponent(okButton);
				dialogContent.setComponentAlignment(okButton,
						Alignment.BOTTOM_RIGHT);

				UI.getCurrent().addWindow(dialog);

				okButton.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						UI.getCurrent().removeWindow(dialog);
						ViewHandler.getInstance().returnToDefault();
					}
				});
			}
		});
//		load();
	}

	
//	public void load() {
//		try {
//			List<Geschmack> geschmack = Geschmackverwaltung.getInstance().getAllGeschmack();
//			for (Geschmack e : geschmack) {
//				geschmack.addItem(e);
//				
//			}
////			List<Kategorie> kategorien = Artikelverwaltung.getInstance()
////					.getAllKategorien();
////			for (Kategorie e : kategorien) {
////				kategorie.addItem(e);
////			}
//			List<Rezeptart> rezeptart = Rezeptartverwaltung.getInstance().getAllRezeptart();
//			for (Rezeptart e : rezeptart) {
//				rezeptart.addItem(e);
//			}
////			List<Mengeneinheit> mengen = Mengeneinheitverwaltung.getInstance()
////					.getAllMengeneinheit();
////			for (Mengeneinheit e : mengen) {
////				mengeneinheit.addItem(e);
////			}
//		} catch (ConnectException | DAOException | SQLException e) {
//			e.printStackTrace();
//		}
//	}
}

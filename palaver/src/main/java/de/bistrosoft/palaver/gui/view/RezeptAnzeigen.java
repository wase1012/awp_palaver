package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;
import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;

public class RezeptAnzeigen extends VerticalLayout implements View {

	private static final long serialVersionUID = 2001790425726326012L;
	private Rezept rezept;
//	private List<RezeptHasArtikel>rha=new List<RezeptHasArtikel>();

	// Layouts
	private HorizontalLayout oben = new HorizontalLayout();
	private HorizontalLayout unten = new HorizontalLayout();
	private VerticalLayout links = new VerticalLayout();
	private VerticalLayout rechts = new VerticalLayout();
	private VerticalLayout mitte = new VerticalLayout();
	private VerticalLayout verticalunten = new VerticalLayout();

	// Ueberschriften
	private Label uBezeichnung = new Label(
			"<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Bezeichnung</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label uRezeptersteller = new Label(
			"<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Koch</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label uPortion = new Label(
			"<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Portion</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label uArt = new Label(
			"<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Rezeptart</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label uGeschmack = new Label(
			"<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Geschmack</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label uKommentar = new Label(
			"<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Kommentar</font><b></pre>",
			Label.CONTENT_XHTML);
	private Label uZutaten = new Label(
			"<pre><b><font size='2' face=\"Arial, Helvetica, Tahoma, Verdana, sans-serif\">Zutaten</font><b></pre>",
			Label.CONTENT_XHTML);

	// Textfelder
	private TextField bezeichnung = new TextField();
	private TextField rezeptersteller = new TextField();
	private TextField portion = new TextField();
	private TextField art = new TextField();
	private TextField geschmack = new TextField();

	// Tabellen
	private Table zutaten = new Table();

	// Textarea
	private TextArea kommentar = new TextArea();

	// Button
	private Button aendern = new Button("Rezept bearbeiten");

	public RezeptAnzeigen() {
		super();
		this.setSizeFull();
		this.setMargin(true);
		this.addComponent(oben);
		this.setComponentAlignment(oben, Alignment.MIDDLE_CENTER);

		HorizontalLayout subBox = new HorizontalLayout();
		HorizontalLayout control = new HorizontalLayout();

		// Eigenschaften anpassen
		bezeichnung.setWidth("100%");
		bezeichnung.setReadOnly(true);
		rezeptersteller.setWidth("100%");
		rezeptersteller.setReadOnly(true);
		kommentar.setWidth("100%");
		kommentar.setReadOnly(true);
		portion.setWidth("100%");
		portion.setReadOnly(true);
		geschmack.setWidth("100%");
		geschmack.setReadOnly(true);
		art.setWidth("100%");
		art.setReadOnly(true);
		oben.setSpacing(true);
		links.setWidth("300px");
		links.setSpacing(true);
		mitte.setWidth("300px");
		mitte.setSpacing(true);
		rechts.setWidth("300px");
		rechts.setSpacing(true);
		unten.setWidth("100%");
		unten.setSpacing(true);
		unten.setHeight(100.0f, Unit.PERCENTAGE);
		subBox.setWidth("100%");
		subBox.setImmediate(true);
		control.setSpacing(true);

		// Komponenten zufuegen
		oben.addComponent(links);
		oben.addComponent(mitte);
		oben.addComponent(rechts);
		links.addComponent(uBezeichnung);
		links.addComponent(bezeichnung);
		links.addComponent(uRezeptersteller);
		links.addComponent(rezeptersteller);
		links.addComponent(uPortion);
		links.addComponent(portion);
		links.addComponent(uGeschmack);
		links.addComponent(geschmack);
		links.addComponent(uArt);
		links.addComponent(art);
		links.addComponent(subBox);
		links.addComponent(control);
		links.setComponentAlignment(control, Alignment.MIDDLE_LEFT);
		control.addComponent(aendern);
		mitte.addComponent(unten);
		rechts.addComponent(uZutaten);
		rechts.addComponent(zutaten);

		zutaten.addContainerProperty("Bezeichnug", String.class, null);
		zutaten.addContainerProperty("Menge", Integer.class, null);
		zutaten.addContainerProperty("Mengeneinheit", String.class, null);
		zutaten.addContainerProperty("Typ", String.class, null);

		unten.addComponent(verticalunten);
		verticalunten.addComponent(uKommentar);
		verticalunten.addComponent(kommentar);
	}

	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void getViewParam(ViewData data)
//	{
//		rezept = (Rezept) ((ViewDataObject<?>)data).getData();
//		if(rezept.getId() == null) {
//			try {
//				rezept = Rezeptverwaltung.getInstance().getRezeptById();
//			} catch (ConnectException | DAOException | SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//			
//		bezeichnung.setValue(rezept.getName());
//		bezeichnung.setEnabled(false);
//			
//		geschmack.setValue(rezept.getGeschmack().getName());
//		geschmack.setEnabled(false);
//			
//		kommentar.setValue(rezept.getKommentar());
//		kommentar.setEnabled(false);
//			
//		rezeptersteller.setValue(rezept.getMitarbeiter().getName());
//		rezeptersteller.setEnabled(false);
//					
//		portion.setValue(Integer.parseInt. (rezept.getPortion()));
//		portion.setEnabled(false);
//			
//		art.setValue(rezept.getRezeptart().getName());
//		art.setEnabled(false);
//					
//		BeanItemContainer<RezeptHasArtikel> container;

//		try {
//			container = new BeanItemContainer<RezeptHasArtikel>(RezeptHasArtikel.class, Rezeptverwaltung.getInstance().getAllArtikelByRezeptId());
//			ansprechpartner.setContainerDataSource(container);
//			ansprechpartner.setVisibleColumns(new Object[] {"name", "telefon", "handy", "fax"});
//			ansprechpartner.sort(new Object[] {"id"}, new boolean[] {true});
//			ansprechpartner.setColumnCollapsingAllowed(true);
//			ansprechpartner.setColumnCollapsed(handyAnspr, false);
//			ansprechpartner.setColumnCollapsed(faxAnspr, false);				
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}

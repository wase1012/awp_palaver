package de.bistrosoft.palaver.gui.layout;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.gui.view.Fussnoten;
import de.bistrosoft.palaver.gui.view.GeschmackEinst;
import de.bistrosoft.palaver.gui.view.MenueAnlegen;
import de.bistrosoft.palaver.gui.view.MenueplanHistorie;
import de.bistrosoft.palaver.gui.view.RezeptAnlegen;
import de.bistrosoft.palaver.gui.view.RezeptAnzeigen;
import de.bistrosoft.palaver.gui.view.RezeptAnzeigenTabelle;
import de.bistrosoft.palaver.gui.view.RezeptartEinst;
import de.bistrosoft.palaver.gui.view.Zubereitungen;
import de.bistrosoft.palaver.gui.view.Menueplan;
import de.bistrosoft.palaver.util.IConstants;
import de.bistrosoft.palaver.util.ViewHandler;

/**
 * @author Jan Lauinger
 * 
 */
@SuppressWarnings("serial")
public class MainLayout extends VerticalLayout implements Command {
	private static MainLayout instance = null;

	private HorizontalLayout header = new HorizontalLayout();

	private MenuBar menu = new MenuBar();

	@SuppressWarnings("deprecation")
	private MainLayout() {
		super();

		this.setSizeFull();

		header.setWidth("100%");
		header.setHeight("100px");
		header.setStyleName("palaver-header");

		// Image logo = new Image(null, new
		// ThemeResource("../img/cafe_palaver_Logo.png"));
		// header.addComponent(logo);
		// header.setComponentAlignment(logo, Alignment.MIDDLE_RIGHT);
		// logo.addClickListener(new ClickListener()
		// {
		// @Override
		// public void click(ClickEvent event)
		// {
		// ViewHandler.getInstance().switchView(DefaultView.class);
		// }
		// });

		header.addListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				ViewHandler.getInstance().switchView(DefaultView.class);
			}
		});

		this.addComponent(header);

		menu.setWidth("100%");
		MenuItem artikelItem = menu.addItem(IConstants.MENU_ARTIKEL_HEADLINE,
				null);
		artikelItem.addItem(IConstants.MENU_ARTIKEL_NEU, this);
		artikelItem.addItem(IConstants.MENU_ARTIKEL_ANZEIGEN, this);

		MenuItem lieferantItem = menu.addItem(
				IConstants.MENU_LIEFERANT_HEADLINE, null);
		lieferantItem.addItem(IConstants.MENU_LIEFERANT_NEW, this);
		lieferantItem.addItem(IConstants.MENU_LIEFERANT_ANZEIGEN, this);

		MenuItem rezeptItem = menu.addItem(IConstants.MENU_REZEPT_HEADLINE,
				null);
		rezeptItem.addItem(IConstants.MENU_REZEPT_NEU, this);
		rezeptItem.addItem(IConstants.MENU_REZEPT_ANZEIGEN, this);
		MenuItem menue1Item = menu.addItem(IConstants.MENU_MENUE_HEADLINE,
				null);
		menue1Item.addItem(IConstants.MENU_MENUE_ANLEGEN, this);


		MenuItem menuplanItem = menu.addItem(IConstants.MENU_MENUPLAN_HEADLINE,
				null);
		menuplanItem.addItem(IConstants.MENU_MENUPLAN_AKTUELL, this);
		menuplanItem.addItem(IConstants.MENU_MENUPLAN_HISTORIE, this);

		MenuItem bestellungItem = menu.addItem(
				IConstants.MENU_BESTELLUNG_HEADLINE, null);

		MenuItem einstellungItem = menu.addItem(
				IConstants.MENU_EINSTELLUNGEN_HEADLINE, null);
		einstellungItem.addItem(IConstants.MENU_FUSSNOTE, this);
		einstellungItem.addItem(IConstants.MENU_GESCHMACK, this);
		einstellungItem.addItem(IConstants.MENU_REZEPTART, this);
		einstellungItem.addItem(IConstants.MENU_ZUBEREITUNG, this);
		this.addComponent(menu);

		DefaultView content = new DefaultView();
		this.addComponent(content);
		this.setExpandRatio(content, 1);

		// this.addComponent(new ArtikelErstellen());
		// this.setExpandRatio(this.getComponent(2), 1);
	}

	public static MainLayout getInstance() {
		if (instance == null) {
			instance = new MainLayout();
		}
		return instance;
	}

	@Override
	public void menuSelected(MenuItem selectedItem) {
		switch (selectedItem.getText()) {
		case IConstants.MENU_REZEPT_ANZEIGEN:
			ViewHandler.getInstance().switchView(RezeptAnzeigenTabelle.class);
			break;
		case IConstants.MENU_REZEPT_NEU:
			ViewHandler.getInstance().switchView(RezeptAnlegen.class);
			break;
		case IConstants.MENU_MENUPLAN_AKTUELL:
			ViewHandler.getInstance().switchView(Menueplan.class);
			break;
		case IConstants.MENU_MENUPLAN_HISTORIE:
			ViewHandler.getInstance().switchView(MenueplanHistorie.class);
			break;
		case IConstants.MENU_FUSSNOTE:
			ViewHandler.getInstance().switchView(Fussnoten.class);
			break;
		case IConstants.MENU_GESCHMACK:
			ViewHandler.getInstance().switchView(GeschmackEinst.class);
			break;
		case IConstants.MENU_REZEPTART:
			ViewHandler.getInstance().switchView(RezeptartEinst.class);
			break;
		case IConstants.MENU_ZUBEREITUNG:
			ViewHandler.getInstance().switchView(Zubereitungen.class);
			break;
		case IConstants.MENU_MENUE_ANLEGEN:
			ViewHandler.getInstance().switchView(MenueAnlegen.class);
			break;
		default:
			ViewHandler.getInstance().switchView(DefaultView.class);
		}
	}

}

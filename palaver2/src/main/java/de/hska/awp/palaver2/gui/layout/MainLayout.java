/**
 * Sebastian Walz
 */
package de.hska.awp.palaver2.gui.layout;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.MenuItem;

import de.hska.awp.palaver2.Application;
import de.hska.awp.palaver2.gui.view.ArtikelAnzeigen;
import de.hska.awp.palaver2.gui.view.ArtikelErstellen;
import de.hska.awp.palaver2.gui.view.BestellungAnzeigen;
import de.hska.awp.palaver2.gui.view.BestellungErstellen;
import de.hska.awp.palaver2.gui.view.BestellungAuswaehlen;
import de.hska.awp.palaver2.gui.view.BestellungLieferantAuswaehlen;
import de.hska.awp.palaver2.gui.view.KategorienAnzeigen;
import de.hska.awp.palaver2.gui.view.LieferantAnzeigen;
import de.hska.awp.palaver2.gui.view.LieferantErstellen;
import de.hska.awp.palaver2.gui.view.MengeneinheitErstellen;
import de.hska.awp.palaver2.gui.view.MengeneinheitenAnzeigen;
import de.hska.awp.palaver2.gui.view.MitarbeiterAnzeigen;
import de.hska.awp.palaver2.gui.view.MitarbeiterErstellen;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * @author Sebastian
 *
 */
@SuppressWarnings("serial")
public class MainLayout extends VerticalLayout implements Command
{
	private static MainLayout		instance = null;
	
	private HorizontalLayout		header = new HorizontalLayout();
	
	private MenuBar					menu = new MenuBar();
	private MenuItem 				username = null;
	@SuppressWarnings("deprecation")
	private MainLayout()
	{
		super();
		
		this.setSizeFull();
		
		header.setWidth("100%");
		header.setHeight("100px");
		header.setStyleName("palaver-header");
		
//		Image logo = new Image(null, new ThemeResource("../img/cafe_palaver_Logo.png"));
//		header.addComponent(logo);
//		header.setComponentAlignment(logo, Alignment.MIDDLE_RIGHT);
//		logo.addClickListener(new ClickListener()
//		{
//			@Override
//			public void click(ClickEvent event)
//			{
//				ViewHandler.getInstance().switchView(DefaultView.class);
//			}
//		});
		
		header.addListener(new LayoutClickListener()
		{
			
			@Override
			public void layoutClick(LayoutClickEvent event)
			{
				ViewHandler.getInstance().switchView(DefaultView.class);
			}
		});
		
		this.addComponent(header);
		
		menu.setWidth("100%");
		MenuItem artikelItem = menu.addItem(IConstants.MENU_ARTIKEL_HEADLINE, null);
		artikelItem.addItem(IConstants.MENU_ARTIKEL_NEU, this);
		artikelItem.addItem(IConstants.MENU_ARTIKEL_ANZEIGEN, this);
		
		MenuItem lieferantItem = menu.addItem(IConstants.MENU_LIEFERANT_HEADLINE, null);
		lieferantItem.addItem(IConstants.MENU_LIEFERANT_NEW, this);
		lieferantItem.addItem(IConstants.MENU_LIEFERANT_ANZEIGEN, this);
		
//		MenuItem mitarbeiterItem = menu.addItem(IConstants.MENU_MITARBEITER_HEADLINE, null);
//		mitarbeiterItem.addItem(IConstants.MENU_MITARBEITER_NEU, this);
//		mitarbeiterItem.addItem(IConstants.MENU_MITARBEITER_ANZEIGEN, this);
		
		MenuItem bestellungItem = menu.addItem(IConstants.MENU_BESTELLUNG_HEADLINE, null);
		bestellungItem.addItem(IConstants.MENU_BESTELLUNG_NEW_RANDOM, this);
		bestellungItem.addItem(IConstants.MENU_BESTELLUNG_NEW, this);
		bestellungItem.addItem(IConstants.MENU_BESTELLUNG_ANZEIGEN, this);
		
		MenuItem einstellungItem = menu.addItem(IConstants.MENU_EINSTELLUNGEN_HEADLINE, null);
//		einstellungItem.addItem(IConstants.MENU_MENGENEINHEIT_NEU, this);
		einstellungItem.addItem(IConstants.MENU_MENGENEINHEIT_ANZEIGEN, this);
		einstellungItem.addItem(IConstants.MENU_KATEGORIE_ANZEIGEN, this);
		this.addComponent(menu);
		
		MenuItem logout = menu.addItem(IConstants.MENU_LOGOUT, this);
		MenuItem username = menu.addItem(getUser(), null);
		username.setEnabled(false);
		
		DefaultView content = new DefaultView();
		this.addComponent(content);
		this.setExpandRatio(content, 1);
		
//		this.addComponent(new ArtikelErstellen());
//		this.setExpandRatio(this.getComponent(2), 1);
	}
	
	public static MainLayout getInstance()
	{
		if (instance == null)
		{
			instance = new MainLayout();
		}
		return instance;
	}

	@Override
	public void menuSelected(MenuItem selectedItem)
	{
		switch (selectedItem.getText())
		{
			case IConstants.MENU_ARTIKEL_NEU:
				ViewHandler.getInstance().switchView(ArtikelErstellen.class);
			break;
			case IConstants.MENU_LIEFERANT_NEW:
				ViewHandler.getInstance().switchView(LieferantErstellen.class);
			break;
//			case IConstants.MENU_MITARBEITER_NEU:
//				ViewHandler.getInstance().switchView(MitarbeiterErstellen.class);
//			break;
//			case IConstants.MENU_MITARBEITER_ANZEIGEN:
//				ViewHandler.getInstance().switchView(MitarbeiterAnzeigen.class);
//			break;
			case IConstants.MENU_ARTIKEL_ANZEIGEN:
				ViewHandler.getInstance().switchView(ArtikelAnzeigen.class);
			break;
			case IConstants.MENU_LIEFERANT_ANZEIGEN:
				ViewHandler.getInstance().switchView(LieferantAnzeigen.class);
			break;
//			case IConstants.MENU_MENGENEINHEIT_NEU:
//				ViewHandler.getInstance().switchView(MengeneinheitErstellen.class);
//			break;
			case IConstants.MENU_MENGENEINHEIT_ANZEIGEN:
				ViewHandler.getInstance().switchView(MengeneinheitenAnzeigen.class);
			break;
			
			case IConstants.MENU_KATEGORIE_ANZEIGEN:
				ViewHandler.getInstance().switchView(KategorienAnzeigen.class);
			break;
			case IConstants.MENU_LOGOUT:
				Application.getInstance().setUsername(null);
				instance = null;
				UI.getCurrent().setContent(new LoginForm());
			break;
			case IConstants.MENU_BESTELLUNG_NEW:
				ViewHandler.getInstance().switchView(BestellungAuswaehlen.class);
			break;
			case IConstants.MENU_BESTELLUNG_NEW_RANDOM:
				ViewHandler.getInstance().switchView(BestellungLieferantAuswaehlen.class);
			break;
			case IConstants.MENU_BESTELLUNG_ANZEIGEN:
				ViewHandler.getInstance().switchView(BestellungAnzeigen.class);
			break;
			default: 
				ViewHandler.getInstance().switchView(DefaultView.class);
			break;
		}
	}
	
	private String getUser()
	{
		return "Benutzer : " + Application.getInstance().getUsername();
	}
}

/**
 * Sebastian Walz
 */
package de.hska.awp.palaver2.gui.layout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.gui.view.FussnoteEinst;
import de.bistrosoft.palaver.gui.view.GeschmackEinst;
import de.bistrosoft.palaver.gui.view.KuchenrezeptAnlegen;
import de.bistrosoft.palaver.gui.view.KuchenrezeptAnzeigen;
import de.bistrosoft.palaver.gui.view.MenueAnlegen;
import de.bistrosoft.palaver.gui.view.MenueAnzeigenTabelle;
import de.bistrosoft.palaver.gui.view.Menueplan;
import de.bistrosoft.palaver.gui.view.MenueplanHistorie;
import de.bistrosoft.palaver.gui.view.RegelnAnzeigen;
import de.bistrosoft.palaver.gui.view.RezeptAnlegen;
import de.bistrosoft.palaver.gui.view.RezeptAnzeigenTabelle;
import de.bistrosoft.palaver.gui.view.RezeptartEinst;
import de.bistrosoft.palaver.gui.view.ZubereitungEinst;
import de.hska.awp.palaver.Application;
import de.hska.awp.palaver2.gui.view.ArtikelAnzeigen;
import de.hska.awp.palaver2.gui.view.ArtikelErstellen;
import de.hska.awp.palaver2.gui.view.BestellungAnzeigen;
import de.hska.awp.palaver2.gui.view.BestellungBearbeitenAuswaehlen;
import de.hska.awp.palaver2.gui.view.BestellungGenerieren;
import de.hska.awp.palaver2.gui.view.BestellungLieferantAuswaehlen;
import de.hska.awp.palaver2.gui.view.EmailOhneBestellung;
import de.hska.awp.palaver2.gui.view.KategorienAnzeigen;
import de.hska.awp.palaver2.gui.view.LieferantAnzeigen;
import de.hska.awp.palaver2.gui.view.LieferantErstellen;
import de.hska.awp.palaver2.gui.view.MengeneinheitenAnzeigen;
import de.hska.awp.palaver2.gui.view.MitarbeiterAnzeigen;
import de.hska.awp.palaver2.gui.view.MitarbeiterErstellen;
import de.hska.awp.palaver2.gui.view.NachrichtAnzeigen;
import de.hska.awp.palaver2.gui.view.RollenAnzeigen;
import de.hska.awp.palaver2.mitarbeiterverwaltung.domain.Rollen;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.ViewHandler;

/**
 * @author Sebastian
 *
 */
@SuppressWarnings("serial")
public class MainLayout extends VerticalLayout implements Command
{
	private HorizontalLayout		header = new HorizontalLayout();
	private static final Logger		log					= LoggerFactory.getLogger(MainLayout.class.getName());
	
	private MenuBar					menu = new MenuBar();
	@SuppressWarnings({ "deprecation", "unused" })
	public MainLayout()
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
		
		MenuItem mitarbeiterItem = menu.addItem(IConstants.MENU_MITARBEITER_HEADLINE, null);
		if (Application.getInstance().userHasPersmission(Rollen.ADMINISTRATOR))
		{
			mitarbeiterItem.addItem(IConstants.MENU_MITARBEITER_NEU, this);
		}
		mitarbeiterItem.addItem(IConstants.MENU_MITARBEITER_ANZEIGEN, this);
		
		MenuItem rezeptItem = menu.addItem(IConstants.MENU_REZEPT_HEADLINE,
				null);
		rezeptItem.addItem(IConstants.MENU_REZEPT_NEU, this);
		rezeptItem.addItem(IConstants.MENU_REZEPT_ANZEIGEN, this);
		MenuItem menue1Item = menu.addItem(IConstants.MENU_MENUE_HEADLINE,
				null);
		menue1Item.addItem(IConstants.MENU_MENUE_ANLEGEN, this);
		menue1Item.addItem(IConstants.MENU_MENUE_SUCHEN, this);
		
		MenuItem menuplanItem = menu.addItem(IConstants.MENU_MENUPLAN_HEADLINE,
				null);
		menuplanItem.addItem(IConstants.MENU_MENUPLAN_AKTUELL, this);
		menuplanItem.addItem(IConstants.MENU_MENUPLAN_HISTORIE, this);
		
		MenuItem kuchenverwaltungItem = menu.addItem(IConstants.MENU_KUCHENVERWALTUNG_HEADLINE,
				null);
		kuchenverwaltungItem.addItem(IConstants.MENU_KUCHENREZEPT_ANLEGEN, this);
		kuchenverwaltungItem.addItem(IConstants.MENU_KUCHENREZEPT_ANZEIGEN, this);
		
		MenuItem bestellungItem = menu.addItem(IConstants.MENU_BESTELLUNG_HEADLINE, null);
		if (Application.getInstance().userHasPersmission(Rollen.ADMINISTRATOR) || Application.getInstance().userHasPersmission(Rollen.BESTELLER))
		{
			bestellungItem.addItem(IConstants.MENU_BESTELLUNG_NEW_RANDOM, this);
			bestellungItem.addItem(IConstants.MENU_BESTELLUNG_GENERATE, this);
		}
		bestellungItem.addItem(IConstants.MENU_BESTELLUNG_BEARBEITEN, this);
		bestellungItem.addItem(IConstants.MENU_BESTELLUNG_ANZEIGEN, this);
		
		if (Application.getInstance().userHasPersmission(Rollen.ADMINISTRATOR))
		{
			MenuItem regelItem = menu.addItem(IConstants.MENU_REGEL, this);
		}
		MenuItem einstellungItem = menu.addItem(IConstants.MENU_EINSTELLUNGEN_HEADLINE, null);
		einstellungItem.addItem(IConstants.MENU_HEADER, this);
		einstellungItem.addItem(IConstants.MENU_MENGENEINHEIT_ANZEIGEN, this);
		einstellungItem.addItem(IConstants.MENU_KATEGORIE_ANZEIGEN, this);
//		einstellungItem.addItem(IConstants.MENU_ROLLEN_ANZEIGEN, this);
		einstellungItem.addItem(IConstants.MENU_FUSSNOTE, this);
		einstellungItem.addItem(IConstants.MENU_GESCHMACK, this);
		einstellungItem.addItem(IConstants.MENU_REZEPTART, this);
		einstellungItem.addItem(IConstants.MENU_ZUBEREITUNG, this);
		einstellungItem.addItem("Email", this);
		einstellungItem.addItem("Nachrichten", this);
		this.addComponent(menu);
		
		MenuItem logout = menu.addItem(IConstants.MENU_LOGOUT, this);
		MenuItem username = menu.addItem(getUser(), null);
		username.setEnabled(false);
		
//		DefaultView content = new DefaultView();
		NachrichtAnzeigen content = new NachrichtAnzeigen();
		this.addComponent(content);
		this.setExpandRatio(content, 1);
		
//		this.addComponent(new ArtikelErstellen());
//		this.setExpandRatio(this.getComponent(2), 1);
		
		if (UI.getCurrent().getSession().getBrowser().isTouchDevice())
		{
			setHeaderVisible(false);
		}
	}

	@Override
	public void menuSelected(MenuItem selectedItem)
	{
		if (selectedItem.getText().equals(IConstants.MENU_ARTIKEL_NEU))
		{
			ViewHandler.getInstance().switchView(ArtikelErstellen.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_LIEFERANT_NEW))
		{
			ViewHandler.getInstance().switchView(LieferantErstellen.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_MITARBEITER_NEU))
		{
			ViewHandler.getInstance().switchView(MitarbeiterErstellen.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_ARTIKEL_ANZEIGEN))
		{
			ViewHandler.getInstance().switchView(ArtikelAnzeigen.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_LIEFERANT_ANZEIGEN))
		{
			ViewHandler.getInstance().switchView(LieferantAnzeigen.class);
			
		}
		else if (selectedItem.getText().equals(IConstants.MENU_MITARBEITER_ANZEIGEN))
		{
			ViewHandler.getInstance().switchView(MitarbeiterAnzeigen.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_MENGENEINHEIT_ANZEIGEN))
		{
			ViewHandler.getInstance().switchView(MengeneinheitenAnzeigen.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_KATEGORIE_ANZEIGEN))
		{
			ViewHandler.getInstance().switchView(KategorienAnzeigen.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_LOGOUT))
		{
			UI.getCurrent().setContent(new LoginForm());
			UI.getCurrent().getSession().close();
			UI.getCurrent().close();
			log.info("**************************************************************");
			log.info("LOGOUT");
			log.info("**************************************************************");
		}
		else if (selectedItem.getText().equals(IConstants.MENU_BESTELLUNG_NEW_RANDOM))
		{
			ViewHandler.getInstance().switchView(BestellungLieferantAuswaehlen.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_BESTELLUNG_BEARBEITEN))
		{
			ViewHandler.getInstance().switchView(BestellungBearbeitenAuswaehlen.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_BESTELLUNG_ANZEIGEN))
		{
			ViewHandler.getInstance().switchView(BestellungAnzeigen.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_REZEPT_ANZEIGEN))
		{
			ViewHandler.getInstance().switchView(RezeptAnzeigenTabelle.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_REZEPT_NEU))
		{
			ViewHandler.getInstance().switchView(RezeptAnlegen.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_MENUPLAN_AKTUELL))
		{
			ViewHandler.getInstance().switchView(Menueplan.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_MENUPLAN_HISTORIE))
		{
			ViewHandler.getInstance().switchView(MenueplanHistorie.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_KUCHENREZEPT_ANLEGEN))
		{
			ViewHandler.getInstance().switchView(KuchenrezeptAnlegen.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_KUCHENREZEPT_ANZEIGEN))
		{
			ViewHandler.getInstance().switchView(KuchenrezeptAnzeigen.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_FUSSNOTE))
		{
			ViewHandler.getInstance().switchView(FussnoteEinst.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_GESCHMACK))
		{
			ViewHandler.getInstance().switchView(GeschmackEinst.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_REZEPTART))
		{
			ViewHandler.getInstance().switchView(RezeptartEinst.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_ZUBEREITUNG))
		{
			ViewHandler.getInstance().switchView(ZubereitungEinst.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_MENUE_ANLEGEN))
		{
			ViewHandler.getInstance().switchView(MenueAnlegen.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_MENUE_SUCHEN))
		{
			ViewHandler.getInstance().switchView(MenueAnzeigenTabelle.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_REGEL))
		{
			ViewHandler.getInstance().switchView(RegelnAnzeigen.class);
		}
		else if(selectedItem.getText().equals("Email")) //Temp
		{
			ViewHandler.getInstance().switchView(EmailOhneBestellung.class);
		}
		else if (selectedItem.getText().equals("Nachrichten"))
		{
			ViewHandler.getInstance().switchView(NachrichtAnzeigen.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_ROLLEN_ANZEIGEN))
		{
			ViewHandler.getInstance().switchView(RollenAnzeigen.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_BESTELLUNG_GENERATE))
		{
			ViewHandler.getInstance().switchView(BestellungGenerieren.class);
		}
		else if (selectedItem.getText().equals(IConstants.MENU_HEADER))
		{
			setHeaderVisible(!this.header.isVisible());
		}
		else 
		{
			ViewHandler.getInstance().returnToDefault();
		}
	}
	
	private String getUser()
	{
		return "Benutzer : " + Application.getInstance().getUsername();
	}
	
	private void setHeaderVisible(Boolean arg0)
	{
		this.header.setVisible(arg0);
	}
}

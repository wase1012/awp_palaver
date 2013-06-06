/**
 * Created by Sebastian Walz
 * 16.04.2013 14:54:32
 */
package de.hska.awp.palaver2.util;

public interface IConstants
{

	// DB Verbindung
//  Team1
//	String		DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/palaver?user=root&password=awp";
//  Team2
	String		DB_CONNECTION_URL = "jdbc:mysql://localhost:1433/palaver?user=root&password=root";
	
	// Menuepunkte mit Unterpunkten
	String		MENU_ARTIKEL_HEADLINE = "Artikel";
	String		MENU_ARTIKEL_NEU = "Artikel anlegen";
	String		MENU_ARTIKEL_ANZEIGEN = "Alle Artikel anzeigen";
	String		MENU_LIEFERANT_HEADLINE = "Lieferant";
	String		MENU_LIEFERANT_NEW = "Lieferant anlegen";
	String		MENU_LIEFERANT_ANZEIGEN = "Alle Lieferanten anzeigen";
	String		MENU_MITARBEITER_HEADLINE = "Mitarbeiter";
	String		MENU_MITARBEITER_ANZEIGEN = "Alle Mitarbeiter anzeigen";
	String      MENU_MITARBEITER_NEU = "Mitarbeiter anlegen";
	String		MENU_EINSTELLUNGEN_HEADLINE = "Einstellungen";
	String		MENU_BESTELLUNG_HEADLINE = "Bestellung";
	String		MENU_BESTELLUNG_NEW_RANDOM = "Neue Bestellung anlegen";
	String		MENU_BESTELLUNG_NEW = "Neue Bestellung";
	String 		MENU_BESTELLUNG_BEARBEITEN = "Bestellung bearbeiten";
	String 		MENU_BESTELLUNG_ANZEIGEN = "Alle Bestellungen anzeigen";
	String		MENU_BESTELLUNG_GENERATE = "Neue Bestellungen generieren";
	String		MENU_NACHRICHT_ANZEIGEN = "Nachrichten anzeigen";
	String		MENU_NACHRICHT_NEU = "Nachrichten erstellen";
	String		MENU_NACHRICHT_HEADLINE = "Nachrichten";
	String		MENU_MENGENEINHEIT_ANZEIGEN = "Mengeneinheiten";
	String		MENU_MENGENEINHEIT_NEU = "Mengeneinheit anlegen";
	String		MENU_KATEGORIE_ANZEIGEN = "Kategorien";
	String		MENU_ROLLEN_ANZEIGEN = "Rollen anzeigen";
	String 		MENU_REZEPT_HEADLINE = "Rezept";
	String 		MENU_REZEPT_NEU = "Anlegen";
	String 		MENU_REZEPT_ANZEIGEN = "Anzeigen";
	String 		MENU_REZEPT_AENDERN = "Ändern";
	String 		MENU_REZEPT_LOESCHEN = "Löschen";
	String 		MENU_MENUPLAN_HEADLINE = "Menüplan";
	String 		MENU_MENUPLAN_AKTUELL = "Aktueller Menüplan";
	String 		MENU_MENUPLAN_HISTORIE = "Menüplan-Historie";
	String		MENU_KUCHENVERWALTUNG_HEADLINE = "Kuchen";
	String		MENU_KUCHENREZEPT_ANLEGEN = "Kuchenrezept Anlegen";
	String		MENU_KUCHENREZEPT_ANZEIGEN = "Kuchenrezept Anzeigen";
	String 		MENU_FUSSNOTE = "Fussnoten";
	String 		MENU_GESCHMACK = "Geschmäcker";
	String 		MENU_REZEPTART = "Rezeptarten";
	String 		MENU_ZUBEREITUNG = "Zubereitungen";
	String		MENU_LOGOUT = "Logout";
	String 		MENU_MENUE_HEADLINE = "Menü";
	String 		MENU_MENUE_ANLEGEN = " Menü anlegen";
	String 		MENU_MENUE_SUCHEN = " Menü suchen";
	String 		MENU_REGEL = "Regeln";
	String 		MENU_HEADER = "Kopfzeile anzeigen/ausblenden";
	
	// Beschriftungen fuer Knoepfe
	String		BUTTON_SHOW_FILTER = "Filter anzeigen";
	String		BUTTON_HIDE_FILTER = "Filter ausblenden";
	String		BUTTON_SAVE = "Speichern";
	String		BUTTON_SENDEN = "Senden";
	String		BUTTON_DISCARD = "Verwerfen";
	String		BUTTON_ADD = "Hinzufügen";
	String		BUTTON_NEW = "Neu";
	String		BUTTON_EDIT = "Bearbeiten";
	String		BUTTON_OK = "OK";
	String		BUTTON_DELETE = "Löschen";
	String		BUTTON_JA = "Ja";
	String 		BUTTON_NEIN = "Nein";
	String		BUTTON_EMAILVERSAND = "Per Email versenden";
	String		BUTTON_DOWNLOAD = "Download";
	String		BUTTON_BACK = "Zurück";
	String		BUTTON_ALL_ORDERS = "Alle Bestellungen";
	String 		BUTTON_REZEPTSAVEASMENUE = "Als Menü Speichern";	
	String		BUTTON_SELECT = "Auswählen";

	
	// Icons fuer Knoepfe
	String		BUTTON_DELETE_ICON = "img/delete.ico";
	String		BUTTON_SAVE_ICON = "img/save.ico";
	String		BUTTON_DISCARD_ICON = "img/cross.ico";
	String 		BUTTON_ADD_ICON = "img/add.ico";
	String 		BUTTON_NEW_ICON = "img/new.ico";
	String 		BUTTON_EDIT_ICON = "img/edit.ico";
	String		BUTTON_OK_ICON = "img/check.ico";
	String		BUTTON_EXCEL_ICON = "img/excel1.ico";
//	String		BUTTON_EMAILVERSAND_ICON = "";
	
	String		ICON_YES = "img/tick_circle.ico";
	String		ICON_NO = "img/cross_circle.ico";
	String		ICON_DELETE = "img/Delete.ico";

	// Beschriftungen für Notifications
	String		INFO_REZEPT_SAVE = "Rezept wurde gespeichert!";
	String		INFO_REZEPT_MENUE_SAVE = "Menü kann nur als Haupgericht gespeichert werden!";
	String		INFO_REZEPT_ZUTATEN = "Bitte Zutaten wählen!";
	String		INFO_REZEPT_MENGE = "Bitte Menge kleiner 100.000 wählen!";
	String		INFO_REZEPT_REZEPTART = "Bitte Rezeptart wählen!";	
	String 		INFO_REZEPT_KOCH = "Bitte Koch wählen!";
	String		INFO_REZEPT_NAME = "Bitte Namen eingeben!";
	
	String		INFO_REGEL_SAVE = "Regel wurde gespeichert!";
	String		INFO_REGEL_DELETE = "Regel wurde gelöscht!";
	String 		INFO_REGEL_REGELTYP = "Bitte Regeltyp wählen!";
	String 		INFO_REGEL_OPERATOR = "Bitte Operator wählen!";

	// Beschriftung für Tabellenüberschriften
	String 		BESTELLGROESSE = "Bestellgröße";
}

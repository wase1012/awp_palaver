package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.bistrosoft.palaver.artikelverwaltung.domain.Artikel;
import de.bistrosoft.palaver.artikelverwaltung.service.Artikelverwaltung;
import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.GeschmackDAO;
import de.bistrosoft.palaver.data.MitarbeiterDAO;
import de.bistrosoft.palaver.data.RezeptartDAO;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Rezept;
import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
import de.bistrosoft.palaver.rezeptverwaltung.service.Rezeptverwaltung;
import de.bistrosoft.palaver.util.ViewHandler;

public class WinSelectArtikel extends Window {

	// private IndexedContainer container;
	Window subwindow = this;

	// private FilterTable table;
	// private TextField searchField = new TextField();
	VerticalLayout box = new VerticalLayout();

	// private Button showFilter;
	private Button btAdd;
	private TwinColSelect artikelcol = new TwinColSelect();
	
	private String artikelcolInput;

//	BeanItemContainer<Artikel> artikelContainer;

	private Table tblArtikel;

	// private static final String ARTIKEL = "name";

	public WinSelectArtikel(Table tblArtikel) {
        artikelcol.setMultiSelect(true);
        artikelcol.setSizeFull();
		initLayout();
		initArtikelList();
		this.tblArtikel = tblArtikel;
		// initSearch();

//		tblArtikel.getContainerDataSource().addItem();
	}

	private void initLayout() {
		final VerticalLayout box = new VerticalLayout();
		box.setSizeFull();
		box.setMargin(true);
		setContent(box);
		btAdd = new Button("Hinzufügen");
		box.addComponent(artikelcol);
		box.addComponent(btAdd);
		

//		btAdd.addClickListener(new ClickListener() {
//			
//
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 903679555643091354L;
//
//			@Override
//			public void buttonClick(ClickEvent event) {
////				extractSelected();
//			}
//		});
		// box.addComponent(searchField);
	}

	// private void initSearch() {
	//
	// // Info im Suchfeld setzen
	// searchField.setInputPrompt("Artikel suchen");
	//
	// // TextChangeEvent wird ausgelöst, wenn bei der Eingabe eine Pause ist
	// searchField.setTextChangeEventMode(TextChangeEventMode.LAZY);
	//
	// // ChangeListener hinzufügen
	// searchField.addTextChangeListener(new TextChangeListener() {
	// public void textChange(final TextChangeEvent event) {
	//
	// // Alle Filter entfernen
	// artikelContainer.removeAllContainerFilters();
	// // Nur den eingegeben Filter hinzufügen
	// artikelContainer.addContainerFilter(new ArtikelFilter(event
	// .getText()));
	// }
	// });
	// }
	//
	// // Klasse für MenueFilter
	// private class ArtikelFilter implements Filter {
	// private String needle;
	//
	// // Konstruktor für Menuefilter
	// public ArtikelFilter(String needle) {
	// // string in Kleinbuchstaben
	// this.needle = needle.toLowerCase();
	// }
	//
	// public boolean passesFilter(Object itemId, Item item) {
	// String haystack = ("" + item.getItemProperty(ARTIKEL).getValue())
	// .toLowerCase();
	// return haystack.contains(needle);
	// }
	//
	// public boolean appliesToProperty(Object id) {
	// return true;
	// }
	// }

	private void initArtikelList() {

		List<Artikel> artikel;
		try {
			artikel = Artikelverwaltung.getInstance().getAllArtikelName();
			for (Artikel e : artikel) {
				artikelcol.addItem(e.getId());
				artikelcol.setItemCaption(e.getId(), e.getName());
			}
		} catch (ConnectException | SQLException | DAOException e) {

			e.printStackTrace();
		}

		

		
		// showFilter = new Button("Filter anzeigen");
		//
		// table = new FilterTable();
		// table.setSizeFull();
		// table.setFilterBarVisible(false);
		// table.setFilterGenerator(new customFilter());
		// table.setFilterDecorator(new customFilterDecorator());
		//
		// BeanItemContainer<Artikel> container;
		// try {
		// container = new BeanItemContainer<Artikel>(Artikel.class,
		// Artikelverwaltung.getInstance().getAllArtikelName());
		// table.setContainerDataSource(container);
		// table.setVisibleColumns(new Object[] { "name" });
		// table.sort(new Object[] { "name" }, new boolean[] { true });
		// } catch (IllegalArgumentException | ConnectException | DAOException
		// | SQLException e) {
		// e.printStackTrace();
		// }

		// this.addComponent(showFilter);
		// this.setComponentAlignment(showFilter, Alignment.MIDDLE_RIGHT);
		// this.addComponent(table);
		// this.setExpandRatio(table, 1);

	}

//	private List<Artikel> extractSelected() {
//
//		List<Artikel> al = new ArrayList<>();
//		Set<Artikel> artikel = (Set<Artikel>) artikelcol.getValue();
//		for (Artikel a : artikel) {
//			al.add(a);
//		}
//
//		return al;
//	}
}

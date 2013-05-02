package de.bistrosoft.palaver.gui.view;

import java.sql.SQLException;
import java.util.List;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.bistrosoft.palaver.artikelverwaltung.domain.Artikel;
import de.bistrosoft.palaver.artikelverwaltung.service.Artikelverwaltung;
import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;

public class WinSelectArtikel extends Window {

	private IndexedContainer container;
	Window subwindow = this;

	private FilterTable table;
//	private TextField searchField = new TextField();
	VerticalLayout box = new VerticalLayout();

	private Button showFilter;
	private TwinColSelect artikelcol = new TwinColSelect();
	BeanItemContainer<Artikel> artikelContainer;

	private static final String ARTIKEL = "name";

	public WinSelectArtikel() {
		initLayout();
		initArtikelList();
//		initSearch();
	}

	private void initLayout() {
		final VerticalLayout box = new VerticalLayout();
		box.setSizeFull();
		box.setMargin(true);
		setContent(box);
		box.addComponent(artikelcol);
//		box.addComponent(searchField);

		container = new IndexedContainer();
		container.addContainerProperty("name", String.class, null);

	}

//	private void initSearch() {
//
//		// Info im Suchfeld setzen
//		searchField.setInputPrompt("Artikel suchen");
//
//		// TextChangeEvent wird ausgelöst, wenn bei der Eingabe eine Pause ist
//		searchField.setTextChangeEventMode(TextChangeEventMode.LAZY);
//
//		// ChangeListener hinzufügen
//		searchField.addTextChangeListener(new TextChangeListener() {
//			public void textChange(final TextChangeEvent event) {
//
//				// Alle Filter entfernen
//				artikelContainer.removeAllContainerFilters();
//				// Nur den eingegeben Filter hinzufügen
//				artikelContainer.addContainerFilter(new ArtikelFilter(event
//						.getText()));
//			}
//		});
//	}
//
//	// Klasse für MenueFilter
//	private class ArtikelFilter implements Filter {
//		private String needle;
//
//		// Konstruktor für Menuefilter
//		public ArtikelFilter(String needle) {
//			// string in Kleinbuchstaben
//			this.needle = needle.toLowerCase();
//		}
//
//		public boolean passesFilter(Object itemId, Item item) {
//			String haystack = ("" + item.getItemProperty(ARTIKEL).getValue())
//					.toLowerCase();
//			return haystack.contains(needle);
//		}
//
//		public boolean appliesToProperty(Object id) {
//			return true;
//		}
//	}

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
}

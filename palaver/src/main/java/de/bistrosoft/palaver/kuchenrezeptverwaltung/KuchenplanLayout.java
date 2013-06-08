package de.bistrosoft.palaver.kuchenrezeptverwaltung;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenplan;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.KuchenplanHasKuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.domain.Kuchenrezept;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Kuchenplanverwaltung;
import de.bistrosoft.palaver.kuchenrezeptverwaltung.service.Kuchenrezeptverwaltung;
import de.bistrosoft.palaver.util.CalendarWeek;
import de.bistrosoft.palaver.util.Week;
import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;

@SuppressWarnings("serial")
public class KuchenplanLayout extends CustomComponent {

	// Konstanten
	KuchenplanLayout instance = this;
	private static final int ROWS = 7;
	private static final int COLUMNS = 2;
	public GridLayout layout = null;
	private Kuchenplan kuchenplan = null;

	// Tabellen
	private Table itemMoTable;
	private Table itemDiTable;
	private Table itemMiTable;
	private Table itemDoTable;
	private Table itemFrTable;
	private Table itemSaTable;
	private Table itemSoTable;

	private FilterTable kuchenTable;

	List<KuchenplanHasKuchenrezept> tmpItemsMo = new ArrayList<KuchenplanHasKuchenrezept>();
	List<KuchenplanHasKuchenrezept> tmpItemsDi = new ArrayList<KuchenplanHasKuchenrezept>();
	List<KuchenplanHasKuchenrezept> tmpItemsMi = new ArrayList<KuchenplanHasKuchenrezept>();
	List<KuchenplanHasKuchenrezept> tmpItemsDo = new ArrayList<KuchenplanHasKuchenrezept>();
	List<KuchenplanHasKuchenrezept> tmpItemsFr = new ArrayList<KuchenplanHasKuchenrezept>();
	List<KuchenplanHasKuchenrezept> tmpItemsSa = new ArrayList<KuchenplanHasKuchenrezept>();
	List<KuchenplanHasKuchenrezept> tmpItemsSo = new ArrayList<KuchenplanHasKuchenrezept>();

	// BeanContainer
	private BeanItemContainer<Kuchenrezept> containerKuchen;
	private BeanItemContainer<KuchenplanHasKuchenrezept> containerKuchenplanHasKuchenrezeptMo;
	private BeanItemContainer<KuchenplanHasKuchenrezept> containerKuchenplanHasKuchenrezeptDi;
	private BeanItemContainer<KuchenplanHasKuchenrezept> containerKuchenplanHasKuchenrezeptMi;
	private BeanItemContainer<KuchenplanHasKuchenrezept> containerKuchenplanHasKuchenrezeptDo;
	private BeanItemContainer<KuchenplanHasKuchenrezept> containerKuchenplanHasKuchenrezeptFr;
	private BeanItemContainer<KuchenplanHasKuchenrezept> containerKuchenplanHasKuchenrezeptSa;
	private BeanItemContainer<KuchenplanHasKuchenrezept> containerKuchenplanHasKuchenrezeptSo;

	public Kuchenplan getKuchenplan() {
		return kuchenplan;
	}

	public void setKuchenplan(Kuchenplan kuchenplan) {
		this.kuchenplan = kuchenplan;
	}

	// Seitenlayout erstellen
	public KuchenplanLayout(int week, int year) {
		kuchenplan = null;
//		kuchenplan = Kuchenplanverwaltung.getInstance()
//				.getKuchenplanByWeekWithItems(new Week(week, year));
		if (kuchenplan == null) {
			kuchenplan = new Kuchenplan(new Week(week, year));
		}
		setSizeFull();

		// vertikales Layout anlegen
		HorizontalLayout outer = new HorizontalLayout();
		outer.setWidth("900px");
		setCompositionRoot(outer);

		// GridLayout erstellen
		layout = new GridLayout(COLUMNS, ROWS);
		layout.setWidth("400px");
		layout.setStyleName("menueplan-grid");

		// GridLayout zu Seitenlayout hinzufÃ¼gen
		outer.addComponent(layout);
		outer.setComponentAlignment(layout, Alignment.MIDDLE_LEFT);
		// outer.setExpandRatio(layout, 1);

		// Fülle Datumsspalte mit Wochentag und Datum
		ArrayList<GregorianCalendar> dates = CalendarWeek.getDatesOfWeek(week,
				year);
		for (int row = 0; row < ROWS; row++) {
			GregorianCalendar date = dates.get(row);
			String strDay = date.getDisplayName(Calendar.DAY_OF_WEEK, 2,
					Locale.GERMANY);

			String strDate = date.get(Calendar.DAY_OF_MONTH) + "."
					+ (date.get(Calendar.MONTH) + 1) + "."
					+ date.get(Calendar.YEAR);

			Label lbTmp = new Label("<div align=center><B>" + "\r\n" + strDay
					+ "\r\n" + strDate + "</B></div>", ContentMode.HTML);
			 lbTmp.setHeight("152px");
			 lbTmp.setWidth("149px");
			layout.addComponent(lbTmp, 0, row);
			layout.setComponentAlignment(lbTmp, Alignment.MIDDLE_CENTER);
		}

		// Füge Listen für KuchenplanItems ein
		itemMoTable = new Table();
		itemMoTable.setWidth("400px");
		itemMoTable.setHeight("150px");
		itemMoTable.setStyleName("palaverTable2");
		itemMoTable.setImmediate(true);
		itemMoTable.setColumnWidth("kuchenname", 300);
		itemMoTable.setColumnWidth("anzahl", 70);
		containerKuchenplanHasKuchenrezeptMo = new BeanItemContainer<KuchenplanHasKuchenrezept>(
				KuchenplanHasKuchenrezept.class);
		itemMoTable
				.setContainerDataSource(containerKuchenplanHasKuchenrezeptMo);
		itemMoTable.setVisibleColumns(new Object[] { "kuchenname", "anzahl" });
		itemMoTable.setEditable(true);

		itemDiTable = new Table();
		itemDiTable.setWidth("400px");
		itemDiTable.setHeight("150px");
		itemDiTable.setStyleName("palaverTable2");
		itemDiTable.setPageLength(16);
		itemDiTable.setImmediate(true);
		itemDiTable.setColumnWidth("kuchenname", 300);
		itemDiTable.setColumnWidth("anzahl", 70);
		containerKuchenplanHasKuchenrezeptDi = new BeanItemContainer<KuchenplanHasKuchenrezept>(
				KuchenplanHasKuchenrezept.class);
		itemDiTable
				.setContainerDataSource(containerKuchenplanHasKuchenrezeptDi);
		itemDiTable.setVisibleColumns(new Object[] { "kuchenname", "anzahl" });
		itemDiTable.setEditable(true);

		itemMiTable = new Table();
		itemMiTable.setWidth("400px");
		itemMiTable.setHeight("150px");
		itemMiTable.setStyleName("palaverTable2");
		itemMiTable.setPageLength(16);
		itemMiTable.setImmediate(true);
		itemMiTable.setColumnWidth("kuchenname", 300);
		itemMiTable.setColumnWidth("anzahl", 70);
		containerKuchenplanHasKuchenrezeptMi = new BeanItemContainer<KuchenplanHasKuchenrezept>(
				KuchenplanHasKuchenrezept.class);
		itemMiTable
				.setContainerDataSource(containerKuchenplanHasKuchenrezeptMi);
		itemMiTable.setVisibleColumns(new Object[] { "kuchenname", "anzahl" });
		itemMiTable.setEditable(true);

		itemDoTable = new Table();
		itemDoTable.setWidth("400px");
		itemDoTable.setHeight("150px");
		itemDoTable.setStyleName("palaverTable2");
		itemDoTable.setPageLength(16);
		itemDoTable.setImmediate(true);
		itemDoTable.setColumnWidth("kuchenname", 300);
		itemDoTable.setColumnWidth("anzahl", 70);
		containerKuchenplanHasKuchenrezeptDo = new BeanItemContainer<KuchenplanHasKuchenrezept>(
				KuchenplanHasKuchenrezept.class);
		itemDoTable
				.setContainerDataSource(containerKuchenplanHasKuchenrezeptDo);
		itemDoTable.setVisibleColumns(new Object[] { "kuchenname", "anzahl" });
		itemDoTable.setEditable(true);

		itemFrTable = new Table();
		itemFrTable.setWidth("400px");
		itemFrTable.setHeight("150px");
		itemFrTable.setStyleName("palaverTable2");
		itemFrTable.setPageLength(16);
		itemFrTable.setImmediate(true);
		itemFrTable.setColumnWidth("kuchenname", 300);
		itemFrTable.setColumnWidth("anzahl", 70);
		containerKuchenplanHasKuchenrezeptFr = new BeanItemContainer<KuchenplanHasKuchenrezept>(
				KuchenplanHasKuchenrezept.class);
		itemFrTable
				.setContainerDataSource(containerKuchenplanHasKuchenrezeptFr);
		itemFrTable.setVisibleColumns(new Object[] { "kuchenname", "anzahl" });
		itemFrTable.setEditable(true);

		itemSaTable = new Table();
		itemSaTable.setWidth("400px");
		itemSaTable.setHeight("150px");
		itemSaTable.setStyleName("palaverTable2");
		itemSaTable.setPageLength(16);
		itemSaTable.setImmediate(true);
		itemSaTable.setColumnWidth("kuchenname", 300);
		itemSaTable.setColumnWidth("anzahl", 70);
		containerKuchenplanHasKuchenrezeptSa = new BeanItemContainer<KuchenplanHasKuchenrezept>(
				KuchenplanHasKuchenrezept.class);
		itemSaTable
				.setContainerDataSource(containerKuchenplanHasKuchenrezeptSa);
		itemSaTable.setVisibleColumns(new Object[] { "kuchenname", "anzahl" });
		itemSaTable.setEditable(true);

		itemSoTable = new Table();
		itemSoTable.setWidth("400px");
		itemSoTable.setHeight("150px");
		itemSoTable.setStyleName("palaverTable2");
		itemSoTable.setPageLength(16);
		itemSoTable.setImmediate(true);
		itemSoTable.setColumnWidth("kuchenname", 300);
		itemSoTable.setColumnWidth("anzahl", 70);
		containerKuchenplanHasKuchenrezeptSo = new BeanItemContainer<KuchenplanHasKuchenrezept>(
				KuchenplanHasKuchenrezept.class);
		itemSoTable
				.setContainerDataSource(containerKuchenplanHasKuchenrezeptSo);
		itemSoTable.setVisibleColumns(new Object[] { "kuchenname", "anzahl" });
		itemSoTable.setEditable(true);

		layout.addComponent(itemMoTable, 1, 0);
		layout.addComponent(itemDiTable, 1, 1);
		layout.addComponent(itemMiTable, 1, 2);
		layout.addComponent(itemDoTable, 1, 3);
		layout.addComponent(itemFrTable, 1, 4);
		layout.addComponent(itemSaTable, 1, 5);
		layout.addComponent(itemSoTable, 1, 6);

		// Füge Liste mit allen Kuchen ein
		kuchenTable = new FilterTable();
		kuchenTable.setSizeFull();
		kuchenTable.setWidth("250px");
		kuchenTable.setStyleName("palaverTable2");
		kuchenTable.setFilterBarVisible(true);
		kuchenTable.setDragMode(com.vaadin.ui.CustomTable.TableDragMode.ROW);

		outer.addComponent(kuchenTable);
		outer.setComponentAlignment(kuchenTable, Alignment.MIDDLE_RIGHT);

		// Drag&Drop Kuchenliste
		kuchenTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Kuchen löschen
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				KuchenplanHasKuchenrezept selected = (KuchenplanHasKuchenrezept) t
						.getData("itemId");
				Table tSource = (Table) t.getSourceComponent();
				@SuppressWarnings("unchecked")
				BeanItemContainer<KuchenplanHasKuchenrezept> bicSource = (BeanItemContainer<KuchenplanHasKuchenrezept>) t
						.getSourceComponent();
				@SuppressWarnings("unchecked")
				List<KuchenplanHasKuchenrezept> liSource = (List<KuchenplanHasKuchenrezept>) t
						.getSourceComponent();
				bicSource.removeItem(selected);
				liSource.remove(selected);
				// containerArtikel.addItem(selected.getArtikel());
				// artikelTable.markAsDirty();
				tSource.markAsDirty();
			}
		});

		// Drag&Drop Montag
		itemMoTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		itemMoTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Kuchen hinzufügen
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof Kuchenrezept) {
					Kuchenrezept selected = (Kuchenrezept) t.getData("itemId");
					// containerArtikel.removeItem(selected);
					KuchenplanHasKuchenrezept tmp = new KuchenplanHasKuchenrezept(
							selected, 1);
					tmpItemsMo.add(tmp);
					containerKuchenplanHasKuchenrezeptMo.addItem(tmp);
				}
				// artikelTable.markAsDirty();
				itemMoTable.markAsDirty();
			}
		});

		// Drag&Drop Dienstag
		itemDiTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		itemDiTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Kuchen hinzufügen
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof Kuchenrezept) {
					Kuchenrezept selected = (Kuchenrezept) t.getData("itemId");
					// containerArtikel.removeItem(selected);
					KuchenplanHasKuchenrezept tmp = new KuchenplanHasKuchenrezept(
							selected, 2);
					tmpItemsDi.add(tmp);
					containerKuchenplanHasKuchenrezeptDi.addItem(tmp);
				}
				// artikelTable.markAsDirty();
				itemDiTable.markAsDirty();
			}
		});

		// Drag&Drop Mittwoch
		itemMiTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		itemMiTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Kuchen hinzufügen
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof Kuchenrezept) {
					Kuchenrezept selected = (Kuchenrezept) t.getData("itemId");
					// containerArtikel.removeItem(selected);
					KuchenplanHasKuchenrezept tmp = new KuchenplanHasKuchenrezept(
							selected, 3);
					tmpItemsMi.add(tmp);
					containerKuchenplanHasKuchenrezeptMi.addItem(tmp);
				}
				// artikelTable.markAsDirty();
				itemMiTable.markAsDirty();
			}
		});

		// Drag&Drop Donnerstag
		itemDoTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		itemDoTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Kuchen hinzufügen
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof Kuchenrezept) {
					Kuchenrezept selected = (Kuchenrezept) t.getData("itemId");
					// containerArtikel.removeItem(selected);
					KuchenplanHasKuchenrezept tmp = new KuchenplanHasKuchenrezept(
							selected, 4);
					tmpItemsDo.add(tmp);
					containerKuchenplanHasKuchenrezeptDo.addItem(tmp);
				}
				// artikelTable.markAsDirty();
				itemDoTable.markAsDirty();
			}
		});

		// Drag&Drop Freitag
		itemFrTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		itemFrTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Kuchen hinzufügen
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof Kuchenrezept) {
					Kuchenrezept selected = (Kuchenrezept) t.getData("itemId");
					// containerArtikel.removeItem(selected);
					KuchenplanHasKuchenrezept tmp = new KuchenplanHasKuchenrezept(
							selected, 5);
					tmpItemsFr.add(tmp);
					containerKuchenplanHasKuchenrezeptFr.addItem(tmp);
				}
				// artikelTable.markAsDirty();
				itemFrTable.markAsDirty();
			}
		});

		// Drag&Drop Samstag
		itemSaTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		itemSaTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Kuchen hinzufügen
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof Kuchenrezept) {
					Kuchenrezept selected = (Kuchenrezept) t.getData("itemId");
					// containerArtikel.removeItem(selected);
					KuchenplanHasKuchenrezept tmp = new KuchenplanHasKuchenrezept(
							selected, 6);
					tmpItemsSa.add(tmp);
					containerKuchenplanHasKuchenrezeptSa.addItem(tmp);
				}
				// artikelTable.markAsDirty();
				itemSaTable.markAsDirty();
			}
		});

		// Drag&Drop Sonntag
		itemSoTable.setDragMode(com.vaadin.ui.Table.TableDragMode.ROW);
		itemSoTable.setDropHandler(new DropHandler() {
			/**
			 * Prueft, ob das Element verschoben werden darf.
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			/**
			 * Kuchen hinzufügen
			 */
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
				if (t.getData("itemId") instanceof Kuchenrezept) {
					Kuchenrezept selected = (Kuchenrezept) t.getData("itemId");
					// containerArtikel.removeItem(selected);
					KuchenplanHasKuchenrezept tmp = new KuchenplanHasKuchenrezept(
							selected, 7);
					tmpItemsSo.add(tmp);
					containerKuchenplanHasKuchenrezeptSo.addItem(tmp);
				}
				// artikelTable.markAsDirty();
				itemSoTable.markAsDirty();
			}
		});

		try {
			containerKuchen = new BeanItemContainer<Kuchenrezept>(
					Kuchenrezept.class, Kuchenrezeptverwaltung.getInstance()
							.getAllKuchenrezepte());
			kuchenTable.setContainerDataSource(containerKuchen);
			kuchenTable.setVisibleColumns(new Object[] { "name" });
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void speichern() {
		
	}
}

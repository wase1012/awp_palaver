package de.bistrosoft.palaver.menueplanverwaltung;
import java.util.ArrayList;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import fi.jasoft.dragdroplayouts.DDGridLayout;

@SuppressWarnings("serial")
public class WinSelectMenue extends Window {
	Window subwindow = this;
	private Table contactList = new Table();
	private TextField searchField = new TextField();
	private Button addNewContactButton = new Button("Neu");
	private Button ok = new Button("Auswählen");
	private FormLayout editorLayout = new FormLayout();
	private FieldGroup editorFields = new FieldGroup();

	private ArrayList<TextField> textfields = new ArrayList<>();
	private static final String MENU = "Menübezeichnung";
	private static final String[] fieldNames = new String[] { MENU, "Portionenanzahl", "Eigentümer" };
	
	Component destComp;
	int destRow;
	int destCol;
	DDGridLayout menueGrid;

	IndexedContainer contactContainer = createDummyDatasource();

	public WinSelectMenue(DDGridLayout nMenueGrid,Component nDestComp,int nDestRow, int nDestCol) {
		menueGrid = nMenueGrid;
		destComp=nDestComp;
		destCol = nDestCol;
		destRow = nDestRow;
		initLayout();
		initContactList();
		initEditor();
		initSearch();
		initAddRemoveButtons();
		initOKButton();
	}
	
	private void initLayout() {

		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		setContent(splitPanel);

		VerticalLayout leftLayout = new VerticalLayout();
		splitPanel.addComponent(leftLayout);
		splitPanel.addComponent(editorLayout);
		leftLayout.addComponent(contactList);
		HorizontalLayout bottomLeftLayout = new HorizontalLayout();
		leftLayout.addComponent(bottomLeftLayout);
		bottomLeftLayout.addComponent(searchField);
		bottomLeftLayout.addComponent(addNewContactButton);

		leftLayout.setSizeFull();

		leftLayout.setExpandRatio(contactList, 1);
		contactList.setSizeFull();

		bottomLeftLayout.setWidth("100%");
		searchField.setWidth("100%");
		bottomLeftLayout.setExpandRatio(searchField, 1);

		editorLayout.setMargin(true);
		editorLayout.setVisible(false);
	}

	private void initEditor() {

		for (String fieldName : fieldNames) {
			
			TextField field = new TextField(fieldName);
			textfields.add(field);
			editorLayout.addComponent(field);
			field.setWidth("100%");

			editorFields.bind(field, fieldName);
		}

		editorFields.setBuffered(false);
		editorLayout.addComponent(ok);
	}

	private void initSearch() {

		searchField.setInputPrompt("Menü suchen");

		searchField.setTextChangeEventMode(TextChangeEventMode.LAZY);

		searchField.addTextChangeListener(new TextChangeListener() {
				public void textChange(final TextChangeEvent event) {

					contactContainer.removeAllContainerFilters();
					contactContainer.addContainerFilter(new ContactFilter(event
							.getText()));
				}
		});
	}

	private class ContactFilter implements Filter {
		private String needle;

		public ContactFilter(String needle) {
			this.needle = needle.toLowerCase();
		}

		public boolean passesFilter(Object itemId, Item item) {
			String haystack = ("" + item.getItemProperty(MENU).getValue()).toLowerCase();
			return haystack.contains(needle);
		}

		public boolean appliesToProperty(Object id) {
			return true;
		}
	}
	
	private void initOKButton() {
	ok.addClickListener(new ClickListener() {
		public void buttonClick(ClickEvent event) {
			String titel = textfields.get(0).getValue();
			menueGrid.removeComponent(destComp);
			MenueComponent menue = new MenueComponent(titel, menueGrid, destRow, destCol);
//			menueGrid.removeComponent(destComp);
			menueGrid.addComponent(menue, destCol, destRow);
			menueGrid.setComponentAlignment(menue, Alignment.MIDDLE_CENTER);
			subwindow.close();
		}
	});
	}
	
	private void initAddRemoveButtons() {
		addNewContactButton.addClickListener(new ClickListener() {
			@SuppressWarnings("unchecked")
			public void buttonClick(ClickEvent event) {

				contactContainer.removeAllContainerFilters();
				Object contactId = contactContainer.addItemAt(0);

				contactList.getContainerProperty(contactId, MENU).setValue(
						"New");

				contactList.select(contactId);
			}
		});
	}

	private void initContactList() {
		contactList.setContainerDataSource(contactContainer);
		contactList.setVisibleColumns(new String[] { MENU });
		contactList.setSelectable(true);
		contactList.setImmediate(true);

		contactList.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				Object contactId = contactList.getValue();

				if (contactId != null)
					editorFields.setItemDataSource(contactList
							.getItem(contactId));

				editorLayout.setVisible(contactId != null);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private static IndexedContainer createDummyDatasource() {
		IndexedContainer ic = new IndexedContainer();

		for (String p : fieldNames) {
			ic.addContainerProperty(p, String.class, "");
		}

		String[] fnames = { "Kartoffelauflauf", "Bolognese", "Hackbraten", "Salat"};

		int s = fnames.length;
		for (int i = 0; i < s; i++) {
			Object id = ic.addItem();
			ic.getContainerProperty(id, MENU).setValue(fnames[i]);

		}

		return ic;
	}	

}

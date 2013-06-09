package de.bistrosoft.palaver.util;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class TwinColTouch extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3723143101691071320L;

	VerticalLayout vlMain = new VerticalLayout();
	// Komponenten für PC
	TwinColSelect tcs;

	// Komponenten für Touch
	private ListSelect ls;

	Boolean isTouch = false;

	public TwinColTouch() {

		setCompositionRoot(vlMain);
		vlMain.setSizeFull();

		isTouch = UI.getCurrent().getSession().getBrowser().isTouchDevice();

		if (isTouch) {
			layoutTouch();
		} else {
			layoutNonTouch();
		}
	}

	public TwinColTouch(String caption) {
		setCompositionRoot(vlMain);
		vlMain.setSizeFull();

		isTouch = UI.getCurrent().getSession().getBrowser().isTouchDevice();

		if (isTouch) {
			layoutTouch();
			ls.setCaption(caption);
		} else {
			layoutNonTouch();
			ls.setCaption(caption);
		}
	}

	private void layoutNonTouch() {
		tcs = new TwinColSelect();
		tcs.setWidth("100%");
		tcs.setImmediate(true);
		vlMain.addComponent(tcs);
	}

	private void layoutTouch() {
		ls = new ListSelect();
		ls.setWidth("100%");
		ls.setImmediate(true);
		vlMain.addComponent(ls);
	}

	public void removeAllItems() {
		if (isTouch) {
			ls.removeAllItems();
		} else {
			tcs.removeAllItems();
		}

	}

	public void addItem(Object item) {
		if (isTouch) {
			ls.addItem(item);
		} else {
			tcs.addItem(item);
		}
	}

	public void select(Object item) {
		if (isTouch) {
			ls.select(item);
		} else {
			tcs.select(item);
		}
		
	}

	public Object getValue() {
		if (isTouch) {
			return ls.getValue();
		} else {
			return tcs.getValue();
		}
		
	}
}

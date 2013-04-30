/**
 * Android
 * 18.04.2013 21:30:11
 */
package de.bistrosoft.palaver.util;

import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.gui.layout.MainLayout;
import de.bistrosoft.palaver.gui.layout.DefaultView;

public class ViewHandler {
	private static ViewHandler instance = null;

	private ViewHandler() {
		super();
	}

	public static ViewHandler getInstance() {
		if (instance == null) {
			instance = new ViewHandler();
		}
		return instance;
	}

	public void switchView(Class<? extends VerticalLayout> view) {
		try {
			if (view != null) {
				MainLayout.getInstance().removeComponent(
						MainLayout.getInstance()
								.getComponent(
										MainLayout.getInstance()
												.getComponentCount() - 1));
				MainLayout.getInstance().addComponent(view.newInstance());
				MainLayout.getInstance().setExpandRatio(
						MainLayout.getInstance()
								.getComponent(
										MainLayout.getInstance()
												.getComponentCount() - 1), 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			MainLayout.getInstance().addComponent(new DefaultView());
			MainLayout.getInstance().setExpandRatio(
					MainLayout.getInstance().getComponent(
							MainLayout.getInstance().getComponentCount() - 1),
					1);
		}
	}

	public void returnToDefault() {
		switchView(DefaultView.class);
	}
}

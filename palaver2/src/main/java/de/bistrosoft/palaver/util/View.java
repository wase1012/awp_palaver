package de.bistrosoft.palaver.util;

import com.vaadin.ui.ComponentContainer;

/**
 * @author Michael Marschall
 *
 */
public interface View extends ComponentContainer
{
	public void getViewParam(ViewData data);
}

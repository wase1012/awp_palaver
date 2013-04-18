/**
 * Michael Marschall
 * 18.04.2013 21:22:04
 */
package de.bistrosoft.palaver.gui.layout;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Sebastian
 *
 */
@SuppressWarnings("serial")
public class DefaultView extends VerticalLayout
{
	public DefaultView()
	{
		super();
		this.setSizeFull();
		
		Image content = new Image(null, new ThemeResource("../img/palaverschild.jpeg"));
		this.addComponent(content);
		this.setComponentAlignment(content, Alignment.MIDDLE_CENTER);
	}
}
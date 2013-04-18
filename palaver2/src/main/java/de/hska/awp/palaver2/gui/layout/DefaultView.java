/**
 * Created by Sebastian
 * 17.04.2013 - 13:37:37
 */
package de.hska.awp.palaver2.gui.layout;

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

/**
 * Michael Marschall
 * 18.04.2013 21:22:04
 */
package de.bistrosoft.palaver.gui.layout;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.util.View;
import de.bistrosoft.palaver.util.ViewData;


@SuppressWarnings("serial")
public class DefaultView extends VerticalLayout implements View
{
	public DefaultView()
	{
		super();
		this.setSizeFull();
		
		Image content = new Image(null, new ThemeResource("../img/palaverschild.jpeg"));
		this.addComponent(content);
		this.setComponentAlignment(content, Alignment.MIDDLE_CENTER);
	}

	@Override
	public void getViewParam(ViewData data) {
		// TODO Auto-generated method stub
		
	}
}
/**
 * 
 */
package de.hska.awp.palaver2.gui.layout;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

/**
 * @author Sebastian
 *
 */
@SuppressWarnings("serial")
public class LoginForm extends VerticalLayout
{
	private HorizontalLayout form = new HorizontalLayout();
	
	private Embedded logo;
	private TextField username = new TextField("Benutzername");
	private PasswordField password = new PasswordField("Password");
	
	private Button loginButton = new Button("Login");
	
	public LoginForm()
	{
		super();
		
		this.setSizeFull();
		form.setWidth("350px");
		form.setHeight("300px");
		form.setStyleName("palaver-login");
		form.setMargin(true);

		
		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		form.addComponent(content);
		form.setComponentAlignment(content, Alignment.MIDDLE_CENTER);

		this.addComponent(form);
		this.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
		
		logo = new Embedded(null, new ThemeResource("img/cafe_palaver_Logo.png"));
		content.addComponent(logo);
		
		VerticalLayout fields = new VerticalLayout();
		fields.addComponent(username);
		fields.addComponent(password);
		fields.addComponent(loginButton);
		fields.setSpacing(true);
		fields.setWidth("300px");
		username.setWidth("100%");
		password.setWidth("100%");
		fields.setComponentAlignment(loginButton, Alignment.BOTTOM_RIGHT);
		
		content.addComponent(fields);
		
		loginButton.addClickListener(new ClickListener()
		{
			
			@Override
			public void buttonClick(ClickEvent event)
			{
				UI.getCurrent().setContent(MainLayout.getInstance());
			}
		});
		
		loginButton.setClickShortcut(KeyCode.ENTER);
	}
}

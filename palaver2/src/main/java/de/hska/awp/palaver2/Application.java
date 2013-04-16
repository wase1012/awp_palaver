package de.hska.awp.palaver2;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.dao.ConnectException;
import de.hska.awp.palaver2.dao.DAO;
import de.hska.awp.palaver2.dao.DAOException;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class Application extends UI
{
    @Override
    protected void init(VaadinRequest request) 
    {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);
        
        Button button = new Button("Datenbank Test");
        button.addClickListener(new Button.ClickListener() 
        {
            public void buttonClick(ClickEvent event) 
            {
                DAO dao = new DAO();
                try
				{
					ResultSet set = dao.get("SELECT 1");
					while(set.next())
					{
						layout.addComponent(new Label("Erfolg: " + set.getString(1)));
					}
				} 
                catch (ConnectException e)
				{
					layout.addComponent(new Label(e.toString()));
				} 
                catch (DAOException e)
				{
                	layout.addComponent(new Label(e.toString()));
				} 
                catch (SQLException e)
				{
                	layout.addComponent(new Label(e.toString()));
				}
            }
        });
        layout.addComponent(button);
    }
}

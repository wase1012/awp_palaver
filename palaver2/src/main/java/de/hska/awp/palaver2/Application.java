package de.hska.awp.palaver2;

import java.sql.SQLException;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.dao.ConnectException;
import de.hska.awp.palaver2.dao.DAOException;
import de.hska.awp.palaver2.dao.SystemDAO;
import de.hska.awp.palaver2.gui.layout.MainLayout;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class Application extends UI
{
    private static Application instance = null;
    
    /**
     * Zugriff auf "MAIN" Klasse und Session
     * @return Application
     */
    public static Application getInstance()
    {
    	if (instance == null)
    	{
    		instance = new Application();
    	}
    	return instance;
    }
	
	@Override
    protected void init(VaadinRequest request) 
    {
        final VerticalLayout layout = new VerticalLayout();
//        layout.setMargin(true);
        layout.setSizeFull();
        setContent(layout);
        
//        Button button = new Button("Datenbank Test");
//        button.addClickListener(new Button.ClickListener() 
//        {
//            public void buttonClick(ClickEvent event) 
//            {
//                SystemDAO dao = SystemDAO.getInstance();
//                try
//				{
//					layout.addComponent(new Label("Erfolg: " + dao.testConnection()));
//				} 
//                catch (ConnectException e)
//				{
//					layout.addComponent(new Label(e.toString()));
//				} 
//                catch (DAOException e)
//				{
//                	layout.addComponent(new Label(e.toString()));
//				} 
//                catch (SQLException e)
//				{
//                	layout.addComponent(new Label(e.toString()));
//				}
//            }
//        });
//        layout.addComponent(button);
        layout.addComponent(MainLayout.getInstance());
    }
}

/**
 * Michael Marschall
 * 18.04.2013 21:58:01
 */
package de.bistrosoft.palaver;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.bistrosoft.palaver.gui.layout.MainLayout;

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

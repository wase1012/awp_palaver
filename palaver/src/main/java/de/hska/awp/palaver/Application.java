package de.hska.awp.palaver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServletService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.hska.awp.palaver2.gui.layout.LoginForm;
import de.hska.awp.palaver2.gui.layout.MainLayout;

/**
 * The Application's "main" class
 */
@Theme("palaver")
@SuppressWarnings("serial")
public class Application extends UI
{
    private static ThreadLocal<Application>	currentApplication	= new ThreadLocal<Application>();
    private static Application				instance = null;
    private static final Logger				log					= LoggerFactory.getLogger(Application.class.getName());
    
    private String 							username;
    
    private MainLayout						layout;
    
    /**
     * Zugriff auf "MAIN" Klasse und Session
     * @return Application
     */
    public static Application getInstance()
    {
    	return instance;
    }
    
    private static void setInstance(Application application)
	{
		if (getInstance() == null)
		{
			instance = application;
		}
	}
	
    /**
     * 
     */
	@Override
    protected void init(VaadinRequest request) 
    {
        setInstance(this);
        log.info("**************************************************************");
        log.info("New Session with following data:");
        log.info("IP : " + request.getRemoteAddr());
        log.info("**************************************************************");
		Page.getCurrent().setTitle("PalaverApp");
		
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
//        layout.addComponent(MainLayout.getInstance());
        layout.addComponent(new LoginForm());
    }
	
	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username)
	{
		log.info("LOGIN USER: " + username);
		this.username = username;
	}
	
	public MainLayout getLayout()
	{
		return layout;
	}
	
	public void login(String username)
	{
		setUsername(username);
		layout = new MainLayout();
	}
	
	@Override
	public void close()
	{
		log.info("Closing Application");
		super.close();

		super.getSession().close();
		getPage().setLocation("/Logout");
		instance = null;
	}

	public void onRequestStart(HttpServletRequest request, HttpServletResponse response)
	{
		currentApplication.set(this);
		log.info("IP : " +request.getRemoteAddr());
	}


	public void onRequestEnd(HttpServletRequest request, HttpServletResponse response)
	{
		currentApplication.set(null);
		currentApplication.remove();
	}
}
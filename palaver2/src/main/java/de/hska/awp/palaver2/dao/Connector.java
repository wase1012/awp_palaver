package de.hska.awp.palaver2.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Connector 
{
	public static Integer classid = 11;

	private Statement stmt;

	private Connection connection;
	
	public Connector(Statement stmt, Connection connection) 
	{
		super();
		this.stmt = stmt;
		this.connection = connection;
	}

	public Connector() {
		super();
	}

	public Statement getStmt() 
	{
		return stmt;
	}

	public Connection getConnection() 
	{
		return connection;
	}

	public void setConnection(Connection connection) throws SQLException 
	{
		this.connection = connection;
		this.stmt = connection.createStatement();
	}

	public void connect() throws ConnectException 
	{
//		try 
//		{
//			// Create a connection to the database
////		    connection = new SQLServerDriver().connect(url, new Properties());
////		    stmt = connection.createStatement();
//		} 
//		catch (SQLException e) 
//		{
//		    throw new ConnectException("Connection failed. URL: " + url);
//		}
		 String DATASOURCE_CONTEXT = "java:comp/env/jdbc/palaverDB";
		    
		    connection = null;
		    try {
		      Context initialContext = new InitialContext();
//		      if ( initialContext == null)
//		      {
//		    	  
//		      }
		      DataSource datasource = (DataSource)initialContext.lookup(DATASOURCE_CONTEXT);
		      if (datasource != null) {
		    	  connection = datasource.getConnection();
		      }
		      else 
		      {
		    	  
		      }
		    }
		    catch ( NamingException ex ) 
		    {
		    	throw new ConnectException("Connection failed.");
		    }
		    catch(SQLException ex)
		    {
		    	throw new ConnectException("Connection failed.");
		    }
	}

	public void disconnect() throws ConnectException 
	{
		try 
		{
			stmt.close();
			connection.close();
		} 
		catch (Exception e) 
		{
			throw new ConnectException("Problem while closing connection.");
		}
	}
}
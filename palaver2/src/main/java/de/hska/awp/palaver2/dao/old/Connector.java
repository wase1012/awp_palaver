package de.hska.awp.palaver2.dao.old;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.jdbc.Driver;

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

	public void connect(String url) throws ConnectException 
	{
		try 
		{
			// Create a connection to the database
		    connection = new Driver().connect(url, new Properties());
		    stmt = connection.createStatement();
		} 
		catch (SQLException e) 
		{
		    throw new ConnectException("Connection failed. URL: " + url);
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
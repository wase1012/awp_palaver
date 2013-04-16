/*
 * DAO.java
 *
 * created at 28.11.2012 09:12:11  by Sebastian Walz
 *
 * Copyright (c) 2012 SEEBURGER AG, Germany. All Rights Reserved.
 */
package de.hska.awp.palaver2.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.sun.rowset.*;

import javax.sql.rowset.CachedRowSet;

public class DAO
{
	private Connector 				conn;
	
	private Statement 				statement;
	
	public DAO()
	{
		super();
		conn = new Connector();
	}
	
	public synchronized void setConnection(Connection connection) throws SQLException
	{
		if (connection != null)
		{
			conn.setConnection(connection);
			statement = conn.getStmt();
		}
	}
	
	public synchronized Connection getConnection()
	{
		return conn.getConnection();
	}
	
	@SuppressWarnings("resource")
	public synchronized ResultSet get(String querry) throws ConnectException, DAOException, SQLException 
	{
		openConnection();

		ResultSet result = null;
		CachedRowSet cache = new CachedRowSetImpl();
		try 
		{
			result = statement.executeQuery(querry);
			cache.populate(result);	
		} 
		catch (Exception e) 
		{
			throw new DAOException("Statement error: " + querry + " caused by: " + e.toString());
		}
		finally 
		{
			closeConnection();
			if (result != null)
			{
				result.close();
			}	
		}
		return cache.getOriginal();
	}
	
	public synchronized void put(String querry) throws ConnectException, DAOException 
	{
		openConnection();

		try 
		{
			statement.executeUpdate(querry);
		} 
		catch (Exception e) 
		{
			throw new DAOException("Statement error: " + querry);
		}
		finally
		{
			closeConnection();	
		}
	}
	
	private void openConnection() throws ConnectException
	{
//		conn.connect(MessageFormat.format(IConstants.URL, properties.getHost(), properties.getPort(), 
//				properties.getDbname(), properties.getUsername(), properties.getPassowrd()));
//		statement = conn.getStmt();
	}
	
	private void closeConnection() throws ConnectException, DAOException
	{
		conn.disconnect();
		try
		{
			statement.close();
		}
		catch (SQLException e)
		{
			throw new DAOException(e.toString());
		}
	}
}
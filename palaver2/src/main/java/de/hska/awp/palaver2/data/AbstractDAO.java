/*
 * DAO.java
 *
 * created at 28.11.2012 09:12:11  by Sebastian Walz
 *
 */
package de.hska.awp.palaver2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.sun.rowset.*;

import javax.sql.rowset.CachedRowSet;

public abstract class AbstractDAO
{
	private Connector 				conn;
	
	private Statement 				statement;
	
	public AbstractDAO()
	{
		super();
		conn = new Connector();
	}
	
	@SuppressWarnings({ "resource", "restriction" })
	protected synchronized ResultSet getManaged(String querry) throws ConnectException, DAOException, SQLException 
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
	
	protected synchronized void putManaged(String querry) throws ConnectException, DAOException 
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
	
	protected ResultSet get(String querry) throws SQLException
	{
		ResultSet result = null;
		result = statement.executeQuery(querry);
		
		return result;
	}
	
	protected void openConnection() throws ConnectException
	{
		conn.connect();
		statement = conn.getStmt();
	}
	
	protected void closeConnection() throws ConnectException, DAOException
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
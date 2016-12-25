package com.ohadr.c3p0_test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

public class DbConnectionUserRunnable implements Runnable 
{
	private static Logger log = Logger.getLogger(DbConnectionUserRunnable.class);

	private DataSource dataSource;
	private Connection connection;
	private final int sleepTimeSeconds;

	public DbConnectionUserRunnable(DataSource dataSource, int sleepTimeSeconds)
	{
		this.dataSource = dataSource;
		this.sleepTimeSeconds = sleepTimeSeconds;
	}
	
	@Override
	public void run() 
	{
		try
		{
			connection = dataSource.getConnection();
			ResultSet rs = executeQuery();

			Thread.sleep(sleepTimeSeconds * 1000);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				connection.close();
				log.trace("connection is closed.");
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private ResultSet executeQuery() throws SQLException
	{
		String sql = "SELECT * FROM SERVICES";

		CallableStatement statement = null;
		statement = connection.prepareCall(sql);

		ResultSet result = null;

		result = statement.executeQuery();
		return result;
	}	

}

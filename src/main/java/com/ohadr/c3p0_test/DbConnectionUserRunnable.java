package com.ohadr.c3p0_test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DbConnectionUserRunnable implements Runnable 
{
	private DataSource dataSource;
	private Connection connection;

	public DbConnectionUserRunnable(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	@Override
	public void run() 
	{
		try
		{
			connection = dataSource.getConnection();
			ResultSet rs = executeQuery();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				connection.close();
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
		try
		{
			Thread.sleep(15000);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}	

}

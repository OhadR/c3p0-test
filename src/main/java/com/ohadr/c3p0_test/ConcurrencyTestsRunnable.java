package com.ohadr.c3p0_test;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

public class ConcurrencyTestsRunnable implements Runnable 
{
	private static Logger log = Logger.getLogger(ConcurrencyTestsRunnable.class);

	private DataSource dataSource;

	private boolean keepRunning;

	public ConcurrencyTestsRunnable(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	@Override
	public void run() 
	{
		keepRunning = true;
		
		log.info("thread is running");
		do{
			try
			{
				Connection connection = dataSource.getConnection();
				Thread.sleep(10);
				connection.close();
				Thread.sleep(100);
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
		}while(keepRunning);
	}
	
	public void stop()
	{
		keepRunning = false;
	}
}

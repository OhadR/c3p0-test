package com.ohadr.c3p0_test;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ohadr.common.types.c3p0.ConnectionPoolStatus;


@Component
public class Manager implements InitializingBean
{
	private static Logger log = Logger.getLogger(Manager.class);

	private final int THREAD_SLEEP_TIME_SECONDS = 15;

	@Autowired
	private DataSource  dataSource;

    

	@Override
	public void afterPropertiesSet() throws Exception
	{
//		addWorkout( new WorkoutMetadata(CftCalcConstants.ANNIE, CftCalcConstants.ANNIE, true) );
//		addWorkout( new WorkoutMetadata(CftCalcConstants.CINDY, CftCalcConstants.CINDY, true) );
//		addWorkout( new WorkoutMetadata(CftCalcConstants.BARBARA, CftCalcConstants.BARBARA, true) );
	}


	public void runThreads(int numThreads) 
	{
		log.info("running " + numThreads + " threads, each one sleeps " + THREAD_SLEEP_TIME_SECONDS + " secs...");
		for(int i = 0 ; i < numThreads; ++i)
		{
			Thread t = new Thread(new DbConnectionUserRunnable(dataSource, THREAD_SLEEP_TIME_SECONDS));
			t.start();
		}
		log.info(numThreads + " threads have started.");
	}


	private static ConnectionPoolStatus getConnectionPoolStatus(ComboPooledDataSource comboPooledDataSource)
	{
		ConnectionPoolStatus connectionPoolStatus = new ConnectionPoolStatus();
		try
		{
			connectionPoolStatus.dataSourceName = comboPooledDataSource.getDataSourceName();
			connectionPoolStatus.numBusyConnections = comboPooledDataSource.getNumBusyConnections();
			connectionPoolStatus.numBusyConnectionsAllUsers = comboPooledDataSource.getNumBusyConnectionsAllUsers();

			connectionPoolStatus.numIdleConnections = comboPooledDataSource.getNumIdleConnections();
			connectionPoolStatus.numIdleConnectionsAllUsers = comboPooledDataSource.getNumIdleConnectionsAllUsers();

			connectionPoolStatus.numConnections = comboPooledDataSource.getNumConnections();
			connectionPoolStatus.numConnectionsAllUsers = comboPooledDataSource.getNumConnectionsAllUsers();
			
			connectionPoolStatus.numThreadsAwaitingCheckoutDefaultUser = comboPooledDataSource.getNumThreadsAwaitingCheckoutDefaultUser();
			
			connectionPoolStatus.numUnclosedOrphanedConnections = comboPooledDataSource.getNumUnclosedOrphanedConnections();
		}
		catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return connectionPoolStatus;
		
	}


	public ConnectionPoolStatus getDataSourceStatus()
	{
		ComboPooledDataSource comboPooledDataSource = (ComboPooledDataSource)dataSource;
		return getConnectionPoolStatus(comboPooledDataSource);
		
	}	
	

}
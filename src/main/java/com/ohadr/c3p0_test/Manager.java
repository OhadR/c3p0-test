package com.ohadr.c3p0_test;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ohadr.common.types.c3p0.ConnectionPoolStatus;
import com.ohadr.common.utils.c3p0.C3P0Utils;


@Component
public class Manager implements InitializingBean
{
	private static Logger log = Logger.getLogger(Manager.class);

	private final int THREAD_SLEEP_TIME_SECONDS = 15;
	Runnable ctr;

	@Autowired
	private DataSource  dataSource;

    

	@Override
	public void afterPropertiesSet() throws Exception
	{
		ctr = new ConcurrencyTestsRunnable(dataSource);
//		addWorkout( new WorkoutMetadata(CftCalcConstants.ANNIE, CftCalcConstants.ANNIE, true) );
//		addWorkout( new WorkoutMetadata(CftCalcConstants.CINDY, CftCalcConstants.CINDY, true) );
//		addWorkout( new WorkoutMetadata(CftCalcConstants.BARBARA, CftCalcConstants.BARBARA, true) );
	}


	public void runThreads(int numThreads) 
	{
		runThreads(numThreads, THREAD_SLEEP_TIME_SECONDS);
	}


    /**
     * this method runs 'numThreads' threads, each one takes a connection, sleeps 'sleepTimeSeconds' and releases the connection.
     * @param numThreads
     * @param sleepTimeSeconds
     * @param response
     * @throws Exception
     */
	public void runThreads(int numThreads, int sleepTimeSeconds)
	{
		log.info("running " + numThreads + " threads, each one sleeps " + sleepTimeSeconds + " secs...");
		for(int i = 0 ; i < numThreads; ++i)
		{
			Thread t = new Thread(new DbConnectionUserRunnable(dataSource, sleepTimeSeconds));
			t.start();
		}
		log.info(numThreads + " threads have started.");
	}	

	
	public ConnectionPoolStatus getDataSourceStatus()
	{
		ComboPooledDataSource comboPooledDataSource = (ComboPooledDataSource)dataSource;
		return C3P0Utils.getConnectionPoolStatus(comboPooledDataSource);		
	}


	public void runConcurrencyTest(int numThreads)
	{
		for(int i = 0; i < numThreads; ++i)
		{
	        Thread t = new Thread(ctr);
	        t.start();	        
		}
		
	}
	
	public void stopConcurrencyTest()
	{
		((ConcurrencyTestsRunnable)ctr).stop();
	}
	
}
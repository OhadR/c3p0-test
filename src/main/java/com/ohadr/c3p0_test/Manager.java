package com.ohadr.c3p0_test;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class Manager implements InitializingBean
{
	private static Logger log = Logger.getLogger(Manager.class);

    

	@Override
	public void afterPropertiesSet() throws Exception
	{
//		addWorkout( new WorkoutMetadata(CftCalcConstants.ANNIE, CftCalcConstants.ANNIE, true) );
//		addWorkout( new WorkoutMetadata(CftCalcConstants.CINDY, CftCalcConstants.CINDY, true) );
//		addWorkout( new WorkoutMetadata(CftCalcConstants.BARBARA, CftCalcConstants.BARBARA, true) );
	}


	public void runThreads(int numThreads) 
	{
		for(int i = 0 ; i < numThreads; ++i)
		{
			Thread t = new Thread(new DbConnectionUserRunnable());
			t.start();
		}
	}

}
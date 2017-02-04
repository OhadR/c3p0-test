package com.ohadr.c3p0_test;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ohadr.c3p0.leak_use_case.AffiliateManager;
import com.ohadr.c3p0.leak_use_case.LeakTestRunnable;


@Component
public class LeakTestCaseRunner implements InitializingBean
{
	private static Logger log = Logger.getLogger(LeakTestCaseRunner.class);

	Runnable ltr;		//LeakTestRunnable
	private boolean keepRunning = true;

	@Autowired
	private AffiliateManager affiliateManager;

    

	@Override
	public void afterPropertiesSet() throws Exception
	{
		ltr = new LeakTestRunnable(affiliateManager);
	}


	public class Moshe
	{
		private List<Integer> strs = new ArrayList<Integer>();

		public Moshe(int size)
		{
			for(int i = 0; i < size; ++i)
			{
				strs.add(i);
			}
		}
		
		public List<Integer> getList() 
		{ 
			log.info("Moshe getList()");
			return strs; 
		}
		
	}
	/*
	 * runs the leak test in loop (for start, do not run many threads)
	 */
	public void runLeakTest() throws InterruptedException
	{
		do
		{
	        Thread t = new Thread(ltr);
	        t.start();	        
			Thread.sleep(3000);
		}while(keepRunning);
	}
	
	public void stopLeakTest()
	{
		keepRunning = false;
	}
}
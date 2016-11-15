package com.ohadr.c3p0_test;

public class DbConnectionUserRunnable implements Runnable 
{

	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		try
		{
			Thread.sleep(15000);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

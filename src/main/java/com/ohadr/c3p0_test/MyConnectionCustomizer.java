package com.ohadr.c3p0_test;

import java.sql.Connection;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import com.mchange.v2.c3p0.ConnectionCustomizer;

public class MyConnectionCustomizer implements ConnectionCustomizer
{
	private static Logger log = Logger.getLogger(MyConnectionCustomizer.class);
	
	//i use static map so its data will not be related to a specific object
	private static Map<Connection, Map.Entry<Date, String>> connections;

	public MyConnectionCustomizer() 
	{
		connections = new HashMap<Connection, Map.Entry<Date, String>>();
	}

	@Override
	public void onAcquire(Connection c, String parentDataSourceIdentityToken) throws Exception
	{
		log.info("onAcquire");
	}

	@Override
	public void onDestroy(Connection c, String parentDataSourceIdentityToken) throws Exception
	{
		log.info("onDestroy");
	}

	@Override
	public void onCheckOut(Connection c, String parentDataSourceIdentityToken) throws Exception
	{
		log.debug("onCheckOut");

		Map.Entry<Date, String> entry = new AbstractMap.SimpleEntry<Date, String>(new Date(), getThreadStackTraceAsString());
		connections.put(c, entry);
	}

	@Override
	public void onCheckIn(Connection c, String parentDataSourceIdentityToken) throws Exception
	{
		log.debug("onCheckIn");
		
		connections.remove(c);
	}
	
	private String getThreadStackTraceAsString()
	{
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StringBuffer sb = new StringBuffer();
		for(StackTraceElement ste : stacktrace)
		{
			sb.append(ste.toString());
		}
		return sb.toString();
	}
	
	public static final Map<Connection, Map.Entry<Date, String>> getConnectionsMap()
	{
        return Collections.unmodifiableMap( connections );
	}

	/**
	 * 
	 * @param minutes
	 * @return only the connections that exist in the map more that {minutes} 
	 */
	public static final Set<Map.Entry<Date, String>> getConnectionsMap(int minutes)
	{
		Set<Map.Entry<Date, String>> retVal = new HashSet<Map.Entry<Date, String>>();
		
		Map<Connection, Map.Entry<Date, String>> connectionsCopy = new HashMap<Connection, Map.Entry<Date, String>>( connections );
		Date now = new Date();
		for(Map.Entry<Date, String> value : connectionsCopy.values())
		{
			Date insertionDate = value.getKey();
			long insertionTimeMsecs = insertionDate.getTime();
			long milis = insertionTimeMsecs + TimeUnit.MINUTES.toMillis( minutes );
			Date insertionDatePlusInterval = new Date( milis ); 

			//if this connection is "checked out" more than <minutes> minutes: 
			if( now.after( insertionDatePlusInterval ) )
			{
				retVal.add(value);
			}
		}
		
		return retVal;

	}
}

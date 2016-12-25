package com.ohadr.c3p0_test;

import java.sql.Connection;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
		log.info("onCheckOut");

		Map.Entry<Date, String> entry = new AbstractMap.SimpleEntry<Date, String>(new Date(), getThreadStackTraceAsString());
		connections.put(c, entry);
	}

	@Override
	public void onCheckIn(Connection c, String parentDataSourceIdentityToken) throws Exception
	{
		log.info("onCheckIn");
		
		connections.remove(c);
	}
	
	private String getThreadStackTraceAsString()
	{
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StringBuffer sb = new StringBuffer();
		for(StackTraceElement ste : stacktrace)
		{
			sb.append(ste.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public static final Map<Connection, Map.Entry<Date, String>> getConnectionsMap()
	{
        return Collections.unmodifiableMap( connections );
	}

}

package com.ohadr.c3p0_test.web;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.ohadr.c3p0_test.Manager;
import com.ohadr.c3p0_test.MyConnectionCustomizer;
import com.ohadr.common.types.c3p0.ConnectionPoolStatus;
import com.ohadr.common.types.c3p0.ConnectionPoolStatusCollection;
import com.ohadr.common.utils.JsonUtils;


@Controller
public class WebController 
{
	private static Logger log = Logger.getLogger(WebController.class);

    @Autowired
    private Manager manager;


    @RequestMapping(value = "/runThreads", method = RequestMethod.GET)
    protected void getAllWorkoutsNames(
            @RequestParam int numThreads,
    		HttpServletResponse response) throws Exception
    {
    	manager.runThreads(numThreads);
    	String jsonResponse = "ohads";
    	response.getWriter().println( jsonResponse );    	
    }

    
    @RequestMapping("/ping")	
	protected void ping(
			HttpServletResponse response) throws Exception{
		log.info( "got to ping" );
		response.getWriter().println("ping response: pong");
	}
    
    @RequestMapping(value = "/connPoolStatus", method = RequestMethod.GET)
    protected void getDataSourceStatus(
    		HttpServletResponse response) throws Exception
    {
    	ConnectionPoolStatus status = manager.getDataSourceStatus();
    	ConnectionPoolStatusCollection coll = new ConnectionPoolStatusCollection();
    	coll.collection = new ArrayList<ConnectionPoolStatus>();
    	coll.collection.add(status);
    	String statusJson = JsonUtils.convertToJson(coll);
    	response.getWriter().println( statusJson );    	
    }
    
    @RequestMapping(value = "/connectionsMap", method = RequestMethod.GET)
    protected void getConnectionsMap(
    		HttpServletResponse response) throws Exception
    {
    	Map<Connection, String> map = MyConnectionCustomizer.getConnectionsMap();
    	String statusJson = JsonUtils.convertToJson( map );
    	response.getWriter().println( statusJson );    	
    }
}
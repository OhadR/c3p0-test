package com.ohadr.c3p0_test.web;

import java.util.ArrayList;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.ohadr.c3p0_test.LeakTestCaseRunner;
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

    @Autowired
    private LeakTestCaseRunner leakTestCaseRunner;

    /**
     * this method runs 'numThreads' threads, each one takes a connection, sleeps 'sleepTimeSeconds' and releases the connection.
     * @param numThreads
     * @param sleepTimeSeconds
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/runThreads", method = RequestMethod.GET)
    protected void getAllWorkoutsNames(
            @RequestParam int numThreads,
            @RequestParam("sleepTimeSeconds") Optional<Integer> sleepTimeSeconds,
            HttpServletResponse response) throws Exception
    {
    	if(sleepTimeSeconds.isPresent())
    	{
        	manager.runThreads(numThreads, sleepTimeSeconds.get());
    	}
    	else
    	{
        	manager.runThreads(numThreads);
    	}
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
    
    /**
     * 
     * @param minutes - Optional parameter (as of spring 4.1.1, using java.util.Optional)
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/connectionsMap", method = RequestMethod.GET)
    protected void getConnectionsMap(
            @RequestParam("minutes") Optional<Integer> minutes,
    		HttpServletResponse response) throws Exception
    {
    	String statusJson;
    	statusJson = minutes.isPresent() ?
    			JsonUtils.convertToJson( MyConnectionCustomizer.getConnectionsMap(minutes.get()) ) :
    			JsonUtils.convertToJson( MyConnectionCustomizer.getConnectionsMap() );
    	response.getWriter().println( statusJson );    	
    }

    @RequestMapping(value = "/runConcurrencyTest", method = RequestMethod.GET)
    protected void runConcurrencyTest(
            @RequestParam int numThreads,
            HttpServletResponse response) throws Exception
    {
       	manager.runConcurrencyTest(numThreads);

    	String jsonResponse = "Concurrent Test is running";
    	response.getWriter().println( jsonResponse );    	
    }
    
    @RequestMapping(value = "/stopConcurrencyTest", method = RequestMethod.GET)
    protected void stopConcurrencyTest(HttpServletResponse response) throws Exception
    {
       	manager.stopConcurrencyTest();

    	String jsonResponse = "Concurrent Test is stopping";
    	response.getWriter().println( jsonResponse );    	
    }

    @RequestMapping(value = "/runLeakTestCase", method = RequestMethod.GET)
    protected void runLeakTestCase(HttpServletResponse response) throws Exception
    {
    	leakTestCaseRunner.runLeakTestCase();

    	response.getWriter().println( "runLeakTestCase" );    	
    }
}
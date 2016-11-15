package com.ohadr.c3p0_test.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.ohadr.c3p0_test.Manager;


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
}
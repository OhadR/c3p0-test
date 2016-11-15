package com.ohadr.c3p0_test.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ohadr.c3p0_test.Manager;


@Controller
public class WebController 
{
	private static Logger log = Logger.getLogger(WebController.class);

    @Autowired
    private Manager manager;


    /**
     * 
     * @param workoutName
     * @param response
     * @throws IOException
     * /
    @RequestMapping(value = "/secured/getSortedTraineesByGradePerWorkout", method = RequestMethod.GET)
    protected void getSortedTraineesByGrade(
    		@RequestParam String workoutName,
            HttpServletResponse response) throws IOException 
    {
    	Collection<ITrainee> trainees = manager.getSortedTraineesByGradePerWorkout( workoutName );
    	String jsonResponse = Utils.convertToJson( trainees );
    	response.getWriter().println( jsonResponse );
    }*/

    @RequestMapping(value = "/getAllWorkoutsNames", method = RequestMethod.GET)
    protected void getAllWorkoutsNames(
    		HttpServletResponse response) throws Exception
    {
    	manager.runThreads(1000);
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
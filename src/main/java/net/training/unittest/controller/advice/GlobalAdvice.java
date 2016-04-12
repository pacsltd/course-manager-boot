package net.training.unittest.controller.advice;

import java.util.List;

import net.training.unittest.dao.EventDao;
import net.training.unittest.model.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

//Target all Controllers within specific packages
@ControllerAdvice("net.training.unittest.controller")
public class GlobalAdvice {

	@Autowired
	private EventDao mainDao;


	//TODO this should be a default global error for all controllers
	@ExceptionHandler(CannotGetJdbcConnectionException.class) 
	public ModelAndView handleAllException(Exception ex) {
		ModelAndView model = new ModelAndView("errorPage");
		model.addObject("errorMsg", "Unable to connect to Database !!");
		//LOG the error and what controller/method was running
		return model;

	}
	
	// this is just to ensure for every call we get the upcoming events
	@ModelAttribute("events")
	public List<Event> getUpcomingEvents(Model model) {
		List<Event> allEnabledEvents = mainDao.getAllEnabledEventsByDate();
		//TODO use a cache for the results to speed up  
		model.addAttribute("", allEnabledEvents);
		return allEnabledEvents;
	}



}

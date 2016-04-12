package net.training.unittest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class BootController {

		static final Logger LOGGER = LoggerFactory.getLogger(BootController.class);
		
	    @RequestMapping("/")
	    @ResponseBody
	    String home() {
	    	LOGGER.info("Blimey this is great fun !!");
	        return "Hello World = Phil Ere!";
	    }
}

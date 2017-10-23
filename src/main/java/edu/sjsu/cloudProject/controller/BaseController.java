package edu.sjsu.cloudProject.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jets3t.service.S3ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.sjsu.cloudProject.util.AWSUtils;

@Controller
public class BaseController {
	
	final static Logger logger = Logger.getLogger(BaseController.class);
	
	/***
	 * Base controller
	 */
	@RequestMapping("/")
	public ModelAndView welcome() {
		logger.info("in welcome function");
		
		ModelAndView mv = new ModelAndView("redirect:/listKeys");
		
		return mv;
	}
}

package edu.sjsu.cloudProject.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;

import edu.sjsu.cloudProject.model.S3Detail;
import edu.sjsu.cloudProject.model.User;
import edu.sjsu.cloudProject.service.S3DetailService;
import edu.sjsu.cloudProject.service.UserService;
import edu.sjsu.cloudProject.util.AWSUtils;



@Controller
public class UserController {
	final static Logger logger = Logger.getLogger(UserController.class);
	private UserService UserService;
	
	private S3DetailService s3DetailService;
	
	@Autowired(required=true)
	@Qualifier(value="S3DetailService")
	public void setS3DetailService(S3DetailService ps){
		this.s3DetailService = ps;
	}
	
	
	@Autowired(required=true)
	@Qualifier(value="UserService")
	public void setUserService(UserService ps){
		this.UserService = ps;
	}
	
	
	/***
	 * Controller to list all users in the systems
	 */
	@RequestMapping(value = "/Users", method = RequestMethod.GET)
	public ModelAndView listUsers(Model model,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("index");
		if(request.getSession().getAttribute("user") == null)
		{
			logger.error("Illegal access of listUsers");
			return mv;
		}
		
		model.addAttribute("listUsers", this.UserService.listUsers());
		return mv;
	}
	
	
	/***
	 * Controller to logout user session
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(@RequestParam(value = "username", required = true) String userName,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("index");
		if(request.getSession().getAttribute("user") != null && request.getSession().getAttribute("username").equals(userName))
		{
			logger.info("Sucessful Logged out Username: " + userName);
			request.getSession().setAttribute("user",null);
			request.getSession().setAttribute("username",null);
			mv.addObject("User", null);
		}
		
		return mv;
	}
	
	
	/***
	 * Controller to provide login page
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getLogin(Model model,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}
	
	
	/***
	 * Controller to login user session
	 */
	@RequestMapping(value= "/login" , method = RequestMethod.POST)
	public ModelAndView login(
			@RequestParam(value = "username", required = true) String userName,
			@RequestParam(value = "password", required = true) String password,
			HttpServletRequest request) {
		logger.info("in login function " + userName + " --- " + password);
		ModelAndView mv = new ModelAndView("login");
		try
		{
			
			
			User uTemp = this.UserService.getUserByName(userName, password);
			
			if(uTemp != null)
			{
				
				request.getSession().setAttribute("user",uTemp);
				request.getSession().setAttribute("username",userName);
				request.getSession().setAttribute("firstname",uTemp.getFirstName());
				request.getSession().setAttribute("lastname",uTemp.getLastName());
				request.getSession().setAttribute("userid",uTemp.getId());
				request.getSession().setAttribute("emailid",uTemp.getEmailId());
				
				logger.info("LoggedIN:" + uTemp);
				mv.addObject("statusLogin", "Logout");
				 mv = new ModelAndView("redirect:/listKeys");
			}
			else
			{
				request.getSession().setAttribute("user",null);
				logger.info("Unsucessful in Login Username: " + userName);
				mv = new ModelAndView("login");
				mv.addObject("nologin", "Incorrect username and password!");
				mv.addObject("username", userName);
				mv.addObject("password", password);
				return mv;
			}
			
			mv.addObject("User", uTemp);
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			//System.out.println(e.getMessage());
			mv.addObject("nologin", "Something went wrong, please try again!");
		}
		return mv;
	}
	
	
	/***
	 * Controller to provide signup page
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView getSignup(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("register");
		return mv;
	}
	
	
	/***
	 * Controller to singup
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView signUp(
			@RequestParam(value = "username", required = true) String userName,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "firstname", required = true) String firstName,
			@RequestParam(value = "lastname", required = true) String lastName,
			@RequestParam(value = "emailid", required = true) String emailId) {
		logger.info("in signup function");
		ModelAndView mv = new ModelAndView("index");
		logger.info(userName + " " +    password+ " " +  firstName+ " " +  lastName + " " + emailId);
		
		
		User uTemp = this.UserService.searchUserByUserName(userName);
		
		if(uTemp != null)
		{
			logger.info("Username already exists:" + userName);
			mv = new ModelAndView("register");
			mv.addObject("UsernameExists", "Username already exists!");
			mv.addObject("username", userName);
			mv.addObject("password", password);
			mv.addObject("firstname", firstName);
			mv.addObject("lastname", lastName);
			mv.addObject("emailid", emailId);
			return mv;
		}
		
		User uNew = new User( userName,  password,  firstName,  lastName, emailId);
		
		this.UserService.addUser(uNew);
		uTemp = null;
		uTemp = this.UserService.getUserByName(userName, password);
		
		if(uTemp!=null) {
			AWSUtils.createBucket(userName);
			logger.info("User and Bucket successfully created :" + uTemp);
		}
		
		
		return mv;
	}
	
	/***
	 * Controller to delete user
	 */
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public ModelAndView deleteUser(
			@RequestParam(value = "username", required = true) String userName,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("index");
		if(request.getSession().getAttribute("user") == null)
		{
			logger.error("Illegal access of deleteUser, Username:" + userName);
			return mv;
		}
		
		logger.info("in delete user function");
		//ModelAndView mv = new ModelAndView("index");
		logger.info("Deleting user: "+ userName );
		
		
		User uTemp = this.UserService.searchUserByUserName(userName);
		List<KeyVersion> keys = new ArrayList<KeyVersion>();
		
		
		List<S3Detail> sdList = s3DetailService.listS3Details(uTemp);
        logger.info("s3Details to delete: " + sdList.size());
        for(S3Detail s: sdList)
        {
        		s3DetailService.removeS3Detail(s.getId());
        		keys.add(new KeyVersion(userName + "/" + s.getKeyName()));
        }
        keys.add(new KeyVersion(userName + "/"));
		
		this.UserService.removeUser(uTemp.getId());
		 sdList = s3DetailService.listS3Details(uTemp);
		uTemp = this.UserService.searchUserByUserName(userName);
		if(uTemp==null && sdList == null)
		{
			AWSUtils.removeBucket(userName, keys);
			logger.info("User and Bucket successfully deleted :" + uTemp);
		}
		
		return mv;
	}
}

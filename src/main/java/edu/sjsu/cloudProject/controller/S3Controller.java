package edu.sjsu.cloudProject.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import edu.sjsu.cloudProject.model.S3Detail;
import edu.sjsu.cloudProject.model.User;
import edu.sjsu.cloudProject.service.S3DetailService;
import edu.sjsu.cloudProject.service.UserService;
import edu.sjsu.cloudProject.util.AWSUtils;

@Controller
public class S3Controller {
	final static Logger logger = Logger.getLogger(S3Controller.class);
	private S3DetailService s3DetailService;
	
	@Value("${cloudfronturl}")
	private String cloudfronturl;
	
	@Value("${basebucket}")
	private String basebucket;
	
	@Autowired(required=true)
	@Qualifier(value="S3DetailService")
	public void setS3DetailService(S3DetailService ps){
		this.s3DetailService = ps;
	}
	
	
	private UserService userService;
	
	@Autowired(required=true)
	@Qualifier(value="UserService")
	public void setUserService(UserService ps){
		this.userService = ps;
	}
	
	AmazonS3 s3client = null;
    	
	
	/***
	 * controller to list all keys for a username
	 */
	@RequestMapping("/listKeys")
	public ModelAndView listKeys(
			HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("index");
		if(request.getSession().getAttribute("user") == null)
		{
			logger.error("Illegal access of listKeys" );
			return mv;
		}
		
		logger.info("in listFiles function");
        try {
            logger.info("Started processing Listing objects");
            
            List<S3Detail> sdList = s3DetailService.listS3Details((User)request.getSession().getAttribute("user"));
            logger.info("sdList: " + sdList.size());
            for(S3Detail s: sdList)
            {
            		logger.info(s);
            }
            
            if(request.getSession().getAttribute("fileSize") != null)
            {
            		mv.addObject("fileSize", "1");
            		request.getSession().setAttribute("fileSize",null);
            }
            else
            {
            		mv.addObject("fileSize", "-1");
            }
            
            if(request.getSession().getAttribute("uploadError") != null)
            {
            		mv.addObject("uploadError", "1");
            		request.getSession().setAttribute("uploadError",null);
            }
            else
            {
            		mv.addObject("uploadError", "-1");
            }
            
            mv.addObject("sdList", sdList);
            mv.addObject("cfLink", cloudfronturl);
            
         } catch (AmazonServiceException ase) {
        	 	AWSUtils.printException(ase);
         } catch (AmazonClientException ace) {
        	 	AWSUtils.printException(ace);
         }
		
		return mv;
	}
	
	/***
	 * Controller to upload object to user's S3 location
	 */
	@RequestMapping(value = "/uploadKey",method = RequestMethod.POST )
	public ModelAndView uploadFiles(
			@RequestParam(value = "keyname", required = true) MultipartFile file,
			@RequestParam(value = "description", required = true) String description,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		
		ModelAndView mv = new ModelAndView("redirect:/listKeys");
		
		if(request.getSession().getAttribute("user") == null)
		{
			logger.error("Illegal access of uploadFiles"  );
			return mv;
		}
		
		//System.out.println("********** " + cloudfronturl + " ------------- " + basebucket);
		logger.info("in uploadKey function ");
		
        try {
        		logger.info("Started processing Uploaded Key");
        		
        		
        		if (!file.isEmpty()) {
        			try {
        				if(file.getSize() > 10485760)
        				{
        					
        					logger.error("file size more than 10 MB : "  +  file.getOriginalFilename() + ", file size: "+ file.getSize());
        					System.out.println("file size more than 10 MB : "  +  file.getOriginalFilename() + ", file size: "+ file.getSize());
        					request.getSession().setAttribute("fileSize",1);
            				return mv;
        				}
        				int ret = AWSUtils.uploadKey(request.getSession().getAttribute("username") + "",file );
        				
        				if(ret == -1)
        				{
        					logger.error("failed to upload: "  +  file.getName());
            				request.getSession().setAttribute("uploadError",1);
            				
            				return mv;
        				}
        				
        				
        				
        			} catch (Exception e) {
        			
        			
        				logger.error("failed to upload: "  +  file.getName());
        				request.getSession().setAttribute("uploadError",1);
        				
        				return mv;
        			
        				
        			}
        		
        		
        		
	        		User uTemp = (User)request.getSession().getAttribute("user");
	        		
	        		
	        		S3Detail sdTemp = s3DetailService.searchS3Detail(uTemp, file.getOriginalFilename());
		    		if(sdTemp != null)
		    		{
		    			sdTemp.setUpdateTime(DateTime.now().toDate());
		    			sdTemp.setDescription(description);
		    			s3DetailService.updateS3Detail(sdTemp);
		    		}
		    		else
		    		{
		    			sdTemp = new S3Detail();
		    			sdTemp.setUpdateTime(DateTime.now().toDate()); //check if null
		        		sdTemp.setUploadTime(DateTime.now().toDate());
		        		sdTemp.setUser(uTemp);
		        		sdTemp.setKeyName(file.getOriginalFilename());
		        		sdTemp.setDescription(description);
		        		s3DetailService.addS3Detail(sdTemp);
		    		}
	        		
        		}
        		else
        		{
        			logger.error("empty file: "  +  file.getName());
    				return mv;
    			
        		}
        		
         } catch (AmazonServiceException ase) {
        	 	AWSUtils.printException(ase);
	     } catch (AmazonClientException ace) {
	    	 	AWSUtils.printException(ace);
	     }
		
		
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

		
		return mv;
	}
	
	/***
	 * Controller to delete the object from user location
	 */
	@RequestMapping("/deleteKey")
	public ModelAndView deleteKey(
			@RequestParam(value = "keyname", required = true) String keyName,
			HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("redirect:/listKeys");
		
		if(request.getSession().getAttribute("user") == null)
		{
			logger.error("Illegal access of uploadFiles");
			return mv;
		}
		logger.info("Started processing deletekey");
		
		s3client = AWSUtils.getS3Client();
		basebucket = AWSUtils.checkBucket(request.getSession().getAttribute("username") + "");
		
        try {
        		User uTemp = (User)request.getSession().getAttribute("user");
		    s3client.deleteObject(new DeleteObjectRequest(basebucket ,  uTemp.getUsername() + "/" + keyName));
		    
		    
		    if(uTemp != null)
		    {
		    		S3Detail sdTemp = s3DetailService.searchS3Detail(uTemp, keyName);
		    		if(sdTemp != null)
		    		{
		    			s3DetailService.removeS3Detail(sdTemp.getId());
		    			logger.info("Deleted key!!");
		    		}
		    }
        		
         } catch (AmazonServiceException ase) {
        	 	AWSUtils.printException(ase);
        } catch (AmazonClientException ace) {
        		AWSUtils.printException(ace);
        }
        return mv;
	}
	
	
	
	
	
	
	
}

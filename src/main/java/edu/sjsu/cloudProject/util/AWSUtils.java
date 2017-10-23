package edu.sjsu.cloudProject.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;

import edu.sjsu.cloudProject.model.User;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

@Component
public class AWSUtils {
	final static Logger logger = Logger.getLogger(AWSUtils.class);
	private static AmazonS3 s3client = null;

	
	
	private  static String accessid;
	
	private  static String secretkey;
	
	private  static String cloudfrontId;
	
	private static String baseBucket;
	
	public static String getBaseBucket() {
		return baseBucket;
	}

	private static String replicationBucket;
	
	
	
	/***
	 * Setting properties
	 * from property file
	 */
	@Value("${accessid}")
	public  void setAccessid(String accessid) {
		AWSUtils.accessid = accessid;
		System.out.println("*************************** access id set: " + AWSUtils.accessid );
	}

	@Value("${secretkey}")
	public  void setSecretkey(String secretkey) {
		AWSUtils.secretkey = secretkey;
		System.out.println("*************************** secretkey set: " + AWSUtils.secretkey );
	}

	@Value("${cloudfrontid}")
	public   void setCloudfrontId(String cloudfrontId) {
		AWSUtils.cloudfrontId = cloudfrontId;
		System.out.println("*************************** cloudfrontid  set: " + AWSUtils.cloudfrontId );
	}

	@Value("${basebucket}")
	public  void setBaseBucket(String baseBucket) {
		AWSUtils.baseBucket = baseBucket;
		System.out.println("*************************** baseBucket set: " + AWSUtils.baseBucket );
	}
	
	@Value("${replicationBucket}")
	public  void setReplicationBucket(String replicationBucket) {
		AWSUtils.replicationBucket = replicationBucket;
		System.out.println("*************************** replicationBucket set: " + AWSUtils.replicationBucket );
	}
	
	
	/***
	 * Checking bucket which one is available 
	 * from replication/main
	 */
	public static String checkBucket(String userName)
	{
		S3Object object = null; 
		try
		{
			object = s3client.getObject(baseBucket,userName + "/");
			
		}
		catch(AmazonServiceException e)
		{
			try
			{
				logger.error("Not able to connect to: " + baseBucket + ", connecting to: " + replicationBucket);
				System.out.println("--Not able to connect to: " + baseBucket + ", connecting to: " + replicationBucket);
				String temp = baseBucket;
				baseBucket = replicationBucket;
				replicationBucket = temp;
				
				
				object = s3client.getObject(baseBucket,userName + "/");
				
				
			}
			catch(AmazonServiceException e1)
			{
				logger.error("**Not able to connect to: " + baseBucket + " also");
				System.out.println("**Not able to connect to: " + baseBucket + " also");
				printException(e1);
			}
			
		}
		return baseBucket;
	}
	
	/***
	 * getting S3 client for 
	 * TA acceleraton 
	 */
	public static AmazonS3 getS3ClientTA(String userName)
	{
		AmazonS3 s3client = getS3Client();
		checkBucket( userName);
		
		
			
		try
		{
			if(s3client == null)
			{
				s3client = new AmazonS3Client(new BasicAWSCredentials(accessid, secretkey));
				
				if(baseBucket.equals("cloudproject1"))
					s3client.setRegion(Region.getRegion(Regions.US_WEST_1));
				else
					s3client.setRegion(Region.getRegion(Regions.US_EAST_1));
				
				s3client.setS3ClientOptions(S3ClientOptions.builder().setAccelerateModeEnabled(true).build());
			}
			
			
			
			
		}
		catch (AmazonServiceException ase) {
	 		printException(ase);
	 		return null;
		} catch (AmazonClientException ace) {
		 	printException(ace);
		 	return null;
		}
	     return s3client;
	}
	
	/***
	 * getting S3 client for 
	 */
	public static AmazonS3 getS3Client()
	{
		try
		{
			if(s3client == null)
				s3client = new AmazonS3Client(new BasicAWSCredentials(accessid, secretkey));
			
			
			
			
		}
		catch (AmazonServiceException ase) {
	 		printException(ase);
	 		return null;
		} catch (AmazonClientException ace) {
		 	printException(ace);
		 	return null;
		}
	     return s3client;
	}
	
	
	/***
	 * Function to print exception
	 */
	public static void printException(Exception ex)
	{
		if(ex.getClass() == AmazonClientException.class)
		{
			logger.error("Error Message: " + ex.getMessage());
		}
		else if(ex.getClass() == AmazonServiceException.class)
		{
			AmazonServiceException ase = (AmazonServiceException)ex;
			logger.error("Error:    " + ase.getMessage());
			logger.error("Request ID:       " + ase.getRequestId());
		}
		else
			logger.error("Error Message: " + ex.getMessage());
			
	}
	
	
	/***
	 * Function to upload object at S3 location 
	 */
	public static int uploadKey(String username, MultipartFile file)
	{
		System.out.println("in UP: "+ username);
		AmazonS3 s3client = getS3ClientTA(username);
		
		byte[] bytes = null;
		try {
			bytes = file.getBytes();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		String serverPath = System.getProperty("catalina.home");
		File dir = new File(serverPath + File.separator + "tmpFiles");
		if (!dir.exists())
			dir.mkdirs();

		
		File serverFile = new File(dir.getAbsolutePath()
				+ File.separator + file.getName());
		BufferedOutputStream stream = null;
		try {
			stream = new BufferedOutputStream(
					new FileOutputStream(serverFile));
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		try {
			stream.write(bytes);
		
			stream.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

			logger.info("Server File Location="
				+ serverFile.getAbsolutePath());
		try {
			s3client.putObject(new PutObjectRequest(baseBucket ,  username + "/" + file.getOriginalFilename(), serverFile));
		}
		catch (AmazonServiceException ase) {
    	 		printException(ase);
    	 		return -1;
		} catch (AmazonClientException ace) {
    	 		printException(ace);
    	 		return -1;
		}
		serverFile.delete();
		return 1;
	}
	
	
	/***
	 * Function to create 
	 * bucket for new registered user
	 */
	public static int createBucket(String username)
	{
		
		
		System.out.println("Creating Bucket for User: " + username);
		try {
			getS3Client();
			checkBucket(username);
			if(s3client != null)
			{
				final InputStream inpStr = new InputStream() {
				      @Override
				      public int read() throws IOException {
				        return -1;
				      }
				    };
				    final ObjectMetadata objMeta = new ObjectMetadata();
				    objMeta.setContentLength(0L);
				s3client.putObject(new PutObjectRequest(baseBucket ,  username + "/" , inpStr, objMeta));
			}else
			{
				logger.error("Error fetching instance of S3client.");
				return -1;
			}
		}
		catch (AmazonServiceException ase) {
    	 		printException(ase);
    	 		return -1;
		} catch (AmazonClientException ace) {
    	 		printException(ace);
    	 		return -1;
		}
		logger.info("Created Bucket for User: " + username);
		return 1;
	}
	
	
	/***
	 * function to remove bucket 
	 */
	public static int removeBucket(String userName, List<KeyVersion> keys)
	{
		
		
		logger.info("Removing Bucket for User: " + userName);
		try {
			s3client = AWSUtils.getS3Client();
			checkBucket(userName);
			if(s3client != null)
			{
				DeleteObjectsRequest multiObjectDeleteReq =  new DeleteObjectsRequest(baseBucket);

				
				
				
				multiObjectDeleteReq.setKeys(keys);

				try {
				    com.amazonaws.services.s3.model.DeleteObjectsResult delObjRes = s3client.deleteObjects(multiObjectDeleteReq);
				    System.out.format("Successfully deleted all the %s items.\n", delObjRes.getDeletedObjects().size());
				    			
				} catch (com.amazonaws.services.s3.model.MultiObjectDeleteException e) {
					printException(e);
				}
				
				
			}else
			{
				logger.error("Error fetching instance of S3client.");
				return -1;
			}
		}
		catch (AmazonServiceException ase) {
    	 		printException(ase);
    	 		return -1;
		} catch (AmazonClientException ace) {
			printException(ace);
    	 		return -1;
		}
		logger.info("Deleted Bucket for User: " + userName);
		return 1;
	}
}

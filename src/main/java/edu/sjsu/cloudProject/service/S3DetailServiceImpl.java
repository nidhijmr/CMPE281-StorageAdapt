package edu.sjsu.cloudProject.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import edu.sjsu.cloudProject.dao.S3DetailDAO;
import edu.sjsu.cloudProject.dao.UserDAOImpl;
import edu.sjsu.cloudProject.model.S3Detail;
import edu.sjsu.cloudProject.model.User;

public class S3DetailServiceImpl implements S3DetailService{
	final static Logger logger = Logger.getLogger(S3DetailServiceImpl.class);
	private S3DetailDAO S3DetailDAO;

	public void setS3DetailDAO(S3DetailDAO S3DetailDAO) {
		this.S3DetailDAO = S3DetailDAO;
	}

	
	/***
	 * Function to add S3Details
	 */
	@Transactional
	public void addS3Detail(S3Detail p) {
		this.S3DetailDAO.addS3Detail(p);
	}

	/***
	 * Function to update S3Details
	 */
	@Transactional
	public void updateS3Detail(S3Detail p) {
		this.S3DetailDAO.updateS3Detail(p);
	}

	/***
	 * Function to List S3Details
	 */
	@Transactional
	public List<S3Detail> listS3Details(User user) {
		return this.S3DetailDAO.listS3Details(user);
	}

	/***
	 * Function to remove S3Details
	 */
	@Transactional
	public void removeS3Detail(int s3DetailId) {
		this.S3DetailDAO.removeS3Detail(s3DetailId);
	}
	
	
	/***
	 * Function to search S3Detail
	 */
	@Transactional
	public S3Detail searchS3Detail(User user, String keyName) {
		return this.S3DetailDAO.searchS3Detail(user, keyName);
	}
}

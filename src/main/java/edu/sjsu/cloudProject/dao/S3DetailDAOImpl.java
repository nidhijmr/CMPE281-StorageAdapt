package edu.sjsu.cloudProject.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import edu.sjsu.cloudProject.controller.S3Controller;
import edu.sjsu.cloudProject.model.S3Detail;
import edu.sjsu.cloudProject.model.User;
import edu.sjsu.cloudProject.service.S3DetailService;


public class S3DetailDAOImpl implements S3DetailDAO{
	final static Logger logger = Logger.getLogger(S3DetailDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	
	/***
	 * Function to add S3Details
	 */
	public void addS3Detail(S3Detail u) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(u);
		logger.info("S3Detail saved successfully, S3Detail Details: "+u);
		
	}

	/***
	 * Function to update S3Details
	 */
	public void updateS3Detail(S3Detail u) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(u);
		logger.info("S3Detail updated successfully, S3Detail Details: "+u);
		
	}

	/***
	 * Function to remove S3Details
	 */
	public void removeS3Detail(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		S3Detail p = (S3Detail) session.load(S3Detail.class, id);
		if(null != p){
			session.delete(p);
		}
		logger.info("S3Detail deleted successfully, S3Detail details="+p);
		
	}
	
	/***
	 * Function to list S3Details
	 */
	public List<S3Detail> listS3Details(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(S3Detail.class);
		List<S3Detail> s3DetailList = (List<S3Detail>) criteria.add(Restrictions.eq("user", user)).list();
		for(S3Detail u : s3DetailList){
			logger.info("S3Detail List::"+u);
		}
		return s3DetailList;
	}
	
	/***
	 * Function to search S3Details
	 */
	public S3Detail searchS3Detail(User user, String keyName) {
		
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(S3Detail.class);
		S3Detail s3DetailTemp = (S3Detail) criteria.add(Restrictions.eq("user", user))
				                .add(Restrictions.eq("keyName", keyName)).uniqueResult();
		
		return s3DetailTemp;
	}
	
	
}

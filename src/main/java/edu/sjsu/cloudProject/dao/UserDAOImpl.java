package edu.sjsu.cloudProject.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import edu.sjsu.cloudProject.model.User;

public class UserDAOImpl implements UserDAO{
	final static Logger logger = Logger.getLogger(UserDAOImpl.class);
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		
		this.sessionFactory = sf;
		System.out.println("sf: "+ this.sessionFactory);
	}
	
	
	/***
	 * Function to add user
	 */
	public void addUser(User u) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(u);
		logger.info("User saved successfully, User Details: "+u);
		
	}

	
	/***
	 * Function to update user
	 */
	public void updateUser(User u) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(u);
		logger.info("User updated successfully, User Details: "+u);
		
	}

	
	/***
	 * Function to list Users
	 */
	public List<User> listUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> userList = session.createQuery("from User").list();
		for(User u : userList){
			logger.info("User List::"+u);
		}
		return userList;
	}

	
	/***
	 * Function to get User by Username
	 */
	public User getUserByUserName(String userName,String password) {
		Session session = this.sessionFactory.getCurrentSession();		
		Criteria criteria = session.createCriteria(User.class);
		User u = (User) criteria.add(Restrictions.eq("username", userName))
				.add(Restrictions.eq("password", password))
		                             .uniqueResult();
		logger.info("User loaded successfully, User details="+u);
		return u;
	}

	
	/***
	 * Function to remove User
	 */
	public void removeUser(int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		User p = (User) session.load(User.class, userId);
		if(null != p){
			session.delete(p);
		}
		logger.info("User deleted successfully, User details="+p);
		
	}
	
	/***
	 * Function to search User by username
	 */
	public User searchUserByUserName(String userName) {
		Session session = this.sessionFactory.getCurrentSession();		
		Criteria criteria = session.createCriteria(User.class);
		User u = (User) criteria.add(Restrictions.eq("username", userName))
		                             .uniqueResult();
		logger.info("User loaded successfully, User details="+u);
		return u;
	}
	
	
}

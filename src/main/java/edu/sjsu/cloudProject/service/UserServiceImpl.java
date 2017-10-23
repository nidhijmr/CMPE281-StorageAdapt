package edu.sjsu.cloudProject.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import edu.sjsu.cloudProject.dao.UserDAO;
import edu.sjsu.cloudProject.model.User;

public class UserServiceImpl implements UserService{
	final static Logger logger = Logger.getLogger(UserServiceImpl.class);
	private UserDAO UserDAO;

	public void setUserDAO(UserDAO UserDAO) {
		
		this.UserDAO = UserDAO;
		System.out.println("Userdo: "+ this.UserDAO);
	}

	
	/***
	 * Function to add User
	 */
	@Transactional
	public void addUser(User p) {
		this.UserDAO.addUser(p);
	}

	
	/***
	 * Function to update User
	 */
	@Transactional
	public void updateUser(User p) {
		this.UserDAO.updateUser(p);
	}

	/***
	 * Function to list all users
	 */
	@Transactional
	public List<User> listUsers() {
		return this.UserDAO.listUsers();
	}

	/***
	 * Function to search user by username
	 */
	@Transactional
	public User getUserByName(String username,String password) {
		return this.UserDAO.getUserByUserName(username, password);
	}

	/***
	 * Function to remove User
	 */
	@Transactional
	public void removeUser(int userId) {
		this.UserDAO.removeUser(userId);
	}

	
	/***
	 * Function to seacrh User by username
	 */
	@Transactional
	public User searchUserByUserName(String username) {
		return this.UserDAO.searchUserByUserName(username);
	}
}

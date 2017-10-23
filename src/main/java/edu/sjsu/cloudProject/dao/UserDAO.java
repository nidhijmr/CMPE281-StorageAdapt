package edu.sjsu.cloudProject.dao;

import java.util.List;

import edu.sjsu.cloudProject.model.User;

public interface UserDAO {
	public void addUser(User p);
	public void updateUser(User p);
	public List<User> listUsers();
	public User getUserByUserName(String userName,String password);
	public void removeUser(int userId);
	public User searchUserByUserName(String userName);
}

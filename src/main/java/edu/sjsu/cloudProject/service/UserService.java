package edu.sjsu.cloudProject.service;

import java.util.List;

import edu.sjsu.cloudProject.model.User;

public interface UserService {
	public void addUser(User p);
	public void updateUser(User p);
	public List<User> listUsers();
	public User getUserByName(String username,String password);
	public void removeUser(int userId);
	public User searchUserByUserName(String username);
}

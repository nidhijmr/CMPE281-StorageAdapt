package edu.sjsu.cloudProject.dao;

import java.util.List;

import edu.sjsu.cloudProject.model.S3Detail;
import edu.sjsu.cloudProject.model.User;

public interface S3DetailDAO {
	public void addS3Detail(S3Detail p);
	public void updateS3Detail(S3Detail p);
	public List<S3Detail> listS3Details(User user);
	public void removeS3Detail(int id);
	public S3Detail searchS3Detail(User user,String keyName);
}

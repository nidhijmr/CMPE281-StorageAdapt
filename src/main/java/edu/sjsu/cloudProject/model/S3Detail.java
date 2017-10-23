package edu.sjsu.cloudProject.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;


/***
 * Model for S3Details
 */
@Entity
@Table(name="s3detail")
public class S3Detail {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "userId")
	private User user;
	
	@Column(name="uploadtime", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadTime;
	
	@Column(name="updateTime", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	
	
	
	@Column(name="description")
	private String description;
	
	@Column(name="keyname")
	private String keyName;
	
	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString()
	{
		return "id: " + id + ", username: " + user.getUsername() + ", UploadTime: " + uploadTime 
				+ ", UpdateTime: " + updateTime +  
				", Description: " + description + ", keyname: " + keyName;
	}
}

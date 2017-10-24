# CMPE 281: cloudProject-1 StorageAdapt
*	University Name: http://www.sjsu.edu/ 
*	Course: [Cloud Technologies](http://info.sjsu.edu/web-dbgen/catalog/courses/CMPE281.html)
*	Professor: [Sanjay Garje](https://www.linkedin.com/in/sanjaygarje/)
*	ISA: [Divyankitha Urs](https://www.linkedin.com/in/divyankithaurs/)
*	Student: [Nidhi Jamar](https://www.linkedin.com/in/nidhi-jamar-9bb450bb/)

### Project Introduction
The application is the integrated solution for users to allow secure file access and storage from anywhere, with any       device. Users can create an account, sign in, upload new files, update existing ones, delete and download them.

![dashboard](https://user-images.githubusercontent.com/32632834/31925637-f8c49bd0-b83e-11e7-8ead-5087190a111b.png)

### Features of the application
* Register an account
![signup](https://user-images.githubusercontent.com/32632834/31925817-f9177e62-b83f-11e7-8f81-8902dfa108df.png)

* Sign into the application
![login](https://user-images.githubusercontent.com/32632834/31925834-11acb2bc-b840-11e7-8e27-91072f9f635d.png)

* Upload Files
![upload](https://user-images.githubusercontent.com/32632834/31925849-23779dcc-b840-11e7-8e31-cfb49725df15.png)

* List files uploaded by user and Download it
![listkeys](https://user-images.githubusercontent.com/32632834/31925889-4e1e9378-b840-11e7-9ddd-59e8dd4e8558.png)

* Delete files
![delete](https://user-images.githubusercontent.com/32632834/31925954-af16ce16-b840-11e7-8e31-3b34e7cc0677.png)

* Logout
![logout](https://user-images.githubusercontent.com/32632834/31925863-366aecd6-b840-11e7-9f59-0cda400a7184.png)

* ### Architecture Diagram
![archdiagram](https://user-images.githubusercontent.com/32632834/31925984-d93cb386-b840-11e7-8475-480e30309895.png)


### PreRequisites
* ### AWS Components to be setup
* EC2: The EC2 instance will be created and the .war file of the project will be deployed in the webapps folder of the Tomcat server. Further, AMI of this instance will be created which will be used in the launch configuration of the AutoScaling group.
* S3: This will be used to store the user's uploaded files. A base bucket will be created and inside it the files will be uploaded agaianst each user. The storage of this bucket will be Standard S3.
* S3-Infrequent Access: Another bucket will be created in different region whose stores will be S3-IA.
* S3-Transfer Acceleration: This will be enabled on the buckets for faster upload of files.
* Amazon Glacier: As per the Lifecycle policy, the files will be archived in this.
* RDS: A MySQl instance will be created in this, where data related to user and corresponding files uploaded will be saved.
* CloudFront: This has been configured for download of files.
* Classic Load Balancer: This has been configured to distribute the load between the EC2 intances created.
* AutoScaling Group: This has been configured to auto scale the EC2 instance for higher avalability and scalabitlity.
* Route 53: The IP address of the application will be resolved by this Domain Name Server.
* CloudWatch: To set up monitoring on the AutoScaling group instances.
* Lambda: On the termination/Launch of new EC2 instances, it invoked the Lambda function (created in python) which further invoked SNS Topic to send notifcation emails.
* SNS: Configured to send email to subscribers of the topic created in it.

### Configuration on Local Machine
* Softwares required to be setup : Maven project, Java 1.8, Apache Tomcat 8.5, Eclipse IDE, Db Visualizer

### Run project locally
* Download the code from this repository.
* Import it into the Eclipse as Maven Project.
* Create an IAM user in the AWS console and assign it the administrator access.
* Generate an access key for this user and keep a note of the access id and secret key.
* In the application.properties file of the project, update the user's access id, secret key, cloudfront url, cloudfront id, basebucket name, replication bucket name.
* Run the Maven clean and install on the project.
* Inside the target folder, a .war file has been created.
* Copy this .war file into the Tomcat webapps folder.
* Start the Tomcat server using command sudo $CATALINA_HOME/bin/startup.sh
* The application will be accessed at http://localhost:8080.
* Once it runs fine here, it can be deployed to the EC2 instance.






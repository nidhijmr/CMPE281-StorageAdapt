<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>StorageAdapt</title>
  <!-- Bootstrap core CSS-->
  <link href="/cloudProject/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Custom fonts for this template-->
  <link href="/cloudProject/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <!-- Custom styles for this template-->
  <link href="/cloudProject/resources/css/sb-admin.css" rel="stylesheet">
  <script type="text/javascript">
            
            function alertFunction() {
            		return confirm('Are you sure you want to delete this document?');
            }
            
            function messageFunction(fileSize, uploadError)
            {
            
            		if(fileSize  == 1)
         		{
         			 alert('FileSize is more than 10 MB!');
         			 
         		}
            		if(uploadError == 1)
          		{
            			alert('Error uploading file to S3!');
          		}
            			
            			 
            }
        </script>
       
</head>

<body class="fixed-nav sticky-footer bg-dark" id="page-top" onload='javascript:messageFunction(${fileSize}, ${uploadError})'>
  <!-- Navigation-->

  <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
    <a class="navbar-brand" href="/cloudProject/"><img src="/cloudProject/resources/images/icon.png" alt="Smiley face" height="42" width="42"> <font size="5px"><b>StorageAdapt</b></font></a>
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
      <ul class="navbar-nav navbar-sidenav" id="exampleAccordion">
      <% if(request.getSession().getAttribute("user") != null){%>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Dashboard">
          <a class="nav-link" href="/cloudProject/">
            <i class="fa fa-fw fa-dashboard"></i>
            <span class="nav-link-text">Dashboard</span>
          </a>
        </li>
        
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Upload">
          <a class="nav-link" data-toggle="modal" data-target="#uploadKey">
            <i class="fa fa-fw fa-area-chart"></i>
            <span class="nav-link-text">Upload</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="List">
          <a class="nav-link" href="/cloudProject/listKeys">
            <i class="fa fa-fw fa-table"></i>
            <span class="nav-link-text">List</span>
          </a>
        </li>
        <%}%>
      </ul>
      <ul class="navbar-nav sidenav-toggler">
        <li class="nav-item">
          <a class="nav-link text-center" id="sidenavToggler">
            <i class="fa fa-fw fa-angle-left"></i>
          </a>
        </li>
      </ul>
      <ul class="navbar-nav ml-auto">
       	<li class="nav-item">
  		<% if(request.getSession().getAttribute("user") == null){%>
          <a class="nav-link" href="/cloudProject/login">
            <i class="fa fa-fw fa-sign-out"></i>Login</a>
          <%} 
  		else{%>
  		<a class="nav-link" data-toggle="modal" data-target="#exampleModal">
            <i class="fa fa-fw fa-sign-out"></i>Logout</a>
          <%} %>
        </li>
      </ul>
    </div>
  </nav>
  
  
  <div class="content-wrapper" >
  <% if(request.getSession().getAttribute("user") != null){%>
    <div class="container-fluid" id="welcomeDiv">
      <div class="row">
        <div class="col-12">
          <p><b>Username</b>: <%=request.getSession().getAttribute("username")%> &emsp;   <b>Email</b>: <%=request.getSession().getAttribute("emailid")%>  &emsp;   <b>Name</b>: <%=request.getSession().getAttribute("firstname")%> <%=request.getSession().getAttribute("lastname")%></p>
        </div>
      </div>
    </div>
    <div class="container-fluid" id="listDiv" >
      <!-- Breadcrumbs-->
      <div class="row">
        <div class="col-12">
         <div class="card mb-3">
        <div class="card-header">
          <i class="fa fa-table"></i> Document List</div>
        <div class="card-body">
          <div class="table-responsive">
          	<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
              <thead style="background-color: rgba(0,0,0,.03);">
                <tr>
                  <th>Key</th>
                  <th>Description</th>
                  <th>UploadTime</th>
                  <th>UpdateTime</th>
                  <th>Download</th>
                  <th>Update</th>
                  <th>Delete</th>
                </tr>
              </thead>
			 <tbody>
				<c:forEach var="listValue" items="${sdList}">
					<tr>
		                  <td>${listValue.keyName}</td>
		                  <td>${listValue.description}</td>
		                  <td>${listValue.uploadTime}</td>
		                  <td>${listValue.updateTime}</td>
		                  <td><a href="http://${cfLink}/<%=request.getSession().getAttribute("username")%>/${listValue.keyName}">Download</a></td>
		                  <td><a data-toggle="modal" data-target="#uploadKey" href="">Update</a></td>
		                  <td><a onclick=" return alertFunction();" href="/cloudProject/deleteKey?keyname=${listValue.keyName}">Delete</a></td>
                		</tr>
				</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
		</div>
		</div>
        </div>
      </div>
    
    <%}
    else{%>
    
        <div class="text-center" >
        		<font size="6" ><b>	<i>Welcome to StorageAdapt </i></b></font> <br/>
       		<font size="4" ><i>StorageAdapt is an integrated solution that enables secure file access and storage from anywhere, with any device.</i></font>
       		<br/>
       		<br/>
    		</div>
    <%} %>
    </div>
    
    <!-- /.container-fluid-->
    <!-- /.content-wrapper-->
    <footer class="sticky-footer">
      <div class="container">
        <div class="text-center">
          <small><i>Copyright @NidhiJamar Cloud Project 2017</i></small>
        </div>
      </div>
    </footer>
    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
      <i class="fa fa-angle-up"></i>
    </a>
    <!-- Logout Modal-->
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">—</span></button>
          </div>
          <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
          <div class="modal-footer">
            <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
            
            <a class="btn btn-primary" href="/cloudProject/logout?username=<%=request.getSession().getAttribute("username") %>">Logout</a>
          </div>
        </div>
      </div>
    </div>
    <!-- Upload Key Modal-->
    <div class="modal fade" id="uploadKey" tabindex="-1" role="dialog" aria-labelledby="uplaodKeyModalLabel" aria-hidden="true">
      <div class="modal-dialog" role="document">
      <form action="/cloudProject/uploadKey" method="post" enctype="multipart/form-data" >
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="uplaodKeyLabel">Upload document</h5>
            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">—</span>
            </button>
          </div>
          <div class="modal-body">
	          <div class="form-group">
	            <label for="upload">Select</label>
	            <input type="file" class="form-control" id="keyname" name="keyname" />
	          </div>
	          <div class="form-group">
	            <label for="description">Description</label>
	            <input class="form-control" id="description"  placeholder="description" name="description" />
	          </div>
          </div>
          <div class="modal-footer">
<!--             <button class="btn btn-secondary" type="button" data-dismiss="modal">Upload</button> -->
            <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
            <input type="submit" class="btn btn-primary btn-block" value="Upload" />
            </div>
        </div>
        </form>
      </div>
    </div>
    <!-- Bootstrap core JavaScript-->
    <script src="/cloudProject/resources/vendor/jquery/jquery.min.js"></script>
    <script src="/cloudProject/resources/vendor/popper/popper.min.js"></script>
    <script src="/cloudProject/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
    <!-- Core plugin JavaScript-->
    <script src="/cloudProject/resources/vendor/jquery-easing/jquery.easing.min.js"></script>
    <!-- Custom scripts for all pages-->
    <script src="/cloudProject/resources/js/sb-admin.min.js"></script>
    <script src="/cloudProject/resources/vendor/datatables/jquery.dataTables.js"></script>
    <script src="/cloudProject/resources/vendor/datatables/dataTables.bootstrap4.js"></script>
    
    <script src="/cloudProject/resources/js/sb-admin-datatables.min.js"></script>
  </div>
</body>

</html>

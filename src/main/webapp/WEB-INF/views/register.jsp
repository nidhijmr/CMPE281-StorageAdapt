<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
</head>

<body class="bg-dark">
  <div class="container">
    <div class="card card-register mx-auto mt-5">
      <div class="card-header">Register an Account</div>
      <div class="card-body">
        <form action="/cloudProject/signup" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input class="form-control" id="username"  aria-describedby="emailHelp" placeholder="Enter username" name="username" value="${username}" required="true">
            <label name="errorUsername"><font size="2" color="red"><i>${UsernameExists}</i></font></label>
          </div>
          <div class="form-group">
            <div class="form-row">
              <div class="col-md-6">
                <label for="firstname">First name</label>
                <input class="form-control" id="firstname" type="text" aria-describedby="nameHelp" placeholder="Enter first name" name="firstname" value="${firstname}" required="true"> 
              </div>
              <div class="col-md-6">
                <label for="lastname">Last name</label>
                <input class="form-control" id="lastname" type="text" aria-describedby="nameHelp" placeholder="Enter last name" name="lastname" value="${lastname}" required="true">
              </div>
            </div>
          </div>
          <div class="form-group">
            <div class="form-row">
              <div class="col-md-6">
                <label for="emailid">Email</label>
                <input class="form-control" id="emailid" type="email" placeholder="Email" name="emailid" value="${emailid}" required="true">
              </div>
            </div>
          </div>
          <div class="form-group">
            <div class="form-row">
              <div class="col-md-6">
                <label for="password">Password</label>
                <input class="form-control" id="password" type="password" placeholder="Password" name="password" value="${password}" required="true">
              </div>
            </div>
          </div>
          <input type="submit" class="btn btn-primary btn-block" value="Register" />
        </form>
        <div class="text-center">
        	 <a class="d-block small mt-1" href="/cloudProject/">Home</a>
          <a class="d-block small mt-1" href="/cloudProject/login">Login Page</a>
        </div>
      </div>
    </div>
  </div>
  <!-- Bootstrap core JavaScript-->
  <script src="/cloudProject/resources/vendor/jquery/jquery.min.js"></script>
  <script src="/cloudProject/resources/vendor/popper/popper.min.js"></script>
  <script src="/cloudProject/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
  <!-- Core plugin JavaScript-->
  <script src="/cloudProject/resources/vendor/jquery-easing/jquery.easing.min.js"></script>
</body>

</html>

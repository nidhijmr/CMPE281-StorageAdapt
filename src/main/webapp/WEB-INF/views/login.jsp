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
    <div class="card card-login mx-auto mt-5">
      <div class="card-header">Login</div>
      <div class="card-body">
        <form action="/cloudProject/login" method="post">
          <div class="form-group">
            <label for="exampleInputEmail1">Username</label>
            <input class="form-control" id="username" aria-describedby="emailHelp" placeholder="Enter username" name="username" required="true" value="${username}">
          </div>
          <div class="form-group">
            <label for="exampleInputPassword1">Password</label>
            <input class="form-control" id="password" type="password" placeholder="Password" name="password" required="true" value="${password}">
          </div>
          <label name="errorLogin"><font size="2" color="red"><i>${nologin}</i></font></label>
          <input type="submit" class="btn btn-primary btn-block" value="Login" />
        </form>
        <div class="text-center">
       
          <a class="d-block small mt-1" href="/cloudProject/">Home</a>
          
          	<a class="d-block small mt-1" href="/cloudProject/signup">Register an Account</a>
          	
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

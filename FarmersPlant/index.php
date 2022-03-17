
<!DOCTYPE html>
<html lang="en">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel= "stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.0/css/all.min.css">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!--Css-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.0/js/all.min.js" integrity="sha512-Cvxz1E4gCrYKQfz6Ne+VoDiiLrbFBvc6BPh4iyKo2/ICdIodfgc5Q9cBjRivfQNUXBCEnQWcEInSXsvlNHY/ZQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <link rel = "stylesheet" type= "text/css" href="Public/css/index.css">

    <title>Index</title>
</head>
    <body>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
        
        <!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">FarmersPlant</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDarkDropdown" aria-controls="navbarNavDarkDropdown" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDarkDropdown">
      <ul class="navbar-nav">
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDarkDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Admin
          </a>
          <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
            <li><a class="dropdown-item" href="viewTask.php">View Task</a></li>
            <li><a class="dropdown-item" href="report.php">View Grade Reports</a></li>
            <li><a class="dropdown-item" href="register.php">Add Admin</a></li>
            <li><a class="dropdown-item" href="farmerinfo.php">Farmers Info</a></li>
            <li><a class="dropdown-item" href="trainerinfo.php">Trainer info</a></li>
            <li><a class="dropdown-item" href="logout.php">Log out</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>

        <!-- Page Content-->
  <div class="container py-4">
  <header class="pb-3 mb-4 border-bottom">
        <a class="d-flex align-items-center text-dark text-decoration-none">
          <span class="fs-4">FarmersPlant</span>
        </a>
      </header>
  

  <div class="p-5 mb-4 bg-light rounded-3">
        <div class="container-fluid py-5">
          <h1 class="display-5 fw-bold">Welcome to Farmers Plant</h1>
          <p class="col-md-8 fs-4">FarmersPlant is a learning management system that was develop by APC & NU students for Filipino farmers.</p>
          <a class="btn btn-primary btn-lg" type="button" href="viewTask.php">View Tasks</a>
          <a class="btn btn-primary btn-lg" type="button" href="report.php">View Grade Reports</a>
        </div>
      <div class="row align-items-md-stretch">
        <div class="col-md-6">
          <div class="h-100 p-5 text-white bg-dark rounded-3">
            <h2>Farmer</h2>
            <p>Check Farmers Information</p>
            <a class="btn btn-outline-light" type="button" href="farmerinfo.php">Check Farmer's Info</a>
          </div>
        </div> 
        <div class="col-md-6">
          <div class="h-100 p-5 bg-light border rounded-3">
            <h2>Trainer</h2>
            <p>Check trainers Information now.</p>
            <a class="btn btn-outline-secondary" type="button" href="trainerinfo.php">check trainers info</a>
          </div>
        </div>   
      </div>
      <footer class="pt-3 mt-4 text-muted border-top">
        &copy; 2021
      </footer>
      </div>

      <div class="container-fluid">
        <div class="row jumbotron">
          <div class="col-xs-12 col-sm-12 col-md-9 col-lg-9 col-xl-10">
            <p class="lead">If you want to add a new admin for this website, you can add more administration through register to add more Administrator</p>  
          </div>
          <div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 col-xl-2">
            <a href="register.php" ><button type="button"  class="btn btn-outline-secondary btn-lg">Register</button></a>
          </div>
        </div>
      </div>
  </div>

 

        <!-- Page content -->
     
<!-- JavaScript Bundle with Popper -->

    </body>
</html>

<?php
include ("session.php");

?>
<html>

<form method="post">
    <head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Login</title>
		<!-- CSS only -->
	<link rel = "stylesheet" href="public/css/index.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">		
	
	</head>

    <body>
		<!-- JavaScript Bundle with Popper -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

	<section class="vh-100" style="background-color: beige;">
  <div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col-12 col-md-8 col-lg-6 col-xl-5">
        <div class="card shadow-2-strong" style="border-radius: 1rem;">
          <div class="card-body p-5 text-center">

            <h3 class="mb-5">Sign in</h3>

            <div class="form-outline mb-4">
              <input type="text" id="typeEmailX-2" class="form-control form-control-lg" name="usr"/>
              <label class="form-label" for="typeEmailX-2" >Username <?php if (isset($_POST['usr']))  ?> </label>
            </div>

            <div class="form-outline mb-4">
              <input type="password" id="typePasswordX-2" class="form-control form-control-lg" name="pass"/>
              <label class="form-label" for="typePasswordX-2">Password <?php if (isset($_POST['pass'])) ?></label>
            </div>

            <!-- Checkbox -->
            <div class="form-check d-flex justify-content-start mb-4">
              <input
                class="form-check-input"
                type="checkbox"
                value=""
                id="form1Example3"
              />
              <label class="form-check-label" for="form1Example3"> Remember password </label>
            </div>

            <button class="btn btn-primary btn-lg btn-block" type="submit" name="submit" value="Login">Login</button>

            <hr class="my-4">

          </div>
        </div>
      </div>
    </div>
  </div>
	</body>
</html>

<?php
   
   if($_SERVER['REQUEST_METHOD'] == "POST"){

   require('config.php');

   $usr = $_POST['usr'];
   $pass = $_POST['pass'];

   $conn = new mysqli($servername, $username, $password, $dbname);
	
	$sql = "SELECT username FROM admin_table WHERE username = '$usr' AND password = md5('$pass') Limit 1 ";
				$result = mysqli_query($conn,$sql);
				 $stmt = $conn->prepare($sql);
				 $stmt->execute();
				 $stmt->store_result();
				 $stmt->fetch();
				 $rnum = $stmt->num_rows;
			
                 if($rnum >= 1){
			
				 	session_start();
			        $_SESSION["username"] = $usr;
					$_SESSION["sID"] = "";
				print '<script>alert("Login Succesful")</script>';
                print '<script>window.location = "Index.php"</script>';
			

				}
				else{
					print '<script>alert("Wrong Email/Password")</script>';
					
				}
            }

?>
<html>
    <meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <head>
        <body>
        <form method="post">

<input type="text" name="name" placeholder="Full Name"> <?php if (isset($_POST['name'])) ?><br>
<input type="text" name="user" placeholder="Username"> <?php if (isset($_POST['user'])) ?><br>
<input type="text" name="pass" placeholder="Password"> <?php if (isset($_POST['pass'])) ?><br>
<input type="text" name="cpass" placeholder="Confirm Password"> <?php if (isset($_POST['cpass'])) ?><br>

<input type="submit" id="submit" name="submit" value="Register"><br>

        </body>
    </head>

</html>

<?php
include('session.php');

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $user = $_POST['user'];
    $name = $_POST['name'];
    $pass = $_POST['pass'];
include('config.php');


if ($_POST['pass'] != $_POST['cpass']) {
    

        print '<script>alert("Password is not match")</script>';
        
}
else{

    $sql = "INSERT INTO admin_table (username, password, full_name) VALUES ('$user', md5('$pass'), '$pass')";		
    $r = @mysqli_query ($conn, $sql); // Run the query.
    if ($r) { // If it ran OK.
    
        // Print a message:
        print '<script>alert("Register Succesful")</script>';
        print '<script>window.location = "register.php"</script>';

}

}
}

?>
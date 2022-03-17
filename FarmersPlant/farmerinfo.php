<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	 <!-- JavaScript Bundle with Popper -->
	 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <!--Css-->
	<title>trainerinfo</title>
</head>
<body>
	 <!-- JavaScript Bundle with Popper -->
	 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

</body>
</html>

<?php


//session_start();

include ("session.php");

	require ('config.php');
	
	
    $conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$sql = "SELECT * FROM users WHERE email is null";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    // output data of each row
echo "<table border = 2> ";	

	echo"<tr>
    <th> Farmer ID </th>
	<th> Name </th>
	<th> Place Enrolled </th> 
	<th> Birthday</th>


	</tr>";
	

    while($row = mysqli_fetch_assoc($result)) {
?>
		<tr>
		<td>
        <?php echo $row['id']?></td><td> 
		<?php echo $row['full_name']?></td><td> 
		<?php echo $row['place_enrolled']?></td><td> 
		<?php echo $row['bdate']?></td><td> 

        
		<a href="deleteF.php?id=<?php echo $row['id']; ?>">Delete</a></td>

		</tr>
		</div>
		
  <?php
  
}}
?>
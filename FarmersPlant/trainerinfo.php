<html>
<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

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

$sql = "SELECT * FROM users WHERE email is not null";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    // output data of each row
echo "<table border = 2> ";	

	echo"<tr>
    <th> Trainer ID </th>
	<th> Name </th>
	<th> Place Enrolled </th> 
	<th> Birthday</th>
	<th> Username</th>
	<th> Email</th>

	</tr>";
	

    while($row = mysqli_fetch_assoc($result)) {
?>
		<tr>
		<td>
        <?php echo $row['id']?></td><td> 
		<?php echo $row['full_name']?></td><td> 
		<?php echo $row['place_enrolled']?></td><td> 
		<?php echo $row['bdate']?></td><td> 
		<?php echo $row['username']?></td><td> 
		<?php echo $row['email']?></td><td> 
        
		<a href="delete.php?id=<?php echo $row['id']; ?>">Delete</a></td>

		</tr>
		</div>
		
  <?php
  
}}
?>
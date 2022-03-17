<html>

<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
<form action="excel.php" method="post">
	<input type="submit" name="report" value="Generate Reports"/>
</form>

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

$sql = "SELECT a.id, a.user_id, b.full_name, a.name, a.created_at, a.updated_at FROM tasks a, users b WHERE a.user_id = b.id";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    // output data of each row
echo "<table border = 2> ";	

	echo"<tr>
    <th> ID</th>
    <th> Created by</th>
	<th> Task Title </th>
	<th> Created On </th> 
	<th> Updated On</th>


	</tr>";
	

    while($row = mysqli_fetch_assoc($result)) {
?>
		<tr>
		<td>
        <?php echo $row['id']?></td><td>   
        <?php echo $row['full_name']?></td><td> 
		<?php echo $row['name']?></td><td> 
		<?php echo $row['created_at']?></td><td> 
		<?php echo $row['updated_at']?></td><td> 
		
        
		<!-- <a href="subView.php?id=<?php echo $row['user_id']; ?>">View Submissions</a></td> -->

		</tr>
		</div>
		
  <?php
  
}

}	
?>

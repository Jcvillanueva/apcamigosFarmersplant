<html>

<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
<form action="excel_sub.php" method="post">
	<input type="submit" name="report" value="Generate Reports"/>
</form>

</html>

<?php


include ("session.php");

	require ('config.php');
	$id = $_GET['id'];
	$_SESSION["sID"] = $id;

    $conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$sql = "SELECT b.full_name, a.* FROM task_uploads a, users b WHERE a.user_id = b.id AND a.user_id = '$id'";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    // output data of each row
echo "<table border = 2> ";	

	echo"<tr>
    <th> ID </th>
	<th> User_ID </th>
	<th> Task_ID </th> 
	<th> Name </th>
	<th> Graded?</th>
	

	</tr>";
	

    while($row = mysqli_fetch_assoc($result)) {
?>
		<tr>
		<td>
        <?php echo $row['id']?></td><td> 
		<?php echo $row['user_id']?></td><td> 
		<?php echo $row['task_id']?></td><td> 
		<?php echo $row['full_name']?></td><td> 
		<?php echo $row['is_graded']?></td>
		
        

		</tr>
		</div>
		
  <?php
  
}}
?>
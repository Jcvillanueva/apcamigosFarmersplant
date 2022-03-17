<?php
require ('config.php');
include ("session.php");
$conn = new mysqli($servername, $username, $password, $dbname);


$vID = $_SESSION["sID"];

$output = '';
if(isset($_POST["report"])){
    $sql = "SELECT b.full_name, a.* FROM task_uploads a, users b WHERE a.user_id = b.id AND a.user_id = '$vID' ORDER BY id DESC";
    $result = mysqli_query($conn, $sql);

    if (mysqli_num_rows($result) > 0) {
        // output data of each row
    $output .='
        <table border = 2>	
    
        <tr>
    <th> ID </th>
	<th> User_ID </th>
	<th> Task_ID </th> 
	<th> Name </th>
	<th> Graded?</th>
    
    
        </tr>
        ';
        
    
        while($row = mysqli_fetch_assoc($result)) {
  

        $output .='
            <tr>
            <td>'.$row["id"].'</td>
            <td>'.$row["user_id"].'</td>
            <td>'.$row["task_id"].'</td>
            <td>'.$row["full_name"].'</td>
            <td>'.$row["is_graded"].'</td>
             
            
            
            
            </tr>
           
            ';
      
      
    }
    $output .= '</table>';
    header("Content-Type: application/xls");
    header("Content-Disposition:attachment; filename=download.xls");

    echo $output; 

    }	

}

?>
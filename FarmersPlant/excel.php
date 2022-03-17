<?php
require ('config.php');
$conn = new mysqli($servername, $username, $password, $dbname);


$output = '';
if(isset($_POST["report"])){
    $sql = "SELECT a.id, a.user_id, b.full_name, a.name, a.created_at, a.updated_at FROM tasks a, users b WHERE a.user_id = b.id ORDER BY id DESC";
    $result = mysqli_query($conn, $sql);

    if (mysqli_num_rows($result) > 0) {
        // output data of each row
    $output .='
        <table border = 2>	
    
        <tr>
        <th> ID</th>
        <th> Created by</th>
        <th> Task Title </th>
        <th> Created On </th> 
        <th> Updated On</th>
    
    
        </tr>
        ';
        
    
        while($row = mysqli_fetch_assoc($result)) {
  

        $output .='
            <tr>
            <td>'.$row["id"].'</td>
            <td>'.$row["full_name"].'</td>
            <td>'.$row["name"].'</td>
            <td>'.$row["created_at"].'</td>
            <td>'.$row["updated_at"].'</td>
             
            
            
            
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
<?php

require ('config.php');
		

$id = $_GET['id']; // get id through query string

$del = mysqli_query($conn,"delete from users where id = '$id'"); // delete query

if($del)
{
    mysqli_close($conn); // Close connection
    header("location:farmerinfo.php"); // redirects to all records page
    exit;	
}
else
{
    echo "Error deleting record"; // display error message if not delete
}
?>
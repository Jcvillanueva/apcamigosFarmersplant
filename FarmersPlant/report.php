<?php
    require ('config.php');
    include ("session.php");

    $sql = '
        SELECT 
        task_uploads.user_id,
        task_uploads.task_id,
        CONCAT("http://localhost:8000/storage/", task_uploads.task_path) AS file_url,
        users.full_name AS farmer_full_name,
        users.place_enrolled AS farmer_address,
        users.bdate AS farmer_bdate,
        tasks.name AS task_name,
        feedbacks.feedback AS trainer_feedback,
        feedbacks.created_at AS graded_at
        FROM task_uploads 
        LEFT JOIN users ON users.id = task_uploads.user_id 
        LEFT JOIN tasks ON tasks.id = task_uploads.task_id 
        LEFT JOIN feedbacks on feedbacks.task_id = tasks.id AND feedbacks.user_id = users.id;
    ';
    $result = mysqli_query($conn, $sql);

    if (isset($_GET['export'])) {
        if ($_GET['export'] == 1) {
            if($result->num_rows > 0){ 
                $delimiter = ","; 
                $filename = "report-data_" . date('Y-m-d') . ".csv"; 
                 
                // Create a file pointer 
                $f = fopen('php://memory', 'w'); 
                 
                // Set column headers 
                $fields = array('ID', 'user_id', 'task_id', 'file_url', 'farmer_full_name', 'farmer_address', 'farmer_bdate', 'task_name', 'trainer_feedback', 'graded_at'); 
                fputcsv($f, $fields, $delimiter); 

                $ctr = 1;
                // Output each row of the data, format line as csv and write to file pointer 
                while ($row = mysqli_fetch_assoc($result)) {
                    $lineData = array(
                        $ctr,
                        $row['user_id'], 
                        $row['task_id'], 
                        $row['file_url'], 
                        $row['farmer_full_name'], 
                        $row['farmer_address'], 
                        $row['farmer_bdate'], 
                        $row['task_name'], 
                        $row['trainer_feedback'], 
                        $row['graded_at']
                    ); 
                    fputcsv($f, $lineData, $delimiter);
                    $ctr++;
                } 
                 
                // Move back to beginning of file 
                fseek($f, 0); 
                 
                // Set headers to download file rather than displayed 
                header('Content-Type: text/csv'); 
                header('Content-Disposition: attachment; filename="' . $filename . '";'); 
                 
                //output all remaining data on a file pointer 
                fpassthru($f); 
            }
        }
        exit;
    }
?>


<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	<title>Grade Reports</title>
</head>
<body>

    <div class="container">
        <a href="report.php?export=1" class="btn btn-success mt-5">Export</a>
        <table class="table table-striped mt-2 border">
            <thead>
                <tr>
                    <th>
                        ID
                    </th>
                    <th>
                        user_id
                    </th>
                    <th>
                        task_id
                    </th>
                    <th>
                        file_url
                    </th>
                    <th>
                        farmer_full_name
                    </th>
                    <th>
                        farmer_address
                    </th>
                    <th>
                        farmer_bdate
                    </th>
                    <th>
                        task_name
                    </th>
                    <th>
                        trainer_feedback
                    </th>
                    <th>
                        graded_at
                    </th>
                </tr>
            </thead>
            <tbody>
                <?php
                    if (mysqli_num_rows($result) > 0) {
                        $ctr = 1;
                        while($row = mysqli_fetch_assoc($result)) {
                            echo "<tr>";
                            echo "<td>" . $ctr . "</td>";
                            echo "<td>" . $row['user_id'] . "</td>";
                            echo "<td>" . $row['task_id'] . "</td>";
                            echo "<td>" . $row['file_url'] . "</td>";
                            echo "<td>" . $row['farmer_full_name'] . "</td>";
                            echo "<td>" . $row['farmer_address'] . "</td>";
                            echo "<td>" . $row['farmer_bdate'] . "</td>";
                            echo "<td>" . $row['task_name'] . "</td>";
                            echo "<td>" . $row['trainer_feedback'] . "</td>";
                            echo "<td>" . $row['graded_at'] . "</td>";
                            echo "</tr>";
                            $ctr++;
                        }
                    }
                ?>
            </tbody>
        </table>

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>

<?php
    $conn->close();
?>

<?php 
 
 //database constants
 $Customer_Username = $_POST['Customer_Username'];
 $conn = mysqli_connect('localhost', 'root', '', 'mobileproject');
 
 if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
 
 //Checking if any error occured while connecting
 if (mysqli_connect_errno()) {
 echo "Failed to connect to MySQL: " . mysqli_connect_error();
 die();
 }
 
 //creating a query
 $stmt = $conn->prepare("SELECT `Notes`, `Date` FROM `notes` WHERE `Username` = '$Customer_Username';");
 
 //executing the query 
 $stmt->execute();
 
 //binding results to the query 
 $stmt->bind_result($Notes, $Date);
 
 $booking = array(); 
 
 //traversing through all the result 
 while($stmt->fetch()){
 $temp = array();
 $temp['Notes'] = $Notes; 
 $temp['Date'] = $Date; 

 array_push($booking, $temp);
 }
 
 //displaying the result in json format 
 echo json_encode($booking);



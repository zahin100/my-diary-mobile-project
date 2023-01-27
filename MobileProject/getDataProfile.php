
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
 $stmt = $conn->prepare("SELECT `FirstName`, `LastName`, `PhoneNum`, `Birthdate`, `Address`, `Gender`  FROM `customer_profile` WHERE `Username` = '$Customer_Username';");
 
 //executing the query 
 $stmt->execute();
 
 //binding results to the query 
 $stmt->bind_result($firstName, $lastName, $phoneNum, $Birthdate, $Address, $Gender);
 
 $booking = array(); 
 
 //traversing through all the result 
 while($stmt->fetch()){
 $temp = array();

 $temp['FirstName'] = $firstName;
 $temp['LastName'] = $lastName;
	$temp['PhoneNum'] = $phoneNum;
	$temp['Birthdate'] = $Birthdate;
    $temp['Address'] = $Address;
    $temp['Gender'] = $Gender;


 array_push($booking, $temp);
 }
 
 //displaying the result in json format 
 echo json_encode($booking);



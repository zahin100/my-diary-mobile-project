<?php

$hostname = "localhost";
$database = "mobileproject";
$username = "root";
$password = "";

$db = new PDO("mysql:host=$hostname;dbname=$database", $username, $password);



if(isset($_REQUEST['selectFn']) && $_REQUEST['selectFn'] == "fnLogin")
{
    $varUsername = $_REQUEST["Username"];
    $varPassword = $_REQUEST["Password"];

    try{

    
        
        $stmt = $db->prepare('SELECT * FROM customer WHERE Username=?');
        $stmt->execute([$_REQUEST["Username"]]);
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
        

        if($rows == null)
        {
            echo "Username does not exist in Database";
        }
        else
        {
            echo json_encode($rows);
        } 
        


        /* $stmt->execute([$_REQUEST["stud_no"]]);
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if($rows != null)
        {
            $response['respond'] = "User Found!";
             $response['code']  = 1;
            echo json_encode($response);
        }
        else
        {

        } */
        
    }
    catch(Exception $ee)
    {
        $response['respond'] = "Exception occured!". $ee->getMessage();
        echo json_encode($response);
    }

   
}

else if(isset($_REQUEST['selectFn']) && $_REQUEST['selectFn'] == "fnCreateProfile")
{

    $Username = $_REQUEST["username"];
    $varFirstName = $_REQUEST["firstName"];
    $varLastName = $_REQUEST["lastName"];
    $varPhoneNum = $_REQUEST["phone"];
    $varBirthDate = $_REQUEST["birth"];
    $varAddress = $_REQUEST["address"];
    $varGender = $_REQUEST["gender"];

    try{
        $stmt = $db->prepare("INSERT into customer_profile (Username, FirstName, LastName, PhoneNum, Birthdate, Address, Gender) values (:Username, :FirstName, :LastName, :PhoneNum, :Birthdate, :Address, :Gender)");

        $stmt->execute(array(':Username' =>$Username,':FirstName' =>$varFirstName, ':LastName'=>$varLastName, ':PhoneNum'=>$varPhoneNum, ':Birthdate'=>$varBirthDate, ':Address'=>$varAddress, ':Gender'=>$varGender));

        $response['respond'] = "Create Profile Success";
        echo json_encode($response);
    }
    catch(Exception $ee)
    {
        $response['respond'] = "Exception occured!". $ee->getMessage();
        echo json_encode($response);
    }
}


else if(isset($_REQUEST['selectFn']) && $_REQUEST['selectFn'] == "fnSignUp")
{

    $varName = $_REQUEST["Username"];
    $varPassword = $_REQUEST["Password"];
    $varConfirmPassword = $_REQUEST["ConfirmPassword"];
    $varEmail = $_REQUEST["Email"];
    $pattern = '/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/';


    try{
 
        $stmt = $db->prepare("SELECT * FROM customer WHERE Username = :username");
        $stmt->bindParam(':username', $varName);
        $stmt->execute();
        $result = $stmt->fetch();

        if($result)
        {
            echo "Error! Username has been taken in Database";
        }

        else if($varPassword != $varConfirmPassword)
        {
            echo "Password Do not Match!";
        }

        else if(!preg_match($pattern, $varEmail))
        {
            echo "Error: Invalid email address format";
        }


        else
        {
            $stmt = $db->prepare("INSERT into customer (Username, Email, Password) values (:Username, :Email, :Password)");

            $stmt->execute(array(':Username' =>$varName, ':Email'=>$varEmail, ':Password'=>$varPassword));

            $response['respond'] = "Sign Up Success!";
            echo json_encode($response);
        } 

        
    }
    catch(Exception $ee)
    {
        $response['respond'] = "Exception occured!". $ee->getMessage();
        echo json_encode($response);
    }
}

else if(isset($_REQUEST['selectFn']) && $_REQUEST['selectFn'] == "fnNote"){

    $Username = $_REQUEST["Customer_Username"];
    $varNotes = $_REQUEST["Notes"];
    $varDate = $_REQUEST["Date"];

    try{
        $stmt = $db->prepare("INSERT into notes (Notes, Date, Username) values (:Notes, :Date, :Username)");

        $stmt->execute(array(':Notes' =>$varNotes,':Date' =>$varDate, ':Username'=>$Username));

        $response['respond'] = "Create Note Success";
        echo json_encode($response);
    }
    catch(Exception $ee)
    {
        $response['respond'] = "Exception occured!". $ee->getMessage();
        echo json_encode($response);
    }

}

else
{
    $response["respond"] = "All fields are Required !";
    echo json_encode($response);
}
?>

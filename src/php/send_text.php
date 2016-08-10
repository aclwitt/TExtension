<?php
    // Step 1: Get the Twilio-PHP library from twilio.com/docs/libraries/php, 
    // and move it into the folder containing this sendnotifications.php file.
 	require_once "../lib/twilio-php-master/Services/Twilio.php";

 	$type = $argv[1];
 	$to = $argv[2];
 	$msg = $argv[3];
 	
 	$json = json_decode(file_get_contents("../config/authdetails.json"));


 	$AccountSid = $json->accounts->TwilioAccountSID; // Your Account SID from www.twilio.com/console
 	$AuthToken = $json->accounts->TwilioAuthToken;   // Your Auth Token from www.twilio.com/console

 	$client = new Services_Twilio($AccountSid, $AuthToken);


 	// echo "MESSAGE 2: $msg";
 	$message = $client->account->messages->create(array(
 	    "From" => "+14318004272 ", // From a valid Twilio number
 	    "To" => "+14388314268",   // Text this number
 	    "Body" => $msg 
 	));

 	// Display a confirmation message on the screen
 	echo "Sent message {$message->sid}";


    // Step 2: set our AccountSid and AuthToken from https://twilio.com/console
    $AccountSid = "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    $AuthToken = "your_auth_token";

    // Step 3: instantiate a new Twilio Rest Client
    $client = new Services_Twilio($AccountSid, $AuthToken);

    // Step 4: make an array of people we know, to send them a message. 
    // Feel free to change/add your own phone number and name here.
    $people = array(
        "+15558675309" => "Curious George",
        "+15558675308" => "Boots",
        "+15558675307" => "Virgil",
    );

    // Step 5: Loop over all our friends. $number is a phone number above, and 
    // $name is the name next to it
    foreach ($people as $number => $name) {

        $sms = $client->account->messages->sendMessage(

            // Step 6: Change the 'From' number below to be a valid Twilio number 
            // that you've purchased
            "+15017250604", 

            // the number we are sending to - Any phone number
            $number,

            // the sms body
            "Hey $name, Monkey Party at 6PM. Bring Bananas!"
        );

        // Display a confirmation message on the screen
        echo "Sent message to $name";
    }
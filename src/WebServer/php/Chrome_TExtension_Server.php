<?php  

	$domain = AF_INET;
	$type = SOCK_STREAM;
	$protocol = SOL_TCP;
	echo "creating socket\n";
	//Creates a socket
	if (!($socket = socket_create($domain,$type,$protocol))){
		$error_code = socket_last_error($socket);
		$error = socket_strerror($error_code);
		die("Error #$error_code when creating socket: $error\n");
	}
	echo "binding socket\n";
	//Binds socket 
	if (!(socket_bind($socket,'159.203.25.93',1026))){
		$error_code = socket_last_error($socket);
		$error = socket_strerror($error_code);
		die("Error #$error_code when binding socket: $error\n");
	}
	echo "setting to keep-alive\n";

	//Sets to keep-alive
	if (!(socket_set_option($socket,SOL_SOCKET,SO_KEEPALIVE,TRUE))){
		$error_code = socket_last_error($socket);
		$error = socket_strerror($error_code);
		die("Error #$error_code when keeping socket alive: $error\n");
	}
	echo "Telling socket to listen\n";

	//Tells the socket to listen for incoming connections
	if (!(socket_listen($socket))){
		$error_code = socket_last_error($socket);
		$error = socket_strerror($error_code);
		die("Error #$error_code when telling socket to listen: $error\n");
	}
	echo "Accept incoming connections\n";
	//Accepts incoming connections
	if (!($socket = socket_accept($socket))){
		$error_code = socket_last_error($socket);
		$error = socket_strerror($error_code);
		die("Error #$error_code when accepting incoming connections on socket: $error\n");
	}
	while (TRUE){
		$msg = socket_read($socket,2048);
		if ($msg === FALSE) break; 
		echo "Message: " . $msg; 
		sleep(1); 
		// while($msg = socket_read($socket,100)){
		// 	echo $msg; 
		// }
		// if (socket_last_error()){
		// 	$error_code = socket_last_error($socket);
		// 	$error = socket_strerror($error_code);
		// 	die("Error #$error_code when reading from socket: $error\n");
		// }
	}
	socket_close($socket);
	echo "Done!\n";


?>
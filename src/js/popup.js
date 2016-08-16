
window.onload = function(){
	// startMessageDaemon(); 
	// createNotification();
	checkForMessages();
}


//Creates a notificaction with the given title, message, and icon
function createNotification(title,message,icon){
	var opt = {
	  type: "basic",
	  title: title,
	  message: message,
	  iconUrl: icon
	}
	// document.getElementById('send_text_area').innerHTML = "babble 2!";
	chrome.notifications.create("THIS_MY_ID",opt,function(){
	});
}
//Checks for a new message in the json file
function checkForMessages(){
	request = new XMLHttpRequest();
	var url = "http://159.203.25.93/chrome_textension_backend/test.json";
	request.open("POST", url,true);
	request.setRequestHeader("Content-type", "application/json");
	request.onreadystatechange = function () { 
	    if (request.readyState == 4 && request.status == 200) {
	    	console.log(request.responseText);
	        var messages = JSON.parse(request.responseText).messages;
	        var source = messages[0].from;
	        var message = messages[0].message; 
	       	createNotification(source,message,"../images/email.png");
	    }
	}
	request.send();
	startMessageDaemon();
}
function startMessageDaemon(){
	if (timer) clearTimeout(timer); 
	var timer = setTimeout(checkForMessages,5000);
}



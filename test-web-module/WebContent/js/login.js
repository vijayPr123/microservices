$(document).ready(function(){

	function createCORSRequest(method, url, username, password){
		var xhr = new XMLHttpRequest();
		if ("withCredentials" in xhr){
			// XHR has 'withCredentials' property only if it supports CORS
			xhr.open(method, url, true, username, password);
		} else if (typeof XDomainRequest != "undefined"){ // if IE use XDR
			xhr = new XDomainRequest();
			xhr.open(method, url);
		} else {
			xhr = null;
		}
		return xhr;
	}



	$("#loginBtn").click(function() {
		console.log("Inside Login button submit function");

		var username = $("#username").val();
		var password = $("#password").val();

		//	Checking for blank fields.
		if( username =='' || password ==''){
			$('input[type="text"],input[type="password"]').css("border","2px solid red");
			$('input[type="text"],input[type="password"]').css("box-shadow","0 0 3px red");
			alert("Please fill all fields...!!!!!!");
			
		} else {
			console.log("Proceeding for login form submit!");

			var request = createCORSRequest("POST", "http://localhost:8762/auth", username, password);
			if (request) {
				// Define a callback function
				request.onload = function(){};
				var loginFormObj = {};
				loginFormObj["username"] = username;
				loginFormObj["password"] = password;
				
				request.onreadystatechange = function() {
			        if (this.readyState == 4) {
			        	if (this.status == 200) {
			        		var tokenStr = this.getResponseHeader('Auth-Token');
			        		alert("Success! Token generated is: " + tokenStr);
				            console.log("Success ! Token generated is: " + tokenStr);				            
			        	} else {
			        		alert("Error! Incorrect credentials!");
			        	}			        
			        } 
				}
				
				// Send request
				request.send(JSON.stringify(loginFormObj));
			}		

		}
	});
});
$(document).ready(function(){
	
	// check onload
	isValidAccess();

	function isValidAccess() {		
		console.log("isValidAccess() | In home page");
		
		console.log("isValidAccess() | Token value is: " + localStorage.getItem('token'))
		$.ajax({
			url: "/home",
			type: 'GET',
			// Fetch the stored token from localStorage and set in the header
			headers: {"Authorization": localStorage.getItem('token')},
			error: function(err) {
				console.log("Error: " + err);
			    switch (err.status) {
			      case "400":
			        // bad request
			        break;
			      case "401":
			        // unauthorized
			        break;
			      case "403":
			        // forbidden
			        break;
			      default:
			        //Something bad happened
			        break;
			    }
			    
			    window.location.assign("/login.html");
			  },
			  success: function(data) {
			    console.log("isValidAccess() | Allowed access!!");
			  }
		});
	}
});
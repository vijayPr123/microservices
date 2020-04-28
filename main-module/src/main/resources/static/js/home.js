$(document).ready(function(){
	
	console.log("In home.js");
	
	// Print authToken in console	
	console.log('Auth-Token: ' + localStorage.getItem('Auth-Token'));
	
	$("#logoutBtn").click(function() {
		localStorage.removeItem("token");
		$("#logoutForm").submit();
	});
	
});
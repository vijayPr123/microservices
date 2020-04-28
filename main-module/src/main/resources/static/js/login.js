$(document).ready(function(){

	$('#loginForm').submit(function(e) {
		console.log("Inside Login button submit function");

		e.preventDefault();
		$.ajax({
			url: '/doLogin',
			data: $(this).serialize(),
			type: 'POST',
			success: function(data, textStatus, jqXHR) {
				var authToken = jqXHR.getResponseHeader('Authorization');
				console.log('Auth-Token: ' + authToken);
				localStorage.setItem('Auth-Token', authToken);
				
				var authRoleType = jqXHR.getResponseHeader('Authorization-Role-Type');
				console.log('Auth-Role-Type: ' + authRoleType);
				localStorage.setItem('Auth-Role-Type', authRoleType);

				// pass this token as GET request for displaying home page
				navigateToHomeForm();
			}
		});
	});

	function navigateToHomeForm() {		
		console.log("Inside navigateToHomeForm submit function");

		$.ajax({
			url: '/' + localStorage.getItem('Auth-Role-Type') + 'Home',		  
			type: 'GET',
			beforeSend: function(request) {
				request.setRequestHeader("Authorization", localStorage.getItem('Auth-Token'));
			},
			success: function(data, textStatus, jqXHR) {
				// if success, HTML response is expected for home, so replace current
				if (textStatus === 'success') {					
					var newDoc = document.open('text/html', 'replace');
					newDoc.write(data);
					newDoc.close();
				}	  		
			}
		});

	}


});
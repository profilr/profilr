<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		
		<script src="https://apis.google.com/js/platform.js" async defer></script>
		
		<script>
			function signOut() {
				gapi.load("auth2", function() {
					gapi.auth2.init({client_id:'128861007694-c9to43lrkm7t7pa7it4lnu6c0p3msjhn.apps.googleusercontent.com'}).then(function() {
						gapi.auth2.getAuthInstance().signOut().then(function() {
							console.log("Logged out?");
							window.location.replace('${urlMappings.logoutUrl}');
						});
					});
				});
			}
		</script>
		
	</HEAD>

	<BODY>
	
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<h1>Profile</h1>
			
			<div class="button blue" onclick="signOut()" style="cursor: pointer;"><p>Logout</p></div>
		</div>
		
	</BODY>

</HTML>
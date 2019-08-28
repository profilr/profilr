<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		
		<!-- This one is for the .tk -->
		<meta name="google-signin-client_id" content="128861007694-c9to43lrkm7t7pa7it4lnu6c0p3msjhn.apps.googleusercontent.com">
		
		<!-- This one is for the .org -->
		<!-- <meta name="google-signin-client_id" content="128861007694-u6svnj6vpmaqneu5hc9kjoefk617j4l4.apps.googleusercontent.com"> -->
		<script src="https://apis.google.com/js/platform.js" async defer></script>
		
	</HEAD>

	<BODY>
	
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<h1>Profile</h1>
			
			<a href="${urlMappings.logoutUrl}"><div class="button blue"><p>Logout</p></div></a>
		</div>
		
	</BODY>

</HTML>
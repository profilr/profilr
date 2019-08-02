<html>

	<head>
		<title>Profilr</title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/animate.css"/>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/homestyle.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		
		<!-- This one is for the .tk -->
		<!-- <meta name="google-signin-client_id" content="128861007694-c9to43lrkm7t7pa7it4lnu6c0p3msjhn.apps.googleusercontent.com"> -->
		
		<!-- This one is for the .org -->
		<meta name="google-signin-client_id" content="128861007694-u6svnj6vpmaqneu5hc9kjoefk617j4l4.apps.googleusercontent.com">
		<script src="https://apis.google.com/js/platform.js" async defer></script>
		<script>
			gapi.auth2.getAuthInstance().signOut();	
		
			function onSuccess(user) {
				var id_token = user.getAuthResponse().id_token;
				setTimeout(function() {
					document.body.classList.add('slideOutUp');
					setTimeout(function() {
						window.location.replace('${urlMappings.authUrl}' + '?token=' + id_token);
					}, 1000);
				}, 1000);
			}
		</script>
		
	</head>

	<body class="animated slideInUp">
		
		<div id="title">
			<H1>Profilr</H1>
			<p>The online test-profiling service</p>
			<br/><br/>
			<div class="g-signin2" data-onsuccess="onSuccess"></div>
		</div>
	
	</body>

</html>
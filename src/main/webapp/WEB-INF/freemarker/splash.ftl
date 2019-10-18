<html>

	<head>
		<title>Profilr</title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/homestyle.css"/>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.7.2/animate.min.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		
		<meta name="google-signin-client_id"content="128861007694-c9to43lrkm7t7pa7it4lnu6c0p3msjhn.apps.googleusercontent.com">
		<script src="https://apis.google.com/js/platform.js" async defer></script>
		
		<script>
			function onSuccess(user) {
				var id_token = user.getAuthResponse().id_token;
				setTimeout(function() {
					document.body.classList.add('slideOutUp');
					document.body.addEventListener('animationend', function() { 
						window.location.replace('${urlMappings.authUrl}' + '?token=' + id_token);
					})
				}, 1000);
			}
		</script>
		
	</head>

	<body class="animated slideInUp">
		
		<div id="title" class="splash">
			<h1>Profilr</h1>
			<p>The online test-profiling service</p>
			<br/><br/>
			<div onload="signOut()" class="g-signin2" data-onsuccess="onSuccess" data-prompt="select_account"></div>
		</div>
	
	</body>

</html>
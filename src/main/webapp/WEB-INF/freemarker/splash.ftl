<html>

	<head>
		<title>Profilr</title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/homestyle.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		
		<!-- OLD SIGN IN <meta name="google-signin-client_id"content="128861007694-c9to43lrkm7t7pa7it4lnu6c0p3msjhn.apps.googleusercontent.com">
		<script src="https://apis.google.com/js/platform.js" async defer></script>-->

				
		<script>
			function onSuccess(user) {
				var id_token = user.getAuthResponse().id_token;
				setTimeout(function() {
					title = document.getElementById("title")
					title.classList.add('slideFromCenter');
					title.addEventListener('animationend', function() { 
						window.location.replace('${urlMappings.authUrl}' + '?token=' + id_token);
					})
				}, 1500);
			}
		</script>
		
	</head>

	<body>
	
		<div id="header">
			<a href="about"> About </a>
		</div>
	
		<div id="title" class="splash slideToCenter">
			<h1>Profilr</h1>
			<p>The online test-profiling service</p>
			<br/><br/>

			<script src="https://accounts.google.com/gsi/client" async defer></script>
			<div id="g_id_onload"
				 data-client_id="587058858563-nolgj75b6ntuu8uv6no3v7bs34fp2fb0.apps.googleusercontent.com"
				 data-callback="onSuccess">
			</div>
			<div class="g_id_signin" data-type="standard"></div>

			<!-- OLD SIGN IN
				<div onload="signOut()" class="g-signin2" data-onsuccess="onSuccess" data-prompt="select_account"></div>
			-->
		</div>
	
	</body>

</html>

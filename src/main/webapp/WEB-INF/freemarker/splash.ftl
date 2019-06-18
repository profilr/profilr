<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="styles/style.css"/>
		<link rel="stylesheet" href="styles/animate.css"/>
		<link rel="stylesheet" href="styles/homestyle.css"/>
		
		<meta name="google-signin-client_id" content="128861007694-c9to43lrkm7t7pa7it4lnu6c0p3msjhn.apps.googleusercontent.com">
		<script src="https://apis.google.com/js/platform.js" async defer></script>
		<script>
			function onSuccess(user) {
				var id_token = user.getAuthResponse().id_token;
				window.location.replace('${urlMappings.authUrl}' + '?token=' + id_token);
			}
		</script>
		
	</HEAD>

	<BODY class="animated bounceInUp">
		
		<div id="title">
			<H1>Profilr</H1>
			<p>The online test-profiling service</p>
			<div class="g-signin2" data-onsuccess="onSuccess"></div>
		</div>
	
	</BODY>

</HTML>
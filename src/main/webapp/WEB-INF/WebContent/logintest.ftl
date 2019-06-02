<HTML>

	<HEAD>
		
		<Title> Log into Profilr! </Title>
	
		<meta name="google-signin-client_id" content="128861007694-c9to43lrkm7t7pa7it4lnu6c0p3msjhn.apps.googleusercontent.com">
		<script src="https://apis.google.com/js/platform.js" async defer></script>
	
	</HEAD>
	
	<BODY>
	
		<script>
			console.log("HUH");
			function onSuccess(user) {
				var id_token = user.getAuthResponse().id_token;
				window.location.replace('http://nojosh.com/profilr/authorize?token=' + id_token);
			}
		</script>
		<div class="g-signin2" data-onsuccess="onSuccess"/>
		
	</BODY>

</HTML>
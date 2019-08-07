<HTML>

	<HEAD>
		<Title>404</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
	</HEAD>

	<BODY>
	
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<h1>404 Resource Not Found</h1>
			<p>There is no resource with the provided ID in the database.</p>
			<br/>
			<a href="${urlMappings.homeUrl}"> Go home </a>
		</div>
		
	</BODY>

</HTML>
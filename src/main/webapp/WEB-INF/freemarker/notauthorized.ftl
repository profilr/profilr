<HTML>

	<HEAD>
		<Title>401</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
	</HEAD>

	<BODY>
	
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<h1>401 Not Authorized</h1>
			<p>Looks like you don't have permission to make changes to this resource.</p>
			<br/>
			<a href="${urlMappings.homeUrl}"> Go home </a>
		</div>
		
	</BODY>

</HTML>
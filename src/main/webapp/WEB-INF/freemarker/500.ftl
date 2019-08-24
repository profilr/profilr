<HTML>

	<HEAD>
		<Title>500</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
	</HEAD>

	<BODY>
	
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<h1>500 Internal Server Error</h1>
			<p>Sorry, something went wrong with the server.</p>
			<p>Please email us at <a href="mailto:profilrteam@gmail.com">profilrteam@gmail.com</a> to let us know of this error if it keeps happening. Include the information below, as well as what you were doing to reach this error, to help us properly diagnose it.</p>
			<hr/>
			<p>Timestamp: ${date}</p>
			<p>Exception class: ${e.class.name}</p>
			<p>Exception causes:</p>
			<ol>
				<#assign ex = e>
				<#list 1..20 as _>
					<li>${ex.class.name}:
						<#if ex.message??>
							${ex.message}
						<#else>
							(no message provided)
						</#if>
					</li>
					<#if !ex.cause??>
						<#break>
					</#if>
					<#assign ex = ex.cause>
					<#if _ == 20>
						<li>...and more exceptions (too many to print)</li>
					</#if>
				</#list>
			</ol>
			<a href="${urlMappings.homeUrl}"> Go home </a>
		</div>
		
	</BODY>

</HTML>
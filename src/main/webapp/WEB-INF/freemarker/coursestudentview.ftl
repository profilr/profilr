<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/courseAdminViewStyle.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	</HEAD>

	<BODY>
		
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<h1 id="title">${course.name} - ${section.name}</h1>
			
			<div id="testsTab">
				<h2>Course Tests</h2>
				<table class="list">
					<#list course.tests as test>
						<#if test.published>
							<tr>
								<td><a href="${urlMappings.editResponseUrl}/${test.testID}"><p>${test.name}</p></a></td>
								<td><p><#if submissionTimes[test.testID?string]??>Submitted at: ${submissionTimes[test.testID?string]}<#else>Not Submitted Yet</#if></p></td>
							</tr>
						</#if>
					<#else>
						<tr>
							<tr><td><p style="color: #777;">Looks like there's nothing to show here...</p></td><td></td></tr>
						</tr>
					</#list>
				</table>
			</div>
			
		</div>
	
	</BODY>

</HTML>

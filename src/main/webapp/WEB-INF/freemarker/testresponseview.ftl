<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
	</HEAD>

	<BODY>
	
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
		
			<h1>Responses for ${test.name}</h1>
			
			<#list course.sections as section>
				<h3>${section.name}</h3>
				<table class="List">
					<#list section.users?sort as student>
						<tr>
							<td> ${student.fullName} </td>
							<td><p><#if submissionTimes[student.userID]??>Submitted at: ${submissionTimes[student.userID]}<#else>Not Submitted Yet</#if></p></td>
						</tr>
					</#list>
				</table>				
			</#list>
			
		</div>
		
	</BODY>

</HTML>
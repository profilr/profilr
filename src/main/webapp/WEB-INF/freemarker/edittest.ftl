<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
	</HEAD>

	<BODY>
		
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<h1 id="title">${test.name}</h1>
			
			<#if test??>
				<table class="list">
				
					<tr>
						<th>Number</th>
						<th>Question</th>
						<th>Topic</th>
					</tr>	
				
					<#list list.questions as question>
						
						
					
					</#list>
				
					<tr>
						<td><input id="number" type="text"/></td>
						<td><input id="question" type="text"/></td>
						<td><input id="topic" type="text"/></td>
					</tr>
				
				</table>
			</#if>
			
		</div>
	
	</BODY>

</HTML>
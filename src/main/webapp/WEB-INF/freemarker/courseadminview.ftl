<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
	</HEAD>

	<BODY>
		
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<h1 id="title">${course.name}</h1>
			
			<#if course.sections??>
				<p>Course Sections</p>
				<table>
					<#list course.sections as section>
						<tr><th><p>${section.name}</p></th></tr>
					</#list>
				</table>
			</#if>
			
			<#if course.topics??>
				<p>Course Topics</p>
				<table>
					<#list course.tests as test>
						<tr><th><p>${test.name}</p></th><th><p>${test.id}</p></th></tr>
					</#list>
				</table>
			</#if>
			
			<#if course.tests??>
				<p>Course Assignments</p>
				<table>
					<#list course.tests as test>
						<tr><th><p>${test.name}</p></th><th><p>${test.id}</p></th></tr>
					</#list>
				</table>
			</#if>
			
		</div>
	
	</BODY>

</HTML>
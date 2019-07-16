<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="styles/style.css"/>
		<link rel="stylesheet" href="styles/animate.css"/>
		<link rel="stylesheet" href="styles/coursesstyles.css">
		
	</HEAD>

	<BODY>
		
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<h1 id="title">Courses</h1>
			
			<#if administratedCourses??>
				<p>Courses you administrate</p>
				<table>
					<#list administratedCourses as course>
						<tr><th><p class="courseName">${course.name}</p></th></tr>
					</#list>
				</table>
			</#if>
			
			<#if enrolledCourses??>
				<p>Courses you're enrolled in</p>
				<table>
					<#list enrolledCourses as course>
						<tr><th><p class="courseName">${course.name}</p></th></tr>
					</#list>
				</table>
			</#if>
			
			<p>Enroll button doesn't go anywhere yet...</p>
			<div class="centered"><div class="row">
				<a><div class="column button blue">Enroll in a New Course</div></a>
				<#if canCreate?? && canCreate>
					<a href="${urlMappings.createCourseUrl}"><div class="column button blue">Create a New Course</div></a>
				</#if>
			</div></div>
		</div>
	
	</BODY>

</HTML>
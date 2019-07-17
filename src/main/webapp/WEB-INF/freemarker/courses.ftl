<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
	</HEAD>

	<BODY>
		
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<h1 id="title">Courses</h1>
			
			<#if administratedCourses??>
				<p>Courses you administrate</p>
				<br/><br/>
				<table class="list">
					<#list administratedCourses as course>
						<tr><td><a href="${urlMappings.courseAdminViewUrl}/${course.courseID}"><p>${course.name}</p></a></td><td style="text-align: right;"><a href="${urlMappings.deleteCourseUrl}/course/${course.courseID}"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z"/></svg></a></td></tr>
					</#list>
				</table>
				<br/><br/>
			</#if>
			
			<#if enrolledCourses??>
				<p>Courses you're enrolled in</p>
				<br/><br/>
				<table class="list">
					<#list enrolledCourses as course>
						<tr><th><p>${course.name}</p></th></tr>
					</#list>
				</table>
				<br/><br/>
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
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
				<#list administratedCourses as course>
					<div class="course">
						<p class="courseName">${course.name}</p>
						<p class="courseOwner">${course.owner}</p>
					</div>
				</#list>
			</#if>
			
			<#if enrolledCourses??>
				<p>Courses you're enrolled in</p>
				<#list enrolledCourses as course>
					<div class="course">
						<p class="courseName">${course.name}</p>
						<p class="courseOwner">${course.owner}</p>
					</div>
				</#list>
			</#if>
			
			<p>Enroll button doesn't go anywhere yet...</p>
			<div class="centered"><div class="row">
				<a><div class="column button blue">Enroll in a New Course</div></a>
				<a href="${urlMappings.createCourseUrl}"><div class="column button blue">Create a New Course</div></a>
			</div></div>
		</div>
	
	</BODY>

</HTML>
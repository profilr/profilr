<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
	</HEAD>

	<BODY>
		
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<h1 id="title">Courses</h1>
			
			<#if enrolledCourses??>
				<br/>
				<table class="list">
					<tr class="inert"><th><p>Courses you're enrolled in</p></th><th></th></tr>
					<#list enrolledCourses as course>
						<tr>
							<td><a href="${urlMappings.courseViewUrl}/${course.courseID}"><p>${course.name}</p></a></td>
							<td style="text-align: right;"><a href="${urlMappings.unenrollUrl}/${course.courseID}"><img src="${urlMappings.images}/baseline-exit-24px.svg"/></a></td>
						</tr>
					<#else>
						<tr class="inert"><td><p style="color: #777;">Looks like there's nothing to show here...</p></td><td></td></tr>
					</#list>
				</table>
				<br/>
			</#if>

		
		
			<br/>
			<table class="list">
				<tr class="inert"><th><p>Courses you administer</p></th><th></th></tr>
				<#list administratedCourses as course>
					<tr>
						<td><a href="${urlMappings.courseViewUrl}/${course.courseID}"><p>${course.name}</p></a></td>
						<td style="text-align: right;">
							<a href="${urlMappings.renameCourseUrl}/${course.courseID}"><img src="${urlMappings.images}/icons8-rename-24.png"/></a>
							<a href="${urlMappings.deleteCourseUrl}/${course.courseID}"><img src="${urlMappings.images}/baseline-exit-24px.svg"/></a>
						</td>
					</tr>
				<#else>
					<#if canCreate>
						<tr class="inert"><td><p style="color: #777;">Looks like there's nothing to show here... why don't you create a course?</p></td><td></td></tr>
					<#else>
						<tr class="inert"><td>
							<p style="color: #777;">
								You aren't approved to create courses. Please email
								<a href="mailto:profilrteam@gmail.com?subject=Profilr%20Course%20Administrator%20Whitelist"> profilrteam@gmail.com </a>
								from the same email address you used to sign up to be approved.
							</p>
						</td><td></td></tr>
					</#if>
				</#list>
			</table>
			<br/>
			
			<br/><br/><br/>
			<div class="centered"><div class="row">
				<a href="${urlMappings.enrollUrl}"><div class="column button blue">Enroll in a New Course</div></a>
				<#if canCreate>
					<a href="${urlMappings.createCourseUrl}"><div class="column button blue">Create a New Course</div></a>
				</#if>
			</div></div>
		</div>
	
	</BODY>

</HTML>

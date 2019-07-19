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
					<tr class="header"><th><p>Courses you're enrolled in</p></th><th></th></tr>
					<#list enrolledCourses as course>
						<tr><td><p>${course.name}</p></td></tr>
					<#else>
						<tr><td><p style="color: #777;">Looks like there's nothing to show here...</p></td><td></td></tr>
					</#list>
				</table>
				<br/>
			</#if>

			<#if canCreate?? && canCreate && administratedCourses??>
				<br/>
				<table class="list">
					<tr class="header"><th><p>Courses you administer</p></th><th></th></tr>
					<#list administratedCourses as course>
						<tr>
							<td><a href="${urlMappings.courseAdminViewUrl}/${course.courseID}"><p>${course.name}</p></a></td>
							<td style="text-align: right;"><a href="${urlMappings.deleteCourseUrl}/${course.courseID}"><img src="${urlMappings.images}/baseline-delete-24px.svg"/></a></td>
						</tr>
					<#else>
						<tr><td><p style="color: #777;">Looks like there's nothing to show here...</p></td><td></td></tr>
					</#list>
				</table>
				<br/>
			</#if>
			
			<br/><br/><br/>
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

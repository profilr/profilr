<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
	</HEAD>

	<BODY>
		
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
		
			<h1 id="title">Remove <#if admin>admin<#else>student</#if> from course?</h1>
			<p>
				Are you sure you want to remove ${user.fullName} from the course?
				<#if !admin><strong>All their responses will be deleted.</strong></#if>
			</p>
			<p><span id="errorTooltip" class="tooltip"></span></p>
			
			<div class="centered" style="margin: 10px"><div class="row">
				<div id="yes" class="button blue" style="float: left; margin: 10px"><p>I am sure.</p></div>
				<p style="float: left;"> </p>
				<div id="no" class="button blue" style="float: left; margin: 10px"><p>No, go back.</p></div>
			</div></div>
		</div>
	
		<script>
			$("#yes").on("click", function() {
				$.ajax({
					url: '${urlMappings.kickUrl}/${course.courseID}/${user.userID}',
					dataType: 'text',
					type: 'post',
					contentType: 'application/x-www-form-urlencoded',
					success: function( data, textStatus, jQxhr ){
						window.location.replace("${urlMappings.courseViewUrl}/${course.courseID}#membersTab");
					},
					error: function( jqXhr, textStatus, errorThrown ){
						if (jqXhr.status === 404) {
							$("#errorTooltip").html("Error: The student is not enrolled in your course. Please try again later.")
						} else if (jqXhr.status == 400) {
							$("#errorTooltip").html("Sorry, can't try to kick yourself from a course. You can leave a course from the homepage.")
						} else {
							$("#errorTooltip").html("Sorry, an unexpected error occured");
							console.log(errorThrown);
						}
					}
				});
			});
			
			$("#no").on("click", function() { window.location.replace("${urlMappings.courseViewUrl}/${course.courseID}#membersTab"); });
		</script>
	
	</BODY>

</HTML>
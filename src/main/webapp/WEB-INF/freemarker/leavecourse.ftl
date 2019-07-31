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
		
			<h1 id="title">Leave Course?</h1>
			<p>Are you sure you want to stop administrating ${course.name}?</p>
			<#if course.admins?size == 1>
				<p><strong>You are the ONLY admin of this course. If you leave this course, it will be deleted.</strong></p> 
			<#else>
				<p>There will be ${course.admins?size - 1} administrators left.</p>
			</#if>
			
			<div class="centered" style="margin: 10px"><div class="row">
				<div id="yes" class="button blue" style="float: left; margin: 10px"><p>I am sure.</p></div>
				<p style="float: left;"> </p>
				<div id="no" class="button blue" style="float: left; margin: 10px"><p>No, go back.</p></div>
			</div></div>
		</div>
	
		<script>
			$("#yes").on("click", function() {
				$.ajax({
	                url: '${urlMappings.deleteCourseUrl}',
	                dataType: 'text',
	                type: 'post',
	                contentType: 'application/x-www-form-urlencoded',
	                data: "courseId=${course.courseID}",
	                success: function( data, textStatus, jQxhr ){
	                	window.location.replace("${urlMappings.homeUrl}");
	                },
	                error: function( jqXhr, textStatus, errorThrown ){
	                    console.log( errorThrown );
	                }
	            });
			});
			
			$("#no").on("click", function() { window.location.replace("${urlMappings.homeUrl}"); });
		</script>
	
	</BODY>

</HTML>
<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
		
		<script>
			function createCourse() {
				if ($("#courseName").val() === "")
					return
				$.ajax({
	                url: '${urlMappings.createCourseUrl}',
	                dataType: 'text',
	                type: 'post',
	                contentType: 'application/x-www-form-urlencoded',
	                data: "courseName=" + $("#courseName").val(),
	                success: function( data, textStatus, jQxhr ){
	                	window.location.replace("${urlMappings.homeUrl}");
	                },
	                error: function( jqXhr, textStatus, errorThrown ){
	                    console.log( errorThrown );
	                }
	            });
			}
		</script>
		
	</HEAD>

	<BODY>
		
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
		
			<h1 id="title">Create a Course</h1>
			<p>Course Name</p>
			<input id="courseName" type="text"/>
			
			<div id="submit" class="button blue" style="float: right;" onclick="createCourse()"><p>Create</p></div>
		</div>
	
	</BODY>

</HTML>
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
		
			<h1 id="title">Delete Section?</h1>
			<p>Are you sure you want to delete ${section.name}?</p>
			
			<div class="centered"><div class="row">
				<div id="yes" class="button blue" style="float: left;"><p>I am sure.</p></div>
				<p style="float: left;"> </p>
				<div id="no" class="button blue" style="float: left;"><p>Nevermind.</p></div>
			</div></div>
		</div>
	
		<script>
			$("#yes").on("click", function() {
				$.ajax({
	                url: '${urlMappings.deleteSectionUrl}',
	                dataType: 'text',
	                type: 'post',
	                contentType: 'application/x-www-form-urlencoded',
	                data: "sectionId=${section.sectionID}",
	                success: function( data, textStatus, jQxhr ){
	                	window.location.replace("${urlMappings.courseViewUrl}/${section.course.courseID}");
	                },
	                error: function( jqXhr, textStatus, errorThrown ){
	                    console.log( errorThrown );
	                }
	            });
			});
			
			$("#no").on("click", function() { window.location.replace("${urlMappings.courseViewUrl}/${section.course.courseID}"); });
		</script>
	
	</BODY>

</HTML>
<html>

	<head>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
		
		<script>
			function joinCourse() {
				if ($("#joinCode").val() === "") {
					return $("#requiredTooltip").html("(Required Field)");
				}
				
				var params = {};
				params["joinCode"] = $("#joinCode").val();
				
				$.ajax({
					url: '${urlMappings.enrollUrl}',
					dataType: 'text',
					type: 'post',
					contentType: 'application/x-www-form-urlencoded',
					data: $.param(params),
					success: function( data, textStatus, jQxhr ){
						window.location.replace("${urlMappings.homeUrl}");
					},
					error: function( jqXhr, textStatus, errorThrown ){
						if (jqXhr.status === 404)
							return $("#requiredTooltip").html("(Error: Course Not Found)")
					   	else if (jqXhr.status === 409)
					   		return $("#requiredTooltip").html("(Error: Can't enroll in a course you are already enrolled in or an admin of)")
					   	else
					   		return $("#requiredTooltip").html("(Something happened on the server, please try again later")
					}
				});
			}
		</script>
		
	</head>
	<body>
		
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
		
			<h1 id="title">Enroll in a Course</h1>
			<p>Join Code <span id="requiredTooltip" class="tooltip"></span></p>
			<input id="joinCode" type="text"/>
			
			<div id="submit" class="button blue" style="float: right;" onclick="joinCourse()"><p>Join</p></div>
		</div>
	
		<script>
			$("#joinCode").keyup(function(e) { if (e.keyCode == 13) { joinCourse(); } });
		</script>
	
	</body>

</html>
<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
		
		<script>
			function rename() {
				if ($("#name").val() === "")
					return $("#requiredTooltip").show();
				
				var params = {};
				params["name"] = $("#name").val();
				
				$.ajax({
	                url: '${renameUrl}',
	                dataType: 'text',
	                type: 'post',
	                contentType: 'application/x-www-form-urlencoded',
	                data: $.param(params),
	                success: function( data, textStatus, jQxhr ){
	                	window.location.replace("${redirect}");
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
		
			<h1 id="title">Rename "${name}"</h1>
			<p>New Name <span id="requiredTooltip" class="tooltip" style="display: none"> (Required field) </span></p>
			<input id="name" type="text" value="${name}"/>
			<div id="submit" class="button blue" style="float: right;" onclick="rename()"><p>Rename</p></div>
		</div>
		
		<script>
			$("#name").keyup(function(e) { if (e.keyCode == 13) { rename(); } });
		</script>
	
	</BODY>

</HTML>
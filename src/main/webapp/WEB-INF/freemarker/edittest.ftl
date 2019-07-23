<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

		<script>		
			function createQuestion() {
				var label = $("#label").val();
				var text = $("#question").val();
				var topic = $("#topic").val();
				if (label == "" || text == "" || topic =="")
					return;
				$.ajax({url:'${urlMappings.editTestUrl}/${test.testId}/create-question',
					dataType: 'text',
					type: 'post',
					contentType: 'application/json',
					data: "{}",
					success: function(jqxhr, textStatus, error) { window.location.reload(); },
					error: function(data, textStatus, jqxhr){console.log(error);}
					});
			}
		
		</script>
	</HEAD>

	<BODY>
		
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<h1 id="title">${test.name}</h1>
			
			<#if test??>
				<table class="list">
				
					<tr>
						<th>Number</th>
						<th>Question</th>
						<th>Topic</th>
						<th></th>
					</tr>	

					<#if test.questions??>
					<#list test.questions as question>
						
						<tr>
							<td><p>${question.label}</p></td>
							<td><p>${question.text}</p></td>
							<td><p>TopicPlaceholder</p></td>
							<td>Delete</td>
						</tr>	
					
					</#list>
					</#if>
				
					<tr>
						<td><input id="number" type="text" placeholder="Number"/></td>
						<td><input id="question" type="text" placeholder="Question"/></td>
						<td><select id="topic"><option value="">Pick a Topic</option><#list topics as topic><option value="${topic.topicId}">${topic.name}</option></#list></select></td>
						<td><p onclick="createQuestion()">Add</p></td>
					</tr>
				
				</table>
			</#if>
			
		</div>
	
	</BODY>

</HTML>

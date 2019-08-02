<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/testEditorStyles.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

		<script>

			function updateResponses() {
				responses = document.getElementsByClassName("question");
				var i;
				for (i = 0; i < responses.length; i++)
					updateResponse(responses[i].name);
			}
		
			function updateResponse(questionID) {
				var response = {};
				response.question_id = questionID;
				response.correct = document.getElementById(questionID + ".correct").checked;
				response.reason = document.getElementById(questionID + ".reason").value;
				response.notes = document.getElementById(questionID + ".notes").value;
				
				$.ajax({url:'${urlMappings.updateResponseUrl}/${test.testID}',
					dataType: 'text',
					type: 'post',
					contentType: 'application/json',
					data: JSON.stringify(response),
					success: function(jqxhr, textStatus, data) {},
					error: function(error, textStatus, jqxhr){ console.log(error); }
				});
			}

			function checkClicked(questionID) {
				correct = document.getElementById(questionID + ".correct");
				reason = document.getElementById(questionID + ".reason");
				reason.parentElement.classList.toggle("hidden", correct.checked);
			}
			
		</script>
	</HEAD>

	<BODY>
		
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			
			<h1 id="title">${test.name}</h1>
			
			<table class="list" style="text-align: center;">
			<tr>
				<th>Number</th>
				<th>Question</th>
				<th>Points</th>
				<th>Correct?</th>
				<th>Reason Missed</th>
				<th>Notes</th>
			</tr>
			<#if test.questions??>
				<#list test.questions as question>
					<tr class="inert question" name="${question.questionID}">
						<td>${question.label}</td>
						<td>${question.text}</td>
						<td>${question.weight}</td>
						<td><input type="checkbox" id="${question.questionID}.correct" onclick="checkClicked('${question.questionID}')" checked/></td>
						<td class="hidden"><select id="${question.questionID}.reason">
							<option hidden selected>Reason...</option>
							<#if reasons??>
							<#list reasons as reason>
								<option value=${reason.value}>${reason.text}</option>
							</#list>
							</#if>
						</select></td>
						<td><input type="text" id="${question.questionID}.notes"/></td>
					</tr>
				</#list>
			</#if>
			</table>
			
		</div>
		
	</BODY>
	
</HTML>

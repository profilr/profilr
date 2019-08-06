<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/responseEditorStyles.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

		<script>
		
		
			function updateResponses() {
				elements = document.getElementsByClassName("question");
				var i;
				for (i = 0; i < elements.length; i++) {
					questionID = elements[i].getAttribute("name");
					updateResponse(questionID);
				}
			}
			
			function updateResponse(questionID) {
				var response = {
					"question_id": parseInt(questionID),
					"correct": document.getElementById(questionID + ".correct").checked,
					"reason_id": parseInt(document.getElementById(questionID + ".reason").value) === -1 
								? null : parseInt(document.getElementById(questionID + ".reason").value),
					"notes": document.getElementById(questionID + ".notes").value
				};
				
				console.log(response);
				
				$.ajax({url:'${urlMappings.editResponseUrl}/${test.testID}/edit-response',
					dataType: 'text',
					type: 'post',
					contentType: 'application/json',
					data: JSON.stringify(response),
					success: function(jqxhr, textStatus, data) {alert(questionID)},
					error: function(error, textStatus, s){ console.log(error); }
				});
			}

			function checkClicked(questionID) {
				correct = document.getElementById(questionID + ".correct");
				reason = document.getElementById(questionID + ".reason");
				reason.classList.toggle("hidden", correct.checked);
			}
			
			function refreshResponseView(rsp) {
				var correct = document.getElementById(rsp.question.questionID + ".correct");
				var reason = document.getElementById(rsp.question.questionID + ".reason");
				var notes = document.getElementById(rsp.question.questionID + ".notes");
				
				correct.checked = rsp.correct;
				reason.classList.toggle("hidden", rsp.correct);
				reason.value = rsp.reason;
				notes.value = rsp.notes;
			}
			
			function refreshResponses(data) {
				for (var i = 0; i < data.length; i++)
					refreshResponseView(data[i]);
			}
			
			function requestResponses() {
				$.ajax({url:'${urlMappings.editResponseUrl}/${test.testID}/get-response-data',
					dataType: 'text',
					type: 'get',
					success: function(jqxhr, textStatus, data) { refreshResponses(JSON.parse(data.responseText)); },
					error: function(jqxhr, textStatus, error ){ console.log(error); }
				});
			}
			
		</script>
	</HEAD>

	<BODY onload="requestResponses()">
		
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			
			<h1 id="title">${test.name}</h1>
			
			<table class="list" style="text-align: center;">
			<tr>
				<th class="labelColumn">Number</th>
				<th class="textColumn">Question</th>
				<th class="topicColumn">Topic</th>
				<th class="pointsColumn">Points</th>
				<th class="correctColumn">Correct?</th>
				<th class="reasonColumn">Reason Missed</th>
				<th class="notesColumn">Notes</th>
			</tr>
			<#if test.questions??>
				<#list test.questions as question>
					<tr class="inert question" name="${question.questionID}">
						<td>${question.label}</td>
						<td>${question.text}</td>
						<td>${question.topic.name}</td>
						<td>${question.weight}</td>
						<td><input type="checkbox" id="${question.questionID}.correct" onclick="checkClicked('${question.questionID}')" checked/></td>
						<td><select id="${question.questionID}.reason" class="hidden">
							<option hidden selected value="-1">Reason...</option>
							<#list reasons as reason>
								<option value=${reason.reasonID}>${reason.text}</option>
							</#list>
						</select></td>
						<td><input type="text" id="${question.questionID}.notes"/></td>
					</tr>
				</#list>
			</#if>
			</table>
			
			<div id="submit" class="button blue" style="float: right;" onclick="updateResponses()"><p>Save</p></div>
			<p id="saveStatus"></p>
			
		</div>
		
	</BODY>
	
</HTML>

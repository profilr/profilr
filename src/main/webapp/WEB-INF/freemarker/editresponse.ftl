<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/responseEditorStyles.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

		<script>
		
			/*function updateResponses() {
				var responses = [];
				elements = document.getElementsByClassName("question");
				var i;
				for (i = 0; i < elements.length; i++) {
					questionID = elements[i].getAttribute("name");
					
					var response = {};
					response.question_id = parseInt(questionID);
					response.correct = document.getElementById(questionID + ".correct").checked;
					response.reason = document.getElementById(questionID + ".reason").value;
					response.notes = document.getElementById(questionID + ".notes").value;
					
					if (reason == '-1')
						continue;
					
					responses.push(response);
				}
				
				$.ajax({url:'${urlMappings.editResponseUrl}/${test.testID}/edit-responses',
					dataType: 'text',
					type: 'post',
					contentType: 'application/json',
					data: JSON.stringify(responses),
					success: function(jqxhr, textStatus, data) {},
					error: function(error, textStatus, jqxhr){ console.log(error); }
				});
			}*/
		
			function updateResponses() {
				elements = document.getElementsByClassName("question");
				var i;
				for (i = 0; i < elements.length; i++) {
					questionID = elements[i].getAttribute("name");
					updateResponse(questionID);
				}
			}
			
			function updateResponse(questionID) {
				var response = {};
				response.question_id = parseInt(questionID);
				response.correct = document.getElementById(questionID + ".correct").checked;
				response.reason = document.getElementById(questionID + ".reason").value;
				response.notes = document.getElementById(questionID + ".notes").value;
				
				if (reason == "-1")
					return;
				
				$.ajax({url:'${urlMappings.editResponseUrl}/${test.testID}/edit-response',
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
					error: function(error, textStatus, jqxhr){ console.log(error); }
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
			
			<div id="submit" class="button blue" style="float: right;" onclick="updateResponses()"><p>Save</p></div>
			<p id="saveStatus"></p>
			
		</div>
		
	</BODY>
	
</HTML>

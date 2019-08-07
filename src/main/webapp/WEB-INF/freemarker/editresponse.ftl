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
				document.getElementById("saveStatus").innerHTML = "Saving..."
				var response = {
					"question_id": parseInt(questionID),
					"correct": parseInt(document.getElementById(questionID + ".correct").value),
					"reason_id": parseInt(document.getElementById(questionID + ".reason").value) === -1 
								? null : parseInt(document.getElementById(questionID + ".reason").value),
					"notes": document.getElementById(questionID + ".notes").value
				};
				
				$.ajax({url:'${urlMappings.editResponseUrl}/${test.testID}/edit-response',
					dataType: 'text',
					type: 'post',
					contentType: 'application/json',
					data: JSON.stringify(response),
					success: function(jqxhr, textStatus, data) { document.getElementById("saveStatus").innerHTML = "Saved!" },
					error: function(error, textStatus, s){ console.log(error); }
				});
			}

			function updateReasonVisibility(questionID) {
				correct = document.getElementById(questionID + ".correct")
				pointsCorrect = parseInt(correct.value);
				maxPoints = parseFloat(document.getElementById(questionID + ".maxPoints").innerHTML);
				
				if (pointsCorrect > maxPoints)
					correct.value = pointsCorrect = maxPoints;
				
				if (pointsCorrect < 0)
					correct.value = pointsCorrect = 0;
				
				reason = document.getElementById(questionID + ".reason");
				
				reason.classList.toggle("hidden", parseInt(correct.value) == maxPoints);
			}
			
			function refreshResponseView(rsp) {
				var correct = document.getElementById(rsp.question.questionID + ".correct");
				var reason = document.getElementById(rsp.question.questionID + ".reason");
				var notes = document.getElementById(rsp.question.questionID + ".notes");
				
				correct.value = rsp.correct;
				reason.value = rsp.reason.reasonID;
				notes.value = rsp.notes;
				updateReasonVisibility(rsp.question.questionID);
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
				<th class="correctColumn">Correct</th>
				<th class="reasonColumn">Reason Missed</th>
				<th class="notesColumn">Notes</th>
			</tr>
			<#if test.questions??>
				<#list test.questions as question>
					<tr class="inert question" name="${question.questionID}">
						<td>${question.label}</td>
						<td>${question.text}</td>
						<td>${question.topic.name}</td>
						<td id="${question.questionID}.maxPoints">${question.weight}</td>
						<td><input type="number" id="${question.questionID}.correct" value="${question.weight}" onchange="updateReasonVisibility(${question.questionID})" style="width: 40px" min="0" max="${question.weight}"/></td>
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
			
			<table style="float: right; color: #000;">
                <tr>
					<td><p id="saveStatus"></p></td>
                	<td><div id="submit" class="button blue" style="float: right;" onclick="updateResponses()"><p>Save</p></div></td>
                </tr>
            </table>
			
		</div>
		
	</BODY>
	
</HTML>


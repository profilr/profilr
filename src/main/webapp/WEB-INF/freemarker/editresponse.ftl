<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/responseEditorStyles.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

		<script>
		
			var lengthExceeded = false;
			var ajaxRequests = [];
		
			function pushResponse() {
				$("#saveStatus").removeClass("tooltip");
				$("saveStatus").text("Saving...");
				pushAnswers();
				pushPlanOfAction();
				$.when.apply($, ajaxRequests).then(
					function() { //on success
						$("#saveStatus").text("Saved successfully");
						$("#saveStatus").removeClass("tooltip");
					},
					function() { //on failiure
						if (lengthExceeded) {
							$("#saveStatus").text("Plan of action must have less than 1000 characters, and notes must have less than 500 characters.");
							$("#saveStatus").addClass("tooltip");
						} else {
							// TODO: fill in with better AJAX error handling code.
							$("#saveStatus").text("Something went wrong. Please try again later.");
							$("#saveStatus").addClass("tooltip");
						}
					}
				);
			}
			
			function pushPlanOfAction() {
				text = document.getElementById("planOfAction");
				
				var response = {
						"text" : text.value
					};
				
				ajaxRequests.push($.ajax({
					url:'${urlMappings.editResponseUrl}/${test.testID}/update-response',
					dataType: 'text',
					type: 'post',
					contentType: 'application/json',
					data: JSON.stringify(response),
					success: function(jqxhr, textStatus, data) {},
					error: function(jqXhr, textStatus, errorThrown ){
						if (jqXhr.status == 413)
							lengthExceeded = true;
					}
				}));
			}
			
			function pushAnswers() {
				elements = document.getElementsByClassName("question");
				for (var i = 0; i < elements.length; i++) {
					questionID = elements[i].getAttribute("name");
					pushAnswer(questionID)
				}
			}
			
			function pushAnswer(questionID) {
				document.getElementById("saveStatus").innerHTML = "Saving..."
				var response = {
					"question_id": parseInt(questionID),
					"correct": parseInt(document.getElementById(questionID + ".correct").value),
					"reason_id": parseInt(document.getElementById(questionID + ".reason").value) === -1 
								? null : parseInt(document.getElementById(questionID + ".reason").value),
					"notes": document.getElementById(questionID + ".notes").value
				};
				
				ajaxRequests.push($.ajax({
					url:'${urlMappings.editResponseUrl}/${test.testID}/update-answer',
					dataType: 'text',
					type: 'post',
					contentType: 'application/json',
					data: JSON.stringify(response),
					success: function(jqxhr, textStatus, data) {},
					error: function(jqXhr, textStatus, errorThrown ){
						if (jqXhr.status == 413)
							lengthExceeded = true;
					}
				}));

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
			
			function pullAnswerView(rsp) {
				var correct = document.getElementById(rsp.question.questionID + ".correct");
				var reason = document.getElementById(rsp.question.questionID + ".reason");
				var notes = document.getElementById(rsp.question.questionID + ".notes");
				
				correct.value = rsp.correct;
				reason.value = rsp.reason ? rsp.reason.reasonID : -1;
				notes.value = rsp.notes;
				updateReasonVisibility(rsp.question.questionID);
			}
			
			function pullAnswers(data) {
				for (var i = 0; i < data.length; i++)
					pullAnswerView(data[i]);
			}
			
			function requestAnswers() {
				$.ajax({url:'${urlMappings.editResponseUrl}/${test.testID}/get-answer-data',
					dataType: 'text',
					type: 'get',
					success: function(jqxhr, textStatus, data) { pullAnswers(JSON.parse(data.responseText)); },
					error: function(jqxhr, textStatus, error ){ console.log(error); }
				});
			}
			
			function pullResponse(data) {
				document.getElementById("planOfAction").value = data.text;
			}
			
			function requestResponse() {
				$.ajax({url:'${urlMappings.editResponseUrl}/${test.testID}/get-response-data',
					dataType: 'text',
					type: 'get',
					success: function(jqxhr, textStatus, data) { pullResponse(JSON.parse(data.responseText)); },
					error: function(jqxhr, textStatus, error ){ console.log(error); }
				});
			}
			
			function request() {
				requestAnswers();
				requestResponse();
			}
			
		</script>
	</HEAD>

	<BODY onload="request()">
		
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			
			<h1 id="title">${test.name}</h1>
			
			<table class="list" style="text-align: center;">
			<tr>
				<th class="labelColumn">Number</th>
				<th class="typeColumn">Type</th>
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
						<td>${question.questionType.name}</td>
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
			
			<br><br>
			
			<p>Give a plan of action for topics not mastered:</p>
			<textarea id="planOfAction" rows="4" cols="50"></textarea>
			
			<table style="float: right; color: #000;">
				<tr>
					<td><p id="saveStatus"></p></td>
					<td><div id="submit" class="button blue" style="float: right;" onclick="pushResponse()"><p>Save</p></div></td>
				</tr>
			</table>
			
		</div>
		
	</BODY>
	
</HTML>


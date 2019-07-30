<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/testEditorStyles.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

		<script>		
			function createQuestion() {
				var label = $("#label").val();
				var text = $("#question").val();
				var topic = $("#topic").val();
				var points = $("#points").val();
				if (label == "" || text == "" || topic =="" || points == "")
					return;
				$.ajax({url:'${urlMappings.editTestUrl}/${test.testID}/create-question',
					dataType: 'text',
					type: 'post',
					contentType: 'application/json',
					data: '{"label": "' + label + '", "text": "' + text + '", "topic_id": ' + topic + ', "weight": ' + points + '}',
					success: function(jqxhr, textStatus, data) { window.location.reload(); },
					error: function(error, textStatus, jqxhr){console.log(error);}
					});
			}
		
			function deleteQuestion(questionID) {
				$.ajax({url:'${urlMappings.editTestUrl}/${test.testID}/delete-question/' + questionID,
					dataType: 'text',
					type: 'post',
					contentType: 'application/json',
					data: '{}',
					success: function(jqxhr, textStatus, data) { window.location.reload(); },
					error: function(error, textStatus, jqxhr){console.log(error);}
					});
			}
			
			function startEditQuestion(questionID, topicID) {
				questionRow = document.getElementById("question" + questionID);
				editRow = document.getElementById("editQuestion" + questionID);
				
				questionRow.classList.remove("shown");
				editRow.classList.add("shown");
				
				topicDropdown = editRow.querySelector('select[name="topic"]');
				topicDropdown.value=topicID;
			}

			function stopEditQuestion(questionID) {
				questionRow = document.getElementById("question" + questionID);
				editRow = document.getElementById("editQuestion" + questionID);
				
				questionRow.classList.add("shown");
				editRow.classList.remove("shown");
				
				var label = editRow.querySelector('input[name="label"]').value;
				var text = editRow.querySelector('input[name="text"]').value;
				var topic = editRow.querySelector('select[name="topic"]').value;
				var points = editRow.querySelector('input[name="points"]').value;

				if (label == "" || text == "" || topic =="" || points == "")
					return;
				
				$.ajax({url:'${urlMappings.editTestUrl}/${test.testID}/edit-question/' + questionID,
					dataType: 'text',
					type: 'post',
					contentType: 'application/json',
					data: '{"label": "' + label + '", "text": "' + text + '", "topic_id": ' + topic + ', "weight": ' + points + '}',
					success: function(jqxhr, textStatus, data) { window.location.reload(); },
					error: function(error, textStatus, jqxhr){console.log(error);}
					});
			}

		</script>
	</HEAD>

	<BODY>
		
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<h1 id="title">${test.course.name} - ${test.name}</h1>
			
			<table class="list">
				
				<tr class="inert">
					<th class="labelColumn">Number</th>
					<th class="textColumn">Question</th>
					<th class="topicColumn">Topic</th>
					<th class="pointsColumn">Points</th>
					<th class="editColumn"></th>
					<th class="deleteColumn"></th>
				</tr>

				<#list test.questions as question>
				
					<tr id="question${question.questionID}" class="popup shown">
						<td><p>${question.label}</p></td>
						<td><p>${question.text}</p></td>
						<td><p>${question.topic.name}</p></td>
						<td><p>${question.weight}</p></td>
						<td style="text-align: right;"><img src="${urlMappings.images}/baseline-create-24px.svg" style="cursor: pointer;" onclick="startEditQuestion(${question.questionID}, ${question.topic.topicID})"/></td>
						<td style="text-align: right;"><img src="${urlMappings.images}/baseline-delete-24px.svg" style="cursor: pointer;" onclick="deleteQuestion(${question.questionID})"/></td>
					</tr>
					<tr id="editQuestion${question.questionID}" class="popup">			
						<td><input name="label" type="text" placeholder="Label" size=5 value="${question.label}"/></td>
						<td><input name="text" type="text" placeholder="Question" value="${question.text}"/></td>
						<td><select name="topic"><option value="" selected disabled hidden>Pick a Topic</option><#list topics as topic><option value="${topic.topicID}">${topic.name}</option></#list></select></td>
						<td><input name="points" type="number" placeholder="Points" style="width: 50px;" value="${question.weight}"/></td>
						<td></td>
						<td style="text-align: right;"><img src="${urlMappings.images}/baseline-done-24px.svg" style="cursor: pointer;" onclick="stopEditQuestion(${question.questionID})"/></td>
					</tr>
				
				</#list>
			
				<tr id="newQuestionInput">
					<td><input id="label" type="text" placeholder="Label" size=5/></td>
					<td><input id="question" type="text" placeholder="Question"/></td>
					<td><select id="topic"><option value="" selected disabled hidden>Pick a Topic</option><#list topics as topic><option value="${topic.topicID}">${topic.name}</option></#list></select></td>
					<td><input id="points" type="number" placeholder="Points" size=5 style="width: 50px;"/></td>
					<td></td>
					<td style="text-align: right;"><img src="${urlMappings.images}/baseline-add-24px.svg" style="cursor: pointer;" onclick="createQuestion()"/></td>
				</tr>
			
			</table>
			
		</div>
	
		<script>
			$("#newQuestionInput").keyup(function(e) { if (e.keyCode == 13) { createQuestion(); } });
		</script>
	
	</BODY>

</HTML>

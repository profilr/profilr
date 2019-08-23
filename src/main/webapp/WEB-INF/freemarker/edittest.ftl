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
				var type = $("#type").val();
				var topic = $("#topic").val();
				var points = $("#points").val();
				
				if (label == "" || type == "" || topic =="" || points == "")
					return;
				
				var q = {
					"label": label,
					"question_type_id": parseInt(type),
					"topic_id": parseInt(topic),
					"weight": parseInt(points)
				}
				
				$.ajax({url:'${urlMappings.editTestUrl}/${test.testID}/create-question',
					dataType: 'text',
					type: 'post',
					contentType: 'application/json',
					data: JSON.stringify(q),
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
			
			function startEditQuestion(questionID, topicID, typeID) {
				questionRow = document.getElementById("question" + questionID);
				editRow = document.getElementById("editQuestion" + questionID);
				
				questionRow.classList.remove("shown");
				editRow.classList.add("shown");
				
				typeDropdown = editRow.querySelector('select[name="type"]');
				typeDropdown.value = typeID;
				
				topicDropdown = editRow.querySelector('select[name="topic"]');
				topicDropdown.value=topicID;
			}

			function stopEditQuestion(questionID) {
				questionRow = document.getElementById("question" + questionID);
				editRow = document.getElementById("editQuestion" + questionID);
				
				questionRow.classList.add("shown");
				editRow.classList.remove("shown");
				
				var label = editRow.querySelector('input[name="label"]').value;
				var type = editRow.querySelector('select[name="type"]').value;
				var topic = editRow.querySelector('select[name="topic"]').value;
				var points = editRow.querySelector('input[name="points"]').value;

				if (label == "" || type == "" || topic =="" || points == "")
					return;
				
				var q = {
					"label": label,
					"question_type_id": parseInt(type),
					"topic_id": parseInt(topic),
					"weight": parseInt(points)
				}
				
				$.ajax({url:'${urlMappings.editTestUrl}/${test.testID}/edit-question/' + questionID,
					dataType: 'text',
					type: 'post',
					contentType: 'application/json',
					data: JSON.stringify(q),
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
			
			<#if !test.published>
				<p>This test is currently not published. Tests are not visible to enrolled students unless published.</p>
				<p>You can publish this test from the course page by clicking the publish button to the right of the test name.</p><br/><br/>
			</#if>
			
			<table class="list">
				
				<tr class="inert">
					<th class="labelColumn">Number</th>
					<th class="typeColumn">Type</th>
					<th class="topicColumn">Topic</th>
					<th class="pointsColumn">Points</th>
					<th class="editColumn"></th>
					<th class="deleteColumn"></th>
				</tr>

				<#list test.questions as question>
				
					<tr id="question${question.questionID}" class="popup shown">
						<td><p>${question.label}</p></td>
						<td><p>${question.questionType.name}</p></td>
						<td><p>${question.topic.name}</p></td>
						<td><p>${question.weight}</p></td>
						<td style="text-align: right;"><img src="${urlMappings.images}/baseline-create-24px.svg" style="cursor: pointer;" onclick="startEditQuestion(${question.questionID}, ${question.topic.topicID}, ${question.questionType.questionTypeID})"/></td>
						<td style="text-align: right;"><img src="${urlMappings.images}/baseline-delete-24px.svg" style="cursor: pointer;" onclick="deleteQuestion(${question.questionID})"/></td>
					</tr>
					<tr id="editQuestion${question.questionID}" class="popup">			
						<td><input name="label" type="text" placeholder="Number" size=5 value="${question.label}"/></td>
						<td><select name="type"><option value="" selected disabled hidden>Pick a Type</option><#list types as type><option value="${type.questionTypeID}">${type.name}</option></#list></select></td>
						<td><select name="topic"><option value="" selected disabled hidden>Pick a Topic</option><#list topics as topic><option value="${topic.topicID}">${topic.name}</option></#list></select></td>
						<td><input name="points" type="number" placeholder="Points" style="width: 64px;" value="${question.weight}"/></td>
						<td></td>
						<td style="text-align: right;"><img src="${urlMappings.images}/baseline-done-24px.svg" style="cursor: pointer;" onclick="stopEditQuestion(${question.questionID})"/></td>
					</tr>
				
				</#list>
			
				<tr id="newQuestionInput">
					<td><input id="label" type="text" placeholder="Label" size=5/></td>
					<td><select id="type"><option value="" selected disabled hidden>Pick a Type</option><#list types as type><option value="${type.questionTypeID}">${type.name}</option></#list></select></td>
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

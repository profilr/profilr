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
				$.ajax({url:'${urlMappings.editTestUrl}/${test.testId}/create-question',
					dataType: 'text',
					type: 'post',
					contentType: 'application/json',
					data: '{"label": "' + label + '", "text": "' + text + '", "topic_id": ' + topic + ', "weight": ' + points + '}',
					success: function(jqxhr, textStatus, data) { window.location.reload(); },
					error: function(error, textStatus, jqxhr){console.log(error);}
					});
			}
		
			function deleteQuestion(questionID) {
				$.ajax({url:'${urlMappings.editTestUrl}/${test.testId}/delete-question/' + questionID,
					dataType: 'text',
					type: 'post',
					contentType: 'application/json',
					data: '{}',
					success: function(jqxhr, textStatus, data) { window.location.reload(); },
					error: function(error, textStatus, jqxhr){console.log(error);}
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
						<th class="labelColumn">Number</th>
						<th class="textColumn">Question</th>
						<th class="topicColumn">Topic</th>
						<th class="pointsColumn">Points</th>
						<th class="editColumn"></th>
						<th class="deleteColumn"></th>
					</tr>	

					<#if test.questions??>
					<#list test.questions as question>
						
						<tr>
							<td><p>${question.label}</p></td>
							<td><p>${question.text}</p></td>
							<td><p>${question.topic.name}</p></td>
							<td><p>${question.weight}</p></td>
							<td style="text-align: right;"><img src="${urlMappings.images}/baseline-create-24px.svg" style="cursor: pointer;"/></td>
							<td style="text-align: right;"><img src="${urlMappings.images}/baseline-delete-24px.svg" style="cursor: pointer;" onclick="deleteQuestion(${question.questionId})"/></td>
						</tr>	
					
					</#list>
					</#if>
				
					<tr id="newQuestionInput">
						<td><input id="label" type="text" placeholder="Label" size=5/></td>
						<td><input id="question" type="text" placeholder="Question"/></td>
						<td><select id="topic"><option value="">Pick a Topic</option><#list topics as topic><option value="${topic.topicId}">${topic.name}</option></#list></select></td>
						<td><input id="points" type="number" placeholder="Points" size=5/></td>
						<td></td>
						<td style="text-align: right;"><img src="${urlMappings.images}/baseline-add-24px.svg" style="cursor: pointer;" onclick="createQuestion()"/></td>
					</tr>
				
				</table>
			</#if>
			
		</div>
	
		<script>
			$("#newQuestionInput").keyup(function(e) { if (e.keyCode == 13) { createQuestion(); } });
		</script>
	
	</BODY>

</HTML>

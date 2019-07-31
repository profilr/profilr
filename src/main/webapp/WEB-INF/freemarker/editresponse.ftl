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
				response.correct = row.getElementsByName("correct")[0].checked;
				response.reason = row.getElementsByName("reason")[0].value;
				response.notes = row.getElementByName("notes")[0].value;
				
				$.ajax({url:'${urlMappings.updateResponseUrl}',
					dataType: 'text',
					type: 'post',
					contentType: 'application/json',
					data: JSON.stringify(response),
					success: function(jqxhr, textStatus, data) {},
					error: function(error, textStatus, jqxhr){ console.log(error); }
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
				
					<#if test.questions??>
					<#list test.questions as question>
						<tr class="inert">
							<table>
								<tr class="inert question" id="response${question.question_id}" name="${question.question_id}">
									<td>${question.label}</td>
									<td>${question.text}</td>
									<td>${question.weight}</td>
									<td><input type="checkbox" name="correct" checked/></td>
									<td class="popup"><choice name="reason">
										<option hidden selected>Reason...</option>
										<#if reasons??>
										<#list reasons as reason>
											<option value=${reason.value}>${reason.text}</option>
										</#list>
										</#if>
									</choice></td>
									<td><input type="text" name="notes"/></td>
									
								</tr>
							</table>
						</tr>
					</#list>
					</#if>
					
				</table>
			</#if>
			
		</div>
		
	</BODY>
	
</HTML>

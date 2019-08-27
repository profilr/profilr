<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/responseEditorStyles.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	</HEAD>

	<BODY>
		
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			
			<h1 id="title">${test.name} - ${user.fullName}</h1>
			
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
						<#assign answer = answers[question.questionID?string]>
						<td>${question.label}</td>
						<td>${question.questionType.name}</td>
						<td>${question.topic.name}</td>
						<td>${question.weight}</td>
						<#if answer??>
							<td>${answer.correct}</td>
							<#if answer.reason??><td>${answer.reason.text}</td><#else><td></td></#if>
							<td>${answer.notes}</td>
						<#else>
							<td><p>No response for this question.<p></td>
							<td></td>
							<td></td>
						</#if>
					</tr>
				</#list>
			</#if>
			</table>
			
			<br><br>
			
			<p>Give a plan of action for topics not mastered:</p>
			<textarea readonly id="planOfAction" rows="4" cols="50">${response.text}</textarea>
			
		</div>
		
	</BODY>
	
</HTML>


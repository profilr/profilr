<html>

	<head>
		<title>Profilr</title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/performance.css"/>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/loading.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		<script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	</head>

	<body>
	
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<div style="display: flex; align-items: baseline">
				<h1 id="title" style="flex-grow: 1">${course.name} Performance</h1>
				<span style="flex-grow: 0"><a href="${urlMappings.performanceUrl}/raw?courseID=${course.courseID}" target="_blank"><img src="${urlMappings.images}/baseline-get_app-24px.svg"/>Download Raw</a></span>
			</div>
			<div class="quadrant">
				<table>
					<tr>
						<td><label for="bytopic-test-filter">Test</label></td>
						<td><label for="bytopic-questiontype-filter">Question Type</label></td>
						<td><label for="bytopic-section-filter">Section</label></td>
						<td><label for="bytopic-student-filter">Student</label></td>
					</tr>
					<tr>
						<td>
							<select id="bytopic-test-filter">
								<option value="-1" selected>All tests...</option>
								<#list course.tests as test>
									<option value="${test.testID}">${test.name}</option>
								</#list>
							</select>
						</td>
						<td>
							<select id="bytopic-questiontype-filter">
								<option value="-1" selected>All types...</option>
								<#list course.questionTypes as type>
									<option value="${type.questionTypeID}">${type.name}</option>
								</#list>
							</select>
						</td>
						<td>
							<select id="bytopic-section-filter">
								<option value="-1" selected>All sections...</option>
								<#list course.sections as section>
									<option value="${section.sectionID}">${section.name}</option>
								</#list>
							</select>
						</td>
						<td>
							<select id="bytopic-student-filter">
								<option value="-1" selected>All students...</option>
								<#list course.enrolledStudents as student>
									<option value="${student.userID}">${student.fullName}</option>
								</#list>
							</select>
						</td>
						<td>
							<button type="button" id="bytopic-button">Add</button>
							<button type="button" id="bytopic-clear">Clear</button>
						</td>
					</tr>
				</table>
				<div id="bytopic-graph"></div>
				<div class="lds-ring" id="bytopic-loading"><div></div><div></div><div></div><div></div></div>
			</div>
			<div class="quadrant">
				<table>
					<tr>
						<td><label for="byreason-test-filter">Test</label></td>
						<td><label for="byreason-topic-filter">Topic</label></td>
						<td><label for="byreason-questiontype-filter">Question Type</label></td>
						<td><label for="byreason-section-filter">Section</label></td>
						<td><label for="byreason-student-filter">Student</label></td>
					</tr>
					<tr>
						<td>
							<select id="byreason-test-filter">
								<option value="-1" selected>All tests...</option>
								<#list course.tests as test>
									<option value="${test.testID}">${test.name}</option>
								</#list>
							</select>
						</td>
					<td>
						<select id="byreason-topic-filter">
							<option value="-1" selected>All topics...</option>
							<#list course.topics as type>
								<option value="${type.topicID}">${type.name}</option>
							</#list>
						</select>
					</td>
					<td>
							<select id="byreason-questiontype-filter">
								<option value="-1" selected>All types...</option>
								<#list course.questionTypes as type>
									<option value="${type.questionTypeID}">${type.name}</option>
								</#list>
							</select>
						</td>
						<td>
							<select id="byreason-section-filter">
								<option value="-1" selected>All sections...</option>
								<#list course.sections as section>
									<option value="${section.sectionID}">${section.name}</option>
								</#list>
							</select>
						</td>
						<td>
							<select id="byreason-student-filter">
								<option value="-1" selected>All students...</option>
								<#list course.enrolledStudents as student>
									<option value="${student.userID}">${student.fullName}</option>
								</#list>
							</select>
						</td>
						<td>
							<button type="button" id="byreason-button">Go</button>
						</td>
					</tr>
				</table>
				<div id="byreason-graph"></div>
				<div class="lds-ring" id="byreason-loading"><div></div><div></div><div></div><div></div></div>
			</div>
			<div class="quadrant" style="background: #777"> </div>
			<div class="quadrant" style="background: #444"> </div>
		</div>
		
		<script>
		function bytopic(testID, questionTypeID, sectionID, userID) {
			$("#bytopic-loading").show();
			$.ajax({
				url:'${urlMappings.performanceUrl}/bytopic',
				dataType: 'text',
				type: 'get',
				data: {
					courseID: ${course.courseID},
					testID: testID,
					questionTypeID: questionTypeID,
					sectionID: sectionID,
					userID: userID
				},
				contentType: 'application/json',
				success: function(jqxhr, textStatus, data) {
					$("#bytopic-loading").hide();
					var json = JSON.parse(data.responseText);
					Plotly.plot("bytopic-graph", [{
						x: Object.keys(json),
						y: Object.values(json),
						name: (function(){
							// Specifically using double equals instead of triple equals
							var testName = testID == -1 ?
											"All Tests" :
											$("#bytopic-test-filter option:selected").text();
							var studentSectionName = sectionID == -1 ?
														(userID == -1 ?
															"All Students" :
															$("#bytopic-student-filter option:selected").text()) :
														$("#bytopic-section-filter option:selected").text();
							var questionTypeName = questionTypeID == -1 ?
													"All Types" :
													$("#bytopic-questiontype-filter option:selected").text();
							return testName + " - " + studentSectionName + " - " + questionTypeName;
						})(),
						type: "bar"
					}], {title: "Performance by Topics"}, {responsive: true});
				},
				error: function(jqxhr, textStatus, error){ console.log(error); }
			});
		};
		
		function byreason(testID, topicID, questionTypeID, sectionID, userID) {
			$("#byreason-loading").show();
			$.ajax({
				url:'${urlMappings.performanceUrl}/byreason',
				dataType: 'text',
				type: 'get',
				data: {
					courseID: ${course.courseID},
					testID: testID,
					topicID: topicID,
					questionTypeID: questionTypeID,
					sectionID: sectionID,
					userID: userID
				},
				contentType: 'application/json',
				success: function(jqxhr, textStatus, data) {
					$("#byreason-loading").hide();
					var json = JSON.parse(data.responseText);
					Plotly.newPlot("byreason-graph", [{
						labels: Object.keys(json),
						values: Object.values(json),
						type: "pie"
					}], {title: "Reasons Missed"}, {responsive: true});
				},
				error: function(jqxhr, textStatus, error){ console.log(error); }
			});
		};
		
		$("#bytopic-button").on("click", function (e) {
			bytopic($("#bytopic-test-filter").val(),
					$("#bytopic-questiontype-filter").val(),
					$("#bytopic-section-filter").val(),
					$("#bytopic-student-filter").val());
		});
		
		$("#bytopic-clear").on("click", function (e) {
			Plotly.react("bytopic-graph", [{"type": "bar"}], {responsive: true});
		});
		
		$("#bytopic-section-filter").on("change", function (e) {$("#bytopic-student-filter").val("-1");});
		$("#bytopic-student-filter").on("change", function (e) {$("#bytopic-section-filter").val("-1");});
		
		$("#byreason-button").on("click", function (e) {
			byreason($("#byreason-test-filter").val(),
					 $("#byreason-topic-filter").val(),
					 $("#byreason-questiontype-filter").val(),
					 $("#byreason-section-filter").val(),
					 $("#byreason-student-filter").val());
		});
		
		$("#byreason-section-filter").on("change", function (e) {$("#byreason-student-filter").val("-1");});
		$("#byreason-student-filter").on("change", function (e) {$("#byreason-section-filter").val("-1");});

		bytopic(-1, -1, -1, -1);
		byreason(-1, -1, -1, -1, -1);
		
		</script>
		
	</body>

</html>
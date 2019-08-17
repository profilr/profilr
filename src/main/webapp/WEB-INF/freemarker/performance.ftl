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
			<h1>${course.name} Performance</h1>
			<div class="quadrant">
				<label for="bytopic_test_filter">Filter test:</label>
				<select id="bytopic_test_filter">
					<option value="-1" selected>All tests...</option>
					<#list course.tests as test>
						<option value="${test.testID}">${test.name}</option>
					</#list>
				</select>
				<label for="bytopic_section_filter">Filter section:</label>
				<select id="bytopic_section_filter">
					<option value="-1" selected>All sections...</option>
					<#list course.sections as section>
						<option value="${section.sectionID}">${section.name}</option>
					</#list>
				</select>
				<label for="bytopic_student_filter">Filter student:</label>
				<select id="bytopic_student_filter">
					<option value="-1" selected>All students...</option>
					<#list course.enrolledStudents as student>
						<option value="${student.userID}">${student.fullName}</option>
					</#list>
				</select>
				<button type="button" id="bytopic_button">Add</button>
				<div id="bytopic_graph">
				</div>
				<div class="lds-ring" id="bytopic_loading"><div></div><div></div><div></div><div></div></div>
			</div>
			<div class="quadrant" style="background: #AAA"> </div>
			<div class="quadrant" style="background: #777"> </div>
			<div class="quadrant" style="background: #444"> </div>
		</div>
		
		<script>
		function bytopic(testID, sectionID, userID) {
			$("#bytopic_loading").show();
			$.ajax({
				url:'${urlMappings.performanceUrl}/bytopic',
				dataType: 'text',
				type: 'get',
				data: {
					courseID: ${course.courseID},
					testID: testID,
					sectionID: sectionID,
					userID: userID
				},
				contentType: 'application/json',
				success: function(jqxhr, textStatus, data) {
					$("#bytopic_loading").hide();
					json = JSON.parse(data.responseText);
					Plotly.plot("bytopic_graph",[{
						x: Object.keys(json),
						y: Object.values(json),
						name: (function(){
							// Specifically using double equals instead of triple equals
							var testName = testID == -1 ?
											"All Tests" :
											$("#bytopic_test_filter option:selected").text();
							var studentSectionName = sectionID == -1 ?
														(userID == -1 ?
															"All Students" :
															$("#bytopic_student_filter option:selected").text()) :
														$("#bytopic_section_filter option:selected").text();
							return testName + " - " + studentSectionName;
						})(),
						type: "bar"
					}], {title: "Performance by Topics"}, {responsive: true});
				},
				error: function(jqxhr, textStatus, error){ console.log(error); }
			});
		}
		
		$("#bytopic_button").on("click", function (e) {
			bytopic($("#bytopic_test_filter").val(),
					$("#bytopic_section_filter").val(),
					$("#bytopic_student_filter").val());
		})
		
		$("#bytopic_section_filter").on("change", function (e) {$("#bytopic_student_filter").val("-1");})
		$("#bytopic_student_filter").on("change", function (e) {$("#bytopic_section_filter").val("-1");})
		
		bytopic(-1, -1, -1)
		</script>
		
	</body>

</html>
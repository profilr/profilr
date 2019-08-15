<html>

	<head>
		<title>Profilr</title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/performance.css"/>
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
				<div id="bytopic_graph"></div>
			</div>
			<div class="quadrant" style="background: #AAA"> </div>
			<div class="quadrant" style="background: #777"> </div>
			<div class="quadrant" style="background: #444"> </div>
		</div>
		
		<script>
		function bytopic(testID, sectionID, userID) {
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
					json = JSON.parse(data.responseText);
					Plotly.plot("bytopic_graph",[{
						x: Object.keys(json),
						y: Object.values(json),
						type: "bar"
					}], {title: "Performance by Topics"}, {responsive: true})
					
				},
				error: function(jqxhr, textStatus, error){ console.log(error); }
			});
		}
		
		bytopic(-1, -1, -1)
		</script>
		
	</body>

</html>
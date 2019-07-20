<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/courseAdminViewStyle.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

		<script>
			
			function openTab(tabName) {
				var i, x, tablinks;
				x = document.getElementsByClassName("tab");
				for (i = 0; i < x.length; i++) { x[i].style.display = "none"; }
				
				tablinks = document.getElementsByClassName("tabLink");
				for (i = 0; i < x.length; i++) { tablinks[i].classList.remove("highlighted");	}
				
				document.getElementById(tabName).style.display = "block";
				document.getElementById(tabName + "Link").classList.add("highlighted");
			}
			
			function createSection() {
				$.ajax({
	                url: '${urlMappings.createSectionUrl}',
	                dataType: 'text',
	                type: 'post',
	                contentType: 'application/x-www-form-urlencoded',
	                data: "sectionName=" + $("#sectionName").val() + "&courseId=" + ${course.courseId},
	                success: function( data, textStatus, jQxhr ){
	                	window.location.hash = "#sectionsTab";
	                	window.location.reload();
	                },
	                error: function( jqXhr, textStatus, errorThrown ){
	                    console.log( errorThrown );
	                }
	            });
			}
		
			function createTopic() {
				$.ajax({
	                url: '${urlMappings.createTopicUrl}',
	                dataType: 'text',
	                type: 'post',
	                contentType: 'application/x-www-form-urlencoded',
	                data: "topicName=" + $("#topicName").val() + "&courseId=" + ${course.courseId},
	                success: function( data, textStatus, jQxhr ){
	                	window.location.hash = "#topicsTab";
	                	window.location.reload();
	                },
	                error: function( jqXhr, textStatus, errorThrown ){
	                    console.log( errorThrown );
	                }
	            });
			}
			
			function createTest() {
				$.ajax({
	                url: '${urlMappings.createTestUrl}',
	                dataType: 'text',
	                type: 'post',
	                contentType: 'application/x-www-form-urlencoded',
	                data: "testName=" + $("#testName").val() + "&courseId=" + ${course.courseId},
	                success: function( data, textStatus, jQxhr ){
	                	window.location.hash = "#testsTab";
	                	window.location.reload();
	                },
	                error: function( jqXhr, textStatus, errorThrown ){
	                    console.log( errorThrown );
	                }
	            });
			}
			
		</script>
		
	</HEAD>

	<BODY>
			
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<h1 id="title">${course.name}</h1>
			
			<table class="tabBar">
				<tr>
					<td class="tabLink highlighted" id ="sectionsTabLink" onclick="openTab('sectionsTab')" style="width: 33%;"><a href="javascript:void(0)"><p>Sections</p></a></td>
					<td class="tabLink" id="topicsTabLink" onclick="openTab('topicsTab')" style="width: 33%;"><a href="javascript:void(0)"><p>Topics</p></a></td>
					<td class="tabLink" id="testsTabLink" onclick="openTab('testsTab')" style="width: 33%;"><a href="javascript:void(0)"><p>Tests</p></a></td>
				</tr>
			</table>
			
			<div id="sectionsTab" class="tab" style="display: block;">
				<#if course.sections??>
					<h2>Course Sections</h2>
					<table class="list">
						<#list course.sections as section>
							<tr>
								<td><p>${section.name}</p></td>
								<td style="text-align: right;"><a href="${urlMappings.deleteSectionUrl}/${section.sectionId}"><img src="${urlMappings.images}/baseline-delete-24px.svg"/></a></td>
							</tr>
						</#list>
						<tr><td><input type="text" id="sectionName" placeholder="Section Name..."/></td><td style="text-align: right;"><img src="${urlMappings.images}/baseline-add-24px.svg" style="cursor: pointer;" onclick="createSection()"/></td></tr>
						
					</table>
				</#if>
			</div>
			
			<div id="topicsTab" class="tab">
				<#if course.topics??>
					<h2>Course Topics</h2>
					<table class="list">
						<#list course.topics as topic>
							<tr>
								<td><p>${topic.name}</p></td>
								<td style="text-align: right;"><a href="${urlMappings.deleteTopicUrl}/${topic.topicId}"><img src="${urlMappings.images}/baseline-delete-24px.svg"/></a></td>
							</tr>
						</#list>
						<tr><td><input type="text" id="topicName" placeholder="Topic Name..."/></td><td style="text-align: right;"><img src="${urlMappings.images}/baseline-add-24px.svg" style="cursor: pointer;" onclick="createTopic()"/></td></tr>
					</table>
				</#if>
			</div>
			
			<div id="testsTab" class="tab">
				<#if course.tests??>
					<h2>Course Assignments</h2>
					<table class="list">
						<#list course.tests as test>
							<tr>
								<td><p>${test.name}</p></td>
								<td style="text-align: right;"><a href="${urlMappings.deleteTestUrl}/${test.testId}"><img src="${urlMappings.images}/baseline-delete-24px.svg"/></a></td>
							</tr>
						</#list>
						<tr><td><input type="text" id="testName" placeholder="Test Name..."/></td><td style="text-align: right;"><img src="${urlMappings.images}/baseline-add-24px.svg" style="cursor: pointer;" onclick="createTest()"/></td></tr>
					</table>
				</#if>
			</div>
			
			<script>
				if (window.location.hash) {
					console.log(window.location.hash);
					openTab(window.location.hash.substring(1));
				}
				
				$("#sectionName").keyup(function(e) { if (e.keyCode == 13) { createSection(); } });
				$("#topicName").keyup(function(e) { if (e.keyCode == 13) { createTopic(); } });
				$("#testName").keyup(function(e) { if (e.keyCode == 13) { createTest(); } });
				
			</script>
			
		</div>
	
	</BODY>

</HTML>

<html>

	<head>
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
				
				window.location.hash = "#"+tabName;
			}
		
			function createSection() {
				if ($("#sectionName").val() === "")
					return $("#sectionRequiredTooltip").show();
				$.ajax({
	                url: '${urlMappings.createSectionUrl}',
	                dataType: 'text',
	                type: 'post',
	                contentType: 'application/x-www-form-urlencoded',
	                data: "sectionName=" + $("#sectionName").val() + "&courseId=" + ${course.courseID},
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
				if ($("#topicName").val() === "")
					return $("#topicRequiredTooltip").show();
				$.ajax({
	                url: '${urlMappings.createTopicUrl}',
	                dataType: 'text',
	                type: 'post',
	                contentType: 'application/x-www-form-urlencoded',
	                data: "topicName=" + $("#topicName").val() + "&courseId=" + ${course.courseID},
	                success: function( data, textStatus, jQxhr ){
	                	window.location.hash = "#topicsTab";
	                	window.location.reload();
	                },
	                error: function( jqXhr, textStatus, errorThrown ){
	                    console.log( errorThrown );
	                }
	            });
			}
			
			function createQuestionType() {
				if ($("#questionTypeName").val() === "")
					return $("#questionTypeRequiredTooltip").show();
				$.ajax({
	                url: '${urlMappings.createQuestionTypeUrl}',
	                dataType: 'text',
	                type: 'post',
	                contentType: 'application/x-www-form-urlencoded',
	                data: "questionTypeName=" + $("#questionTypeName").val() + "&courseId=" + ${course.courseID},
	                success: function( data, textStatus, jQxhr ){
	                	window.location.hash = "#questionTypesTab";
	                	window.location.reload();
	                },
	                error: function( jqXhr, textStatus, errorThrown ){
	                    console.log( errorThrown );
	                }
	            });
			}
			
			function createTest() {
				if ($("#testName").val() === "")
					return $("#testRequiredTooltip").show();
				$.ajax({
	                url: '${urlMappings.createTestUrl}',
	                dataType: 'text',
	                type: 'post',
	                contentType: 'application/x-www-form-urlencoded',
	                data: "testName=" + $("#testName").val() + "&courseId=" + ${course.courseID},
	                success: function( data, textStatus, jQxhr ){
	                	window.location.hash = "#testsTab";
	                	window.location.reload();
	                },
	                error: function( jqXhr, textStatus, errorThrown ){
	                    console.log( errorThrown );
	                }
	            });
			}
			
			function inviteAdmin() {
				if ($("#inviteEmail").val() === "")
					return $("#inviteErrorTooltip").html("(Required Field)");
				$.ajax({
	                url: '${urlMappings.inviteAdminUrl}',
	                dataType: 'text',
	                type: 'post',
	                contentType: 'application/x-www-form-urlencoded',
	                data: "email=" + $("#inviteEmail").val() + "&courseID=" + ${course.courseID},
	                success: function( data, textStatus, jQxhr ){
	                	window.location.reload();
	                },
	                error: function( jqXhr, textStatus, errorThrown ){
	                    if (jqXhr.status === 404) {
	                    	$("#inviteErrorTooltip").html("Error: User not found");
	                    } else if (jqXhr.status == 400) {
	                    	$("#inviteErrorTooltip").html("Error: User is already a student or admin"); 
	                    } else {
	                    	$("#inviteErrorTooltip").html("An unknown error occured. Please try again later");
	                    	console.log(errorThrown);	
	                    }
	                }
	            });
			}
			
			function publishTest(testID) {
				$.ajax({
	                url: '${urlMappings.editTestUrl}/' + testID + '/publish',
	                dataType: 'text',
	                type: 'post',
	                success: function( data, textStatus, jQxhr ){
	                	window.location.hash = "#testsTab";
	                	window.location.reload();
	                },
	                error: function( jqXhr, textStatus, errorThrown ){
	                    console.log( errorThrown );
	                }
	            });
			}
			
			function unpublishTest(testID) {
				$.ajax({
	                url: '${urlMappings.editTestUrl}/' + testID + '/unpublish',
	                dataType: 'text',
	                type: 'post',
	                success: function( data, textStatus, jQxhr ){
	                	window.location.hash = "#testsTab";
	                	window.location.reload();
	                },
	                error: function( jqXhr, textStatus, errorThrown ){
	                    console.log( errorThrown );
	                }
	            });
			}
			
			function toggleDropdown(dropdownID) {
				elements = document.getElementsByClassName("dropdown-content");
				for (i = 0; i < elements.length; i++) {
					if (elements[i].id == dropdownID)
						elements[i].classList.toggle("shown");
					else
						elements[i].classList.remove("shown");
				}
			}
			
			function hideDropdowns() {
				elements = document.getElementsByClassName("dropdown-content");
				for (i = 0; i < elements.length; i++)
					elements[i].classList.remove("shown");
			}
			
			window.onclick = function(event) {
				if (!event.target.classList.contains("dropdownButton"))
					hideDropdowns();
			}
			
		</script>
		
	</head>

	<body>
			
		<#include "navbar.ftl">
		
		<div class="bodyContainer" style="overflow: visible;">
			<h1 id="title">${course.name}</h1>
			
			<table class="tabBar">
				<tr>
					<td class="tabLink highlighted" id ="sectionsTabLink" onclick="openTab('sectionsTab')" style="width: 20%;"><a href="javascript:void(0)"><p>Sections</p></a></td>
					<td class="tabLink" id="topicsTabLink" onclick="openTab('topicsTab')" style="width: 20%;"><a href="javascript:void(0)"><p>Topics</p></a></td>
					<td class="tabLink" id="questionTypesTabLink" onclick="openTab('questionTypesTab')" style="width: 20%;"><a href="javascript:void(0)"><p>Question Types</p></a></td>
					<td class="tabLink" id="testsTabLink" onclick="openTab('testsTab')" style="width: 20%;"><a href="javascript:void(0)"><p>Tests</p></a></td>
					<td class="tabLink" id="membersTabLink" onclick="openTab('membersTab')" style="width: 20%;"><a href="javascript:void(0)"><p>Members</p></a></td>
				</tr>
			</table>
			
			<div id="sectionsTab" class="tab" style="display: block;">
				<h2>Course Sections</h2>
				<table class="list">
					<#list course.sections as section>
						<tr>
							<td><p>${section.name}</p></td>
							<td style="text-align: right;"><p>Join Code: ${section.joinCode}</p></td>
							<td style="text-align: right;">
								<a href="${urlMappings.renameSectionUrl}/${section.sectionID}"><img src="${urlMappings.images}/icons8-rename-24.png"/></a>
								<a href="${urlMappings.deleteSectionUrl}/${section.sectionID}"><img src="${urlMappings.images}/baseline-delete-24px.svg"/></a>
							</td>
						</tr>
					</#list>
					<tr><td>
						<input type="text" id="sectionName" placeholder="Section Name..."/>
						<span id="sectionRequiredTooltip" class="tooltip" style="display: none"> (Required field) </span>
					</td>
					<td><!-- no join code here --></td>
					<td style="text-align: right;"><img src="${urlMappings.images}/baseline-add-24px.svg" style="cursor: pointer;" onclick="createSection()"/></td></tr>
				</table>
			</div>
			
			<div id="topicsTab" class="tab">
				<h2>Course Topics</h2>
				<table class="list">
					<#list course.topics as topic>
						<tr>
							<td><p>${topic.name}</p></td>
							<td style="text-align: right;">
								<a href="${urlMappings.renameTopicUrl}/${topic.topicID}"><img src="${urlMappings.images}/icons8-rename-24.png"/></a>
								<a href="${urlMappings.deleteTopicUrl}/${topic.topicID}"><img src="${urlMappings.images}/baseline-delete-24px.svg"/></a>
							</td>
						</tr>
					</#list>
					<tr>
						<td>
							<input type="text" id="topicName" placeholder="Topic Name..."/>
							<span id="topicRequiredTooltip" class="tooltip" style="display: none"> (Required field) </span>
						</td>
						<td style="text-align: right;">
							<img src="${urlMappings.images}/baseline-add-24px.svg" style="cursor: pointer;" onclick="createTopic()"/>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="questionTypesTab" class="tab">
				<h2>Course Question Types</h2>
				<table class="list">
					<#list course.questionTypes as type>
						<tr>
							<td><p>${type.name}</p></td>
							<td style="text-align: right;">
								<a href="${urlMappings.renameQuestionTypeUrl}/${type.questionTypeID}"><img src="${urlMappings.images}/icons8-rename-24.png"/></a>
								<a href="${urlMappings.deleteQuestionTypeUrl}/${type.questionTypeID}"><img src="${urlMappings.images}/baseline-delete-24px.svg"/></a>
							</td>
						</tr>
					</#list>
					<tr>
						<td>
							<input type="text" id="questionTypeName" placeholder="Question Type..."/>
							<span id="questionTypeRequiredTooltip" class="tooltip" style="display: none"> (Required field) </span>
						</td>
						<td style="text-align: right;">
							<img src="${urlMappings.images}/baseline-add-24px.svg" style="cursor: pointer;" onclick="createQuestionType()"/>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="testsTab" class="tab">
				<h2>Course Tests</h2>
				<table class="list">
					<#list course.tests as test>
						<tr>
							<td class="testNameColumn"><a href="${urlMappings.editTestUrl}/${test.testID}">${test.name}</a></td>
							<td style="text-align: right;"><div class="dropdown">
								<img src="${urlMappings.images}/baseline-more_vert-24px.svg" class="dropdownButton" onclick="toggleDropdown('${test.testID}.dropdown')"/>
								<div id="${test.testID}.dropdown" class="dropdown-content">
									<table class="list" style="margin: 0px; width: 100%;">
										<tr style="vertical-align: middle;">
											<td style="width: 24px;"><a href="${urlMappings.editTestUrl}/${test.testID}"><img src="${urlMappings.images}/baseline-create-24px.svg"/></a></td>
											<td><a href="${urlMappings.editTestUrl}/${test.testID}"><p class="actionText">Edit</p></a></td>
										</tr>
										<tr style="vertical-align: middle;">
											<td style="width: 24px;"><a href="${urlMappings.renameTestUrl}/${test.testID}"><img src="${urlMappings.images}/icons8-rename-24.png"/></a></td>
											<td><a href="${urlMappings.renameTestUrl}/${test.testID}"><p class="actionText">Rename</p></a></td>
										</tr>
										<tr style="vertical-align: middle;">
											<td style="width: 24px;"><a href="${urlMappings.deleteTestUrl}/${test.testID}"><img src="${urlMappings.images}/baseline-delete-24px.svg"/></a></td>
											<td><a href="${urlMappings.deleteTestUrl}/${test.testID}"><p class="actionText">Delete</p></a></td>
										</tr>
										<tr style="vertical-align: middle;">
											<td style="width: 24px;"><a href="${urlMappings.viewResponsesUrl}/${test.testID}"><img src="${urlMappings.images}/baseline-ballot-24px.svg"/></a></td>
											<td><a href="${urlMappings.viewResponsesUrl}/${test.testID}"><p class="actionText">View Responses</p></a></td>
										</tr>
										
											<#if !test.published>
												<tr style="vertical-align: middle; cursor: pointer;" onclick="publishTest(${test.testID})"><td class="testPublishButtonColumn" style="text-align: right;"><img src="${urlMappings.images}/baseline-visibility_off-24px.svg"/></td> <td class="testPublishTextColumn" style="text-align: left;"><p>Unpublished</p></td></tr>
											<#else>
												<tr style="vertical-align: middle; cursor: pointer;" onclick="unpublishTest(${test.testID})"><td class="testPublishColumn" style="text-align: right;" ><img src="${urlMappings.images}/baseline-visibility-24px.svg"/> <td class="testPublishTextColumn" style="text-align: left;"><p>Published</p></td></tr>
											</#if>
										
									</table>
								</div>
							</div></td>
						</tr>
					</#list>
					<tr>
						<td>
							<input type="text" id="testName" placeholder="Test Name..."/>
							<span id="testRequiredTooltip" class="tooltip" style="display: none"> (Required field) </span>
						</td>
						<td style="text-align: right;">
							<img src="${urlMappings.images}/baseline-add-24px.svg" style="cursor: pointer;" onclick="createTest()"/>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="membersTab" class="tab">
				<h2>Members</h2>
				<h3>Admins</h3>
				<table class="List">
					<#list course.admins?sort as admin>
						<tr class="inert">
							<td style="width: 45%"> ${admin.fullName} </td>
							<td style="width: 45%"> ${admin.emailAddress} </td>
							<td style="width: 10%" align="right">
								<a href="
									<#if userID == admin.userID>
										${urlMappings.deleteCourseUrl}/${course.courseID}
									<#else>
										${urlMappings.kickUrl}/${course.courseID}/${admin.userID}
									</#if>
								">
									<img src="${urlMappings.images}/baseline-exit-24px.svg"/>
								</a>
							</td>
						</tr>
					</#list>
					<tr class="inert">
						<td>Enter email address to invite an administrator</td>
						<td><input type="email" id="inviteEmail" placeholder="Email address..."/> <span id="inviteErrorTooltip" class="tooltip"></span></td>
						<td align="right"><img src="${urlMappings.images}/baseline-add-24px.svg" style="cursor: pointer;" onclick="inviteAdmin()"/></td>
					</tr>
				</table>
				<#list course.sections as section>
				<h3>${section.name}</h3>
					<table class="List">
						<#list section.users?sort as student>
							<tr>
								<td style="width: 45%"> ${student.fullName} </td>
								<td style="width: 45%"> ${student.emailAddress} </td>
								<td style="width: 10%" align="right"><a href="${urlMappings.kickUrl}/${course.courseID}/${student.userID}"><img src="${urlMappings.images}/baseline-exit-24px.svg"/></a></td>
							</tr>
						</#list>
					</table>				
				</#list>
			</div>
			
			<script>
				if (window.location.hash) {
					openTab(window.location.hash.substring(1));
				}
				
				$("#sectionName").keyup(function(e) { if (e.keyCode == 13) { createSection(); } });
				$("#topicName").keyup(function(e) { if (e.keyCode == 13) { createTopic(); } });
				$("#testName").keyup(function(e) { if (e.keyCode == 13) { createTest(); } });
				$("#inviteEmail").keyup(function(e) { if (e.keyCode == 13) { inviteAdmin(); } });
				
			</script>
			
		</div>
	
	</body>

</html>
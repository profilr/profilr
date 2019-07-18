<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/courseAdminViewStyle.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>

		<script>
		
		function openTab(event, tabName) {
			var i, x, tablinks;
			x = document.getElementsByClassName("tab");
			for (i = 0; i < x.length; i++) { x[i].style.display = "none"; }
			tablinks = document.getElementsByClassName("tabLink");
			for (i = 0; i < x.length; i++) { tablinks[i].className = tablinks[i].className.replace(" highlighted", "");	}
			document.getElementById(tabName).style.display = "block";
			event.currentTarget.className += " highlighted";
		}

		function showCreateSectionPane() {
			document.getElementById("createSectionPane").classList.add("show");
		}

		function hideCreateSectionPane() {
			document.getElementById("createSectionPane").classList.remove("show");
		}
		
		</script>
		
	</HEAD>

	<BODY>
	
		<script>
			
			function openTab(event, tabName) {
				var i, x, tablinks;
				x = document.getElementsByClassName("tab");
				for (i = 0; i < x.length; i++) { x[i].style.display = "none"; }
				tablinks = document.getElementsByClassName("tabLink");
				for (i = 0; i < x.length; i++) { tablinks[i].className = tablinks[i].className.replace(" highlighted", "");	}
				document.getElementById(tabName).style.display = "block";
				event.currentTarget.className += " highlighted";
			}
			
		</script>
		
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<h1 id="title">${course.name}</h1>
			
			<table class="tabBar">
				<tr>
					<td class="tabLink highlighted" onclick="openTab(event, 'sectionsTab')" style="width: 33%;"><a href="javascript:void(0)"><p>Sections</p></a></td>
					<td class="tabLink" onclick="openTab(event, 'topicsTab')" style="width: 33%;"><a href="javascript:void(0)"><p>Topics</p></a></td>
					<td class="tabLink" onclick="openTab(event, 'testsTab')" style="width: 33%;"><a href="javascript:void(0)"><p>Tests</p></a></td>
				</tr>
			</table>
			
			<div id="sectionsTab" class="tab" style="display: block;">
				<#if course.sections??>
					<h2>Course Sections</h2>
					<table class="list">
						<#list course.sections as section>
							<tr><th><p>${section.name}</p></th></tr>
						</#list>
						<tr id="createSectionPane" class="popup"><td><input type="text" id="sectionName" placeholder="Section Name..."/></td><td style="text-align: right;"><p>Done</p></td></tr>
					</table>
					<div class="button blue" onclick="showCreateSectionPane()"><p>Create Section</p></div>
				</#if>
			</div>
			
			<div id="topicsTab" class="tab">
				<#if course.topics??>
					<h2>Course Topics</h2>
					<table class="list">
						<#list course.tests as test>
							<tr><th><p>${test.name}</p></th><th><p>${test.id}</p></th></tr>
						</#list>
					</table>
				</#if>
			</div>
			
			<div id="testsTab" class="tab">
				<#if course.tests??>
					<h2>Course Assignments</h2>
					<table class="list">
						<#list course.tests as test>
							<tr><th><p>${test.name}</p></th><th><p>${test.id}</p></th></tr>
						</#list>
					</table>
				</#if>
			</div>
			
		</div>
	
	</BODY>

</HTML>

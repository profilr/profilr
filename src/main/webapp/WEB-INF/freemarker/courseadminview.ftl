<HTML>

	<HEAD>
		<Title>Profilr</Title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/courseAdminViewStyle.css"/>
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
		
	</HEAD>

	<BODY>
		
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<h1 id="title">${course.name}</h1>
			
			<table class="tabBar">
				<tr>
					<td class="tabLink highlighted" onclick="openTab(event, 'sectionsTab')" style="width: 33%;"><p>Sections</p></td>
					<td class="tabLink" onclick="openTab(event, 'topicsTab')" style="width: 33%;"><p>Topics</p></td>
					<td class="tabLink" onclick="openTab(event, 'testsTab')" style="width: 33%;"><p>Test</p></td>
				</tr>
			</table>
			
			<div id="sectionsTab" class="tab" style="display: block;">
				<#if course.sections??>
					<h2>Course Sections</h2>
					<table>
						<#list course.sections as section>
							<tr><th><p>${section.name}</p></th></tr>
						</#list>
					</table>
				</#if>
			</div>
			
			<div id="topicsTab" class="tab">
				<#if course.topics??>
					<h2>Course Topics</h2>
					<table>
						<#list course.tests as test>
							<tr><th><p>${test.name}</p></th><th><p>${test.id}</p></th></tr>
						</#list>
					</table>
				</#if>
			</div>
			
			<div id="testsTab" class="tab">
				<#if course.tests??>
					<h2>Course Assignments</h2>
					<table>
						<#list course.tests as test>
							<tr><th><p>${test.name}</p></th><th><p>${test.id}</p></th></tr>
						</#list>
					</table>
				</#if>
			</div>
			
		</div>
	
	</BODY>

</HTML>
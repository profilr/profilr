<html>

	<head>
		<title>Profilr</title>
		<link rel="stylesheet" href="${urlMappings.stylesheets}/style.css"/>
		<link rel="shortcut icon" type="image/x-icon" href="${urlMappings.favicon}"/>
	</head>

	<body>
	
		<#assign highlight = "About">
		<#include "navbar.ftl">
		
		<div class="bodyContainer">
			<h1>About Profilr</h1>
			<p>(Version <code>${version}</code>)</p>
			<h2>The Team</h2>
			<table>
				<tr>
					<td><img src="${urlMappings.images}/team/jacob.jpg" height="200px"></td>
					<td><img src="${urlMappings.images}/team/avash.jpg" height="200px"></td>
					<td><img src="${urlMappings.images}/team/arjun.jpg" height="200px"></td>
				</tr>
				<tr>
					<td><center><h3>Jacob Acosta</h3></center></td>
					<td><center><h3>Avash Shrestha</h3></center></td>
					<td><center><h3>Arjun Vikram</h3></center></td>
				</tr>
			</table>
			<p>Profilr is an innovative application that helps teachers track student performance over time across different topics and question types, so that they can better understand the knowledge gaps of their students. Profilr gives students a platform to "profile" their tests, or to break down which questions they missed and why. By profiling their tests, students can learn from their mistakes and determine the topics and question types to spend more time studying. The data collected from students' profiles is then displayed in a dashboard for teachers, allowing them to use this data to identify the topics that they need to spend more time reviewing in class. The teacher dashboard contains 4 graphs, a bar graph of student performance on different topics, a bar graph of student performance on different question types, a pie chart of the reasons that students miss questions, and a line graph of student performance over time. In addition, teachers can use filters to compare this data between different sections, individual students, and the class average.
</p>
			<h2>Sponsors</h2>
			<!-- JRebel -->
			<a height="50" href="https://jrebel.com/software/jrebel/" target="_blank" class="sponsor">
				<img height="50" src="${urlMappings.images}/sponsors/jrebel.svg"/>
			</a>
			<!-- Auth0 -->
			<a width="150" height="50" href="https://auth0.com/?utm_source=oss&utm_medium=gp&utm_campaign=oss" target="_blank" class="sponsor">
				<img width="150" height="50" src="${urlMappings.images}/sponsors/auth0.png" alt="Single Sign On & Token Based Authentication for Open Source Projects - Auth0"/>
			</a>
			<!-- Sentry -->
			<a height="50" href="https://sentry.io/welcome/" target="_blank" class="sponsor">
				<img height="50" src="${urlMappings.images}/sponsors/sentry.svg"/>
			</a>
			<!-- Linode -->
			<a height="50" href="https://www.linode.com/" target="_blank" class="sponsor">
				<img height="50" src="${urlMappings.images}/sponsors/linode.svg"/>
			</a>
			<p>Thank you to all the companies who we reached out to who were able to provide us with free subscriptions to their products! We couldn't have made Profilr without their support!</p>
		</div>
		
	</body>

</html>

<div id="navbar">
	<ul>
		<li class="inert"><img src="${urlMappings.images}/logo.png" /></li>
		<li class="<#if !highlight?? || highlight == "Home"> active </#if>">
			<a href="${urlMappings.homeUrl}">Home</a>
		</li>
		<li class="right <#if highlight?? && highlight == "Profile"> active </#if>"> 
			<#if session?? && session.username??>
				<a href="${urlMappings.profileUrl}">${session.username}</a>
			<#else>
				<a href="${urlMappings.splashUrl}">Login</a>
			</#if>
		</li>
	</ul>
</div>

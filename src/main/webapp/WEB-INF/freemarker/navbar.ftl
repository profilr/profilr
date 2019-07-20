<div id="navbar">
	<ul>
		<li class="inert"><img src="${urlMappings.images}/logo.png" /></li>
		<#list navElements as elementName, element>
			<li class="
				<#if element.align?? && element.align == "right">right</#if>
				<#if element.highlighted?? && element.highlighted>active</#if>
			">
			
				<a href="${element.uri}">${element.displayName}</a>
				
			</li>
		</#list>
	</ul>
</div>

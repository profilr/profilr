<div id="navbar">
	<ul>
		<li><p><img src="${urlMappings.images}/logo.png" width="2px" /></p></li>
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
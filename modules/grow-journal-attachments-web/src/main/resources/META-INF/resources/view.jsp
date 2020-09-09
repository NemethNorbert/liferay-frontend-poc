<%--
/**
* Copyright (c) 2000-present Liferay, Inc. All rights reserved.
*
* This library is free software; you can redistribute it and/or modify it under
* the terms of the GNU Lesser General Public License as published by the Free
* Software Foundation; either version 2.1 of the License, or (at your option)
* any later version.
*
* This library is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
* details.
*/
--%>

<%@ include file="/init.jsp" %>


<ul class="nav nav-nested">
	<li class="nav-item">
		<a
			aria-controls="navCollapse01"
			aria-expanded="true"
			class="collapse-icon nav-link"
			data-toggle="collapse"
			href="#navCollapse01"
			role="button"
		>
			Attachments
			<span class="collapse-icon-closed">
				<svg
					class="lexicon-icon lexicon-icon-caret-right"
					focusable="false"
					role="presentation"
				>
					<use href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#caret-right"></use>
				</svg>
			</span>
			<span class="collapse-icon-open">
				<svg
					class="lexicon-icon lexicon-icon-caret-bottom"
					focusable="false"
					role="presentation"
				>
					<use href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#caret-bottom"></use>
				</svg>
			</span>
		</a>
		<div class="collapse show" id="navCollapse01">
			<ul class="nav nav-stacked">
				<c:if test="<%= Validator.isNotNull(journalAttachmentsDisplayContext.getAttachments()) %>">
				<%
				for (String title : journalAttachmentsDisplayContext.getTitle()) {
				%>

					<li class="nav-item">
						<a class="nav-link" href=""><span class="glyphicon glyphicon-paperclip"></span><%= title %></a>
					</li>

				<%
				}
				%>
				</c:if>
			</ul>
		</div>
	</li>
<%@ include file="/init.jsp" %>

<%
Map<String, Object> data = new HashMap<>();

String message = (String)request.getAttribute("data");

data.put("message", message);
%>

<div class="react-component">
	<react:component
		data="<%= data %>"
		module="js/Index.es"
	/>
</div>
<%
weaver.interfaces.action.HtmlToDoc action = new weaver.interfaces.action.HtmlToDoc();
action.doGet(request,response);
out.clear();
out = pageContext.pushBody();
%>
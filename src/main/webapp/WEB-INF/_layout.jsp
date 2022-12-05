<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String pageBody = "/WEB-INF/" + request.getAttribute("pageBody");
    String contextPath = request.getContextPath();
%>
<html>
<jsp:include page="header.jsp"/>
<body>
<jsp:include page="navbar.jsp"/>
<jsp:include page="<%=pageBody%>"/>
<jsp:include page="footer.jsp"/>
</body>
</html>

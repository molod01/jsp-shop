<%@ page import="entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String authError = (String) request.getAttribute("authError");
    String contextPath = request.getContextPath();
    User user = (User) request.getAttribute("authUser");
%>
<nav>
    <a class="home-link" href="<%=contextPath%>/"><img style="width: 1.5em; margin-right: .2em;" src="<%=contextPath%>/img/car.png"><span>Showcase</span></a>
    <% if (user == null) { %>
    <form method="post" class="form-login-main form-inline">
        <% if (authError != null) { %>
        <span style="color: aliceblue" class="auth-error mx-2"><%= authError %></span>
        <% } %>
        <input placeholder="Login" class="form-control mx-1" type="text" name="userLogin"/>
        <input placeholder="Password" class="form-control mx-1" type="password" name="userPassword"/>
        <input type="hidden" name="form-id" value="nav-auth-form"/>
        <input class="btn btn-dark mx-1" type="submit" value="Log in"/>
        <a href="register" class="btn btn-dark mx-1" role="button">Register</a>
    </form>
    <% } else { %>
<%--    <span class="auth-hello">Hello, <%= user.getName() %></span>--%>
    <% if (user.getEmailCode() != null) { %>
    <span style="font-size: 20pt;"><a href="<%=contextPath%>/confirm/"
                                      title="Пошта не підтверджена, уведіть код з листа">&#x1F4E7;</a></span>
    <% } %>
    <a href="<%=contextPath%>/profile">
        <img class="auth-avatar float-right"
             src="<%=contextPath%>/image/<%=user.getAvatar()%>"
             alt="<%=user.getLogin()%>"></a>
    <a class="logout float-right btn btn-dark mx-1" href="?logout=true">Log out</a>
    <% } %>
</nav>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String regError = (String) request.getAttribute("regError");
    String regOk = (String) request.getAttribute("regOk");
    String login = (String) request.getAttribute("login");
    String username = (String) request.getAttribute("username");
    String email = (String) request.getAttribute("email");
    if (login == null) {
        login = "";
    }
    if (username == null) {
        username = "";
    }
    if (email == null) {
        email = "";
    }
%>
<div class="container py-5">
    <div class="row justify-content-center align-items-center">
        <div class="col-12 col-lg-9 col-xl-7">
            <div class="card shadow-2-strong card-registration" style="border-radius: 15px;">
                <div class="card-body p-4 p-md-5">
                    <%if (regOk == null) {%>
                    <h3 class="mb-4 pb-2 pb-md-0 mb-md-5">Registration</h3>
                    <form method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="login">Login</label>
                            <input class="form-control" type="text" id="login" name="login" value="<%=login%>">
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input class="form-control" type="password" id="password" name="password">
                        </div>
                        <div class="form-group">
                            <label for="confirmPassword">Confirm password</label>
                            <input class="form-control" type="password" id="confirmPassword" name="confirmPassword">
                        </div>
                        <div class="form-group">
                            <label for="name">Name</label>
                            <input class="form-control" type="text" id="name" name="name" value="<%=username%>">
                        </div>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input class="form-control" type="email" id="email" name="email" value="<%=email%>">
                        </div>
                        <div class="form-group">
                            <label for="avatar">Avatar</label>
                            <input class="form-control" type="file" id="avatar" name="avatar">
                        </div>
                        <div class="form-group">
                            <% if (regError != null) { %>
                            <p class="text-danger"><%=regError%>
                            </p>
                            <%}%>
                        </div>
                        <div class="mt-4 pt-2">
                            <input class="btn btn-primary btn-lg" type="submit" value="Submit"/>
                        </div>
                    </form>
                    <%} else {%>
                    <h3 class="mb-4 pb-2 pb-md-0 mb-md-5 text-center text-success">
                        <%=regOk%>
                    </h3>
                    <%}%>
                </div>
            </div>
        </div>
    </div>
</div>
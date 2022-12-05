<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.User" %>
<%
    String contextPath = request.getContextPath();
    User authUser = (User) request.getAttribute("authUser");
    String updateError = (String) request.getAttribute("updateError");
    String updateOk = (String) request.getAttribute("updateOk");
    String email = authUser.getEmail() == null ? "" : authUser.getEmail();
%>
<script>
    function updateAvatar() {
        document.getElementById("fileLoader").click();
    }

    function update() {
        document.getElementById("update-image").click();
    }

    function hide(e) {
        setTimeout(() => e.target.addClass('hidden'), 1000);
    }
</script>
<div class="container py-5">
    <div class="row justify-content-center align-items-center">
        <div class="col-12">
            <div class="card shadow-2-strong" style="border-radius: 15px;">
                <div class="card-body p-4 p-md-5">
                    <div class="row">
                        <div class="col-md-5 border-right profile-panel">
                            <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                                <%if (updateOk != null) {%>
                                <div id="notification">
                                    <h5 class="text-center text-success"><%=updateOk%>
                                    </h5>
                                </div>
                                <%}%>
                                <div class="profile-picture-container mt-4 mb-4" onclick="updateAvatar()">
                                    <img class="rounded-circle profile-avatar"
                                         src="<%=contextPath%>/image/<%=authUser.getAvatar()%>">
                                    <div class="update-image">
                                        <form method="post" enctype="multipart/form-data">
                                            <input class="hidden" type="file" id="fileLoader" name="avatar"
                                                   onchange="update()"/>
                                            <input class="hidden" type="submit" id="update-image"/>
                                        </form>
                                        <p id="upload-pic" class="fa">&#xf093;</p>
                                    </div>
                                </div>
                                <span class="font-weight-bold"><%=authUser.getLogin()%></span>
                                <span class="text-black-50"><%=email%></span>
                            </div>
                        </div>
                        <div class="col-md-7">
                            <div class="p-3 py-5">
                                <div class="d-flex justify-content-between align-items-center mb-3">
                                    <h4 class="text-right">Profile Settings</h4>
                                </div>
                                <form method="post" enctype="multipart/form-data">
                                    <div class="row mt-2">
                                        <div class="col-md-6">
                                            <label for="login" class="labels">Login</label>
                                            <input id="login"
                                                   name="login"
                                                   type="text"
                                                   class="form-control"
                                                   placeholder="<%=authUser.getLogin()%>"
                                                   value=""/>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="labels">Name</label>
                                            <input name="name"
                                                   type="text"
                                                   class="form-control"
                                                   value=""
                                                   placeholder="<%=authUser.getName()%>"/>
                                        </div>
                                    </div>
                                    <div class="row mt-3">
                                        <div class="col-md-12"><label class="labels">Email</label>
                                            <input name="email" type="email" class="form-control"
                                                   placeholder="<%=email%>">
                                            <% if (authUser.getEmailCode() != null) { %>
                                            <small><a href="<%=contextPath%>/confirm/">Підтвердіть пошту</a></small>
                                            <%}%>
                                        </div>
                                    </div>
                                    <div class="row mt-3">
                                        <div class="col-md-12">
                                            <label class="labels">Old Password</label>
                                            <input name="oldPass" type="password" class="form-control"
                                                   placeholder="********">
                                        </div>
                                    </div>
                                    <div class="row mt-3">
                                        <div class="col-md-12"><label class="labels">Password</label><input
                                                name="newPass" type="password" class="form-control"
                                                placeholder="********"></div>
                                    </div>
                                    <div class="row mt-3">
                                        <div class="col-md-12"><label class="labels">Confirm Password</label><input
                                                name="confirmNewPass" type="password" class="form-control"
                                                placeholder="********"></div>
                                    </div>
                                        <% if (updateError != null) { %>
                                        <div class="col-md-12">
                                            <p class="text-danger"><%=updateError%>
                                            </p>
                                        </div>
                                        <%}%>
                                    </div>
                                    <div class="mt-5 text-center">
                                        <input style="color: aliceblue" class="btn active profile-button" type="submit"
                                               value="Save Profile">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


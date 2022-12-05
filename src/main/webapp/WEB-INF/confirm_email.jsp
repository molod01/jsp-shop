<%@ page import="entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    User authUser = (User) request.getAttribute("authUser");
    String home = request.getContextPath();
    String confirmed = (String) request.getAttribute("confirmed");
%>
<div class="container py-5">
    <div class="row justify-content-center align-items-center">
        <div class="col-12">
            <div class="card shadow-2-strong" style="border-radius: 15px;">
                <div class="card-body p-4 p-md-5">
                    <div class="confirm-mail">
                        <h1>Підтвердження пошти</h1>
                        <% if ("Ok".equals((confirmed)) || authUser.getEmailCode() == null) {%>
                        <h3 class="text-success">Пошта підтверджена</h3>
                        <% } else if (authUser == null) { %>
                        <h2>Увійдіть до системи</h2>
                        <% } else { %>
                        <% if (confirmed != null) {%>
                        <h3 class="text-danger"><%=confirmed%>
                        </h3>
                        <% } else {%>
                        <form>
                            <div class="form-group">
                                <label for="code">Введіть код з електронного листа</label>
                                <input class="form-control" id="code" name="confirm" type="text"/>
                            </div>
                            <input class="btn btn-primary btn-lg" type="submit" value="Підтвердити"/>
                        </form>
                        <% }
                        } %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

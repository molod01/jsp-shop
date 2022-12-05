<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String contextPath = request.getContextPath();
    Boolean emptyData = (Boolean) request.getAttribute("emptyData");
    String md5Hash = (String) request.getAttribute("md5Hash");
    String sha1Hash = (String) request.getAttribute("sha1Hash");
%>
<div class="container py-5">
    <div class="row justify-content-center align-items-center">
        <div class="col-12 col-lg-9 col-xl-7">
            <div class="card shadow-2-strong card-registration" style="border-radius: 15px;">
                <div class="card-body p-4 p-md-5">
                    <form>
                        <div class="form-outline">
                            <label for="HashString">Data to hash</label>
                            <input class="form-control" type="text" id="HashString" name="userString"/>
                        </div>
                        <div class="mt-4 pt-2">
                            <input class="btn btn-primary btn-lg" type="submit" value="Hash"/>
                        </div>
                    </form>
                    <% if (md5Hash != null) { %>
                    <p>
                        MD5: <%= md5Hash %>
                    </p>
                    <% }
                        if (sha1Hash != null) { %>
                    <p>
                        SHA1: <%= sha1Hash %>
                    </p>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</div>
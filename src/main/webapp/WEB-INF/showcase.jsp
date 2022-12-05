<%@ page import="java.util.List" %>
<%@ page import="entities.Car" %>
<%@ page import="entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    List<Car> cars = (List<Car>) request.getAttribute("cars");
    User authUser = (User) request.getAttribute("authUser");
    String contextPath = request.getContextPath();
%>
<div class="w-75 mx-auto mt-3" style="min-height: 100%">
    <div class="row hidden-md-up my-3">
        <jsp:include page="filters.jsp"/>
    </div>
    <div class="row hidden-md-up my-5">
        <% if (!cars.isEmpty()) { %>
            <% for (Car car : cars) { %>
            <div class="col-md-4">
                <a style="text-decoration: none; color: black;" href="<%=contextPath%>/car?id=<%= car.getId() %>">
                    <div class="card" style="margin-bottom: 1em; min-height: 16em;">
                        <div class="card-block">
                            <div class="group">
                                <img class="card-img-top image" style="object-fit: cover; height: 10em;"
                                     src="<%= car.getPictureURL() %>" />
                            </div>
                            <h4 class="card-title mx-3 mt-3">
                                <%= car.getModel() %>
                            </h4>
                            <p class="card-text mx-3 mb-3">
                                <%= car.getManufacturer() %>
                                <small style="font-size: small;" class="mx-2">
                                    <%= car.getReleaseYear() %>
                                </small>
                            </p>
                            <p class="price card-text mx-3 mb-2 text-right">
                                <%= car.getPrice()%></p>
                        </div>
                    </div>
                </a>
            </div>
            <%}%>
        <%} else {%>
            <div class="col-md-12 my-5">
                <h3 class="text-center">There's no cars...</h3>
            </div>
        <%}%>
    </div>
    <div class="row hidden-md-up my-5">
    <% if (authUser != null) { %>
    <jsp:include page="add-form.jsp"/>
    <% } %>
    </div>
</div>
<script>
    const formatter = new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
    });

    const prices = document.getElementsByClassName("price")
    Array.from(prices).map(price => price.textContent = formatter.format(parseFloat(price.textContent)));
</script>
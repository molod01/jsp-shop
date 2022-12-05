<%@ page import="entities.Car" %>
<%@ page import="entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    Car car = (Car) request.getAttribute("car");
    User authUser = (User) request.getAttribute("authUser");
    String contextPath = request.getContextPath();
%>
<script>
    const editAuto = () => {
        let editWindow = document.getElementsByClassName("edit-window")[0];
        let overlay = document.getElementById("overlay");
        editWindow.classList.remove("hidden");
        overlay.classList.remove("hidden");
    }
    const closeEdit = () => {
        let editWindow = document.getElementsByClassName("edit-window")[0];
        let overlay = document.getElementById("overlay");
        editWindow.classList.add("hidden");
        overlay.classList.add("hidden");
    }
</script>
<div class="w-75 mx-auto mt-3 mb-3" style="min-height: 100%">
    <div class="container-md mx-6">
        <div style="z-index: 0; min-width: 400px;" class="card">
            <img class="card-img-top" style="max-height: 28em; object-fit: cover;" src="<%= car.getPictureURL()%>"
                 alt="auto"/>
            <div class="card-body mx-4">
                <div class="m-3 mb-3" style="display: inline-flex;">
                    <h4 class="model"><%= car.getManufacturer() %></h4>
                    <h4 style="font-weight: 600;"><%= car.getModel() %></h4>
                    <small class="mx-2 mt-2"><%= car.getReleaseYear() %>
                    </small>
                </div>
                <p style="font-size: 11pt;" class="card-text mx-3 mb-3">
                    Engine capacity: <%= car.getEngineCapacity() %>L
                </p>
                <p style="font-size: 11pt;" class="card-text mx-3 mb-3">
                    Color: <%= car.getColor() %>
                </p>
                <p style="font-size: larger;" class="card-text mx-3 mb-3 price text-right"><%= car.getPrice()%>
                </p>
            </div>
            <%if (authUser != null) {%>
            <form class="mb-0" method="post">
                <input type="hidden" name="method" value="DELETE">
                <div class="btn-group w-100" >
                    <button onclick="editAuto()" type="button" class="btn btn-success btn-lg">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                             class="bi bi-pen-fill" viewBox="0 0 16 16">
                            <path d="m13.498.795.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0 1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 .131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001z"/>
                        </svg>
                    </button>
                    <button type="submit" class="btn btn-danger btn-lg ">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                             class="bi bi-trash-fill" viewBox="0 0 16 16">
                            <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"/>
                        </svg>
                    </button>
                </div>
            </form>
            <%}%>
        </div>
        <div onclick="closeEdit()" id="overlay" class="hidden">
        </div>
        <div style="position: absolute;
                top: 50%; left: 50%;
                transform: translate(-50%, -60%);"
             class="edit-window hidden container-sm">
            <jsp:include page="edit-form.jsp"/>
        </div>
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
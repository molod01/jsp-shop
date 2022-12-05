<%@ page import="entities.Car" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
  Car car = (Car) request.getAttribute("car");
  String contextPath = request.getContextPath();
%>
<div style="z-index: 1" class="card p-5">
  <h4 class="text-center">Edit auto</h4>
  <form method="post" class="mx-auto mb-5 mt-3">
    <input type="hidden" name="method" value="PUT">
    <div class="row">
      <div class="col my-3">
        <label for="model" class="form-label">Model</label>
        <input value="<%=car.getModel() %>" type="text" class="form-control" id="model" name="model" />
      </div>
      <div class="col my-3">
        <label for="manufacturer" class="form-label">Manufacturer</label>
        <input value="<%=car.getManufacturer()%>" type="text" class="form-control" id="manufacturer" name="manufacturer" />
      </div>
    </div>
    <div class="row">
      <div class="col mb-3">
        <label for="releaseYear" class="form-label">Release Year</label>
        <input min="1900" max="2099" value="<%=car.getReleaseYear()%>" type="number" class="form-control" id="releaseYear" name="releaseYear" />
      </div>
      <div class="col mb-3">
        <label for="engineCap" class="form-label">Engine Capacity</label>
        <input pattern="^\d*(\.\d{0,2})?$" value="<%=car.getEngineCapacity()%>" oninput="this.value=this.value.replace(/[^0-9,.]{2,}|[0-9]{2,}/g,'');" class="form-control"
               id="engineCap" name="engineCapacity" />
      </div>
    </div>
    <div class="row">
      <div class="col mb-3">
        <label for="price" class="form-label">Price</label>
        <input value="<%=car.getPrice()%>" type="number" class="form-control" id="price" name="price" />
      </div>
      <div class="col mb-3">
        <label for="color" class="form-label">Color</label>
        <input value="<%=car.getColor()%>" type="text" class="form-control" id="color" name="color" />
      </div>
    </div>
    <div class="mb-3">
      <label for="photo" class="form-label">Picture link</label>
      <input type="text" class="form-control" id="photo" name="pictureURL" />
    </div>
    <div class="btn-group w-100">
      <button type="submit" class="btn btn-outline-dark btn-outline-secondary">Edit</button>
      <button type="button" onclick="closeEdit()" class="btn btn-outline-dark btn-outline-secondary">Close</button>
    </div>
  </form>
</div>
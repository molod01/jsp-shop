<%@ page import="entities.Filters" %>
<%
  Filters filters = (Filters) request.getAttribute("filters");
  Filters query = (Filters) request.getAttribute("query");
%>
<%if(filters != null) {%>
<form id="filter" method="get" class="w-100 mx-auto mt-3 mb-3" style="justify-content: space-between;">
  <h4 class="text-center mb-3">Filters</h4>
  <%if(!filters.getManufacturers().isEmpty()){ %>
  <div class="my-2">
    <button class="filter-btn btn-outline-secondary btn btn-light w-100" type="button" data-bs-toggle="collapse" data-bs-target="#collapse" aria-expanded="true" aria-controls="collapse">Manufacturer</button>
    <div class="collapse p-3 grid" id="collapse" >
      <% for(String man : filters.getManufacturers())  { %>
      <div class="form-check g-col-4 mx-auto">
        <input id="<%=man%>" class="form-check-input" type="checkbox" name="manufacturers" value="<%=man%>"
          <%if(query.getManufacturers().contains(man)){%> checked="checked">
        <%} else { %>><%}%>
        <label class="form-check-label" for="<%=man%>"><%=man%></label>
      </div>
      <%}%>
    </div>
  </div>
  <%}%>
  <%if(!filters.getReleaseYears().isEmpty()){ %>
  <div class="my-2">
    <button class="filter-btn btn-outline-secondary btn btn-light w-100" type="button" data-bs-toggle="collapse" data-bs-target="#collapse1" aria-expanded="true" aria-controls="collapse">Release Year</button>
    <div class="collapse p-3 grid" id="collapse1" >
      <% for(String year : filters.getReleaseYears())  { %>
      <div class="form-check g-col-4 mx-auto">
        <input id="<%=year%>" class="form-check-input" type="checkbox" name="releaseYears" value="<%=year%>"
          <%if(query.getReleaseYears().contains(year)){%> checked="checked">
        <%} else { %>><%}%>
        <label class="form-check-label" for="<%=year%>"><%=year%></label>
      </div>
      <%}%>
    </div>
  </div>
  <%}%>
  <%if(!filters.getColors().isEmpty()){ %>
  <div class="my-2">
    <button class="filter-btn btn-outline-secondary btn btn-light w-100" type="button" data-bs-toggle="collapse" data-bs-target="#collapse2" aria-expanded="true" aria-controls="collapse">Color</button>
    <div class="collapse p-3 grid" id="collapse2">
      <% for(String color : filters.getColors())  { %>
      <div class="form-check g-col-4 mx-auto">
        <input class="form-check-input" type="checkbox" name="colors" value="<%=color%>"
          <%if(query.getColors().contains(color)){%> checked="checked">
        <%} else { %>><%}%>
        <label class="form-check-label" for="<%=color%>"><%=color%></label>
      </div>
      <%}%>
    </div>
  </div>
  <%}%>
  <%if(!filters.getEngineCapacities().isEmpty()){ %>
  <div class="my-2">
    <button class="filter-btn btn-outline-secondary btn btn-light w-100" type="button" data-bs-toggle="collapse" data-bs-target="#collapse3" aria-expanded="true" aria-controls="collapse">Engine Capacity</button>
    <div class="collapse p-3 grid" id="collapse3" >
      <% for(String ec : filters.getEngineCapacities())  { %>
      <div class="form-check g-col-4 mx-auto">
        <input class="form-check-input" type="checkbox" name="engineCapacities" value="<%=ec%>"
               <%if(query.getEngineCapacities().contains(ec)){%>checked="checked">
        <%} else { %>><%}%>
        <label class="form-check-label" for="<%=ec%>"><%=ec%>L</label>
      </div>
      <%}%>
    </div>
  </div>
  <%}%>
  <div class="my-2">
    <button class="filter-btn btn-outline-secondary btn btn-light w-100" type="button" data-bs-toggle="collapse" data-bs-target="#collapse4" aria-expanded="true" aria-controls="collapse">Price</button>
    <div class="collapse" id="collapse4" >
      <div class="input-group my-2">
        <span class="input-group-text">$</span>
        <input onclick="if(value === ''){value='<%=filters.getMinPrice()%>'; name='minPrice'}" class="form-control" type="number" placeholder="<%=filters.getMinPrice()%>" min="<%=filters.getMinPrice()%>"
          <%if(query.getMinPrice() != null){%>
               name="minPrice" value='<%=query.getMinPrice()%>'
          <%}%>>
        <span class="input-group-text">$</span>
        <input onclick="if(value === ''){value='<%=filters.getMaxPrice()%>'; name='maxPrice'}" class="form-control" type="number" placeholder="<%=filters.getMaxPrice()%>" max="<%=filters.getMaxPrice()%>"
          <%if(query.getMaxPrice() != null){%>
               name="maxPrice" value='<%=query.getMaxPrice()%>'
          <%}%>>
      </div>
    </div>
  </div>
  <div class="btn-group w-100">
    <button onclick="clearCheckboxes()" class="filter-btn btn btn-outline-secondary btn-light w-50">Reset</button>
    <button type="submit" class="filter-btn btn btn-outline-secondary btn-light w-50">Search</button>
  </div>
</form>
<%}%>
<script>
  const clearCheckboxes = () => {
    Array.from(document.querySelectorAll('input[type=checkbox]'))
            .map(ch => ch.checked = '');
    Array.from(document.querySelectorAll('input[type=number]'))
            .map(price => price.removeAttribute('name'))
  }
  let filterSections = [
    document.querySelectorAll('#collapse input'),
    document.querySelectorAll('#collapse1 input'),
    document.querySelectorAll('#collapse2 input'),
    document.querySelectorAll('#collapse3 input'),
  ];
  let sections = document.querySelectorAll('.collapse')
  let filterButtons = document.querySelectorAll('.filter-btn')

  for(let i = 0; i < filterSections.length; i++){
    if(Array.from(filterSections[i]).filter(ch => ch.checked === true).length > 0){
      sections[i].classList.add('show');
      filterButtons[i].classList.add('active');
    }
  }
  if(document.querySelectorAll('#collapse4 input')[0].value){
    sections[4].classList.add('show');
    filterButtons[4].classList.add('active');
  }
</script>
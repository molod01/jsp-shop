<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String contextPath = request.getContextPath();
%>
<form action="<%=contextPath%>/" method="post" class="w-100 mx-auto mb-3 mt-3">
  <h4 class="text-center">Add auto</h4>
  <div class="row">
    <div class="col my-3">
      <label for="model" class="form-label">Model</label>
      <input type="text" class="form-control" id="model" name="model" required/>
    </div>
    <div class="col my-3">
      <label for="manufacturer" class="form-label">Manufacturer</label>
      <input type="text" class="form-control" id="manufacturer" name="manufacturer" required/>
    </div>
  </div>
  <div class="row">
    <div class="col mb-3">
      <label for="releaseYear" class="form-label">Release Year</label>
      <input min="1900" max="2099" type="number" class="form-control" id="releaseYear" name="releaseYear" required/>
    </div>
    <div class="col mb-3">
      <label for="engineCap" class="form-label">Engine Capacity</label>
      <input pattern="^\d*(\.\d{0,2})?$"  class="form-control"
             id="engineCap" name="engineCapacity" required/>
    </div>
  </div>
  <div class="row">
    <div class="col mb-3">
      <label for="price" class="form-label">Price</label>
      <input type="number" class="form-control" id="price" name="price" required/>
    </div>
    <div class="col mb-3">
      <label for="color" class="form-label">Color</label>
      <input onfocusout="preSearch()" type="text" class="form-control" id="color" name="color" required/>
    </div>
  </div>
  <div class="mb-5">
    <label for="photo" class="form-label">Picture link</label>
    <input type="text" class="form-control" id="photo" name="pictureURL" />
  </div>
  <button type="submit" class="btn btn-outline-dark w-100 mb-5">Sumbit</button>
</form>
<script>
  const options = {
    method: 'GET',
    headers: {
      'X-RapidAPI-Key': 'b9ae5338cdmsh5a9a21091cb064fp193584jsnbd73bf8dbe29',
      'X-RapidAPI-Host': 'bing-image-search1.p.rapidapi.com',
      'Access-Control-Allow-Origin': '*'
    }
  };
  const getImage = (keyword) => {
    let query = `https://bing-image-search1.p.rapidapi.com/images/search?q=` + keyword + `&count=1`
    return fetch(query, options)
            .then(response => response.json())
            .then(json => {
              if(json.value[0].contentUrl){
                 return json.value[0].contentUrl;
              } else return "";
            })
            .catch(err => console.error(err));
  }
  const preSearch = () => {
    let carPreInfo = {
      model: document.querySelector('#model').value,
      manufacturer: document.querySelector('#manufacturer').value,
      releaseYear: document.querySelector('#releaseYear').value,
      color: document.querySelector('#color').value

    };
    carPreInfo = Object.values(carPreInfo);
    if(carPreInfo.filter(e => e !== "").length === 4){
      getImage(carPreInfo.toString())
              .then(result =>
                      document.querySelector('#photo').value = result
              );
    }
  }
</script>

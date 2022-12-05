package entities;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Car {
    private String id;
    private String model;
    private String manufacturer;
    private Integer releaseYear;
    private Double engineCapacity;
    private Double price;
    private String color;
    private String pictureURL;

    public Car() {
        setPictureURL("/WebBasics/image/24579b36-6276-476f-a1da-e046783420bf.png");
    }

    public Car(HttpServletRequest req) throws SQLException {
        String id = req.getParameter("id");
        String model = req.getParameter("model");
        String manufacturer = req.getParameter("manufacturer");
        String releaseYear = req.getParameter("releaseYear");
        String engineCapacity =req.getParameter("engineCapacity");
        String price = req.getParameter("price");
        String color = req.getParameter("color");
        String pictureURL = req.getParameter("pictureURL");

        //validation
        if(id != null && !id.isEmpty()) setId(id);
        if(model != null && !model.isEmpty()) setModel(model);
        if(manufacturer != null && !manufacturer.isEmpty()) setManufacturer(manufacturer);
        if(releaseYear != null && !releaseYear.isEmpty()) setReleaseYear(Integer.parseInt(releaseYear));
        if(engineCapacity != null && !engineCapacity.isEmpty()) setEngineCapacity(Double.parseDouble(engineCapacity));
        if(price != null && !price.isEmpty()) setPrice(Double.parseDouble(price));
        if(color != null && !color.isEmpty()) setColor(color);
        if(pictureURL != null && !pictureURL.isEmpty()) setPictureURL(pictureURL);

    }

    public Car(ResultSet res) throws SQLException {
        this.setId(res.getString("id"));
        this.setModel(res.getString("model"));
        this.setManufacturer(res.getString("manufacturer"));
        this.setReleaseYear(res.getInt("releaseYear"));
        this.setEngineCapacity(res.getDouble("engineCapacity"));
        this.setPrice(res.getDouble("price"));
        this.setColor(res.getString("color"));
        this.setPictureURL(res.getString("pictureURL"));
    }

    public String getId() {
        return id;
    }

    public Car setId(String id) {
        this.id = id;
        return this;
    }

    public String getModel() {
        return model;
    }

    public Car setModel(String model) {
        this.model = model;
        return this;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Car setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public Car setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public Double getEngineCapacity() {
        return engineCapacity;
    }

    public Car setEngineCapacity(Double engineCapacity) {
        this.engineCapacity = engineCapacity;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Car setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getColor() {
        return color;
    }

    public Car setColor(String color) {
        this.color = color;
        return this;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public Car setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
        return this;
    }
}

package entities;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Filters {
    List<String> manufacturers = new ArrayList<>();
    List<String> releaseYears = new ArrayList<>();
    List<String> colors = new ArrayList<>();
    List<String> engineCapacities = new ArrayList<>();
    Double minPrice;
    Double maxPrice;

    public Filters(List<Car> cars) {
        setManufacturers(new HashSet<String>(cars.stream().map(Car::getManufacturer).toList()).stream().toList());
        setReleaseYears(new HashSet<String>(cars.stream().map(car -> car.getReleaseYear().toString()).toList()).stream().toList());
        setColors(new HashSet<String>(cars.stream().map(Car::getColor).toList()).stream().toList());
        setEngineCapacities(new HashSet<String>(cars.stream().map(car -> car.getEngineCapacity().toString()).toList()).stream().toList());
        List<Double> prices = cars.stream().map(Car::getPrice).toList();
        setMinPrice(prices.stream().min(Double::compare).get());
        setMaxPrice(prices.stream().max(Double::compare).get());
    }

    public Filters(HttpServletRequest req) {
        if (req.getQueryString() != null) {
            req.getParameterMap().keySet()
                    .forEach(property -> {
                        if(!property.equals("minPrice") && !property.equals("maxPrice")) {
                            String setterName = "set" +
                                    property.substring(0, 1).toUpperCase() +
                                    property.substring(1);
                            try {
                                this.getClass().getMethod(setterName, List.class)
                                        .invoke(this, Arrays.stream(req.getParameterValues(property)).toList());
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                System.out.println(e);
                                throw new RuntimeException(e);
                            }
                        }
                    });
            String minPrice = req.getParameter("minPrice");
            String maxPrice = req.getParameter("maxPrice");
            if(minPrice != null){
                setMinPrice(Double.parseDouble(minPrice));
            }
            if(maxPrice != null){
                setMaxPrice(Double.parseDouble(maxPrice));
            }

        }
    }

    public List<Car> apply(List<Car> cars){
        if (!getManufacturers().isEmpty()) {
            cars = cars.stream().filter(car -> getManufacturers().contains(car.getManufacturer())).toList();
        }
        if (!getReleaseYears().isEmpty()) {
            cars = cars.stream().filter(car -> getReleaseYears().contains(car.getReleaseYear().toString())).toList();
        }
        if (!getColors().isEmpty()) {
            cars = cars.stream().filter(car -> getColors().contains(car.getColor())).toList();
        }
        if (!getEngineCapacities().isEmpty()) {
            cars = cars.stream().filter(car -> getEngineCapacities().contains(car.getEngineCapacity().toString())).toList();
        }
        if (getMinPrice() != null && getMaxPrice() != null) {
            cars = cars.stream().filter(car -> car.getPrice() >= getMinPrice() && car.getPrice() <= getMaxPrice()).toList();
        }
        return cars;
    }

    public List<String> getManufacturers() {
        return manufacturers;
    }

    public Filters setManufacturers(List<String> manufacturers) {
        this.manufacturers = manufacturers;
        return this;
    }

    public List<String> getReleaseYears() {
        return releaseYears;
    }

    public Filters setReleaseYears(List<String> releaseYears) {
        this.releaseYears = releaseYears;
        return this;
    }

    public List<String> getColors() {
        return colors;
    }

    public Filters setColors(List<String> colors) {
        this.colors = colors;
        return this;
    }

    public List<String> getEngineCapacities() {
        return engineCapacities;
    }

    public Filters setEngineCapacities(List<String> engineCapacities) {
        this.engineCapacities = engineCapacities;
        return this;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public Filters setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public Filters setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }
}

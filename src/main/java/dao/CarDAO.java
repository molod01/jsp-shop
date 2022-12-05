package dao;

import entities.Car;
import services.data.DataService;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarDAO {
    private final Connection connection;
    @Inject
    public CarDAO(DataService dataService) {
        this.connection = dataService.getConnection();
    }

    public String add(Car car){
        car.setId(UUID.randomUUID().toString());
        String sql = "INSERT INTO Cars" +
                "(`id`,`model`,`manufacturer`,`releaseYear`,`engineCapacity`,`price`,`color`,`pictureURL`) " +
                "VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement prep = connection.prepareStatement(sql)) {
            prep.setString(1, car.getId());
            prep.setString(2, car.getModel());
            prep.setString(3, car.getManufacturer());
            prep.setInt(   4, car.getReleaseYear());
            prep.setDouble(5, car.getEngineCapacity());
            prep.setDouble(6, car.getPrice());
            prep.setString(7, car.getColor());
            prep.setString(8, car.getPictureURL());
            prep.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return car.getId();
    }
    public List<Car> getAll(){
        try(Statement statement = connection.createStatement()) {
            List<Car> cars = new ArrayList<>();
            ResultSet res = statement.executeQuery("SELECT * FROM Cars;");
            while (res.next()) {
                Car car = new Car(res);
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Car getById(String id) {
        String sql = "SELECT c.* FROM Cars c WHERE c.`id` = ?";
        try (PreparedStatement prep = connection.prepareStatement(sql)) {
            prep.setString(1, id);
            ResultSet res = prep.executeQuery();
            if (res.next()) return new Car(res);
        } catch (Exception ex) {
            System.out.println("CarDAO::getById() " + ex.getMessage()
                    + "\n" + sql + " -- " + id);
        }
        return null;
    }

    public String update(Car car) {

        Car oldCar = getById(car.getId());
        String sql = "UPDATE Cars SET ";
        if (!oldCar.equals(car)) {
            System.out.println("CHANGED");
            if(car.getModel() != null) sql += String.format("`model` = '%s', ", car.getModel());
            if(car.getManufacturer() != null) sql += String.format("`manufacturer` = '%s', ", car.getManufacturer());
            if(car.getReleaseYear() != null) sql += String.format("`releaseYear` = '%s', ", car.getReleaseYear());
            if(car.getColor() != null) sql += String.format("`color` = '%s', ", car.getColor());
            if(car.getEngineCapacity() != null)  sql += String.format("`engineCapacity` = '%s', ", car.getEngineCapacity());
            if(car.getPrice() != null) sql += String.format("`price` = '%s', ", car.getPrice());
            if(car.getPictureURL() != null) sql += String.format("`pictureURL` = '%s', ", car.getPictureURL());
        }
        sql = sql.substring(0, sql.length() - 2);
        sql += String.format(" WHERE id = '%s'", car.getId());
        System.out.println(sql);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return car.getId();
    }
    public boolean deleteById(String id) {
        String sql = "DELETE c.* FROM Cars c WHERE c.`id` = ?";
        try (PreparedStatement prep = connection.prepareStatement(sql)) {
            prep.setString(1, id);
            prep.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("CarDAO::deleteById() " + ex.getMessage()
                    + "\n" + sql + " -- " + id);
        }
        return false;
    }
}

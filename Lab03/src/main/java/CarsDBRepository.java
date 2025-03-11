import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository {

    private JdbcUtils dbUtils;


    private static final Logger logger = LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM Masini WHERE manufacturer = ?";
        logger.traceEntry("saving task {}", manufacturerN);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, manufacturerN);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String manufacturer = resultSet.getString("manufacturer");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                cars.add(new Car(manufacturer, model, year));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }


    @Override
    public List<Car> findBetweenYears(int min, int max) {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM Masini WHERE year BETWEEN ? AND ?";
        JdbcUtils.logger.traceEntry("saving task {}", min, max );
        Connection con = dbUtils.getConnection();
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, min);
            statement.setInt(2, max);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String manufacturer = resultSet.getString("manufacturer");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                cars.add(new Car(manufacturer, model, year));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public void add(Car elem){
        JdbcUtils.logger.traceEntry("saving task {}", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Masini(manufacturer, model, year) values(?,?,?)")) {
            preStmt.setString(1, elem.getManufacturer());
            preStmt.setString(2, elem.getModel());
            preStmt.setInt(3, elem.getYear());
            int result = preStmt.executeUpdate();
            JdbcUtils.logger.trace("Saved {} instances", result);
        } catch (SQLException ex) {
            JdbcUtils.logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        JdbcUtils.logger.traceExit();
    }


    @Override
    public void update(Integer integer, Car elem) {
        String query = "UPDATE Masini SET manufacturer=?, model=?, year=? WHERE id=?";
        JdbcUtils.logger.traceEntry("saving task {}", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, elem.getManufacturer());
            statement.setString(2, elem.getModel());
            statement.setInt(3, elem.getYear());
            statement.setInt(4, integer);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<Car> findAll() {
        JdbcUtils.logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Masini")) {
            try(ResultSet result =preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String manufacturer = result.getString("manufacturer");
                    String model = result.getString("model");
                    int year = result.getInt("year");
                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            JdbcUtils.logger.error(e);
            System.err.println("Error DB" + e);
            e.printStackTrace();
        }
        JdbcUtils.logger.traceExit(cars);
        return cars;
    }
}

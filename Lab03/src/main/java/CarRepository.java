import java.util.List;
import org.apache.logging.log4j.LogManager;

public interface CarRepository extends Repository<Integer,Car> {
    List<Car> findByManufacturer(String manufacturer);
    List<Car> findBetweenYears(int min, int max);
}

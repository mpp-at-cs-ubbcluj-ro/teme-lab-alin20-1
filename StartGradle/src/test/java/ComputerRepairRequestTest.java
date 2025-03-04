import ro.mpp2024.model.ComputerRepairRequest;
import ro.mpp2024.ComputerShopMain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ComputerRepairRequestTest {
    @Test
    @DisplayName("Primul test")
    public void exempluTest1() {
       ComputerRepairRequest compr = new ComputerRepairRequest(5,"Mihai", "Strada Tepes 5", "0755678432", "Toshiba", "2022", "driver problem" );
        assertEquals("0755678432", compr.getPhoneNumber());
        assertEquals("Toshiba", compr.getModel());

        //assertEquals(2,2);
    }
    @Test
    @DisplayName("Al doilea test")
    public void exempluTest2(){
        assertEquals(10,10, "numerele sunt egale!");
    }

}

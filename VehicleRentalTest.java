import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class VehicleRentalTest {

    @Test
    public void testLicensePlate() {
        
        Vehicle v1 = new Car("Toyota", "Corolla", 2022, 5);
        v1.setLicensePlate("AAA100");
        assertEquals("AAA100", v1.getLicensePlate());

        Vehicle v2 = new Car("Honda", "Civic", 2021, 5);
        v2.setLicensePlate("ABC567");
        assertEquals("ABC567", v2.getLicensePlate());

        Vehicle v3 = new Car("Ford", "Focus", 2020, 5);
        v3.setLicensePlate("ZZZ999");
        assertEquals("ZZZ999", v3.getLicensePlate());

        Vehicle v4 = new Car("Toyota", "Camry", 2022, 5);
        assertThrows(IllegalArgumentException.class, () -> {
            v4.setLicensePlate("");
        });

        Vehicle v5 = new Car("Toyota", "Camry", 2022, 5);
        assertThrows(IllegalArgumentException.class, () -> {
            v5.setLicensePlate(null);
        });

        Vehicle v6 = new Car("Toyota", "Camry", 2022, 5);
        assertThrows(IllegalArgumentException.class, () -> {
            v6.setLicensePlate("AAA1000");
        });

        Vehicle v7 = new Car("Toyota", "Camry", 2022, 5);
        assertThrows(IllegalArgumentException.class, () -> {
            v7.setLicensePlate("ZZZ99");
        });
    }
}
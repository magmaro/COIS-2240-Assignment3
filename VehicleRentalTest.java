import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class VehicleRentalTest {
	    @Test
	    void testLicensePlate() {
	        Car car1 = new Car("Toyota", "Corolla", 2019, 5);
	        Car car2 = new Car("Honda", "Civic", 2021, 5);
	        Car car3 = new Car("Ford", "Focus", 2024, 5);

	        car1.setLicensePlate("AAA100");
	        car2.setLicensePlate("ABC567");
	        car3.setLicensePlate("ZZZ999");
	        
	        // Valid plates
	        assertEquals("AAA100", car1.getLicensePlate());
	        assertEquals("ABC567", car2.getLicensePlate());
	        assertEquals("ZZZ999", car3.getLicensePlate());

	        assertFalse("".matches("[A-Z]{3}\\d{3}"));
	        assertFalse("AAA1000".matches("[A-Z]{3}\\d{3}"));
	        assertFalse("ZZZ99".matches("[A-Z]{3}\\d{3}"));

	        Car invalidCar = new Car("Mazda", "3", 2020, 5);

	        // Invalid plates
	        assertThrows(IllegalArgumentException.class, () -> invalidCar.setLicensePlate(""));
	        assertThrows(IllegalArgumentException.class, () -> invalidCar.setLicensePlate(null));
	        assertThrows(IllegalArgumentException.class, () -> invalidCar.setLicensePlate("AAA1000"));
	        assertThrows(IllegalArgumentException.class, () -> invalidCar.setLicensePlate("ZZZ99"));
	    }
}

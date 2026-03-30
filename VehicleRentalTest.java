import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

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
        assertThrows(IllegalArgumentException.class, () -> v4.setLicensePlate(""));

        Vehicle v5 = new Car("Toyota", "Camry", 2022, 5);
        assertThrows(IllegalArgumentException.class, () -> v5.setLicensePlate(null));

        Vehicle v6 = new Car("Toyota", "Camry", 2022, 5);
        assertThrows(IllegalArgumentException.class, () -> v6.setLicensePlate("AAA1000"));

        Vehicle v7 = new Car("Toyota", "Camry", 2022, 5);
        assertThrows(IllegalArgumentException.class, () -> v7.setLicensePlate("ZZZ99"));
    }

    @Test
    public void testRentAndReturnVehicle() {
        Vehicle vehicle = new Car("Toyota", "Corolla", 2022, 5);
        vehicle.setLicensePlate("AAA100");
        Customer customer = new Customer(1, "John Doe");

        assertEquals(Vehicle.VehicleStatus.Available, vehicle.getStatus());

        RentalSystem rentalSystem = RentalSystem.getInstance();

        boolean rentSuccess = rentalSystem.rentVehicle(vehicle, customer, java.time.LocalDate.now(), 100.0);
        assertTrue(rentSuccess);
        assertEquals(Vehicle.VehicleStatus.Rented, vehicle.getStatus());

        boolean rentAgain = rentalSystem.rentVehicle(vehicle, customer, java.time.LocalDate.now(), 100.0);
        assertFalse(rentAgain);

        boolean returnSuccess = rentalSystem.returnVehicle(vehicle, customer, java.time.LocalDate.now(), 0.0);
        assertTrue(returnSuccess);
        assertEquals(Vehicle.VehicleStatus.Available, vehicle.getStatus());

        boolean returnAgain = rentalSystem.returnVehicle(vehicle, customer, java.time.LocalDate.now(), 0.0);
        assertFalse(returnAgain);
    }

    @Test
    public void testSingletonRentalSystem() throws Exception {
        Constructor<RentalSystem> constructor = RentalSystem.class.getDeclaredConstructor();
        int modifiers = constructor.getModifiers();
        assertEquals(Modifier.PRIVATE, modifiers);

        RentalSystem instance = RentalSystem.getInstance();
        assertNotNull(instance);
    }
}
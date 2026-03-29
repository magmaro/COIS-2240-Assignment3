import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class RentalSystem {
	
	// Private static instance
	private static RentalSystem instance;
	
	private List<Vehicle> vehicles = new ArrayList<>();
	private List<Customer> customers = new ArrayList<>();
	private RentalHistory rentalHistory = new RentalHistory();
	
	// Private constructor
	private RentalSystem() {}
	
	// Public accessor method
	public static RentalSystem getInstance() {
		if (instance == null) {
			instance = new RentalSystem(); // Fixed: instantiate RentalSystem, not Singleton
		}
		return instance;
	}

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        saveVehicle(vehicle);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        saveCustomer(customer);
    }

    public void rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.Available) {
            vehicle.setStatus(Vehicle.VehicleStatus.Rented);
            rentalHistory.addRecord(new RentalRecord(vehicle, customer, date, amount, "RENT"));
            System.out.println("Vehicle rented to " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not available for renting.");
        }
    }

    public void returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.Rented) {
            vehicle.setStatus(Vehicle.VehicleStatus.Available);
            rentalHistory.addRecord(new RentalRecord(vehicle, customer, date, extraFees, "RETURN"));
            System.out.println("Vehicle returned by " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not rented.");
        }
    }    

    
    private void saveVehicle(Vehicle vehicle) {
        try (FileWriter fwriter = new FileWriter("vehicles.txt", true)) {
            fwriter.write(
                vehicle.getLicensePlate() + "," +
                vehicle.getMake() + "," +
                vehicle.getModel() + "," +
                vehicle.getYear() + "," +
                vehicle.getStatus() + "\n"
            );
        } catch (IOException e) {
            System.out.println("Error, couldn't save vehicle");
        }
    }
    
    private void saveCustomer(Customer customer) {
        try (FileWriter cwriter = new FileWriter("customers.txt", true)) {
            cwriter.write(
                customer.getCustomerId() + "," +
                customer.getCustomerName() + "\n"
            );
        } catch (IOException e) {
            System.out.println("Error, couldn't save customer details");
        }
    }
    
    private void saveRecord(RentalRecord record) {
        try (FileWriter rwriter = new FileWriter("rental_records.txt", true)) {
            rwriter.write(
                record.getRecordType() + "," +
                record.getVehicle().getLicensePlate() + "," +
                record.getCustomer().getCustomerName() + "," +
                record.getRecordDate() + "," +
                record.getTotalAmount() + "\n"
            );
        } catch (IOException e) {
            System.out.println("Error, couldn't save rental record");
        }
    }

    private void saveAllVehicles() {
        try (FileWriter fwriter = new FileWriter("vehicles.txt")) {
            for (Vehicle v : vehicles) {
                fwriter.write(
                    v.getLicensePlate() + "," +
                    v.getMake() + "," +
                    v.getModel() + "," +
                    v.getYear() + "," +
                    v.getStatus() + "\n"
                );
            }
        } catch (IOException e) {
            System.out.println("Error saving all vehicles");
        }
    }
    
    public void displayVehicles(Vehicle.VehicleStatus status) {
        if (status == null) {
            System.out.println("\n=== All Vehicles ===");
        } else {
            System.out.println("\n=== " + status + " Vehicles ===");
        }
        
        System.out.printf("|%-16s | %-12s | %-12s | %-12s | %-6s | %-18s |%n", 
            " Type", "Plate", "Make", "Model", "Year", "Status");
        System.out.println("|--------------------------------------------------------------------------------------------|");
    	  
        boolean found = false;
        for (Vehicle vehicle : vehicles) {
            if (status == null || vehicle.getStatus() == status) {
                found = true;
                String vehicleType;
                if (vehicle instanceof Car) {
                    vehicleType = "Car";
                } else if (vehicle instanceof Minibus) {
                    vehicleType = "Minibus";
                } else if (vehicle instanceof PickupTruck) {
                    vehicleType = "Pickup Truck";
                } else {
                    vehicleType = "Unknown";
                }
                System.out.printf("| %-15s | %-12s | %-12s | %-12s | %-6d | %-18s |%n", 
                    vehicleType, vehicle.getLicensePlate(), vehicle.getMake(), vehicle.getModel(), vehicle.getYear(), vehicle.getStatus().toString());
            }
        }
        if (!found) {
            if (status == null) {
                System.out.println("  No Vehicles found.");
            } else {
                System.out.println("  No vehicles with Status: " + status);
            }
        }
        System.out.println();
    }

    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println("  " + c.toString());
        }
    }
    
    public void displayRentalHistory() {
        if (rentalHistory.getRentalHistory().isEmpty()) {
            System.out.println("  No rental history found.");
        } else {
            System.out.printf("|%-10s | %-12s | %-20s | %-12s | %-12s |%n", 
                " Type", "Plate", "Customer", "Date", "Amount");
            System.out.println("|-------------------------------------------------------------------------------|");
            
            for (RentalRecord record : rentalHistory.getRentalHistory()) {                
                System.out.printf("| %-9s | %-12s | %-20s | %-12s | $%-11.2f |%n", 
                    record.getRecordType(), 
                    record.getVehicle().getLicensePlate(),
                    record.getCustomer().getCustomerName(),
                    record.getRecordDate().toString(),
                    record.getTotalAmount()
                );
            }
            System.out.println();
        }
    }
    
    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(plate)) {
                return v;
            }
        }
        return null;
    }
    
    public Customer findCustomerById(int id) {
        for (Customer c : customers)
            if (c.getCustomerId() == id)
                return c;
        return null;
    }
}
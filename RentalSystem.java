import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class RentalSystem {
	private static RentalSystem instance; // variable containing itself 
	
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();
    
    private RentalSystem(){} // private constructor 
    
    // getter for instance of itself 
    public static RentalSystem getInstance() {
    	if (instance == null) {
    		instance = new RentalSystem();
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
        if (vehicle.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
            vehicle.setStatus(Vehicle.VehicleStatus.RENTED);
            rentalHistory.addRecord(new RentalRecord(vehicle, customer, date, amount, "RENT"));
            System.out.println("Vehicle rented to " + customer.getCustomerName());
            saveRecord(rentalHistory.getRentalHistory().getLast());
        }
        else {
            System.out.println("Vehicle is not available for renting.");
        }
    }

    public void returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.RENTED) {
            vehicle.setStatus(Vehicle.VehicleStatus.AVAILABLE);
            rentalHistory.addRecord(new RentalRecord(vehicle, customer, date, extraFees, "RETURN"));
            System.out.println("Vehicle returned by " + customer.getCustomerName());
            saveRecord(rentalHistory.getRentalHistory().getLast());
        }
        else {
            System.out.println("Vehicle is not rented.");
        }
    }    

    public void displayAvailableVehicles() {
    	System.out.println("|     Type         |\tPlate\t|\tMake\t|\tModel\t|\tYear\t|");
    	System.out.println("---------------------------------------------------------------------------------");
    	 
        for (Vehicle v : vehicles) {
            if (v.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
                System.out.println("|     " + (v instanceof Car ? "Car          " : "Motorcycle   ") + "|\t" + v.getLicensePlate() + "\t|\t" + v.getMake() + "\t|\t" + v.getModel() + "\t|\t" + v.getYear() + "\t|\t");
            }
        }
        System.out.println();
    }
    
    public void displayAllVehicles() {
        for (Vehicle v : vehicles) {
            System.out.println("  " + v.getInfo());
        }
    }

    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println("  " + c.toString());
        }
    }
    
    public void displayRentalHistory() {
        for (RentalRecord record : rentalHistory.getRentalHistory()) {
            System.out.println(record.toString());
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

    public Customer findCustomerByName(String name) {
        for (Customer c : customers)
            if (c.getCustomerName().equalsIgnoreCase(name))
                return c;
        return null;
    }
    
    // [9 Points] Currently, all vehicle, customer, and rental record details are lost upon exiting the program.
    // To address this, implement the following methods in the RentalSystem class. Each method should write to
    // the file in append mode to preserve existing entries.
    // a.	saveVehicle(Vehicle vehicle): adds vehicle details to vehicles.txt. Called inside addVehicle(...).
    // b.	saveCustomer(Customer customer): adds customer details to customers.txt. Called inside addCustomer(...).
    // c.	saveRecord(RentalRecord record): adds rental record details to rental_records.txt. Called at the end of
    //			rentVehicle(...) and returnVehicle(...) after a record is added to the rental history.
    
    // adds vehicle details to vehicles.txt
    public void saveVehicle (Vehicle vehicle) {
    	File file = new File ("./Save Data/vehicles.txt");
    	try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(vehicle.getInfo());
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    // adds customer details to customer.txt
    public void saveCustomer (Customer customer) {
    	File file = new File ("./Save Data/customer.txt");
    	try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(customer.toString());
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    // adds rental record details to rental_records.txt
    public void saveRecord (RentalRecord record) {
    	File file = new File ("./Save Data/rental_records.txt");
    	try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(record.toString());
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
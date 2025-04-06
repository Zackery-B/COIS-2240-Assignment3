import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class RentalSystem {
	private static RentalSystem instance; // variable containing itself 
	
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();
    
    private RentalSystem(){loadData();} // private constructor 
    
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
    
    // adds vehicle details to vehicles.txt
    private void saveVehicle (Vehicle vehicle) {
    	File file = new File ("./Save Data/vehicles.txt");
    	try {
    		boolean isNewFile = !file.exists();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
    		if (isNewFile) {
    			bw.write("License Plate | Make | Model | Year | Status | Other\n--------------------------------------------------------");
    			bw.newLine();
    		}
			bw.write(vehicle.getInfo());
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    // adds customer details to customer.txt
    private void saveCustomer (Customer customer) {
    	File file = new File ("./Save Data/customer.txt");
    	try {
    		boolean isNewFile = !file.exists();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(customer.toString());
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    // adds rental record details to rental_records.txt
    private void saveRecord (RentalRecord record) {
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
    
    // load save data into respective lists
    private void loadData() {
    	File file; // file to retrieve data from
    	
    	// create directory to save data if it doesn't exist
    	File dir = new File ("./Save Data");
		if (!dir.exists()) {
			dir.mkdir();
		}
    	
    	// load vehicle data from vehicles.txt
    	file = new File ("./Save Data/vehicles.txt");
    	if (file.exists()) {
    		ArrayList<String[]> allVehicleData = new ArrayList<>();
    		allVehicleData = readFile("./Save Data/vehicles.txt"); // read data from file
    		
    		for (String[] vehicleData :  allVehicleData) {
    			
    			Vehicle vehicle = null; 
    			
	    		switch (vehicleData[vehicleData.length-1]) { // there is no type --------- problem 
	    			case "Car" :
	    				// remove the "seats :" and trim the integer
	    				int numSeats = Integer.parseInt(vehicleData[5].split(":")[1].trim());
	    				
	    				// create the car with all needed values 
	    				vehicle = new Car(vehicleData[1],vehicleData[2],Integer.parseInt(vehicleData[3]),numSeats);
	    				
	    				break;
	    			
	    			case "Motorcycle" :
	    				boolean hasSideCare;
	    				
	    				// clean hasSideCare
	    				if (vehicleData[5] == "Sidecar: Yes") {
	    					hasSideCare = true;
	    				}else {
	    					hasSideCare =false;
	    				}
	    				
	    				// create the motorcycle with all needed values 
	    				vehicle = new Motorcycle(vehicleData[1],vehicleData[2],Integer.parseInt(vehicleData[3]),hasSideCare);
	    					
	    				break;
	    				
	    			case "Truck" :
	    				float cargoCapacity = Float.parseFloat(vehicleData[5].split(":")[1].trim());
	    				// create the truck with all needed values
	    				vehicle = new Truck(vehicleData[1],vehicleData[2],Integer.parseInt(vehicleData[3]),cargoCapacity);
	    				
	    				break;
	    		}
	    		
	    		vehicle.setLicensePlate(vehicleData[0]);
	    		this.vehicles.add(vehicle);
    		}
    	}
    	
    	// load customer data from customer.txt
    	file = new File ("./Save Data/customer.txt");
    	if (file.exists()) {
    		ArrayList<String[]> allCustomerData = new ArrayList<>();
    		allCustomerData = readFile("./Save Data/customer.txt"); // read data from file
    		
    		for (String[] customerData : allCustomerData) {
    			// clean and save 
    		}
    	}
    	
    	// load records from rental_records.txt
    	file = new File ("./Save Data/rental_records.txt");
    	if (file.exists()) {
    		
    	}
    	
    	
    	
    	
    }
    
    // reads a file and returns all the data in a 2D list 
    private ArrayList<String[]> readFile(String path) {
    	ArrayList<String[]> lines = new ArrayList<>();
    	
    	try {
	    	BufferedReader reader = new BufferedReader(new FileReader(path));
	    	String line;
	    	String[] values;
	    	
	    	while((line = reader.readLine()) != null) {
	    		values = line.split("|"); // separate all values into a list 
	    		
	    		// remove all white space 
	    		for (String value : values)
	    				value.trim();	
	    		
	    		// add to lines list 
	    		lines.add(values);
	    	}
	    	
	    	reader.close();
	    	
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	
    	lines.remove(0); //remove the header line 
    	lines.remove(1); //remove the separator line 
    	return lines; 
    }

}
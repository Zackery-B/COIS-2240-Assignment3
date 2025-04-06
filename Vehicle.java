public abstract class Vehicle {
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private VehicleStatus status;

    public enum VehicleStatus { AVAILABLE, RESERVED, RENTED, MAINTENANCE, OUTOFSERVICE }

    public Vehicle(String make, String model, int year) {
    	this.make = capitalize(make);
    	this.model = capitalize(model);
        this.year = year;
        this.status = VehicleStatus.AVAILABLE;
        this.licensePlate = null;
    }
    
    // helper method to avoid repeat code 
    private String capitalize(String input) {
    	if (input == null || input.isEmpty())
    		return null;
    	else
    		return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
    

    public Vehicle() {
        this(null, null, 0);
    }

    public void setLicensePlate(String plate) {
    	plate = plate == null ? null : plate.toUpperCase();
    	boolean isValid = this.isValidPlate(plate); // use validPlate method 
    	
    	if (isValid)
    		this.licensePlate = plate;
    	else
    		throw new IllegalArgumentException("Invalid; plate is not correct format"); 
    }

    public void setStatus(VehicleStatus status) {
    	this.status = status;
    }

    public String getLicensePlate() { return licensePlate; }

    public String getMake() { return make; }

    public String getModel() { return model;}

    public int getYear() { return year; }

    public VehicleStatus getStatus() { return status; }

    public String getInfo() {
    	String vehicleType = this.getClass().getSimpleName();
        return licensePlate + " | " + vehicleType + " | " + make + " | " + model + " | " + year + " | " + status;
    }
    
    private boolean isValidPlate(String plate) {
    	boolean isValid = true;
    	
    	// test general errors 
    	if (plate == null || plate.length() != 6)
    		isValid = false;
    	
    	// Confirm the first 3 letters 
    	for (int i = 0; i < 3 && isValid; i++ ){
    		if (!Character.isLetter(plate.charAt(i))) // check if not letter 
    			isValid = false;
    	}
    	
    	// Confirm the last 3 numbers 
    	for (int i = 3; i < 6 && isValid; i++ ){
    		if (!Character.isDigit(plate.charAt(i))) // check if not number 
    			isValid = false;
    	}
    	    	
    	return isValid;
    }

}

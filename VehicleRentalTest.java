

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class VehicleRentalTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}
	
	@Test
	void testLicensePlateValidation() {
		Vehicle v = new Car(null, null, 0, 0);
		
		// valid license plates
		v.setLicensePlate("AAA100");	
		assertTrue(v.getLicensePlate() == "AAA100");
		
		v.setLicensePlate("ABC567");
		assertTrue(v.getLicensePlate() == "ABC567");
		
		v.setLicensePlate("ZZZ999");
		assertTrue(v.getLicensePlate() == "ZZZ999");
		
		// invalid license plates
		assertThrows(Exception.class, () ->
			v.setLicensePlate("AAA1000"),
	        "Expected exception to be thrown, but it wasn't"
	    );

		assertThrows(Exception.class, () ->
			v.setLicensePlate("ZZZ99"),
	        "Expected exception to be thrown, but it wasn't"
	    );

		assertThrows(Exception.class, () ->
			v.setLicensePlate(""),
	        "Expected exception to be thrown, but it wasn't"
	    );

		assertThrows(Exception.class, () ->
			v.setLicensePlate(null),
	        "Expected exception to be thrown, but it wasn't"
	    );

		assertFalse(v.getLicensePlate() == "AAA1000" || v.getLicensePlate() == "ZZZ99" || v.getLicensePlate() == "" || v.getLicensePlate() == null);

	}
	
	@Test
	void testRentAndReturnVehicle() {
		
		// instantiate car and customer objects
		Vehicle v = new Car ("Toyota", "Corolla", 2024, 4);
		Customer c = new Customer (14, "John");
		
		// ensure vehicle is initially available
		assertTrue(v.getStatus() == Vehicle.VehicleStatus.AVAILABLE);
		
		// retrieve RentalSystem instance
		RentalSystem rs = RentalSystem.getInstance();
		
		// call rentVehicle() and assert rental returns true (is rented)
		assertTrue(rs.rentVehicle(v, c, LocalDate.now(), 25));
		
		// assert v is marked as rented
		assertTrue(v.getStatus() == Vehicle.VehicleStatus.RENTED);
		
		// assert renting same vehicle fails
		assertFalse(rs.rentVehicle(v, c, LocalDate.now(), 25));
		
		// assert returning is successful
		assertTrue(rs.returnVehicle(v, c, LocalDate.now(), 25));
		
		// ensure vehicle is available again
		assertTrue(v.getStatus() == Vehicle.VehicleStatus.AVAILABLE);
		
		// assert returning again is unsuccessful
		assertFalse(rs.returnVehicle(v, c, LocalDate.now(), 25));
		
	}

}

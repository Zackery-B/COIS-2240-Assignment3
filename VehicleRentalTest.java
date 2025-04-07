

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class VehicleRentalTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		RentalSystem rentalSystem = RentalSystem.getInstance();
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
		
		//assertFalse();
		//assertThrows();

	}

}

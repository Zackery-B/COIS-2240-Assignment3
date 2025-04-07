

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
		Vehicle v1 = new Car(null, null, 0, 0);
		Vehicle v2 = new Car(null, null, 0, 0);
		Vehicle v3 = new Car(null, null, 0, 0);
		Vehicle v4 = new Car(null, null, 0, 0);
		
		v1.setLicensePlate("AAA100");	
		assertTrue(v1.getLicensePlate() == "AAA100");
		
		v1.setLicensePlate("ABC567");
		assertTrue(v1.getLicensePlate() == "ABC567");
		
		v1.setLicensePlate("ZZZ999");
		assertTrue(v1.getLicensePlate() == "ZZZ999");
		
		assertFalse();
		assertThrows();

	}

}

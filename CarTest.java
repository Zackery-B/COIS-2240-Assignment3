import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarTest {

    Car car;


    @Test
    void testCarConstructor() {
        assertEquals("ABC123", car.getLicensePlate());
        assertEquals("Toyota", car.getMake());
        assertEquals("Corolla", car.getModel());
        assertEquals(2020, car.getYear());
        assertEquals(5, car.getNumSeats());
        assertEquals("Available", car.getStatus());
    }

    @Test
    void testGetNumSeats() {
        assertEquals(5, car.getNumSeats());
    }

    @Test
    void testRentVehicle() {
        car.rentVehicle();
        assertEquals("Rented", car.getStatus());
    }

    @Test
    void testReturnVehicle() {
        car.rentVehicle();
        car.returnVehicle();
        assertEquals("Available", car.getStatus());
    }

    @Test
    void testGetInfo() {
        String expected = "License Plate: ABC123\nMake: Toyota\nModel: Corolla\nYear: 2020\nStatus: Available\nNumber of Seats: 5";
        assertEquals(expected, car.getInfo());
    }

    @Test
    void testSetLicensePlate() {
        car.setLicensePlate("XYZ999");
        assertEquals("XYZ999", car.getLicensePlate());
    }

    @Test
    void testSetStatus() {
    	car.rentVehicle(); // sets to RENTED
    	assertEquals("Rented", car.getStatus());

    	car.returnVehicle(); // sets back to AVAILABLE
    	assertEquals("Available", car.getStatus());
    }

    @Test
    void testToString() {
        String expected = "Car - Toyota Corolla (2020) [ABC123] - Seats: 5 - Status: Available";
        assertEquals(expected, car.toString());
    }
}

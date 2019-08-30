package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Location;
import main.Utilities;

/**
 * A JUnit test suite to test the methods of the Utilities class.
 * @author Jay
 */
public class UtilitiesTest {
	/**
	 * Tests that the correct boolean values are returned when the 'locationStringValid()' method is called
	 * on various valid and invalid strings.
	 */
	@Test
	public void testLocationStringValid() {
		assertEquals("Incorrect boolean value returned", false, Utilities.locationStringValid(""));
		assertEquals("Incorrect boolean value returned", false, Utilities.locationStringValid("1"));
		assertEquals("Incorrect boolean value returned", false, Utilities.locationStringValid("2,"));
		assertEquals("Incorrect boolean value returned", false, Utilities.locationStringValid("3.4.5,2"));
		assertEquals("Incorrect boolean value returned", false, Utilities.locationStringValid("--4,2"));
		assertEquals("Incorrect boolean value returned", false, Utilities.locationStringValid("two"));
		assertEquals("Incorrect boolean value returned", true, Utilities.locationStringValid("23.321,26.203"));
		assertEquals("Incorrect boolean value returned", true, Utilities.locationStringValid("-13.490,12.360"));
		assertEquals("Incorrect boolean value returned", true, Utilities.locationStringValid("-13,12"));
	}
	
	/**
	 * Tests that the correct boolean values are returned when the 'numPassengersValid()' method is called
	 * on various valid and invalid strings.
	 */
	@Test
	public void testNumPassengersValid() {
		assertEquals("Incorrect boolean value returned", false, Utilities.numPassengersValid("one"));
		assertEquals("Incorrect boolean value returned", false, Utilities.numPassengersValid("-1"));
		assertEquals("Incorrect boolean value returned", false, Utilities.numPassengersValid("0"));
		assertEquals("Incorrect boolean value returned", false, Utilities.numPassengersValid("0.5"));
		assertEquals("Incorrect boolean value returned", true, Utilities.numPassengersValid("1"));
		assertEquals("Incorrect boolean value returned", true, Utilities.numPassengersValid("2"));
		assertEquals("Incorrect boolean value returned", true, Utilities.numPassengersValid("50"));
	}
	
	/**
	 * Tests that valid location strings are correctly parsed into Location objects using the 'stringToLocation()'
	 * method.
	 */
	@Test
	public void testStringToLocation() {
		Location location1 = Utilities.stringToLocation("-13.490,12.360");
		assertEquals("Latitude value incorrect", -13.490, location1.getLatitude(), 0);
		assertEquals("Longitude value incorrect", 12.360, location1.getLongitude(), 0);
		
		Location location2 = Utilities.stringToLocation("13,12");
		assertEquals("Latitude value incorrect", 13, location2.getLatitude(), 0);
		assertEquals("Longitude value incorrect",12, location2.getLongitude(), 0);
		
		Location location3 = Utilities.stringToLocation("-23.001,-9.693");
		assertEquals("Latitude value incorrect", -23.001, location3.getLatitude(), 0);
		assertEquals("Longitude value incorrect",-9.693, location3.getLongitude(), 0);
	}
}

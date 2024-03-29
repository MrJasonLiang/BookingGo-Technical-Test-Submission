package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;

import main.Ride;
import main.SearchResult;
import main.SupplierApiResponse;

/**
 * A JUnit test suite to test the methods of the SearchResult class.
 * @author Jay
 */
public class SearchResultTest {
	private String daveApiResponse = "{\"supplier_id\":\"DAVE\","
			+ "\"pickup\":\"3.412,-2.512\","
			+ "\"dropoff\":\"3.414,-2.341\","
			+ "\"options\":["
			+ "{\"car_type\":\"STANDARD\",\"price\":746638},"
			+ "{\"car_type\":\"LUXURY\",\"price\":960406},"
			+ "{\"car_type\":\"PEOPLE_CARRIER\",\"price\":502419},"
			+ "{\"car_type\":\"LUXURY_PEOPLE_CARRIER\",\"price\":27134},"
			+ "{\"car_type\":\"MINIBUS\",\"price\":429252}]}";
	
	private String ericApiResponse = "{\"supplier_id\":\"ERIC\","
			+ "\"pickup\":\"3.412,-2.512\","
			+ "\"dropoff\":\"3.414,-2.341\","
			+ "\"options\":["
			+ "{\"car_type\":\"STANDARD\",\"price\":404284},"
			+ "{\"car_type\":\"EXECUTIVE\",\"price\":304345},"
			+ "{\"car_type\":\"LUXURY\",\"price\":795243},"
			+ "{\"car_type\":\"LUXURY_PEOPLE_CARRIER\",\"price\":740120}]}";
	
	/**
	 * Tests that the options from a SupplierApiResponse object are correctly added to the SearchResult object.
	 */
	@Test
	public void testAddSupplierApiResponse() {
		SearchResult searchResult = new SearchResult();
		SupplierApiResponse response = new Gson().fromJson(daveApiResponse, SupplierApiResponse.class);
		
		searchResult.addSupplierApiResponse(response);
		
		List<Ride> rides = searchResult.getAllRides();
		Ride firstRide = rides.get(0);
		
		assertEquals("Number of rides incorrect", 5, rides.size());
		assertEquals("Supplier ID incorrect", "DAVE", firstRide.getSupplierID());
		assertEquals("Car type incorrect", "STANDARD", firstRide.getCarType());
		assertEquals("Price incorrect", 746638, firstRide.getPrice());
		assertEquals("Car type incorrect", "MINIBUS", rides.get(4).getCarType());
	}
	
	/**
	 * Tests that only options capable of holding the specified number of passengers are kept when the
	 * 'removeInvalidRides()' method is called on a SearchResult object.
	 */
	@Test
	public void testRemoveInvalidRides() {
		SearchResult searchResult = new SearchResult();
		SupplierApiResponse response = new Gson().fromJson(daveApiResponse, SupplierApiResponse.class);
		
		searchResult.addSupplierApiResponse(response);
		
		searchResult.removeInvalidRides(4);
		assertEquals("Number of rides incorrect", 5, searchResult.getAllRides().size());
		
		searchResult.removeInvalidRides(5);
		assertEquals("Number of rides incorrect", 3, searchResult.getAllRides().size());
		
		searchResult.removeInvalidRides(6);
		assertEquals("Number of rides incorrect", 3, searchResult.getAllRides().size());
		
		searchResult.removeInvalidRides(7);
		assertEquals("Number of rides incorrect", 1, searchResult.getAllRides().size());
		
		searchResult.removeInvalidRides(16);
		assertEquals("Number of rides incorrect", 1, searchResult.getAllRides().size());
		
		searchResult.removeInvalidRides(17);
		assertEquals("Number of rides incorrect", 0, searchResult.getAllRides().size());
	}
	
	/**
	 * Tests that the rides returned by the 'getCheapestRidesDescPrice()' method are in descending price order
	 * and each car type has only one option (the cheapest option between suppliers).
	 */
	@Test
	public void testGetCheapestRidesDescPrice() {
		SearchResult searchResult = new SearchResult();
		SupplierApiResponse response1 = new Gson().fromJson(daveApiResponse, SupplierApiResponse.class);
		SupplierApiResponse response2 = new Gson().fromJson(ericApiResponse, SupplierApiResponse.class);
		
		searchResult.addSupplierApiResponse(response1);
		searchResult.addSupplierApiResponse(response2);
		
		List<Ride> rides = searchResult.getCheapestRidesDescPrice();
		
		assertEquals("Number of rides incorrect", 6, rides.size());
		
		for (int i = 0; i < rides.size() - 1; i++) {
			assertEquals("Rides not in descending price order", true, rides.get(i).getPrice() >= rides.get(i + 1).getPrice());
		}
		
		Ride fourthRide = rides.get(3);
		
		assertEquals("Supplier ID incorrect", "ERIC", fourthRide.getSupplierID());
		assertEquals("Car type incorrect", "STANDARD", fourthRide.getCarType());
	}
}

package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;

import main.Ride;
import main.SearchResult;
import main.SupplierApiResponse;

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
	
	@Test
	public void testAddSupplierApiResponse() {
		SearchResult searchResult = new SearchResult();
		SupplierApiResponse response = new Gson().fromJson(daveApiResponse, SupplierApiResponse.class);
		
		searchResult.addSupplierApiResponse(response);
		
		List<Ride> rides = searchResult.getAllRides();
		Ride firstRide = rides.get(0);
		
		assertEquals(5, rides.size());
		assertEquals("DAVE", firstRide.getSupplierID());
		assertEquals("STANDARD", firstRide.getCarType());
		assertEquals(746638, firstRide.getPrice());
		assertEquals("MINIBUS", rides.get(4).getCarType());
	}
	
	@Test
	public void testRemoveInvalidRides() {
		SearchResult searchResult = new SearchResult();
		SupplierApiResponse response = new Gson().fromJson(daveApiResponse, SupplierApiResponse.class);
		
		searchResult.addSupplierApiResponse(response);
		
		searchResult.removeInvalidRides(4);
		assertEquals(5, searchResult.getAllRides().size());
		
		searchResult.removeInvalidRides(5);
		assertEquals(3, searchResult.getAllRides().size());
		
		searchResult.removeInvalidRides(6);
		assertEquals(3, searchResult.getAllRides().size());
		
		searchResult.removeInvalidRides(7);
		assertEquals(1, searchResult.getAllRides().size());
		
		searchResult.removeInvalidRides(16);
		assertEquals(1, searchResult.getAllRides().size());
		
		searchResult.removeInvalidRides(17);
		assertEquals(0, searchResult.getAllRides().size());
	}
	
	@Test
	public void testGetCheapestRidesDescPrice() {
		SearchResult searchResult = new SearchResult();
		SupplierApiResponse response1 = new Gson().fromJson(daveApiResponse, SupplierApiResponse.class);
		SupplierApiResponse response2 = new Gson().fromJson(ericApiResponse, SupplierApiResponse.class);
		
		searchResult.addSupplierApiResponse(response1);
		searchResult.addSupplierApiResponse(response2);
		
		List<Ride> rides = searchResult.getCheapestRidesDescPrice();
		
		assertEquals(6, rides.size());
		
		for (int i = 0; i < rides.size() - 1; i++) {
			assertEquals(true, rides.get(i).getPrice() >= rides.get(i + 1).getPrice());
		}
		
		Ride fourthRide = rides.get(3);
		
		assertEquals("ERIC", fourthRide.getSupplierID()); //check eric replae dave
		assertEquals("STANDARD", fourthRide.getCarType());
	}
}

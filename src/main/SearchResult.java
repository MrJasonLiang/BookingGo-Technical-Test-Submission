package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class to represent a manipulatable search result from querying supplier APIs, i.e. collection of ride options.
 * You can e.g. view rides, filter rides based on passengers, and view rides in descending order.
 * @author Jay
 */
public class SearchResult {
	private List<Ride> rides = new ArrayList<Ride>();
	
	/**
	 * Adds the options from the given supplier API response to the list of rides in the search result.
	 * @param response the supplier API response to add options from
	 */
	public void addSupplierApiResponse(SupplierApiResponse response) {
		for (Option option : response.getOptions()) {
				rides.add(new Ride(response.getSupplierID(), option.getCarType(), option.getPrice()));
		}
	}
	
	/**
	 * Filters the list of rides in the search result so only those with enough room for the specified
	 * number of passengers remain.
	 * @param numPassengers the number of passengers to filter the rides on
	 */
	public void removeInvalidRides(int numPassengers) {
		List<Ride> filteredRides = new ArrayList<Ride>();
		
		for (Ride ride : rides) {
			if (SearchEngine.carCapacities.get(ride.getCarType()) >= numPassengers) {
				filteredRides.add(ride);
			}
		}
		
		rides = filteredRides;
	}
	
	/**
	 * Returns a read-only list of all the rides in the search result.
	 * @return a read-only list of all the rides in the search result
	 */
	public List<Ride> getAllRides() {
		return Collections.unmodifiableList(rides);
	}
	
	/**
	 * Returns a list of the cheapest rides for each car type (if there are multiple rides for one car type,
	 * the cheapest option is chosen) and sorted in descending price order (the first item being the most expensive).
	 * @return a list of the cheapest rides for each car type, sorted in descending price order
	 */
	public List<Ride> getCheapestRidesDescPrice() {
		List<Ride> ridesList = new ArrayList<Ride>(this.getCheapestRidesAsMap().values());
		
		Collections.sort(ridesList, Collections.reverseOrder());
		
		return ridesList;
	}
	
	/**
	 * Returns a map of the cheapest rides for each car type, with the key being the car type and the cheapest ride
	 * option being the value for that key.
	 * @return a map of the cheapest rides for each car type
	 */
	private Map<String, Ride> getCheapestRidesAsMap() {
		Map<String, Ride> ridesMap = new HashMap<String, Ride>(); 
		
		for (Ride ride : rides) {
			String carType = ride.getCarType();
			
			if (!ridesMap.containsKey(carType) || ridesMap.get(carType).getPrice() > ride.getPrice()) {
				ridesMap.put(carType, ride);
			}
		}
		
		return ridesMap;
	}
	
	/**
	 * Prints the list of cheapest rides for each car type in descending price order. The format of the output
	 * is {car type} - {supplier} - {price}.
	 */
	public void printCheapestRidesDescPrice() {
		System.out.println("========== Search Results ==========");
		System.out.println();
		
		for (Ride ride : this.getCheapestRidesDescPrice()) {
			System.out.println(ride.getCarType() + " - " + ride.getSupplierID() + " - " + ride.getPrice());
		}
	}
}

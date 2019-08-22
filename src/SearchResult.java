import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResult {
	private List<Ride> rides = new ArrayList<Ride>();
	
	public void addSupplierApiResponse(SupplierApiResponse response) {
		for (Option option : response.getOptions()) {
				rides.add(new Ride(response.getSupplierID(), option.getCarType(), option.getPrice()));
		}
	}
	
	public void removeInvalidRides(int numPassengers) {
		List<Ride> filteredRides = new ArrayList<Ride>();
		
		for (Ride ride : rides) {
			if (SearchEngine.carCapacities.get(ride.getCarType()) >= numPassengers) {
				filteredRides.add(ride);
			}
		}
		
		rides = filteredRides;
	}
	
	public List<Ride> getRidesDescPrice() {
		List<Ride> ridesList = new ArrayList<Ride>(this.getRidesAsMap().values());
		
		Collections.sort(ridesList, Collections.reverseOrder());
		
		return ridesList;
	}
	
	private Map<String, Ride> getRidesAsMap() {
		Map<String, Ride> ridesMap = new HashMap<String, Ride>(); 
		
		for (Ride ride : rides) {
			String carType = ride.getCarType();
			
			if (!ridesMap.containsKey(carType) || ridesMap.get(carType).getPrice() > ride.getPrice()) {
				ridesMap.put(carType, ride);
			}
		}
		
		return ridesMap;
	}
}

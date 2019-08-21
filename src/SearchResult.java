import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResult {
	private Map<String, Ride> rides = new HashMap<String, Ride>();
	
	public void addSupplierApiResponse(SupplierApiResponse response) {
		for (Option option : response.getOptions()) {
			String carType = option.getCarType();
			int price = option.getPrice();
			
			if (!rides.containsKey(carType) || rides.get(carType).getPrice() > price) {
				rides.put(carType, new Ride(response.getSupplierID(), carType, price));
			}
		}
	}
	
	public void removeInvalidRides(int numPassengers) {
		Map<String, Ride> filteredRides = new HashMap<String, Ride>(); 
		
		for (Ride ride : rides.values()) {
			if (SearchEngine.carCapacities.get(ride.getCarType()) >= numPassengers) {
				filteredRides.put(ride.getCarType(), ride);
			}
		}
		
		rides = filteredRides;
	}
	
	public List<Ride> getRidesDescPrice() {
		List<Ride> ridesList = new ArrayList<Ride>(rides.values());
		
		Collections.sort(ridesList, Collections.reverseOrder());
		
		return ridesList;
	}
}

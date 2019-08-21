
public class Part1 {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Insufficient number of command line arguments.");
			System.out.println("Pickup and dropoff locations should be provided as 2 arguments each in the format 'latitude,longitude'.");
			System.out.println("E.g. '51.470020,-0.454295'.");
			return;
		}
		
		String pickup = args[0];
		String dropoff = args[1];
		
		if (!locationStringValid(pickup) || !locationStringValid(dropoff)) {
			System.out.println("Pickup or dropoff location invalid. Must be in the format 'latitude,longitude'.");
			System.out.println("E.g. '51.470020,-0.454295'.");
			return;
		}
		
		SearchEngine searchEngine = new SearchEngine();
		SearchResult res = searchEngine.searchRides(stringToLocation(pickup), stringToLocation(dropoff), 6);
		System.out.println("======== RESULTS =======");
		for (Ride ride : res.getRidesDescPrice()) {
			System.out.println(ride.getCarType() + " - " + ride.getSupplierID() + " - " + ride.getPrice());
		}
	}
	
	public static boolean locationStringValid(String location) {
		String[] coords = location.split(",");
		
		if (coords.length != 2) {
			return false;
		}
		
		try {
			Double.parseDouble(coords[0]);
			Double.parseDouble(coords[1]);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static Location stringToLocation(String location) {
		String[] coords = location.split(",");
		
		return new Location(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
	}
}

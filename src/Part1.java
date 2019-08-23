
public class Part1 {
	public static void main(String[] args) {
		if (!(args.length == 2 || args.length == 3)) {
			System.out.println("Invalid number of command line arguments.");
			System.out.println("Two arguments, the pickup and dropoff locations, should be provided in the format 'latitude,longitude'.");
			System.out.println("E.g. '51.470020,-0.454295'.");
			System.out.println("An optional third argument can be provided to specify the number of passenger.");
			return;
		}
		
		String pickup = args[0];
		String dropoff = args[1];
		
		if (!locationStringValid(pickup) || !locationStringValid(dropoff)) {
			System.out.println("Pickup or dropoff location invalid. Must be in the format 'latitude,longitude'.");
			System.out.println("E.g. '51.470020,-0.454295'.");
			return;
		}
		
		SearchResult searchResult;
		
		if (args.length == 2) {
			searchResult = new SearchEngine().searchRides(stringToLocation(pickup), stringToLocation(dropoff));
		} else {
			searchResult = new SearchEngine().searchRides(stringToLocation(pickup), stringToLocation(dropoff), Integer.parseInt(args[2]));
		}
		
		for (Ride ride : searchResult.getRidesDescPrice()) {
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

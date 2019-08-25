package main;

public class Part1 {
	public static void main(String[] args) {
		try {
			new Part1().run(args);
		} catch (InvalidArgumentException e) {
			System.out.println();
			System.out.println("Terminating due to invalid arguments.");
		}
	}
	
	public void run(String[] args) throws InvalidArgumentException {
		if (!this.argumentsValid(args)) {
			System.out.println("The command line arguments supplied were invalid.");
			System.out.println("The first two arguments should be the pickup and dropoff location.");
			System.out.println("These should be specified in the format 'latitude,longitude', e.g. '51.470020,-0.454295'.");
			System.out.println("An optional third argument can be provided to specify the number of passengers.");
			
			throw new InvalidArgumentException();
		}
		
		Location pickup = this.stringToLocation(args[0]);
		Location dropoff = this.stringToLocation(args[1]);
		
		SearchResult searchResult;
		
		if (args.length == 2) {
			searchResult = new SearchEngine().searchRides(pickup, dropoff);
		} else {
			searchResult = new SearchEngine().searchRides(pickup, dropoff, Integer.parseInt(args[2]));
		}
		
		System.out.println();
		searchResult.printCheapestRidesDescPrice();
	}
	
	private boolean argumentsValid(String[] args) {
		if (args.length == 2) {
			return this.locationStringValid(args[0]) && this.locationStringValid(args[1]);
		} else if (args.length == 3) {
			return this.locationStringValid(args[0]) && this.locationStringValid(args[1]) && this.numPassengersValid(args[2]);
		} else {
			return false;
		}
	}
	
	private boolean locationStringValid(String location) {
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
	
	private boolean numPassengersValid(String numPassengers) {
		try {
			Integer.parseInt(numPassengers);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	private Location stringToLocation(String location) {
		String[] coords = location.split(",");
		
		return new Location(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
	}
	
	public class InvalidArgumentException extends Exception {
		private static final long serialVersionUID = 1L;
	}
}

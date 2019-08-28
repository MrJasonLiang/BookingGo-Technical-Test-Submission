package main;

public class Part1 {
	public static void main(String[] args) {
		try {
			new Part1().run(args);
		} catch (InvalidArgumentException e) {
			System.out.println("The command line arguments supplied were invalid.");
			System.out.println("The first two arguments should be the pickup and dropoff location.");
			System.out.println("These should be specified in the format 'latitude,longitude', e.g. '51.470020,-0.454295'.");
			System.out.println("An optional third argument can be provided to specify the number of passengers.");
			System.out.println();
			System.out.println("Terminating due to invalid arguments.");
		}
	}
	
	public void run(String[] args) throws InvalidArgumentException {
		if (!this.argumentsValid(args)) {
			throw new InvalidArgumentException();
		}
		
		Location pickup = Utilities.stringToLocation(args[0]);
		Location dropoff = Utilities.stringToLocation(args[1]);
		
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
			return Utilities.locationStringValid(args[0]) && Utilities.locationStringValid(args[1]);
		} else if (args.length == 3) {
			return Utilities.locationStringValid(args[0]) && Utilities.locationStringValid(args[1]) && Utilities.numPassengersValid(args[2]);
		} else {
			return false;
		}
	}
	
	public class InvalidArgumentException extends Exception {
		private static final long serialVersionUID = 1L;
	}
}

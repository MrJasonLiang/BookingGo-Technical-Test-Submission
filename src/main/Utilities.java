package main;

public class Utilities {
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
	
	public static boolean numPassengersValid(String numPassengers) {
		try {
			return Integer.parseInt(numPassengers) > 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static Location stringToLocation(String location) {
		String[] coords = location.split(",");
		
		return new Location(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
	}
}

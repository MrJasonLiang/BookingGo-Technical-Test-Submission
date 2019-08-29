package main;

/**
 * A utility class with methods for validating input strings and parsing strings into Location objects.
 * @author Jay
 */
public class Utilities {
	/**
	 * Checks to see if the location string is of the format 'latitude,longitude', where latitude and longitude
	 * are given as decimal numbers.
	 * @param location the location string to validate
	 * @return true if the location string is valid, false otherwise
	 */
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
	
	/**
	 * Checks to see if the number of passengers is a positive whole number.
	 * @param numPassengers the number of passengers string to validate
	 * @return true if the string represents a positive whole number, false otherwise
	 */
	public static boolean numPassengersValid(String numPassengers) {
		try {
			return Integer.parseInt(numPassengers) > 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Returns a Location object representing the given location string, given in the format 'latitude,longitude'.
	 * @param location the location string in the format 'latitude,longitude'
	 * @return a Location object representing the given location string
	 */
	public static Location stringToLocation(String location) {
		String[] coords = location.split(",");
		
		return new Location(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
	}
}

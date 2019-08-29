package main;

/**
 * A class to represent a location, specified by it's latitude and longitude coordinates.
 * @author Jay
 */
public class Location {
	private double latitude;
	private double longitude;
	
	/**
	 * Constructs a new Location object with the given latitude and longitude coordinates.
	 * @param latitude the latitude coordinate
	 * @param longitude the longitude coordinate
	 */
	public Location(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	 * Returns the latitude.
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * Returns the longitude.
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	
	@Override
	public String toString() {
		return latitude + "," + longitude;
	}
}

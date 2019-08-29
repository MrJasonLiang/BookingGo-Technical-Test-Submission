package main;

/**
 * A class to represent a ride option from querying a supplier's API, which also stores the supplier's ID.
 * @author Jay
 */
public class Ride implements Comparable<Ride> {
	private String supplierID;
	private String carType;
	private int price;
	
	/**
	 * Constructs a new Ride with the given information.
	 * @param supplierID the supplier ID
	 * @param carType the car type
	 * @param price the price
	 */
	public Ride(String supplierID, String carType, int price) {
		this.supplierID = supplierID;
		this.carType = carType;
		this.price = price;
	}
	
	/**
	 * Returns the supplier ID.
	 * @return the supplier ID
	 */
	public String getSupplierID() {
		return supplierID;
	}
	
	/**
	 * Returns the car type for this ride.
	 * @return the car type for this ride
	 */
	public String getCarType() {
		return carType;
	}
	
	/**
	 * Returns the price for this ride.
	 * @return the price for this ride
	 */
	public int getPrice() {
		return price;
	}
	
	@Override
	public int compareTo(Ride other) {
		return Integer.compare(price, other.price);
	}
}

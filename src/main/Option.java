package main;

/**
 * A class to represent an option from the list of options in the JSON response returned from querying a
 * supplier's API.
 * @author Jay
 */
public class Option {
	private String car_type;
	private int price;
	
	/**
	 * Returns the car type for this option.
	 * @return the car type for this option
	 */
	public String getCarType() {
		return car_type;
	}
	
	/**
	 * Returns the price for this option.
	 * @return the price for this option
	 */
	public int getPrice() {
		return price;
	}
}

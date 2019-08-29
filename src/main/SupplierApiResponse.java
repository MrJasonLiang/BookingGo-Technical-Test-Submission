package main;

import java.util.Collections;
import java.util.List;

/**
 * A class to represent the JSON response returned from querying a supplier's API.
 * @author Jay
 */
public class SupplierApiResponse {
	private String supplier_id;
	private String pickup;
	private String dropoff;
	private List<Option> options;
	
	/**
	 * Returns the supplier ID.
	 * @return the supplier ID
	 */
	public String getSupplierID() {
		return supplier_id;
	}
	
	/**
	 * Returns the pickup location for the journey.
	 * @return the pickup location for the journey
	 */
	public String getPickup() {
		return pickup;
	}
	
	/**
	 * Returns the drop off location for the journey.
	 * @return the drop off location for the journey
	 */
	public String getDropoff() {
		return dropoff;
	}
	
	/**
	 * Returns a read-only list of the ride options available from the supplier's response.
	 * @return a read-only list of the ride options available from the supplier's response
	 */
	public List<Option> getOptions() {
		return Collections.unmodifiableList(options);
	}
}

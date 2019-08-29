package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

// TODO Comment all classes
// TODO Maybe move into packages?
// TODO Update README

/**
 * A class representing a search engine that queries supplier APIs and returns a search result of ride options.
 * @author Jay
 */
public class SearchEngine {	
	public static Map<String, Integer> carCapacities = initCarCapacities();
	private String[] supplierAPIs = {
			"https://techtest.rideways.com/dave",
			"https://techtest.rideways.com/eric",
			"https://techtest.rideways.com/jeff"};
	
	/**
	 * Initialises the map of car types and their capacities.
	 * @return a map of car types and their capacities
	 */
	private static Map<String, Integer> initCarCapacities() {
		Map<String, Integer> carCapacities = new HashMap<String, Integer>();
		
		carCapacities.put("STANDARD", 4);
		carCapacities.put("EXECUTIVE", 4);
		carCapacities.put("LUXURY", 4);
		carCapacities.put("PEOPLE_CARRIER", 6);
		carCapacities.put("LUXURY_PEOPLE_CARRIER", 6);
		carCapacities.put("MINIBUS", 16);
		
		return carCapacities;
	}
	
	/**
	 * Returns the SearchResult object returned from querying supplier APIs using the given pickup and
	 * drop off locations.
	 * @param pickup the pickup location for the journey
	 * @param dropoff the drop off location for the journey
	 * @return the SearchResult object returned from querying supplier APIs
	 */
	public SearchResult searchRides(Location pickup, Location dropoff) {
		System.out.println("Searching rides...");
		System.out.println();
		
		SearchResult searchResult = new SearchResult();
		
		// Contacts each supplier's API and adds the options from their response to the search result.
		// If an error occurs during this process, the supplier is skipped.
		for (String supplierAPI : supplierAPIs) {
			try {
				System.out.println("Contacting supplier API: " + supplierAPI);
				searchResult.addSupplierApiResponse(this.queryAPI(supplierAPI, pickup, dropoff));
			} catch (SocketTimeoutException e) {
				System.out.println("Supplier API took too long to respond. Skipping supplier.");
			} catch (IOException e) {
				System.out.println(e.getMessage() + " Skipping supplier.");
			}
		}
		
		return searchResult;
	}
	
	/**
	 * Returns the SearchResult object returned from querying supplier APIs using the given pickup and
	 * drop off locations, taking into account the number of passengers specified.
	 * @param pickup the pickup location for the journey
	 * @param dropoff the drop off location for the journey
	 * @param numPassengers the number of passengers
	 * @return the SearchResult object returned from querying supplier APIs
	 */
	public SearchResult searchRides(Location pickup, Location dropoff, int numPassengers) {
		SearchResult searchResult = this.searchRides(pickup, dropoff);
		searchResult.removeInvalidRides(numPassengers);
		return searchResult;
	}
	
	/**
	 * Returns a SupplierApiResponse object representing the JSON response from querying the given supplier API
	 * using the given pickup and drop off locations.
	 * @param supplierAPI the supplier API to query
	 * @param pickup the pickup location for the journey
	 * @param dropoff the drop off location for the journey
	 * @return a SupplierApiResponse object representing the JSON response from querying the given supplier API
	 * @throws IOException if an IO error occurs
	 */
	private SupplierApiResponse queryAPI(String supplierAPI, Location pickup, Location dropoff) throws IOException {
		String url = supplierAPI + "?pickup=" + pickup + "&dropoff=" + dropoff;
		
		// Creates a GET request to the API with a timeout of 2 seconds.
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setConnectTimeout(2000);
		
		int responseCode = conn.getResponseCode();
		
		if (responseCode == HttpURLConnection.HTTP_OK) {
			// If the HTTP status code returned was 200 (request succeeded), reads the JSON response into a string
			// and returns it as a SupplierApiResponse object.
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder httpResponse = new StringBuilder();
			String line;
			
			while ((line = in.readLine()) != null) {
				httpResponse.append(line);
			}
			
			in.close();
			
			return new Gson().fromJson(httpResponse.toString(), SupplierApiResponse.class);
		} else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
			throw new ApiException("An error occurred whilst contacting the supplier API (bad request error).");
		} else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
			throw new ApiException("An error occurred whilst contacting the supplier API (internal server error).");
		} else {
			throw new ApiException("An error occurred whilst contacting the supplier API.");
		}
	}
	
	/**
	 * A custom exception class representing when an error occurs whilst trying to contact a supplier's API.
	 * @author Jay
	 */
	public class ApiException extends IOException {
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructs a new ApiException with the given message.
		 * @param message the error message
		 */
		public ApiException(String message) {
			super(message);
		}
	}
}

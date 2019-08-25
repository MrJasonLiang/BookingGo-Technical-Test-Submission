import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class SearchEngine {	
	public static Map<String, Integer> carCapacities = initCarCapacities();
	private String[] supplierAPIs = {
			"https://techtest.rideways.com/dave",
			"https://techtest.rideways.com/eric",
			"https://techtest.rideways.com/jeff"};
	
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
	
	public SearchResult searchRides(Location pickup, Location dropoff) {
		System.out.println("Searching rides...");
		System.out.println();
		
		SearchResult searchResult = new SearchResult();
		
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
	
	public SearchResult searchRides(Location pickup, Location dropoff, int numPassengers) {
		SearchResult searchResult = this.searchRides(pickup, dropoff);
		searchResult.removeInvalidRides(numPassengers);
		return searchResult;
	}
	
	private SupplierApiResponse queryAPI(String supplierAPI, Location pickup, Location dropoff) throws IOException {
		String url = supplierAPI + "?pickup=" + pickup + "&dropoff=" + dropoff;
		
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setConnectTimeout(2000);
		
		int responseCode = conn.getResponseCode();
		
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder httpResponse = new StringBuilder();
			String line;
			
			while ((line = in.readLine()) != null) {
				httpResponse.append(line);
			}
			
			in.close();
			
			return new Gson().fromJson(httpResponse.toString(), SupplierApiResponse.class);
		} else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
			throw new ApiErrorException("An error occurred whilst contacting the supplier API (bad request error).");
		} else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
			throw new ApiErrorException("An error occurred whilst contacting the supplier API (internal server error).");
		} else {
			throw new ApiErrorException("An error occurred whilst contacting the supplier API.");
		}
	}
	
	public class ApiErrorException extends IOException {
		private static final long serialVersionUID = 1L;
		
		public ApiErrorException(String message) {
			super(message);
		}
	}
}

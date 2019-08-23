import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class SearchEngine {	
	public static Map<String, Integer> carCapacities = new HashMap<String, Integer>();
	private String[] supplierAPIs = {
			"https://techtest.rideways.com/dave",
			"https://techtest.rideways.com/eric",
			"https://techtest.rideways.com/jeff"};
	
	public SearchEngine() {
		carCapacities.put("STANDARD", 4);
		carCapacities.put("EXECUTIVE", 4);
		carCapacities.put("LUXURY", 4);
		carCapacities.put("PEOPLE_CARRIER", 6);
		carCapacities.put("LUXURY_PEOPLE_CARRIER", 6);
		carCapacities.put("MINIBUS", 16);
	}
	
	public SearchResult searchRides(Location pickup, Location dropoff) {
		SearchResult searchResult = new SearchResult();
		
		for (String supplierAPI : supplierAPIs) {
			try {
				searchResult.addSupplierApiResponse(this.queryAPI(supplierAPI, pickup, dropoff));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.out.println("Skipping supplier: " + supplierAPI);
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
			throw new ApiErrorException("Bad request error. Please check the URL follows the correct format.");
		} else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
			throw new ApiErrorException(supplierAPI + " | Internal server error. Something has gone wrong.");
		} else {
			throw new ApiErrorException("An error occurred while contacting this supplier's API.");
		}
	}
	
	public class ApiErrorException extends IOException {
		private static final long serialVersionUID = 1L;
		
		public ApiErrorException(String message) {
			super(message);
		}
	}
}

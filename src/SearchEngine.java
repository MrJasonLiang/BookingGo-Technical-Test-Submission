import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class SearchEngine {	
	public static Map<String, Integer> carCapacities = new HashMap<String, Integer>();
	private String[] suppliers = {"dave", "eric", "jeff"};
	
	public SearchEngine() {
		carCapacities.put("STANDARD", 4);
		carCapacities.put("EXECUTIVE", 4);
		carCapacities.put("LUXURY", 4);
		carCapacities.put("PEOPLE_CARRIER", 6);
		carCapacities.put("LUXURY_PEOPLE_CARRIER", 6);
		carCapacities.put("MINIBUS", 16);
	}
	
	public SearchResult searchRides(Location pickup, Location dropoff) {
		SearchResult result = new SearchResult();
		
		for (String supplier : suppliers) {
			SupplierApiResponse resp = this.queryAPI(supplier, pickup, dropoff);
			if (resp == null) {
				System.out.println("skip");
				continue;
			}
			result.addSupplierApiResponse(resp);
		}
		
		return result;
	}
	
	public SearchResult searchRides(Location pickup, Location dropoff, int numPassengers) {
		SearchResult result = this.searchRides(pickup, dropoff);
		result.removeInvalidRides(numPassengers);
		return result;
	}
	
	public SupplierApiResponse queryAPI(String urla, Location pickup, Location dropoff) {
		// TODO tidy up use of exception
		
		String url = "https://techtest.rideways.com/" + urla + "?pickup=" + pickup + "&dropoff=" + dropoff;
		
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			
			int responseCode = conn.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println(conn.getResponseCode());
				System.out.println(conn.getResponseMessage());
				System.out.println("gucci");
			} else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
				System.out.println(responseCode);
				throw new ApiErrorException("dsa");
			} else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
				System.out.println(responseCode);
				return null;
			} else {
				System.out.println("error");
				return null;
			}
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			StringBuilder response = new StringBuilder();
			String line;
			
			while ((line = in.readLine()) != null) {
				response.append(line);
				System.out.println("L: " + line);
			}
			
			in.close();
			
			System.out.println("R: " + response);
			
			SupplierApiResponse apiResponse = new Gson().fromJson(response.toString(), SupplierApiResponse.class);
//			apiResponse.printOptionsDesc();
			
			return apiResponse;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public class ApiErrorException extends IOException {
		private static final long serialVersionUID = 1L;
		
		public ApiErrorException(String message) {
			super(message);
		}
	}
}

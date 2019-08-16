import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

public class SearchEngine {	
	public void searchRides(Location pickup, Location dropoff) {
		String url = "https://techtest.rideways.com/dave?pickup=" + pickup + "&dropoff=" + dropoff;
		
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			
			int responseCode = conn.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println(conn.getResponseCode());
				System.out.println(conn.getResponseMessage());
				System.out.println("gucci");
			} else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
				System.out.println(responseCode);
				return;
			} else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
				System.out.println(responseCode);
				return;
			} else {
				System.out.println("error");
				return;
			}
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			StringBuilder response = new StringBuilder();
			String line;
			
			while ((line = in.readLine()) != null) {
				response.append(line);
				System.out.println("L: " + line);
			}
			
			System.out.println("R: " + response);
			
			Gson gson = new Gson();
			SupplierApiResponse apiResponse = gson.fromJson(response.toString(), SupplierApiResponse.class);
			apiResponse.printOptionsDesc();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

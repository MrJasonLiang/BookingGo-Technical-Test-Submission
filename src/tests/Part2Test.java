package tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import main.RestApiController;

public class Part2Test {
	@Before
	public void setUp() {
		new RestApiController();
	}
	
	@Test
	public void testGetRequest() throws IOException {
		this.testGetRequestHelper("http://localhost:4567/rides/api?pickup=3.412,2.431&dropoff=3.431,2.421");
		this.testGetRequestHelper("http://localhost:4567/rides/api?pickup=3.412,2.431&dropoff=3.431,2.421&passengers=4");
	}
	
	private void testGetRequestHelper(String url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		
		int responseCode = conn.getResponseCode();
		
		assertEquals("Response code incorrect", HttpURLConnection.HTTP_OK, responseCode);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder httpResponse = new StringBuilder();
		String line;
		
		while ((line = in.readLine()) != null) {
			httpResponse.append(line);
		}
		
		in.close();
		
		new Gson().fromJson(httpResponse.toString(), List.class);
	}
	
	@Test
	public void testGetRequestNoPickupParam() throws IOException {
		String url = "http://localhost:4567/rides/api?dropoff=3.431,2.421";
		assertEquals("Response code incorrect", HttpURLConnection.HTTP_BAD_REQUEST, this.getResponseCodeFromApiQuery(url));
	}
	
	@Test
	public void testGetRequestBadDropoffParam() throws IOException {
		String url = "http://localhost:4567/rides/api?pickup=3.412,2.431&dropoff=3.4312.421";
		assertEquals("Response code incorrect", HttpURLConnection.HTTP_BAD_REQUEST, this.getResponseCodeFromApiQuery(url));
	}
	
	@Test
	public void testPostRequest() throws IOException {
		String url = "http://localhost:4567/rides/api?pickup=3.412,2.431&dropoff=3.431,2.421";
		assertEquals("Response code incorrect", HttpURLConnection.HTTP_NOT_IMPLEMENTED, this.getResponseCodeFromApiQuery(url, "POST"));
	}
	
	@Test
	public void testPutRequest() throws IOException {
		String url = "http://localhost:4567/rides/api?pickup=3.412,2.431&dropoff=3.431,2.421";
		assertEquals("Response code incorrect", HttpURLConnection.HTTP_NOT_IMPLEMENTED, this.getResponseCodeFromApiQuery(url, "PUT"));
	}
	
	@Test
	public void testDeleteRequest() throws IOException {
		String url = "http://localhost:4567/rides/api?pickup=3.412,2.431&dropoff=3.431,2.421";
		assertEquals("Response code incorrect", HttpURLConnection.HTTP_NOT_IMPLEMENTED, this.getResponseCodeFromApiQuery(url, "DELETE"));
	}
	
	private int getResponseCodeFromApiQuery(String url) throws IOException {
		return ((HttpURLConnection) new URL(url).openConnection()).getResponseCode();
	}
	
	private int getResponseCodeFromApiQuery(String url, String requestMethod) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod(requestMethod);
		return conn.getResponseCode();
	}
}

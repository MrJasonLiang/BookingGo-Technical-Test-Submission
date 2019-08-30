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

/**
 * A JUnit test suite to test the functionality of the REST API provided by the RestApiController class.
 * @author Jay
 */
public class RestApiControllerTest {
	/**
	 * Initialises the REST API.
	 */
	@Before
	public void setUp() {
		new RestApiController();
	}
	
	/**
	 * Tests that GET requests to the REST API work and return a JSON payload containing a list (of rides).
	 * @throws IOException if an IO error occurs
	 */
	@Test
	public void testGetRequest() throws IOException {
		this.testGetRequestHelper("http://localhost:4567/rides/api?pickup=3.412,2.431&dropoff=3.431,2.421");
		this.testGetRequestHelper("http://localhost:4567/rides/api?pickup=3.412,2.431&dropoff=3.431,2.421&passengers=4");
	}
	
	/**
	 * Sends a GET request to the given API url and checks that the response is as expected.
	 * @param url the API endpoint to send the GET request to
	 * @throws IOException if an IO error occurs
	 */
	private void testGetRequestHelper(String url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		
		int responseCode = conn.getResponseCode();
		
		assertEquals("Status code incorrect", HttpURLConnection.HTTP_OK, responseCode);
		
		// Reads the response and checks it is a JSON payload containing a list.
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder httpResponse = new StringBuilder();
		String line;
		
		while ((line = in.readLine()) != null) {
			httpResponse.append(line);
		}
		
		in.close();
		
		new Gson().fromJson(httpResponse.toString(), List.class);
	}
	
	/**
	 * Tests to see if the expected HTTP status code is returned when making a GET request with a missing
	 * pickup parameter.
	 * @throws IOException if an IO error occurs
	 */
	@Test
	public void testGetRequestNoPickupParam() throws IOException {
		String url = "http://localhost:4567/rides/api?dropoff=3.431,2.421";
		assertEquals("Status code incorrect", HttpURLConnection.HTTP_BAD_REQUEST, this.getResponseCodeFromApiQuery(url));
	}
	
	/**
	 * Tests to see if the expected HTTP status code is returned when making a GET request with an invalid
	 * drop off value.
	 * @throws IOException if an IO error occurs
	 */
	@Test
	public void testGetRequestBadDropoffParam() throws IOException {
		String url = "http://localhost:4567/rides/api?pickup=3.412,2.431&dropoff=3.4312.421";
		assertEquals("Status code incorrect", HttpURLConnection.HTTP_BAD_REQUEST, this.getResponseCodeFromApiQuery(url));
	}
	
	/**
	 * Tests to see if the expected HTTP status code is returned when making an (unsupported) POST request.
	 * @throws IOException if an IO error occurs
	 */
	@Test
	public void testPostRequest() throws IOException {
		String url = "http://localhost:4567/rides/api?pickup=3.412,2.431&dropoff=3.431,2.421";
		assertEquals("Status code incorrect", HttpURLConnection.HTTP_NOT_IMPLEMENTED, this.getResponseCodeFromApiQuery(url, "POST"));
	}
	
	/**
	 * Tests to see if the expected HTTP status code is returned when making an (unsupported) PUT request.
	 * @throws IOException if an IO error occurs
	 */
	@Test
	public void testPutRequest() throws IOException {
		String url = "http://localhost:4567/rides/api?pickup=3.412,2.431&dropoff=3.431,2.421";
		assertEquals("Status code incorrect", HttpURLConnection.HTTP_NOT_IMPLEMENTED, this.getResponseCodeFromApiQuery(url, "PUT"));
	}
	
	/**
	 * Tests to see if the expected HTTP status code is returned when making an (unsupported) DELETE request.
	 * @throws IOException if an IO error occurs
	 */
	@Test
	public void testDeleteRequest() throws IOException {
		String url = "http://localhost:4567/rides/api?pickup=3.412,2.431&dropoff=3.431,2.421";
		assertEquals("Status code incorrect", HttpURLConnection.HTTP_NOT_IMPLEMENTED, this.getResponseCodeFromApiQuery(url, "DELETE"));
	}
	
	/**
	 * Returns the HTTP status code of the HTTP response given when an API query is made with the given URL.
	 * @param url the API endpoint to send the request to
	 * @return the HTTP status code of the HTTP response given when an API query is made with the given URL
	 * @throws IOException if an IO error occurs
	 */
	private int getResponseCodeFromApiQuery(String url) throws IOException {
		return ((HttpURLConnection) new URL(url).openConnection()).getResponseCode();
	}
	
	/**
	 * Returns the HTTP status code of the HTTP response given when an API query is made with the given URL
	 * and HTTP request method.
	 * @param url the API endpoint to send the request to
	 * @param requestMethod the HTTP request method to use (e.g. GET, POST, PUT, DELETE)
	 * @return the HTTP status code of the HTTP response given when an API query is made with the given URL
	 * and HTTP request method
	 * @throws IOException if an IO error occurs
	 */
	private int getResponseCodeFromApiQuery(String url, String requestMethod) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod(requestMethod);
		return conn.getResponseCode();
	}
}

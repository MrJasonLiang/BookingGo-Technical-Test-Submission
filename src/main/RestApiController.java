package main;

import static spark.Spark.*;

import com.google.gson.Gson;

/**
 * A class that provides the functionality of the SearchEngine class, exposed as a REST API.
 * @author Jay
 */
public class RestApiController {
	/**
	 * Constructs and initialises a new RestApiController object, which starts a REST API that handles
	 * ride requests.
	 */
	public RestApiController() {
		// Handler for GET requests querying for rides.
		get("/rides/api", (request, response) -> {
        	response.type("application/json");
			
			String pickup = request.queryParams("pickup");
        	String dropoff = request.queryParams("dropoff");
        	String numPassengers = request.queryParams("passengers");
        	
        	if (pickup == null) {
        		response.status(400);
        		return "{\"message\": \"Required String parameter 'pickup' is not present\"}";
        	} else if (!Utilities.locationStringValid(pickup)) {
        		response.status(400);
        		return "{\"message\": \"Invalid Pickup location. A valid location would be in the format 'lat,long', e.g. '3.410,-2.157'\"}";
        	} else if (dropoff == null) {
        		response.status(400);
        		return "{\"message\": \"Required String parameter 'dropoff' is not present\"}";
        	} else if (!Utilities.locationStringValid(dropoff)) {
        		response.status(400);
        		return "{\"message\": \"Invalid dropoff location. A valid location would be in the format 'lat,long', e.g. '3.410,-2.157'\"}";
        	}
        	
        	SearchResult searchResult;
        	Location pickupLocation = Utilities.stringToLocation(pickup);
    		Location dropoffLocation = Utilities.stringToLocation(dropoff);
    		
        	if (numPassengers == null) {
        		searchResult = new SearchEngine().searchRides(pickupLocation, dropoffLocation);
        	} else if (Utilities.numPassengersValid(numPassengers)) {
        		searchResult = new SearchEngine().searchRides(pickupLocation, dropoffLocation, Integer.parseInt(numPassengers));
        	} else {
        		response.status(400);
        		return "{\"message\": \"Invalid number of passengers. Must be a positive integer\"}";
        	}
        	
        	return new Gson().toJson(searchResult.getCheapestRidesDescPrice());
        });
		
		// Handlers for the unsupported POST, PUT and DELETE operations to the API.
		
		post("/rides/api", (request, response) -> {
			response.type("application/json");
			response.status(501);
    		return "{\"message\": \"POST operation not supported\"}";
		});

		put("/rides/api", (request, response) -> {
			response.type("application/json");
			response.status(501);
    		return "{\"message\": \"PUT operation not supported\"}";
		});

		delete("/rides/api", (request, response) -> {
			response.type("application/json");
			response.status(501);
    		return "{\"message\": \"DELETE operation not supported\"}";
		});
	}
}

# BookingGo Technical Test Submission

## Introduction

This project was written using Java 12 and uses Maven 3.6.1 to manage dependencies.

The third party libraries used are:
* [Spark](http://sparkjava.com/) - for creating a REST API
* [Gson](https://github.com/google/gson) - for converting JSON into Java objects and vice-versa
* [JUnit 4](https://junit.org/junit4/) - for unit testing

## Setup

First clone the repository using the following Git command:
```
git clone https://github.com/MrJasonLiang/BookingGo-Technical-Test-Submission.git
```

To compile the code, run the Maven command:
```
mvn clean package
```

To test the code, run the Maven command:
```
mvn test
```

## Part 1

### Console application to print the search results for Dave, Eric and Jeff's Taxis

To run the application, use the command:
```
java -cp target\bookinggo-tech-test-1.0-jar-with-dependencies.jar main.Part1 [lat1,long1] [lat2,long2] [passengers]
```

Where `[lat1,long1]` represents the pickup location in the format 'latitude,longitude', e.g. '3.412,-2.512', `[lat2,long2]` represents the drop off location in the same format, and `[passengers]` represents the number of passengers (the passengers argument is optional).

For example:
```
java -cp target\bookinggo-tech-test-1.0-jar-with-dependencies.jar main.Part1 3.412,-2.512 3.414,-2.341
```
or, specifying the number of passengers:
```
java -cp target\bookinggo-tech-test-1.0-jar-with-dependencies.jar main.Part1 3.412,-2.512 3.414,-2.341 6
```

## Part 2

To start the API, run the command:
```
java -cp target\bookinggo-tech-test-1.0-jar-with-dependencies.jar main.Part2
```

The API can be accessed via the URL `http://localhost:4567/rides/api` and takes two mandatory query parameters:
* pickup - the pickup location, in the format 'latitude,longitude', e.g. '3.412,-2.512'
* dropoff - the drop off location, in the same format

and an optional third query parameter 'passengers' specifying the number of passengers.

For example:
```
http://localhost:4567/rides/api?pickup=3.412,-2.512&dropoff=3.414,-2.341
```
or, specifying the number of passengers:
```
http://localhost:4567/rides/api?pickup=3.412,-2.512&dropoff=3.414,-2.341&passengers=6
```

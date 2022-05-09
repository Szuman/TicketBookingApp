# Ticket booking app

## Description

### Seat reservation system for a multiplex

REST API for ticket booking app, in which user is able to
book ticket(s) and seat(s) to the particular movie screening 
in multiplex cinema.

## Endpoints

* GET /screening/?from={datetime}&to={datetime} - get screening by time interval
* GET /screening/{id} - get screening by id
* POST /booking - make booking, adding booking to database
* GET /booking/{id} - get booking by id

## Build and run

To build and run application, you can run by maven

```
mvn spring-boot:run
```
or you can run shell script - start.sh
``` 
./start.sh
```

## Demo endpoints

To run demo with every endpoint included in api,
you can run shell script - endpoints.sh

``` 
./endpoints.sh
```

You can change the example request body for POST in file body.json

### Requirements
* Java 17
* Maven
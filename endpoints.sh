#!/bin/bash

# GET Screenings from time interval
echo "GET Screenings from time interval"
curl -v 'http://localhost:8080/screenings?from=2222-02-21T13:30:00&to=2222-02-23T19:30:00'
echo

# GET Screening by id
echo "GET Screening by id"
curl -v 'http://localhost:8080/screenings/1'
echo

# Make booking
echo "POST Booking"
curl -v POST 'http://localhost:8080/bookings' \
-H 'Content-Type: application/json; charset=utf-8' \
--data-binary '@body.json'

# GET Booking by id
echo
echo "GET Booking by id"
curl -v 'http://localhost:8080/bookings/1'
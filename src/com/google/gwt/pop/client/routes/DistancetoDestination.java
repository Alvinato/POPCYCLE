package com.google.gwt.pop.client.routes;

import com.google.gwt.core.client.JsArray;
import com.google.maps.gwt.client.DistanceMatrixRequest;
import com.google.maps.gwt.client.DistanceMatrixResponse;
import com.google.maps.gwt.client.DistanceMatrixService;
import com.google.maps.gwt.client.DistanceMatrixService.Callback;
import com.google.maps.gwt.client.DistanceMatrixStatus;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.TravelMode;

public class DistancetoDestination {

	
	private LatLng currLocation;
	private LatLng destination;

	public DistancetoDestination(LatLng destination, LatLng currLocation){
		this.destination = destination;
		this.currLocation = currLocation;
	}
	// try putting this code into the routes class and see if itll work with the points given...
	// lets just try printing out the distance first and then go from there
	public void getDistance(){
		System.out.println("getdistance is running");
		DistanceMatrixRequest distanceRequest = DistanceMatrixRequest.create();
		JsArray<LatLng> lopositions = null;
		JsArray<LatLng> destinations = null;
			
		destinations.push(destination);
		lopositions.push(currLocation);
		
		distanceRequest.setOrigins(lopositions);
		distanceRequest.setDestinations(destinations);
		distanceRequest.setTravelMode(TravelMode.BICYCLING);
		
		DistanceMatrixService distanceService = DistanceMatrixService.create();
		
		distanceService.getDistanceMatrix(distanceRequest, new Callback(){
		
			@Override
			public void handle(DistanceMatrixResponse a, DistanceMatrixStatus b) {
				System.out.println("the handler in distance is working");
				String distance = a.toString();
				System.out.println("this is the distance to our destination");
				System.out.println(distance);
			}
			
		});
		
		
		
	}
}

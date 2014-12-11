package com.google.gwt.pop.client.routes;


import com.google.gwt.core.client.JsArray;
import com.google.gwt.pop.client.racks.Racks;
import com.google.maps.gwt.client.DirectionsLeg;
import com.google.maps.gwt.client.DirectionsRenderer;
import com.google.maps.gwt.client.DirectionsRenderer.DirectionsChangedHandler;
import com.google.maps.gwt.client.DirectionsRendererOptions;
import com.google.maps.gwt.client.DirectionsRequest;
import com.google.maps.gwt.client.DirectionsResult;
import com.google.maps.gwt.client.DirectionsRoute;
import com.google.maps.gwt.client.DirectionsService;
import com.google.maps.gwt.client.DirectionsService.Callback;
import com.google.maps.gwt.client.DirectionsStatus;
import com.google.maps.gwt.client.Distance;
import com.google.maps.gwt.client.Duration;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.TravelMode;

public class Routes {
	
	private GoogleMap map;
	private LatLng destination;
	private LatLng origin;
	private double lat;
	private double lng;
	private JsArray<LatLng> loRacks;
	public double totalDistance;
	public double totalTime;
	
	public Routes(GoogleMap map, LatLng destination, LatLng origin, JsArray<LatLng> loRacks){
		this.map = map;
		this.destination = destination;
		this.loRacks = loRacks;
		this.origin = origin;
		
	}

	public void getroutefromorigin(){
		
			//System.out.println(this.lat);
			//System.out.println(this.lng);
			//LatLng origin = LatLng.create(lat,lng);
		 	
			DirectionsRequest request = DirectionsRequest.create();
		    request.setDestination(this.destination);
		    request.setOrigin(origin);
		    request.setTravelMode(TravelMode.BICYCLING);
		    // renders the results and puts it onto map
		    final DirectionsRenderer directionsrenderer = DirectionsRenderer.create();
		    directionsrenderer.setMap(this.map);
		    DirectionsService directionsService = DirectionsService.create();
		       
		    directionsService.route(request, new Callback(){
				public void handle(DirectionsResult a, DirectionsStatus b) {
					System.out.println("here");
					if(b == DirectionsStatus.OK){
						directionsrenderer.setDirections(a);
						
					}else{
						System.out.println("Error");
					}
				}
		    });
		    
		    
		    // make a new route method that plots bike racks...	
		    }
	public void getroutewithbikeracks(){
		
		
		DirectionsRequest request = DirectionsRequest.create();
	    request.setDestination(this.destination);
	    request.setOrigin(origin);
	    request.setTravelMode(TravelMode.BICYCLING);
	    // renders the results and puts it onto map
	    final DirectionsRenderer directionsrenderer = DirectionsRenderer.create();
	    
	    directionsrenderer.setMap(this.map);
	    DirectionsService directionsService = DirectionsService.create();
	  
	    directionsService.route(request, new Callback(){
			public void handle(DirectionsResult a, DirectionsStatus b) {
				System.out.println("here");
				if(b == DirectionsStatus.OK){
					directionsrenderer.setDirections(a);
					 //DirectionsResult result = DirectionsResult.create();
					    JsArray<DirectionsRoute> listofdirections = a.getRoutes();
					    System.out.print(listofdirections.length());
					    System.out.println("just before the loop");
					    for (int i = 0; i <= listofdirections.length(); i++){
					    	DirectionsRoute route = listofdirections.get(i);
					    	// contains the list of all the lat lons from the path...
					    	JsArray<LatLng> overview = route.getOverviewPath();
					    	// get the legs of the route
					    	JsArray<DirectionsLeg> leg = route.getLegs();
					    //	double totalDistance = 0;
					   // 	double totalTime = 0;
					    	for(int j = 0; j < leg.length(); j++){
					    		DirectionsLeg currleg = leg.get(j);
					    		Distance distance = currleg.getDistance();
					    		Duration time = currleg.getDuration();
					    		double doubleTime= time.getValue();
					    		double doubleDistance = distance.getValue();
					    		totalDistance += doubleDistance;
					    		totalTime += doubleTime;
					    	}
					    	//totalTime = totalTime*(1/60);
					    	//String stringDistance = Double.toString(totalDistance);
					    	//String stringTime = Double.toString(totalTime);
					    	
					    	//System.out.println(stringTime);
					    	//System.out.println(stringDistance);
					    	// this should go through the JsArray and print out all latlng points to see if its working..
					    	System.out.println("the loop is running");
					    	System.out.println(overview.length());
					    	for(int c = 0; c < overview.length(); c++){
					    		System.out.println("this part is running as well");
					    		LatLng locations = overview.get(c);
					    		double lat = locations.lat();
					    		double lng = locations.lng();
					    		System.out.println(lat);
					    		System.out.println(lng);
					    	}
					    	// i have all the locations in overview.  now i need to go through overview and check whether the loracks relate to them...
					    	// have to make a new plot racks object everytime...
					    	for(int g = 0; g < overview.length(); g++){
					    		LatLng locations = overview.get(g);
					    		Racks plotracks = new Racks(map, locations, null, loRacks, 500);
					    		plotracks.currLocationRacks();
					    	}
					    }
					    
					
				}else{
					System.out.println("Error");
				}
			}
	    });
	    
	}
	
	public double getMeters(){
		System.out.println("before the get statement");
		return this.totalDistance;
	}
	
	public double getSeconds(){
		System.out.println("before the get statement");
		return this.totalTime;
	}
}

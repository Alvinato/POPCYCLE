package com.google.gwt.pop.client.racks;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.google.maps.gwt.client.Circle;
import com.google.maps.gwt.client.CircleOptions;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.LatLngBounds;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerImage;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.Size;
import com.google.maps.gwt.client.geometry.Spherical;

public class Racks {
	private GoogleMap map;
	private LatLng curlocation;
	private int radius;
	private JsArray<LatLng> loRacks;
	JsArray<LatLng> sortedRacks;
	
	private LatLng closestPoint;
	private LatLng destination;

	public Racks(GoogleMap map, LatLng curlocation, LatLng destination, JsArray<LatLng> loRacks, int radius){
		this.map = map;
		this.curlocation = curlocation;
		this.loRacks = loRacks;
		this.radius = radius;
		this.destination = destination;
	}
	
	
	public void routeRacks(){
		// somehow have to find all the coordinates along a route and then compare every point to every point within our list
		// of latlngs... 
	}

	// we are going to want a couple methods, the racks around our current location and the racks around our destination... 
	// implement the racks around our current location first...
	
	public void destinationRacks(){
		// sort the racks at the destination...
		JsArray <LatLng> sortedRacks = sortRacks("destination");
		
		if(sortedRacks.length() == 0){
			System.out.println("There are no racks in your area");
			Window.alert("there are no bike racks in your area");
		}
		
		for(int i = 0; i <= sortedRacks.length(); i++){
			// go through the entire list of sortedRacks and plot everything... 
			 String iconName = "http://gridchicago.com/metra/images/icon_markers_116x116_black.png";
			 MarkerOptions markerOptions = MarkerOptions.create();
			 markerOptions.setOptimized(false);
			 MarkerImage image = MarkerImage.create(iconName);
			 image.getScaledSize();
			 Size size = Size.create(30, 30);
			 image.setScaledSize(size);
			 image.setScaledSize(size);
			 markerOptions.setIcon(image);
			Marker marker1 = Marker.create(markerOptions);
			 marker1.getVisible();
			 marker1.setPosition(sortedRacks.get(i));
			 marker1.setMap(map); 
		}
		/*System.out.println("blah blah blah");
		String iconName = "http://www2.psd100.com/ppp/2013/11/2701/Map-location-marker-1127205319.png";
		 MarkerOptions markerOptions = MarkerOptions.create();
		 markerOptions.setOptimized(false);
		 markerOptions.setDraggable(true);
		 MarkerImage image = MarkerImage.create(iconName);
		 image.getScaledSize();
		 Size size = Size.create(50, 50);
		 image.setScaledSize(size);
		 markerOptions.setIcon(image);
		 Marker marker = Marker.create(markerOptions);
		 marker.setPosition(closestPoint);
		 marker.setMap(map);*/
	}
	
	public void currLocationRacks(){
		 
	 
		// sorts an entire rack..
		JsArray<LatLng> sortedRacks = sortRacks("curlocation");
		// make if statement if no racks were found in there area... 
		if(sortedRacks.length() == 0){
			// maybe need to make a seperate one that doesnt have this option because when going through route many points...
			System.out.println("There are no racks in your area");
			Window.alert("there are no bike racks in your area");
		}
		
		System.out.println("sortedRacks.length:");
		System.out.println(sortedRacks.length());
		for(int i = 0; i < sortedRacks.length(); i++){
		
			 String iconName = "http://gridchicago.com/metra/images/icon_markers_116x116_black.png";
			 MarkerOptions markerOptions = MarkerOptions.create();
			 markerOptions.setOptimized(false);
			 MarkerImage image = MarkerImage.create(iconName);
			 image.getScaledSize();
			 Size size = Size.create(30, 30);
			 image.setScaledSize(size);
			 image.setScaledSize(size);
			 markerOptions.setIcon(image);
			 Marker marker = Marker.create(markerOptions);
			 marker.setPosition(sortedRacks.get(i));
			 marker.setMap(map); 
			 System.out.println("does this marker get initialized");
		}
	}
	// should parameterize sort Racks so that we can choose which racks to choose to sort...
	
	private JsArray<LatLng> sortRacks(String command){
		// basically have to check the distance between the two latlon points and if greater then include and if smaller then dont...
		
		// sorts the racks for the current location..
		JsArray<LatLng> sortedRacks = (JsArray<LatLng>) JsArray.createArray();
		if(command == "curlocation"){
		
		 double earthRadius = 3958.75;
		
		double curlat = curlocation.lat();
		double curlng = curlocation.lng();
		System.out.println("this line is working");
		int d = 0;
		for (int i = 0; i < loRacks.length(); i++){  // need to fix this... chnage parameters to jsArray.. or arraylist
			System.out.println("inside the loop is running as well");
			// its not running on the fifth time.. make sure to fix problem... 
			
			double lat = loRacks.get(i).lat();
			double lng = loRacks.get(i).lng();
			
			double dLat = Math.toRadians(curlat-lat);
			double dLng = Math.toRadians(curlng-lng);
			
			double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		               Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(curlat)) *
		               Math.sin(dLng/2) * Math.sin(dLng/2);
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		    double dist = earthRadius * c;

		    int meterConversion = 1609;
		    // check if it is within radius and if it is then add to the list of latlngs... 
		    
		    double distance = dist * meterConversion;
		    System.out.println("this is before the if stsatement");
		    System.out.println(distance);
		    if (distance <= radius){
		    	System.out.println("things are being added");
		    	double lat2 = loRacks.get(i).lat();
		    	double lng3 = loRacks.get(i).lng();
		    	System.out.println(lat2);
		    	System.out.println(lng3);
		    	LatLng rack = loRacks.get(i);
		    	System.out.println(d);
		    	sortedRacks.set(d, rack); 
		    	System.out.println(sortedRacks.length());
		    	d++;
		    }
		}
		 return sortedRacks;
		}
	
	
	
	// find a placeholder for the closest one...
	if (command == "destination"){
		 double earthRadius = 3958.75;
			
			double curlat = destination.lat();
			double curlng = destination.lng();
			int d = 0;
			for (int i = 0; i < loRacks.length(); i++){
				
				double lat = loRacks.get(i).lat();
				double lng = loRacks.get(i).lng();
				
				double dLat = Math.toRadians(curlat-lat);
				double dLng = Math.toRadians(curlng-lng);
				
				double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
			               Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(curlat)) *
			               Math.sin(dLng/2) * Math.sin(dLng/2);
			    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
			    double dist = earthRadius * c;

			    int meterConversion = 1609; 
			    
			    double distance = dist * meterConversion;
			    
			    if (distance <= radius){
			    	sortedRacks.set(d, loRacks.get(i)); 
			    	d++;
			    }
			    double distanceClosest = 999999999;
			    if (distance <= distanceClosest){
			    	distanceClosest = distance;
			    	closestPoint = loRacks.get(i);
			    	System.out.println("the closest point so far");	
			    }
			    }
			}
	
	if (command == "route"){
		
		
	}
	return sortedRacks;
	
	}	
}

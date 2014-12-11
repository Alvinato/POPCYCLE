package com.google.gwt.pop.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class GeoCoder {
	
	//GeoCode request URL
	// Passing json so we can get the output in a json format
	
	private static final String jsonURL = 
			"http://maps.googleapis.com/maps/api/geocode/json";
	
	//address is in the form of address, city, province
	//changes the json object to a string
	public String createJSONobj(String fullAddress) throws IOException
	{
		//have to convert the string into UTF8 format
		URL url = new URL (jsonURL + "?address=" + 
		URLEncoder.encode(fullAddress, "UTF-8") + "&sensor=false");
		
		//showme url
		//System.out.println(url);
		// Open the connection
		
		URLConnection conn = url.openConnection();
		
		// this byte array output keeps output data from google
		ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
		
		// copy the output data from Google which is in JSON
		
		IOUtils.copy(conn.getInputStream(), output);
		
		// close output stream
		output.close();
		
		return output.toString();
		
	}
	
	
	

	public static void main(String[] args) 
	{
		
	// test to see what values the createJSON does
	//	try {
	//		System.out.println(createJSONobj("134 Abbott St, Vancouver, British Columbia"));
	//	} catch (IOException e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		//}
		// test if my parser can work with the json format
		/**
		Parser parser = new Parser();
		parser.read();
		ArrayList<PointOfInterest> testpoints = parser.getPointList();
		PointOfInterest poiAtJ = new PointOfInterest();
		for (int i =0; i < testpoints.size(); i++){
			//PointOfInterest poiAtJ = new PointOfInterest();
			poiAtJ = testpoints.get(i);
			System.out.println("Street Number= "   + poiAtJ.getStNum() +"");
			System.out.println("Street Address= "   + poiAtJ.getStName() +"");
		}
		
		PointOfInterest testpoi = new PointOfInterest();
		testpoi = testpoints.get(0);
		try {
			// change this back to static for it to work
			createJSONobj(""+ testpoi.getStNum()+" ,"  + testpoi.getStName() + ", Vancouver, British Columbia");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		**/
	}

	
	
	

}

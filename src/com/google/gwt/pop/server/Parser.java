package com.google.gwt.pop.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.google.gwt.pop.shared.PointOfInterest;

 
public class Parser {
	
	/** 
	PointOfInterest Structure:
	
	StreetNum
	StreetName
	**/
	
	ArrayList<PointOfInterest> alopoi = new ArrayList<PointOfInterest>();
	
	

	public void read(){
		
		URL bikeData;
		String strLine;
		String endFile = ",,,,,,,,,,,,";
		final String deLimit = ",";
		
		
		//ArrayList<PointOfInterest> lopoi = new ArrayList<PointOfInterest>();
		
		
		try {
			// get URL content
			bikeData = new URL("http://www.ugrad.cs.ubc.ca/~e9i8/2012BikeRackData.csv");
			// create a new connection
			URLConnection conn = bikeData.openConnection();
			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));
			
			int i = 1;
			while ((strLine = br.readLine()) != null)
			{
				if (i >= 5)
				{
					if (strLine.equals(endFile))
					{
						break;
					}
					
					// use comma as separator
					String[] bikerack = strLine.split(deLimit);
		 
					//System.out.println("Street Number= " + bikerack[1] 
		            //                     + ", Street name= " + bikerack[2] + "]");
					
					PointOfInterest poi = new PointOfInterest();
					
					poi.setStNum(bikerack[1]);
					poi.setStName(bikerack[2]);
					alopoi.add(poi);
					
					
				}
				i++;
			}
			
			br.close();
 
			System.out.println("Done");
 
		} catch (MalformedURLException e) {
			System.out.println("mal exception thrown");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO exception thrown");
			e.printStackTrace();
		}
 
	}
	
	
	public ArrayList<PointOfInterest> getPointList() {
		return this.alopoi;
	}
}
/**
public static void main(String args[]){
	
	Parser parser = new Parser();
	
	
}
**/
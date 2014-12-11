package com.example.popcycle.server;

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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
 
public class BikeRackParser {
	public static void main(String[] args) {
 
		
		URL bikeData;
		String strLine;
		String endFile = ",,,,,,,,,,,,";
		final String deLimit = ",";
		
		/** 
		PointOfInterest Structure:
		
		StreetNum
		StreetName
		**/
		
		ArrayList<PointOfInterest> lopoi = new ArrayList<PointOfInterest>();
		
		
		try {
			// get URL content
			bikeData = new URL("ftp://webftp.vancouver.ca/opendata/bike_rack/2012BikeRackData.csv");
			// create a new connection
			URLConnection conn = bikeData.openConnection();
			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));
			
			//HashMap lineMap = new HashMap<String, int>();
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
					lopoi.add(poi);
					
					
				}
				i++;
			}
			
			// TESTING TO SEE IF THE ARRAYLIST IS RIGHT

			for (int j = 0; j < lopoi.size(); j++)
			{
				PointOfInterest poiAtJ = lopoi.get(j); 
				System.out.println("Street Number= "   + poiAtJ.getStNum() +"");
				System.out.println("Street Address= "   + poiAtJ.getStName() +"");
			}
			
			System.out.println(lopoi.size());
				
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
}
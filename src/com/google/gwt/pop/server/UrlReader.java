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
 
// TO VIEW WHAT THE CSV FILE IS BEING READ LIKE IN JAVA
public class UrlReader {
	public static void main(String[] args) {
 
		URL bikeData;
 
		try {
			// get URL content
			bikeData = new URL("ftp://webftp.vancouver.ca/opendata/bike_rack/2012BikeRackData.csv");
			// create a new connection
			URLConnection conn = bikeData.openConnection();
 
			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));
 
			String inputLine;
 /**
			//save to this filename
			String fileName = "/Users/Austin/Third year summer/CPSC 310/310 project/text.html";
			File file = new File(fileName);
 
			if (!file.exists()) {
				file.createNewFile();
			}
 
			//use FileWriter to write file
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
 **/
			
			while ((inputLine = br.readLine()) != null) {
				//bw.write(inputLine);
				System.out.println(inputLine);
			}
 
			//bw.close();
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
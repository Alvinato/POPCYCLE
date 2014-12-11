package com.google.gwt.pop.server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gwt.pop.shared.PointOfInterest;

public class BikeRackParser {

	ArrayList<PointOfInterest> alopoi = new ArrayList<PointOfInterest>();

	
	public void readBikeRack(String urlstr)
	{
		String urlLink = urlstr;
		
		URL bikeRackData;
		String strLine;
		final String deLimit = ",";
		final String strEnd = ",,,,,,,,,,,,";
		
		
		try{
			bikeRackData = new URL(urlLink);
			URLConnection conn = bikeRackData.openConnection();
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			
			System.out.println("here");
			
			int i = 0;
			strLine = br.readLine();
			while ((strLine = br.readLine()) != null)
			{
				if ( i >= 4)
				{
					// this is the end of the file
					if (strLine.equals(strEnd)){
						break;
					}
					String[] rackList = strLine.split(deLimit);
					PointOfInterest poi = new PointOfInterest();
					//System.out.println(rackList[1]);
					poi.setStNum(rackList[1]);
					//System.out.println(rackList[2]);
					poi.setStName(rackList[2]);
					//System.out.println(strLine);
					alopoi.add(poi);
				
				}
				i++;
			}
			br.close();
		} catch (MalformedURLException e) {
			System.out.println("mal exception thrown");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO exception thrown");
			e.printStackTrace();
		}
		
	}
	
	public void getJSONobj(ArrayList<PointOfInterest> listpoi)
	{
		final String jsonURL = "http://maps.googleapis.com/maps/api/geocode/json";
		int j = 0;
		for (PointOfInterest point: listpoi)
		{
			if (j % 7 == 0)
			{
				try{
					Thread.sleep(1400);
				}catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				
			}
			try{
				Thread.sleep(100);
			}catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			String stNum = point.getStNum();
			String stName = point.getStName();
			String address = ""+stNum+" "+stName+"" + "Vancouver, British Columbia";
			//System.out.println(address);
			try {
				URL url = new URL(jsonURL + "?address=" + URLEncoder.encode(address, "UTF-8") +
						"&sensor=false");
				
				URLConnection conn = url.openConnection();
				
				ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
				
				IOUtils.copy(conn.getInputStream(), output);
				
				output.close();
				
				String jsonAdd = output.toString();
				//System.out.println(output.toString());
				System.out.println("setting json obj");
				System.out.println(j);
				point.setJSONadd(jsonAdd);
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			j++;
		}
	}
	
	public void setLatLng(ArrayList<PointOfInterest> pointswithjson)
	{
		for(PointOfInterest currpoi: pointswithjson)
		{
		try {
			String jsonstr = currpoi.getJSONadd();
			JSONObject jsonobj = new JSONObject(jsonstr);
			JSONArray array = jsonobj.getJSONArray("results");
			if (array.length() != 0)
			{
				JSONObject geometry = jsonobj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry");
				JSONObject location = geometry.getJSONObject("location");
				String lat = location.getString("lat");
				String lng = location.getString("lng");
				currpoi.setLat(lat);
				currpoi.setLng(lng);
				currpoi.setJSONadd("has been used");
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	
	
	public ArrayList<PointOfInterest> getRackList(){
		return alopoi;
	}
	
	/**
	public static void main(String[] args) 
	{
		String urlstrp1 = "http://www.ugrad.cs.ubc.ca/~e9i8/2012BikeRackDatap1.csv";
		BikeRackParser brp = new BikeRackParser();
		brp.readBikeRack(urlstrp1);
		ArrayList<PointOfInterest> testList = brp.getRackList();
		brp.getJSONobj(testList);
		brp.setLatLng(testList);
		
		for (PointOfInterest point: testList)
		{
			System.out.println(point.getStName());
			System.out.println(point.getStNum());
			System.out.println(point.getLat());
			System.out.println(point.getLng());
			
		}
		
	}
	**/
	
	
}

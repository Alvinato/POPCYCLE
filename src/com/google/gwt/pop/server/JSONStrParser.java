package com.google.gwt.pop.server;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gwt.pop.shared.PointOfInterest;



public class JSONStrParser {
	
	Parser parser = new Parser();
	
	//ArrayList<PointOfInterest> alopoilat = new ArrayList<PointOfInterest>();
	
	//ArrayList<PointOfInterest> alopoilat = parser.getPointList();
	
	//GeoCoder jsonstr = new GeoCoder();

	PointOfInterest newpoi;
	String corraddress;
	
	public void parserJSONstr(String jsonstr, PointOfInterest currpoi)
	{
		System.out.println(jsonstr);

		try {
			
			JSONObject jsonobj = new JSONObject(jsonstr);
			JSONArray array = jsonobj.getJSONArray("results");
			//System.out.println("here");
			//System.out.println(array.length());
			if (array.length() != 0){
				//System.out.println("in if");
			JSONObject geometry = jsonobj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry");
			JSONObject location = geometry.getJSONObject("location");
			String lat = location.getString("lat");
			String lng = location.getString("lng");
			currpoi.setLat(lat);
			currpoi.setLng(lng);
			//System.out.println(lat);
			System.out.println(currpoi.getStName());
			System.out.println(currpoi.getLat());
			System.out.println(currpoi.getLng());
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	public static void main(String[] args) 
	{
		
		Parser parser = new Parser();
		parser.read();
		
		ArrayList<PointOfInterest> testpoints = parser.getPointList();
		System.out.println(parser.getPointList().get(0).getStName());
		//PointOfInterest poiAtJ = new PointOfInterest();
		PointOfInterest testpoi = new PointOfInterest();
		testpoi = testpoints.get(0);
		System.out.println(testpoi.getStNum());
		String jsonstr;
		GeoCoder geocoder = new GeoCoder();
		try {
			jsonstr = geocoder.createJSONobj
					(""+ testpoi.getStNum()+" ,"  + testpoi.getStName() + ", Vancouver, British Columbia");
			parserJSONstr(jsonstr, testpoi);
			//System.out.println(jsonstr);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	**/

}

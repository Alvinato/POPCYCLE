package com.google.gwt.pop.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.google.gwt.pop.shared.DrinkingFountain;

public class DrinkingParser {

	ArrayList<DrinkingFountain> alodf = new ArrayList<DrinkingFountain>();
	public void readDrink()
	{
		String urlLink = "http://www.ugrad.cs.ubc.ca/~e9i8/drinkingFountains.csv";
		URL drinkData;
		String strLine;
		final String deLimit = ",";
		
		try{
			drinkData = new URL(urlLink);
			URLConnection conn = drinkData.openConnection();
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			
			System.out.println("here");
			
			int i = 1;
			
			while((strLine = br.readLine()) != null)
			{
				if (i >= 2){
				String[] drinkList = strLine.split(deLimit);
				//System.out.println(strLine);
				DrinkingFountain df = new DrinkingFountain();
				df.setDlat(drinkList[0]);
				System.out.println(df.getDlat());
				df.setDlon(drinkList[1]);
				System.out.println(drinkList[1]);
				if (drinkList[2].contains("Temporary Fountain"))
				{
					df.setDadd((drinkList[2].substring(28)));
					System.out.println(df.getAdd());
					alodf.add(df);
				}
				if (drinkList[2].contains("Bottle Filling Station location:"))
				{
					df.setDadd((drinkList[2].substring(33)));
					System.out.println(df.getAdd());
					alodf.add(df);
				}
				else{
					df.setDadd((drinkList[2].substring(18)));
					System.out.println(df.getAdd());
					alodf.add(df);
				}
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
	
	
	public ArrayList<DrinkingFountain> getDflist()
	{
		return alodf;
	}
	
	public static void main(String[] args) 
	{
		DrinkingParser dp = new DrinkingParser();
		dp.readDrink();
	}
}

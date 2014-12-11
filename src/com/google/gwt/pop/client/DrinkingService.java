package com.google.gwt.pop.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.pop.shared.DrinkingFountain;

@RemoteServiceRelativePath("DrinkingService")
public interface DrinkingService extends RemoteService{
	
	public void importDrinkingData();
	public void deleteDrinkData();
	public ArrayList<DrinkingFountain> getDrinkpoints();

}

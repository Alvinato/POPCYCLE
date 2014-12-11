package com.google.gwt.pop.client;

import java.util.ArrayList;

import com.google.gwt.pop.shared.DrinkingFountain;
import com.google.gwt.pop.shared.PointOfInterest;
import com.google.gwt.pop.shared.UserInformation;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserInfoServiceAsync {
	
	  public void addUserInformation(String firstName, String lastName, String emailAddress, String description, AsyncCallback<Void> async);
	  // public void removeStock(String symbol, AsyncCallback<Void> async);
	  public void getUserInformation(String emailAddress, AsyncCallback<UserInformation> async);
	  
	  public void updateSavedRoutes(UserInformation userInformation, AsyncCallback<Void> async);
	void getAllUsers(AsyncCallback<ArrayList<UserInformation>> callback);
	void updateTravelledDestination(PointOfInterest poi, String email,
			AsyncCallback<Void> callback);
	void checkUserExists(String email, AsyncCallback<Boolean> callback);
	void getFreqbr(ArrayList<String> bikerackuserlist,
			AsyncCallback<Long> callback);
	void createUser(UserInformation user, AsyncCallback<Void> callback);
	void updateUser(String firstName, String lastName, String emailAddress,
			String description, AsyncCallback<Void> callback);
	public void sortMostDistance(ArrayList<UserInformation> result,
			AsyncCallback<ArrayList<UserInformation>> asyncCallback);
	void addDistance(String email, Integer travelled, AsyncCallback<Void> callback);
}

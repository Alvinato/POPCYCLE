package com.google.gwt.pop.client;

import java.util.ArrayList;

import com.google.gwt.pop.shared.DrinkingFountain;
import com.google.gwt.pop.shared.PointOfInterest;
import com.google.gwt.pop.shared.UserInformation;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UserInfoService")

public interface UserInfoService extends RemoteService {
	
	  public void addUserInformation(String firstName, String lastName, String emailAddress, String description) throws NotLoggedInException;
	  // public void removeUserInfo(String symbol) throws NotLoggedInException;
	  public UserInformation getUserInformation(String emailAddress) throws NotLoggedInException;
	  
	  public void updateSavedRoutes(UserInformation userInformation) throws NotLoggedInException;
	  
	  public ArrayList<UserInformation> getAllUsers();
	  
	  public void updateTravelledDestination(PointOfInterest poi, String email);
	  
	  public boolean checkUserExists(String email);
	  
	  public Long getFreqbr(ArrayList<String> bikerackuserlist);
	  
	  public void createUser(UserInformation user);
	
	  void updateUser(String firstName, String lastName, String emailAddress,
			String description);
	  
	  public ArrayList<UserInformation> sortMostDistance(ArrayList<UserInformation> loui);
	  
	  public void addDistance(String email, Integer travelled);
}

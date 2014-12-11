package com.google.gwt.pop.shared;

import java.io.Serializable;
import java.util.ArrayList;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
//HUEHUEHUEHUEHUEHUEHUEHUE
public class UserInformation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6494810767595794289L;
	
	@Persistent
	private String firstName;
	
	@Persistent
    private String lastName;
    
    @PrimaryKey
    @Persistent
    private String emailAddress;
    
    @Persistent
    private String description;
    
    
    //start with empty list
    @Persistent
    public ArrayList<String> currentLatLng;
    
    @Persistent
    public ArrayList<String> destinationLatLng;
    
    @Persistent
    public ArrayList<Double> currentLat;
    
    @Persistent
    public ArrayList<Double> currentLong;
    
    @Persistent
    public ArrayList<Double> destinationLat;
    
    @Persistent
    public ArrayList<Double> destinationLong;
    
    //at the moment it is drinking ids
    @Persistent
    public ArrayList<String> IDSelected;
    
    //User's distance travelled
    @Persistent
    public Integer distanceTravelled;
    
    public void setListDestination() {
    	ArrayList<String> los = new ArrayList<String>();
    	los.add("0");
    	IDSelected = los;
    }
    
    public ArrayList<String> getListDestination() {
    	return IDSelected;
    }
    
    //private ArrayList<SavedRoutes> savedRoutes = new ArrayList<SavedRoutes>();     
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    public void addRoute(String currentLatLng, String destinationLatLng, Double currentLat, Double currentLong, 
    		Double destinationLat, Double destinationLong) {
    	
    	this.currentLatLng.add(currentLatLng);
    	this.destinationLatLng.add(destinationLatLng);
    	this.currentLat.add(currentLat);
    	this.currentLong.add(currentLong);
    	this.destinationLat.add(destinationLat);
    	this.destinationLong.add(destinationLong);
  
    }
    
    public void deleteRoute(int index) {
    	
    	currentLatLng.remove(index);
    	destinationLatLng.remove(index);
    	currentLat.remove(index);
    	currentLong.remove(index);
    	destinationLat.remove(index);
    	destinationLong.remove(index);
    	
    }
    public void addPointforUser(String pointKey){
    	IDSelected.add(pointKey);
    }
    
    public void initializeDistance()
    {
    	distanceTravelled = 0;
    }
    
    public Integer getDistance(){
    	return distanceTravelled;
    }
    
    public void setDistance(int distance){
    	distanceTravelled = distance;
    }

}

package com.google.gwt.pop.shared;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PointOfInterest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2657284607477979275L;
	// the data structure
	 @PrimaryKey
	 @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	 private Long key;
	 @Persistent
	private String StNum;
	 @Persistent
	private String StName;
	 @Persistent
	 private String lattitude;
	 @Persistent
	 private String longitude;
	 
	 //Austin Change
	 @Persistent
	 private String jsonAdd;

	 @Persistent
	 private int frequency;
	
	
	//edit this.
	public PointOfInterest()
	{
		StNum = "";
		StName = "";
		frequency = 0;
	}
	// the getters and setters
	public String getStNum() {
		return StNum;
	}
	public void setStNum(String stNum) {
		StNum = stNum;
	}
	public String getStName() {
		return StName;
	}
	public void setStName(String stName) {
		StName = stName;
	}
	public String getLat() {
		return lattitude;
	}
	public void setLat(String lat) {
		this.lattitude = lat;
	}
	public String getLng() {
		return longitude;
	}
	public void setLng(String lng) {
		this.longitude = lng;
	}
	
	public void setJSONadd(String jsonadd)
	{
		this.jsonAdd = jsonadd;
	}
	public String getJSONadd()
	{
		return jsonAdd;
	}
	
	public int getFreq(){
		return frequency;
	}
	
	//increment frequency
	public void incremFreq()
	{
		frequency = frequency + 1;
	}
	
	public long getKey(){
		return key;
	}
	//increment frequency
	public void setFrequency(int freq)
	{
		frequency = freq;
	}

}

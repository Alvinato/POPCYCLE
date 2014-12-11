package com.example.popcycle.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PointOfInterest {
	
	// the data structure
	 
	 @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String StNum;
	 @Persistent
	private String StName;
	
	
	//edit this.
	public PointOfInterest()
	{
		StNum = "";
		StName = "";
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

}

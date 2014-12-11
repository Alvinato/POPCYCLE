package com.google.gwt.pop.shared;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class DrinkingFountain implements Serializable{
	
	private static final long serialVersionUID = -2657284607477979275L;
	// the data structure
	 @PrimaryKey
	 @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	 private Long key;
	 @Persistent
	private String address;
	 @Persistent
	 private String drinkLatt;
	 @Persistent
	 private String drinkLong;
	 
	 
	 public DrinkingFountain()
	 {}
	 
	 public String getAdd()
	 {
		 return address;
	 }
	 
	 public String getDlat()
	 {
		 return drinkLatt;
	 }
	 
	 public String getDlon()
	 {
		 return drinkLong;
	 }
	 
	 public void setDadd(String add)
	 {
		 address = add;
	 }
	 
	 public void setDlat(String lat)
	 {
		 drinkLatt = lat;
	 }
	 
	 public void setDlon(String lon)
	 {
		 drinkLong = lon;
	 }
	 
		public long getKey(){
			return key;
		}

}

package com.google.gwt.pop.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.pop.client.NotLoggedInException;
import com.google.gwt.pop.client.UserInfoService;
import com.google.gwt.pop.shared.DrinkingFountain;
import com.google.gwt.pop.shared.PointOfInterest;
import com.google.gwt.pop.shared.UserInformation;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserInfoServiceImpl extends RemoteServiceServlet implements UserInfoService{
	
	private static final Logger LOG = Logger.getLogger(UserInfoServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF 
		= JDOHelper.getPersistenceManagerFactory("transactions-optional");
	private static ArrayList<UserInformation> loui = new ArrayList<UserInformation>();
	
	public void addUserInformation(String firstName, String lastName, String emailAddress, String description) throws NotLoggedInException{
		System.out.println("ADD USER INFO PRINT");
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			UserInformation userInfo = new UserInformation();
			userInfo.setFirstName(firstName);
			userInfo.setLastName(lastName);
			userInfo.setEmailAddress(emailAddress);
			userInfo.setDescription(description);
			pm.makePersistent(userInfo);
		} finally {
			pm.close();
		}
	}
	
	@Override
	 public void updateUser(String firstName, String lastName, String emailAddress, String description) {
	  PersistenceManager pm = getPersistenceManager();
	     try {
	         UserInformation user = (UserInformation) pm.getObjectById(UserInformation.class, 
	           emailAddress);
	         user.setDescription(description);
	         user.setFirstName(firstName);
	         user.setLastName(lastName);
	        
	     } finally {
	         pm.close();
	     }
	}
	public void updateSavedRoutes(UserInformation userInformation) throws NotLoggedInException {
		System.out.println("SAVED ROUTES PRINT");
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(userInformation);
		} finally {
			pm.close();
		}
		
	}


	private void checkLoggedIn() throws NotLoggedInException{
		// TODO Auto-generated method stub
		if (getUser() == null) {
			throw new NotLoggedInException("Not Logged In.");
		}
		
	}

	private User getUser() {
		// TODO Auto-generated method stub
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}
	
	public UserInformation getUserInformation(String emailAddress) throws NotLoggedInException {
		checkLoggedIn();
		
		
		PersistenceManager pm = getPersistenceManager();
		
		try {
			Query q = pm.newQuery(UserInformation.class, "emailAddress == queryEmail");
			q.declareParameters("String queryEmail");
			List<UserInformation> userInfos = (List<UserInformation>) q.execute(emailAddress);
			
			//THIS LINE HAS TO BE HERE OR ELSE WILL HAVE SERIALIZATION PROBLEM DONT KNOW WHY
			userInfos = (List<UserInformation>) pm.detachCopyAll(userInfos);
			
			if (userInfos.size() == 0) {
				return null;
			} else {
				return userInfos.get(0);
			}
			
			/*for (UserInformation userInfo : userInfos) {
				firstName.add(userInfo.getFirstName());
				lastName.add(userInfo.getLastName());
				emailAddress.add(userInfo.getEmailAddress());
				description.add(userInfo.getDescription());
				
			} */
		} finally {
			pm.close();
		}
		
	}
	
	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}

	@Override
	public ArrayList<UserInformation> getAllUsers() {
		PersistenceManager pm = getPersistenceManager();
		
		try{
			Query query = pm.newQuery(UserInformation.class);
			List<UserInformation> users = (List<UserInformation>) query.execute();
			users = (List<UserInformation>) pm.detachCopyAll(users);
			for(UserInformation user: users)
			{
				loui.add(user);
			}
		}
		
		finally{
			pm.close();
		}
		return (ArrayList<UserInformation>) loui;
	}

	@Override
	public void updateTravelledDestination(PointOfInterest poi, String email) {
		PersistenceManager pm = getPersistenceManager();
	    try {
	        UserInformation user = (UserInformation) pm.getObjectById(UserInformation.class, 
	        		email);
	        
	        String val = Long.toString(poi.getKey());
	        	user.addPointforUser(val);
	    } finally {
	        pm.close();
	    }
		
	}

	@Override
	public boolean checkUserExists(String email) {
		PersistenceManager pm = getPersistenceManager();
		//UserInformation user = new UserInformation();
		boolean exists = false;
	    try {
	    	Query query = pm.newQuery(UserInformation.class);
	    	List<UserInformation> users = (List<UserInformation>) query.execute();

	    		for(UserInformation user: users)
	    		{
	    			if (user.getEmailAddress().equals(email))
	    			{
	    				exists = true;
	    			}
	    		}
	        
	    } finally {
	        pm.close();
	    }
return exists;
	}

	@Override
	public void createUser(UserInformation user) {
		PersistenceManager pm = getPersistenceManager();
		
		try{
			pm.makePersistent(user);
		}
		finally{
			pm.close();
		}
		
	}

	@Override
	 public Long getFreqbr(ArrayList<String> bikerackuserlist) {
	  int freqCounter = 0;
	  int highestCounter = 0;
	  Long valhighestfreq = 0L;
	  String currval;

	  if (bikerackuserlist.size() < 1)
	  {
	   String bikeRack = bikerackuserlist.get(0);
	   valhighestfreq = Long.parseLong(bikeRack);

	   return valhighestfreq;
	  }
	  
	  else{
	   
	   for (int i = 0; i < bikerackuserlist.size(); i++)
	   {
	    String poi1 = bikerackuserlist.get(i);
	    freqCounter = 0;
	    for (int j = 0; j < bikerackuserlist.size(); j++)
	    {
	     String poi2 = bikerackuserlist.get(j);
	     currval = poi2;
	     if (poi1.equals(poi2)){
	      freqCounter++;
	     }
	     
	     if (highestCounter < freqCounter)
	     {
	      highestCounter = freqCounter;
	      valhighestfreq = Long.parseLong(currval);
	     }
	    }
	   }
	   return valhighestfreq;
	  }
	 //return 0;
	 }
	  
	  /*
	  
	  for (int i = 0; i < loi.size(); i++) {
		  int k = 0;
		  for (int j = 0; j < loi.size(); j++) {
			  if (loi.get(i).equals(loi.get(j))) {
				  k++;
			  }
			  if (k == 2) {
				  if (loi.contains(loi.get(i))) {
					  break;
				  }
				  
			  }
		  }
	  }
	  
	  
	  
	  for(int i = 0; i<loi.size(); i++)
	  {
	   if (currfreq > highestfreq)
	   {
	    freqCounter = currfreq;
	    highestfreq = currval;
	   }
	   for(int j = i; j<loi.size(); j++)
	   {
	    currval = loi.get(j);
	    if(loi.get(i) == (loi.get(j)))
	    {
	     currfreq++;
	    }
	   }
	  }

	  return highestfreq;
	  
	  */
	  
	 
	 private ArrayList<Integer> swapSort(ArrayList<Integer> aloi){
	  int val;
	  int temp;
	  
	  boolean flag = true;
	  
	  while (flag)
	  {
	   flag = false;
	   {
	    for(int j=0; j< aloi.size(); j++)
	    {
	     if (aloi.get(j) > aloi.get(j+1))
	     {
	      temp = aloi.get(j);
	      aloi.set(j, aloi.get(j+1));
	      aloi.set(j+1, temp);
	      flag = true;
	      
	     }
	    }
	   }
	  }
	  
	  int currentValue = Integer.parseInt("1234");
	  
	  
	  
	  return aloi;
	  
	 }
	 
	 private ArrayList<Integer> makeintointList(ArrayList<String> alos)
	 {
	  ArrayList<Integer> loi = new ArrayList<Integer>();
	  if (alos == null){
	   return null;
	  }
	  
	  for (String str: alos)
	  {
	   int currentValue = Integer.parseInt(str);
	   loi.add(currentValue);
	  }
	  
	  return loi;
	 }
	 
	 
	 @Override
		public ArrayList<UserInformation> sortMostDistance(ArrayList<UserInformation> loui)
		{
		        UserInformation temp;
		        for(int i=0; i < loui.size()-1; i++){
		 
		            for(int j=1; j < loui.size()-i; j++){
		            	int freq1 = loui.get(j).getDistance();
		            	int freq2 = loui.get(j-1).getDistance();
		                if(freq2 < freq1){
		                	
		                    temp = loui.get(j-1);
		                    loui.set(j - 1, loui.get(j));
		                   // arr[j-1] = arr[j];
		                  //  arr[j] = temp;
		                    loui.set(j, temp);
		                    
		                }
		            }
		            //System.out.println((i+1)+"th iteration result: "+Arrays.toString(arr));
		        }
		        return loui;
		    
		}

	 @Override
		public void addDistance(String email, Integer travelled) {
			PersistenceManager pm = getPersistenceManager();
			try{
				UserInformation user = (UserInformation) pm.getObjectById(UserInformation.class, 
		        		email);
				int distance = user.getDistance() + travelled;
				user.setDistance(distance);
			}
			finally{
				pm.close();
			}
			
			
		}
}
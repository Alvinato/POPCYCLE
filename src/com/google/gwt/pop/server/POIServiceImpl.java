package com.google.gwt.pop.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.pop.client.NotLoggedInException;
import com.google.gwt.pop.client.POIService;
import com.google.gwt.pop.shared.PointOfInterest;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


@SuppressWarnings("serial")
public class POIServiceImpl extends RemoteServiceServlet implements
POIService {

 // private static final Logger LOG = Logger.getLogger(POIServiceImpl.class.getName());
  private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");
  

List<PointOfInterest> alopoi = new ArrayList<PointOfInterest>();
PointOfInterest testpoi = new PointOfInterest();

@Override
public ArrayList<PointOfInterest> getPoints() {
	PersistenceManager pm = getPersistenceManager();
	
	try{
		Query query = pm.newQuery(PointOfInterest.class);
		List<PointOfInterest> points = (List<PointOfInterest>) query.execute();
		
		for(PointOfInterest poi: points)
		{
			alopoi.add(poi);
		}
	}
	
	finally{
		pm.close();
	}
	return (ArrayList<PointOfInterest>) alopoi;
}

@Override
public void importDatap1()
{
	String urlstrp1 = "http://www.ugrad.cs.ubc.ca/~e9i8/2012BikeRackDatap1.csv";
	BikeRackParser brp = new BikeRackParser();
	brp.readBikeRack(urlstrp1);
	ArrayList<PointOfInterest> listofpoints = brp.getRackList();
	brp.getJSONobj(listofpoints);
	brp.setLatLng(listofpoints);
	
	PersistenceManager pm = getPersistenceManager();
	try{
		pm.makePersistentAll(listofpoints);
	}
	finally
	{
		pm.close();
	}
}



private PersistenceManager getPersistenceManager() {
   return PMF.getPersistenceManager();
}



@Override
public void addPOI(PointOfInterest poi) throws NotLoggedInException {
	// TODO Auto-generated method stub
	
}

@Override
public void removePOI(PointOfInterest poi) throws NotLoggedInException {
	// TODO Auto-generated method stub
	
}

@Override
public void deleteallPOI() {
	PersistenceManager pm = getPersistenceManager();
	
	try{
		Query query = pm.newQuery(PointOfInterest.class);
		List<PointOfInterest> lopoidel = (List<PointOfInterest>) query.execute();
		pm.deletePersistentAll(lopoidel);
		
	}
	finally {
		pm.close();
	}
	
}

public static void main(String[] args) 
{
	POIServiceImpl poi = new POIServiceImpl();
	poi.importDatap1();
	
	
}

@Override
public void importDatap3() {
	String urlstrp1 = "http://www.ugrad.cs.ubc.ca/~e9i8/2012BikeRackDatap3.csv";
	BikeRackParser brp = new BikeRackParser();
	brp.readBikeRack(urlstrp1);
	ArrayList<PointOfInterest> listofpoints = brp.getRackList();
	brp.getJSONobj(listofpoints);
	brp.setLatLng(listofpoints);
	
	PersistenceManager pm = getPersistenceManager();
	try{
		pm.makePersistentAll(listofpoints);
	}
	finally
	{
		pm.close();
	}
	
}

@Override
public void importDatap2() {
	String urlstrp1 = "http://www.ugrad.cs.ubc.ca/~e9i8/2012BikeRackDatap2.csv";
	BikeRackParser brp = new BikeRackParser();
	brp.readBikeRack(urlstrp1);
	ArrayList<PointOfInterest> listofpoints = brp.getRackList();
	brp.getJSONobj(listofpoints);
	brp.setLatLng(listofpoints);
	
	PersistenceManager pm = getPersistenceManager();
	try{
		pm.makePersistentAll(listofpoints);
	}
	finally
	{
		pm.close();
	}
	
}

@Override
public void importDatap4() {
	String urlstrp1 = "http://www.ugrad.cs.ubc.ca/~e9i8/2012BikeRackDatap4.csv";
	BikeRackParser brp = new BikeRackParser();
	brp.readBikeRack(urlstrp1);
	ArrayList<PointOfInterest> listofpoints = brp.getRackList();
	brp.getJSONobj(listofpoints);
	brp.setLatLng(listofpoints);
	
	PersistenceManager pm = getPersistenceManager();
	try{
		pm.makePersistentAll(listofpoints);
	}
	finally
	{
		pm.close();
	}
	
}

@Override
public void addFreq(Long key) {
	PersistenceManager pm = getPersistenceManager();
	try{
		PointOfInterest poi = (PointOfInterest) pm.getObjectById(PointOfInterest.class, 
        		key);
		int setFreq = poi.getFreq() + 1;
		poi.setFrequency(setFreq);
	}
	finally{
		pm.close();
	}
	
	
}

@Override
public ArrayList<PointOfInterest> sortMostRidden(ArrayList<PointOfInterest> alopoi)
{
        PointOfInterest temp;
        for(int i=0; i < alopoi.size()-1; i++){
 
            for(int j=1; j < alopoi.size()-i; j++){
            	int freq1 = alopoi.get(j).getFreq();
            	int freq2 = alopoi.get(j-1).getFreq();
                if(freq2 < freq1){
                	
                    temp = alopoi.get(j-1);
                    alopoi.set(j - 1, alopoi.get(j));
                   // arr[j-1] = arr[j];
                  //  arr[j] = temp;
                    alopoi.set(j, temp);
                    
                }
            }
            //System.out.println((i+1)+"th iteration result: "+Arrays.toString(arr));
        }
        return alopoi;




}
}
package com.google.gwt.pop.server;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.gwt.pop.shared.DrinkingFountain;
import com.google.gwt.pop.client.DrinkingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class DrinkingServiceImpl extends RemoteServiceServlet implements 
DrinkingService{

	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	  
	ArrayList<DrinkingFountain> dflist = new ArrayList<DrinkingFountain>();
	@Override
	public void importDrinkingData() {
		DrinkingParser dp = new DrinkingParser();
		dp.readDrink();
		dflist = dp.getDflist();
		
		PersistenceManager pm = getPersistenceManager();
		
		try{
			pm.makePersistentAll(dflist);
		}
		finally
		{
			pm.close();
		}
		
	}

	@Override
	public void deleteDrinkData() {
		PersistenceManager pm = getPersistenceManager();
		
		try{
			Query query = pm.newQuery(DrinkingFountain.class);
			List<DrinkingFountain> lodf = (List<DrinkingFountain>) query.execute();
			pm.deletePersistentAll(lodf);
		}
		finally
		{
			pm.close();
		}
		
	}

	@Override
	public ArrayList<DrinkingFountain> getDrinkpoints() {
		PersistenceManager pm = getPersistenceManager();
		ArrayList<DrinkingFountain> lodf = new ArrayList<DrinkingFountain>();
		
		try{
			Query query = pm.newQuery(DrinkingFountain.class);
			List<DrinkingFountain> fountains = (List<DrinkingFountain>) query.execute();
			
			for (DrinkingFountain df: fountains)
			{
				lodf.add(df);
			}
		}
		finally{
			pm.close();
		}
		return (ArrayList<DrinkingFountain>) lodf;
	}
	
	private PersistenceManager getPersistenceManager() {
		   return PMF.getPersistenceManager();
		}


}

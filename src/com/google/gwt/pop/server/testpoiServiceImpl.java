package com.google.gwt.pop.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.google.gwt.pop.client.testpoiService;
import com.google.gwt.pop.shared.testpoi;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class testpoiServiceImpl extends RemoteServiceServlet 
implements testpoiService{
	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	@Override
	public void add()
	{
		testpoi test = new testpoi("Test");
		PersistenceManager pm = getPersistenceManager();
		try{
			pm.makePersistent(test);
			//Window.alert("Successfully persisted!");
		}
		finally
		{
			pm.close();
		}
	}

	private PersistenceManager getPersistenceManager() {
		// TODO Auto-generated method stub
		return PMF.getPersistenceManager();
	}

}

package com.google.gwt.pop.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("testpoiService")
public interface testpoiService extends RemoteService{
	
	public void add();

}

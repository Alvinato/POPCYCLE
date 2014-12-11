package com.google.gwt.pop.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("adminlogin")
public interface AdminLoginService extends RemoteService {
  public AdminLoginInfo Adminlogin(String requestUri);
}
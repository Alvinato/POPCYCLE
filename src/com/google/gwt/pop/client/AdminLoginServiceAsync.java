package com.google.gwt.pop.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AdminLoginServiceAsync {
  public void Adminlogin(String requestUri, AsyncCallback<AdminLoginInfo> async);
}
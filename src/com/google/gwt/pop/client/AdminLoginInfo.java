package com.google.gwt.pop.client;

import java.io.Serializable;

public class AdminLoginInfo implements Serializable {

  private boolean loggedIn = false;
  private String AdminloginUrl;
  private String AdminlogoutUrl;
 

  public boolean AdminisLoggedIn() {
    return loggedIn;
  }

  public void AdminsetLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }

  public String AdmingetLoginUrl() {
    return AdminloginUrl;
  }

  public void AdminsetLoginUrl(String loginUrl) {
    this.AdminloginUrl = loginUrl;
  }

  public String AdmingetLogoutUrl() {
    return AdminlogoutUrl;
  }

  public void AdminsetLogoutUrl(String logoutUrl) {
    this.AdminlogoutUrl = logoutUrl;
  }


}
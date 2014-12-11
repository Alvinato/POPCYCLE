package com.google.gwt.pop.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.pop.client.AdminLoginInfo;
import com.google.gwt.pop.client.AdminLoginService;
import com.google.gwt.pop.client.LoginInfo;
import com.google.gwt.pop.client.LoginService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AdminLoginServiceImpl extends RemoteServiceServlet implements
    AdminLoginService {

  public AdminLoginInfo Adminlogin(String requestUri) {
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    AdminLoginInfo AdminloginInfo = new AdminLoginInfo();

    if (user != null) {
      AdminloginInfo.AdminsetLoggedIn(true);
    
      AdminloginInfo.AdminsetLogoutUrl(userService.createLogoutURL(requestUri));
    } else {
      AdminloginInfo.AdminsetLoggedIn(false);
      AdminloginInfo.AdminsetLoginUrl(userService.createLoginURL(requestUri));
    }
    return AdminloginInfo;
  }

}
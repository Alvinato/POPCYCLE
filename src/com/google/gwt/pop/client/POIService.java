
package com.google.gwt.pop.client;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gwt.pop.shared.PointOfInterest;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("POIService")
public interface POIService extends RemoteService {
  public void addPOI(PointOfInterest poi) throws NotLoggedInException;
  public void removePOI(PointOfInterest poi) throws NotLoggedInException;
  public void importDatap1();
  public ArrayList<PointOfInterest> getPoints();
  public void deleteallPOI();
  public void importDatap3();
  public void importDatap2();
  public void importDatap4();
  public void addFreq(Long key);
  public ArrayList<PointOfInterest> sortMostRidden(ArrayList<PointOfInterest> alopoi);
}

package com.google.gwt.pop.client;

import java.util.ArrayList;

import com.google.gwt.pop.shared.PointOfInterest;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface POIServiceAsync {

	void addPOI(PointOfInterest poi, AsyncCallback<Void> callback);

	void getPoints(AsyncCallback<ArrayList<PointOfInterest>> callback);

	void importDatap1(AsyncCallback<Void> callback);

	void removePOI(PointOfInterest poi, AsyncCallback<Void> callback);

	void deleteallPOI(AsyncCallback<Void> callback);

	void importDatap3(AsyncCallback<Void> callback);

	void importDatap2(AsyncCallback<Void> callback);

	void importDatap4(AsyncCallback<Void> callback);

	void sortMostRidden(ArrayList<PointOfInterest> lopoi,
			AsyncCallback<ArrayList<PointOfInterest>> asyncCallback);

	void addFreq(Long key, AsyncCallback<Void> callback);

}
